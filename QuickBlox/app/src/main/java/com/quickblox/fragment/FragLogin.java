package com.quickblox.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.quickblox.R;
import com.quickblox.activity.ActChatList;
import com.quickblox.activity.ActOption;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.utils.App;
import com.quickblox.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragLogin extends Fragment {

    String TAG = "FragLogin";
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.tvSumbit) TextView tvSumbit;



    Bitmap mPhotoBitmap;
    String mSocialImgName = "Profile.jpg", mProfileImg = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try{


            initialize();
            clickEvent();

        }catch (Exception e){
            e.printStackTrace();
        }
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

                    final String strEmailID = edtEmail.getText().toString().trim();

                    if(checkValidation(strEmailID)){

                        QBUser qbUser = new QBUser(strEmailID, Constants.Password);

                        QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() { // Kullanıcınin belirlenen şifre ile giriş yapılmasını sağlayan kod bloğu
                            @Override
                            public void onSuccess(QBUser qbUser, Bundle bundle) {

                                App.showSnackBar(tvSumbit, "Login succesufully");
                                ((ActOption)getActivity()).QuickBlox_Login(strEmailID);
                            }

                            @Override
                            public void onError(QBResponseException e) {
                                App.showSnackBar(tvSumbit, "Error: "+e.getErrors());
                            }
                        });
                    }

                }
            });





        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public boolean checkValidation(String strEmailID){

        App.hideSoftKeyboardMy(getActivity(), tvSumbit);

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
