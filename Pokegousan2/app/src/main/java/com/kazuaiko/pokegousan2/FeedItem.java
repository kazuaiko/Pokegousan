package com.kazuaiko.pokegousan2;

import java.util.Date;

/**
 * Created by naitou_ka on 2016/07/29.
 */
public class FeedItem {
    private String title;
    private String description;
    private String category;
    private String date;
    private String pubDate;
    private String link;
    private Date ddate;

    public FeedItem( String title, String description, String category, String date, Date ddate ){
        this.title = title;
        this.description = description;
        this.category = category;
        this.date = date;
        this.ddate = ddate;
    }

    public FeedItem(){
        this.title = "";
        this.description = "";
        this.category = "";
        this.date = "";
        this.pubDate = "";
        this.link = "";
        this.ddate= null;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public Date getDDate(){
        return ddate;
    }
    public void setDDate(Date ddate){
        this.ddate = ddate;
    }

    public String getPubDate(){
        return pubDate;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getlink(){
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
class FeedItemComparator implements java.util.Comparator {
    public int compare(Object s, Object t) {
        //               + (x > y)
        // compare x y = 0 (x = y)
        //               - (x < y)
        return (int) (((FeedItem) t).getDDate().getTime() - ((FeedItem) s).getDDate().getTime());
    }
}
