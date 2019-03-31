package com.offerup.newtest;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class WorkoutActivity extends AppCompatActivity {




    private TabLayout tabLayout;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);



        viewPager = (ViewPager) findViewById(R.id.vPager);
        tabLayout = (TabLayout)findViewById(R.id.tabToolbar);
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AbsTab(), "Abs");
        adapter.addFragment(new LegsTab(), "Legs");



        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

}}
