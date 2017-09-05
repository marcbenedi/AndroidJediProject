package pro.marcb.androidjediproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pro.marcb.androidjediproject.DB.MyDataBaseHelper;
import pro.marcb.androidjediproject.SupportClass.Constants;

public class Login extends AppCompatActivity {

    private final static String TAG = "MainActivity.class";

    //TODO: Login firebase o twitter
    private Context context;
    private Button logInButton;
    private TextView signUp;
    private EditText usernameTV, passwordTV;
    private SharedPreferences preferences;
    private MyDataBaseHelper myDataBaseHelper;
    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String username = usernameTV.getText().toString();
            String passowrd = passwordTV.getText().toString();

            if (username.isEmpty() || passowrd.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Fill all the fields before", Toast.LENGTH_LONG).show();
            } else {
                String stored_password = myDataBaseHelper.getPassword(username);
                if (stored_password == null || !stored_password.equals(passowrd)) {

                    Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
                    ImageView image = new ImageView(context);
                    image.setImageResource(R.drawable.login_incorrecte);

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(context).
                                    setMessage("No en mi guardia!").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).
                                    setView(image);
                    builder.create().show();
                } else {
                    //intent to MainActivity
                    //finish this activity
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(Constants.SHARED_PREFERENCES.USER_LOGGED, username);
                    editor.apply();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }

        }
    };
    private View.OnClickListener signInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), SignUp.class);
            startActivityForResult(i, Constants.SING_UP_ACTIVITY_RESULT.SignUpCODE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        context = this;
        String userlogged = preferences.getString(Constants.SHARED_PREFERENCES.USER_LOGGED, null);
        if (userlogged != null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void initUI() {

        logInButton = (Button) findViewById(R.id.loginButton);
        logInButton.setOnClickListener(loginClick);

        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnClickListener(signInClick);

        usernameTV = (EditText) findViewById(R.id.username);
        passwordTV = (EditText) findViewById(R.id.password);

        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Constants.SING_UP_ACTIVITY_RESULT.SignUpCODE) {
            String user = data.getStringExtra(Constants.SING_UP_ACTIVITY_RESULT.USERNAME_FIELD);
            String pass = data.getStringExtra(Constants.SING_UP_ACTIVITY_RESULT.PASSWORD_FIELD);

            usernameTV.setText(user);
            passwordTV.setText(pass);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //myDataBaseHelper.close();
    }


    @Override
    protected void onStart() {
        super.onStart();
        myDataBaseHelper = MyDataBaseHelper.getInstance(this);
    }
}
