package iskconbangalore.org.hkmsadhana;

/**
 * Created by i308830 on 6/16/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SadhanaTracker.db";
    public static final String CONTACTS_TABLE_NAME = "SadhanaUpdate";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_DATE = "date";
//    public static final String CONTACTS_COLUMN_EMAIL = "email";
//    public static final String CONTACTS_COLUMN_STREET = "street";
//    public static final String CONTACTS_COLUMN_CITY = "place";
//    public static final String CONTACTS_COLUMN_PHONE = "phone";
//    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table SadhanaUpdate " +
                        "(id integer primary key, date integer, MA text, DA text, SB text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS SadhanaUpdate");
        onCreate(db);
    }

    public boolean insertSadhana (long date,Context context, String MA, String DA, String SB) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("MA", MA);
        contentValues.put("DA", DA);
        contentValues.put("SB", SB);

        long rowInserted = db.insert("SadhanaUpdate", null, contentValues);
        if(rowInserted != -1)
            Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Sadhana where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String date, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
//        contentValues.put("phone", phone);
//        contentValues.put("email", email);
//        contentValues.put("street", street);
//        contentValues.put("place", place);
        db.update("SadhanaUpdate", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("SadhanaUpdate",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getSadhanaHistory() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from SadhanaUpdate", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_DATE)));
            res.moveToNext();
        }
        return array_list;
    }
}
