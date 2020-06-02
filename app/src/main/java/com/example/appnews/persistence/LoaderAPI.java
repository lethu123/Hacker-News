package com.example.appnews.persistence;

import com.example.appnews.presentation.home.NewModel;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import tool.compet.core.helper.DkJsonHelper;
import tool.compet.core.util.DkLogs;
import tool.compet.core.util.DkStrings;
import tool.compet.core.util.Dks;

import static tool.compet.core.BuildConfig.DEBUG;

public class LoaderAPI {

    public ArrayList<NewModel> loadNews() throws Exception {
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

        ArrayList<NewModel> newModels = new ArrayList<>();

        if (articleIds != null) {
            for (String articleId : articleIds) {
                NewModel model = new NewModel();
                model = loadItem(articleId);
                newModels.add(model);
                if (newModels.size() > 10) {
                    break;
                }
            }
        }

        return newModels;
    }

    public NewModel loadItem(String id) throws Exception  {
        String articleLink = DkStrings.format("https://hacker-news.firebaseio.com/v0/item/%s.json?print=pretty", id);

        URL url = new URL(articleLink);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn = (HttpURLConnection) url.openConnection();

        conn.setConnectTimeout(30_000);
        conn.setReadTimeout(30_000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        String articleJson = Dks.stream2string(conn.getInputStream());

        if (DEBUG) {
            //DkLogs.log(this, "article json: %s", articleJson);
        }

        NewModel newModel = DkJsonHelper.getIns().json2obj(articleJson, NewModel.class);
        if (DEBUG) {
            DkLogs.log(this, "article by: %s", newModel.url);
        }

        return newModel;
    }
}
