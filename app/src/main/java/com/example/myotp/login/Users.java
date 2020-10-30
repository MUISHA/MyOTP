package com.example.myotp.login;

public class Users {
     String mEmail, mPassword,mFullName, mPhone, mUserIUd;

    public Users(String mEmail, String mPassword, String mFullName, String mPhone, String mUserIUd) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mFullName = mFullName;
        this.mPhone = mPhone;
        this.mUserIUd = mUserIUd;
    }
    public Users() {

    }
    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmFullName() {
        return mFullName;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmUserIUd() {
        return mUserIUd;
    }

    public void setmUserIUd(String mUserIUd) {
        this.mUserIUd = mUserIUd;
    }


}
