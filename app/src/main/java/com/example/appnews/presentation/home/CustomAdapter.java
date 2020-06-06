package com.example.appnews.presentation.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnews.R;
import com.example.appnews.event.ItemStoryEvent;
import com.example.appnews.persistence.NewsDB;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tool.compet.core.datetime.DkDateTimes;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {
    private final HomeFragment homeFragment;

    Context context;
    int layoutID;
    ArrayList<NewModel> news;
    String logos[];
    NewsDB newsDB;
    int idItem;
    ArrayList<NewModel> origin = new ArrayList<>();

    ItemStoryEvent event;
//    AsyncResponse event;

    public CustomAdapter(HomeFragment homeFragment, Context context, ArrayList<NewModel> news, int layoutID, String[] logos, ItemStoryEvent event) {
        this.homeFragment = homeFragment;
        this.context = context;
        this.news = news;
        this.layoutID = layoutID;
        this.logos = logos;
        this.event = event;
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
        holder.txtBy.setText("By: " + newModel.by);
        holder.txtDay.setText(DkDateTimes.formatTime(newModel.time));

        try {
            String urlAddress = newModel.url;
            URL url = new URL(urlAddress);
            String domain = url.getHost();
            holder.txtUrl.setText(domain);
        } catch (Exception e) {
            System.out.println("Exception caught =" + e.getMessage());
        }

        Picasso.with(context)
                .load(logos[position])
                .placeholder(R.drawable.no)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NewModel> filterList = new ArrayList<>();
            if (origin.size() == 0) {
                origin.addAll(news);
            }
            if (constraint.toString().isEmpty()) {
                filterList.addAll(origin);
            } else {
                for (NewModel model : origin) {
                    if (model.title.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(model);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            news.clear();
            news.addAll((Collection<? extends NewModel>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtCategory, txtDay, txtUrl, txtBy;
        ImageView imageView;
        ImageButton imageButton;
        NewModel model = new NewModel();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtDay = itemView.findViewById(R.id.txtDay);
            imageView = itemView.findViewById(R.id.imageItem);
            txtUrl = itemView.findViewById(R.id.txtUrl);
            txtBy = itemView.findViewById(R.id.txtBy);
            imageButton = itemView.findViewById(R.id.bookmark);

            itemView.setOnClickListener(v -> {
                idItem = news.get(getAbsoluteAdapterPosition()).getId();
                // save into db
                event.itemClick(idItem, 1);
                homeFragment.onItemClick(getBindingAdapterPosition());
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idItem = news.get(getAbsoluteAdapterPosition()).getId();
                    showPopupMenu(v);
                }
            });
        }
    }

    // add bookmark
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // add bookmark here
                event.itemClick(idItem, 2);
                return true;
            }
        });
        popupMenu.show();
    }
}

