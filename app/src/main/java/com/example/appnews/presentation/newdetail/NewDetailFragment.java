package com.example.appnews.presentation.newdetail;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import com.example.appnews.MainActivity;
import com.example.appnews.R;
import com.example.appnews.presentation.AppFragment;

import tool.compet.appbundle.binder.annotation.DkBindView;

public class NewDetailFragment extends AppFragment {
    @DkBindView(R.id.new_detail_webview)
    WebView webView;

    public String url;

    public NewDetailFragment() {
    }

    @Override
    public int layoutResourceId() {
        return R.layout.frag_new_detail;
    }

    @Override
    public int fragmentContainerId() {
        return 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Story Detail");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        webView.loadUrl(url);
    }

}
