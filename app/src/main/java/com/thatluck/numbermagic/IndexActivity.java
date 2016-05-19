package com.thatluck.numbermagic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Button btn_number=(Button)findViewById(R.id.btn_number);
        Button btn_poker=(Button)findViewById(R.id.btn_poker);
        Button btn_dict=(Button)findViewById(R.id.btn_dict);
        btn_number.setOnClickListener(this);
        btn_poker.setOnClickListener(this);
        btn_dict.setOnClickListener(this);
        updateFromPreference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_number:
                Intent intent=new Intent(IndexActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_poker:
                Intent intent1=new Intent(IndexActivity.this,PokerActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_dict:
                Intent intent2=new Intent(IndexActivity.this,DictActivity.class);
                startActivity(intent2);
                break;
            default:break;

        }
    }
    private void updateFromPreference(){
        Context context=getApplicationContext();
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        MainPreference.TIMER_LENGTH= Integer.parseInt(sharedPreferences.getString(MainPreference.PS_TIMER_LENGTH, MainPreference.TIMER_LENGTH + ""));
        MainPreference.NUMBER_COUNT= Integer.parseInt(sharedPreferences.getString(MainPreference.PS_NUMBER_COUNT, MainPreference.NUMBER_COUNT + ""));
        MainPreference.POKER_TIME_INTERVAL= Integer.parseInt(sharedPreferences.getString(MainPreference.PS_POKER_TIME_INTERVAL, MainPreference.POKER_TIME_INTERVAL + ""));
        MainPreference.DICT=sharedPreferences.getString(MainPreference.PS_DICT, getString(R.string.dict));

        MainPreference.NUMBER_BEGIN= Integer.parseInt(sharedPreferences.getString(MainPreference.PS_NUMBER_BEGIN, MainPreference.NUMBER_BEGIN + ""));
        MainPreference.NUMBER_END= Integer.parseInt(sharedPreferences.getString(MainPreference.PS_NUMBER_END, MainPreference.NUMBER_END + ""));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateFromPreference();
    }
}
