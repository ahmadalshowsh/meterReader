package com.example.meterreader;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.text.FirebaseVisionText;
//import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.List;

//implements BottomNavigationView.OnNavigationItemSelectedListener
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{




    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadFragments(new HomeFragment());
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this );



    }


    public boolean loadFragments(androidx.fragment.app.Fragment fragment){

        if(fragment!=null){

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.perent_container, fragment)
                    .commit();

        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.miHome:
                fragment = new HomeFragment();
                break;
            case R.id.miSearch:
                fragment = new SearchkFragment();
                break;
            case R.id.miProfile:
                fragment = new ProfileFragment();
                break;
            case R.id.miSettings:
                fragment = new SettingsFragment();
                break;


        }



        return loadFragments(fragment);
    }



    @Override
    public void onBackPressed() {

        if (bottomNavigationView.getSelectedItemId()==R.id.miHome)
        {
            super.onBackPressed();
            finish();//exit from app
        }
        else {
            bottomNavigationView.setSelectedItemId(R.id.miHome);
        }

    }




}