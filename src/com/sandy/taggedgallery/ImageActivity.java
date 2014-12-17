package com.sandy.taggedgallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sandy.staggeredgrid.ImageLoader;
import com.sandy.staggeredgrid.ScaleImageView;

public class ImageActivity extends Activity {

	ScaleImageView imageView;
	private ImageLoader mLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_view);
		imageView = (ScaleImageView) findViewById(R.id.full_image_view);
		mLoader = new ImageLoader(getApplicationContext());
		Intent i = getIntent();
		String url = i.getExtras().getString("url");
		if (url.length() > 0) {
			mLoader.DisplayImage(url, imageView,2);
		}
		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
