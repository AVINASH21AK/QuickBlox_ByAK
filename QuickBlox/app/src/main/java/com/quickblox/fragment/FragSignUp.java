package com.quickblox.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.quickblox.R;
import com.quickblox.activity.ActOption;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.utils.App;
import com.quickblox.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragSignUp extends Fragment {

    String TAG = "FragSignUp";
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.tvSumbit) TextView tvSumbit;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_signup, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try{

            registerSession();
            initialize();
            clickEvent();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void registerSession() {
        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {   // verileri veritabanı olan quickblox'akaydetmeyi sağlar.
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                App.showLog(TAG, "registerSession: onSuccess");
            }

            @Override
            public void onError(QBResponseException e) {
                App.showLog(TAG, "registerSession: onError"+e.getErrors());
                App.showLog(TAG, "registerSession: onError"+e.getMessage());
            }
        });
    }

    public void initialize(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void clickEvent(){
        try{


            tvSumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String strName = edtName.getText().toString().trim();
                    String strEmailID = edtEmail.getText().toString().trim();

                    if(checkValidation(strName, strEmailID)){

                        QBUser qbUser = new QBUser(strEmailID, Constants.Password);
                        qbUser.setFullName(strName);

                        QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                            @Override
                            public void onSuccess(QBUser qbUser, Bundle bundle) {
                                App.showSnackBar(tvSumbit, "Sign up succesufull");

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        edtEmail.setText("");
                                        edtName.setText("");

                                        ((ActOption)getActivity()).viewPager.setCurrentItem(0);
                                    }
                                }, 350);

                            }

                            @Override
                            public void onError(QBResponseException e) {
                                App.showSnackBar(tvSumbit, "Error : "+e.getMessage());
                            }
                        });

                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkValidation(String strName, String strEmailID){

        App.hideSoftKeyboardMy(getActivity(), tvSumbit);

        if(!App.isValidString(strName)){
            App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.Name);
            return false;
        }

        if(!App.isValidString(strEmailID)){
            App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.Email);
            return false;
        }

        else if(!App.isValidEmail(strEmailID)){
            App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.EmailInvalid);
            return false;
        }


        else {
            return true;
        }
    }



}
