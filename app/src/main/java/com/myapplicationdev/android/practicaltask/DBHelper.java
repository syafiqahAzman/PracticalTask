package com.myapplicationdev.android.practicaltask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "stu.db";

    private static final String TABLE_NAME = "Student";
    private static final  String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GPA = "gpa";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," + COLUMN_GPA + " REAL )";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertStudent(String name, Double gpa ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_GPA, gpa);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<Student>();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_GPA + " FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Double gpa = cursor.getDouble(2);
                Student obj = new Student(id, name, gpa);
                students.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }
}
