package iskconbangalore.org.hkmsadhana;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.security.GeneralSecurityException;

/**
 * Created by i308830 on 6/22/17.
 */

public class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String BUTTON_TEXT = "Sync Data";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { SheetsScopes.SPREADSHEETS };
    private com.google.api.services.sheets.v4.Sheets mService = null;
    public String updateSheetId;
    public Integer operation;
    public String historicalSheetId;
    private Exception mLastError = null;
    private MainActivity Ma;

    MakeRequestTask(GoogleAccountCredential credential,String updateId,Integer operation, MainActivity con) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Sheets API Android Quickstart")
                .build();
        Log.d("info","Inside Constructor ");
        this.updateSheetId = updateId;
        this.operation = operation;
        this.Ma = con;
    }

    /**
     * Background task to call Google Sheets API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected List<String> doInBackground(Void... params) {
        try {
                Log.d("info","Inside Do in Background");
                //return determine_call();

                if (operation ==0) {
                    Log.d("info", "Call CreateSheet");
                    return createSheet();
                }
                    else if(operation ==1)
                    return getDataFromApi();
                else if (operation ==2)
                    return updateSheet();


        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
        return null;
    }

    /**
     * Fetch a list of names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     * @return List of names and majors
     * @throws IOException
     */
//    public List<String> determine_call()
//    {
//        if(operation == 0
//    }
    private List<String> createSheet() throws IOException{
        Spreadsheet requestBody = new Spreadsheet();

       // Sheets sheetsService = createSheetsService();
        Spreadsheet request = this.mService.spreadsheets().create(requestBody).execute();
        Log.d("info","Request:"+request);
        String spreadSheetId = request.getSpreadsheetId();
        Log.d("info","SpreadSheetId="+spreadSheetId);

        List<String> results = new ArrayList<String>();
        results.add(spreadSheetId);
        Log.d("info", "GSheetIdList:" + results);
        return results;

    }
    private List<String> getDataFromApi() throws IOException {
        //String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
       String spreadsheetId = "1xk8AY8MOWiqwC3qvFEyOVN-wBdMtDW8QtirmcUkocrU";
        // String range = "Class Data!A2:E";
        String range = "A2:E";
        Log.d("info","Insie getDataFromAPi");
        Log.d("info","GID="+updateSheetId);
        List<String> results = new ArrayList<String>();
//            ValueRange response = this.mService.spreadsheets().values()
//                    .get(updateSheetId, range)
//                    .execute();
        ValueRange response = this.mService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        Log.d("info","response="+response);


            List<List<Object>> values = response.getValues();
            Log.d("info", "response=" + values);
            if (values != null) {
                results.add("Name,Number,Status,FolkID,FG");
                for (List row : values) {
                    results.add(row.get(0) + ", "+row.get(1)+"," +row.get(2)+"," +row.get(3)+"," +row.get(4));
                }
            }
        createSheet();
        Log.d("info", "results" + results);
        return results;
    }

        public List<String> updateSheet() throws IOException, GeneralSecurityException
        {
//            Log.d("info","Inside updateSheet");

//            data1.add ("Sameer");
//            data1.add("6544");
//            data1.add ("Tentative");
//            data1.add("4123");
//            data1.add("SRID");
           List<String> results = new ArrayList<String>();
//
//
//            List<List<Object>> data = new ArrayList<List<Object>>();
//            data.add (data1);
//
//            ValueRange body = new ValueRange()
//                    .setValues(data);
////            UpdateValuesResponse result =
////                    this.mService.spreadsheets().values().update(updateSheetId, range, body)
////                            .setValueInputOption("USER_ENTERED")
////                            .execute();

            DBHelper mydb = new DBHelper(Ma);
            ArrayList<SadhanaUpdate> SadhanaHistory= new ArrayList<SadhanaUpdate>();
            List<List<Object>> data = new ArrayList<List<Object>>();

            SadhanaHistory = mydb.getSadhanaHistory();

            for (int i=0;i<SadhanaHistory.size();i++)
            {   Log.d("info","SadhanaUpdate:"+SadhanaHistory.get(i).MA.toString());
                List<Object> data1 = new ArrayList<Object>();
                data1.add(SadhanaHistory.get(i).MA.toString());
                data1.add(SadhanaHistory.get(i).DA.toString());
                data1.add(SadhanaHistory.get(i).SB.toString());
                data1.add(SadhanaHistory.get(i).Japa.toString());
                data.add(data1);
                Log.d("info","DataRow:"+data1);

            }

            Log.d("info","UpdatedData:"+data);





            String spreadsheetId = updateSheetId;
            Log.d("info","SpreadSheetID:"+spreadsheetId); // TODO: Update placeholder value.
            List<ValueRange> finaldata = new ArrayList<>();
            ValueRange body = new ValueRange();
            body.setValues(data);
            String range = "A2:E32";
            body.setRange(range);
            finaldata.add(body);

            String valueInputOption = "USER_ENTERED"; // T

            BatchUpdateValuesRequest requestBody = new BatchUpdateValuesRequest();
            requestBody.setValueInputOption(valueInputOption);
            requestBody.setData(finaldata);

            Sheets.Spreadsheets.Values.BatchUpdate request =
                    this.mService.spreadsheets().values().batchUpdate(spreadsheetId, requestBody);

            BatchUpdateValuesResponse response = request.execute();
            Log.d("info","BatchUpdateInfo:"+response);
            return results;
        }

    @Override
    protected void onPreExecute() {
//        mOutputText.setText("");
//        mProgress.show();
    }

    @Override
    protected void onPostExecute(List<String> output) {

        if (operation == 0)
        {
            SharedPreferences sheetId = Ma.getSharedPreferences("GoogleSheetId", Ma.MODE_PRIVATE);
            Editor edit= sheetId.edit();
            edit.putString("gSheetId", output.get(0).toString());
            edit.commit();
            Log.d("info","gSheetId="+output.get(0));
            Log.d("info","Final Value="+sheetId.getString("gSheetId",""));
        }
    }

    @Override
    protected void onCancelled() {
//        mProgress.hide();
//        if (mLastError != null) {
//            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
//                showGooglePlayServicesAvailabilityErrorDialog(
//                        ((GooglePlayServicesAvailabilityIOException) mLastError)
//                                .getConnectionStatusCode());
//            } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                startActivityForResult(
//                        ((UserRecoverableAuthIOException) mLastError).getIntent(),
//                        SyncData.REQUEST_AUTHORIZATION);
//            } else {
//                mOutputText.setText("The following error occurred:\n"
//                        + mLastError.getMessage());
//            }
//        } else {
//            mOutputText.setText("Request cancelled.");
//        }
    }
}