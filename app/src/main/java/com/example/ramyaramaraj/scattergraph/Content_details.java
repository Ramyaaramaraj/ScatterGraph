package com.example.ramyaramaraj.scattergraph;

import java.util.ArrayList;
import java.util.HashMap;

//public class Content_details {
//    public HashMap hm=new HashMap();
//    public HashMap getHm() {
//        return hm;
//    }
//    public void setHm(HashMap hm) {
//        this.hm = hm;
//    }
//    public Content_details(HashMap getjson) {
//        hm=getjson;
//    }
//
//
//}


public class Content_details {

    public ArrayList canvas_details = new ArrayList();
    public ArrayList Xaxis = new ArrayList();
    public ArrayList Yaxis = new ArrayList();
    public ArrayList Caption = new ArrayList();
    public ArrayList range = new ArrayList();



    public Content_details(ArrayList canvas_details, ArrayList Caption, ArrayList Xaxis, ArrayList Yaxis, ArrayList range) {
        this.canvas_details = canvas_details;
        this.Xaxis = Xaxis;
        this.Yaxis = Yaxis;
        this.Caption = Caption;
        this.range = range;
    }

    public Content_details(HashMap getjson) {

    }

    public ArrayList getCanvas_details() {
        return canvas_details;
    }

    public void setCanvas_details(ArrayList canvas_details) {
        this.canvas_details = canvas_details;
    }
    public ArrayList getCaption() {
        return Caption;
    }

    public void setCaption(ArrayList caption) {
        Caption = caption;
    }

    public ArrayList getXaxis() {
        return Xaxis;
    }

    public void setXaxis(ArrayList xaxis) {
        Xaxis = xaxis;
    }

    public ArrayList getYaxis() {
        return Yaxis;
    }

    public void setYaxis(ArrayList yaxis) {
        Yaxis = yaxis;
    }

    public ArrayList getRange() {
        return range;
    }

    public void setRange(ArrayList range) {
        this.range = range;
    }
}