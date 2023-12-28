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
//        tạo bảng User
        db.execSQL("CREATE TABLE User (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT    NOT NULL,\n" +
                "    password TEXT    NOT NULL,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    email    TEXT    NOT NULL,\n" +
                "    phone    TEXT    NOT NULL,\n" +
                "    role     INTEGER NOT NULL,\n" +
                "    avatar   TEXT    NOT NULL\n" +
                ");\n");

//        tạo bảng nhà cung cấp
        db.execSQL("CREATE TABLE Supplier (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    phone    TEXT    NOT NULL,\n" +
                "    address  TEXT    NOT NULL,\n" +
                "    tax_code TEXT    NOT NULL\n" +
                ");\n");

//        tạo bảng đơn vị vận chuyển
        db.execSQL("CREATE TABLE Delivery (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name     TEXT    NOT NULL,\n" +
                "    phone    TEXT    NOT NULL,\n" +
                "    price    INTEGER NOT NULL,\n" +
                "    tax_code TEXT    NOT NULL\n" +
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
                "    price       INTEGER DEFAULT (0),\n" +
                "    quantity    INTEGER DEFAULT (0),\n" +
                "    img         TEXT    ,\n" +
                "    id_category INTEGER REFERENCES Category (id),\n" +
                "    id_supplier INTEGER REFERENCES Supplier (id) \n" +
                ");\n");

//        tạo bảng phiếu nhập
        db.execSQL("CREATE TABLE Bill_in (\n" +
                "    id                  TEXT PRIMARY KEY,\n" +
                "    date_time           TEXT NOT NULL,\n" +
                "    sum                 INTEGER,\n" +
                "    status              INTEGER,\n" +
                "    id_user             INTEGER REFERENCES User (id) \n" +
                ");\n");

//        tạo bảng chi tiết phiếu nhập
        db.execSQL("CREATE TABLE Bill_in_detail (\n" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    price       INTEGER NOT NULL,\n" +
                "    quantity    INTEGER NOT NULL,\n" +
                "    total       INTEGER,\n" +
                "    id_product  INTEGER REFERENCES Product (id),\n" +
                "    id_bill_in  TEXT    REFERENCES Bill_in (id) \n" +
                ");\n");

//        tạo bảng phiếu xuất
        db.execSQL("CREATE TABLE Bill_out (\n" +
                "    id        TEXT PRIMARY KEY,\n" +
                "    date_time TEXT NOT NULL,\n" +
                "    sum       INTEGER,\n" +
                "    address   TEXT NOT NULL,\n" +
                "    status    INTEGER NOT NULL,\n" +
                "    id_user   INTEGER REFERENCES User (id), \n" +
                "    id_delivery INTEGER REFERENCES Delivery (id) \n" +
                ");\n");

