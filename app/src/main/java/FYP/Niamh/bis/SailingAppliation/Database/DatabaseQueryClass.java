package FYP.Niamh.bis.SailingAppliation.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import FYP.Niamh.bis.SailingAppliation.Features.SafetyCRUD.CreateSafetyEquipment.Safety;
import FYP.Niamh.bis.SailingAppliation.Util.Config;

/*
 * Adapted from Michael Gleesons lecture on 12/11/2020 gleeson.io
 */

public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
    }

    public long insertSafety(Safety safety){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_SAFETY_NAME, safety.getName());
        contentValues.put(Config.COLUMN_SAFETY_DESCRIPTION, safety.getDescription());
        contentValues.put(Config.COLUMN_SAFETY_ISSUE, safety.getIssue());
        contentValues.put(Config.COLUMN_SAFETY_AVAILABLE, safety.getAvailable());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_SAFETY, null, contentValues);
        } catch (SQLiteException e){
            Log.d("NIAMH_FYP", "Exception: "+ e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Safety> getAllSafety(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_SAFETY, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Safety> safetyList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SAFETY_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SAFETY_NAME));
                        String description = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SAFETY_DESCRIPTION));
                        String issue = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SAFETY_ISSUE));
                        String available = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SAFETY_AVAILABLE));

                        safetyList.add(new Safety(id, name, description, issue, available));
                    }   while (cursor.moveToNext());

                    return safetyList;
                }
        } catch (Exception e){
            Log.d("NIAMH_FYP", "Exception: "+ e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }
    public Safety getSafetyById(long safetyId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Safety safety = null;

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_SAFETY, null,
                    Config.COLUMN_SAFETY_ID + " = ? ", new String[] {String.valueOf(safetyId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                String safetyName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SAFETY_NAME));
                String description = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SAFETY_DESCRIPTION));
                String issue = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SAFETY_ISSUE));
                String available = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SAFETY_AVAILABLE));

                safety = new Safety((int) safetyId, safetyName, description, issue, available);
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return safety;
    }


    public long updateSafetyInfo(Safety safety){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_SAFETY_NAME, safety.getName());
        contentValues.put(Config.COLUMN_SAFETY_DESCRIPTION, safety.getDescription());
        contentValues.put(Config.COLUMN_SAFETY_ISSUE, safety.getIssue());
        contentValues.put(Config.COLUMN_SAFETY_AVAILABLE, safety.getAvailable());
        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_SAFETY, contentValues,
                    Config.COLUMN_SAFETY_ID + " = ? ",
                    new String[] {String.valueOf(safety.getId())});
        } catch (SQLiteException e){
            Log.d("NIAMH_FYP", "Exception: "+ e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public boolean deleteSafetyById(long subjectId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_SAFETY,
                Config.COLUMN_SAFETY_ID + " = ? ", new String[]{String.valueOf(subjectId)});

        return row > 0;
    }


    public boolean deleteAllSafetys(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Config.TABLE_SAFETY, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_SAFETY);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Log.d("NIAMH_FYP", "Exception: "+ e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }

    public long getNumberOfSafety(){
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_SAFETY);
        } catch (SQLiteException e){
            Log.d("NIAMH_FYP", "Exception: "+ e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return count;
    }


}
