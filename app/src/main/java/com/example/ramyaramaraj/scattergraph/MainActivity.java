package com.example.ramyaramaraj.scattergraph;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
public class MainActivity extends AppCompatActivity {
        DrawLine drawLine;
        int h,w,x,y;
        ArrayList Canvas_details=new ArrayList();
        ArrayList Xaxis=new ArrayList();
        ArrayList Yaxis=new ArrayList();
        ArrayList caption=new ArrayList();
        ArrayList range=new ArrayList();
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
                InputStream is = getAssets().open("details.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                Element element = doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("rect");
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        Canvas_details.add(getValue("val", element2));
                    }
                }
                Log.i("canvas", String.valueOf(Canvas_details));
                 nList = doc.getElementsByTagName("captions");
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        caption.add(getValue("title", element2));
                    }
                }
                int count=0;
                nList = doc.getElementsByTagName("plot");
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    ++count;
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        Xaxis.add(getValue("xaxis_point", element2));
                        Yaxis.add(getValue("yaxis_points", element2));
                    }
                }
                nList = doc.getElementsByTagName("limit");
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    ++count;
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        range.add(getValue("value", element2));

                    }
                }


                // Log.i("val", String.valueOf(count));
            }catch (Exception e) {e.printStackTrace();}
            int h= Integer.parseInt( (String) Canvas_details.get(0));
            int w= Integer.parseInt( (String)Canvas_details.get(1));
            int x= Integer.parseInt( (String)Canvas_details.get(2));
            int y= Integer.parseInt( (String) Canvas_details.get(3));

            Content_details cd = new Content_details(Canvas_details,caption,Xaxis,Yaxis,range);
           Log.i("txt", String.valueOf(Xaxis.get(0)));

            DrawLine d = new DrawLine(this);
            setContentView(R.layout.activity_main);
            RelativeLayout rel=(RelativeLayout)findViewById(R.id.rel);
            RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(w,h);
            rl.setMargins(x,y,0,0);
            d.setLayoutParams(rl);
            d.setBackgroundColor(Color.LTGRAY);
            rel.addView(d);
            d.setvalues(cd);
        }
    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
