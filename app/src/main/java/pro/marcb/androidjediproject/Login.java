package pro.marcb.androidjediproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pro.marcb.androidjediproject.DB.MyDataBaseHelper;
import pro.marcb.androidjediproject.SupportClass.Constants;

public class Login extends AppCompatActivity {

    private final static String TAG ="MainActivity.class";

    private Button logInButton;
    private TextView signUp;
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

        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnClickListener(signInClick);

        usernameTV = (EditText) findViewById(R.id.username);
        passwordTV = (EditText) findViewById(R.id.password);

        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        myDataBaseHelper = MyDataBaseHelper.getInstance(this);

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
                String stored_password = myDataBaseHelper.getPassword(username);
                if (stored_password == null || !stored_password.equals(passowrd)){
                    Toast.makeText(getApplicationContext(), "Incorrect username or password",Toast.LENGTH_LONG).show();
                }
                else {
                    //TODO: Store user logged in in shared preferences
                    //intent to MainActivity
                    //finish this activity
                }
            }

        }
    };

    private View.OnClickListener signInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: Intent to sing up activity
            Intent i = new Intent(getApplicationContext(),SignUp.class);
            startActivityForResult(i,Constants.SignUpCODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO quan retorni fer set de password i username
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDataBaseHelper.close();
    }
}
