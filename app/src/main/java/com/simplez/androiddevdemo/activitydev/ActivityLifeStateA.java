package com.simplez.androiddevdemo.activitydev;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.simplez.androiddevdemo.R;

/**
 * @author MrSimpleZ
 * @version V1.0
 * @Title: AndroidDevDemo
 * @Package com.simplez.androiddevdemo.activitydev
 * @Description: (用一句话描述该文件做什么)
 * @date 2019/3/7 16:46
 */
public class ActivityLifeStateA extends AppCompatActivity {

		private static final String ACTIVITY_TAG = "ActivityTag";

		@Override
		protected void onCreate(@Nullable Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				TextView tvHello = findViewById(R.id.tv_hello);
				tvHello.setText("This is ActivityLifeStateA");
				tvHello.setOnClickListener( v -> {
						Intent intent = new Intent();
						intent.setClass(this, ActivityLifeStateB.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);

						ActivityLifeStateB.startActivity(this);
						finish();
				});
				Log.i(ACTIVITY_TAG, "ActivityLifeStateA onCreate()");
		}

		@Override
		protected void onNewIntent(Intent intent) {
				super.onNewIntent(intent);
				Log.i(ACTIVITY_TAG, "ActivityLifeStateA onNewIntent()");
		}

		@Override
		protected void onRestart() {
				super.onRestart();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateA onRestart()");
		}

		@Override
		protected void onStart() {
				super.onStart();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateA onStart()");
		}

		@Override
		protected void onResume() {
				super.onResume();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateA onResume()");
		}

		@Override
		protected void onPause() {
				super.onPause();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateA onPause()");
		}

		@Override
		protected void onStop() {
				super.onStop();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateA onStop()");
		}

		@Override
		protected void onDestroy() {
				super.onDestroy();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateA onDestroy()");
		}

		public static void startActivity(Context context){
				Intent intent = new Intent(context, ActivityLifeStateA.class);
				context.startActivity(intent);
		}
}
