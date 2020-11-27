package com.example.androidapplication;

public class MobileHelperClass {
    String MobileNo;

    public MobileHelperClass(String mobileNo) {
        MobileNo = mobileNo;
    }
    public MobileHelperClass() {
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }
}
