/*
 * Copyright (c) 2016 [zhiye.wei@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smile.moment.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Smile Wei
 * @since 2016/4/14.
 */
public class Jokes implements Parcelable {


    /**
     * boardid : comment_bbs
     * clkNum : 0
     * digest : 夫妻约定把“上床”叫“上课”。一日老婆发短信给老公:“今晚上课。”老公回信:“有应酬,改自习!”老婆不悦。次日老公说要上课,老婆回:“不必了,昨晚已请家教!”
     * docid : BKK4E8SH9001E8SI
     * downTimes : 11
     * imgType : 0
     * picCount : 0
     * program : HY
     * recType : 0
     * replyCount : 0
     * replyid : BKK4E8SH9001E8SI
     * source : 段子哥
     * title : 媳妇昨晚已请家教
     * upTimes : 55
     */

    public final static int TYPE_MSG = 0;
    public final static int TYPE_FOOTER_LOAD = 1;
    public final static int TYPE_FOOTER_FULL = 2;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type = TYPE_MSG;
    private String boardid;
    private int clkNum;
    private String digest;
    private String docid;
    private int downTimes;
    private int imgType;
    private int picCount;
    private String program;
    private int recType;
    private int replyCount;
    private String replyid;
    private String source;
    private String title;
    private int upTimes;

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public int getClkNum() {
        return clkNum;
    }

    public void setClkNum(int clkNum) {
        this.clkNum = clkNum;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public int getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(int downTimes) {
        this.downTimes = downTimes;
    }

    public int getImgType() {
        return imgType;
    }

    public void setImgType(int imgType) {
        this.imgType = imgType;
    }

    public int getPicCount() {
        return picCount;
    }

    public void setPicCount(int picCount) {
        this.picCount = picCount;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public int getRecType() {
        return recType;
    }

    public void setRecType(int recType) {
        this.recType = recType;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUpTimes() {
        return upTimes;
    }

    public void setUpTimes(int upTimes) {
        this.upTimes = upTimes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.boardid);
        dest.writeInt(this.clkNum);
        dest.writeString(this.digest);
        dest.writeString(this.docid);
        dest.writeInt(this.downTimes);
        dest.writeInt(this.imgType);
        dest.writeInt(this.picCount);
        dest.writeString(this.program);
        dest.writeInt(this.recType);
        dest.writeInt(this.replyCount);
        dest.writeString(this.replyid);
        dest.writeString(this.source);
        dest.writeString(this.title);
        dest.writeInt(this.upTimes);
    }

    public Jokes() {
    }

    protected Jokes(Parcel in) {
        this.boardid = in.readString();
        this.clkNum = in.readInt();
        this.digest = in.readString();
        this.docid = in.readString();
        this.downTimes = in.readInt();
        this.imgType = in.readInt();
        this.picCount = in.readInt();
        this.program = in.readString();
        this.recType = in.readInt();
        this.replyCount = in.readInt();
        this.replyid = in.readString();
        this.source = in.readString();
        this.title = in.readString();
        this.upTimes = in.readInt();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<Jokes> CREATOR = new Parcelable.Creator<Jokes>() {
        @Override
        public Jokes createFromParcel(Parcel source) {
            return new Jokes(source);
        }

        @Override
        public Jokes[] newArray(int size) {
            return new Jokes[size];
        }
    };
}
