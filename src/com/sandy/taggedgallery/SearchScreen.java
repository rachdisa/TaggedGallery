package com.sandy.taggedgallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class SearchScreen extends Activity {
	private EditText search_field;
	private Button search_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_screen);

		search_field = (EditText) findViewById(R.id.search_edit);
		search_button = (Button) findViewById(R.id.search_button);

		search_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String search_string = search_field.getText().toString()
						.toLowerCase();
				if (search_string != null && search_string.length() != 0) {
					Intent i = new Intent(SearchScreen.this, MainActivity.class);
					i.putExtra("SEARCH_TAG", search_string);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(),
							"Invalid Search String", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
