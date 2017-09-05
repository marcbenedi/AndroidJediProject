package pro.marcb.androidjediproject.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pro.marcb.androidjediproject.R;
import pro.marcb.androidjediproject.SupportClass.MemoryScore;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AdapterViewHolder> {

    private ArrayList<MemoryScore> scores;

    public RecyclerViewAdapter(ArrayList<MemoryScore> scores) {
        this.scores = scores;
    }

    @Override
    public RecyclerViewAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        MemoryScore m = scores.get(position);
        holder.name.setText(m.username);
        holder.score.setText(m.score);
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView score;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.username);
            this.score = (TextView) itemView.findViewById(R.id.score);
        }
    }
}
