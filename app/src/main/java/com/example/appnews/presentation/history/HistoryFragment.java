package com.example.appnews.presentation.history;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.example.appnews.R;
import com.example.appnews.persistence.NewsDB;
import com.example.appnews.presentation.AppFragment;
import com.example.appnews.presentation.home.NewModel;
import com.example.appnews.presentation.newdetail.NewDetailFragment;

import java.util.ArrayList;

import tool.compet.appbundle.binder.annotation.DkBindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends AppFragment {
    @DkBindView(R.id.lvHistory)
    RecyclerView recyclerView;

    CustomAdapter customAdapter;
    NewsDB newsDB;
    private ArrayList<NewModel> news = new ArrayList<>();
    @Override
    public int layoutResourceId() {
        return R.layout.frag_history;
    }

    @Override
    public int fragmentContainerId() {
        return R.id.frag_container;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customAdapter = new CustomAdapter(this, context, news, R.layout.item_lv_history);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(customAdapter);
        loadHistory();

    }

    private void loadHistory() {
        newsDB = new NewsDB(context);
        Cursor cursor = newsDB.getAllItem(1);
        if (cursor != null) {
            news.clear();
            while (cursor.moveToNext()) {
                NewModel model = new NewModel();
                model.setId(cursor.getInt(1));
                model.setBy(cursor.getString(2));
                model.setDescendants(cursor.getString(3));
                model.setScore(cursor.getInt(4));
                model.setTime(cursor.getInt(5));
                model.setTitle(cursor.getString(6));
                model.setType(cursor.getString(7));
                model.setUrl(cursor.getString(8));
                news.add(model);
            }
            customAdapter.notifyDataSetChanged();
        }
    }

    public void onItemClick(int bindingAdapterPosition) {

        NewDetailFragment f = new NewDetailFragment();
        f.url = news.get(bindingAdapterPosition).url;

        getChildNavigator()
                .beginTransaction()
                .add(f)
                .commit();
    }
}
