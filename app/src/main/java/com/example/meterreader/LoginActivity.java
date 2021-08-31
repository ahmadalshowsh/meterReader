package com.example.meterreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtPass;
    private Button btnLogin;
    private Button btnLogout;
    ReaderDB readerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtName =(EditText) findViewById(R.id.Name);
        txtPass = (EditText)findViewById(R.id.Password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        readerDB = new ReaderDB(this);
        SettingsFragment c = new SettingsFragment();






        if(c.checklogout)
        {
            savelogoutPrefsData();
        }

     // check if has been login befor

        if(restorePrefData())
        {
            Intent mainActivity  = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(mainActivity);
            finish();
        }


//
//        String Name = "ahmad";
//        String Pass = "123";
//        boolean inser = readerDB.insertData( Name, Pass);
//        if (inser == true) {
//            Toast.makeText(LoginActivity.this, "User have been added ", Toast.LENGTH_LONG).show();
//        }
//




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name =txtName.getText().toString();
                String pass = txtPass.getText().toString();


                if (name.equals("") || pass.equals("")) {

                    Toast.makeText(LoginActivity.this, "fild all blanck", Toast.LENGTH_LONG).show();
                } else {
                    boolean checkpass = readerDB.checknamepassword(name, pass);
                    if (checkpass == true) {
                        savePrefsData();
                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);
                        finish();

                        Toast.makeText(LoginActivity.this, "successed", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(LoginActivity.this, "Faild", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });




    }

    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("saveLogin",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("login",false);
        return  isIntroActivityOpnendBefore;



    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("saveLogin",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("login",true);
        editor.commit();


    }

    private void savelogoutPrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("saveLogin",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("login",false);
        editor.commit();


    }

}