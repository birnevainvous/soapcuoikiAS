package com.example.timesheetsoap.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class HistoryModel implements Parcelable{
    private int id;
    private String startTimeH, endTimeH;
    private String taskH, projectH;
    private int totalTime;
    private boolean accept;

    public HistoryModel() {
    }

    public HistoryModel(int id, String startTimeH, String endTimeH, String taskH, String projectH, int totalTime, boolean accept) {
        this.id = id;
        this.startTimeH = startTimeH;
        this.endTimeH = endTimeH;
        this.taskH = taskH;
        this.projectH = projectH;
        this.totalTime = totalTime;
        this.accept = accept;
    }

    protected HistoryModel(Parcel in) {
        id = in.readInt();
        startTimeH = in.readString();
        endTimeH = in.readString();
        taskH = in.readString();
        projectH = in.readString();
        totalTime = in.readInt();
        accept = in.readByte() != 0;
    }

    public static final Creator<HistoryModel> CREATOR = new Creator<HistoryModel>() {
        @Override
        public HistoryModel createFromParcel(Parcel in) {
            return new HistoryModel(in);
        }

        @Override
        public HistoryModel[] newArray(int size) {
            return new HistoryModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTimeH() {
        return startTimeH;
    }

    public void setStartTimeH(String startTimeH) {
        this.startTimeH = startTimeH;
    }

    public String getEndTimeH() {
        return endTimeH;
    }

    public void setEndTimeH(String endTimeH) {
        this.endTimeH = endTimeH;
    }

    public String getTaskH() {
        return taskH;
    }

    public void setTaskH(String taskH) {
        this.taskH = taskH;
    }

    public String getProjectH() {
        return projectH;
    }

    public void setProjectH(String projectH) {
        this.projectH = projectH;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(startTimeH);
        parcel.writeString(endTimeH);
        parcel.writeString(taskH);
        parcel.writeString(projectH);
        parcel.writeInt(totalTime);
        parcel.writeByte((byte) (accept ? 1 : 0));
    }
}
