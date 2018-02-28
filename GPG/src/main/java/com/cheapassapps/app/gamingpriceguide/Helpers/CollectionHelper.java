package com.cheapassapps.app.gamingpriceguide.Helpers;


import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Nelson on 2/26/2018.
 */

public class CollectionHelper {

    private String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gpg";
    private String collectionFilename = filepath + "/GPGCollection.gpgc";
    private Context context;

    public CollectionHelper(Context context){
        this.context = context.getApplicationContext();
    }

    public void addToCollection(int gameID){
        Toast.makeText(context, "Saved to collection", Toast.LENGTH_LONG).show();
    }

    public void saveCollection() {


        File appDirectory = new File(context.getFilesDir(), collectionFilename);

    }
}
