package com.example.meterreader;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String[] permission;
    ReaderDB readerDB ;

    private Button btnlogout ;
    public boolean checklogout=false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_settings, container, false);

         btnlogout = view.findViewById(R.id.btnLogout);
        readerDB = new ReaderDB(getActivity());
        permission= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


         btnlogout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

//                 Intent loginactivity  = new Intent(getActivity(),LoginActivity.class);
//                 startActivity(loginactivity);
//                 checklogout=true;


                 if(StorgePermioin())
                     exportclientCSV();
                 else
                     PermissionExport();
             }


         });

         return view;

    }

private void exportclientCSV()
{
    File folder = new File(Environment.getExternalStorageDirectory()+"/"+"CleintCSV");
    boolean foldercree = false;
    if(!folder.exists())
    {
        foldercree=folder.mkdir();
    }
    Log.d("CSV_TAG","Export CSV "+foldercree);
    String csvfile = "client.csv";
    String path=folder+"/"+csvfile;
    ArrayList<XYValue> clientInfo = new ArrayList<XYValue>();
    clientInfo=readerDB.GetCustomerdatail();


    try {
        FileWriter fileWriter = new FileWriter(path);

        for (int i =0 ;i <clientInfo.size();i++)
        {

            fileWriter.append(clientInfo.get(i).getXCustname());
            fileWriter.append(",");


            fileWriter.append(clientInfo.get(i).getYMeterno());
            fileWriter.append(",");


            fileWriter.append(clientInfo.get(i).getZRead());
            fileWriter.append(",");


            fileWriter.append(clientInfo.get(i).getUReadDate());
            fileWriter.append("\n");
        }

        fileWriter.flush();
        fileWriter.close();


    }catch (Exception e)
    {

    }

}


private boolean StorgePermioin()
{
    boolean result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

    return result;

}

private void PermissionExport()
{
    ActivityCompat.requestPermissions(getActivity(), permission, 1);
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 1:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    exportclientCSV();
                }
                else
                {
                    Toast.makeText(getActivity(), "not ",Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }


    private void ImportClientCSV()
    {
        String fileNamePath = Environment.getExternalStorageDirectory()+"/"+"CleintCSV"+"/"+"client.csv";
         File csvfile = new File(fileNamePath);

        if (csvfile.exists())
        {
            try {
                CSVReader csvReader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                String[] ligins;
                while ((ligins=csvReader.readNext())!=null)
                {
                    String custName,meterNo,custread,readDate;
                    custName=ligins[0];
                    meterNo=ligins[1];
                    custread=ligins[2];
                    readDate=ligins[3];

                    readerDB.insertCustdata(custName,meterNo,custread,readDate);
                }

            }catch (Exception e)
            {

            }
        }
    }
}