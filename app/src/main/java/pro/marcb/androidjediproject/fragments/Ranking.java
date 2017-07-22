package pro.marcb.androidjediproject.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pro.marcb.androidjediproject.Adapters.PagerAdapter;
import pro.marcb.androidjediproject.R;

public class Ranking extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_ranking, container, false);

        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager(),new String[]{"64","6"}));

        // Give the TabLayout the ViewPager (material)
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs);

        //normal color - selected color
        tabLayout.setTabTextColors(Color.DKGRAY, Color.BLACK);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

}
