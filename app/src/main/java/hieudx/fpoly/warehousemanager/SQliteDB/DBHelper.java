package hieudx.fpoly.warehousemanager.SQliteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static String DB_NAME = "WareHouseManager";
    static int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        tạo bảng thể loại sản phẩm
        db.execSQL("create table Category (id integer PRIMARY KEY AUTOINCREMENT, name text NOT NULL);");

//        tạo bảng user
        db.execSQL("create table User (id integer PRIMARY KEY AUTOINCREMENT, username text NOT NULL, password text NOT NULL, phone text NOT NULL, role int NOT NULL);");

//        tạo bảng product
        db.execSQL("create table Product (id integer PRIMARY KEY AUTOINCREMENT, name text NOT NULL, price integer NOT NULL," +
                " quantity_in integer NOT NULL, quantity_out integer DEFAULT(0) NOT NULL, date_in text NOT NULL," +
                " date_out text NOT NULL, id_category integer NOT NULL REFERENCES Category (id));");

//        tạo bảng bill
        db.execSQL("create table Bill (id integer PRIMARY KEY AUTOINCREMENT, date_time text NOT NULL, total integer NOT NULL, id_bill_detail integer NOT NULL REFERENCES Bill_Detail (id));");

//        tạo bảng bill_detail
        db.execSQL("create table Bill_Detail (id integer PRIMARY KEY AUTOINCREMENT, id_user integer NOT NULL REFERENCES User (id), id_product integer not NULL REFERENCES Product (id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
//            xóa bảng cũ nếu tồn tại
            db.execSQL("DROP TABLE IF EXISTS Category");
            db.execSQL("DROP TABLE IF EXISTS User");
            db.execSQL("DROP TABLE IF EXISTS Product");
            db.execSQL("DROP TABLE IF EXISTS Bill");
            db.execSQL("DROP TABLE IF EXISTS Bill_Detail");
            onCreate(db);
        }
    }
}
