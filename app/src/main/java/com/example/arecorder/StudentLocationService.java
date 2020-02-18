package com.example.arecorder;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

public class StudentLocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE="com.example.arecorder.UPDATE_LOCATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){

            final String action=intent.getAction();
            if(ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult result= LocationResult.extractResult(intent);
                if(result!=null){
                    Location location=result.getLastLocation();
                    String lat= new String(String.valueOf(location.getLatitude()));
                    String lon= new String(String.valueOf(location.getLongitude()));
                            /*.append("/")
                            .append(location.getLongitude()).toString();*/
                    try{
                        StudentHomePage.getInstance().updateTextView(lat,lon);
                    }catch (Exception e){
                        Toast.makeText(context,lat+" "+lon,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        else{

        }
    }
}
