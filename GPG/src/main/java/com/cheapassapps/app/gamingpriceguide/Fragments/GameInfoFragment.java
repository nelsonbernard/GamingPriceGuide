package com.cheapassapps.app.gamingpriceguide.Fragments;


import android.graphics.BlurMaskFilter;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheapassapps.app.gamingpriceguide.Helpers.DatabaseHelper;
import com.cheapassapps.app.gamingpriceguide.Helpers.PricingDataHelper;
import com.cheapassapps.app.gamingpriceguide.Objects.Game;
import com.cheapassapps.app.gamingpriceguide.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameInfoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public static GameInfoFragment newInstance(int gameID){
        Bundle args = new Bundle();
        args.putInt("id", gameID);
        GameInfoFragment fragment = new GameInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public GameInfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper.ClearCurrentGame();
        try {
            DatabaseHelper.GetGame(this.getArguments().getInt("id"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_info, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.gameImageView);
        TextView textView = (TextView) view.findViewById(R.id.gameTitleTextView);
        TextView looseText = (TextView) view.findViewById(R.id.looseText);
        TextView cibText = (TextView) view.findViewById(R.id.cibText);
        TextView newText = (TextView) view.findViewById(R.id.newText);
        TextView textConsole = (TextView) view.findViewById(R.id.textConsole);
        TextView description = (TextView) view.findViewById(R.id.description);
        ImageView bannerImageView = (ImageView) view.findViewById(R.id.bannerImageView);
        //HorizontalScrollView screenshotsScrollView = (HorizontalScrollView) view.findViewById(R.id.screenshotsScrollView);
        LinearLayout screenshotsLayout = (LinearLayout) view.findViewById(R.id.screenshotsLayout);
        String imageurl = "http://www.cheapassgames.xyz/gpgapp/images/" + DatabaseHelper.getCurrentGame().getConsoleID() + "/" + DatabaseHelper.getCurrentGame().getImageName();
        Picasso.with(getContext()).load(imageurl).into(imageView);
        textView.setText(DatabaseHelper.getCurrentGame().getName());
        looseText.setText(DatabaseHelper.getCurrentGame().getLoosePrice());
        cibText.setText(DatabaseHelper.getCurrentGame().getCompletePrice());
        newText.setText(DatabaseHelper.getCurrentGame().getNewPrice());
        textConsole.setText(DatabaseHelper.getCurrentGame().getConsoleRealName());
        description.setText(DatabaseHelper.getCurrentGame().getDescription());
        Picasso.with(getContext()).load(DatabaseHelper.getCurrentGame().getScreen_url()).into(bannerImageView);


        for(int i = 0; i < DatabaseHelper.getCurrentGame().getImages().size(); i++)
        {
            ImageView screenshot = new ImageView(getContext());
            screenshot.setMaxHeight(75);
            screenshot.setPadding(5,5,5,5);
            screenshot.setScaleType(ImageView.ScaleType.FIT_CENTER);

            Picasso.with(getContext()).load(DatabaseHelper.getCurrentGame().getImages().get(i)).into(screenshot);
            screenshotsLayout.addView(screenshot);
        }


        return view;
    }
}
