package com.thatluck.numbermagic;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Lenovo on 2015/p11/15.
 */
public class ContentTool {
    public static String getNowDateTime(){
        String datetime=null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        datetime=sdf.format(date);
        return datetime;
    }

    public static String generateNumbers(int numberCount) {
        StringBuffer numbers=new StringBuffer();
        Random random=new Random();
        for(int i=0;i<numberCount/2;i++){
            int r=random.nextInt(100);
            String word="";
            if(r<10){
                word="0"+r;
            }else if(r==100){
                word="00";
            }else{
                word=r+"";
            }
            numbers.append(word);

        }
        return numbers.toString();
    }
    public static String generateNumbers(int numberCount,int begin,int end) {
        StringBuffer numbers=new StringBuffer();
        Random random=new Random();
        int k=end-begin;
        int old=0;
        for(int i=0;i<numberCount/2;i++){
            double doubleNumber=random.nextDouble();
            int r=new Double(Math.round(doubleNumber * (end - begin))).intValue()+begin;
            if(r==old&&k>=5){
                i--;
                continue;
            }
            old=r;
            String word="";
            if(r<10){
                word="0"+r;
            }else if(r==100){
                word="00";
            }else{
                word=r+"";
            }
            numbers.append(word);

        }
        return numbers.toString();
    }
}
