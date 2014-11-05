package com.example.protoimage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

//All referenced from http://stackoverflow.com/questions/13591944/load-images-in-a-gallery-and-elasticsearch

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	Bitmap bmImg;

	public static List<String> mThumbIds = new ArrayList<String>();

	public void addmThumbIds(String url){
		mThumbIds.add(url);
	}

	// Constructor
	public ImageAdapter(Context c){
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.size();
	}

	public Object getItem(int position) {
		return mThumbIds.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			downloadFile(imageView, mThumbIds.get(position));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new GridView.LayoutParams(135, 135));
			imageView.setPadding(0, 0, 1, 0);
		}
		else{
			imageView=(ImageView) convertView;
			downloadFile(imageView, mThumbIds.get(position));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new GridView.LayoutParams(135, 135));
			imageView.setPadding(0, 0, 1, 0);
		}
		return imageView;
	}
	void downloadFile(final ImageView imageView, String fileUrl) {

		AsyncTask<Object, Object, String> task = new AsyncTask<Object, Object, String>() {

			@Override
			protected String doInBackground(Object... params) {
				URL myFileUrl = null;
				try {
					myFileUrl = new URL((String) params[0]);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
					conn.setDoInput(true);
					conn.connect();
					InputStream is = conn.getInputStream();

					bmImg = BitmapFactory.decodeStream(is);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}
			protected void onPostExecute(String unused) {
				imageView.setImageBitmap(bmImg);
			}
		};
		task.execute(fileUrl);
	}
}
