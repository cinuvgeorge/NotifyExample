package com.accubits.notifyexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "MainActivity";
    Button subscribe,unsubscribe;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView);
        subscribe = findViewById(R.id.button_sub);
        unsubscribe = findViewById(R.id.button_unsub);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("intentKey"));

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        String id = task.getResult().getId();
                        String msg = getString(R.string.fcm_token, id);
                        String token = task.getResult().getToken();
                        Log.e(TAG, msg);

                    }
                });
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().subscribeToTopic("global");
                Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();
            }
        });

        unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("global");
                Toast.makeText(MainActivity.this, "Unsubscribed", Toast.LENGTH_SHORT).show();
            }
        });

      /*  text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              *//*  Toast.makeText(getApplicationContext(), ""+messageNotification, Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("Title", messageNotification);
                editor.apply();

                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.frameLayout, new DataFragment());
                transaction.addToBackStack(null);
                transaction.commit();*//*
            }
        });*/

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String messageNotification = intent.getStringExtra("key");
            text.setText(messageNotification);
        }
    };
}

