package com.cheapassapps.app.gamingpriceguide.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.cheapassapps.app.gamingpriceguide.Objects.Console;
import com.cheapassapps.app.gamingpriceguide.Objects.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nelson on 8/25/2017.
 */

public class PricingDataHelper {

    public ArrayList<Console> getConsoleList() {
        return consoleList;
    }


    private ArrayList<Console> consoleList = new ArrayList<>();
    private ArrayList<Game> gamesList = new ArrayList<>();
    private Game game = new Game();
    static final String api_Key = "87155ee49fb9cca0cedcdecd38260c83b2c2d0fc";
    static final String base_API_URL = "http://www.giantbomb.com/api/";
    static final String base_Search_URL = "https://www.giantbomb.com/api/search/?api_key=" + api_Key;
    static final String url_attrib_format = "&format=json";
    static final String url_attrib_query = "&query=";
    static final String url_attrib_resources = "&resources=game";
    private static String base_URL = "http://www.cheapassgames.xyz/public_html/gpgapp/";
    private static String image_URL = "http://www.cheapassgames.xyz/public_html/gpgapp/images/";
    private static String scripts_URL = "http://www.cheapassgames.xyz/public_html/gpgapp/phpscripts/";
    private static String GETCONSOLELISTSCRIPT = scripts_URL + "consolelist.php";
    private static String GETGAMESBYCONSOLESCRIPT = scripts_URL + "gamesbyconsole.php";

    String pdUrl = "";

    //    static final String url = "https://www.giantbomb.com/api/search/?api_key=87155ee49fb9cca0cedcdecd38260c83b2c2d0fc&format=json&query=\"metroid prime 3\"&resources=game";
    private Game tempGame = new Game();

    public Game getGamePricingData(){
        return game;
    }



    public void getCurrentGameInfo(String gameName){


        GetGameInfoTask myTask = new GetGameInfoTask();
        try {
            myTask.execute(gameName).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



    private class GetGameInfoTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            String searchURL = base_Search_URL + url_attrib_format + url_attrib_query + params[0] + url_attrib_resources;

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(searchURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "GPG");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                String data = "";
                String line = "";

                while(line != null){
                    line = reader.readLine();
                    data = data + line;
                    Log.d("Response: ", "> " + line);
                }

                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = (JSONArray) jsonObject.get("results");
                jsonObject = (JSONObject) jsonArray.get(0);

                String gbURL = jsonObject.get("api_detail_url").toString();

                gbURL = gbURL + "?api_key=" + api_Key + "&format=json";
                url = new URL(gbURL);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "GPG");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();

                stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                line = "";

                while(line != null){
                    line = reader.readLine();
                    data = data + line;
                    Log.d("Response: ", "> " + line);
                }

                jsonObject = new JSONObject(data);
                jsonArray = (JSONArray) jsonObject.get("results");
                jsonObject = (JSONObject) jsonArray.get(0);

                String string = jsonObject.get("description").toString();
                string = Jsoup.clean(string, Whitelist.basic());
                tempGame.setId(Integer.parseInt(jsonObject.getString("id")));
                tempGame.setName(jsonObject.getString("name"));

                Log.i("Name", tempGame.getName());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                try{
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
