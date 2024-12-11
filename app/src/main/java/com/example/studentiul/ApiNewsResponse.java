package com.example.studentiul;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiNewsResponse {

    @SerializedName("news")
    private List<NewsItem> news;

    public List<NewsItem> getNews() {
        return news;
    }

    public void setNews(List<NewsItem> news) {
        this.news = news;
    }



    public static class NewsItem {
        @SerializedName("title")
        private String title;

        @SerializedName("date")
        private String datePublished;

        @SerializedName("article")
        private String article;

        @SerializedName("photo_url")
        private String image;


        // Getters and setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDatePublished() {
            return datePublished;
        }

        public void setDatePublished(String datePublished) {
            this.datePublished = datePublished;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setArticle(String article) {
            this.article = article;
        }

        public String getArticle() {
            return article;
        }

    }
}
