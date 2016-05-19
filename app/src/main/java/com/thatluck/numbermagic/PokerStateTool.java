package com.thatluck.numbermagic;

/**
 * Created by Administrator on 2016/5/19.
 */
 public class  PokerStateTool {
    public static boolean FLAG_BEGIN;
    public static boolean FLAG_PAUSE;
    public static boolean FLAG_QUIT;
    public static void init(){
        FLAG_BEGIN=false;
        FLAG_PAUSE=false;
        FLAG_QUIT=false;
    }
    public static void setBegin(){
        FLAG_BEGIN=true;
        FLAG_PAUSE=false;
        FLAG_QUIT=false;

    }
    public static void setPause(){
        FLAG_BEGIN=true;
        FLAG_PAUSE=true;
        FLAG_QUIT=false;
    }
    public static void setStop(){

        FLAG_QUIT=true;

    }
    //是否开始
    public static boolean isBegined(){
        if(FLAG_BEGIN==true
                &&FLAG_PAUSE==false
                &&FLAG_QUIT==false){
            return true;
        }else{
            return false;
        }
    }
    //是否暂停
    public static boolean isPauseed(){
        if(FLAG_BEGIN==true
                &&FLAG_PAUSE==true
                &&FLAG_QUIT==false
                ){
            return true;
        }else{
            return false;
        }
    }
    //是否停止
    public static boolean isStoped(){
        if(FLAG_QUIT==true){
            return true;
        }else{
            return false;
        }
    }

}
