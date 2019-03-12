package com.simplez.androiddevdemo.broadcastdev;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author MrSimpleZ
 * @version V1.0
 * @Title: AndroidDevDemo
 * @Package com.simplez.androiddevdemo.broadcastdev
 * @Description: (用一句话描述该文件做什么)
 * @date 2019/3/12 16:24
 */
public class CustomReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
				String broadCast = intent.getStringExtra("TEST_BROAD_CAST");
				Toast.makeText(context, broadCast, Toast.LENGTH_SHORT).show();
		}
}
