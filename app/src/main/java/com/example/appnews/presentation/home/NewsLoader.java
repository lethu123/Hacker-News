package com.example.appnews.presentation.home;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import tool.compet.core.helper.DkJsonHelper;
import tool.compet.core.type.DkCallback;
import tool.compet.core.util.DkLogs;
import tool.compet.core.util.DkStrings;
import tool.compet.core.util.Dks;

import static tool.compet.core.BuildConfig.DEBUG;

public class NewsLoader extends AsyncTask<String, NewModel, NewModel> {
    private final HomeFragment callback;
    NewModel model;

    DkCallback dkCallback;
    final String id = "";
    final ArrayList<String> listIds = new ArrayList<>();

    public NewsLoader(HomeFragment callback, DkCallback dkCallback) {
        this.callback = callback;
        this.dkCallback = dkCallback;
    }

    @Override
    protected NewModel doInBackground(String... string) {
        loadNews(); // list ID of item
        for (String articleId : listIds) {
            model = loadItem(articleId);
            publishProgress(model); // item
           // return model; //item
        }
        return null;
    }

    public ArrayList<String> loadNews() {
        try {
            String articleIdsLink = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";

            URL url = new URL(articleIdsLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(30_000);
            conn.setReadTimeout(30_000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            String json = Dks.stream2string(conn.getInputStream());
            String[] articleIds = DkJsonHelper.getIns().json2obj(json, String[].class);

            if (DEBUG) {
                DkLogs.log(this, "article ids: %s", Arrays.toString(articleIds));
            }

            ArrayList<String> newModels = new ArrayList<>();

            if (articleIds != null) {
                for (String articleId : articleIds) {
                    listIds.add(articleId);
                    if (listIds.size() > 10) {
                        break;
                    }
                }
            }
            return listIds;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }

    }

    public NewModel loadItem(String id) {
        try {
            String articleLink = DkStrings.format("https://hacker-news.firebaseio.com/v0/item/%s.json?print=pretty", id);

            URL url = new URL(articleLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(30_000);
            conn.setReadTimeout(30_000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            String articleJson = Dks.stream2string(conn.getInputStream());

            NewModel newModel = DkJsonHelper.getIns().json2obj(articleJson, NewModel.class);
            if (DEBUG) {
                DkLogs.log(this, "article by: %s", newModel.url);
            }

            return newModel;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new NewModel();
        }
    }

    ;

    @Override
    protected void onProgressUpdate(NewModel... newModels) {
        //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
//        super.onProgressUpdate(newModels);
        dkCallback.call(newModels[0]);
    }

    @Override
    protected void onPostExecute(NewModel aBoolean) {
        //callback.onLoadArticleIdResult(aBoolean);
    }


}
