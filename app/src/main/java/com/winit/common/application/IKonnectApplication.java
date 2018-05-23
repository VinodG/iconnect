package com.winit.common.application;

import android.app.ActivityManager;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.winit.common.Preference;
import com.winit.common.constant.AppConstants;
import com.winit.common.httpmanager.FileSystemPersistence;
import com.winit.common.httpmanager.HttpDownloadManager;
import com.winit.common.httpmanager.HttpImageManager;
import com.winit.common.util.StringUtils;
import com.winit.iKonnect.R;
import com.winit.iKonnect.dataobject.ChatGroupModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;


public class IKonnectApplication extends MultiDexApplication {
	public static String MyLock = "Lock";
	public static Context mContext;

	private HttpImageManager mHttpImageManager;
	private HttpDownloadManager httpDownloadManager;
	protected static FirebaseAnalytics mFirebaseAnalytics;
	private static HashMap<String, Object> hmCache = new HashMap<>();
	private DatabaseReference root;
	private ChatGroupEventListener chatGroupEventListener;
	private TreeMap<Integer,ChatGroupModel> chatGroupModels;
	private HashMap<String,Integer> hmCount= new HashMap<>();
	private ArrayList<String> arrAllKeyData= new ArrayList<>();
	private Preference preference;
	private ArrayList<Long> lastReadTime= new ArrayList<>();
	private int Count=0;
	private int k=0;

	@Override
	public void onCreate() {
		super.onCreate();
		if (mContext == null)
			mContext = this;
		int memClass = (( ActivityManager) getSystemService( Context.ACTIVITY_SERVICE )).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 8;
		preference= Preference.getInstance();
		mHttpImageManager = new HttpImageManager(
				HttpImageManager.createDefaultMemoryCache(cacheSize),
				new FileSystemPersistence(AppConstants.CACHE_DIR_PATH));
		httpDownloadManager = new HttpDownloadManager();
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
	}

	public static void logEvents(String event, Bundle params){
		mFirebaseAnalytics.logEvent(event, params);
		mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
	}

	public HttpImageManager getHttpImageManager() {
		return mHttpImageManager;
	}

	public static Object getCacheObject(String type) {
		return hmCache.get(type);
	}

	public static void setCacheObject(String type, Object object) {
		IKonnectApplication.hmCache.put(type, object);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public DatabaseReference getRoot() {
		return root;
	}

	public void setRoot(DatabaseReference root) {
		this.root = root;
		root.addValueEventListener(chatGroupEventListener = new ChatGroupEventListener());
	}

	public ChatGroupEventListener getChatGroupEventListener() {
		return chatGroupEventListener;
	}

	public void setChatGroupEventListener(ChatGroupEventListener chatGroupEventListener) {
		this.chatGroupEventListener = chatGroupEventListener;
	}

	public TreeMap<Integer, ChatGroupModel> getChatGroupModels() {
		return chatGroupModels;
	}

	public static void setTypeFace(ViewGroup group, Typeface typeface) {
		if (typeface != null) {
			int count = group.getChildCount();
			View v;
			for (int i = 0; i < count; i++) {
				v = group.getChildAt(i);
				if (v instanceof TextView || v instanceof Button || v instanceof EditText/* etc. */)
					((TextView) v).setTypeface(typeface);
				else if (v instanceof ViewGroup)
					setTypeFace((ViewGroup) v, typeface);
			}
		}
	}

	@BindingAdapter("android:src")
	public static void setImageUrl(ImageView imageView, String url) {
		imageView.setTag(R.string.IMAGE_PATH,url);
		final Uri uri = Uri.parse(url);
		Bitmap bitmap = null;
		if (uri != null) {
			bitmap = ((IKonnectApplication) mContext).mHttpImageManager.loadImage(new HttpImageManager.LoadRequest(uri, imageView, url));
			if (bitmap != null && !bitmap.isRecycled()) {
				imageView.setImageBitmap(bitmap);
			} else {
				imageView.setImageResource(R.drawable.popup_bg);
			}
		} else {
			imageView.setImageResource(R.drawable.popup_bg);
		}
	}

	@BindingAdapter({"bind:font"})
	public static void setTypeFace(View v, Typeface typeface) {
		if (v instanceof TextView || v instanceof Button || v instanceof EditText)
			((TextView) v).setTypeface(typeface);
	}

	@BindingAdapter({"bind:imageUrl", "bind:error"})
	public static void setImageUrl(ImageView imageView, String url, Drawable error) {
		if (!StringUtils.isEmpty(url)) {
			imageView.setTag(R.string.IMAGE_PATH,url);
			imageView.setTag(R.string.isRound, imageView.getTag());
			final Uri uri = Uri.parse(url);
			Bitmap bitmap = null;
			if (uri != null) {
				bitmap = ((IKonnectApplication) mContext).mHttpImageManager.loadImage(new HttpImageManager.LoadRequest(uri, imageView, url));
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
					imageView.setImageDrawable(error);
				}
			} else {
				imageView.setImageDrawable(error);
			}
		}
		else
			imageView.setImageDrawable(error);
	}

	public static void setImageUrl(ImageView imageView, String url,int error) {
		final Uri uri = Uri.parse(url);
		Bitmap bitmap=null;
		if (uri!=null){
			bitmap = ((IKonnectApplication)mContext).mHttpImageManager.loadImage(new HttpImageManager.LoadRequest(uri, imageView,url));
			if (bitmap!=null){
				imageView.setImageBitmap(bitmap);
			}
			else {
				imageView.setImageResource(error);
			}
		}
		else{
			imageView.setImageResource(error);
		}
	}

