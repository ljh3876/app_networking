package com.example.jinhwan.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main3Activity extends AppCompatActivity {
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        data =new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView =(ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }
    public void onClick(View v){
        myThread.start();
    }
    String urlstr="https://news.google.co.kr/news?cf=all&hl=ko&pz=1&ned=kr&output=rss";
    Handler myHandler = new Handler();
    Thread myThread = new Thread() {
        @Override
        public void run() {
            try {

                URL url = new URL(urlstr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    int itemCount = readData(urlConnection.getInputStream());
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private int readData(InputStream is) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(is);
            int datacount = parseDocument(document);
            return datacount;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
                  e.printStackTrace();
              }
        return 0;
    }

    private int parseDocument(Document doc){
        Element docEle = doc.getDocumentElement();
        NodeList nodelist = docEle.getElementsByTagName("item");
        int count = 0;
        if((nodelist!=null) && (nodelist.getLength()>0)){
            for(int i =0; i <nodelist.getLength(); i++){
                String newItem = getTagData(nodelist, i);

                if(newItem != null){
                    data.add(newItem);
                    count++;
                }
            }
        }
        return count;
    }

    private String getTagData(NodeList nodelist, int index){
        String newsItem = null;
        Element entry = (Element)nodelist.item(index);
        Element title = (Element)entry.getElementsByTagName("title").item(0);
        Element pubDate = (Element)entry.getElementsByTagName("pubDate").item(0);

        String titleValue = null;
        String pubDateValue = null;
        if(title!=null){
            Node firstChild = title.getFirstChild();
            if(firstChild!=null)
                titleValue = firstChild.getNodeValue();
        }
        if(pubDate!=null){
            Node firstChild2 = pubDate.getFirstChild();
            if(firstChild2!=null)
                pubDateValue=firstChild2.getNodeValue();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Date date = new Date();
        newsItem = titleValue + " - " +simpleDateFormat.format(date.parse(pubDateValue));
        return newsItem;
    }
}
