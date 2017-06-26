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
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SadhanaTracker.db";
    public static final String CONTACTS_TABLE_NAME = "SadhanaUpdate";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_DATE = "StrDate";
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
                        "(StrDate text primary key, MA text, DA text, SB text,JapaNo integer,readingMinutes integer);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS SadhanaUpdate");
        onCreate(db);
    }

    public Double[] getSummarydB(String year, String month)
    {    String query ="";
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        String StartDate = year+"/"+month+"/01";
        String EndDate = year+"/"+month+"/31";
        query = "select COUNT(*) MAtotal,"+
                "sum(case when MA = 'YES' then 1 else 0 end) MAYES,"+
                "sum(case when MA = 'LATE' then 1 else 0 end) MALATE,"+
                "sum(case when MA = 'SICK' OR MA = 'AS' OR MA = 'OS' then 1 else 0 end) MAEXCUSE,"+
                "sum(case when DA = 'YES' then 1 else 0 end) DAYES,"+
                "sum(case when DA = 'LATE' then 1 else 0 end) DALATE,"+
                "sum(case when DA = 'SICK' OR DA = 'AS' OR DA = 'OS' then 1 else 0 end) DAEXCUSE,"+
                "sum(case when SB = 'YES' then 1 else 0 end) SBYES,"+
                "sum(case when SB = 'LATE' then 1 else 0 end) SBLATE,"+
                "sum(case when SB = 'SICK' OR SB = 'AS' OR SB = 'OS' then 1 else 0 end) SBEXCUSE,"+
                "sum(readingMinutes) read,"+
                "avg(japaNo) japa "+
                "from sadhanaUpdate  where StrDate between  '"+StartDate+"' and '"+EndDate+"'";
        Log.d("info","Query Data="+query);
       // Log.d("info","Query Data="+StartDate+" , "+EndDate);
//        query = "select count(*) from SadhanaUpdate where StrDate between  '"+StartDate+"' and '"+EndDate+"'";
        res = db.rawQuery(query, null );
        res.moveToFirst();
        Integer TotalCount = res.getInt(0);
        Integer MaYesCount = res.getInt(1);
        Integer MaCountLate = res.getInt(2);
        Integer MaExcuseCount = res.getInt(3);
        Integer DaYesCount = res.getInt(4);
        Integer DaCountLate = res.getInt(5);
        Integer DaExcuseCount = res.getInt(6);
        Integer SbYesCount = res.getInt(7);
        Integer SbCountLate = res.getInt(8);
        Integer SbExcuseCount = res.getInt(9);

        Double ReadTime = (1.0*res.getInt(10))/60;
        Double japa = res.getDouble(11);
        Double MaFinalValue = ((100.0*MaYesCount)+(50*MaCountLate))/(TotalCount - MaExcuseCount);
        Double DaFinalValue = ((100.0*DaYesCount)+(50*DaCountLate))/(TotalCount - DaExcuseCount);
        Double SbFinalValue = ((100.0*SbYesCount)+(50*SbCountLate))/(TotalCount - SbExcuseCount);


//
        Double[] QueryResult = {MaFinalValue,DaFinalValue,SbFinalValue,japa, ReadTime};
//         Log.d("info","QueryResult="+QueryResult);

         return QueryResult;

    }
    public boolean insertSadhana (String date,Context context, String MA, String DA, String SB,Integer JPNo, Integer readMin) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("StrDate", date);
        contentValues.put("MA", MA);
        contentValues.put("DA", DA);
        contentValues.put("SB", SB);
        contentValues.put("JapaNO",JPNo);
        contentValues.put("readingMinutes",readMin);

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
//    public ArrayList<SadhanaUpdate> getSadhanaHistory() {
//        ArrayList<SadhanaUpdate> array_list = new ArrayList<SadhanaUpdate>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from SadhanaUpdate", null );
//        res.moveToFirst();
//
//        while(res.isAfterLast() == false){
//            array_list.add((SadhanaUpdate) res);
//            res.moveToNext();
//        }
//
//        return array_list;
//    }
}
