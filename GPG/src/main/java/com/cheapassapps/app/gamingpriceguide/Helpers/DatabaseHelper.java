package com.cheapassapps.app.gamingpriceguide.Helpers;
import android.os.AsyncTask;
import android.util.Log;
import com.cheapassapps.app.gamingpriceguide.Objects.Console;
import com.cheapassapps.app.gamingpriceguide.Objects.Game;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class DatabaseHelper {

    private static ArrayList<Console> consoleList = new ArrayList<>();
    private static ArrayList<Game> gamesList = new ArrayList<>();
    private static Game game = new Game();

    public static Game getCurrentGame(){ return game; }
    public static void ClearCurrentGame(){ game = new Game(); }
    public static ArrayList<Game> GetGamesList() {
        return gamesList;
    }
    public static ArrayList<Console> GetConsoleList() { return consoleList; }



    public static void GetAllConsoles(){

        GetAllConsolesTask myTask = new GetAllConsolesTask();
        try {
            myTask.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void GetGamesFromConsole(String consoleid) throws IOException, JSONException {

        GetGamesFromConsoleTask myTask = new GetGamesFromConsoleTask();
        try {
            myTask.execute(consoleid).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void GetGame(int id) throws ExecutionException, InterruptedException {

        GetGameTask myTask = new GetGameTask();
        try {
            myTask.execute(id).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class GetGameTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Integer... params) {

            String gamejson = "{\"id\":\"" + params[0].toString() + "\"}";
            String postResponse = null;
            Game currentGame = new Game();
            try {
                postResponse = doPostRequest("http://www.nelsonbernard.com/gpgapp/phpscripts/gameinfo.php", gamejson);
                JSONObject jsonObject = new JSONObject(postResponse);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);
                    currentGame.setImageName(currentObject.getString("image_name"));
                    currentGame.setName(currentObject.getString("name"));
                    currentGame.setId(Integer.parseInt(currentObject.getString("id")));
                    currentGame.setLoosePrice(currentObject.getString("loose"));
                    currentGame.setCompletePrice(currentObject.getString("cib"));
                    currentGame.setNewPrice(currentObject.getString("new"));
                    currentGame.setConsoleID(currentObject.getString("consoleid"));
                    currentGame.setConsoleRealName(currentObject.getString("consolerealname"));
                    currentGame.setGiantBombID(currentObject.getString("giantbomb_id"));

                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            gamejson = "{\"query\":\"" + currentGame.getName() + "\"}";

            try {
                postResponse = doPostRequest("http://www.nelsonbernard.com/gpgapp/phpscripts/giantbombsearch.php", gamejson);
                JSONObject currentObject = new JSONObject(postResponse);
                JSONArray jsonArray = currentObject.getJSONArray("images");

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    if(obj.get("tags").toString().contains("Screenshots") || obj.get("tags").toString().contains("All Images")){
                        currentGame.setImages(obj.get("thumb_url").toString());
                    }
                }

                if(currentObject.getString("description").equals("null"))
                    if(currentObject.getString("deck").equals("null"))
                        currentGame.setDescription("No description available");
                    else
                        currentGame.setDescription(currentObject.getString("deck"));
                else
                    currentGame.setDescription(currentObject.getString("description"));

                //currentGame.setDescription(currentObject.getString("description"));
                currentGame.setGiantBombID(currentObject.getString("guid"));


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            currentGame.setDescription(removeTags(currentGame.getDescription()));

            if(currentGame.getDescription().startsWith("Overview"))
                currentGame.setDescription(currentGame.getDescription().replaceFirst("Overview", ""));

            game = currentGame;
             return null;
        }
    }

    private static class GetGamesFromConsoleTask extends AsyncTask<String, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            gamesList.clear();
            String consolejson = "{\"consoleid\":\"" + params[0] + "\"}";
            String postResponse = null;
            try {
                postResponse = doPostRequest("http://www.nelsonbernard.com/gpgapp/phpscripts/gamesbyconsole.php", consolejson);
                JSONObject jsonObject = new JSONObject(postResponse);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);
                    Game currentGame = new Game();
                    currentGame.setImageName(currentObject.getString("image"));
                    currentGame.setName(currentObject.getString("name"));
                    currentGame.setId(Integer.parseInt(currentObject.getString("id")));
                    currentGame.setLoosePrice(currentObject.getString("loose"));
                    currentGame.setCompletePrice(currentObject.getString("cib"));
                    currentGame.setNewPrice(currentObject.getString("new"));
                    currentGame.setConsoleID(params[0]);
                    gamesList.add(currentGame);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
        }
    }

    private static class GetAllConsolesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            consoleList.clear();

            try {
                URL url = new URL("http://www.nelsonbernard.com/gpgapp/phpscripts/consolelist.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                String data = "";
                String line = "";

                while (line != null) {
                    line = reader.readLine();
                    data = data + line;
                    Log.d("Response: ", "> " + line);
                }

                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = (JSONArray) jsonObject.get("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    Console console = new Console();
                    console.setName(jsonObject.getString("name"));
                    console.setConsoleID(jsonObject.getString("consoleid"));
                    console.setId(Integer.parseInt(jsonObject.getString("id")));

                    String imageLocation = "";
                    if(console.getConsoleID() == "playstation-1-ps1" || console.getConsoleID() == "playstation-portable-psp")
                        imageLocation = "http://www.nelsonbernard.com/gpgapp/images/consoles/" + console.getConsoleID() + ".svg";
                    else
                        imageLocation = "http://www.nelsonbernard.com/gpgapp/images/consoles/" + console.getConsoleID() + ".png";
                    console.setImageURL(imageLocation);
                    consoleList.add(console);
                    Log.i("Game:  ", console.getName());
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(Void avoid){
            super.onPostExecute(avoid);
        }
    }

    private static String doPostRequest(String url, String json) throws IOException{
        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    static String removeTags(String in)
    {
        int index=0;
        int index2=0;
        while(index!=-1)
        {
            index = in.indexOf("<");
            index2 = in.indexOf(">", index);
            if(index!=-1 && index2!=-1)
            {
                in = in.substring(0, index).concat(in.substring(index2+1, in.length()));
            }
        }
        return in;
    }
}
