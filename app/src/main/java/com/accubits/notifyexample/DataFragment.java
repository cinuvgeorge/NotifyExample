package com.accubits.notifyexample;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import static android.content.Context.MODE_PRIVATE;

public class DataFragment extends Fragment {
    TextView displayTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        displayTitle = view.findViewById(R.id.text_data);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String txtTitle = (sharedPreferences.getString("Title", ""));
        displayTitle.setText(txtTitle);

        return view;
    }
}
