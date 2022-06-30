package com.example.timesheetsoap.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel implements Parcelable{
    private int id;
    private String username, password, role, name, email;

    ArrayList<TimesheetModel> timesheetModel;
    ArrayList<InvoiceModel> invoiceModel;
    ArrayList<HistoryModel> historyModel;

    public EmployeeModel(int id, String username, String password, String role, String name, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;

        timesheetModel = new ArrayList<>();
        invoiceModel = new ArrayList<>();
        historyModel = new ArrayList<>();
    }

    public EmployeeModel(){
        timesheetModel = new ArrayList<>();
        invoiceModel = new ArrayList<>();
        historyModel = new ArrayList<>();
    }

    protected EmployeeModel(Parcel in) {
        id = in.readInt();
        username = in.readString();
        password = in.readString();
        role = in.readString();
        name = in.readString();
        email = in.readString();
        timesheetModel = in.createTypedArrayList(TimesheetModel.CREATOR);
        invoiceModel = in.createTypedArrayList(InvoiceModel.CREATOR);
        historyModel = in.createTypedArrayList(HistoryModel.CREATOR);
    }

    public static final Creator<EmployeeModel> CREATOR = new Creator<EmployeeModel>() {
        @Override
        public EmployeeModel createFromParcel(Parcel in) {
            return new EmployeeModel(in);
        }

        @Override
        public EmployeeModel[] newArray(int size) {
            return new EmployeeModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<TimesheetModel> getTimesheetModel() {
        return timesheetModel;
    }

    public void setTimesheetModel(ArrayList<TimesheetModel> timesheetModel) {
        this.timesheetModel = timesheetModel;
    }

    public ArrayList<InvoiceModel> getInvoiceModel() {
        return invoiceModel;
    }

    public void setInvoiceModel(ArrayList<InvoiceModel> invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    public ArrayList<HistoryModel> getHistoryModel() {
        return historyModel;
    }

    public void setHistoryModel(ArrayList<HistoryModel> historyModel) {
        this.historyModel = historyModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(role);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeTypedList(timesheetModel);
        parcel.writeTypedList(invoiceModel);
        parcel.writeTypedList(historyModel);
    }
}
