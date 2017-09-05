package pro.marcb.androidjediproject.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import pro.marcb.androidjediproject.R;

public class MediaPlayer extends Fragment {

    private Button play, pause, stop;

    private android.media.MediaPlayer mediaPlayer;
    private State state;

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        mediaPlayer.stop();
        state = State.STOPED;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mediaplayer, container, false);
        play = (Button) view.findViewById(R.id.play);
        pause = (Button) view.findViewById(R.id.pause);
        stop = (Button) view.findViewById(R.id.stop);

        mediaPlayer = android.media.MediaPlayer.create(getActivity(), R.raw.canso_cutre);
        state = State.PREPARED;
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == State.STOPED) try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                state = State.STARTED;
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state != State.STOPED) {
                    mediaPlayer.pause();
                    state = State.PAUSED;
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                state = State.STOPED;
            }
        });


        return view;
    }

    private enum State {PREPARED, STARTED, PAUSED, STOPED}
}
