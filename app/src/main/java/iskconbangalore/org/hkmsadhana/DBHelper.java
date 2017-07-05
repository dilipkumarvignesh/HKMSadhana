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
        public static final String CONTACTS_TABLE_NAME = "SadhanaUpdate1";
        public static final String CONTACTS_COLUMN_ID = "id";
        public static final String CONTACTS_COLUMN_DATE = "StrDate";
        public static final String CONTACTS_COLUMN_MA= "MA";
        public static final String CONTACTS_COLUMN_DA = "DA";
        public static final String CONTACTS_COLUMN_SB = "SB";
        public static final String CONTACTS_COLUMN_JAPA = "JapaNo";
        public static final String CONTACTS_COLUMN_RM = "readingMinutes";

    //    private HashMap hp;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME , null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(
                    "create table SadhanaUpdate1 " +
                            "(StrDate text primary key, MA text, DA text, SB text,JapaNo integer,readingMinutes integer);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS SadhanaUpdate1");
            onCreate(db);
        }

        public void initializeMonthlySadhana(String Smonth,int year)
        {
            int days;
            String Sdate,Sday;
            SQLiteDatabase db = this.getWritableDatabase();

            if( Smonth == "04" || Smonth == "06" || Smonth == "09" || Smonth == "11")
            {
                days =30;
            }
            else if (Smonth =="02")
            {
                days = 28;
            }
            else
            {
                days =31;
            }
            for (int i=1;i<days;i++)
            {
                if(i < 10){

                    Sday  = "0" + i ;
                }
                else
                {
                    Sday = ""+i;
                }
                Sdate = year+"/"+Smonth+"/"+Sday;
                ContentValues contentValues = new ContentValues();
                contentValues.put("StrDate", Sdate);
                contentValues.put("MA", "NA");
                contentValues.put("DA", "NA");
                contentValues.put("SB", "NA");
                contentValues.put("JapaNo",0);
                contentValues.put("readingMinutes", 0);

                db.insert("SadhanaUpdate1", null, contentValues);
                Log.d("info","InsertedDate:"+Sdate);
           }

        }


        public Double[] getSummarydB(String year, String month)
        {
            String query ="";
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
                    "from sadhanaUpdate1  where StrDate between  '"+StartDate+"' and '"+EndDate+"'";
            Log.d("info","Query Data="+query);

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


            Double[] QueryResult = {MaFinalValue,DaFinalValue,SbFinalValue,japa, ReadTime};

             return QueryResult;
        }

        public void updateSadhana(String Date,String Status, String Field)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put("StrDate",Date);
            if (Field == "MA")
             cv.put("MA",Status);
            else if (Field == "DA")
             cv.put("DA",Status);
            else if (Field == "SB")
                cv.put("SB",Status);
            int upd = db.update("SadhanaUpdate1", cv, "StrDate = ?", new String[] {Date});
            Log.d("info","SelectedDate:"+Date);
            Log.d("info","No Of Rows affected:"+upd);

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

            long rowInserted = db.insert("SadhanaUpdate1", null, contentValues);
            if(rowInserted != -1)
                Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
            return true;
        }

        public SadhanaUpdate getData(String Date) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "select * from SadhanaUpdate1 where StrDate = ?";
            Log.d("info","QueryRecordData:"+query);
            Cursor res =  db.rawQuery(query, new String[]{Date} );
            String date, MA, JapaRounds, DA,SB, RMin ;
              if(res.getCount() > 0) {
                res.moveToFirst();
                  date= res.getString(res.getColumnIndex(CONTACTS_COLUMN_DATE));
                  MA= res.getString(res.getColumnIndex(CONTACTS_COLUMN_MA));
                  DA=res.getString(res.getColumnIndex(CONTACTS_COLUMN_DA));
                  SB=res.getString(res.getColumnIndex(CONTACTS_COLUMN_SB));
                  Integer Japa = res.getInt(res.getColumnIndex(CONTACTS_COLUMN_JAPA));
                  JapaRounds = Japa.toString();
                  Integer RM = res.getInt(res.getColumnIndex(CONTACTS_COLUMN_RM));
                  RMin=RM.toString();
                  SadhanaUpdate obj = new SadhanaUpdate(date,MA,DA,SB,JapaRounds,RMin);
                  return obj;
            }
            else
              {
                  Log.d("info","No Records fetched");
              }
              return null;
        }

        public int numberOfRows(){
            SQLiteDatabase db = this.getReadableDatabase();
            int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
            return numRows;
        }


        public ArrayList<SadhanaUpdate> getSadhanaHistory() {
            ArrayList<SadhanaUpdate> outer = new ArrayList<>();

            String date, MA, JapaRounds, DA,SB, RMin ;

            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( "select * from SadhanaUpdate1", null );
            res.moveToFirst();
            int counter = 0;
            Log.d("info","Inside Sadhanahistory:"+res);
            while(res.isAfterLast() == false){
                Log.d("info","Counter:"+counter);
                counter = counter+1;
                date= res.getString(res.getColumnIndex(CONTACTS_COLUMN_DATE));
                MA= res.getString(res.getColumnIndex(CONTACTS_COLUMN_MA));
                DA=res.getString(res.getColumnIndex(CONTACTS_COLUMN_DA));
                SB=res.getString(res.getColumnIndex(CONTACTS_COLUMN_SB));
                Integer Japa = res.getInt(res.getColumnIndex(CONTACTS_COLUMN_JAPA));
                JapaRounds = Japa.toString();
                Integer RM = res.getInt(res.getColumnIndex(CONTACTS_COLUMN_RM));
                RMin=RM.toString();
                SadhanaUpdate obj = new SadhanaUpdate(date,MA,DA,SB,JapaRounds,RMin);
                outer.add(obj);
                res.moveToNext();

            }
            Log.d("info","Sadhanadata:"+outer);
            return outer;
        }


    }
