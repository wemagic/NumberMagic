package com.thatluck.numbermagic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lenovo on 2015/p11/12.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    public static final String USER_DEFAULT="king";
    public static final String KEY_ID="_id";
    public static final String KEY_USER="user";
    public static final String KEY_LENGTH="length";
    public static final String KEY_USETIME="usetime";
    public static final String KEY_DATETIME="daytime";
    public static final String KEY_KIND="kind";
    public static final String KEY_RESULT="result";
    public static final String DATABASE_NAME="mydatabase.db";
    public static final String DATABASE_TABLE="aaa";
    public static final  int DATABASE_VERSION=1;
    public static final String DATABASE_CREATE="create table "+DATABASE_TABLE+" ("+KEY_ID+
            " integer primary key autoincrement,"+KEY_USER+" text not null,"+KEY_LENGTH+" int ,"+KEY_USETIME+" int not null,"+KEY_DATETIME+" datetime not null,"+KEY_KIND+" int not null,"+KEY_RESULT+" text not null);" ;
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("provider", "helper--onCreate");
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("provider", "helper--onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);

    }
}
