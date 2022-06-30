package com.example.timesheetsoap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.timesheetsoap.models.EmployeeModel;
import com.example.timesheetsoap.models.InvoiceModel;

import java.util.ArrayList;

public class ArClerkAct extends AppCompatActivity {

    InvoiceModel invoiceModel;
    EmployeeModel employee;

    ListView listView;
    ArrayList<InvoiceModel> invoiceModels;
    ArClerkAct adapter;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_clerk);


    }
}