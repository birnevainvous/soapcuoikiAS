package com.example.timesheetsoap.ws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class wsLogin {
    public static String NAMESPACE = "http://ws/";
    public static String METHOD_NAME = "login";
    public static String ACTION = "login";
    public static String URL = "http://localhost:8080/TimesheetWS/TimesheetSubWebService?WSDL";

    public wsLogin(){

    }

    public String Login(String userName, String pass) throws XmlPullParserException, IOException {
        SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
        soapObject.addProperty("username", userName);
        soapObject.addProperty("password", pass);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE transportSE = new HttpTransportSE(URL);

        transportSE.call(ACTION, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;
        String res = object.getProperty(0).toString();
        System.out.println(res);

        return res;
    }
}
