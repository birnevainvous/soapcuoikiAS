package com.example.timesheetsoap.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class InvoiceModel implements Parcelable{
    private int id;
    private String startDate, endDate;
    private String taskIV, projectIV, clientName;
    private int invoiceTime;

    EmployeeModel workerId;

    public InvoiceModel() {
        workerId = new EmployeeModel();
    }

    public InvoiceModel(int id, String startDate, String endDate, String taskIV, String projectIV, String clientName, int invoiceTime) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskIV = taskIV;
        this.projectIV = projectIV;
        this.clientName = clientName;
        this.invoiceTime = invoiceTime;

        workerId = new EmployeeModel();
    }

    protected InvoiceModel(Parcel in) {
        id = in.readInt();
        startDate = in.readString();
        endDate = in.readString();
        taskIV = in.readString();
        projectIV = in.readString();
        clientName = in.readString();
        invoiceTime = in.readInt();
    }

    public static final Creator<InvoiceModel> CREATOR = new Creator<InvoiceModel>() {
        @Override
        public InvoiceModel createFromParcel(Parcel in) {
            return new InvoiceModel(in);
        }

        @Override
        public InvoiceModel[] newArray(int size) {
            return new InvoiceModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTaskIV() {
        return taskIV;
    }

    public void setTaskIV(String taskIV) {
        this.taskIV = taskIV;
    }

    public String getProjectIV() {
        return projectIV;
    }

    public void setProjectIV(String projectIV) {
        this.projectIV = projectIV;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(int invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public EmployeeModel getWorkerId() {
        return workerId;
    }

    public void setWorkerId(EmployeeModel workerId) {
        this.workerId = workerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(taskIV);
        parcel.writeString(projectIV);
        parcel.writeString(clientName);
        parcel.writeInt(invoiceTime);
    }
}
