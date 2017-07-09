package pro.marcb.androidjediproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.material.joanbarroso.flipper.CoolImageFlipper;

import org.w3c.dom.Text;

import pro.marcb.androidjediproject.R;
import pro.marcb.androidjediproject.SupportClass.ResponsiveGridView;

public class Memory extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private final int CONSTANT = 2;

    private CoolImageFlipper imageFlipper;
    private ResponsiveGridView responsiveGridView;

    private TextView num_columns_display;
    private TextView num_rows_display;

    private SeekBar num_rows;
    private SeekBar num_columns;

    private Button startGame;

    private View configuration;
    private ResponsiveGridView game;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_memory, container, false);

        initUI(v);

        return v;
    }

    private void initUI(View v) {

        num_columns_display = (TextView) v.findViewById(R.id.display_num_cols);
        num_rows_display = (TextView) v.findViewById(R.id.display_num_rows);

        num_rows = (SeekBar)v.findViewById(R.id.num_rows);
        num_columns = (SeekBar) v.findViewById(R.id.num_cols);

        num_columns_display.setText(String.valueOf(getNumColumns()));
        num_rows_display.setText(String.valueOf(getNumRows()));

        startGame = (Button) v.findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        num_rows.setOnSeekBarChangeListener(this);
        num_columns.setOnSeekBarChangeListener(this);

        configuration = v.findViewById(R.id.configure);
        game = (ResponsiveGridView) v.findViewById(R.id.game);

    }

    private void startGame(){

        configuration.setVisibility(View.GONE);
        game.setVisibility(View.VISIBLE);

        game.setmColCount(getNumColumns());
        game.setmRowCount(getNumRows());

        game.removeAllViews();

    }

    private int getNumColumns() {return num_columns.getProgress()+CONSTANT;}
    private int getNumRows() {return num_rows.getProgress()+CONSTANT;}


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.num_rows:
                num_rows_display.setText(String.valueOf(progress+CONSTANT));
                break;
            case R.id.num_cols:
                num_columns_display.setText(String.valueOf(progress+CONSTANT));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
