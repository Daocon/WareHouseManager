package hieudx.fpoly.warehousemanager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.Supplier;

public class Supplier_Dao {
    private DBHelper dbHelper;
    private Context context;

    public Supplier_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public ArrayList<Supplier> getAll() {
        ArrayList<Supplier> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Supplier", null);
        if (c.getCount() != 0) { // nếu có dữ liệu
            c.moveToFirst(); // chuyển con trỏ về đầu bảng
            do {
//                hoặc c.getInt(c.getColumnIndex("id"))
                list.add(new Supplier(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)));
            } while (c.moveToNext()); // nếu chưa đến cuối bảng thì đi tiếp
        }
        return list;
    }

    public Supplier getSupplierById(int id) {
        Supplier supplier = new Supplier();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Supplier WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            supplier = new Supplier(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
        }
        return supplier;
    }

    public ArrayList<HashMap<String, Object>> getListSupplier() {
        ArrayList<Supplier> list = getAll();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>(); //tạo một arrList có kiểu là hashmap
        for (Supplier supplier : list) { // duyệt qua từng đối tượng trong arrList
            HashMap<String, Object> hs = new HashMap<>(); //mỗi lần lặp tạo ra 1 hashmap mới chứa cặp key-value
            hs.put("id", supplier.getId());
            hs.put("name", supplier.getName());
            listHM.add(hs);
        }
        return listHM;
    }
}
