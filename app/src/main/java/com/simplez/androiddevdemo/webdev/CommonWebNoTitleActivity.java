package com.simplez.androiddevdemo.webdev;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.simplez.androiddevdemo.R;
import com.simplez.androiddevdemo.utils.FileUtils;
import com.simplez.androiddevdemo.utils.PictureChromeClient;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class CommonWebNoTitleActivity extends AppCompatActivity {

		ProgressBar mProgress;
		private WebView webView;
		private String mUrl;
		private Handler mHandler;

		private String mSaveUrl;

		//拍照选图片
		private PictureChromeClient mPictureChromeClient;
		public static final int REQUEST_SELECT_FILE_CODE = 100;
		private ValueCallback<Uri> mUploadMsg;
		private ValueCallback<Uri[]> mUploadMsgs;
		private String mPath = "";
		//拍照选图片

		private static final int RC_STORAGE = 124;
		public static final String[] STORAGE =
			{WRITE_EXTERNAL_STORAGE};

		private static final int RC_CALL = 125;
		public static final String[] CALL =
			{CALL_PHONE};

		private static final int RC_CAMER = 125;
		public static final String[] CAMMER =
			{CAMERA};

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);

				//初始化接收的参数
				initRevDatas();
				//初始化webview
				initWebViewSetting();
				//处理消息事件
				applyHandlerCallback();
				//右上角分享
				setShareButtonListener(true);
				//返回键监听
				setBackButtonListener();
		}

		private void initWebViewSetting() {
				//显示网页
				webView.loadUrl(mUrl);

				webView.getSettings().setUseWideViewPort(true);
				webView.getSettings().setDisplayZoomControls(false);
				webView.getSettings().setLoadWithOverviewMode(true);
				webView.getSettings().setDomStorageEnabled(true);
				webView.getSettings().setTextZoom(100);
				// 设置js可用
				webView.getSettings().setJavaScriptEnabled(true);
				webView.getSettings().setAppCacheEnabled(false);
				webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
				webView.getSettings().setBuiltInZoomControls(true);
				webView.getSettings().setAllowFileAccess(true);
				webView.getSettings().setBuiltInZoomControls(true);
				mPictureChromeClient = new PictureChromeClient();
				webView.setWebChromeClient(mPictureChromeClient);
				mPictureChromeClient.setOpenFileChooserCallBack(new PictureChromeClient.OpenFileChooserCallBack() {
						@Override
						public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
								mUploadMsg = uploadMsg;
								showFileSelect();
						}

						@Override
						public void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
								if (mUploadMsgs != null) {
										mUploadMsgs.onReceiveValue(null);
								}
								mUploadMsgs = filePathCallback;
								showFileSelect();
						}

						@Override
						public void showTitle(String title) {

						}

						@Override
						public void showProgress(int progress) {
								if (progress == 100) {
										mProgress.setVisibility(View.GONE);
								} else {
										mProgress.setVisibility(View.GONE);
										mProgress.setProgress(progress);
								}
						}
				});

				// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
				webView.setWebViewClient(new CustomWebViewClient());

				webView.addJavascriptInterface(new JavaScriptInterface(), "AppFunction");
		}


		private void showFileSelect() {
//				if (EasyPermissions.hasPermissions(CommonWebNoTitleActivity.this, Manifest.permission.CAMERA)) {
//						//图库选图的intent
//						Intent intent1 = new Intent(Intent.ACTION_PICK);
//						intent1.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "image/*");
//						//相机拍照
//						Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//						String pictureFile = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
//						mPath = getPhotoPath() + pictureFile;
//						File file = new File(mPath);
//						Uri imageUri = Uri.fromFile(file);
//						intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//
//						Intent intent = Intent.createChooser(intent1, "选取图片");
//						intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});
//
//						startActivityForResult(intent, REQUEST_SELECT_FILE_CODE);
//				} else {
//						// Ask for both permissions
//						EasyPermissions.requestPermissions(
//							CommonWebNoTitleActivity.this,
//							"请您开启摄像头权限",
//							RC_CAMER,
//							CAMERA);
//				}


		}

		private void initRevDatas() {
				//TODO 初始化参数
		}


		class CustomWebViewClient extends WebViewClient {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {

						if (url.startsWith("https") || url.startsWith("http")) {
								mUrl = url;
								if (mUrl.contains("activeList")) {
										//TODO 跳转网页
								} else {
										return false;
								}
						} else {
								try {
										Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
										startActivity(intent);
								} catch (Exception e) {
										return true;
								}
						}
						return true;
				}


		}


		private void applyHandlerCallback() {
				//TODO 处理Handler

		}

		private void setShareButtonListener(boolean isShare) {

		}

		private void setBackButtonListener() {

		}


		@Override
		public void onBackPressed() {
				mUrl = webView.getUrl();
				if (isEqualToBaseUrl()) {
						finish();
						return;
				}
				if (webView.canGoBack()) {
						webView.goBack();
				} else {
						finish();
				}
		}

		@Override
		protected void onResume() {
				super.onResume();
		}

		/**
		 * 对URL中文进行编码
		 *
		 * @param url
		 * @return
		 */
		public String encodeUrl(String url) {
				return Uri.encode(url, "-![.:/,%?&=]");
		}

		final class JavaScriptInterface {


				@JavascriptInterface
				public void call(String phone) {
//						if (EasyPermissions.hasPermissions(CommonWebNoTitleActivity.this, Manifest.permission.CALL_PHONE)) {
//								Intent intentCall = new Intent(Intent.ACTION_CALL,
//									Uri.parse("tel:" + phone));
//								if (ActivityCompat.checkSelfPermission(CommonWebNoTitleActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//										ADToast.showShort(CommonWebNoTitleActivity.this, "请到设置中开启拨打电话权限");
//										return;
//								}
//								startActivity(intentCall);
//						} else {
//								// Ask for both permissions
//								EasyPermissions.requestPermissions(
//									CommonWebNoTitleActivity.this,
//									"请先开启拨打电话权限",
//									RC_CALL,
//									Manifest.permission.CALL_PHONE);
//						}
				}

				/**
				 * 开启提醒
				 */
				@JavascriptInterface
				public void openNotificationClick() {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
								// 进入设置系统应用权限界面
								Intent intent = new Intent(Settings.ACTION_SETTINGS);
								startActivity(intent);
						} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
								// 进入设置系统应用权限界面
								Intent intent = new Intent(Settings.ACTION_SETTINGS);
								startActivity(intent);
						}


				}

				/**
				 * 跳到浏览器下载
				 */
				@JavascriptInterface
				public void downloadbyBrowser(String url) {
						Intent intent = new Intent();
						intent.setAction("android.intent.action.VIEW");
						Uri content_url = Uri.parse(url);
						intent.setData(content_url);
						startActivity(intent);
				}

				/**
				 * 调用保存图片
				 */
				@JavascriptInterface
				public void appSaveBitmip(String url) {
//						if (EasyPermissions.hasPermissions(CommonWebNoTitleActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//								OkHttpClientManager.downloadImage(url, new OkHttpClientManager.OnBitmapDownLoadListener() {
//										@Override
//										public void onSuccess(Bitmap bitmap) {
//
//										}
//
//										@Override
//										public void onErr() {
//
//										}
//								});
//						} else {
//								// Ask for both permissions
//								EasyPermissions.requestPermissions(
//									CommonWebNoTitleActivity.this,
//									"请您开启读写权限",
//									RC_STORAGE,
//									STORAGE);
//						}
				}
		}

		/**
		 * 判断重定向后地址与初始地址是否一致，如一致则控制WebView关闭
		 *
		 * @return 与原Url一致 true
		 */
		public boolean isEqualToBaseUrl() {
				String currentUrl;
				if (TextUtils.isEmpty(mSaveUrl)) {
						return false;
				}
				if (TextUtils.isEmpty(mUrl)) {
						return false;
				}
				if (mUrl.contains("?")) {
						currentUrl = mUrl.split("[?]")[0];
				} else {
						currentUrl = mUrl;
				}

				return currentUrl.equals(mSaveUrl);
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				super.onActivityResult(requestCode, resultCode, data);
				if(requestCode == 600 && resultCode == 901){
						if(null != webView){
								webView.loadUrl(mSaveUrl);
						}
				}
				if (resultCode == RESULT_CANCELED) {
						if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
								mUploadMsgs.onReceiveValue(null);
								mUploadMsgs = null;
						} else {
								mUploadMsg.onReceiveValue(null);
								mUploadMsg = null;
						}
				}
				if (requestCode == REQUEST_SELECT_FILE_CODE && resultCode == RESULT_OK) {
						if (null != data) {
								Uri uri = data.getData();
								String path = FileUtils.getPhotoPathFromContentUri(this, uri);
								if (uri != null) {
										mPath = path;
								}
						}

						if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
								Uri[] uri = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
								if (null == uri) {
										File temp = FileUtils.getFilePath(mPath);
										uri = new Uri[]{Uri.fromFile(temp)};
								}
								mUploadMsgs.onReceiveValue(uri);
								mUploadMsgs = null;
						} else {
								Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
								if (result == null) {
										File temp = FileUtils.getFilePath(mPath);
										result = Uri.fromFile(temp);
								}
								mUploadMsg.onReceiveValue(result);
								mUploadMsg = null;
						}

				}
		}

		private String getPhotoPath() {
				return Environment.getExternalStorageDirectory() + "/DCIM/";
		}

}
