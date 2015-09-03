package com.example.egidijusgecius.test_animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import static android.view.ViewAnimationUtils.createCircularReveal;

/**
 * Single Responsibility:
 *
 * //todo
 */
public class RevealActivity extends AppCompatActivity {

	public static final String REVEAL_ANIMATION_START_X = "REVEAL_ANIMATION_START_X";
	public static final String REVEAL_ANIMATION_START_Y = "REVEAL_ANIMATION_START_Y";
	private View logoContainer;
	private View root;
	private int activityRevealCenterX;
	private int activityRevealCenterY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reveal_activity);
		extractExtras();

		root = findViewById(R.id.root_container);
		Button showBtn = (Button) findViewById(R.id.show_btn);
		showBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showRevealAnimation(logoContainer);
			}
		});
		Button hideBtn = (Button) findViewById(R.id.hide_btn);
		hideBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				reverseRevealAnimation(logoContainer);
			}
		});
		logoContainer = findViewById(R.id.logo_container);

		setUpActionBar();
		revealActivity();
	}

	private void extractExtras() {
		Bundle extras = getIntent().getExtras();
		activityRevealCenterX = extras.getInt(REVEAL_ANIMATION_START_X);
		activityRevealCenterY = extras.getInt(REVEAL_ANIMATION_START_Y);
	}

	private void setUpActionBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		setSupportActionBar(toolbar);
	}

	private void revealActivity() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			root.setVisibility(View.VISIBLE);
			return;
		}
		revealActivityWithAnimation();
	}

	@TargetApi (Build.VERSION_CODES.LOLLIPOP)
	private void revealActivityWithAnimation() {

		ViewTreeObserver viewTreeObserver = root.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
			viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					root.setVisibility(View.VISIBLE);
					animate();
				}

				private void animate() {
					//todo a hack added to compensate for a bit of missing height
					int endRadius = Math.max(root.getWidth(), root.getHeight()) + 100;
					Animator animator = createCircularReveal(root, activityRevealCenterX, activityRevealCenterY, 0, endRadius);
					animator.setDuration(1000 /*ms*/);
					animator.start();
				}
			});
		}
	}

	private void showRevealAnimation(View view) {

		int width = view.getWidth();
		int height = view.getHeight();

		int centerX = width / 2;
		int centerY = height / 2;
		int endRadius = Math.max(width, height);
		Animator animator = createCircularReveal(view, centerX, centerY, 0, endRadius);
		animator.setDuration(3000);
		view.setVisibility(View.VISIBLE);
		animator.start();
	}

	private void reverseRevealAnimation(final View view) {
		int width = view.getWidth();
		int height = view.getHeight();

		int centerX = width / 2;
		int centerY = height / 2;
		int startRadius = Math.max(width, height);

		Animator animator = createCircularReveal(view, centerX, centerY, startRadius, 0);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				view.setVisibility(View.INVISIBLE);
			}
		});
		animator.start();
	}

	/**
	 *
	 * @param origin
	 * @param centerX x coordinate on the screen where to start reveal animation
	 * @param centerY y coordinate on the screen where to start reveal animation
	 */
	public static void goToThisActivity(Activity origin, int centerX, int centerY) {
		Intent intent = new Intent(origin, RevealActivity.class);
		intent.putExtra(REVEAL_ANIMATION_START_X, centerX);
		intent.putExtra(REVEAL_ANIMATION_START_Y, centerY);
		origin.startActivity(intent);
	}
}
