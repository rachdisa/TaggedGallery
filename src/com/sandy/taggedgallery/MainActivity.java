package com.sandy.taggedgallery;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sandy.staggeredgrid.StaggeredAdapter;
import com.sandy.staggeredgrid.StaggeredGridView;

public class MainActivity extends Activity {

	private JSONObject instaData;
	private ArrayList<String> imageLinks = new ArrayList<String>();
	private StaggeredGridView gridView;
	int number = 0;
	String search_tag;
	RequestImagesTask request;
	StaggeredAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (getIntent().hasExtra("SEARCH_TAG")) {
			search_tag = getIntent().getStringExtra("SEARCH_TAG");
		} else
			search_tag = "selfie";
		request = new RequestImagesTask(
				"https://api.instagram.com/v1/tags/"
						+ search_tag
						+ "/media/recent?access_token=24376445.8ddc4e6.f43f38eec87e40e18ff8dcc44683371c",
				this);
		request.execute();
		gridView = (StaggeredGridView) this
				.findViewById(R.id.image_grid_view);
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		gridView.setItemMargin(margin);
		gridView.setPadding(margin, 0, margin, 0);
		gridView.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view,
					int position, long id) {
				Intent i = new Intent(MainActivity.this, ImageActivity.class);
				try {
					String url = instaData.getJSONArray("data")
							.getJSONObject(position).getJSONObject("images")
							.getJSONObject("standard_resolution")
							.getString("url");
					i.putExtra("url", url);
				} catch (JSONException e) {
					i.putExtra("url", "");
				}
				startActivity(i);
			}
		});
	}

	private class RequestImagesTask extends AsyncTask<Void, Void, Void> {
		private String url;

		public RequestImagesTask(String url, Context c) {
			super();
			this.url = url;
		}

		@Override
		protected Void doInBackground(Void... params) {
			instaData = WebInterface.requestWebService(url);
			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			try {
				Log.i("Sandeep",
						"No of images: "
								+ instaData.getJSONArray("data").length());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			imageLinks.clear();
			try {
				for (int i = 0; i < instaData.getJSONArray("data").length(); i++) {
					imageLinks.add(instaData.getJSONArray("data")
							.getJSONObject(i).getJSONObject("images")
							.getJSONObject("thumbnail").getString("url"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			String[] urls = new String[imageLinks.size()];
			imageLinks.toArray(urls);
			adapter = new StaggeredAdapter(MainActivity.this, R.id.imageView1,
					urls);
			gridView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
	}
}
