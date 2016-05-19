package com.thatluck.numbermagic;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static int KIND_NUMBER=1;
    public static int KIND_POKER=2;
    public static final String RESULT_SUCCESS="success";
    public static final String RESULT_FAIL="fail";
    int timerLength=0;
    TextView tv_number=null;
    public static TextView tv_timer=null;
    Button btn_reset=null;
    Button btn_hide=null;
    Button btn_show=null;
    Button btn_compare=null;
    EditText et_repeat=null;

    String old="";
    boolean isCanceled=false;
    Timer timer=new Timer();
    MyTask task=new MyTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_number=(TextView)findViewById(R.id.tv_number);
        tv_timer=(TextView)findViewById(R.id.tv_timer);
        btn_reset=(Button)findViewById(R.id.btn_reset);
        btn_show=(Button)findViewById(R.id.btn_show);
        btn_hide=(Button)findViewById(R.id.btn_hide);
        btn_compare=(Button)findViewById(R.id.btn_compare);
        et_repeat=(EditText)findViewById(R.id.et_repeat);

        btn_reset.setOnClickListener(this);
        btn_compare.setOnClickListener(this);
        btn_hide.setOnClickListener(this);
        btn_show.setOnClickListener(this);

        tv_number.setVisibility(View.INVISIBLE);
        
        reset();
        timerLength=MainPreference.TIMER_LENGTH;

    }
    private  void cancel(){
        if(task!=null){
            task.cancel();
            task=null;
        }
        if(timer!=null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
    private  void reset(){
        cancel();
        et_repeat.setText("");
        old=ContentTool.generateNumbers(MainPreference.NUMBER_COUNT,MainPreference.NUMBER_BEGIN,MainPreference.NUMBER_END);
        tv_number.setVisibility(View.INVISIBLE);
        tv_number.setText(old);
        et_repeat.setEnabled(false);

        Toast.makeText(this, "准备完毕", Toast.LENGTH_LONG).show();

    }
    private boolean compare() {
        boolean flag=false;
        String repeat=et_repeat.getText().toString();
        if(!old.isEmpty()&&!repeat.isEmpty()&&old.equals(repeat)){
            add(MainActivity.KIND_NUMBER,MainActivity.RESULT_SUCCESS);
            flag=true;
        }else{
           // add(MainActivity.KIND_NUMBER,MainActivity.RESULT_FAIL);
        }
        if(et_repeat.isEnabled()){
            et_repeat.setEnabled(false);
        }
        return flag;
    }


    private void begin(){
        tv_number.setVisibility(View.VISIBLE);
        timerLength=MainPreference.TIMER_LENGTH;
        if(et_repeat.isEnabled()) {
            et_repeat.setEnabled(false);
        }
        showTimer();
    }
    private void stop() {
        tv_number.setVisibility(View.INVISIBLE);
        if(timer!=null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }

        if(!et_repeat.isEnabled()){
            et_repeat.setEnabled(true);
        }
    }
    private void showTimer() {
        timer=new Timer();
        if(task!=null){
            task.cancel();
            task = null;
        }
        task=new MyTask();
        timer.schedule(task, 1000, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancel();
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    tv_timer.setText("" + timerLength);
                    timerLength--;
                    if(timerLength<0){
                        stop();
                    }
                }
            });
        }
    }
    private void openPreference() {

        Intent intent = new Intent(MainActivity.this, MainPreference.class);
        startActivity(intent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reset:
                reset();break;
            case R.id.btn_hide:
                stop();break;
            case R.id.btn_show:
                begin();break;

            case R.id.btn_compare:
                boolean flag=compare();
                if(flag){
                    Toast.makeText(this, "恭喜答对了", Toast.LENGTH_LONG).show();
                    et_repeat.setText("");
                }else{
                    Toast.makeText(this, "有错误，请核对", Toast.LENGTH_LONG).show();
                }



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                openPreference();
                return true;
            case R.id.action_data:
                Intent intent=new Intent(MainActivity.this,ContentActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void add(int kind,String result) {
        Log.i("provider", "content2--add");
        int usetime=MainPreference.TIMER_LENGTH- Integer.parseInt(MainActivity.tv_timer.getText().toString());
        int length=MainPreference.NUMBER_COUNT*2;
        ContentValues cvs=new ContentValues();
        cvs.put(MyDBHelper.KEY_USER,MyDBHelper.USER_DEFAULT);
        cvs.put(MyDBHelper.KEY_DATETIME,ContentTool.getNowDateTime());
        cvs.put(MyDBHelper.KEY_USETIME,usetime);
        cvs.put(MyDBHelper.KEY_LENGTH,length);
        cvs.put(MyDBHelper.KEY_KIND,kind);
        cvs.put(MyDBHelper.KEY_RESULT,result);
        ContentResolver cr = getContentResolver();
        Uri insertedId=cr.insert(MyContentProvider.CONTENT_URI, cvs);
        String id= insertedId.getPathSegments().get(1);
        //Toast.makeText(this, id, Toast.LENGTH_LONG).show();
    }

}
