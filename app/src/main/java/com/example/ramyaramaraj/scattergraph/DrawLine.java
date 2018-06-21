package com.example.ramyaramaraj.scattergraph;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
class DrawLine extends View {
    Paint paint = new Paint();
    Paint point = new Paint();
    Paint plot = new Paint();
    Paint axis = new Paint();
    Paint coordinate = new Paint();
    Paint labels = new Paint();
    public DrawLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK);
        point.setColor(Color.BLUE);
        plot.setColor(Color.RED);
        axis.setColor(Color.YELLOW);
        coordinate.setColor(Color.BLUE);
        labels.setColor(Color.GREEN);
    }
    public DrawLine(Context context) {
        super(context);

    }
    HashMap<String,ArrayList> cvalues;
    public  void setvalues(HashMap<String,ArrayList> cd) {
        cvalues =cd;
        postInvalidate();
    }
    @Override
    public void onDraw(Canvas canvas) {
        if (cvalues != null) {
            //.........................Canvas Attributes..................
            int length= canvas.getHeight();
            int breadth=canvas.getWidth();
            int size = getResources().getDimensionPixelSize(R.dimen.myFontSize);
            axis.setTextSize(size);
            //..................Colours................
            ArrayList colours=new ArrayList();
            colours.addAll(cvalues.get("Colours"));
            //..................Labels................
            ArrayList Labels=new ArrayList();
            Labels.addAll(cvalues.get("Labels"));
            size = getResources().getDimensionPixelSize(R.dimen.myFontSize);
            point.setTextSize(size);
            labels.setTextSize(size);
            canvas.drawText((String) Labels.get(0), breadth/2-150, 30, point);
            canvas.drawText((String) Labels.get(1), breadth/2-100, 70, point);
            Path path = new Path();
            path.moveTo(length-50,length/2);
            path.lineTo(length-50,length/2-100);
            canvas.drawPath(path, point);
            canvas.drawTextOnPath((String) Labels.get(2),path,0,0,point);
            //...............Rectangle Creation..............
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            int length_dec=length/20;
            int breadth_dec=breadth/20;
            canvas.drawRect(100, 100, breadth-100, length-100,paint);
            //.............Xarray and Yarray Creation................
            ArrayList Xaxis=new ArrayList();
            Xaxis.addAll(cvalues.get("Xaxis"));
            ArrayList Yaxis=new ArrayList();
            Yaxis.addAll(cvalues.get("Yaxis"));
            //.............XFormat Checking.............
            String xcheck=(String) Xaxis.get(0);
            int check=xFormat(xcheck);
            int xc=0;
            HashMap xplot=new HashMap();
            switch (check)
            {
                case 0:
                    xplot=xString(Xaxis,canvas,length,breadth);
                    xc=1;
                    break;
                default:
                    Log.i("scale","Integer");
                    xplot=xNumber(Xaxis,canvas,length,breadth);
                    xc=2;
                    break;
            }
            //.............YFormat Checking.............
            String ycheck=(String) Yaxis.get(0);
            int checky=yFormat(ycheck);
            int yc=0;
            HashMap yplot=new HashMap();
            switch (checky)
            {
                case 0:
                    yplot=yString(Yaxis,canvas,length,breadth);
                    yc=1;
                    break;
                default:
                    Log.i("scale","y...Integer");
                    yplot= yNumber(Yaxis,canvas,length,breadth);
                    yc=2;
                    break;
            }
            //.........PLOTTING..............
            plot(Xaxis,Yaxis,xplot,yplot,canvas,xc,yc);
        } else {
            return;
        }
    }
    private void plot(ArrayList xaxis, ArrayList yaxis,  HashMap xplot, HashMap yplot, Canvas canvas, int xc, int yc) {
        int s=xaxis.size();
        if((xc==1)&&(yc==1)) { //X and Y String
            for (int j = 0; j < s; j++) {
                String val1=(String) xaxis.get(j);
                String val2=(String) yaxis.get(j);
                Object xcc =  xplot.get(val1);
                Object ycc =  yplot.get(val2);
                canvas.drawCircle((int) xcc, (int) ycc, 5, coordinate);
            }
        }
        if((xc==1)&&(yc==2)) {  //X String Y Float
            for (int j = 0; j < s; j++) {
                    String val2=(String) yaxis.get(j);
                    float tc=Float.parseFloat(val2);
                    //Check wheather the number is Integer or Float..........
                    if((yplot.containsKey(tc))) {
                        float new_value=  Float.parseFloat((String)yaxis.get(j));
                        String val1= (String) xaxis.get(j);
                        Object ycc =  yplot.get(new_value);
                        Object xcc =  xplot.get(val1);
                        canvas.drawCircle((int) xcc, (int) ycc, 5, coordinate);
                    }
                    else {
                        float val;
                        //...............Float Logic.....
                        float xcc =Float.parseFloat((String) yaxis.get(j));
                        //...........Integer part separation........
                        int integer_part= (int) xcc;
                        //........Decimal part separation........
                        String dot=".";
                        int count=0;
                        for(int d=0;d<val2.length();d++) {
                            if(String.valueOf(val2.charAt(d)).equals(dot)) {
                                ++count;
                                break;
                            }
                        }
                        String decimal=val2.substring(count+1);
                        float decimal_part=Float.parseFloat((decimal));
                        //...........Distance between two Elements.................
                        int distance;
                        Object temp1=yplot.get((float)integer_part);
                        Object temp2;
                        int v=s-1;
                        if(j==v) {
                            temp2=yplot.get((float)integer_part-1);
                        }
                        else {
                            temp2=yplot.get((float)integer_part+1);
                        }
                        //distance=Integer.parseInt((String) temp2)-Integer.parseInt((String) temp1);
                        distance=(int)temp2-(int)temp1;
                        //.................Internal Distance Calculation..............
                        float internal_distance=(float)distance/100;
                        //..........New Pixel......
                        float pixel_new= (float)((float)internal_distance*decimal_part) ;
                        val=(int)temp1+pixel_new;
                        String val1= (String) xaxis.get(j);
                        Object xcc_f =  xplot.get(val1);
                        canvas.drawCircle((int)xcc_f,(int) val, 5, coordinate);
                    }
            }
        }
        if((xc==2)&&(yc==1)) {   //X Number....Y String....//
            for (int j = 0; j < s; j++) {
                //int val1=Integer.parseInt ((String) xaxis.get(j));;
                String val1=(String) xaxis.get(j);
                float tc=Float.parseFloat(val1);
                //Check wheather the number is Integer or Float..........
                if((xplot.containsKey(tc))){
                    float new_value=  Float.parseFloat((String)xaxis.get(j));
                    String val2= (String) yaxis.get(j);
                    Object xcc =  xplot.get(new_value);
                    Object ycc =  yplot.get(val2);
                    canvas.drawCircle((int) xcc, (int) ycc, 5, coordinate);
                }
                else {
                    float val;
                    //...............Float Logic.....
                    float xcc =Float.parseFloat((String) xaxis.get(j));
                    //...........Integer part separation........
                    int integer_part= (int) xcc;
                    //........Decimal part separation........
                    String dot=".";
                    int count=0;
                    for(int d=0;d<val1.length();d++){
                        if(String.valueOf(val1.charAt(d)).equals(dot)){
                            ++count;
                            break;
                        }
                    }
                    String decimal=val1.substring(count+1);
                    float decimal_part=Float.parseFloat((decimal));
                    //...........Distance between two Elements.................
                    int distance;
                    Object temp1=xplot.get((float)integer_part);
                    Object temp2;
                    int v=s-1;
                    if(j==v) {
                        temp2=xplot.get((float)integer_part-1);
                    }
                    else {
                        temp2=xplot.get((float)integer_part+1);
                    }
                    distance=(int)temp2-(int)temp1;
                    //.................Internal Distance Calculation..............
                    float internal_distance=(float)distance/100;
                    //..........New Pixel......
                    float pixel_new= (float)((float)internal_distance*decimal_part) ;
                    val=(int)temp1+pixel_new;
                    String val2= (String) yaxis.get(j);
                    Object ycc_f =  yplot.get(val2);
                    canvas.drawCircle((int)val,(int) ycc_f, 5, coordinate);
                }
            }
        }
        if((xc==2)&&(yc==2)) {    //X And Y Number
            Object xcc_f,ycc_f;
            for (int j = 0; j < s; j++) {
                //....................Corresponding X Range....................//
                String val2_x=(String) xaxis.get(j);
                float tc_x=Float.parseFloat(val2_x);
                //Check wheather the number is Integer or Float..........
                if((xplot.containsKey(tc_x))) {
                    Log.i("Enter xi","Enterinhg x int ");
                    float new_value_x=  Float.parseFloat((String)xaxis.get(j));
                    String val1_x= (String) xaxis.get(j);
                    float temp_val1_x=Float.parseFloat(val1_x);
                    xcc_f = (int) xplot.get(temp_val1_x);
                    Log.i("msg","1 x");
                }
                else {
                    Log.i("Enter xf","Entering x float");
                    float val_x;
                    //...............Float Logic.....
                    float xcc_x =Float.parseFloat((String) xaxis.get(j));
                    //...........Integer part separation........
                    int integer_part_x= (int) xcc_x;
                    //........Decimal part separation........
                    String dot_x=".";
                    int count_x=0;
                    for(int d=0;d<val2_x.length();d++) {
                        if(String.valueOf(val2_x.charAt(d)).equals(dot_x)) {
                            break;
                        }
                        ++count_x;
                    }
                    String decimal_x=val2_x.substring(count_x+1);
                    float decimal_part_x=Float.parseFloat((decimal_x));
                    //...........Distance between two Elements.................
                    int distance_x;
                    Object temp1=xplot.get((float)integer_part_x);
                    Log.i("S", String.valueOf(temp1));
                    Object temp2;
                    int v=s-1;
                    if(j==v) {
                        temp2=xplot.get((float)integer_part_x-1);
                    }
                    else {
                        temp2=xplot.get((float)integer_part_x+1);
                    }
                    distance_x=(int)temp2-(int)temp1;
                    //.................Internal Distance Calculation..............
                    float internal_distance_x=(float)distance_x/100;
                    //..........New Pixel......
                    float pixel_new= (float)((float)internal_distance_x*decimal_part_x) ;
                    val_x=(int)temp1+pixel_new;
                    xcc_f = (int)val_x;
                    Log.i("msg","Exit X float");

                }
                //....................Corresponding Y Range....................//
                String val2_y=(String) yaxis.get(j);
                float tc_y=Float.parseFloat(val2_y);
                //Check wheather the number is Integer or Float..........
                if((yplot.containsKey(tc_y))) {
                    Log.i("Enter yi","Enterinhg y int ");
                    float new_value_y=  Float.parseFloat((String)yaxis.get(j));
                    String val1_y= (String) yaxis.get(j);
                    float temp_val1_y=Float.parseFloat(val1_y);
                    ycc_f = (int) yplot.get(temp_val1_y);
                    Log.i("y ", String.valueOf((int)ycc_f));
                    Log.i("msg","123");
                }
                else {
                    Log.i("Enter yf ","Enterinhg y float ");
                    float val_y;
                    //...............Float Logic.....
                    float xcc_y =Float.parseFloat((String) yaxis.get(j));
                    //...........Integer part separation........
                    int integer_part= (int) xcc_y;
                    //........Decimal part separation........
                    String dot_y=".";
                    int count_y=0;
                    for(int d=0;d<val2_y.length();d++) {
                        if(String.valueOf(val2_y.charAt(d)).equals(dot_y)) {
                            ++count_y;
                            break;
                        }
                    }
                    String decimal_y=val2_y.substring(count_y+1);
                    float decimal_part_y=Float.parseFloat((decimal_y));
                    //...........Distance between two Elements.................
                    int distance_y;
                    Object temp1_y=yplot.get((float)integer_part);
                    Object temp2_y;
                    int v_y=s-1;
                    if(j==v_y) {
                        temp2_y=yplot.get((float)integer_part-1);
                    }
                    else {
                        temp2_y=yplot.get((float)integer_part+1);
                    }
                    distance_y=(int)temp2_y-(int)temp1_y;
                    //.................Internal Distance Calculation..............
                    float internal_distance_y=(float)distance_y/100;
                    //..........New Pixel......
                    float pixel_new= (float)((float)internal_distance_y*decimal_part_y) ;
                    val_y=(int)temp1_y+pixel_new;
                    ycc_f = (int)val_y;


                }
            canvas.drawCircle((int) xcc_f, (int)ycc_f, 5, coordinate);
            }
        }
    }
    private HashMap yNumber(ArrayList Yaxis, Canvas canvas, int length, int breadth) {
        //................Yarray Creation.........
        int yaxis[]=new int[Yaxis.size()];
        for(int i=0;i<Yaxis.size();i++) {
            float temp=Float.parseFloat((String) Yaxis.get(i));
            yaxis[i]= (int)(temp) ;
        }
        //.................TempArray Generation..........
        //.....................Y.................
        int temp_yaxis[] =new  int[Yaxis.size()];
        System.arraycopy(yaxis, 0,temp_yaxis, 0, Yaxis.size());
        Arrays.sort(temp_yaxis);
        int ymin = temp_yaxis[0];
        int ymax = temp_yaxis[Yaxis.size() - 1];
        //...............yscale Generation..................
        int yinc=(ymax-ymin)/Yaxis.size();
        ArrayList yscale=new ArrayList();
        int temp=ymin;
        if(yinc!=0)
        {
            for(;;) {
                if(temp<=ymax) {
                    yscale.add(temp);
                    temp=temp+yinc;
                }
                else {
                    yscale.add(temp);
                    break;
                }
            }
        }
        else
        {
            yinc=1;
            for(;;) {
                if(temp<=ymax) {
                    yscale.add(temp);
                    temp=temp+yinc;
                }
                else {
                    yscale.add(temp);
                    break;
                }
            }
        }

        Log.i("txt", String.valueOf(Yaxis));
        Log.i("yyscale", String.valueOf(yscale));
        int yscale_count=yscale.size();
        //...........Horizontal Lines.................
        int hxs = 100, hxst =breadth-100, hys =length-100, hyst =length-100;
        int ysplit=((length-100)-100)/yscale_count;
        for (int i = 0; i <=yscale_count; i++) {
            canvas.drawLine(hxs, hys, hxst, hyst,plot);
            hys = hys -ysplit;
            hyst = hyst - ysplit;
        }
        //.............ypoint fixing and Yscale Printing................
        int xstart=100;  int ystart=length-100-ysplit;
        HashMap ypixel = new HashMap();
        for(int i=0;i<yscale_count;i++) {
            int count;
            canvas.drawCircle(xstart, ystart, 5, paint);
            canvas.drawText(String.valueOf((Integer) yscale.get(i)), xstart-60, ystart, axis);
            ypixel.put(Float.parseFloat(String.valueOf(yscale.get(i))), ystart);
            int plot=(Integer) yscale.get(i);
            int temp_inc=ysplit/(yinc);
            Log.i("this", String.valueOf(temp_inc));
            Log.i("s", String.valueOf(ysplit));
            if(temp_inc!=0) {
                for (count = 1; count <= yinc; count++) {
                    ++plot;
                    ypixel.put((float) plot, ystart - (temp_inc * count));
                }
                ystart-=ysplit;
            }
            else
            {
                temp_inc=1;
                for (count = 1; count <= yinc; count++) {
                    ++plot;
                    ypixel.put((float) plot, ystart - (temp_inc * count));
                }
                ystart-=ysplit;
            }
        }
        Log.i("Xsize", String.valueOf(ypixel));
        return ypixel;
    }
    private HashMap yString(ArrayList Yaxis, Canvas canvas, int length, int breadth) {
        //...........Horizontal Lines.................
        int hxs = 100, hxst =breadth-100, hys =length-100, hyst =length-100;
        int ysplit=((length-100)-100)/Yaxis.size();
        for (int i = 0; i <=Yaxis.size(); i++) {
            canvas.drawLine(hxs, hys, hxst, hyst,plot);
            hys = hys -ysplit;
            hyst = hyst - ysplit;
        }
        //.............Ypoint fixing and Yscale Printing...............
        int xstart=100;  int ystart=length-100-ysplit;
        HashMap ypixel = new HashMap();
        for(int i=0;i<Yaxis.size();i++) {
            canvas.drawCircle(xstart, ystart, 5, paint);
            canvas.drawText(String.valueOf(Yaxis.get(i)), xstart-90, ystart, axis);
            ypixel.put(Yaxis.get(i), ystart);
            ystart=ystart-ysplit;
        }
        return ypixel;
    }
    private HashMap xString(ArrayList Xaxis,Canvas canvas,int length,int breadth) {
        //...........Vertical Lines...............
        int vxs = 100, vxst =100, vys = 100, vyst = length-100;
        int xsplit=((breadth-100)-100)/Xaxis.size();
        for (int i = 0; i <=Xaxis.size(); i++) {
            canvas.drawLine(vxs, vys, vxst, vyst, plot);
            vxs = vxs + xsplit;
            vxst = vxst + xsplit;
        }
        //.............Xpoint fixing and Xscale Printing...............
        int xstart=100+xsplit;int ystart=length-100;
        HashMap xpixel = new HashMap();
        for(int i=0;i<Xaxis.size();i++) {
            canvas.drawCircle(xstart, ystart, 5, paint);
            canvas.drawText( String.valueOf( Xaxis.get(i)), xstart, ystart+50,axis);
            xpixel.put( Xaxis.get(i), xstart);
            xstart+=xsplit;
        }
        return  xpixel;
    }
    private HashMap xNumber(ArrayList Xaxis,Canvas canvas,int length,int breadth) {
        //.............Xarray Creation................
        int xaxisvalues[]=new int[Xaxis.size()];
        for(int i=0;i<Xaxis.size();i++) {
            float temp=Float.parseFloat((String) Xaxis.get(i));
            xaxisvalues[i]= (int)(temp) ;
        }
        //.................TempArray Generation..........
        //..............X..............
        int temp_xaxis[] =new  int[Xaxis.size()];
        System.arraycopy(xaxisvalues, 0,temp_xaxis, 0, Xaxis.size());
        Arrays.sort(temp_xaxis);
        int xmin = temp_xaxis[0];
        int xmax = temp_xaxis[Xaxis.size() - 1];
        //................Xscale Generation..................
       int xinc= (int) ((xmax-xmin)/Xaxis.size());
        ArrayList xscale=new ArrayList();
        int temp=(int)xmin;
        if(xinc!=0)
        {
            for(;;) {
                if(temp<=xmax) {
                    xscale.add(temp);
                    temp=temp+xinc;
                }
                else {
                    xscale.add(temp);
                    break;
                }
            }
        }
        else
        {
            xinc=1;
            for(;;) {
                if(temp<=xmax) {
                    xscale.add(temp);
                    temp=temp+xinc;
                }
                else {
                    xscale.add(temp);
                    break;
                }
            }
        }

        Log.i("scale", String.valueOf(xscale));
        int xscale_count=xscale.size();
        //...........Vertical Lines...............
        int vxs = 100, vxst =100, vys = 100, vyst = length-100;
        int xsplit=((breadth-100)-100)/xscale_count;
        Log.i("scale", String.valueOf(xsplit));
        for (int i = 0; i <=xscale_count; i++) {
            canvas.drawLine(vxs, vys, vxst, vyst, plot);
            vxs = vxs + xsplit;
            vxst = vxst + xsplit;
        }
        //...........xpoint fixing and Xscale Printing...................
        int xstart=100+xsplit;int ystart=length-100;
        HashMap xpixel = new HashMap();
        int temp_inc=(int) (xsplit)/(xinc);
        Log.i("tempinc", String.valueOf(temp_inc));
        for(int i=0;i<xscale_count;i++) {
            int count;
            canvas.drawCircle(xstart, ystart, 5, paint);
            canvas.drawText(String.valueOf((int) xscale.get(i)), xstart, ystart+50,axis);
            xpixel.put(Float.parseFloat(String.valueOf(xscale.get(i))), xstart);
            int plot=( int) xscale.get(i);
            if(temp_inc!=0) {
                for (count = 1; count <= xinc; count++) {
                    ++plot;
                    xpixel.put((float) plot, xstart + (temp_inc * count));
                }
                xstart += xsplit;
            }
            else
            {
                temp_inc=0;
                for (count = 1; count <= xinc; count++) {
                    ++plot;
                    xpixel.put((float) plot, xstart + (temp_inc * count));
                }
                xstart += xsplit;

            }
        }
        return  xpixel;
    }
    //........................Function To check X Range Format.......................
    public int xFormat(String xval) {
        int count=0;
        for(int i = 0; i < xval.length(); i++) {
            if((Character.isDigit(xval.charAt(i)))||(Boolean)((String.valueOf(xval.charAt(i))).equals("."))) {
                ++count;
            }
            else {
                count=0;
                break;
            }
        }
        return count;
    }
    //........................Function To check y Range Format.......................
    public int yFormat(String yval) {
        int count=0;
        for(int i = 0; i < yval.length(); i++) {
            if((Character.isDigit(yval.charAt(i)))||(Boolean)((String.valueOf(yval.charAt(i))).equals("."))) {
                ++count;
            }
            else {
                count=0;
                break;
            }
        }
        return count;
    }
}