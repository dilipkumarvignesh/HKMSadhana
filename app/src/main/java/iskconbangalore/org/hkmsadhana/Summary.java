package iskconbangalore.org.hkmsadhana;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Summary.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Summary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Summary extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button GetButton;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;

    private OnFragmentInteractionListener mListener;

    public Summary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Summary.
     */
    // TODO: Rename and change types and number of parameters
    public static Summary newInstance(String param1, String param2) {
        Summary fragment = new Summary();
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
        view = inflater.inflate(R.layout.fragment_summary, container, false);
        GetButton = (Button)view.findViewById(R.id.GetHistory);

        GetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("GET");
            }
        });
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String Tag) {
        if (mListener != null) {
            TextView year= (TextView)view.findViewById(R.id.Year);
            String yearValue = year.getText().toString();
            TextView month= (TextView)view.findViewById(R.id.Month);
            String monthValue= month.getText().toString();
            String[] data = {yearValue,monthValue};
            Double[] retValue = (Double[]) mListener.onFragmentInteraction("Summary",data);
            LinearLayout layout=(LinearLayout)view.findViewById(R.id.LinearStatus);
            layout.setVisibility(View.VISIBLE);
            TextView Ma = (TextView) view.findViewById(R.id.MaStatus);
            Ma.setText(retValue[0].toString()+"%");
            TextView Da = (TextView) view.findViewById(R.id.DaStatus);
            Da.setText(retValue[1].toString()+"%");
            TextView Sb = (TextView) view.findViewById(R.id.SbStatus);
            Sb.setText(retValue[2].toString()+"%");
            TextView JS = (TextView) view.findViewById(R.id.JapaStatus);
            JS.setText(retValue[3].toString()+" Rounds");
            TextView RS = (TextView) view.findViewById(R.id.ReadingStatus);
            RS.setText(retValue[4].toString()+" Hours");


        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        Object onFragmentInteraction(String Tag,Object Data);
    }
}
