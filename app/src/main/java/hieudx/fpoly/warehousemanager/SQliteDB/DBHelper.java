package hieudx.fpoly.warehousemanager.SQliteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static String DB_NAME = "WareHouseManager.db";
    static int DB_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

//        tạo bảng phiếu nhập
        db.execSQL("CREATE TABLE Bill_in (\n" +
                "    id                  TEXT PRIMARY KEY,\n" +
                "    date_time           TEXT NOT NULL,\n" +
                "    id_user             INTEGER REFERENCES User (id), \n" +
                "    id_supplier         INTEGER REFERENCES Supplier (id) \n" +
                ");\n");

//        tạo bảng nhập kho
        db.execSQL("CREATE TABLE Bill_product_in (\n" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name        TEXT,\n" +
                "    price       INTEGER,\n" +
                "    sale        INTEGER,\n" +
                "    quantity    INTEGER,\n" +
                "    total       INTEGER,\n" +
                "    id_category INTEGER REFERENCES Category (id),\n" +
                "    id_bill_in  TEXT    REFERENCES Bill_in (id) \n" +
                ");\n");


//        tạo bảng phiếu xuất
        db.execSQL("CREATE TABLE Bill_out (\n" +
                "    id        TEXT PRIMARY KEY,\n" +
                "    total     INTEGER NOT NULL,\n" +
                "    date_time TEXT    NOT NULL,\n" +
                "    id_user   INTEGER REFERENCES user (id) \n" +
                ");\n");

//        tạo bảng chi tiết phiếu xuất
        db.execSQL("CREATE TABLE Bill_out_detail (\n" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    address     TEXT    NOT NULL,\n" +
                "    id_product  INTEGER REFERENCES Product (id),\n" +
                "    id_delivery INTEGER REFERENCES Delivery (id), \n" +
                "    id_bill_out TEXT REFERENCES Bill_out (id) \n" +
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
                "    quantity    INTEGER ,\n" +
                "    id_category INTEGER REFERENCES Category (id),\n" +
                "    sale_price  INTEGER NOT NULL\n" +
                ");\n");

//        tạo bảng xuất kho
//        db.execSQL("CREATE TABLE Product_out (\n" +
//                "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                "    name        TEXT    NOT NULL,\n" +
//                "    price       INTEGER NOT NULL,\n" +
//                "    quantity    INTEGER ,\n" +
//                "    id_category INTEGER REFERENCES Category (id),\n" +
//                "    sale_price  INTEGER NOT NULL\n" +
//                ");\n");

//        tạo bảng đơn vị vận chuyển
        db.execSQL("CREATE TABLE Delivery (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    phone    TEXT    NOT NULL,\n" +
                "    price    INTEGER NOT NULL,\n" +
                "    tax_code TEXT    NOT NULL\n" +
                ");\n");

//        tạo bảng nhà cung cấp
        db.execSQL("CREATE TABLE Supplier (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    phone    TEXT    NOT NULL,\n" +
                "    address  TEXT    NOT NULL,\n" +
                "    tax_code TEXT    NOT NULL\n" +
                ");\n");

//        tạo bảng User
        db.execSQL("CREATE TABLE User (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT    NOT NULL,\n" +
                "    password TEXT    NOT NULL,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    email    TEXT    NOT NULL,\n" +
                "    phone    TEXT    NOT NULL,\n" +
                "    role     INTEGER NOT NULL\n" +
                ");\n");

//        tạo bảng nhân viên
        db.execSQL("CREATE TABLE Staff (\n" +
                "    id           INTEGER PRIMARY KEY,\n" +
                "    name         TEXT    NOT NULL,\n" +
                "    phone        TEXT    NOT NULL,\n" +
                "    address      TEXT    NOT NULL,\n" +
                "    work_day     INTEGER NOT NULL,\n" +
                "    salary       INTEGER NOT NULL,\n" +
                "    coefficient  INTEGER NOT NULL\n" +
                ");\n");

        //        thêm dữ liệu mẫu bảng Category
        db.execSQL("INSERT INTO Category VALUES(1,'Bánh kẹo')");
        db.execSQL("INSERT INTO Category VALUES(2,'Nước ngọt')");
        db.execSQL("INSERT INTO Category VALUES(3,'Đồ ăn')");
        db.execSQL("INSERT INTO Category VALUES(4,'Thực phẩm chức năng')");

        //        thêm dữ liệu mẫu bảng User
        db.execSQL("INSERT INTO User VALUES(1,'admin','admin','admin','admin@gmail.com','0973967774',0)");
        db.execSQL("INSERT INTO User VALUES(2,'hieudx','123','do xuan hieu','hieudx@gmail.com','0973967774',1)");
        db.execSQL("INSERT INTO User VALUES(3,'truongtq','123','tran quan truong','truongtq@gmail.com','0123456789',1)");
        db.execSQL("INSERT INTO User VALUES(4,'daohv','123','ha van dao','daohv@gmail.com','0123456789',1)");
        db.execSQL("INSERT INTO User VALUES(5,'duonglt','123','lam tung duong','duonglt@gmail.com','0123456789',1)");

