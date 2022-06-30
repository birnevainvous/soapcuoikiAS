package com.example.timesheetsoap.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class TimesheetModel implements Parcelable{
    private int id;
    private String startTimeTS, endTimeTS;
    private String taskTS, projectTS;
    private int totalTime;

    public TimesheetModel() {
    }

    public TimesheetModel(int id, String startTimeTS, String endTimeTS, String taskTS, String projectTS, int totalTime) {
        this.id = id;
        this.startTimeTS = startTimeTS;
        this.endTimeTS = endTimeTS;
        this.taskTS = taskTS;
        this.projectTS = projectTS;
        this.totalTime = totalTime;
    }

    protected TimesheetModel(Parcel in) {
        id = in.readInt();
        startTimeTS = in.readString();
        endTimeTS = in.readString();
        taskTS = in.readString();
        projectTS = in.readString();
        totalTime = in.readInt();
    }

    public static final Creator<TimesheetModel> CREATOR = new Creator<TimesheetModel>() {
        @Override
        public TimesheetModel createFromParcel(Parcel in) {
            return new TimesheetModel(in);
        }

        @Override
        public TimesheetModel[] newArray(int size) {
            return new TimesheetModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTimeTS() {
        return startTimeTS;
    }

    public void setStartTimeTS(String startTimeTS) {
        this.startTimeTS = startTimeTS;
    }

    public String getEndTimeTS() {
        return endTimeTS;
    }

    public void setEndTimeTS(String endTimeTS) {
        this.endTimeTS = endTimeTS;
    }

    public String getTaskTS() {
        return taskTS;
    }

    public void setTaskTS(String taskTS) {
        this.taskTS = taskTS;
    }

    public String getProjectTS() {
        return projectTS;
    }

    public void setProjectTS(String projectTS) {
        this.projectTS = projectTS;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(startTimeTS);
        parcel.writeString(endTimeTS);
        parcel.writeString(taskTS);
        parcel.writeString(projectTS);
        parcel.writeInt(totalTime);
    }
}
