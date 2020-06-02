package com.example.appnews.persistence;

public class Post {
    int id;
    String title, poster,content, day, category;

    public Post(int id, String title, String poster, String content, String day, String category) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.content = content;
        this.day = day;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", content='" + content + '\'' +
                ", day='" + day + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}