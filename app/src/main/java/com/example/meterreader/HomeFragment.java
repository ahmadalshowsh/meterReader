package com.example.meterreader;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //costmer varaible
    TextInputLayout txtLayInMeterNo,txtLayInRead;
    private Button  btnGetImageRead ,btnsave ,btnCancel;
    private EditText txtmeterNo,txtCustomerName , txtcustRead;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;
    ReaderDB readerDB ;
    ArrayList<String> custid, custname , custmeter,custread;
    String meterno;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        txtmeterNo = (EditText) v.findViewById(R.id.txtMeterNo);
        txtcustRead = (EditText) v.findViewById(R.id.txtReadNo);
        txtCustomerName = (EditText) v.findViewById(R.id.txtCustomerName);
        btnGetImageRead = (Button) v.findViewById(R.id.btnGetImageRead);
        readerDB = new ReaderDB(getActivity());
        btnsave = (Button) v.findViewById(R.id.btnsave);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        txtLayInMeterNo = v.findViewById(R.id.txtlayInMeterNo);
        txtLayInRead= v.findViewById(R.id.txtLayInRead);
        custid = new ArrayList<>();
        custname = new ArrayList<>();
        custmeter = new ArrayList<>();
        custread = new ArrayList<>();



        txtLayInMeterNo.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dispatchTakePictureIntent();
            }
        });
        txtLayInRead.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dispatchTakePictureIntent();
            }
        });

        txtmeterNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_SEARCH)
                {

                    meterno=txtmeterNo.getText().toString();
                    dispalyCustData(meterno);

                    return  true;
                }
                return false;
            }
        });


        btnGetImageRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                meterno=txtmeterNo.getText().toString();
                dispalyCustData(meterno);



            }
        });


        btnsave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                if (txtmeterNo.getText().toString().equals("") || txtCustomerName.getText().toString().equals("") || txtcustRead.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "fill blanck first ", Toast.LENGTH_LONG).show();

                }else
                {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
                    DateFormat dateFormat = DateFormat.getDateInstance();
                    LocalDate date =LocalDate.now();




                    String Name = txtCustomerName.getText().toString();
                    String meterno = txtmeterNo.getText().toString();
                    String read = txtcustRead.getText().toString();

                     readerDB.UpdatetCustdata(meterno, read ,date.toString());
                     Toast.makeText(getActivity(), "Cutmoner have been Updated "+ date, Toast.LENGTH_LONG).show();
                }




            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtmeterNo.setText("");
                txtCustomerName.setText("");
                txtcustRead.setText("");
            }
        });




        return v;
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
           Toast.makeText(getActivity(),"error "+ e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

            detectTextformImage();

        }
    }




    private void detectTextformImage()
    {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
        firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText)
            {
                displayTextFromImage(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
               Toast.makeText(getActivity(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error " , e.getMessage());
            }
        });
    }

    private void displayTextFromImage(FirebaseVisionText firebaseVisionText)
    {
        List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks();
        if(blockList.size()==0)
        {
            Toast.makeText(getActivity() , "لا يوجد نص في الصوره الرجاء المحاوله مرخ اخرئ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(FirebaseVisionText.Block block  : firebaseVisionText.getBlocks())
            {
                String text = block.getText();
                txtmeterNo.setText(text);
                Toast.makeText(getActivity(),text,Toast.LENGTH_LONG).show();
            }

             meterno = txtmeterNo.getText().toString();
            dispalyCustData(meterno);
        }
    }



    void  dispalyCustData(String meterno)
    {
        Cursor cursor = readerDB.checkCustomer(meterno);

        if(cursor.getCount()==0)
        {
            Toast.makeText(getActivity(),"no data fond",Toast.LENGTH_LONG).show();
        }else
        {
            while (cursor.moveToNext())
            {
                custid.add(cursor.getString(0));
                custname.add(cursor.getString(1));
                custmeter.add(cursor.getString(2));
                custread.add(cursor.getString(3));

            }
            txtCustomerName.setText(custname.get(0));
        }
    }



}