package com.example.egidijusgecius.test_animations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Single Responsibility:
 *
 * //todo
 */
public class ExplodeActivity extends AppCompatActivity {

	private Button toChildBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.explode_activity);

		toChildBtn = (Button) findViewById(R.id.to_child_btn);
		toChildBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				goToChildActivity();
			}
		});

	}

	private void goToChildActivity() {
		startActivity(new Intent(this, ChildActivity.class));
	}
}
