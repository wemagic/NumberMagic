package com.thatluck.numbermagic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PokerActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置全屏
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //显示自定义的SurfaceView视图
        //setContentView(new FrameSurfaceView(this));
        setContentView(R.layout.activity_poker);
        Button btn_poker_begin=(Button)findViewById(R.id.btn_poker_begin);
        Button btn_poker_stop=(Button)findViewById(R.id.btn_poker_stop);
        btn_poker_begin.setOnClickListener(this);
        btn_poker_stop.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_poker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(PokerActivity.this, MainPreference.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_poker_begin:
               PokerStateTool.setBegin();
                break;
            case R.id.btn_poker_stop:
                PokerStateTool.setPause();
                break;
            default:break;


        }
    }
}
