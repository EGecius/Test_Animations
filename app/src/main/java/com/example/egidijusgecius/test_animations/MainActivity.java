package com.example.egidijusgecius.test_animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	public static final String ITV_LOGO = "ITV_LOGO";
	private View container;

	boolean isRotatedForward;
	private View blueCircle;
	private View greenCircle;
	private View orangeCircle;
	private View purpleCircle;
	private View logoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		container = findViewById(R.id.circles_container);

		blueCircle = findViewById(R.id.blue_circle);
		greenCircle = findViewById(R.id.green_circle);
		orangeCircle = findViewById(R.id.orange_circle);
		purpleCircle = findViewById(R.id.purple_circle);

		logoView = findViewById(R.id.itv_logo);

		findViewById(R.id.spin_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				animateCircling();
			}
		});
		findViewById(R.id.shared_element_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startSharedElimentTransition();
			}
		});
		findViewById(R.id.reveal_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				goToRevealActivity(view);
			}
		});
	}

	private synchronized void animateCircling() {

		float deviceDensity = getResources().getDisplayMetrics().density;
		float translationInDp = 60 /* px */ * deviceDensity;

		int delayMs = 300;
		int angle;

		if (!isRotatedForward) {
			angle = 360;
			isRotatedForward = true;
		} else {
			angle = -360;
			translationInDp = 0 - translationInDp;
			isRotatedForward = false;
		}

		ObjectAnimator animator = ObjectAnimator.ofFloat(container, View.ROTATION, angle);
		animator.setDuration(2000 /* millis */);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				startSharedElimentTransition();
			}
		});
		animator.start();

		blueCircle.animate().translationXBy(-translationInDp).translationYBy(-translationInDp).setStartDelay(delayMs);
		greenCircle.animate().translationXBy(translationInDp).translationYBy(-translationInDp).setStartDelay(delayMs * 2);
		orangeCircle.animate().translationXBy(translationInDp).translationYBy(translationInDp).setStartDelay(delayMs * 3);
		purpleCircle.animate().translationXBy(-translationInDp).translationYBy(translationInDp).setStartDelay(delayMs * 4);
	}

	private void startSharedElimentTransition() {
		final Intent intent = new Intent(this, ChildActivity.class);
		final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
			logoView, ITV_LOGO);
		ActivityCompat.startActivity(this, intent, options.toBundle());
	}

	private void goToRevealActivity(View view) {
		//todo since getX & getY give coordinate relative to the parent, they do not always tell where they view is
		int centerX = (int) (view.getX() + (view.getWidth() / 2));
		int centerY = (int) (view.getY() + (view.getHeight() / 2));

		RevealActivity.goToThisActivity(this, centerX, centerY);
	}
}
