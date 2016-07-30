package com.kazuaiko.pokegousan2;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.Xml;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by naitou_ka on 2016/07/29.
 */
public class FeedAsyncTaskLoader extends AsyncTaskLoader<List<FeedItem>> {
    public FeedAsyncTaskLoader(Context context) {
        super(context);
        Log.d("FeedAsyncTaskLoader", "FeedAsyncTaskLoader");
    }

    private static final String RSS_URL1 = "http://tome-uto.com/index.rdf";
    private static final String RSS_URL2 = "http://pokemongo-news.jp/index.rdf";
    private static final String RSS_URL3 = "http://pokemongo-mt.blog.jp/index.rdf";
    private static final String RSS_URL4 = "http://pokemongo24g.com/index.rdf";
    private static final String RSS_URL5 = "http://pokemongo-report.blog.jp/index.rdf";
    private static final String RSS_URL6 = "http://pokemongo-matomechan.com/index.rdf";
    private static final String RSS_URL7 = "http://pokemon-go.2chblog.jp/index.rdf";

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d("FeedAsyncTaskLoader", "onStartLoading");
        forceLoad();
    }

    @Override
    public List<FeedItem> loadInBackground() {
        List<FeedItem> list = new ArrayList<FeedItem>();
        Log.d("FeedAsyncTaskLoader", "loadInBackground");

        String xml =  dataRequest(RSS_URL1);
        list = parse(xml, list);

        xml =  dataRequest(RSS_URL2);
        list = parse(xml, list);

        xml =  dataRequest(RSS_URL3);
        list = parse(xml, list);

        xml =  dataRequest(RSS_URL4);
        list = parse(xml, list);

        xml =  dataRequest(RSS_URL5);
        list = parse(xml,list);

        xml =  dataRequest(RSS_URL6);
        list = parse(xml,list);

        xml =  dataRequest(RSS_URL7);
        list = parse(xml,list);

        Collections.sort(list, new FeedItemComparator());

        return list;
    }

    String dataRequest( String url){
        StringBuilder sb = new StringBuilder();
        String xml;

        AndroidHttpClient client = AndroidHttpClient.newInstance("TEST");
        HttpGet get = new HttpGet(url);
        try{
            HttpResponse response = null;
            try {
                response = client.execute(get);
                BufferedReader br = null;
                br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }finally {
            client.close();
        }

        xml = sb.toString();

        return xml;
    }

    List<FeedItem> parse( String xml, List<FeedItem> list ){
        XmlPullParser xmlPullParser = Xml.newPullParser();
        String stringISO8601;

        try {
            xmlPullParser.setInput(new StringReader(xml));
        } catch (XmlPullParserException e) {
            Log.d("FeedAsyncTaskLoader", "Error");
        }
        int count = 0;
        try {
            int eventType;
            String data = null;
            int itemFlg = -1;
            String fieldName = null;
            FeedItem item = new FeedItem();

            eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("FeedAsyncTaskLoader", "Start document");

                } else if(eventType == XmlPullParser.END_DOCUMENT) {
                    Log.d("FeedAsyncTaskLoader", "End document->");

                } else if(eventType == XmlPullParser.START_TAG) {
                    data = xmlPullParser.getName();
                    //Log.d("FeedAsyncTaskLoader", "Start tag " +  data);
                    if(data.equals("item")){
                        itemFlg = 1;
                        item = new FeedItem();
                    }
                    fieldName = data;

                } else if(eventType == XmlPullParser.END_TAG) {
                    data = xmlPullParser.getName();
                    //Log.d("FeedAsyncTaskLoader", "End tag "+ data);
                    if(data.equals("item")){
                        itemFlg = 0;
                        list.add(item);
                    }

                } else if(eventType == XmlPullParser.TEXT) {
                    data = xmlPullParser.getText();
                    //Log.d("FeedAsyncTaskLoader", "Text "+ data);

                    if(itemFlg == 1){
                        if(fieldName.equals("date")){
                            Log.d("FeedAsyncTaskLoader", "date = "+ data);

                            stringISO8601 = data;
                            //Date dateISO8601 = DateUtils.fromString(stringISO8601, "yyyy-MM-dd'T'HH:mm:ssZ");
                            Date dateISO8601 = DateUtils.fromString("yyyy-MM-dd'T'HH:mm:ssZ",stringISO8601);
                            Log.d("FeedAsyncTaskLoader", "date = "+ dateISO8601);
                            item.setDate(data);
                            item.setDDate(dateISO8601);
                            fieldName = "";
                        }
                        if(fieldName.equals("description")){
                            //Log.d("FeedAsyncTaskLoader", "description = "+ data);
                            item.setDescription(data);
                            fieldName = "";
                        }
                        if(fieldName.equals("title")){
                            //Log.d("FeedAsyncTaskLoader", "title = "+ data);
                            item.setTitle(data);
                            fieldName = "";
                        }
                        if(fieldName.equals("pubDate")){
                            Log.d("FeedAsyncTaskLoader", "pubDate = "+ data);
                            item.setPubDate(data);
                            fieldName = "";
                        }
                        if(fieldName.equals("link")){
                            //Log.d("FeedAsyncTaskLoader", "link = "+ data);
                            count++;
                            item.setLink(data);
                            fieldName = "";
                        }
                    }

                }
                eventType = xmlPullParser.next();
            }

        } catch (Exception e) {
            Log.d("FeedAsyncTaskLoader", "Error");
        }
        Log.d("FeedAsyncTaskLoader", "End ->" + count);

        return list;
    }
}

