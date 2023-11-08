package hieudx.fpoly.warehousemanager.dao.Bill;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.bill.Bill_Out;

public class Bill_Out_Dao {
    private DBHelper dbHelper;
    private Context context;

    public Bill_Out_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public ArrayList<Bill_Out> getAll() {
        ArrayList<Bill_Out> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Bill_out", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Bill_Out(c.getString(0), c.getInt(1),c.getString(2),c.getInt(3)));
            } while (c.moveToNext());
        }
        return list;
    }
}
