package com.fzv.glucowatch.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fzv.glucowatch.R;
import com.fzv.glucowatch.activity.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.Set;

/**
 * Created by Nik on 9.5.2015.
 */
public class DataSendFragment extends Fragment {

    public static final String DATA_TRANSCRIPTION = "data_transcription";
    public static final String TAG = "DataSend";
    private GoogleApiClient googleApiClient;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.data_send_fragment, container, false);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Log.i(TAG,"Google api connected.");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.i(TAG,"Google api suspended.");
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.e(TAG, connectionResult.toString());
                    }
                })
                .build();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eventInitialization();
    }

    void eventInitialization() {
        ImageButton sendMassageBtn = (ImageButton) getView().findViewById(R.id.sendDataBtn);
        sendMassageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendMassage();
                    }
                }).start();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    private void sendMassage() {
        if (googleApiClient.isConnected()) {
            boolean nodeExist = false;
            //search for available nodes
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
            //send massage to all nodes
            for (Node node : nodes.getNodes()) {
                nodeExist = true;
                List<Fragment> fragments = ((MainActivity)getActivity()).getFragments();
                if(((InsulinInput)fragments.get(0)).getCurrentValue() != 0)
                    sendData(node,"/insulin",((InsulinInput)fragments.get(0)).getCurrentValue().toString(),null);
                if(((GlucoseInput)fragments.get(1)).getCurrentValue() != 0)
                    sendData(node,"/glucose",((GlucoseInput)fragments.get(1)).getCurrentValue().toString(),null);
                if(!((FoodInput)fragments.get(2)).getMealValue().equals("ni izbrano") && !((FoodInput)fragments.get(2)).getCarbohydratesValue().equals("ni izbrano"))
                    sendData(node,"/food",((FoodInput)fragments.get(2)).getMealValue().toString(),((FoodInput)fragments.get(2)).getCarbohydratesValue().toString());

            }
            if (!nodeExist){
                showConfirmationDialog("Nimate povezave z uro", ConfirmationActivity.FAILURE_ANIMATION);
            }
        }

    }

    private void sendData(Node node, String path, String data1, @Nullable String data2){
        String data = "";
        if(data2 != null)
            data = data1 + ";" + data2;
        else
            data = data1;

        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), path, data.getBytes()).await();
        if(result.getStatus().isSuccess()){
            Log.i(TAG,"Message seccesfuly sended!");
            showConfirmationDialog("Podatki shranjeni", ConfirmationActivity.SUCCESS_ANIMATION);
        }

    }

    private void showConfirmationDialog(String massage, int action){
        Intent intent = new Intent(getActivity() , ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,action);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,massage);
        startActivity(intent);
    }

    public void showToast(final String toast)
    {
        getActivity().runOnUiThread(new Runnable() {
            public void run(){
                Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
