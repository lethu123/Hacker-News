package com.example.appnews.presentation.home;

import android.os.AsyncTask;

import com.example.appnews.event.AsyncResponse;
import com.example.appnews.persistence.LoaderAPI;

import java.util.ArrayList;

public class LoadItem extends AsyncTask<String, Integer, NewModel> {

//    public AsyncResponse delegate;
   // public LoadItem() { }

    public interface AsyncResponse {
        void processFinish(NewModel model);
    }

    public AsyncResponse delegate = null;

    public LoadItem(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected NewModel doInBackground(String... strings) {
        String id = strings[0];
        try {
            LoaderAPI loaderAPI = new LoaderAPI();
            return loaderAPI.loadItem(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new NewModel();
        }
    }

    @Override
    protected void onPostExecute(NewModel model) {
        delegate.processFinish(model);
    }

}
