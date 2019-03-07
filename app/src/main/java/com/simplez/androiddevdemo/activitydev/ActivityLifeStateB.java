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
 * @date 2019/3/7 17:24
 */
public class ActivityLifeStateB extends AppCompatActivity {

		private static final String ACTIVITY_TAG = "ActivityTag";

		@Override
		protected void onCreate(@Nullable Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				TextView tvHello = findViewById(R.id.tv_hello);
				tvHello.setText("This is ActivityLifeStateB");
				Log.i(ACTIVITY_TAG, "ActivityLifeStateB onCreate()");
		}

		@Override
		protected void onNewIntent(Intent intent) {
				super.onNewIntent(intent);
				Log.i(ACTIVITY_TAG, "ActivityLifeStateB onNewIntent()");
		}

		@Override
		protected void onRestart() {
				super.onRestart();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateB onRestart()");
		}

		@Override
		protected void onStart() {
				super.onStart();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateB onStart()");
		}

		@Override
		protected void onResume() {
				super.onResume();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateB onResume()");
		}

		@Override
		protected void onPause() {
				super.onPause();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateB onPause()");
		}

		@Override
		protected void onStop() {
				super.onStop();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateB onStop()");
		}

		@Override
		protected void onDestroy() {
				super.onDestroy();
				Log.i(ACTIVITY_TAG, "ActivityLifeStateB onDestroy()");
		}

		public static void startActivity(Context context){
				Intent intent = new Intent(context, ActivityLifeStateB.class);
				context.startActivity(intent);
		}
}
