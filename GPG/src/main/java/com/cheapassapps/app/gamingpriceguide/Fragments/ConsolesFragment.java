package com.cheapassapps.app.gamingpriceguide.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cheapassapps.app.gamingpriceguide.Helpers.DatabaseHelper;
import com.cheapassapps.app.gamingpriceguide.Helpers.SQLiteDatabaseHelper;
import com.cheapassapps.app.gamingpriceguide.ViewAdapters.ConsoleAdapter;
import com.cheapassapps.app.gamingpriceguide.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsolesFragment extends Fragment {

    public static ConsolesFragment newInstance(){
        ConsolesFragment fragment = new ConsolesFragment();
        return fragment;
    }

    public ConsolesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper.GetAllConsoles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consoles, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        ConsoleAdapter adapter = new ConsoleAdapter(view.getContext(), DatabaseHelper.GetConsoleList());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, GamesFragment.newInstance(DatabaseHelper.GetConsoleList().get(position).getConsoleID()));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
