package pro.marcb.androidjediproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import pro.marcb.androidjediproject.R;
import pro.marcb.androidjediproject.SupportClass.Constants;

public class Profile extends Fragment {

    //TODO: Seleccionar imatge
    //TODO: Last notification
    //TODO: GPS

    private SharedPreferences preferences;
    private EditText username;
    private TextView lastNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        preferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String userlogged = preferences.getString(Constants.SHARED_PREFERENCES.USER_LOGGED, null);
        username = (EditText) v.findViewById(R.id.user_profile_name);
        username.setText(userlogged);

        lastNotification = (TextView) v.findViewById(R.id.last_notification);
        lastNotification.setText(preferences.getString(Constants.SHARED_PREFERENCES.LAST, "No hi ha notificació"));

        return v;
    }


    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES.USER_LOGGED, username.getText().toString());
        editor.apply();
    }
}






