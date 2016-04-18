package com.smile.moment.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Smile Wei
 * @since 2016/4/11.
 */
public class Books implements Parcelable {


    /**
     * docid : BKFKUG6O00964LQ9
     * title : 每日轻松一刻:我是罗斯柴尔德
     * replyCount : 8385
     * lmodify : 2016-04-12 19:04:29
     * ltitle : 每日轻松一刻:我是罗斯柴尔德
     * imgsrc : http://img4.cache.netease.com/3g/2016/4/12/2016041218375294b1e.jpg
     * digest : 我在书店看到《货币战争》，放在了虚拟神话类的书架上。
     * ptime : 2016-04-12 18:34:06
     * url : http://3g.163.com/ntes/16/0412/18/BKFKUG6O00964LQ9.html
     */

    public final static int TYPE_BANNER = 1;
    public final static int TYPE_CONTENT = 0;
    private int type = 0;
    private String docid;
    private String title;
    private int replyCount;
    private String lmodify;
    private String ltitle;
    private String imgsrc;
    private String digest;
    private String url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getLtitle() {
        return ltitle;
    }

    public void setLtitle(String ltitle) {
        this.ltitle = ltitle;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.docid);
        dest.writeString(this.title);
        dest.writeInt(this.replyCount);
        dest.writeString(this.lmodify);
        dest.writeString(this.ltitle);
        dest.writeString(this.imgsrc);
        dest.writeString(this.digest);
        dest.writeString(this.url);
    }

    public Books() {
    }

    protected Books(Parcel in) {
        this.type = in.readInt();
        this.docid = in.readString();
        this.title = in.readString();
        this.replyCount = in.readInt();
        this.lmodify = in.readString();
        this.ltitle = in.readString();
        this.imgsrc = in.readString();
        this.digest = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel source) {
            return new Books(source);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };
}
