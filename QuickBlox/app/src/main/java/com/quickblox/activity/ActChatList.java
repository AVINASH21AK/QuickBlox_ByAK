package com.quickblox.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.quickblox.R;
import com.quickblox.utils.Constants;

import butterknife.ButterKnife;

public class ActChatList extends BaseActivity{

    String TAG = "ActChatList";

    String userEmailID = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            ViewGroup.inflate(this, R.layout.act_chatlist, ll_SubMainContainer);
            ButterKnife.bind(this);

            getActivityIntent();
            initialize();
            clickEvent();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void getActivityIntent(){
        try{

            if(getIntent() != null ){
                userEmailID = getIntent().getStringExtra(Constants.INTENT.UserEmailID);
            }

        }catch (Exception e){
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
