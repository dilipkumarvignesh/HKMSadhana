package iskconbangalore.org.hkmsadhana;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

//import android.app.DialogFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Sync.OnFragmentInteractionListener{
    TextView selectedDate;
    private DBHelper mydb ;
    int Gyear, Gmonth, Gday;


    @Override
    public void onFragmentInteraction(){
//        SyncData sync_now = new SyncData();
//        sync_now.getResultsFromApi();
        syncData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, RemainderService.class));
        mydb = new DBHelper(this);


        insertTodaySadhana();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // selectedDate = (TextView)findViewById(R.id.date);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // Button Save = (Button) findViewById(R.id.Save);
//        Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//              insertTodaySadhana();
//            }
//        });

        //Button Sync = (Button) findViewById(R.id.Sync);
//        Sync.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                syncData();
//            }
//        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                showDatePickerDialog(view);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void insertTodaySadhana()
    {
        Toast.makeText(getApplicationContext(), " Sadhana Updated Successfully.",
                Toast.LENGTH_LONG).show();
        long date = System.currentTimeMillis() / 1000L;
        Log.d("info","date="+date);
        mydb.insertSadhana(date,this,"YES","YES","YES");

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

        if (id == R.id.nav_camera) {
            fragment = new Update();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            fragment = new Remainder();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            fragment = new Sync();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_layout, fragment).commit();

//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
//            setTitle(mNavigationDrawerItemTitles[position]);
//            mDrawerLayout.closeDrawer(mDrawerList);

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
    selectedDate.setText(day+"."+month+"."+year);
        Gyear = year;
        Gmonth = month;
        Gday = day;


    }
    public void syncData()
    {
        Intent intent = new Intent(MainActivity.this, SyncData.class);
        startActivity(intent);

    }

}
