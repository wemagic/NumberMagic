package com.thatluck.numbermagic;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    public static  final Uri CONTENT_URI= Uri.parse("content://com.king.myprovider/elements");
    private static  final int ALLROWS=1;
    private static final int SINGLE_ROW=2;
    private static final UriMatcher matcher;
    private static final String KEY_ID ="_id" ;

    static {
        matcher=new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI("com.king.myprovider","elements",ALLROWS);
        matcher.addURI("com.king.myprovider","elements/#",SINGLE_ROW);

    }
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
       SQLiteDatabase db=dbHelper.getWritableDatabase();
        switch (matcher.match(uri)){
            case SINGLE_ROW:
                String rowId=uri.getPathSegments().get(1);
                selection=KEY_ID+"="+rowId+(!TextUtils.isEmpty(selection)?" AND ("+selection+ ')':"");
                break;
            default:break;
        }
        if(selection==null){
            selection="1";
        }
        int deleteCount=db.delete(MyDBHelper.DATABASE_TABLE,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return deleteCount;
    }

    @Override
    public String getType(Uri uri) {
       switch (matcher.match(uri)){
           case ALLROWS:
               return "vnd.android.cursor.dir/vnd.king.elemental";
           case SINGLE_ROW:
               return "vnd.android.cursor.item/vnd.king.elemental";
           default:throw new IllegalArgumentException("Unsupported URI:"+uri);

       }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("provider", "insert--");
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String nullColumnHack=null;
        long id=db.insert(MyDBHelper.DATABASE_TABLE,nullColumnHack,values);
        if(id>-1){
            Uri insertedId= ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(insertedId,null);
            return insertedId;
        }else {
            return null;
        }
    }
   private MyDBHelper dbHelper;
    @Override
    public boolean onCreate() {
        Log.i("provider", "onCreate--");
        dbHelper=new MyDBHelper(getContext(),MyDBHelper.DATABASE_NAME,null,MyDBHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db;
        SQLiteQueryBuilder builder=new SQLiteQueryBuilder();
        try{
            db=dbHelper.getWritableDatabase();
        }catch (SQLiteException ex){
            db=dbHelper.getReadableDatabase();
        }
        String groupBy=null;
        String having=null;
        switch (matcher.match(uri)){
            case SINGLE_ROW:
                String rowID=uri.getPathSegments().get(1);
                builder.appendWhere(KEY_ID+"="+rowID);
                default:break;
        }
        builder.setTables(MyDBHelper.DATABASE_TABLE);
        Cursor cursor=builder.query(db,projection,selection,selectionArgs,groupBy,having,sortOrder);
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        switch (matcher.match(uri)){
            case SINGLE_ROW:
                String rowId=uri.getPathSegments().get(1);
                selection=KEY_ID+"="+rowId+(!TextUtils.isEmpty(selection)?" AND ("+selection+')' :"");
                break;
            default:break;
        }
        int updateCount=db.update(MyDBHelper.DATABASE_TABLE,values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return updateCount;
    }
}
