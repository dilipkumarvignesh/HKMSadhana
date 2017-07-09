package iskconbangalore.org.hkmsadhana;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int SetOP;
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
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
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button MAsetButton = (Button)view.findViewById(R.id.MAButton);
        Button DAsetButton = (Button)view.findViewById(R.id.DAButton);
        Button SBsetButton = (Button)view.findViewById(R.id.SBButton);

        MAsetButton.setOnClickListener(this);
        DAsetButton.setOnClickListener(this);
        SBsetButton.setOnClickListener(this);
//        setButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimePickerDialog(v);
//            }
        //});
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.MAButton:

                SetOP = 1;
                showTimePickerDialog(v);
                break;
            case R.id.DAButton:

                SetOP = 2;
                showTimePickerDialog(v);
                break;
            case R.id.SBButton:

                SetOP = 3;
                showTimePickerDialog(v);
                break;
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String Tag,View v) {
//        if (mListener != null) {
//            showTimePickerDialog(v);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
      //  mListener = null;
    }

    public void SetTime(int hour,int minutes) throws ParseException
    {
        if (SetOP ==1) {
            TextView MATime = (TextView) view.findViewById(R.id.MATime);
            MATime.setText(hour + ":" + minutes);
        }
        else if (SetOP ==2) {
            TextView DATime = (TextView) view.findViewById(R.id.DATime);
            DATime.setText(hour + ":" + minutes);
        }
        else if (SetOP ==3) {
            TextView SBTime = (TextView) view.findViewById(R.id.SBTime);
            SBTime.setText(hour + ":" + minutes);
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setTargetFragment(this,1);
        FragmentTransaction transaction=getChildFragmentManager().beginTransaction();
        newFragment.show(transaction, "datePicker");

    }
}
