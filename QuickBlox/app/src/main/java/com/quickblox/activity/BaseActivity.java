package com.quickblox.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.quickblox.R;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.utils.App;
import com.quickblox.utils.Constants;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class BaseActivity extends AppCompatActivity {

    protected String TAG = "BaseActivity";

    protected RelativeLayout rl_baseToolbar;
    protected LinearLayout ll_SubMainContainer;
    protected TextView tvTitle;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base);

        baseClickInitialize();

    }

    public void baseClickInitialize(){
        try{

            rl_baseToolbar = (RelativeLayout) findViewById(R.id.rl_baseToolbar);
            ll_SubMainContainer = (LinearLayout) findViewById(R.id.ll_SubMainContainer);
            tvTitle = (TextView) findViewById(R.id.tvTitle);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void QuickBlox_InitializeFramework() {

        QBSettings.getInstance().init(BaseActivity.this, Constants.ApplicationID, Constants.AuthorizationKey, Constants.AuthorizationSecret);
        QBSettings.getInstance().setAccountKey(Constants.AccountKey);

    }

    public void QuickBlox_Login(final String strEmailID){
        try{

            QBUser qbUser = new QBUser(strEmailID, Constants.Password);

            QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    App.showSnackBar(tvTitle, "Login succesufully");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Hawk.put(Constants.SHAREDPREFERENCE.USER.Login, "1");
                            Hawk.put(Constants.SHAREDPREFERENCE.USER.Emailid, strEmailID);

                            Intent intent = new Intent(BaseActivity.this, ActChatList.class);
                            intent.putExtra(Constants.INTENT.UserEmailID, strEmailID);
                            App.myStartActivity(BaseActivity.this, intent);

                        }
                    }, 350);
                }

                @Override
                public void onError(QBResponseException e) {
                    App.showSnackBar(tvTitle, "Error: "+e.getErrors());
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
