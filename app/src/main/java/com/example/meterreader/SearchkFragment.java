package com.example.meterreader;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    ArrayList<String> custid, custname , custmeter,custread,readdate;
    ReaderDB readerDB ;
    recycleViewAdapter recycleViewAdapter;
    RecyclerView recyclerView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchkFragment newInstance(String param1, String param2) {
        SearchkFragment fragment = new SearchkFragment();
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

        View view= inflater.inflate(R.layout.fragment_searchk, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        readerDB = new ReaderDB(getActivity());
        custid= new ArrayList<>();
        custname = new ArrayList<>();
        custmeter = new ArrayList<>();
        custread = new ArrayList<>();
        readdate = new ArrayList<>();


        dispalyCustData();

            recycleViewAdapter = new recycleViewAdapter(getActivity(),custid,custname,custmeter,custread,readdate);
         recyclerView.setAdapter(recycleViewAdapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;
    }


    void  dispalyCustData()
    {
        Cursor cursor = readerDB.readall();
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
                readdate.add(cursor.getString(4));

            }
        }
    }
}