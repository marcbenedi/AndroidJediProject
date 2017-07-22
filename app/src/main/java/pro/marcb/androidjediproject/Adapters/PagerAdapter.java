package pro.marcb.androidjediproject.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import pro.marcb.androidjediproject.fragments.RankingPage;


public class PagerAdapter extends FragmentPagerAdapter {


    private String tabTitles[];
    private Fragment tab = null;

    private ArrayList<Fragment> fragments;

    //creadora
    public PagerAdapter(FragmentManager fm, String titles[]) {
        super(fm);
        tabTitles = titles;
        fragments = new ArrayList<>();
        for (int i = 0; i < tabTitles.length; ++i){
            fragments.add(i,null);
        }
    }


    //crea las tabas, siempre tiene que retornar con el numero de tabs que queremos mostrar
    @Override
    public int getCount() {
        return tabTitles.length;
    }

    //Lanza el fragment asociado con el número de tab
    @Override
    public Fragment getItem(int position) {
        if (fragments.get(position)==null){
            fragments.add(position,RankingPage.newInstance(Integer.valueOf(tabTitles[position])));
        }
        tab = fragments.get(position);
        return tab;
    }

    //pone el nombre en cada tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Genera le título en función de la posición
        return tabTitles[position];
    }
}