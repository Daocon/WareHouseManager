package hieudx.fpoly.warehousemanager.SQliteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static String DB_NAME = "WareHouseManager.db";
    static int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        tạo bảng phiếu nhập
        db.execSQL("CREATE TABLE Bill_in (\n" +
                "    id        INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    total     INTEGER NOT NULL,\n" +
                "    date_time INTEGER NOT NULL,\n" +
                "    id_user   INTEGER REFERENCES User (id) \n" +
                ");\n");

//        tạo bảng chi tiết phiếu nhập
        db.execSQL("CREATE TABLE Bill_in_detail (\n" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    id_product  INTEGER REFERENCES Product (id),\n" +
                "    id_supplier         REFERENCES Supplier (id) \n" +
                ");\n");

//        tạo bảng phiếu xuất
        db.execSQL("CREATE TABLE Bill_out (\n" +
                "    id        INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    total     INTEGER NOT NULL,\n" +
                "    date_time TEXT    NOT NULL,\n" +
                "    id_user   INTEGER REFERENCES user (id) \n" +
                ");\n");

//        tạo bảng chi tiết phiếu xuất
        db.execSQL("CREATE TABLE Bill_out_detail (\n" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    [add]       TEXT    NOT NULL,\n" +
                "    id_product          REFERENCES Product (id),\n" +
                "    id_delivery         REFERENCES Delivery (id) \n" +
                ");\n");

//        tạo bảng thể loại sản phẩm
        db.execSQL("CREATE TABLE Category (\n" +
                "    id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name TEXT    NOT NULL\n" +
                ");\n");

//        tạo bảng sản phẩm
        db.execSQL("CREATE TABLE Product (\n" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name        TEXT    NOT NULL,\n" +
                "    price       INTEGER NOT NULL,\n" +
                "    quantity    INTEGER NOT NULL,\n" +
                "    id_category INTEGER REFERENCES Category (id),\n" +
                "    sale_price  INTEGER NOT NULL\n" +
                ");\n");

//        tạo bảng đơn vị vận chuyển
        db.execSQL("CREATE TABLE Delivery (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    phone    INTEGER NOT NULL,\n" +
                "    price    INTEGER NOT NULL,\n" +
                "    tax_code TEXT    NOT NULL\n" +
                ");\n");

//        tạo bảng nhà cung cấp
        db.execSQL("CREATE TABLE Supplier (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    phone    INTEGER NOT NULL,\n" +
                "    [add]    TEXT    NOT NULL,\n" +
                "    tax_code TEXT    NOT NULL\n" +
                ");\n");

//        tạo bảng User
        db.execSQL("CREATE TABLE User (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT    NOT NULL,\n" +
                "    password TEXT    NOT NULL,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    email    TEXT    NOT NULL,\n" +
                "    phone    INTEGER NOT NULL,\n" +
                "    role     INTEGER NOT NULL\n" +
                ");\n");

//        tạo bảng nhân viên
        db.execSQL("CREATE TABLE Staff (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    phone    INTEGER NOT NULL,\n" +
                "    [add]    TEXT    NOT NULL,\n" +
                "    work_day INTEGER NOT NULL,\n" +
                "    salary   INTEGER NOT NULL\n" +
                ");\n");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
//            xóa bảng cũ nếu tồn tại
            db.execSQL("DROP TABLE IF EXISTS Bill_in");
            db.execSQL("DROP TABLE IF EXISTS Bill_in_detail");
            db.execSQL("DROP TABLE IF EXISTS Bill_out");
            db.execSQL("DROP TABLE IF EXISTS Bill_out_detail");
            db.execSQL("DROP TABLE IF EXISTS Category");
            db.execSQL("DROP TABLE IF EXISTS Product");
            db.execSQL("DROP TABLE IF EXISTS Delivery");
            db.execSQL("DROP TABLE IF EXISTS Supplier");
            db.execSQL("DROP TABLE IF EXISTS User");
            db.execSQL("DROP TABLE IF EXISTS Staff");
            onCreate(db);
        }
    }
}
