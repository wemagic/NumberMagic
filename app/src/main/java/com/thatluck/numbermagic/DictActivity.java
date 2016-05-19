package com.thatluck.numbermagic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DictActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_dict=null;
    String dict=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);
        et_dict=(EditText)findViewById(R.id.et_dict);
        Button btn_dict_modify=(Button)findViewById(R.id.btn_dict_modify);
        Button btn_dict_save=(Button)findViewById(R.id.btn_dict_save);
        Button btn_dict_reset=(Button)findViewById(R.id.btn_dict_reset);
        btn_dict_modify.setOnClickListener(this);
        btn_dict_save.setOnClickListener(this);
        btn_dict_reset.setOnClickListener(this);

        et_dict.setEnabled(false);
        et_dict.setText(MainPreference.DICT);

        dict=getString(R.string.dict);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dict_modify:
                if (!et_dict.isEnabled()) {
                    et_dict.setEnabled(true);
                }
                break;
            case R.id.btn_dict_save:
                et_dict.setEnabled(false);
                String word = et_dict.getText().toString();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(MainPreference.PS_DICT, word);
                editor.apply();
                Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_dict_reset:
                et_dict.setText(dict);
                SharedPreferences sp2 = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor2 = sp2.edit();
                editor2.putString(MainPreference.PS_DICT, dict);
                editor2.apply();
                Toast.makeText(this, "还原默认值", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
