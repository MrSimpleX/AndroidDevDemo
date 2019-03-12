package com.simplez.androiddevdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simplez.androiddevdemo.activitydev.ActivityLifeStateA;
import com.simplez.androiddevdemo.broadcastdev.TestBroadCastReceive;

public class MainActivity extends AppCompatActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_index);
				Button buttonlife = findViewById(R.id.btn_activity_life_state);
				buttonlife.setOnClickListener(v -> ActivityLifeStateA.startActivity(MainActivity.this));


				Intent intent = new Intent();
				intent.setAction("BROADCAST_ACTION");
				intent.putExtra("TEST_BROAD_CAST", "This is BroadCastTest");
				sendBroadcast(intent);

				Button buttonBroadcastReceive = findViewById(R.id.btn_activity_broadcast);
				buttonBroadcastReceive.setOnClickListener( v ->{


						TestBroadCastReceive.startActivity(MainActivity.this);



				});
		}
}
