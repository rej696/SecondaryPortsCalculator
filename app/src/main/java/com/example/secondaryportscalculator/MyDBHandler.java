package com.example.secondaryportscalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DecimalFormat;

public class MyDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "secondaryPortDB.db";
    public static final String TABLE_NAME = "SecondaryPort";
    public static final String _ID = "_ID";
    public static final String COLUMN_SECONDARY_PORT_NAME = "SecondaryPortName";
    public static final String COLUMN_STANDARD_PORT_NAME = "StandardPortName";
    public static final String COLUMN_DHWS = "DifferenceHWSprings";
    public static final String COLUMN_DHWN = "DifferenceHWNeaps";
    public static final String COLUMN_DLWN = "DifferenceLWNeaps";
    public static final String COLUMN_DLWS = "DifferenceLWSprings";

    //initialize the database
    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SECONDARY_PORT_NAME + " TEXT, "
                + COLUMN_STANDARD_PORT_NAME + " TEXT, "
                + COLUMN_DHWS + " REAL, "
                + COLUMN_DHWN + " REAL, "
                + COLUMN_DLWN + " REAL, "
                + COLUMN_DLWS + " REAL "
                + ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public String loadHandler() {
        StringBuilder result = new StringBuilder();
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        while (cursor.moveToNext()) {
            int index = cursor.getInt(0);
            String port_name = cursor.getString(1);
            String standard_port_name = cursor.getString(2);
            String difference_hw_springs = decimalFormat.format(cursor.getDouble(3));
            String difference_hw_neaps = decimalFormat.format(cursor.getDouble(4));
            String difference_lw_neaps = decimalFormat.format(cursor.getDouble(5));
            String difference_lw_springs = decimalFormat.format(cursor.getDouble(6));
            result.append(index).append(" ")
                    .append(port_name).append(" ")
                    .append(standard_port_name).append(" ")
                    .append(difference_hw_springs).append(" ")
                    .append(difference_hw_neaps).append(" ")
                    .append(difference_lw_neaps).append(" ")
                    .append(difference_lw_springs).append(System.getProperty("line.separator"));
        }
        cursor.close();
        db.close();
        return result.toString();
    }

    public void addHandler(SecondaryPort secondary_port) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SECONDARY_PORT_NAME, secondary_port.getPortName());
        values.put(COLUMN_STANDARD_PORT_NAME, secondary_port.getStandardPortName());
        values.put(COLUMN_DHWS, secondary_port.getDifferenceHWSprings());
        values.put(COLUMN_DHWN, secondary_port.getDifferenceHWNeaps());
        values.put(COLUMN_DLWN, secondary_port.getDifferenceLWNeaps());
        values.put(COLUMN_DLWS, secondary_port.getDifferenceLWSprings());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public SecondaryPort findHandler(String secondary_port_name) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_SECONDARY_PORT_NAME + " = " + "'" + secondary_port_name + "'";;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        SecondaryPort secondary_port = new SecondaryPort();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            secondary_port.setPortName(cursor.getString(1));
            secondary_port.setStandardPortName(cursor.getString(2));
            secondary_port.setDifferenceHWSprings(cursor.getDouble(3));
            secondary_port.setDifferenceHWNeaps(cursor.getDouble(4));
            secondary_port.setDifferenceLWNeaps(cursor.getDouble(5));
            secondary_port.setDifferenceLWSprings(cursor.getDouble(6));
            cursor.close();
        } else {
            secondary_port = null;
        }
        db.close();
        return secondary_port;
    }

    public boolean deleteHandler(String secondary_port_name) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_SECONDARY_PORT_NAME + " = '" + secondary_port_name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        SecondaryPort secondary_port = new SecondaryPort();
        if (cursor.moveToFirst()) {
            secondary_port.setPortName(cursor.getString(1));
            db.delete(
                    TABLE_NAME,
                    COLUMN_SECONDARY_PORT_NAME + "=?",
                    new String[] {secondary_port.getPortName()}
            );
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateHandler(String secondary_port_name,
                                 String standard_port_name,
                                 double difference_hw_springs,
                                 double difference_hw_neaps,
                                 double difference_lw_neaps,
                                 double difference_lw_springs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_SECONDARY_PORT_NAME, secondary_port_name);
        args.put(COLUMN_STANDARD_PORT_NAME, standard_port_name);
        args.put(COLUMN_DHWS, difference_hw_springs);
        args.put(COLUMN_DHWN, difference_hw_neaps);
        args.put(COLUMN_DLWN, difference_lw_neaps);
        args.put(COLUMN_DLWS, difference_lw_springs);
        return db.update(TABLE_NAME, args, COLUMN_SECONDARY_PORT_NAME + "=" + secondary_port_name, null) > 0;
    }
}