	public static void setImageUrl(ImageView imageView, String url,int error,boolean isForceLoad) {
		final Uri uri = Uri.parse(url);
		Bitmap bitmap=null;
		if (uri!=null){
			bitmap = ((IKonnectApplication)mContext).mHttpImageManager.loadImage(new HttpImageManager.LoadRequest(uri, imageView,url,false,isForceLoad));
			if (bitmap!=null){
				imageView.setImageBitmap(bitmap);
			}
			else {
				imageView.setImageResource(error);
			}
		}
		else{
			imageView.setImageResource(error);
		}
	}

	@BindingAdapter("android:src")
	public static void setImageResource(ImageView imageView, int resource){
		imageView.setImageResource(resource);
	}

	public static void download(String path){
		Uri uri = Uri.parse(path);
		if (uri!=null) {
			((IKonnectApplication) mContext).httpDownloadManager.download(new HttpDownloadManager.LoadRequest(uri, null, path));
		}
	}

	public class ChatGroupEventListener implements ValueEventListener {

		ChatGroupFetchLishListener chatGroupFetchLishListener; //This listenter pattern only works for when we use events in single class if you want results to callback then use broadcast receviver.

		public ChatGroupFetchLishListener getChatGroupFetchLishListener() {
			return chatGroupFetchLishListener;
		}

		public void setChatGroupFetchLishListener(ChatGroupFetchLishListener chatGroupFetchLishListener) {
			this.chatGroupFetchLishListener = chatGroupFetchLishListener;
		}

		@Override
		public void onDataChange(DataSnapshot dataSnapshot) {

			Iterator i = dataSnapshot.getChildren().iterator();
			if(chatGroupModels == null)
				chatGroupModels = new TreeMap<>();
			else
				chatGroupModels.clear();
			while (i.hasNext()){
				DataSnapshot childDataSnapshot = (DataSnapshot)i.next();
				try
				{
					HashMap<String,Object> data =((HashMap<String,Object>)(childDataSnapshot.getValue()));
					ChatGroupModel chatGroupModel = new ChatGroupModel();
					chatGroupModel.setGroupKey(childDataSnapshot.getKey());
					chatGroupModel.setGroupName((String) data.get("name"));
					chatGroupModel.setGroupId(((Long) data.get("GroupId")).intValue());

					//Calculate No.Of Unread Messages
					lastReadTime.add(k,(Long) ((HashMap)data.get("LastReadTime")).get(""+preference.getStringFromPreference(Preference.STAFF_NUMBER,"")));
					DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot().child("ChatGroups").child(childDataSnapshot.getKey()).child("messages");
					addEventListener(root, chatGroupModel);
					k++;
					Count=0;
					chatGroupModels.put(chatGroupModel.getGroupId(),chatGroupModel);

				}catch (Exception e){
					e.printStackTrace();
				}
			}
			k=0;
			if(chatGroupFetchLishListener != null)
				chatGroupFetchLishListener.onChatGroups(chatGroupModels);
		}

		private  HashMap<String,Integer> getMessageCount(DataSnapshot dataSnapshot) {
			int count=0;
			hmCount.clear();
			long LsstVisitedTime=0;
			if(((HashMap)((HashMap)dataSnapshot.getValue()).get("LastReadTime")).containsKey(preference.getStringFromPreference(Preference.STAFF_NUMBER,"")))
				LsstVisitedTime= (long) ((HashMap)((HashMap)dataSnapshot.getValue()).get("LastReadTime")).get(preference.getStringFromPreference(Preference.STAFF_NUMBER,""));
			if((((HashMap)dataSnapshot.getValue()).containsKey("messages")))
				arrAllKeyData= new ArrayList<>(((HashMap)((HashMap)dataSnapshot.getValue()).get("messages")).keySet());
			for(int i=0;i<arrAllKeyData.size();i++)
			{   if((((HashMap)dataSnapshot.getValue()).containsKey("messages")))
				if(((long)((HashMap)((HashMap)((HashMap)dataSnapshot.getValue()).get("messages")).get(arrAllKeyData.get(i))).get("chatTime"))>LsstVisitedTime)
				{
					count++;
					hmCount.put(String.valueOf(((HashMap)((HashMap)((HashMap)dataSnapshot.getValue()).get("messages")).get(arrAllKeyData.get(i))).get("senderId")),count);
				}
			}
			return hmCount;
		}

		private void addEventListener(DatabaseReference root, final ChatGroupModel chatGroupModel) {
			root.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					Iterator i = dataSnapshot.getChildren().iterator();
					while (i.hasNext()){
						DataSnapshot childDataSnapshot = (DataSnapshot)i.next();
						try {
							HashMap<String,Object> data =((HashMap<String,Object>)(childDataSnapshot.getValue()));
							if((Long)data.get("chatTime")>lastReadTime.get(k))
							{
							}
							Count++;
						}catch (Exception e){
							e.printStackTrace();
						}
					}
					chatGroupModel.setUnreadMessages(Count);
					Count=0;
					k++;
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {

				}
			});

//			root.addValueEventListener(new ValueEventListener() {
//				@Override
//				public void onDataChange(DataSnapshot dataSnapshot) {
//
//				}
//
//				@Override
//				public void onCancelled(DatabaseError databaseError) {
//
//				}
//			});
		}


		@Override
		public void onCancelled(DatabaseError databaseError) {

		}
	}

	public interface ChatGroupFetchLishListener{
		void onChatGroups(TreeMap<Integer,ChatGroupModel> chatGroupModels);
	}


}
