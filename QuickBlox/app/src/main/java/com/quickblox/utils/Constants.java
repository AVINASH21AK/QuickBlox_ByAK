package com.quickblox.utils;

public class Constants {

    public static String Password = "988876665";
    public static String ApplicationID = "74329";
    public static String AuthorizationKey = "br2qVUceAqfea4t";
    public static String AuthorizationSecret = "EzCFeS93Mqtmady";
    public static String AccountKey = "8aLQsy_tniCGXRS_EjPc";


    public static class INTENT{
        public static String From = "from";
        public static String UserEmailID = "userEmailID";
    }

    public static class SHAREDPREFERENCE{

        public static class API{
            public static String DeviceId = "device_id";   //IMEI
            public static String AndroidToken = "androidtoken";  //push notification device id
            public static String AccessToken = "accesstoken";  //which you get frm login, sociallogin register api
        }

        public static class USER{
            public static String Login = "isLogin";   //0-notLogin & 1-Login
            public static String ID = "ID";
            public static String Userid = "userid";
            public static String Emailid = "Emailid";
            public static String Fullname = "fullname";
            public static String Birthdate = "birthdate";
            public static String Gender = "gender";
            public static String FBID = "fbid";
            public static String Googleid = "googleid";
            public static String Photo = "photo";
        }
    }

    public static class DATETIMEFORMATE{
        //12hr- dd/MM/yyyy hh:mm:ss a   --> Small
        //24hr- dd/MM/yyyy HH:mm:ss     --> Capital

        public static String DDMMM = "dd MMM";
        public static String HHMMA = "hh:mm a";
        public static String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
        public static String YYYYMMDD = "yyyy-MM-dd";
        public static String DDMMYYY = "dd-MM-yyyy";
        public static String DDMMMMYYY = "dd MMMM yyyy";
        public static String YYYY = "yyyy";
        public static String MM = "MM";
        public static String DD = "dd";
    }




    public static class MESSAGES{

        public static class ERROR{
            public static String Unknown = "Oops, something went wrong. Please try again later";
            public static String NoInternetConnection = "You do not seem to have a strong Internet connection. Kindly move to a WiFi or stronger cellular signal";
            public static String NoRecordsFound_Quotes = "No records found";
            public static String NoRecordsFound_Events = "No records found";
            public static String NoRecordsFound_Home = "No records found";
            public static String NoRecordsFound_QnA = "No records found";

            public static String Error_401 = "You are logged out from this device because you are logged in from another device";
        }

        public static class VALIDATIONS{
            public static String Name = "Please enter name";
            public static String Email = "Please enter email id";
            public static String EmailInvalid = "Please enter valid email id";
            public static String Photo = "Please select photo";
            public static String Password = "Please enter password";
            public static String PasswordCurrent = "Please enter your current password";
            public static String PasswordNew = "Please enter your new password";
            public static String PasswordRetype = "Please retype new password";

        }


    }


}
