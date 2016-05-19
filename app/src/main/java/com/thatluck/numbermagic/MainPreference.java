package com.thatluck.numbermagic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Lenovo on 2015/p11/15.
 */
public class MainPreference extends PreferenceActivity {
    public static final String PS_TIMER_LENGTH="PS_TIMER_LENGTH";
    public static final String PS_NUMBER_COUNT="PS_NUMBER_COUNT";
    public static final String PS_NUMBER_BEGIN="PS_NUMBER_BEGIN";
    public static final String PS_NUMBER_END="PS_NUMBER_END";
    public static final String PS_POKER_TIME_INTERVAL="PS_POKER_TIME_INTERVAL";
    public static final String PS_DICT="PS_DICT";

    public static  int NUMBER_COUNT=20;
    public static  int TIMER_LENGTH=30;
    public static  int NUMBER_BEGIN=1;
    public static  int NUMBER_END=100;

    public static int POKER_TIME_INTERVAL=2000;
    public static String DICT="";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.ps_3a);

        final EditTextPreference etp_timer_length = (EditTextPreference)findPreference(MainPreference.PS_TIMER_LENGTH);
        SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(this);

        etp_timer_length.setSummary(shp.getString(MainPreference.PS_TIMER_LENGTH, MainPreference.TIMER_LENGTH + ""));
        etp_timer_length.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int length = Integer.parseInt(newValue.toString());
                if (length > 0 && length < 36000) {
                    MainPreference.TIMER_LENGTH = length;
                    etp_timer_length.setSummary(newValue.toString());
                    etp_timer_length.setDefaultValue(newValue);
                } else {
                    Toast.makeText(MainPreference.this, "数值超出了规定的范围", Toast.LENGTH_LONG).show();
                    return false;
                }

                return true;
            }
        });

        final EditTextPreference etp_number_count = (EditTextPreference)findPreference(MainPreference.PS_NUMBER_COUNT);
        etp_number_count.setSummary(shp.getString(MainPreference.PS_NUMBER_COUNT, MainPreference.NUMBER_COUNT + ""));
        etp_number_count.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int count = Integer.parseInt(newValue.toString());
                if (count > 0 && count <= 100) {
                    MainPreference.NUMBER_COUNT = count;
                    etp_number_count.setSummary(newValue.toString());
                    etp_number_count.setDefaultValue(newValue);
                } else {
                    Toast.makeText(MainPreference.this, "数值超出了规定的范围，只允许设置1~100的整数", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

        });

        final EditTextPreference etp_poker_timer_interval = (EditTextPreference)findPreference(MainPreference.PS_POKER_TIME_INTERVAL);
        etp_poker_timer_interval.setSummary(shp.getString(MainPreference.PS_POKER_TIME_INTERVAL, MainPreference.POKER_TIME_INTERVAL+""));
        etp_poker_timer_interval.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int count = Integer.parseInt(newValue.toString());
                if (count > 0 && count <= 100*1000) {
                    MainPreference.POKER_TIME_INTERVAL = count;
                    etp_poker_timer_interval.setSummary(newValue.toString());
                    etp_poker_timer_interval.setDefaultValue(newValue);
                } else {
                    Toast.makeText(MainPreference.this, "数值超出了规定的范围，只允许设置1~100*1000的整数", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

        });



        final EditTextPreference etp_number_begin = (EditTextPreference)findPreference(MainPreference.PS_NUMBER_BEGIN);
        etp_number_begin.setSummary(shp.getString(MainPreference.PS_NUMBER_BEGIN, MainPreference.NUMBER_BEGIN+""));
        etp_number_begin.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int count = Integer.parseInt(newValue.toString());
                int end=MainPreference.NUMBER_END;
                if(count>end){
                    return false;
                }
                if (count > 0 && count <= 100) {

                    MainPreference.NUMBER_BEGIN = count;
                    etp_number_begin.setSummary(newValue.toString());
                    etp_number_begin.setDefaultValue(newValue);
                } else {
                    Toast.makeText(MainPreference.this, "数值超出了规定的范围，只允许设置1~100的整数", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

        });

        final EditTextPreference etp_number_end = (EditTextPreference)findPreference(MainPreference.PS_NUMBER_END);
        etp_number_end.setSummary(shp.getString(MainPreference.PS_NUMBER_END, MainPreference.NUMBER_END+""));
        etp_number_end.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int count = Integer.parseInt(newValue.toString());
                int begin=MainPreference.NUMBER_BEGIN;
                if(count<begin){
                    return false;
                }
                if (count > 0 && count <= 100 ){
                    MainPreference.NUMBER_END = count;
                    etp_number_end.setSummary(newValue.toString());
                    etp_number_end.setDefaultValue(newValue);
                } else {
                    Toast.makeText(MainPreference.this, "数值超出了规定的范围，只允许设置1~100的整数", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

        });
    }
}
