package com.simplez.androiddevdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.simplez.androiddevdemo.activitydev.ActivityLifeStateA;

public class MainActivity extends AppCompatActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				TextView tvHello = findViewById(R.id.tv_hello);
				tvHello.setOnClickListener(v -> {
						ActivityLifeStateA.startActivity(this);
				});
		}
}
