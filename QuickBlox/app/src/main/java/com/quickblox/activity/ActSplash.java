package com.quickblox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.quickblox.R;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.utils.App;
import com.quickblox.utils.Constants;

import butterknife.ButterKnife;

public class ActSplash extends BaseActivity{

    String TAG = "ActSplash";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            ViewGroup.inflate(this, R.layout.act_splash, ll_SubMainContainer);
            ButterKnife.bind(this);

            initialize();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void initialize(){
        try{

            rl_baseToolbar.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(Hawk.contains(Constants.SHAREDPREFERENCE.USER.Login) &&
                            Hawk.get(Constants.SHAREDPREFERENCE.USER.Login).toString().equalsIgnoreCase("1") &&
                            Hawk.contains(Constants.SHAREDPREFERENCE.USER.Emailid) )
                    {

                        QuickBlox_InitializeFramework();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                QuickBlox_Login(Hawk.get(Constants.SHAREDPREFERENCE.USER.Emailid).toString());
                            }
                        }, 500);

                    }
                    else
                    {
                        Intent i1 = new Intent(ActSplash.this, ActOption.class);
                        App.myStartActivityClearTop(ActSplash.this, i1);
                    }

                }
            }, 3000);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
