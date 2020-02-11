package com.alexflex.testyoursoftskills;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.alexflex.testyoursoftskills.adapters.MyFragmentAdapter;

public class PagerActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
    }
}
