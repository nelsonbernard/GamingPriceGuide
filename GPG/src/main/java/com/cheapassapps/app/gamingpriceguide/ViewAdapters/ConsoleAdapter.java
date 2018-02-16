package com.cheapassapps.app.gamingpriceguide.ViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.cheapassapps.app.gamingpriceguide.Objects.Console;
import com.cheapassapps.app.gamingpriceguide.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ConsoleAdapter extends BaseAdapter{

    private final Context mContext;
    private final ArrayList<Console> consoles;

    public ConsoleAdapter(Context context, ArrayList<Console> consoles) {
        this.mContext = context;
        this.consoles = consoles;

        // Sort consoles array so they are listed alphabetically in gridview
        Collections.sort(consoles, new Comparator<Console>(){
            public int compare(Console o1, Console o2){
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    @Override
    public int getCount() {
        return consoles.size();
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

        final Console console = consoles.get(position);

        if(convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.console_layout_item, null);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview_cover_art);

        Picasso.with(mContext).load(console.getImageURL()).into(imageView);

        return convertView;

    }
}

