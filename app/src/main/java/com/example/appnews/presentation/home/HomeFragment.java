package com.example.appnews.presentation.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.example.appnews.constants.Constants;
import com.example.appnews.R;
import com.example.appnews.event.ItemStoryEvent;
import com.example.appnews.persistence.NewsDB;
import com.example.appnews.presentation.AppFragment;
import com.example.appnews.presentation.newdetail.NewDetailFragment;
import com.example.appnews.service.ApiService;
import com.example.appnews.service.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tool.compet.appbundle.binder.annotation.DkBindView;
import tool.compet.core.type.DkCallback;
import tool.compet.core.util.DkLogs;

/**
 * A simple {@link Fragment} subclass.
 * implements ItemStoryEvent
 */
public class HomeFragment extends AppFragment implements ItemStoryEvent, DkCallback<NewModel> {
    @DkBindView(R.id.list)
    RecyclerView recyclerView;

    CustomAdapter customAdapter;
    String[] logos = Constants.logos;
    private ArrayList<NewModel> news = new ArrayList<>();

    NewsDB newsDB;
    NewModel model;
    DkCallback<NewModel> dkCallback;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsDB = new NewsDB(getActivity());
        dkCallback = this;
    }

    @Override
    public int layoutResourceId() {
        return R.layout.home;
    }

    @Override
    public int fragmentContainerId() {
        return R.id.frag_container;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        customAdapter = new CustomAdapter(this, context, news, R.layout.item_lv_home, logos, this);
        recyclerView.setAdapter(customAdapter);

        loadNewsAync();
    }

    private void loadNewsAync() {
        NewsLoader loader = new NewsLoader(this, dkCallback);
        loader.execute();
    }

    void onLoadArticleIdResult(ArrayList<NewModel> newModels) {
        DkLogs.debug(this, "loaded %d news", newModels.size());
//        news.clear();

//        news.addAll(newModels);
//        customAdapter.notifyDataSetChanged();
    }

    public void onItemClick(int bindingAdapterPosition) {
        NewDetailFragment f = new NewDetailFragment();
        f.url = news.get(bindingAdapterPosition).url;

        getChildNavigator()
                .beginTransaction()
                .add(f)
                .commit();
    }

    @Override
    public void itemClick(int id, int type) {
        ApiService service = RetrofitClient.getClient().create(ApiService.class);
        Call<NewModel> call = service.getDetailNews(id);
        call.enqueue(new Callback<NewModel>() {
            @Override
            public void onResponse(Call<NewModel> call, Response<NewModel> response) {
                assert response.body() != null;
                Long result = newsDB.addItem(response.body(), type);
                if (result != -1) {
                    if(type == 1) {
                        Toast.makeText(getContext(), "Đã thêm vào lịch sử", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(getContext(), "Bookmark thành công", Toast.LENGTH_LONG).show();

                } else  Toast.makeText(getContext(), "Đã tồn tại", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<NewModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void call(NewModel model) {
//        this.model = model;
       // news.clear();
        news.add(model);
        customAdapter.notifyDataSetChanged();
    };
}
