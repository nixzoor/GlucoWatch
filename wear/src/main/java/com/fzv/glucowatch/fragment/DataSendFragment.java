package com.fzv.glucowatch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fzv.glucowatch.R;
import com.fzv.glucowatch.synchronicity.DataSynch;

/**
 * Created by Nik on 9.5.2015.
 */
public class DataSendFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.data_send_fragment,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eventInitialization();
    }

    void eventInitialization(){
        Button synchDataBtn = (Button) getView().findViewById(R.id.sendDataBtn);
        synchDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    private void sendData() {
        DataSynch dataSynch = new DataSynch(getActivity());
        dataSynch.putString("testKey","testValue");
    }
}
