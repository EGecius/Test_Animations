package com.example.egidijusgecius.test_animations;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Single Responsibility:
 *
 * //todo
 */
public class ChildActivity extends AppCompatActivity {

	private View logoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.child_activity);

		setUpActionBar();
		setActivityEnterTransition();
	}

	private void setUpActionBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		logoView = LayoutInflater.from(this).inflate(R.layout.itv_logo, null);
		toolbar.addView(logoView);
		setSupportActionBar(toolbar);
	}

	private void setActivityEnterTransition() {
		ViewCompat.setTransitionName(logoView, MainActivity.ITV_LOGO);
	}
}
