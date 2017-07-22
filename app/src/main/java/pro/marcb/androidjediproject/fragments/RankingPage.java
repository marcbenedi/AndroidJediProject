package pro.marcb.androidjediproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pro.marcb.androidjediproject.R;
import pro.marcb.androidjediproject.SupportClass.Constants;

public class RankingPage extends Fragment {

    private RecyclerView recyclerView;
    private int numCards;

    public RankingPage(){}

    public static RankingPage newInstance(int numCards){
        RankingPage rankingPage = new RankingPage();
        Bundle args = new Bundle();
        args.putInt(Constants.RANKING_PAGE_FRAGMENT.NUM_CARDS,numCards);
        rankingPage.setArguments(args);
        return rankingPage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ranking_page, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        numCards = getArguments().getInt(Constants.RANKING_PAGE_FRAGMENT.NUM_CARDS);

        return v;
    }

    private void populateRecyclerView(){



    }

}
