package com.yardages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by Conal on 27/07/2015.
 */
public class YardagesDbHelper extends SQLiteOpenHelper {
    Context context;
    final static int DB_VERSION = 4;
    final static String DB_NAME = "YardagesDB";
    private String DB_PATH;

    private static final String SQL_CREATE_TABLE_SCATTER = "CREATE TABLE SCATTER( "
            +"_id integer primary key autoincrement, "
            +"Description text, "
            +"TeeLatitude double, "
            +"TeeLongitude double, "
            +"TargetLatitude double, "
            +"TargetLongitude double, "
            +"Date datetime"
            +");";

    private static final String SQL_CREATE_TABLE_BALL = "CREATE TABLE BALL( "
            +"_id integer primary key autoincrement, "
            +"Latitude double, "
            +"Longitude double, "
            +"ScatterId integer"
            +");";
    private ArrayList<Scatter> scatterList;


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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SCATTER");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BALL");
        onCreate(sqLiteDatabase);
    }


    public ArrayList<Scatter> getScatterList() {
        ArrayList<Scatter> scatterList = new ArrayList<Scatter>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM SCATTER",null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String desc = cursor.getString(cursor.getColumnIndex("Description"));
                int id = cursor.getInt(cursor.getColumnIndex("_id"));

                Scatter sc = new Scatter();
                sc.setId(id);
                sc.setDescription(desc);
                scatterList.add(sc);
                cursor.moveToNext();
            }
        }
        cursor.close();

//        cursor.moveToFirst();
//        long itemId = cursor.getLong(
//                cursor.getColumnIndexOrThrow("_id")
//        );

        return scatterList;
    }

    public long addScatter(Scatter scatter) throws IOException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put("_id", "null");
        values.put("Description", scatter.getDescription());
        if(scatter.getTeeLocation()!=null) {
            values.put("TeeLatitude", scatter.getTeeLocation().getLatitude());
            values.put("TeeLongitude", scatter.getTeeLocation().getLongitude());
        }
        if(scatter.getTargetLocation()!=null) {
            values.put("TargetLatitude", scatter.getTargetLocation().getLatitude());
            values.put("TargetLongitude", scatter.getTargetLocation().getLongitude());
        }
        values.put("Date", "DATETIME('now')");

        long rowNumScatter, rowNumBall;
        rowNumScatter = db.insert("SCATTER", null, values);

        for(int i=0; i<scatter.getBallList().size();i++){
            ContentValues valuesBall = new ContentValues();
            valuesBall.put("Latitude", scatter.getBall(i).getLatitude());
            valuesBall.put("Longitude", scatter.getBall(i).getLongitude());
            valuesBall.put("ScatterId", rowNumScatter);
            rowNumBall = db.insert("BALL", null, valuesBall);
        }

        db.close();
        try {
            writeToSD();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowNumScatter;
    }

    public Scatter getScatter(long scatterId){
        SQLiteDatabase db = this.getReadableDatabase();

        //table SCATTER, with columns id and description, with selection being id = argument,
        //with the argument being the value of id.
        Cursor scatterCursor = db.query("SCATTER",
                new String[]{"_id", "Description"},
                "_id=?",
                new String[]{String.valueOf(scatterId)},
                null, null, null, null);

        if(scatterCursor != null)
            scatterCursor.moveToFirst();

        Scatter scatter = new Scatter();
        scatter.setId(scatterCursor.getInt(scatterCursor.getColumnIndex("_id")));
        scatter.setDescription(scatterCursor.getString(scatterCursor.getColumnIndex("Description")));

        //also get all balls
        Cursor ballCursor = db.query("BALL",
                new String[]{"_id", "Latitude", "Longitude"},
                "ScatterId=?",
                new String[]{String.valueOf(scatterId)},
                null, null, null, null);

        if(ballCursor.moveToFirst()){
            while(!ballCursor.isAfterLast()){
                int ballId = ballCursor.getInt(ballCursor.getColumnIndex("_id"));
                double ballLatitude = ballCursor.getDouble(ballCursor.getColumnIndex("Latitude"));
                double ballLongitude = ballCursor.getDouble(ballCursor.getColumnIndex("Longitude"));

                Ball b = new Ball();
                b.setId(ballId);
                b.setLatitude(ballLatitude);
                b.setLongitude(ballLongitude);
                scatter.addBall(b);

                ballCursor.moveToNext();
            }
        }
        ballCursor.close();

        return scatter;
    }

    private void writeToSD() throws IOException {
        File sd = Environment.getExternalStorageDirectory();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            DB_PATH = context.getFilesDir().getAbsolutePath().replace("files","databases")+File.separator;
        } else {
            DB_PATH = context.getFilesDir().getPath() + context.getPackageName() + "/databases/";
        }

        if(sd.canWrite()){
            String currentDBPath = DB_NAME;
            String backupDBPath = "yardagesbackup.db";
            File currentDB = new File(DB_PATH, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            if(currentDB.exists()){
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src,0,src.size());
                src.close();
                dst.close();
            }
        }


    }

}
