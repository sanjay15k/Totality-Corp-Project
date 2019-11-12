package com.nsut.demo.totalitycorpproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class CandyBustActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private static int index = 0;

    private LinearLayout downloadLinearLayout;
    private ImageView cancelImageView;
    private View downloadView;
    private TextView downloadTextView;
    private TextView sizeTextView;
    private TextView downloadingTextView;
    private LinearLayout playButtonLinearLayout;
    private Animation slideUp;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout layoutBottomSheet;
    private Button nextButton;
    private LinearLayout mainLayoutBottom;
    private LinearLayout walletLayout;
    private LinearLayout confirmLayout;

    private Animation zoomOut;
    private Animation moveLeftSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_candy_bust);

        index = 0;
        downloadLinearLayout = findViewById(R.id.downloadLinearLayout);
        cancelImageView = findViewById(R.id.cancelImageView);
        downloadView = findViewById(R.id.downloadView);
        downloadTextView = findViewById(R.id.downloadTextView);
        sizeTextView = findViewById(R.id.sizeTextView);
        downloadingTextView = findViewById(R.id.downloadingTextView);
        playButtonLinearLayout = findViewById(R.id.playButtonLinearLayout);
        layoutBottomSheet = findViewById(R.id.bottom_sheet);

        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        moveLeftSecond = AnimationUtils.loadAnimation(this, R.anim.move_left_second);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setPeekHeight(0);

        nextButton = findViewById(R.id.nextButton);
        mainLayoutBottom = findViewById(R.id.mainLayoutBottom);
        walletLayout = findViewById(R.id.walletLayout);
        confirmLayout = findViewById(R.id.confirmLayout);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Hello "+index);
                if(index==0){
                    mainLayoutBottom.setAnimation(zoomOut);

                    mainLayoutBottom.setVisibility(View.GONE);
                    confirmLayout.setVisibility(View.VISIBLE);
                    confirmLayout.setAnimation(moveLeftSecond);

                }
                else if(index==1) {
                    walletLayout.setAnimation(zoomOut);
                    walletLayout.setVisibility(View.GONE);
                    confirmLayout.setVisibility(View.VISIBLE);
                    nextButton.setText("Confirm");
                    nextButton.setBackgroundTintList((ColorStateList.valueOf(Color.parseColor("#424242"))));
                    confirmLayout.setAnimation(moveLeftSecond);
                }
                index++;
            }
        });

        downloadLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelImageView.setImageResource(R.drawable.cross_icon);
                cancelImageView.setVisibility(View.VISIBLE);
                downloadTextView.setVisibility(View.GONE);
                sizeTextView.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        downloadingTextView.setVisibility(View.VISIBLE);
                        int width = downloadLinearLayout.getWidth();
                        expand(downloadView, 2000, width);
                        downloadView.setVisibility(View.VISIBLE);
                    }
                },250);
            }
        });

    }

    public void expand(final View v, int duration, int targetWidth) {
        int startWidth  = targetWidth/5;
        v.setVisibility(View.VISIBLE);
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(startWidth, targetWidth);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().width = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                valueAnimator.pause();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        valueAnimator.resume();
                        downloadingTextView.setVisibility(View.GONE);
                        cancelImageView.setImageResource(R.drawable.completed_icon);

                        downloadingTextView.setVisibility(View.GONE);
                        cancelImageView.setVisibility(View.GONE);
                        downloadView.setVisibility(View.GONE);
                        downloadLinearLayout.setVisibility(View.GONE);

                        playButtonLinearLayout.setVisibility(View.VISIBLE);
                        playButtonLinearLayout.startAnimation(slideUp);

                        playButtonLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            }
                        });
                    }
                }, 1000);
            }
        }, 900);
    }
}
