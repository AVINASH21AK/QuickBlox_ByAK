package com.quickblox.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.quickblox.R;

import butterknife.ButterKnife;

public class ActBlank extends BaseActivity{

    String TAG = "ActBlank";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            ViewGroup.inflate(this, R.layout.act_blank, ll_SubMainContainer);
            ButterKnife.bind(this);

            initialize();
            clickEvent();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void initialize(){
        try{

            //tvTitle.setText(R.string.header_Home);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickEvent(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
