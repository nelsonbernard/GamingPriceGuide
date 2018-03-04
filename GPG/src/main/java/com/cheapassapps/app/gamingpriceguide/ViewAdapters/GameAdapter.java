package com.cheapassapps.app.gamingpriceguide.ViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheapassapps.app.gamingpriceguide.Objects.Console;
import com.cheapassapps.app.gamingpriceguide.Objects.Game;
import com.cheapassapps.app.gamingpriceguide.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Nelson on 8/23/2017.
 */

public class GameAdapter extends BaseAdapter{
    private final Context mContext;
    private final ArrayList<Game> games;

    public GameAdapter(Context context, ArrayList<Game> games) {
        this.mContext = context;
        this.games = games;

        // Sort consoles array so they are listed alphabetically in gridview
/*        Collections.sort(games, new Comparator<Game>() {
            public int compare(Game o1, Game o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });*/
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Game game = games.get(position);

        if(convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.game_layout_item, null);
        }
        final TextView nameTextView = (TextView) convertView.findViewById(R.id.gameNameTextView);
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.gameImageView);
      //  Picasso.with(mContext).load(game.getImageURL()).into(imageView);

        nameTextView.setText(game.getName());
        String imageurl = "http://www.cheapassgames.xyz/gpgapp/images/" + game.getConsoleID() + "/" + game.getImageName();
        Picasso.with(mContext).load(imageurl).into(imageView);

        return convertView;

    }
}
