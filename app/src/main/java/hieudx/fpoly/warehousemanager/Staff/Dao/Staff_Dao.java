package hieudx.fpoly.warehousemanager.Staff.Dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.Staff.Model.Staff;

public class Staff_Dao {
    private DBHelper dbHelper;

    private Context context;

    SQLiteDatabase db;

    public Staff_Dao(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Staff> getStaffList(){
        ArrayList<Staff> listS = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("Select * from Staff", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                listS.add(new Staff(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getInt(4),c.getInt(5),c.getInt(6)));
            } while (c.moveToNext());
        }
        return listS;
    }

    public Staff getStaffById(int id) {
        Staff objStaff = new Staff();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Staff WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            objStaff = new Staff(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getInt(4),c.getInt(5),c.getInt(6));
        }
        return objStaff;
    }

    public boolean insertStaff(Staff s){
        ContentValues values = new ContentValues();
        values.put("name",s.getName());
        values.put("phone",s.getPhone());
        values.put("address",s.getAddress());
        values.put("work_day",s.getWork_day());
        values.put("salary",s.getSalary());
        values.put("coefficient",s.getCoefficient());
        long check = db.insert("Staff",null,values);
        return check > 0;
    }

    public boolean updateStaff(Staff s){
        ContentValues values = new ContentValues();
        values.put("name",s.getName());
        values.put("phone",s.getPhone());
        values.put("address",s.getAddress());
        values.put("work_day",s.getWork_day());
        values.put("salary",s.getSalary());
        values.put("coefficient",s.getCoefficient());
        String[] id = new String[]{String.valueOf(s.getId())};
        long check = db.update("Staff",values,"id=?",id);
        return check>0;
    }

    public int deleteStaff(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM Staff WHERE id = ?",new String[]{String.valueOf(id)});
        long check = db.delete("Staff","id = ?",new String[]{String.valueOf(id)});
        if (check== -1){
            return 0;
        }else {
            return 1;
        }
    }
}
