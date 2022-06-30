package com.example.timesheetsoap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timesheetsoap.models.EmployeeModel;
import com.example.timesheetsoap.models.HistoryModel;
import com.example.timesheetsoap.models.InvoiceModel;
import com.example.timesheetsoap.models.TimesheetModel;
import com.example.timesheetsoap.ws.wsLogin;


import org.apache.commons.io.IOUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    public static String NAMESPACE = "http://ws/";
    public static String METHOD_NAME = "login";
    public static String ACTION = "login";
    public static String URL = "http://192.168.5.101:8080/TimesheetWS/TimesheetSubWebService?WSDL";

    EditText edtTenDangNhap;
    EditText edtMatKhau;
    Button btnDangNhap;

    EmployeeModel employee;
    TimesheetModel timesheet;
    InvoiceModel invoice;
    HistoryModel history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);

        employee = new EmployeeModel();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyTask mt = new MyTask();
                mt.execute();

            }
        });
    }

    class MyTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            loadData();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Continue();
        }
    }

    public void loadData(){
        SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
        String userName = edtTenDangNhap.getText().toString();
        String pass = edtMatKhau.getText().toString();
        soapObject.addProperty("username", userName);
        soapObject.addProperty("password", pass);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE transportSE = new HttpTransportSE(URL);

        try {
            transportSE.call(ACTION, envelope);
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
                            employee.getHistoryModel().add(history);
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
                            employee.getTimesheetModel().add(timesheet);
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
                            employee.getInvoiceModel().add(invoice);
                            /*SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = format2.format(invoice.getStartDate());
                            System.out.println(date);*/
                        }

                    }
                    else {
                        switch (propertyInfo.name){
                            case "id":
                                employee.setId(Integer.parseInt(res));
                                break;
                            case "username":
                                employee.setUsername(res);
                                break;
                            case "password":
                                employee.setPassword(res);
                                break;
                            case "role":
                                employee.setRole(res);
                                break;
                            case "name":
                                employee.setName(res);
                                break;
                            case "email":
                                employee.setEmail(res);
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
    public void Continue(){
        if (employee.getRole().equals("worker")){
            Intent intent = new Intent(this, WorkerAct.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("employee", employee);
            bundle.putParcelableArrayList("historyList", employee.getHistoryModel());

            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (employee.getRole().equals("arClerk")){
            Intent intent = new Intent(this, ArClerkAct.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("employee", employee);

            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}