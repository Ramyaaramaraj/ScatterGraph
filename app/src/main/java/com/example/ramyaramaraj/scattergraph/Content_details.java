package com.example.ramyaramaraj.scattergraph;

import java.util.ArrayList;
import java.util.HashMap;

public class Content_details {
    public HashMap hm=new HashMap();
    public HashMap getHm() {
        return hm;
    }
    public void setHm(HashMap hm) {
        this.hm = hm;
    }
    public Content_details(HashMap getjson) {
        hm=getjson;
    }


}