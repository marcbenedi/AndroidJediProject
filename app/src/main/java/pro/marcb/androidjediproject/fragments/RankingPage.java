package pro.marcb.androidjediproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pro.marcb.androidjediproject.Adapters.RecyclerViewAdapter;
import pro.marcb.androidjediproject.DB.MyDataBaseHelper;
import pro.marcb.androidjediproject.R;
import pro.marcb.androidjediproject.SupportClass.Constants;
import pro.marcb.androidjediproject.SupportClass.MemoryScore;

public class RankingPage extends Fragment {

    private RecyclerView recyclerView;
    private int numCards;
    private MyDataBaseHelper myDataBaseHelper;
    private ArrayList<MemoryScore> scores;
    private LinearLayoutManager myLayoutManager;
    private RecyclerViewAdapter adapter;

    public RankingPage() {
    }

    public static RankingPage newInstance(int numCards) {
        RankingPage rankingPage = new RankingPage();
        Bundle args = new Bundle();
        args.putInt(Constants.RANKING_PAGE_FRAGMENT.NUM_CARDS, numCards);
        rankingPage.setArguments(args);
        return rankingPage;
    }

    //TODO: Clear ranking
    //TODO: Es pert la info

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ranking_page, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        setHasOptionsMenu(true);
        numCards = getArguments().getInt(Constants.RANKING_PAGE_FRAGMENT.NUM_CARDS);

        myDataBaseHelper = MyDataBaseHelper.getInstance(getContext());

        ArrayList<MemoryScore> temporal = (ArrayList) myDataBaseHelper.getScores();
        scores = new ArrayList<>();

        for (MemoryScore ms : temporal) {
            if (Integer.valueOf(ms.numCards) == numCards) scores.add(ms);
        }

        adapter = new RecyclerViewAdapter(scores);
        myLayoutManager = new LinearLayoutManager(inflater.getContext());
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return v;
    }

    private void sortByScore(ArrayList<MemoryScore> scores) {

        Collections.sort(scores, new Comparator<MemoryScore>() {
            @Override
            public int compare(MemoryScore b1, MemoryScore b2) {
                /*int a1 = Integer.valueOf(b1.score);
                int a2 = Integer.valueOf(b2.score);
                return  a1 < a2? 0 : 1;*/
                return b1.score.compareTo(b2.score);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 55) {
            Log.v("Raning", "funciona");
            myDataBaseHelper.deleteScores();
            scores = new ArrayList<>();
            adapter = new RecyclerViewAdapter(scores);
            recyclerView.setAdapter(adapter);

        }

        return super.onOptionsItemSelected(item);
    }

}
