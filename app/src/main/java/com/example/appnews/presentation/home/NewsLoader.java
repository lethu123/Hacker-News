package com.example.appnews.presentation.home;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.example.appnews.persistence.LoaderAPI;
import java.util.ArrayList;
import static tool.compet.core.BuildConfig.DEBUG;

public class NewsLoader extends AsyncTask<String, Integer, ArrayList> {
    private final HomeFragment callback;
    static CustomAdapter customAdapter;
    static ArrayList<NewModel> listNews = new ArrayList<>();
    public NewsLoader(HomeFragment callback) {
        this.callback = callback;
    }

    @Override
    protected ArrayList doInBackground(String... string) {
        try {
            LoaderAPI loaderAPI = new LoaderAPI();
            return loaderAPI.loadNews();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            public void run() {
                customAdapter.notifyDataSetChanged();
            }
        });

        //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList aBoolean) {
        callback.onLoadArticleIdResult(aBoolean);
    }


}