//        tạo bảng chi tiết phiếu xuất
        db.execSQL("CREATE TABLE Bill_out_detail (\n" +
                "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    price       INTEGER NOT NULL,\n" +
                "    quantity    INTEGER NOT NULL,\n" +
                "    total       INTEGER,\n" +
                "    id_product  INTEGER REFERENCES Product (id),\n" +
                "    id_bill_out TEXT REFERENCES Bill_out (id) \n" +
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

//        thêm dữ liệu mẫu bảng User
        db.execSQL("INSERT INTO User VALUES(1,'admin','21232f297a57a5a743894a0e4a801fc3','admin','admin@gmail.com','0973967774',0,'https://www.pngall.com/wp-content/uploads/5/Profile-Avatar-PNG.png')");
        db.execSQL("INSERT INTO User VALUES(2,'hieudx','202cb962ac59075b964b07152d234b70','do xuan hieu','hieudx@gmail.com','0973967774',1,'https://www.pngall.com/wp-content/uploads/5/Profile-Avatar-PNG.png')");
        db.execSQL("INSERT INTO User VALUES(3,'truongtq','123','tran quan truong','truongtq@gmail.com','0123456789',1,'https://www.pngall.com/wp-content/uploads/5/Profile-Avatar-PNG.png')");
        db.execSQL("INSERT INTO User VALUES(4,'daohv','123','ha van dao','daohv@gmail.com','0123456789',1,'https://www.pngall.com/wp-content/uploads/5/Profile-Avatar-PNG.png')");
        db.execSQL("INSERT INTO User VALUES(5,'duonglt','123','lam tung duong','duonglt@gmail.com','0123456789',1,'https://www.pngall.com/wp-content/uploads/5/Profile-Avatar-PNG.png')");

//        thêm dữ liệu mẫu bảng Category
        db.execSQL("INSERT INTO Category VALUES(0,'Bánh kẹo')");
        db.execSQL("INSERT INTO Category VALUES(1,'Nước ngọt')");
        db.execSQL("INSERT INTO Category VALUES(2,'Đồ ăn')");
        db.execSQL("INSERT INTO Category VALUES(3,'Thực phẩm chức năng')");
        db.execSQL("INSERT INTO Category VALUES(4,'test')");

//        thêm dữ liệu mẫu bảng Supplier
        db.execSQL("INSERT INTO Supplier VALUES(1,'Vinamilk','0123456789','số 10, đường Tân Trào, Phường Tân Phú, Quận 7, TP HCM','0300588569')");
        db.execSQL("INSERT INTO Supplier VALUES(2,'Hữu Nghị','0123456789','122 Định Công, Phường Định Công, Quận Hoàng Mai, Thành phố Hà Nội, Việt Nam','0102109239')");
        db.execSQL("INSERT INTO Supplier VALUES(3,'PEPSICO','0123456789','Số 3-4-5, lô CN2, đường số 2, khu công nghiệp Sóng Thần 3, Phường Phú Tân, Thành phố Thủ Dầu Một, Tỉnh Bình Dương, Việt Nam','3702139167')");

//        thêm dữ liệu mẫu bảng Delivery
        db.execSQL("INSERT INTO Delivery VALUES(1,'J&T','0123456789',17000,'0300588569')");
        db.execSQL("INSERT INTO Delivery VALUES(2,'VN Express','0123456789',13000,'0704358569')");
        db.execSQL("INSERT INTO Delivery VALUES(3,'GHTK','0123456789',23000,'0300588569')");

//        thêm dữ liệu mẫu bảng Product
        db.execSQL("INSERT INTO Product VALUES(0,'Bánh trung thu',0,1,'https://png.pngtree.com/element_our/20190601/ourlarge/pngtree-a-big-moon-cake-free-button-image_1374096.jpg',1,1)");
        db.execSQL("INSERT INTO Product VALUES(1,'Bim bim',0,1,'https://minhcaumart.vn/media/com_eshop/products/8936036020755.webp',2,3)");
        db.execSQL("INSERT INTO Product VALUES(2,'Pepsi',0,1,'https://bizweb.dktcdn.net/thumb/grande/100/365/460/products/nuoc-pepsi-330ml.jpg?v=1571624262987',2,3)");
        db.execSQL("INSERT INTO Product VALUES(3,'Kimbap',0,1,'https://www.koreanbapsang.com/wp-content/uploads/2018/09/DSC8399-2-e1696691292303.jpg',3,2)");
        db.execSQL("INSERT INTO Product VALUES(4,'test',0,1,'https://www.koreanbapsang.com/wp-content/uploads/2018/09/DSC8399-2-e1696691292303.jpg',3,2)");

//        thêm dữ liệu mẫu bảng phiếu nhập
        db.execSQL("INSERT INTO Bill_in VALUES('PN_0711_21','06/01/2023 01:35:35',600200,0,2)");
        db.execSQL("INSERT INTO Bill_in VALUES('PN_0510_32','07/09/2023 01:35:35',10000,1,3)");
        db.execSQL("INSERT INTO Bill_in VALUES('PN_2012_43','10/10/2023 01:35:35',14000,0,4)");
        db.execSQL("INSERT INTO Bill_in VALUES('PN_0204_24','22/11/2023 01:35:35',101000,1,2)");

//        thêm dữ liệu bảg chi tiết phiếu nhập
        db.execSQL("INSERT INTO Bill_in_detail VALUES(0,30000,2,600000,1,'PN_0711_21')");
        db.execSQL("INSERT INTO Bill_in_detail VALUES(1,5000,2,10000,2,'PN_0510_32')");
        db.execSQL("INSERT INTO Bill_in_detail VALUES(2,7000,2,14000,2,'PN_2012_43')");
        db.execSQL("INSERT INTO Bill_in_detail VALUES(3,5000,2,10000,3,'PN_0204_24')");
        db.execSQL("INSERT INTO Bill_in_detail VALUES(4,100,2,200,0,'PN_0711_21')");
        db.execSQL("INSERT INTO Bill_in_detail VALUES(5,50,2,1000,1,'PN_0204_24')");

//        thêm dữ liệu mẫu bảng phiếu xuất
        db.execSQL("INSERT INTO Bill_out VALUES('PX_0711_21','20/08/2023 01:35:35',163000,'ádqwrasda',1,2,1)");
        db.execSQL("INSERT INTO Bill_out VALUES('PX_0510_32','22/03/2023 01:35:35',15000,'joipjoiik',1,3,2)");
        db.execSQL("INSERT INTO Bill_out VALUES('PX_2012_43','24/10/2023 01:35:35',20000,',mnzcxnz,mxc',0,1,2)");

//        thêm dữ liệu mẫu bảng chi tiết phiếu xuất
        db.execSQL("INSERT INTO Bill_out_detail VALUES(0,60000,2,120000,1,'PX_0711_21')");
        db.execSQL("INSERT INTO Bill_out_detail VALUES(1,15000,1,15000,1,'PX_0510_32')");
        db.execSQL("INSERT INTO Bill_out_detail VALUES(2,10000,2,20000,1,'PX_2012_43')");
        db.execSQL("INSERT INTO Bill_out_detail VALUES(3,20000,2,40000,1,'PX_0711_21')");
        db.execSQL("INSERT INTO Bill_out_detail VALUES(4,1500,2,3000,1,'PX_0711_21')");

//        thêm dữ liệu mẫu bảng Staff
        db.execSQL("INSERT INTO Staff VALUES(1,'Nguyễn Văn A','0123456789','abc',30,3000000,100000)");
        db.execSQL("INSERT INTO Staff VALUES(2,'Trần Văn B','0123456789','bcd',30,6000000,200000)");
        db.execSQL("INSERT INTO Staff VALUES(3,'Trịnh Văn K','0123456789','jqk',25,100000000,500000)");

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
