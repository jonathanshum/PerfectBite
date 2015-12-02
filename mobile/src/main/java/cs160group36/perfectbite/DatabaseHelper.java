package cs160group36.perfectbite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodData.db";
    public static final String TABLE_NAME = "foodtable";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_FOODNAME = "name";
    public static final String KEY_SERVING = "servingsize";
    public static final String KEY_CAL = "calories";
    public static final String KEY_FAT = "totalfat";
    public static final String KEY_CARB = "carbohydrates";
    public static final String KEY_SODIUM = "sodium";
    public static final String KEY_PROTEIN = "protein";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            " create table  " +  TABLE_NAME  + " ("
                    + KEY_ROWID + " integer primary key autoincrement,  "
                    + KEY_FOODNAME + " text, "
                    + KEY_SERVING + " text, "
                    + KEY_CAL + " integer, "
                    + KEY_FAT + " integer, "
                    + KEY_CARB + " integer, "
                    + KEY_SODIUM + " integer, "
                    + KEY_PROTEIN + " integer); ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("oncreate", "reached");
        db.execSQL(DATABASE_CREATE);

        //Insert default data into database. Currently inserts example values to demonstrate functionality.
        this.insertData(db, "example food 1", "1 cup", 100, 50, 20, 15, 50);
        this.insertData(db, "example food 2", "1 cup", 200, 100, 40, 30, 100);
        this.insertData(db, "example food 3", "3 cookies", 200, 60, 100, 10, 0);
        this.insertData(db, "example food 4", "1/4 cup", 50, 0, 20, 0, 0);
        this.insertData(db, "example food 5", "1 tsp", 80, 0, 35, 0, 5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(SQLiteDatabase db, String name, String serving, int cal, int fat, int carb, int sodium, int protein) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FOODNAME, name);
        contentValues.put(KEY_SERVING, serving);
        contentValues.put(KEY_CAL, cal);
        contentValues.put(KEY_FAT, fat);
        contentValues.put(KEY_CARB, carb);
        contentValues.put(KEY_SODIUM, sodium);
        contentValues.put(KEY_PROTEIN, protein);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor fetchDataFromName(SQLiteDatabase db, String name) {
        String whereClause = KEY_FOODNAME + " = ?";
        String[] whereArgs = new String[] {name};
        Cursor c = db.query(TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return c;
    }
}