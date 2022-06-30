package com.example.timesheetsoap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timesheetsoap.adapter.WorkerAdapter;
import com.example.timesheetsoap.models.EmployeeModel;
import com.example.timesheetsoap.models.HistoryModel;
import com.example.timesheetsoap.models.InvoiceModel;
import com.example.timesheetsoap.models.TimesheetModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class WorkerAct extends AppCompatActivity {

    public static String NAMESPACE = "http://ws/";
    public static String URL = "http://192.168.5.101:8080/TimesheetWS/TimesheetSubWebService?WSDL";

    EmployeeModel employee;
    EmployeeModel employeeData;
    TimesheetModel timesheet;
    HistoryModel history;
    InvoiceModel invoice;

    ListView listView;
    WorkerAdapter adapter;
    Button add;
    TextView tennv, chucvunv;

    String wStart, wTend, wTotalTime, wTask, wProject;
    ArrayList<HistoryModel> historyModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        add = findViewById(R.id.button3);
        listView = findViewById(R.id.listviewNhanVien);
        employeeData = new EmployeeModel();
        tennv = findViewById(R.id.tennv);
        chucvunv = findViewById(R.id.chucvunv);

        Bundle bundle = getIntent().getExtras();
        employee = bundle.getParcelable("employee");
        historyModels = bundle.getParcelableArrayList("historyList");

        tennv.setText(employee.getName());
        chucvunv.setText(employee.getRole());

        adapter = new WorkerAdapter(WorkerAct.this, R.layout.line_worker, historyModels);
        listView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd();
            }
        });
    }

    private void DialogAdd() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_nhanvien);

        EditText edtStartTimeW, edtEndTimeW, edtTotalTimeW, edtTaskW, edtProjectW;
        Button btnAddW;

        edtStartTimeW = dialog.findViewById(R.id.edtStartTimeW);
        edtEndTimeW = dialog.findViewById(R.id.edtEndTimeW);
        edtTotalTimeW = dialog.findViewById(R.id.edtTotalTimeW);
        edtTaskW = dialog.findViewById(R.id.edtTaskW);
        edtProjectW = dialog.findViewById(R.id.edtProjectW);
        btnAddW = dialog.findViewById(R.id.btnAddW);

        btnAddW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wStart = edtStartTimeW.getText().toString();
                wTend = edtEndTimeW.getText().toString();
                wTotalTime = edtTotalTimeW.getText().toString();
                wTask = edtTaskW.getText().toString();
                wProject = edtProjectW.getText().toString();
                historyModels.clear();
                MyTask2 mt = new MyTask2();
                mt.execute();
                //adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    class MyTask2 extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            addData();
            Continue();
            //cancel(true);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            System.out.println(historyModels.size());
            adapter.notifyDataSetChanged();
        }
    }

    private void addData() {
        String method_name = "timeSheetSubmit";
        String action = "timeSheetSubmit";
        SoapObject soapObject = new SoapObject(NAMESPACE, method_name);
        soapObject.addProperty("startTime",wStart);
        soapObject.addProperty("endTime",wTend);
        soapObject.addProperty("task",wTask);
        soapObject.addProperty("project",wProject);
        soapObject.addProperty("totalTime",wTotalTime+"");
        soapObject.addProperty("id",employee.getId()+"");
        soapObject.addProperty("role",employee.getRole());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE transportSE = new HttpTransportSE(URL);

        try {
            transportSE.call(action, envelope);
            SoapObject object2 = (SoapObject) envelope.bodyIn;
            String re = object2.getProperty(0).toString();
            System.out.println(re);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void Continue() {
        String method_name = "login";
        String action = "login";
        SoapObject soapObject = new SoapObject(NAMESPACE, method_name);
        String userName = employee.getUsername();
        String pass = employee.getPassword();
        soapObject.addProperty("username", userName);
        soapObject.addProperty("password", pass);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE transportSE = new HttpTransportSE(URL);

        try {
            transportSE.call(action, envelope);
            SoapObject object = (SoapObject) envelope.getResponse();
            if(object == null){
                Looper.prepare();
                Toast.makeText(this, "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
            }
            else {
                for (int i = 0; i < object.getPropertyCount();i++){
                    PropertyInfo propertyInfo = new PropertyInfo();
                    PropertyInfo subPropertyInfo = new PropertyInfo();
                    String res = (String) object.getProperty(i).toString();
                    object.getPropertyInfo(i,propertyInfo);
                    if (propertyInfo.name.equals("historyModel") || propertyInfo.name.equals("invoiceModel") || propertyInfo.name.equals("timesheetModel"))
                    {
                        timesheet = new TimesheetModel();
                        invoice = new InvoiceModel();
                        history = new HistoryModel();
                        SoapObject listOB = (SoapObject) object.getProperty(i);
                        if (propertyInfo.name.equals("historyModel")){
                            for(int lOB = 0; lOB < listOB.getPropertyCount();lOB++){
                                listOB.getPropertyInfo(lOB,subPropertyInfo);
                                String subRes = (String) listOB.getProperty(lOB).toString();
                                switch (subPropertyInfo.name){
                                    case "accept":
                                        if (subRes.equals("true"))
                                            history.setAccept(true);
                                        else
                                            history.setAccept(false);
                                        break;
                                    case "id":
                                        history.setId(Integer.parseInt(subRes));
                                        break;
                                    case "startTimeH":
                                        String[] timeSplit = subRes.split("\\+");
                                        history.setStartTimeH(timeSplit[0]);
                                        break;
                                    case "endTimeH":
                                        String[] timeSplit2 = subRes.split("\\+");
                                        history.setEndTimeH(timeSplit2[0]);
                                        break;
                                    case "taskH":
                                        history.setTaskH(subRes);
                                        break;
                                    case "projectH":
                                        history.setProjectH(subRes);
                                        break;
                                    case "totalTime":
                                        history.setTotalTime(Integer.parseInt(subRes));
                                        break;
                                    default:
                                        break;
                                }
                            }
                            employeeData.getHistoryModel().add(history);
                            historyModels.add(history);
                            System.out.println(historyModels.size());
                        }

                        if (propertyInfo.name.equals("timesheetModel")){
                            for(int lOB = 0; lOB < listOB.getPropertyCount();lOB++){
                                listOB.getPropertyInfo(lOB,subPropertyInfo);
                                String subRes = (String) listOB.getProperty(lOB).toString();
                                switch (subPropertyInfo.name){
                                    case "id":
                                        timesheet.setId(Integer.parseInt(subRes));
                                        break;
                                    case "startTimeTS":
                                        String[] timeSplit = subRes.split("\\+");
                                        timesheet.setStartTimeTS(timeSplit[0]);
                                        break;
                                    case "endTimeTS":
                                        String[] timeSplit2 = subRes.split("\\+");
                                        timesheet.setEndTimeTS(timeSplit2[0]);
                                        break;
                                    case "taskTS":
                                        timesheet.setTaskTS(subRes);
                                        break;
                                    case "projectTS":
                                        timesheet.setProjectTS(subRes);
                                        break;
                                    case "totalTime":
                                        timesheet.setTotalTime(Integer.parseInt(subRes));
                                        break;
                                    default:
                                        break;
                                }
                            }
                            employeeData.getTimesheetModel().add(timesheet);
                        }

                        if (propertyInfo.name.equals("invoiceModel")){
                            for(int lOB = 0; lOB < listOB.getPropertyCount();lOB++){
                                listOB.getPropertyInfo(lOB,subPropertyInfo);
                                String subRes = (String) listOB.getProperty(lOB).toString();
                                switch (subPropertyInfo.name){
                                    case "clientName":
                                        invoice.setClientName(subRes);
                                        break;
                                    case "id":
                                        invoice.setId(Integer.parseInt(subRes));
                                        break;
                                    case "startDate":
                                        String[] timeSplit = subRes.split("\\+");
                                        invoice.setStartDate(timeSplit[0]);
                                        break;
                                    case "endDate":
                                        String[] timeSplit2 = subRes.split("\\+");
                                        invoice.setEndDate(timeSplit2[0]);
                                        break;
                                    case "taskIV":
                                        invoice.setTaskIV(subRes);
                                        break;
                                    case "projectIV":
                                        invoice.setProjectIV(subRes);
                                        break;
                                    case "invoiceTime":
                                        invoice.setInvoiceTime(Integer.parseInt(subRes));
                                        break;
                                    default:
                                        break;
                                }
                            }
                            employeeData.getInvoiceModel().add(invoice);
                        }

                    }
                    else {
                        switch (propertyInfo.name){
                            case "id":
                                employeeData.setId(Integer.parseInt(res));
                                break;
                            case "username":
                                employeeData.setUsername(res);
                                break;
                            case "password":
                                employeeData.setPassword(res);
                                break;
                            case "role":
                                employeeData.setRole(res);
                                break;
                            case "name":
                                employeeData.setName(res);
                                break;
                            case "email":
                                employeeData.setEmail(res);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}