package com.fzv.glucowatch.synchronicity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Nik on 8.5.2015.
 */
public class DataSynch {

    private GoogleApiClient googleApiClient;

    public DataSynch(Context context) {
        //inizialization googleAPI
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d("connected" , "onConnected: " + connectionHint);
                        // Now you can use the Data Layer API
                    }
                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d("susspended", "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d("error", "onConnectionFailed: " + result);
                    }
                })
                // Request access only to the Wearable API
                .addApi(Wearable.API)
                .build();
    }

    public void putString(String key, String value){
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create("/data");
        dataMapRequest.getDataMap().putString("/" + key,value);
        PutDataRequest dataRequest = dataMapRequest.asPutDataRequest();

        PendingResult<DataApi.DataItemResult> result = Wearable.DataApi.putDataItem(googleApiClient,dataRequest);
    }

}
