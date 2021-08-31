package com.example.meterreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class ReaderDB extends SQLiteOpenHelper {

    private static final String database_name ="meterDB.db";
    private static final int database_Version =6;

    //table users
    private static final String TB_User ="User";

     String DROP_TABLE ="DROP TABLE IF EXISTS";


    //table Customer
    private static final String TB_Customer="Customer" ;

    private Context context;

    public ReaderDB(@Nullable Context context) {
        super(context, database_name, null, database_Version);
        this.context = context;
//        Toast.makeText(context, "this constractor", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String TB_customer = "CREATE TABLE " + TB_Customer + " (cust_id INTEGER PRIMARY KEY AUTOINCREMENT ,cust_name TEXT , meter_no TEXT , read TEXT , read_date TEXT);";
        String TB_users =    "CREATE TABLE " + TB_User + " (user_id INTEGER PRIMARY KEY AUTOINCREMENT , user_name TEXT , password TEXT );";
        try {
            db.execSQL(TB_users);
            db.execSQL(TB_customer);
            Toast.makeText(context, "Table has been created : ", Toast.LENGTH_SHORT).show();

        } catch (SQLiteException e) {
            Toast.makeText(context, "Error : " + e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DROP_TABLE +TB_User);
            db.execSQL(DROP_TABLE +TB_Customer);
            onCreate(db);
            Toast.makeText(context, "Table has been Droped : ", Toast.LENGTH_SHORT).show();

        } catch (SQLiteException e) {
            Toast.makeText(context, "Error : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean insertData( String username ,String userpassword )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name", username);
        contentValues.put("password", userpassword);
        long result = db.insert(TB_User, null, contentValues);

        if (result == -1) return false;
        else
            return true;

    }


    public boolean checkusername(String username )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_User + " where user_id = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checknamepassword(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_User + " where user_name = ? and  password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }


    public boolean insertCustdata(String custName, String meterNo, String meterRead, String readDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cust_name", custName);
        contentValues.put("meter_no", meterNo);
        contentValues.put("read", meterRead);
        contentValues.put("read_date",readDate);
        long result = db.insert(TB_Customer, null, contentValues);

        if (result == -1) return false;
        else
            return true;

    }

    public void UpdatetCustdata(String meterNo, String meterRead , String readDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + TB_Customer + " SET read = " + meterRead + " , read_date = "+ readDate + " where meter_no  = " +  meterNo + " ; ");



    }

    public Cursor checkCustomer(String meterno) {

        String query = "select * from " + TB_Customer + " where meter_no = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, new String[]{meterno});

        }
        return cursor;

    }

    public Cursor readall()
    {
            String query  = "SELECT * FROM " +TB_Customer;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;

            if(db !=null)
            {
                cursor   = db.rawQuery(query,null);
            }

            return cursor;
    }

    public ArrayList<XYValue> GetCustomerdatail()
    {
        ArrayList<XYValue> arcust= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select *  from "+TB_Customer,null);

        cursor.moveToNext();

        while (cursor.isAfterLast()==false)
        {
            XYValue xyValue = new XYValue(cursor.getString(cursor.getColumnIndex("cust_name")),cursor.getString(cursor.getColumnIndex("meter_no")),cursor.getString(cursor.getColumnIndex("read")),cursor.getString(cursor.getColumnIndex("read_date")));

        arcust.add(xyValue);

        cursor.moveToNext();
        }

        return arcust;

    }



    public boolean deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete FROM " +TB_Customer);

        return true;
    }



}
