package com.example.appnews.presentation.favourite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnews.R;
import com.example.appnews.persistence.NewsDB;
import com.example.appnews.presentation.home.NewModel;

import java.net.URL;
import java.util.ArrayList;

import tool.compet.core.datetime.DkDateTimes;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private final FavouriteFragment favouriteFragment;
    Context context;
    int layoutID;
    static  ArrayList<NewModel> news;
    int idItem;
    NewsDB newsDB;

    public CustomAdapter(FavouriteFragment favouriteFragment, Context context, ArrayList<NewModel> news, int layoutID) {
        this.favouriteFragment = favouriteFragment;
        this.context = context;
        this.news = news;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View customView = inflater.inflate(layoutID, null);
        ViewHolder viewHolder = new ViewHolder(customView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewModel newModel = news.get(position);
        holder.txtTitle.setText(newModel.title);
        holder.txtCategory.setText(newModel.type);
        holder.txtDay.setText(DkDateTimes.formatTime(newModel.time));

        try {
            String urlAddress = newModel.url;
            URL url = new URL(urlAddress);
            String domain = url.getHost();
            holder.txtUrl.setText(domain);
        } catch (Exception e) {
            System.out.println("Exception caught =" + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtCategory, txtDay, txtUrl;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitleF);
            txtCategory = itemView.findViewById(R.id.txtCategoryF);
            txtDay = itemView.findViewById(R.id.txtDayF);
            txtUrl = itemView.findViewById(R.id.txtUrlF);
            imageButton = itemView.findViewById(R.id.remove_bookmark);

            itemView.setOnClickListener(v -> {
               favouriteFragment.onItemClick(getBindingAdapterPosition());
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // delete from DB
                    idItem = news.get(getAbsoluteAdapterPosition()).getId();
                    newsDB = new NewsDB(context);
                    newsDB.deleteItem(idItem, 2);
                    news.remove(getLayoutPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

}
