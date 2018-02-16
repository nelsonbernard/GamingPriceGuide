package com.cheapassapps.app.gamingpriceguide.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.cheapassapps.app.gamingpriceguide.Helpers.DatabaseHelper;
import com.cheapassapps.app.gamingpriceguide.ViewAdapters.GameAdapter;
import com.cheapassapps.app.gamingpriceguide.R;
import org.json.JSONException;
import java.io.IOException;


public class GamesFragment extends Fragment {

    public String consoleID;

    public static GamesFragment newInstance(String id){
        Bundle args = new Bundle();
        args.putString("CONSOLEID", id);

        GamesFragment fragment = new GamesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public GamesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.consoleID = this.getArguments().getString("CONSOLEID");
        try {
            DatabaseHelper.GetGamesFromConsole(consoleID);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_games, container, false);
        GridView gamesGridView = (GridView) view.findViewById(R.id.gamesGridView);
        GameAdapter adapter = new GameAdapter(view.getContext(), DatabaseHelper.GetGamesList());

        //GameAdapter adapter = new GameAdapter(view.getContext(), dbHelper.getGamesList());
        gamesGridView.setAdapter(adapter);

        gamesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, GameInfoFragment.newInstance(DatabaseHelper.GetGamesList().get(position).getId()));
                transaction.commit();
            }
        });
        return view;
    }
}
