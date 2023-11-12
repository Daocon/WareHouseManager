package hieudx.fpoly.warehousemanager.dao.Bill;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.bill.Bill_In;
import hieudx.fpoly.warehousemanager.models.bill.Bill_in_detail;

public class Bill_In_Dao {
    private DBHelper dbHelper;
    private Context context;

    public Bill_In_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public ArrayList<Bill_In> getAll() {
        ArrayList<Bill_In> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Bill_in", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Bill_In(c.getString(0), c.getString(1), c.getInt(2)));
            } while (c.moveToNext());
        }
        return list;
    }

    @SuppressLint("Range")
    public String getSumTotal(String id_bill_in) {
        String sum = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE Bill_in_detail SET total = price * quantity WHERE id_bill_in = ?", new String[]{String.valueOf(id_bill_in)});

        Cursor c = db.rawQuery("SELECT SUM(total) AS total_sum FROM Bill_in_detail WHERE id_bill_in = ?", new String[]{String.valueOf(id_bill_in)});
        if (c.moveToFirst()) {
            Locale locale = new Locale("vi", "VN");
            NumberFormat nf = NumberFormat.getInstance(locale);
            sum = nf.format(c.getInt(c.getColumnIndex("total_sum")));
        }
        return sum;
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

        long check2 = db.delete("Bill_product_in", "id_bill_in = ?", new String[]{id_bill_in});
        if (check2 == 0)
            return -1;
        return 1;
    }


}

