package iskconbangalore.org.hkmsadhana;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Update.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Update#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Update extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
    TextView selectedDay, dd, mm, yyyy;
    String setFinalDate;
    Spinner Japa;
    ToggleButton sec1,sec,sec2;
    LinearLayout l1,l2,l1a,l1b,l2a,l2b,MaMain,SbMain,DaMain,JpMain;
    TextView t1;
    String MAStatus,DAStatus,SBStatus,JapaStatus;

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private DBHelper mydb ;
    private OnFragmentInteractionListener mListener;

    public Update() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Update.
     */
    // TODO: Rename and change types and number of parameters
    public static Update newInstance(String param1, String param2) {
        Update fragment = new Update();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }
    public void getTodayDate()
    {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        try {
            setDate(yy,mm,dd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    public void UIInit()
    {

        sec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.GONE);

                }
            }
        });

        sec1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    l1a.setVisibility(View.GONE);
                    l1b.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled
                    l1a.setVisibility(View.VISIBLE);
                    l1b.setVisibility(View.GONE);

                }
            }
        });

        sec2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    l2a.setVisibility(View.GONE);
                    l2b.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled
                    l2a.setVisibility(View.VISIBLE);
                    l2b.setVisibility(View.GONE);

                }
            }
        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update, container, false);
        ImageButton fab = (ImageButton)view.findViewById(R.id.datePicker);
        ImageButton sync = (ImageButton)view.findViewById(R.id.Sync);

        mydb = new DBHelper(getActivity());

        Button MaYes = (Button) view.findViewById(R.id.MAYes);
        MaYes.setOnClickListener(this);
        Button MaNo = (Button) view.findViewById(R.id.MANo);
        MaNo.setOnClickListener(this);
         Button MaLate = (Button) view.findViewById(R.id.MALate);
        MaLate.setOnClickListener(this);
        Button MaSick = (Button) view.findViewById(R.id.MAsick);
        MaSick.setOnClickListener(this);
        Button MaAS = (Button) view.findViewById(R.id.MAas);
        MaAS.setOnClickListener(this);
        Button Maos = (Button) view.findViewById(R.id.MAos);
        Maos.setOnClickListener(this);

        Button DaYes = (Button) view.findViewById(R.id.DAYes);
        DaYes.setOnClickListener(this);
        Button DaNo = (Button) view.findViewById(R.id.DANo);
        DaNo.setOnClickListener(this);
        Button DaLate = (Button) view.findViewById(R.id.DALate);
        DaLate.setOnClickListener(this);
        Button DaSick = (Button) view.findViewById(R.id.DAsick);
        DaSick.setOnClickListener(this);
        Button DaAS = (Button) view.findViewById(R.id.DAas);
        DaAS.setOnClickListener(this);
        Button Daos = (Button) view.findViewById(R.id.DAos);
        Daos.setOnClickListener(this);

        Button SBYes = (Button) view.findViewById(R.id.SBYes);
        SBYes.setOnClickListener(this);
        Button SBNo = (Button) view.findViewById(R.id.SBNo);
        SBNo.setOnClickListener(this);
        Button SBLate = (Button) view.findViewById(R.id.SBLate);
        SBLate.setOnClickListener(this);
        Button SBSick = (Button) view.findViewById(R.id.SBsick);
        SBSick.setOnClickListener(this);
        Button SBAS = (Button) view.findViewById(R.id.SBas);
        SBAS.setOnClickListener(this);
        Button SBos = (Button) view.findViewById(R.id.SBos);
        SBos.setOnClickListener(this);

        sync.setOnClickListener(this);

        sec = (ToggleButton)view.findViewById(R.id.btnTglSec);
        l1 = (LinearLayout)view.findViewById(R.id.lotForMa);
        l2 = (LinearLayout)view.findViewById(R.id.lotForMaRe);

        sec1 = (ToggleButton)view.findViewById(R.id.btnTglSec1);
        l1a = (LinearLayout)view.findViewById(R.id.lotForMa1);
        l1b = (LinearLayout)view.findViewById(R.id.lotForMaRe1);

        sec2 = (ToggleButton)view.findViewById(R.id.btnTglSec2);
        l2a = (LinearLayout)view.findViewById(R.id.lotForMa2);
        l2b = (LinearLayout)view.findViewById(R.id.lotForMaRe2);
        MaMain = (LinearLayout)view.findViewById(R.id.MA_MAIN);
        DaMain = (LinearLayout)view.findViewById(R.id.DA_MAIN);
        SbMain = (LinearLayout)view.findViewById(R.id.SB_MAIN);
        JpMain =(LinearLayout)view.findViewById(R.id.JP_MAIN);
        Japa = (Spinner)view.findViewById(R.id.spinner);

        UIInit();
        JapaSpinner();
