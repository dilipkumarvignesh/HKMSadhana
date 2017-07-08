package iskconbangalore.org.hkmsadhana;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

;

//import android.app.DialogFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Sync.OnFragmentInteractionListener,
        HistoryFragment.OnListFragmentInteractionListener,Update.OnFragmentInteractionListener,
        Remainder.OnFragmentInteractionListener,Summary.OnFragmentInteractionListener,
        EasyPermissions.PermissionCallbacks{
    TextView selectedDate;
    private DBHelper mydb ;
    private String updateSheetId;
    int Gyear, Gmonth, Gday;
    int operation;


    public GoogleAccountCredential mCredential;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String BUTTON_TEXT = "Sync Data";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { SheetsScopes.SPREADSHEETS };


    @Override
    public Object onFragmentInteraction(String Tag, Object Data){
//        SyncData sync_now = new SyncData();
//        sync_now.getResultsFromApi();
        if (Tag.equals("Remainder"))
        {
            Toast.makeText(getApplicationContext(), " In Remainder",
                    Toast.LENGTH_LONG).show();
            mydb.getSadhanaHistory();
            //new MakeRequestTask(mCredential).execute();
          //  getResultsFromApi();
        }
        else if (Tag.equals("Update"))
        {
            Toast.makeText(getApplicationContext(), " In Update",
                    Toast.LENGTH_LONG).show();
            //new MakeRequestTask(mCredential).execute();
            getResultsFromApi();
        }
        else if (Tag.equals("Sync"))
        {
            ArrayList <SadhanaUpdate> UpdateSadhanaHistory = new ArrayList<>();
            SadhanaUpdate acc;
            Toast.makeText(getApplicationContext(), " In Sync",
                    Toast.LENGTH_LONG).show();

            //getResultsFromApi();
            UpdateSadhanaHistory=mydb.getSadhanaHistory();
            Log.d("info","UpdateSadhanaHistory:"+UpdateSadhanaHistory.size());
            for (int i = 0;i<UpdateSadhanaHistory.size();i++)
            {
               acc = UpdateSadhanaHistory.get(i);
                Log.d("info","Date:"+acc.updateDate.toString());
            }
        }
        else if (Tag.equals("Summary"))
        {
            Toast.makeText(getApplicationContext(), " In Summary",
                    Toast.LENGTH_LONG).show();

            String[] Summarydata = (String[])Data;
            Log.d("Info","ObjectData="+Summarydata[0].toString()+" "+Summarydata[1].toString());
            //getHistorySummary();
           //getHistorySummary(Summarydata[0].toString(),Summarydata[1].toString());
            Double[] result = mydb.getSummarydB(Summarydata[0].toString(),Summarydata[1].toString());
            return result;
        }
        return null;

    }



    @Override
    public void onListFragmentInteraction()
    {
        Toast.makeText(getApplicationContext(), "History Fragment",
                Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mydb = new DBHelper(this);
        fragmentManager.beginTransaction().replace(R.id.main_layout, new Update(),"update").commit();

        SharedPreferences FirstUse = this.getSharedPreferences("FirstUse", MODE_PRIVATE);
        String FirstInstall = FirstUse.getString("firstUse", "");

        if (FirstInstall.equals(""))
        {
            Log.d("info","FirstInstall Sadhana:"+FirstInstall);
            String sMonth="";
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);

            if(mm < 10){

                sMonth = "0" +(mm+1);
            }
            else
            {
                sMonth = ""+(mm+1);
            }
            mydb.initializeMonthlySadhana(sMonth,yy);
            Editor edit= FirstUse.edit();
            edit.putString("firstUse", "False");

            edit.commit();
            FirstInstall = FirstUse.getString("firstUse", "");
            Log.d("info","FirstInstall:"+FirstInstall);
        }
        else
        {
            Log.d("info","InitSadhanaDone");
        }

        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        startService(new Intent(this, RemainderService.class));


        Log.d("info","GCredentialsMain="+mCredential);
//        insertTodaySadhana();
        SharedPreferences sheetId = this.getSharedPreferences("GoogleSheetId", MODE_PRIVATE);
        String sheetIdValue = sheetId.getString("gSheetId", "");


        if ( sheetIdValue == "")
        {
            Toast.makeText(getApplicationContext(), " No Google Sheet Id.",
                    Toast.LENGTH_LONG).show();
            operation=0;
            getResultsFromApi();

        }
        else
        {
            updateSheetId= sheetIdValue;
            Toast.makeText(getApplicationContext(), " Google Sheet Id:"+updateSheetId,
                    Toast.LENGTH_LONG).show();
            operation = 2;
            getResultsFromApi();
        }

//        Editor edit = sheetId.edit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // selectedDate = (TextView)findViewById(R.id.date);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();
        String Tag="";
        if (id == R.id.Update) {
            fragment = new Update();
            Tag = "update";
            // Handle the camera action
        } else if (id == R.id.Summary) {
            fragment = new Summary();
            Tag = "summary";

        } else if (id == R.id.History) {
            fragment = new HistoryFragment();
            Tag = "history";

        } else if (id == R.id.Remainder) {
            fragment = new Remainder();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_layout, fragment,Tag).commit();

       }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void getSelectedDate(int year, int month, int day)
    {
    //selectedDate.setText(day+"."+month+"."+year);
        Gyear = year;
        Gmonth = month;
        Gday = day;
        Update fragment = (Update)getSupportFragmentManager().findFragmentByTag("update");
        try {
            fragment.setDate(year,month,day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
//                    mOutputText.setText(
//                            "This app requires Google Play Services. Please install " +
//                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     *     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }
    public void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
           // mOutputText.setText("No network connection available.");
        } else {

            if (operation==0) {
                Log.d("info", "Inside getResults");
                new MakeRequestTask(mCredential, updateSheetId, 0, this).execute();
            }
            else if(operation ==1)
            {
                new MakeRequestTask(mCredential, updateSheetId, 1, this).execute();
            }
            else
            {
                new MakeRequestTask(mCredential, updateSheetId, 2, this).execute();
            }
        }
    }
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                MainActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }



}
