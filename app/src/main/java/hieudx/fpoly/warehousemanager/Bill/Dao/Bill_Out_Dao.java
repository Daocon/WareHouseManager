package hieudx.fpoly.warehousemanager.Bill.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_Out;
import hieudx.fpoly.warehousemanager.Bill.Model.Bill_out_detail;

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
                list.add(new Bill_Out(c.getString(0), c.getString(1), c.getInt(2),c.getString(3), c.getInt(4), c.getInt(5)));
            } while (c.moveToNext());
        }
        return list;
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

    public ArrayList<Bill_out_detail> getListProductDetail(String id_bill_out) {
        ArrayList<Bill_out_detail> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT *,price * quantity AS total FROM Bill_out_detail WHERE id_bill_out = ?", new String[]{id_bill_out});
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Bill_out_detail(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5)));
            } while (c.moveToNext());
        }
        return list;
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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
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
