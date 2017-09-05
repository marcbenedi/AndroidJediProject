package pro.marcb.androidjediproject.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import pro.marcb.androidjediproject.Adapters.PagerAdapter;
import pro.marcb.androidjediproject.DB.MyDataBaseHelper;
import pro.marcb.androidjediproject.R;
import pro.marcb.androidjediproject.SupportClass.MemoryScore;

public class Ranking extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyDataBaseHelper myDataBaseHelper;
    private Collection<MemoryScore> scores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ranking, container, false);

        myDataBaseHelper = MyDataBaseHelper.getInstance(getContext());
        scores = myDataBaseHelper.getScores();
        setHasOptionsMenu(true);
        ArrayList<String> titles = new ArrayList<>();
        //Inicialitzem els titols
        for (MemoryScore ms : scores) {
            String cards = ms.numCards;
            if (!titles.contains(cards)) titles.add(cards);
        }

        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager(), titles));

        // Give the TabLayout the ViewPager (material)
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);

        //normal color - selected color
        tabLayout.setTabTextColors(Color.DKGRAY, Color.BLACK);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, 55, 0, "Clear ranking");
    }
}
