package com.example.appnews.presentation.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewModel {
    @Expose
    @SerializedName("by")
    public String by;

    @Expose
    @SerializedName("descendants")
    public String descendants;

    @Expose
    @SerializedName("id")
    public int id;

    @Expose
    @SerializedName("kids")
    public int[] kids;

    @Expose
    @SerializedName("score")
    public int score;

    @Expose
    @SerializedName("time")
    public long time;

    @Expose
    @SerializedName("title")
    public String title;

    @Expose
    @SerializedName("type")
    public String type;

    @Expose
    @SerializedName("url")
    public String url;


    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getDescendants() {
        return descendants;
    }

    public void setDescendants(String descendants) {
        this.descendants = descendants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getKids() {
        return kids;
    }

    public void setKids(int[] kids) {
        this.kids = kids;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
