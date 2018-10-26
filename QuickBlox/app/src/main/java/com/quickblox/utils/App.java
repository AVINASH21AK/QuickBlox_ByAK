package com.quickblox.utils;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.quickblox.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class App extends Application {

    public static String TAG = "APP";
    static Context context;
    private static App mInstance;
    public static String strFolderName = "QuickBlox";
    public static String strSDCardPath = Environment.getExternalStorageDirectory().toString();
    public static String strFolderFullPath = App.strSDCardPath+ File.separator + App.strFolderName;
    static Typeface tf_Bold;

    public static final String CHANNEL_1_ID = "channel1";



    @Override
    public void onCreate() {
        super.onCreate();


        MultiDex.install(this);
        context = getApplicationContext();
        mInstance = this;

        setRegularFontsAllTxt();

        //-- SharedPreference
        Hawk.init(context).build();

        //-- Permission issue in new phones to strickly get permission
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        createNotificationChannel();
        createFolder();
    }


    public void setRegularFontsAllTxt(){
        try{

            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/GOTHAMHTF-REGULAR.OTF")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Typeface getFont_Bold() {
        tf_Bold = Typeface.createFromAsset(context.getAssets(), "fonts/GOTHAMHTF-BOLD.OTF");
        return tf_Bold;
    }

    //-- Facebook hash key generator
    public static void GenerateKeyHash() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES); //GypUQe9I2FJr2sVzdm1ExpuWc4U= android pc -2 key
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                App.showLog(TAG, "KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-- Check Internet
    public static boolean isInternetAvail(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }


    //-- Create Folder
    public static void createFolder() {
        FileOutputStream out = null;
        try {
            String directoryPath = App.strFolderFullPath;
            File appDir = new File(directoryPath);
            if (!appDir.exists() && !appDir.isDirectory()) {
                if (appDir.mkdirs()) {
                    App.showLog(TAG, "App Directory created");
                } else {
                    App.showLog(TAG, "Unable To Create App Directory!");
                }
            } else {
                App.showLog(TAG, "App Directory Already Exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-- Save Bitmap
    public static void saveBitmapSdcard(String filename, Bitmap bitmap) {
        FileOutputStream out = null;
        try {
            String directoryPath = App.strFolderFullPath;

            File appDir = new File(directoryPath);

            if (!appDir.exists() && !appDir.isDirectory()) {
                if (appDir.mkdirs()) {
                    App.showLog("===CreateDir===", "App dir created");
                } else {
                    App.showLog("===CreateDir===", "Unable to create app dir!");
                }
            }


            out = new FileOutputStream(directoryPath + File.separator + filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //-- Bitmap Resize
    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    //-- Hide Keyboard
    public static void hideSoftKeyboardMy(Activity activity, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            //inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //-- Log
    public static void showLog(String ActivityName, String strMessage) {
        Log.d("From: ", ActivityName + " -- " + strMessage);
    }


    //-- Remove Html tags
    public static String stripHtmlRegex(String source) {
        // Replace all tag characters with an empty string.
        source = source.replaceAll("<(.*?)\\>","");//Removes all items in brackets
        source = source.replaceAll("<(.*?)\\\n","");//Must be undeneath
        source = source.replaceFirst("(.*?)\\>", "");//Removes any connected item to the last bracket
        source = source.replaceAll("&nbsp;","");
        source = source.replaceAll("&amp;","");
        return source.replaceAll("<.*?>", "");
    }

    public static String stripHtmlRegexOLD(String source) {
        return source.replaceAll("<.*?>", "");
    }

    //-- Check Internet
    public static boolean isInternetConnectionAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    //-- IMEI Number
    public static String getIMEInumber(Context context) {
        String strIMEI="";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        strIMEI = telephonyManager.getDeviceId();

        if (strIMEI != null && strIMEI.length() > 0) {
            return strIMEI;
        } else {
            Random ran = new Random();
            int x = ran.nextInt(5) + 1050;

            return x + "1050";
        }

    }

    //-- Snackbar
    public static void showSnackBar(View view, String strMessage) {
        // Toast.makeText(context, ""+strMessage, Toast.LENGTH_SHORT).show();

        try {
            Snackbar snackbar = Snackbar.make(view, strMessage, Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.BLACK);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(3);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //-- Start and End Activity
    public static void myStartActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        //activity.overridePendingTransition(0, 0);
        //activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void myStartActivityNoHistory(Activity activity, Intent intent) {
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
        //activity.overridePendingTransition(0, 0);
        //activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public static void myStartActivityRefersh(Activity activity, Intent intent) {
        activity.finish();
        activity.startActivity(intent);
        //activity.overridePendingTransition(0, 0);
    }

    public static void myStartActivityClearTop(Activity activity, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

        activity.finish();
        activity.startActivity(intent);
        //activity.overridePendingTransition(0, 0);
    }

    public static void myFinishActivityRefresh(Activity activity, Intent intent) {
        activity.finish();
        activity.startActivity(intent);
        //activity.overridePendingTransition(0, 0);
        //activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void myFinishActivity(Activity activity) {
        activity.finish();
        //activity.overridePendingTransition(0, 0);
    }



    //-- Valid email
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}");
        // Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    //-- Valid string
    public static boolean isValidString(String str){

        if(str != null && str.trim().length()>0)
            return true;
        else
            return false;

    }


    //-- Px to Dp Converter
    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    //-- Dp to Px Converter
    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    //float BANNER_SHOW_PERCENTAGE = 0.40f; // 40%
    public static String dynamicSetSize(Activity activity) {
        String HeightWidth = "";
        try
        {

            int SCREEN_HEIGHT = 0, SCREEN_WIDTH = 0;

            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y;
            SCREEN_WIDTH = size.x;

            App.showLog(TAG, "SCR Height: "+ SCREEN_HEIGHT );
            App.showLog(TAG, "SCR Width: "+ SCREEN_WIDTH );

            HeightWidth = SCREEN_HEIGHT +":"+SCREEN_WIDTH;

            return HeightWidth;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return HeightWidth;
    }


    public static String getDateTimeFormate(String strDate, String inputDateFormate, String outputDateFormate) {
        String strFinaldate = "";
        if (strDate != null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormate);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormate);
                String inputDateStr = strDate;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                strFinaldate = outputFormat.format(date);
                //strFinaldate = outputFormat.format(date).toLowerCase();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return strFinaldate;
    }


    public static void showLogParameters(String key, String parameter) {
        Log.d(key, parameter);
    }

    public static void showLogResponce(String strFrom, String strMessage) {
        Log.d("From: " + strFrom, " strResponse: " + strMessage);
    }


    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel3.setDescription("This is channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel3);

        }
    }
}
