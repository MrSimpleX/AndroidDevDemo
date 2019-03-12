package com.simplez.androiddevdemo.broadcastdev;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.simplez.androiddevdemo.R;

/**
 * @author MrSimpleZ
 * @version V1.0
 * @Title: AndroidDevDemo
 * @Package com.simplez.androiddevdemo.broadcastdev
 * @Description: (用一句话描述该文件做什么)
 * @date 2019/3/12 16:27
 */
public class TestBroadCastReceive extends AppCompatActivity {

		private CustomReceiver mCustomReceiver;

		@Override
		protected void onCreate(@Nullable Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);

				setContentView(R.layout.activity_main);
				mCustomReceiver = new CustomReceiver();
				IntentFilter intentFilter = new IntentFilter();
				intentFilter.addAction("BROADCAST_ACTION");
				registerReceiver(mCustomReceiver, intentFilter);
				TextView tv = findViewById(R.id.tv_hello);
				tv.setOnClickListener(v -> {

				});
		}

		@Override
		protected void onResume() {
				super.onResume();



		}

		@Override
		protected void onPause() {
				super.onPause();

		}

		@Override
		protected void onDestroy() {
				super.onDestroy();
				unregisterReceiver(mCustomReceiver);
		}

		public static void startActivity(Context context){
				Intent intent = new Intent(context, TestBroadCastReceive.class);
				context.startActivity(intent);
		}
}
