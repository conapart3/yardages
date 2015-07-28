package com.yardages;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Conal on 27/07/2015.
 */
public class YardagesDbHelper extends SQLiteOpenHelper {
    Context context;
    final static int DB_VERSION = 1;
    final static String DB_NAME = "Yardages.db";

    private static final String SQL_CREATE_TABLE_SCATTER = "CREATE TABLE Scatter( "
            +"_id integer primary key autoincrement, "
            +"Description varchar(255), "
            +"TeeLatitude double, "
            +"TeeLongitude double "
            +"TargetLatitude double, "
            +"TargetLongitude double"
            +");";

    private static final String SQL_CREATE_TABLE_BALL = "CREATE TABLE BALL( "
            +"_id integer primary key autoincrement, "
            +"Latitude double, "
            +"Longitude double, "
            +"ScatterId int"
            +");";


    public YardagesDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_BALL);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_SCATTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Yardages.db.SCATTER");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Yardages.db.BALL");
        onCreate(sqLiteDatabase);
    }
}
