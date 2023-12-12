package hieudx.fpoly.warehousemanager.Bill.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_In;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_in_detail;

public class Bill_In_Dao {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public Bill_In_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Bill_In> getAll() {
        ArrayList<Bill_In> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM Bill_in", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Bill_In(c.getString(0), c.getString(1), c.getInt(2), c.getInt(3)));
            } while (c.moveToNext());
        }
        return list;
    }

    public ArrayList<Bill_in_detail> getListProductDetail(String id_bill_in) {
        ArrayList<Bill_in_detail> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT *,price * quantity AS total FROM Bill_in_detail WHERE id_bill_in = ?", new String[]{id_bill_in});
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Bill_in_detail(c.getInt(0), c.getInt(1), c.getInt(2), String.valueOf(c.getInt(3)), c.getInt(4), c.getString(5)));
            } while (c.moveToNext());
        }
        return list;
    }

    public boolean insertDetail(Bill_in_detail bill_in_detail) {
        ContentValues values = new ContentValues();
        values.put("price", bill_in_detail.getPrice());
        values.put("quantity", bill_in_detail.getQuantity());
        values.put("id_product", bill_in_detail.getId_product());
        values.put("id_bill_in", bill_in_detail.getId_bill_in());
        long check = db.insert("Bill_in_detail", null, values);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean insert(Bill_In bill_in) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", bill_in.getId());
        values.put("date_time", bill_in.getDate_time());
        values.put("id_user", bill_in.getId_user());
        long check = db.insert("Bill_in", null, values);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public void updateSumTotal(String id_bill_in) {
        db.execSQL("UPDATE Bill_in_detail SET total = price * quantity WHERE id_bill_in = ?", new String[]{id_bill_in});
        db.execSQL("UPDATE Bill_in SET sum = (SELECT IFNULL(SUM(total), 0) FROM Bill_in_detail WHERE Bill_in_detail.id_bill_in = Bill_in.id)");
    }

    public String genarateIdBillIn(int listSize) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMM", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        SharedPreferences shared = context.getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        int user_id = shared.getInt("id", 0);
        String generatedId = "PN_" + currentDate + "_" + user_id + (listSize + 1);
        return generatedId;
    }


    //    check trả về số hàng bị xóa
    //    1:xóa thành công, -1: thất bại
    public int delete(String id_bill_in) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("Bill_in", "id = ?", new String[]{id_bill_in});
        if (check == 0)
            return -1;

        long check2 = db.delete("Bill_in_detail", "id_bill_in = ?", new String[]{id_bill_in});
        if (check2 == 0)
            return -1;
        return 1;
    }

    @SuppressLint("Range")
    public List<Pair<String, Float>> getMonthlyTotals() {
        List<Pair<String, Float>> monthlyTotals = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT strftime('%m', substr(Bill_in.date_time, 7, 4) || '-' || substr(Bill_in.date_time, 4, 2) || '-' || substr(Bill_in.date_time, 1, 2)) as Month, SUM(Bill_in_detail.total) as Total " +
                "FROM Bill_in JOIN Bill_in_detail ON Bill_in.id = Bill_in_detail.id_bill_in " +
                "GROUP BY Month", null);
        if (cursor.moveToFirst()) {
            do {
                String month = cursor.getString(cursor.getColumnIndex("Month"));
                Float total = cursor.getFloat(cursor.getColumnIndex("Total"));
                monthlyTotals.add(new Pair<>(month, total));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return monthlyTotals;
    }
}

