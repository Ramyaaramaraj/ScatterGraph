package com.example.ramyaramaraj.scattergraph;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
class DrawLine extends View {
    Paint paint = new Paint();
    Paint point = new Paint();
    Paint plot = new Paint();
    Paint axis = new Paint();
    Paint coordinate = new Paint();
    Paint labels = new Paint();
    public DrawLine(Context context) {
        super(context);
        paint.setColor(Color.BLACK);
        point.setColor(Color.BLUE);
        plot.setColor(Color.RED);
        axis.setColor(Color.YELLOW);
        coordinate.setColor(Color.BLUE);
        labels.setColor(Color.GREEN);
    }
    Content_details cvalues;
    public  void setvalues(Content_details cd) {
        cvalues =cd;
        postInvalidate();
    }
    @Override
    public void onDraw(Canvas canvas) {
        if (cvalues != null) {
            //.........................Canvas Attributes..................
            ArrayList Canvas_details=new ArrayList();
            Canvas_details=cvalues.getCanvas_details();
            int length= Integer.parseInt( (String) Canvas_details.get(0));
            int breadth= Integer.parseInt( (String)Canvas_details.get(1));
            int x= Integer.parseInt( (String)Canvas_details.get(2));
            int y= Integer.parseInt( (String) Canvas_details.get(3));
            int size = getResources().getDimensionPixelSize(R.dimen.myFontSize);
            axis.setTextSize(size);
            //.....................Range and Point Count...............
            ArrayList range=new ArrayList();
            range=cvalues.getRange();
            int xmax_range=Integer.parseInt( (String) range.get(0));
            int ymax_range=Integer.parseInt( (String) range.get(1));
            int point_count=Integer.parseInt( (String) range.get(2));
            //..................Labels................
            ArrayList Caption=new ArrayList();
            Caption=cvalues.getCaption();
            size = getResources().getDimensionPixelSize(R.dimen.myFontSize);
            point.setTextSize(size);
            labels.setTextSize(size);
            canvas.drawText((String) Caption.get(0), breadth/2-150, 30, point);
            canvas.drawText((String) Caption.get(1), breadth/2-100, 70, point);
            Path path = new Path();
            path.moveTo(length-50,length/2);
            path.lineTo(length-50,length/2-100);
            canvas.drawPath(path, point);
            canvas.drawTextOnPath((String) Caption.get(2),path,0,0,point);
            //...............Rectangle Creation..............
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(100, 100, breadth-100, length-100,paint);
            //.............Xarray and Yarray Creation................
            ArrayList Xaxis=new ArrayList();
            Xaxis=cvalues.getXaxis();
            ArrayList Yaxis=new ArrayList();
            Yaxis=cvalues.getYaxis();
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
                    xplot=xInteger(Xaxis,canvas,length,breadth);
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
                    yplot= yInteger(Yaxis,canvas,length,breadth);
                    yc=2;
                    break;
            }
            //.........PLOTTING..............
            plot(Xaxis,Yaxis,xplot,yplot,canvas,xc,yc);
        } else {
            return;
        }
    }
    private void plot(ArrayList xaxis, ArrayList yaxis, HashMap xplot, HashMap yplot, Canvas canvas, int xc, int yc) {
        int s=xaxis.size();
        if((xc==1)&&(yc==1)) {
            for (int j = 0; j < s; j++) {
                String val1= (String) xaxis.get(j);
                String val2= (String) yaxis.get(j);
                Object xcc =  xplot.get(val1);
                Object ycc =  yplot.get(val2);
                canvas.drawCircle((int) xcc, (int) ycc, 5, coordinate);
            }
        }
        if((xc==1)&&(yc==2)) {
            for (int j = 0; j < s; j++) {
                String val1= (String) xaxis.get(j);
                int val2=Integer.parseInt ((String) yaxis.get(j));
                Object xcc =  xplot.get(val1);
                Object ycc =  yplot.get(val2);
                canvas.drawCircle((int) xcc, (int) ycc, 5, coordinate);
            }
        }
        if((xc==2)&&(yc==1)) {
            for (int j = 0; j < s; j++) {
                int val1=Integer.parseInt ((String) xaxis.get(j));
                String val2= (String) yaxis.get(j);
                Object xcc =  xplot.get(val1);
                Object ycc =  yplot.get(val2);
                canvas.drawCircle((int) xcc, (int) ycc, 5, coordinate);
            }
        }
        if((xc==2)&&(yc==2)) {
            for (int j = 0; j < s; j++) {
                int val1=Integer.parseInt ((String) xaxis.get(j));
                int val2=Integer.parseInt ((String) yaxis.get(j));
                Object xcc =  xplot.get(val1);
                Object ycc =  yplot.get(val2);
                canvas.drawCircle((int) xcc, (int) ycc, 5, coordinate);
            }
        }
    }
    private HashMap yInteger(ArrayList Yaxis, Canvas canvas, int length, int breadth) {
        //................Yarray Creation.........
        int yaxis[]=new int[Yaxis.size()];
        for(int i=0;i<Yaxis.size();i++) {
            yaxis[i]= Integer.parseInt((String) Yaxis.get(i)) ;
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
            ypixel.put(yscale.get(i), ystart);
            int plot=(Integer) yscale.get(i);
            int temp_inc=ysplit/(yinc);
            Log.i("this", String.valueOf(temp_inc));
            Log.i("s", String.valueOf(ysplit));
            for(count=1;count<=yinc;count++) {
                ++plot;
                ypixel.put(plot, ystart-(temp_inc*count));
            }
            ystart-=ysplit;
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
    private HashMap xInteger(ArrayList Xaxis,Canvas canvas,int length,int breadth) {
        //.............Xarray Creation................
        int xaxisvalues[]=new int[Xaxis.size()];
        for(int i=0;i<Xaxis.size();i++) {
            xaxisvalues[i]= Integer.parseInt((String) Xaxis.get(i)) ;
        }
        //.................TempArray Generation..........
        //..............X..............
        int temp_xaxis[] =new  int[Xaxis.size()];
        System.arraycopy(xaxisvalues, 0,temp_xaxis, 0, Xaxis.size());
        Arrays.sort(temp_xaxis);
        int xmin = temp_xaxis[0];
       int xmax = temp_xaxis[Xaxis.size() - 1];
       //int xmax=Integer.parseInt((String)Collections.max(Xaxis));
       Log.i("TXT", String.valueOf(xmax));
        //................Xscale Generation..................
        int xinc=(xmax-xmin)/Xaxis.size();
        ArrayList xscale=new ArrayList();
        int temp=xmin;
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
        Log.i("scale", String.valueOf(xscale));
        int xscale_count=xscale.size();
        //...........Vertical Lines...............
        int vxs = 100, vxst =100, vys = 100, vyst = length-100;
        int xsplit=((breadth-100)-100)/xscale_count;
        for (int i = 0; i <=xscale_count; i++) {
            canvas.drawLine(vxs, vys, vxst, vyst, plot);
            vxs = vxs + xsplit;
            vxst = vxst + xsplit;
        }
        //...........xpoint fixing and Xscale Printing...................
       int xstart=100+xsplit;int ystart=length-100;
        HashMap xpixel = new HashMap();
        for(int i=0;i<xscale_count;i++) {
            int count;
            canvas.drawCircle(xstart, ystart, 5, paint);
            canvas.drawText(String.valueOf((Integer) xscale.get(i)), xstart, ystart+50,axis);
            xpixel.put(xscale.get(i), xstart);
            int plot=(Integer) xscale.get(i);
            int temp_inc=(xsplit)/(xinc);
            for(count=1;count<=xinc;count++) {
                ++plot;
                xpixel.put(plot, xstart+(temp_inc*count));
            }
            xstart+=xsplit;
        }
        return  xpixel;
    }
    //........................Function To check X Range Format.......................
    public int xFormat(String xval) {
        int count=0;
        for(int i = 0; i < xval.length(); i++) {
            if(Character.isDigit(xval.charAt(i))) {
                Log.i("scale", String.valueOf(count));
                ++count;
            }
            else {
                Log.i("scale","String");
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
            if(Character.isDigit(yval.charAt(i))) {
                Log.i("scale", String.valueOf(count));
                ++count;
            }
            else {
                Log.i("scale","String");
                count=0;
                break;
            }
        }
        return count;
    }
}