//        thêm dữ liệu mẫu bảng Supplier
        db.execSQL("INSERT INTO Supplier VALUES(1,'Vinamilk','0123456789','số 10, đường Tân Trào, Phường Tân Phú, Quận 7, TP HCM','0300588569')");
        db.execSQL("INSERT INTO Supplier VALUES(2,'Hữu Nghị','0123456789','122 Định Công, Phường Định Công, Quận Hoàng Mai, Thành phố Hà Nội, Việt Nam','0102109239')");
        db.execSQL("INSERT INTO Supplier VALUES(3,'PEPSICO','0123456789','Số 3-4-5, lô CN2, đường số 2, khu công nghiệp Sóng Thần 3, Phường Phú Tân, Thành phố Thủ Dầu Một, Tỉnh Bình Dương, Việt Nam','3702139167')");

//        thêm dữ liệu mẫu bảng phiếu nhập
        db.execSQL("INSERT INTO Bill_in VALUES('PN_0711_21','6/11/2023',2,1)");
        db.execSQL("INSERT INTO Bill_in VALUES('PN_0510_32','6/11/2023',3,1)");
        db.execSQL("INSERT INTO Bill_in VALUES('PN_2012_43','6/11/2023',4,3)");
        db.execSQL("INSERT INTO Bill_in VALUES('PN_0204_24','6/11/2023',2,2)");

//        thêm dữ liệu bảng nhập kho
        db.execSQL("INSERT INTO Bill_product_in VALUES(0,'Bánh trung thu',30000,70000,2,null,1,'PN_0711_21')");
        db.execSQL("INSERT INTO Bill_product_in VALUES(1,'Bim bim',5000,10000,2,null,1,'PN_0510_32')");
        db.execSQL("INSERT INTO Bill_product_in VALUES(2,'Pepsi',7000,15000,2,null,2,'PN_2012_43')");
        db.execSQL("INSERT INTO Bill_product_in VALUES(3,'Kimbap',5000,10000,2,null,3,'PN_0204_24')");
        db.execSQL("INSERT INTO Bill_product_in VALUES(4,'Shushi',100,300,2,null,3,'PN_0204_24')");
        db.execSQL("INSERT INTO Bill_product_in VALUES(5,'Cacao',50,25,2,null,3,'PN_0204_24')");



//        thêm dữ liệu mẫu bảng phiếu xuất
//        db.execSQL("INSERT INTO Bill_out VALUES('PX_0711_21',1200000,'6/11/2023',2)");
//        db.execSQL("INSERT INTO Bill_out VALUES('PN_0510_32',1000,'6/11/2023',3,2)");
//        db.execSQL("INSERT INTO Bill_out VALUES('PN_2012_43',7000,'6/11/2023',4,1)");
//        db.execSQL("INSERT INTO Bill_out VALUES('PN_0204_24',80000,'6/11/2023',2,3)");

//        thêm dữ liệu mẫu bảng chi tiết phiếu nhập
//        db.execSQL("INSERT INTO Bill_in_detail VALUES(0,1,1,'PN_0711_21')");
//        db.execSQL("INSERT INTO Bill_in_detail VALUES(1,2,3,'PN_0510_32')");
//        db.execSQL("INSERT INTO Bill_in_detail VALUES(2,3,3,'PN_2012_43')");
//        db.execSQL("INSERT INTO Bill_in_detail VALUES(3,2,2,'PN_0204_24')");

//        thêm dữ liệu mẫu bảng chi tiết phiếu xuất
//        db.execSQL("INSERT INTO Bill_in_detail VALUES(0,'ababcabcbacbabcbacb',2,'PN_0204_24')");



//        thêm dữ liệu mẫu bảng Staff
        db.execSQL("INSERT INTO Staff VALUES(1,'Nguyễn Văn A','0123456789','abc',30,3000000,100000)");
        db.execSQL("INSERT INTO Staff VALUES(2,'Trần Văn B','0123456789','bcd',30,6000000,200000)");
        db.execSQL("INSERT INTO Staff VALUES(3,'Trịnh Văn K','0123456789','jqk',25,100000000,500000)");

//        thêm dữ liệu mẫu bảng Delivery
        db.execSQL("INSERT INTO Delivery VALUES(1,'J&T','0123456789',17000,'0300588569')");
        db.execSQL("INSERT INTO Delivery VALUES(2,'VN Express','0123456789',13000,'0704358569')");
        db.execSQL("INSERT INTO Delivery VALUES(3,'GHTK','0123456789',23000,'0300588569')");



//        thêm dữ liệu mẫu bảng Product
        db.execSQL("INSERT INTO Product VALUES(1,'Bánh trung thu',30000,100,1,70000)");
        db.execSQL("INSERT INTO Product VALUES(2,'Bim bim',5000,100,1,10000)");
        db.execSQL("INSERT INTO Product VALUES(3,'Pepsi',7000,100,2,15000)");
        db.execSQL("INSERT INTO Product VALUES(4,'Kimbap',5000,100,3,10000)");
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
