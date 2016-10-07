package pt.claudio.security.pixelscamp_content2.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Table {

    public static final String TABLE_NOTE = "notes";
    public static final String TABLE_NOTE_S = "notessecure";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_DESCRIPTION = "description";



    private static final String DATABASE_CREATE = "create table "
            + TABLE_NOTE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_CATEGORY + " text not null, "
            + COLUMN_SUMMARY + " text not null,"
            + COLUMN_DESCRIPTION
            + " text not null"
            + ");";

    private static final String DATABASE_CREATE_2 = "create table "
            + TABLE_NOTE_S
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_CATEGORY + " text not null, "
            + COLUMN_SUMMARY + " text not null,"
            + COLUMN_DESCRIPTION
            + " text not null"
            + ");";

    // Fills the database with the following data
    private static final String DATABASE_INSERT =
            "INSERT INTO notessecure " +
                    "values('1'," +
                    "'SecureNote'," +
                    "'Google account'," +
                    "'Username: elpixel@gmail.com - Pwd: mySup3rDoop3rPassword');";

    private static final String DATABASE_INSERT_2 =
            "INSERT INTO notes " +
                    "values('1'," +
                    "'General'," +
                    "'Meeting'," +
                    "'09:00 GMT+1 @xpto');";

    //Execute the previous queries
    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE_2);
        database.execSQL(DATABASE_INSERT);
        database.execSQL(DATABASE_INSERT_2);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(Table.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(database);
    }
}