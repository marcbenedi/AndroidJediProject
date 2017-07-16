package pro.marcb.androidjediproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pro.marcb.androidjediproject.DB.MyDataBaseHelper;
import pro.marcb.androidjediproject.SupportClass.Constants;

public class SignUp extends AppCompatActivity {


    private final static String TAG ="SignUpq.class";

    private Button logInButton;
    private TextView signUp,textNotAccount,title;
    private EditText usernameTV, passwordTV;
    private SharedPreferences preferences;
    private MyDataBaseHelper myDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }



    private void initUI(){

        logInButton = (Button) findViewById(R.id.loginButton);
        logInButton.setOnClickListener(loginClick);
        logInButton.setText("Create");

        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setVisibility(View.INVISIBLE);

        usernameTV = (EditText) findViewById(R.id.username);
        passwordTV = (EditText) findViewById(R.id.password);

        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        textNotAccount = (TextView) findViewById(R.id.no_account);
        textNotAccount.setVisibility(View.INVISIBLE);

        title = (TextView) findViewById(R.id.title);
        title.setText("Create a FREE account 100% REAL NO FAKE!");

    }

    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String username = usernameTV.getText().toString();
            String passowrd = passwordTV.getText().toString();

            if(username.isEmpty() || passowrd.isEmpty()){
                Toast.makeText(getApplicationContext(),"Fill all the fields before", Toast.LENGTH_LONG).show();
            }
            else {

                long result = myDataBaseHelper.createUser(username,passowrd);
                if (result == -1) {
                    Toast.makeText(getApplicationContext(), "Account could not be created",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Account created",Toast.LENGTH_LONG).show();
                    Intent data = new Intent();
                    data.putExtra(Constants.SING_UP_ACTIVITY_RESULT.USERNAME_FIELD,username);
                    data.putExtra(Constants.SING_UP_ACTIVITY_RESULT.PASSWORD_FIELD,passowrd);
                    setResult(RESULT_OK,data);
                    finish();
                }
            }

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        myDataBaseHelper = MyDataBaseHelper.getInstance(getApplication());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //myDataBaseHelper.close();
    }
}
