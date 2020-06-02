package com.example.appnews.event;

import com.example.appnews.presentation.home.NewModel;

public interface AsyncResponse {
    void processFinish(NewModel model);
}
