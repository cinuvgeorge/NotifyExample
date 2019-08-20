package com.accubits.notifyexample;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;


public class DataFragment extends Fragment {
    TextView data;
    String text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        data = view.findViewById(R.id.text_data);
        SharedPreferences shared = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String channel = (shared.getString("Title", ""));
       // data.setText(name);
        Toast.makeText(getActivity(), ""+channel, Toast.LENGTH_SHORT).show();
        return view;
    }
}
