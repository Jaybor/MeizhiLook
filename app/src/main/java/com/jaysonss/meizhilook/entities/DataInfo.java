package com.jaysonss.meizhilook.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jaybor on 2016/11/10.
 */
public class DataInfo implements Parcelable {
    public static final Creator<DataInfo> CREATOR = new Creator<DataInfo>() {
        @Override
        public DataInfo createFromParcel(Parcel source) {
            return new DataInfo(source);
        }

        @Override
        public DataInfo[] newArray(int size) {
            return new DataInfo[size];
        }
    };

    private String _id;
    private Date createdAt;
    private String desc;
    private Date publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;

    @SuppressWarnings("unused")
    public DataInfo() {
    }

    private DataInfo(Parcel in) {
        this._id = in.readString();
        this.createdAt = (Date) in.readSerializable();
        this.desc = in.readString();
        this.publishedAt = (Date) in.readSerializable();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
    }

    public String get_id() {
        return _id;
    }

    @SuppressWarnings("unused")
    public void set_id(String _id) {
        this._id = _id;
    }

    @SuppressWarnings("unused")
    public Date getCreatedAt() {
        return createdAt;
    }

    @SuppressWarnings("unused")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    @SuppressWarnings("unused")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    @SuppressWarnings("unused")
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    @SuppressWarnings("unused")
    public void setUrl(String url) {
        this.url = url;
    }

    @SuppressWarnings("unused")
    public boolean isUsed() {
        return used;
    }

    @SuppressWarnings("unused")
    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    @SuppressWarnings("unused")
    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeSerializable(this.createdAt);
        dest.writeString(this.desc);
        dest.writeSerializable(this.publishedAt);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.who);
    }
}
