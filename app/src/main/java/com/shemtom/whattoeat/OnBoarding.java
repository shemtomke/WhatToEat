package com.shemtom.whattoeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoarding extends AppCompatActivity {

    private ViewPager viewPager;
    private CardView next;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private SaveState saveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.viewPager);
        next = findViewById(R.id.nextCard);
        dotsLayout = findViewById(R.id.dotsLayout);
        saveState = new SaveState(this, "ob");

        if(saveState.getState() == 1)
        {
            startActivity(new Intent(OnBoarding.this, Dashboard.class));
            finish();
        }
        ObAdapter adapter = new ObAdapter(this);
        viewPager.setAdapter(adapter);

        GoNext();
        ManageScrolling();
        dotsFunction(0);
    }

    @SuppressLint("NewApi")
    private void dotsFunction(int pos) {
        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for (int i = 0; i< dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("-"));
            dots[i].setTextColor(getColor(R.color.grey));
            dots[i].setTextSize(40);

            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0)
        {
            dots[pos].setTextColor(getColor(R.color.yellow));
            dots[pos].setTextSize(40);
        }
    }

    void GoNext()
    {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1, true);
            }
        });
    }
    void ManageScrolling()
    {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotsFunction(position);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(position < 3)
                        {
                            viewPager.setCurrentItem(position + 1, true);
                        }
                        else
                        {
                            saveState.setState(1);
                            startActivity(new Intent(OnBoarding.this, Dashboard.class));
                            finish();
                        }
                    }
                });
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}