package com.thatluck.numbermagic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv_query=null;
    EditText et_user=null;
    EditText et_kind=null;
    EditText et_result=null;
    EditText et_order=null;
    EditText et_datetime=null;
    Button btn_reset_database=null;
    Button btn_query=null;
    CheckBox cb_isall=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        tv_query=(TextView)findViewById(R.id.tv_query);
         et_user=(EditText)findViewById(R.id.et_user);
         et_kind=(EditText)findViewById(R.id.et_kind);
         et_result=(EditText)findViewById(R.id.et_result);
         et_order=(EditText)findViewById(R.id.et_order);
         et_datetime=(EditText)findViewById(R.id.et_datetime);
         btn_reset_database=(Button)findViewById(R.id.bt_reset_database);
         btn_query=(Button)findViewById(R.id.bt_query);
        cb_isall=(CheckBox)findViewById(R.id.cb_isall);

        cb_isall.setChecked(true);
        btn_query.setOnClickListener(this);
        btn_reset_database.setOnClickListener(this);
    }

    private void create() {

        MyDBHelper helper=new MyDBHelper(this,MyDBHelper.DATABASE_NAME,null,MyDBHelper.DATABASE_VERSION);
        SQLiteDatabase db=helper.getWritableDatabase();
        helper.onUpgrade(db, 0, 1);
        Toast.makeText(this, "数据库创建成功!", Toast.LENGTH_LONG).show();
    }
    private Cursor query(int kind ,String _result,String _order,boolean isAll){
        Log.i("provider", "content2--query");

        ContentResolver cr=getContentResolver();
        String[] result_columns=new String[]{MyDBHelper.KEY_ID,MyDBHelper.KEY_USER,MyDBHelper.KEY_LENGTH,MyDBHelper.KEY_USETIME,MyDBHelper.KEY_DATETIME,MyDBHelper.KEY_KIND,MyDBHelper.KEY_RESULT};
        String where=" 1=1 ";
        if(_result!=null&&!_result.isEmpty()){
            where+=" and "+MyDBHelper.KEY_RESULT+"='"+_result+"'";
        }
        if(kind!=-1){
            where+=" and "+MyDBHelper.KEY_KIND+"="+kind;
        }
        if(isAll){
            where=null;
        }
        String[] whereAgrs=null;
        String order=null;
        if(_order!=null&&!_order.isEmpty()){
            order=_order;
        }
        Cursor result=cr.query(MyContentProvider.CONTENT_URI,result_columns,where,whereAgrs,order);
       return result;
    }
    private  void showResult(Cursor result){
        if(result==null){
            this.tv_query.setText("");
            result.close();
            return;
        }
        int index_user=result.getColumnIndexOrThrow(MyDBHelper.KEY_USER);
        int index_length=result.getColumnIndexOrThrow(MyDBHelper.KEY_LENGTH);
        int index_usetime=result.getColumnIndexOrThrow(MyDBHelper.KEY_USETIME);
        int index_datetime=result.getColumnIndexOrThrow(MyDBHelper.KEY_DATETIME);
        int index_kind=result.getColumnIndexOrThrow(MyDBHelper.KEY_KIND);
        int index_result=result.getColumnIndexOrThrow(MyDBHelper.KEY_RESULT);


        int id=result.getColumnIndexOrThrow(MyDBHelper.KEY_ID);

        while(result.moveToNext()){
            String user=result.getString(index_user);
            int length=result.getInt(index_length);
            int usetime=result.getInt(index_usetime);
            String datetime=result.getString(index_datetime);
            int kind=result.getInt(index_kind);
            String myresult=result.getString(index_result);
            long kid=result.getLong(id);
            if(!user.isEmpty()&&!datetime.isEmpty()&&!myresult.isEmpty()){
                this.tv_query.append(kid+ " 字数:"+length+" 使用时长:"+usetime+"秒 "+datetime+"\n");
            }
        }

        result.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_reset_database:
                create();break;
            case R.id.bt_query:
                int kind= Integer.parseInt(et_kind.getText().toString());
                String _result=et_result.getText().toString();
                String _order=et_order.getText().toString();
                boolean isall=cb_isall.isChecked();
                Cursor cursor=query(kind,_result,_order,isall);
                showResult(cursor);
                break;
        }
    }
}
