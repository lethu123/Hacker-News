package com.example.appnews.presentation;

import tool.compet.appbundle.arch.DkSimpleFragment;

public abstract class AppFragment extends DkSimpleFragment {
    @Override
    public boolean isRetainInstance() {
        return false;
    }
}
