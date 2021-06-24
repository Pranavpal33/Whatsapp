package com.example.whatsappclone;

public class Upload {
    String mName;
    String mImageUrl;

    public Upload(){

    }
    public Upload(String name,String imageurl)
    {
        if(name.trim().equals(""))
        {
            name="No Name";
        }


        mName=name;
        mImageUrl=imageurl;
    }

    public String getmName() {
        return mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
