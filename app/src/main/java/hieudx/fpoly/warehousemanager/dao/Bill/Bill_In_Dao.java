package hieudx.fpoly.warehousemanager.dao.Bill;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.bill.Bill_In;
import hieudx.fpoly.warehousemanager.models.bill.Bill_product_in;

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
                list.add(new Bill_In(c.getString(0), c.getString(1), c.getInt(2), c.getInt(3)));
            } while (c.moveToNext());
        }
        return list;
    }

    public ArrayList<Bill_product_in> getListProductDetail(String id_bill_in) {
        ArrayList<Bill_product_in> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Bill_product_in WHERE id_bill_in = ?", new String[]{id_bill_in});
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Bill_product_in(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getString(5), c.getInt(6), c.getString(7)));
            } while (c.moveToNext());
        }
        return list;
    }

    @SuppressLint("Range")
    public String getSumTotal(String id_bill_in) {
        String sum = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE Bill_product_in SET total = price * quantity WHERE id_bill_in = ?", new String[]{String.valueOf(id_bill_in)});

        Cursor c = db.rawQuery("SELECT SUM(total) AS total_sum FROM Bill_product_in WHERE id_bill_in = ?", new String[]{String.valueOf(id_bill_in)});
        if (c.moveToFirst()) {
            Locale locale = new Locale("vi", "VN");
            NumberFormat nf = NumberFormat.getInstance(locale);
            sum = nf.format(c.getInt(c.getColumnIndex("total_sum")));
        }
        return sum;
    }

//    check trả về số hàng bị xóa
 //    1:xóa thành công, -1: thất bại
    public int delete(String id_bill_in) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("Bill_in", "id = ?", new String[]{id_bill_in});
        if (check == 0)
            return -1;

        long check2 =  db.delete("Bill_product_in", "id_bill_in = ?", new String[]{id_bill_in});
        if (check2 == 0)
            return -1;
        return 1;
    }


}

