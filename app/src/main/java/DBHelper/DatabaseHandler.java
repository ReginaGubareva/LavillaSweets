package DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Orders.Order;
import Utils.Util;


public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME);
        String CREATE_TABLE_ORDERS = "CREATE TABLE " + Util.TABLE_NAME + "( "
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_FULLNAME + " TEXT,"
                + Util.KEY_ADDRESS + " TEXT,"
                +Util.KEY_DATE + " TEXT,"
                + Util.KEY_FILLING + " TEXT,"
                + Util.KEY_CONFECTIONARY_NAME + " TEXT,"
                + Util.KEY_QUANTITY + ")";
        db.execSQL(CREATE_TABLE_ORDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
    }

    //Create Read Update Delete

    public void addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_FULLNAME, order.getFullname());
        contentValues.put(Util.KEY_ADDRESS,order.getAddress());
        contentValues.put(Util.KEY_DATE,order.getDate());
        contentValues.put(Util.KEY_FILLING,order.getFilling());
        contentValues.put(Util.KEY_CONFECTIONARY_NAME,order.getConfectionaryname());
        contentValues.put(Util.KEY_QUANTITY,order.getQuantity());
        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }

    public Order getOrder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[] {Util.KEY_ID, Util.KEY_FULLNAME,
                        Util.KEY_ADDRESS,Util.KEY_DATE,Util.KEY_FILLING,Util.KEY_CONFECTIONARY_NAME,Util.KEY_QUANTITY,
                }, Util.KEY_ID + "=?", new String[] {String.valueOf(id)},
                null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Order car = new Order(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));

        return car;
    }

    public List<Order> getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Order> orderList = new ArrayList<>();

        String selectAllCars = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllCars, null);
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId(Integer.parseInt(cursor.getString(0)));
                order.setFullname(cursor.getString(1));
                order.setAddress(cursor.getString(2));
                order.setDate(cursor.getString(3));
                order.setFilling(cursor.getString(4));
                order.setConfectionaryname(cursor.getString(5));
                order.setQuantity(cursor.getString(6));


                orderList.add(order);
            } while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return orderList;
    }

    public int updateOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_FULLNAME, order.getFullname());
        contentValues.put(Util.KEY_ADDRESS, order.getAddress());
        contentValues.put(Util.KEY_DATE, order.getDate());
        contentValues.put(Util.KEY_FILLING, order.getFilling());
        contentValues.put(Util.KEY_CONFECTIONARY_NAME, order.getConfectionaryname());
        contentValues.put(Util.KEY_QUANTITY, order.getQuantity());

        return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?",
                new String[] {String.valueOf(order.getId())});
    }

    public void deleteOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[] {String.valueOf(order.getId())});

        db.close();
    }

    public int getOrdersCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
//    public Order findOrder(int id){
//
//    }
}
