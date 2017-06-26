package iskconbangalore.org.hkmsadhana;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    MakeRequestTask(GoogleAccountCredential credential,String updateId,Integer operation) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Sheets API Android Quickstart")
                .build();
        Log.d("info","Inside Constructor ");
        this.updateSheetId = updateId;
        this.operation = operation;
    }

    /**
     * Background task to call Google Sheets API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected List<String> doInBackground(Void... params) {
        try {
                //return determine_call();
                if (operation ==0)
                    return updateSheet();
                else if(operation ==1)
                    return getDataFromApi();
                else if (operation ==2)
                    return createSheet();


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
    public List<String> createSheet() throws IOException{
        Spreadsheet requestBody = new Spreadsheet();
        Sheets.Spreadsheets.Create request = this.mService.spreadsheets().create(requestBody);

        Spreadsheet response = request.execute();
        String spreadSheetId = response.getSpreadsheetId();
        Log.d("info","SpreadSheetId="+spreadSheetId);
        List<String> results = new ArrayList<String>();
        results.add(spreadSheetId);
        return results;

    }
    private List<String> getDataFromApi() throws IOException {
        //String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
     //   String spreadsheetId = "1xk8AY8MOWiqwC3qvFEyOVN-wBdMtDW8QtirmcUkocrU";
        // String range = "Class Data!A2:E";
        String range = "A2:E";
        Log.d("info","Insie getDataFromAPi");
        Log.d("info","GID="+updateSheetId);
        List<String> results = new ArrayList<String>();
            ValueRange response = this.mService.spreadsheets().values()
                    .get(updateSheetId, range)
                    .execute();
        Log.d("info","response="+response);


            List<List<Object>> values = response.getValues();
            Log.d("info","response="+values);
            if (values != null) {
                results.add("Name,Number,Status,FolkID,FG");
                for (List row : values) {
                    results.add(row.get(0) + ", "+row.get(1)+"," +row.get(2)+"," +row.get(3)+"," +row.get(4));
                }
            }
        Log.d("info","results"+results);
        return results;
    }

        public List<String> updateSheet() throws IOException
        {   String range = "A11:E11";
            Log.d("info","Inside updateSheet");
            List<Object> data1 = new ArrayList<Object>();
            data1.add ("Ishaan");
            data1.add("6544");
            data1.add ("Tentative");
            data1.add("4123");
            data1.add("SRID");
            List<String> results = new ArrayList<String>();


            List<List<Object>> data = new ArrayList<List<Object>>();
            data.add (data1);

            ValueRange body = new ValueRange()
                    .setValues(data);
            UpdateValuesResponse result =
                    this.mService.spreadsheets().values().update(updateSheetId, range, body)
                            .setValueInputOption("USER_ENTERED")
                            .execute();
            return results;
        }

    @Override
    protected void onPreExecute() {
//        mOutputText.setText("");
//        mProgress.show();
    }

    @Override
    protected void onPostExecute(List<String> output) {
//        mProgress.hide();
//        if (output == null || output.size() == 0) {
//            mOutputText.setText("No results returned.");
//        } else {
//            output.add(0, "Data retrieved using the Google Sheets API:");
//            mOutputText.setText(TextUtils.join("\n", output));
//        }
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