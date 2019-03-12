package com.simplez.androiddevdemo.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.net.URISyntaxException;


public class FileUtils {

	private FileUtils(){}

	/**
	 * 获取文件真实路径
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getRealFilePath(final Context context, final Uri uri ) {
		if ( null == uri ) return null;
		int sdkVersion = Build.VERSION.SDK_INT;
		if (sdkVersion < 11) {
			// SDK < Api11
			return getRealPathFromUri_BelowApi11(context, uri);
		}
		if (sdkVersion < 19) {
			// SDK > 11 && SDK < 19
			return getRealPathFromUri_Api11To18(context, uri);
		}
		// SDK > 19
		return getRealPathFromUri_Api11To18(context, uri);
	}

	/***
	 * 获取文件名
	 *
	 * @param urlPath
	 * @return
	 */
	public static final String getFileName(String urlPath){
		String showUrl = "";
		if(TextUtils.isEmpty(urlPath)){
			showUrl = "";
		}else{
			showUrl = urlPath.substring(urlPath.lastIndexOf("/") + 1, urlPath.length());
		}
		return showUrl;
	}

	/**
	 * 适配api19以上,根据uri获取图片的绝对路径
	 */
	@TargetApi(19)
	private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
		String filePath = null;
		String wholeID = DocumentsContract.getDocumentId(uri);

		// 使用':'分割
		String id = wholeID.split(":")[1];

		String[] projection = { MediaStore.Images.Media.DATA };
		String selection = MediaStore.Images.Media._ID + "=?";
		String[] selectionArgs = { id };

		Cursor cursor = context.getContentResolver().query(
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
			selection, selectionArgs, null);
		int columnIndex = cursor.getColumnIndex(projection[0]);

		if (cursor.moveToFirst()) {
			filePath = cursor.getString(columnIndex);
		}
		cursor.close();
		return filePath;
	}

	/**
	 * 适配api11-api18,根据uri获取图片的绝对路径
	 */
	private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
		String filePath = null;
		String[] projection = { MediaStore.Images.Media.DATA };

		CursorLoader loader = new CursorLoader(context, uri, projection, null,
			null, null);
		Cursor cursor = loader.loadInBackground();

		if (cursor != null) {
			cursor.moveToFirst();
			filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
			cursor.close();
		}
		return filePath;
	}

	/**
	 * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
	 */
	private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
		String filePath = null;
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, projection,
			null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
			cursor.close();
		}
		return filePath;
	}

	public static String getPath(Context context, Uri uri) throws URISyntaxException {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver().query(uri, projection, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				// Eat it  Or Log it.
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}



	public static String getPhotoPathFromContentUri(Context context, Uri uri) {
		String photoPath = "";
		if(context == null || uri == null) {
			return photoPath;
		}

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
			String docId = DocumentsContract.getDocumentId(uri);
			if(isExternalStorageDocument(uri)) {
				String [] split = docId.split(":");
				if(split.length >= 2) {
					String type = split[0];
					if("primary".equalsIgnoreCase(type)) {
						photoPath = Environment.getExternalStorageDirectory() + "/" + split[1];
					}
				}
			}
			else if(isDownloadsDocument(uri)) {
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
				photoPath = getDataColumn(context, contentUri, null, null);
			}
			else if(isMediaDocument(uri)) {
				String[] split = docId.split(":");
				if(split.length >= 2) {
					String type = split[0];
					Uri contentUris = null;
					if("image".equals(type)) {
						contentUris = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
					}
					else if("video".equals(type)) {
						contentUris = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
					}
					else if("audio".equals(type)) {
						contentUris = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
					}
					String selection = MediaStore.Images.Media._ID + "=?";
					String[] selectionArgs = new String[] { split[1] };
					photoPath = getDataColumn(context, contentUris, selection, selectionArgs);
				}
			}
		}
		else if("file".equalsIgnoreCase(uri.getScheme())) {
			photoPath = uri.getPath();
		}
		else {
			photoPath = getDataColumn(context, uri, null, null);
		}

		return photoPath;
	}

	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
		Cursor cursor = null;
		String column = MediaStore.Images.Media.DATA;
		String[] projection = { column };
		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		}
		return null;
	}

		public static File getFilePath(String filePath) {
				File file = null;
				makeRootDirectory(filePath);
				try {
						file = new File(filePath);
				} catch (Exception e) {
						e.printStackTrace();
				}
				return file;
		}

		public static void makeRootDirectory(String filePath) {
				File file = null;
				try {
						file = new File(filePath);
						if (!file.exists()) {
								file.mkdir();
						}
				} catch (Exception e) {

				}
		}

}
