package com.marvin.cararenaa.services;
import com.marvin.cararenaa.Constants;
import com.marvin.cararenaa.models.Carzarena;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MarketCheckService {

    public static void findcars(String api_key, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MARKETCHECK_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.MARKETCHECK_BUILD_QUERY_PARAMETER, api_key);
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.MARKETCHECK_TOKEN)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public ArrayList<Carzarena> processResults(Response response){
        ArrayList<Carzarena> carzarenas = new ArrayList<>();

        try{
            String jsonData = response.body().string();
            JSONObject carJSON = new JSONObject(jsonData);
            JSONArray listingsJSON = carJSON.getJSONArray("listings");
            if (response.isSuccessful()){
                for (int i = 0; i < listingsJSON.length(); i++){
                    JSONObject cararenaJSON = listingsJSON.getJSONObject(i);
                    String make = cararenaJSON.getJSONObject("build").getString("make");
                    String model = cararenaJSON.getJSONObject("build").getString("model");
                    String website = cararenaJSON.getJSONObject("dealer").getString("website");
                    String year = cararenaJSON.getJSONObject("build").getString("year");
                    String photo_links = cararenaJSON.getJSONObject("media").getString("photo_links");

                    String body_type = cararenaJSON.getJSONObject("build").getString("body_type");
                    String vehicle_type = cararenaJSON.getJSONObject("build").getString("vehicle_type");
                    String engine = cararenaJSON.getJSONObject("build").getString("engine");
                    String made_in = cararenaJSON.getJSONObject("build").getString("made_in");

                    String latitude = cararenaJSON.getJSONObject("dealer").getString("latitude");
                    String longitude = cararenaJSON.getJSONObject("dealer").getString("longitude");
                    ArrayList<String> details = new ArrayList<>();
                    JSONArray detailsJSON = cararenaJSON.getJSONObject("media").getJSONArray("photo_links");
                    for (int y = 0; y < detailsJSON.length(); y++){
                        details.add(detailsJSON.get(y).toString());
                    }

                    Carzarena carzarena = new Carzarena(make, model, website, year,
                            photo_links,body_type,vehicle_type, engine, made_in ,details, latitude, longitude);
                    carzarenas.add(carzarena);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return carzarenas;
    }

}