//        TextView MaYes = (TextView) view.findViewById(R.id.MAYes);
//        MaYes.setOnClickListener(this);
//        TextView MaYes = (TextView) view.findViewById(R.id.MAYes);
//        MaYes.setOnClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                showDatePickerDialog(view);
            }
        });
        selectedDay = (TextView)view.findViewById(R.id.SelectedDay);
        dd = (TextView)view.findViewById(R.id.dd);
        mm = (TextView)view.findViewById(R.id.mm);
        yyyy = (TextView)view.findViewById(R.id.yyyy);
        getTodayDate();
        return view;
    }
    public void JapaSpinner()
    {

        Japa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private Boolean mIsSpinnerFirstCall = true;
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(!mIsSpinnerFirstCall) {
                    String JPValue=Japa.getSelectedItem().toString();
                    Log.d("info","JPValue:"+JPValue);
                    mydb.updateSadhana(setFinalDate,JPValue,"JAPA");
                    JpMain.setVisibility(View.GONE);
                    // Your code goes gere
                }
                mIsSpinnerFirstCall = false;
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction("Update","UpdateData");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
          //  mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    @Override
    public void onClick(View v) {
        Log.d("info","Button clicked:"+v.getId());
        switch(v.getId()){
            case R.id.MAYes:
                mydb.updateSadhana(setFinalDate,"YES","MA");
                MaMain.setVisibility(View.GONE);
                break;
            case R.id.MANo:
                mydb.updateSadhana(setFinalDate,"NO","MA");
                MaMain.setVisibility(View.GONE);
                break;
            case R.id.MALate:
                mydb.updateSadhana(setFinalDate,"LATE","MA");
                MaMain.setVisibility(View.GONE);
                break;
            case R.id.MAsick:
                mydb.updateSadhana(setFinalDate,"SICK","MA");
                MaMain.setVisibility(View.GONE);
                break;
            case R.id.MAos:
                mydb.updateSadhana(setFinalDate,"OS","MA");
                MaMain.setVisibility(View.GONE);
                break;
            case R.id.MAas:
                mydb.updateSadhana(setFinalDate,"AS","MA");
                MaMain.setVisibility(View.GONE);
                break;
            case R.id.DAYes:
                mydb.updateSadhana(setFinalDate,"YES","DA");
                DaMain.setVisibility(View.GONE);
                break;
            case R.id.DANo:
                mydb.updateSadhana(setFinalDate,"NO","DA");
                DaMain.setVisibility(View.GONE);
                break;
            case R.id.DALate:
                mydb.updateSadhana(setFinalDate,"LATE","DA");
                DaMain.setVisibility(View.GONE);
                break;
            case R.id.DAsick:
                mydb.updateSadhana(setFinalDate,"SICK","DA");
                DaMain.setVisibility(View.GONE);
                break;
            case R.id.DAos:
                mydb.updateSadhana(setFinalDate,"OS","DA");
                DaMain.setVisibility(View.GONE);
                break;
            case R.id.DAas:
                mydb.updateSadhana(setFinalDate,"AS","DA");
                DaMain.setVisibility(View.GONE);
                break;
            case R.id.SBYes:
                mydb.updateSadhana(setFinalDate,"YES","SB");
                SbMain.setVisibility(View.GONE);
                break;
            case R.id.SBNo:
                mydb.updateSadhana(setFinalDate,"NO","SB");
                SbMain.setVisibility(View.GONE);
                break;
            case R.id.SBLate:
                mydb.updateSadhana(setFinalDate,"LATE","SB");
                SbMain.setVisibility(View.GONE);
                break;
            case R.id.SBsick:
                mydb.updateSadhana(setFinalDate,"SICK","SB");
                SbMain.setVisibility(View.GONE);
                break;
            case R.id.SBos:
                mydb.updateSadhana(setFinalDate,"OS","SB");
                SbMain.setVisibility(View.GONE);
                break;
            case R.id.SBas:
                mydb.updateSadhana(setFinalDate,"AS","SB");
                SbMain.setVisibility(View.GONE);
                break;
            case R.id.Sync:
                ((MainActivity)getActivity()).startUpdate();

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener<T> {
        // TODO: Update argument type and name
        Object onFragmentInteraction(String Tag, Object Data);
    }
    public void setDate(int year,int month,int day) throws ParseException {
   //     Toast.makeText(getActivity(), "Date Picked  .",Toast.LENGTH_LONG).show();
        Log.d("info","hello");
        String Smonth,Sday;

        if(month < 10){

            Smonth = "0" +(month+1);
        }
        else
        {
            Smonth = ""+(month+1);
        }
        if(day < 10){

            Sday  = "0" + day ;
        }
        else
        {
            Sday = ""+day;
        }
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date MyDate = newDateFormat.parse(Sday+"/"+Smonth+"/"+year);
        setFinalDate = year+"/"+Smonth+"/"+Sday;
        newDateFormat.applyPattern("EEEE");
        String SelectedDay = newDateFormat.format(MyDate);
        selectedDay.setText(SelectedDay);
        newDateFormat.applyPattern("MMM");
        String SelectedMonth = newDateFormat.format(MyDate);
        mm.setText(SelectedMonth);
        dd.setText(Sday);
        yyyy.setText(""+year);
        Log.d("info","SelectedDate:"+setFinalDate);

        if ( Sday == "01")
        {
            mydb.initializeMonthlySadhana(Smonth,year);
        }


        SadhanaUpdate obtainedData = mydb.getData(setFinalDate);
        MAStatus = obtainedData.MA;

        if(!MAStatus.equals("NA"))

            MaMain.setVisibility(View.GONE);
        else
            MaMain.setVisibility(View.VISIBLE);
        DAStatus = obtainedData.DA;
        if(!DAStatus.equals("NA"))
            DaMain.setVisibility(View.GONE);
        else
        DaMain.setVisibility(View.VISIBLE);
        SBStatus = obtainedData.SB;
        if(!SBStatus.equals("NA"))
            SbMain.setVisibility(View.GONE);
        else
            SbMain.setVisibility(View.VISIBLE);
        JapaStatus = obtainedData.Japa;
        if(!JapaStatus.equals("0"))
            JpMain.setVisibility(View.GONE);
        else
            JpMain.setVisibility(View.VISIBLE);
        Log.d("info","SelectedDayInfo:"+MAStatus+"&"+DAStatus+"&"+SBStatus+"&"+JapaStatus);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(this,1);
        FragmentTransaction transaction=getChildFragmentManager().beginTransaction();
        newFragment.show(transaction, "datePicker");

    }
}
