package pro.marcb.androidjediproject.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.material.joanbarroso.flipper.CoolImageFlipper;

import java.util.ArrayList;
import java.util.Collections;

import pro.marcb.androidjediproject.DB.MyDataBaseHelper;
import pro.marcb.androidjediproject.R;
import pro.marcb.androidjediproject.SupportClass.Constants;
import pro.marcb.androidjediproject.SupportClass.ResponsiveGridView;

public class Memory extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private final String TAG = "Memory";
    private final int CONSTANT = 2;
    Drawable back;
    private CoolImageFlipper imageFlipper;
    private ResponsiveGridView responsiveGridView;
    private TextView num_columns_display;
    private TextView num_rows_display;
    private SeekBar num_rows;
    private SeekBar num_columns;
    private Button startGame;
    private View configuration;
    private ResponsiveGridView game;
    private int lastTagPressed;
    private int tries;
    private int nCards;
    private ArrayList<Integer> colors;
    private ArrayList<Boolean> found;
    private MyDataBaseHelper myDataBaseHelper;
    private boolean waiting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_memory, container, false);

        initUI(v);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        myDataBaseHelper = MyDataBaseHelper.getInstance(getContext());
        imageFlipper = new CoolImageFlipper(getContext());

        return v;
    }

    private void initUI(View v) {

        num_columns_display = (TextView) v.findViewById(R.id.display_num_cols);
        num_rows_display = (TextView) v.findViewById(R.id.display_num_rows);

        num_rows = (SeekBar) v.findViewById(R.id.num_rows);
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

    private void startGame() {

        configuration.setVisibility(View.GONE);
        game.setVisibility(View.VISIBLE);

        game.setmColCount(getNumColumns());
        game.setmRowCount(getNumRows());

        //To remove all child views
        game.removeAllViews();
        waiting = false;

        lastTagPressed = -1;
        tries = 0;
        colors = new ArrayList<>();
        found = new ArrayList<>();
        nCards = getNumColumns() * getNumRows();

        back = getResources().getDrawable(R.drawable.back);

        for (int i = 0; i < nCards; ++i) {
            int id = (int) (Math.random() * (nCards / 2));
            while (Collections.frequency(colors, id) > 1) {
                id = (int) (Math.random() * (nCards / 2));
                Log.v(TAG, String.valueOf(id));
            }
            colors.add(id);
            found.add(false);
        }

        for (int i = 0; i < nCards; ++i) {
            ImageView v = new ImageView(getContext());
            v.setOnClickListener(this);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            v.setImageDrawable(back);

            game.addView(v);
            game.getChildAt(i).setTag(i);
        }

    }


    private int getNumColumns() {
        return num_columns.getProgress() + CONSTANT;
    }

    private int getNumRows() {
        return num_rows.getProgress() + CONSTANT;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.num_rows:
                if (getNumColumns() % 2 != 0 && progress % 2 != 0) {
                    num_rows.setProgress(--progress);
                }
                num_rows_display.setText(String.valueOf(progress + CONSTANT));
                break;
            case R.id.num_cols:
                if (getNumRows() % 2 != 0 && progress % 2 != 0) {
                    num_columns.setProgress(--progress);
                }
                num_columns_display.setText(String.valueOf(progress + CONSTANT));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onClick(final View v) {
        int tag = (int) v.getTag();
        if (lastTagPressed == tag) return;
        if (lastTagPressed == -1 && !found.get(tag) && !waiting) {
            lastTagPressed = tag;
            imageFlipper.flipImage(R.color.colorAccent + colors.get(tag) * 3000, (ImageView) v);
        } else if (!waiting && !found.get(tag)
                && R.color.colorAccent + colors.get(tag) * 3000 == R.color.colorAccent + colors.get(lastTagPressed) * 3000
                ) {
            ++tries;
            found.set(lastTagPressed, true);
            imageFlipper.flipImage(R.color.colorAccent + colors.get(tag) * 3000, (ImageView) v);
            lastTagPressed = -1;
            found.set(tag, true);
        } else if (!found.get(tag) && !waiting) {
            imageFlipper.flipImage(R.color.colorAccent + colors.get(tag) * 3000, (ImageView) v);
            ++tries;
            waiting = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    waiting = false;
                    imageFlipper.flipImage(back, (ImageView) v);
                    ImageView v = (ImageView) game.getChildAt(lastTagPressed);
                    lastTagPressed = -1;
                    imageFlipper.flipImage(back, v);
                }
            }, 2000);
        }


        //See if the game has finished
        boolean fin = true;
        for (int i = 0; i < found.size(); ++i) {
            if (!found.get(i)) {
                fin = found.get(i);
            }
        }

        if (fin) {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            final String username = sharedPreferences.getString(Constants.SHARED_PREFERENCES.USER_LOGGED, null);

            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Your puntuation is " + String.valueOf(tries))
                    .setTitle("Game finished!");
            builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    game.setVisibility(View.GONE);
                    configuration.setVisibility(View.VISIBLE);

                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    game.setVisibility(View.GONE);
                    configuration.setVisibility(View.VISIBLE);
                }
            });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
            myDataBaseHelper.insertScore(username, tries, nCards);
        }

    }

}
