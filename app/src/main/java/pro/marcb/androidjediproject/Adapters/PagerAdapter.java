package pro.marcb.androidjediproject.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import pro.marcb.androidjediproject.fragments.RankingPage;


public class PagerAdapter extends FragmentPagerAdapter {


    private ArrayList<String> tabTitles;
    private Fragment tab = null;

    private ArrayList<Fragment> fragments;

    //creadora
    public PagerAdapter(FragmentManager fm, ArrayList<String> titles) {
        super(fm);
        tabTitles = (ArrayList<String>) titles.clone();
        fragments = new ArrayList<>();
        for (int i = 0; i < tabTitles.size(); ++i) {
            fragments.add(i, null);
        }
    }


    //crea las tabas, siempre tiene que retornar con el numero de tabs que queremos mostrar
    @Override
    public int getCount() {
        return tabTitles.size();
    }

    //Lanza el fragment asociado con el número de tab
    @Override
    public Fragment getItem(int position) {
        if (fragments.get(position) == null) {
            fragments.add(position, RankingPage.newInstance(Integer.valueOf(tabTitles.get(position))));
        }
        tab = fragments.get(position);
        //return tab;
        return RankingPage.newInstance(Integer.valueOf(tabTitles.get(position)));
    }

    //pone el nombre en cada tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Genera le título en función de la posición
        return tabTitles.get(position);
    }
}

