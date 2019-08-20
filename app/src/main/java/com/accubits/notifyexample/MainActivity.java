package com.accubits.notifyexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    Button subscribe,unsubscribe;
    TextView displayText;
    String messageNotification,notifId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayText = findViewById(R.id.textView);
        subscribe = findViewById(R.id.button_sub);
        unsubscribe = findViewById(R.id.button_unsub);

        setFirebase();
        setBroadcastManager();

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicSubcribe();
            }
        });

        unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicUnsubcribe();
            }
        });

        displayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment();
            }
        });
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            messageNotification = intent.getStringExtra("key");
            displayText.setText(messageNotification);
        }
    };

    private void displayFragment(){
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("Title", messageNotification);
        editor.apply();

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout, new DataFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void topicSubcribe(){
        FirebaseMessaging.getInstance().subscribeToTopic("global");
        Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();
    }

    private void topicUnsubcribe(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("global");
        Toast.makeText(MainActivity.this, "Unsubscribed", Toast.LENGTH_SHORT).show();
    }

    private void setFirebase(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String notifId    = task.getResult().getId();
                    }
                });
    }

    private void setBroadcastManager(){
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("intentKey"));
    }

}

