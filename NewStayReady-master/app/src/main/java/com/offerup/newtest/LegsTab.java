package com.offerup.newtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class LegsTab extends Fragment{





    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_legs, container, false);
        TabLayout tabLayoutLegs = (TabLayout) view.findViewById(R.id.tabLayout2);
        tabLayoutLegs.getTabAt(1).select();

        return view;

    }


}
