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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView selectedDay, dd, mm, yyyy;
    String setFinalDate;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_update, container, false);
        ImageButton fab = (ImageButton)view.findViewById(R.id.datePicker);

        mydb = new DBHelper(getActivity());
         TextView MaYes = (TextView) view.findViewById(R.id.MAYes);
         MaYes.setOnClickListener(this);
        TextView MaNo = (TextView) view.findViewById(R.id.MANo);
        MaNo.setOnClickListener(this);
        TextView MaLate = (TextView) view.findViewById(R.id.MALate);
        MaLate.setOnClickListener(this);
        TextView SBYes = (TextView) view.findViewById(R.id.SBYes);
        SBYes.setOnClickListener(this);
        TextView SBNo = (TextView) view.findViewById(R.id.SBNo);
        SBNo.setOnClickListener(this);
        TextView SbLate = (TextView) view.findViewById(R.id.SBLate);
        SbLate.setOnClickListener(this);
        TextView DaYes = (TextView) view.findViewById(R.id.DAYes);
        DaYes.setOnClickListener(this);
        TextView DaNo = (TextView) view.findViewById(R.id.DANo);
        DaNo.setOnClickListener(this);
        TextView DaLate = (TextView) view.findViewById(R.id.DALate);
        DaLate.setOnClickListener(this);
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
        return view;
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(this,1);
        FragmentTransaction transaction=getChildFragmentManager().beginTransaction();
        newFragment.show(transaction, "datePicker");

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

                mydb.updateSadhana(setFinalDate,"YES");
                Log.d("info","MAYES CLick:"+setFinalDate);
                break;
            case R.id.MANo:
                //DO something
                break;
            case R.id.MALate:
                //DO something
                break;
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
        Toast.makeText(getActivity(), "Date Picked  .",Toast.LENGTH_LONG).show();
        Log.d("info","hello");
        String Smonth,Sday,Syear;
        Calendar now = Calendar.getInstance();
//        int year = now.get(Calendar.YEAR);
//        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
//        int day = now.get(Calendar.DAY_OF_MONTH);
        Log.d("info","Date Values:"+month+","+day);
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
      //  Log.d("info","SelectedDate:"+MyDatest);
    }

}
