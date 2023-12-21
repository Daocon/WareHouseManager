package hieudx.fpoly.warehousemanager.Bill.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hieudx.fpoly.warehousemanager.Bill.Model.Bill_Out;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_out_detail;
import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;

public class Bill_Out_Dao {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public Bill_Out_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Bill_Out> getAll() {
        ArrayList<Bill_Out> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM Bill_out", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Bill_Out(c.getString(0), c.getString(1), c.getInt(2), c.getString(3), c.getInt(4), c.getInt(5)));
            } while (c.moveToNext());
        }
        return list;
    }

    public ArrayList<Bill_out_detail> getListProductDetail(String id_bill_out) {
        ArrayList<Bill_out_detail> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT *,price * quantity AS total FROM Bill_out_detail WHERE id_bill_out = ?", new String[]{id_bill_out});
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Bill_out_detail(c.getInt(0), c.getInt(1), c.getInt(2), String.valueOf(c.getInt(3)), c.getInt(4), c.getString(5)));
            } while (c.moveToNext());
        }
        return list;
    }

    public boolean insertDetail(Bill_out_detail bill_out_detail) {
        ContentValues values = new ContentValues();
        values.put("price", bill_out_detail.getPrice());
        values.put("quantity", bill_out_detail.getQuantity());
        values.put("id_product", bill_out_detail.getId_product());
        values.put("id_bill_out", bill_out_detail.getId_bill_out());
        long check = db.insert("Bill_out_detail", null, values);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean insert(Bill_Out bill_out) {
        ContentValues values = new ContentValues();
        values.put("id", bill_out.getId());
        values.put("date_time", bill_out.getDate_time());
        values.put("address", bill_out.getAddress());
        values.put("id_user", bill_out.getId_user());
        values.put("id_delivery", bill_out.getId_delivery());
        long check = db.insert("Bill_out", null, values);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public void updateSumTotal(String id_bill_out) {
        db.execSQL("UPDATE Bill_out_detail SET total = price * quantity WHERE id_bill_out = ?", new String[]{id_bill_out});
        db.execSQL("UPDATE Bill_out SET sum = (SELECT IFNULL(SUM(total), 0) FROM Bill_out_detail WHERE Bill_out_detail.id_bill_out = Bill_out.id)");
    }

    @SuppressLint("Range")
    public String getSumTotal(String id_bill_out) {
        String sum = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE Bill_out_detail SET total = price * quantity WHERE id_bill_out = ?", new String[]{id_bill_out});

        Cursor c = db.rawQuery("SELECT SUM(total) AS total_sum FROM Bill_out_detail WHERE id_bill_out = ?", new String[]{id_bill_out});
        if (c.moveToFirst()) {
            Locale locale = new Locale("vi", "VN");
            NumberFormat nf = NumberFormat.getInstance(locale);
            sum = nf.format(c.getInt(c.getColumnIndex("total_sum")));
        }
        return sum;
    }

    public int delete(String id_bill_out) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("Bill_out", "id = ?", new String[]{id_bill_out});
        if (check == 0)
            return -1;

        long check2 = db.delete("Bill_out_detail", "id_bill_out = ?", new String[]{id_bill_out});
        if (check2 == 0)
            return -1;
        return 1;
    }

    // get monthly totals
    @SuppressLint("Range")
    public List<Pair<String, Float>> getMonthlyTotals(String year) {
        List<Pair<String, Float>> monthlyTotals = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT strftime('%Y-%m', substr(Bill_out.date_time, 7, 4) || '-' || substr(Bill_out.date_time, 4, 2) || '-' || substr(Bill_out.date_time, 1, 2)) as Month, SUM(Bill_out_detail.total) as Total " +
                "FROM Bill_out JOIN Bill_out_detail ON Bill_out.id = Bill_out_detail.id_bill_out " +
                "WHERE strftime('%Y', substr(Bill_out.date_time, 7, 4) || '-' || substr(Bill_out.date_time, 4, 2) || '-' || substr(Bill_out.date_time, 1, 2)) = ? " +
                "GROUP BY Month", new String[]{year});
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

    // get all years
    @SuppressLint("Range")
    public List<String> getAllYears() {
        List<String> years = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT DISTINCT strftime('%Y', substr(Bill_out.date_time, 7, 4) || '-' || substr(Bill_out.date_time, 4, 2) || '-' || substr(Bill_out.date_time, 1, 2)) as Year " +
                "FROM Bill_out", null);
        if (cursor.moveToFirst()) {
            do {
                String year = cursor.getString(cursor.getColumnIndex("Year"));
                years.add(year);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return years;
    }
}
