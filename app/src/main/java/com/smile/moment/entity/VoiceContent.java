package com.smile.moment.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Smile Wei
 * @since 2016/4/20.
 */
public class VoiceContent implements Parcelable {
    /**
     * commentid :
     * ref : <!--VIDEO#0-->
     * topicid :
     * cover : http://img5.cache.netease.com/3g/2015/5/27/2015052717373571fa9.jpg
     * url_mp4 : http://audio.m.126.net/201603/31/4099c4d438c5bdd8e31c17f462764756.mp3
     * alt : 轻松一刻语音版(3月31日)
     * broadcast : in
     * commentboard :
     * videosource : 其他
     * appurl :
     * url_m3u8 : http://audio.m.126.net/201603/31/4099c4d438c5bdd8e31c17f462764756.mp3
     * vid :
     * size :
     */

    private String commentid;
    private String ref;
    private String topicid;
    private String cover;
    private String url_mp4;
    private String alt;
    private String broadcast;
    private String commentboard;
    private String videosource;
    private String appurl;
    private String url_m3u8;
    private String vid;
    private String size;

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTopicid() {
        return topicid;
    }

    public void setTopicid(String topicid) {
        this.topicid = topicid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl_mp4() {
        return url_mp4;
    }

    public void setUrl_mp4(String url_mp4) {
        this.url_mp4 = url_mp4;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getCommentboard() {
        return commentboard;
    }

    public void setCommentboard(String commentboard) {
        this.commentboard = commentboard;
    }

    public String getVideosource() {
        return videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public String getUrl_m3u8() {
        return url_m3u8;
    }

    public void setUrl_m3u8(String url_m3u8) {
        this.url_m3u8 = url_m3u8;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commentid);
        dest.writeString(this.ref);
        dest.writeString(this.topicid);
        dest.writeString(this.cover);
        dest.writeString(this.url_mp4);
        dest.writeString(this.alt);
        dest.writeString(this.broadcast);
        dest.writeString(this.commentboard);
        dest.writeString(this.videosource);
        dest.writeString(this.appurl);
        dest.writeString(this.url_m3u8);
        dest.writeString(this.vid);
        dest.writeString(this.size);
    }

    public VoiceContent() {
    }

    protected VoiceContent(Parcel in) {
        this.commentid = in.readString();
        this.ref = in.readString();
        this.topicid = in.readString();
        this.cover = in.readString();
        this.url_mp4 = in.readString();
        this.alt = in.readString();
        this.broadcast = in.readString();
        this.commentboard = in.readString();
        this.videosource = in.readString();
        this.appurl = in.readString();
        this.url_m3u8 = in.readString();
        this.vid = in.readString();
        this.size = in.readString();
    }

    public static final Parcelable.Creator<VoiceContent> CREATOR = new Parcelable.Creator<VoiceContent>() {
        @Override
        public VoiceContent createFromParcel(Parcel source) {
            return new VoiceContent(source);
        }

        @Override
        public VoiceContent[] newArray(int size) {
            return new VoiceContent[size];
        }
    };
}
