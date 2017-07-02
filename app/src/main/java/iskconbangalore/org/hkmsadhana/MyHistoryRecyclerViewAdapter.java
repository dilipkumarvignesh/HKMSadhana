package iskconbangalore.org.hkmsadhana;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import iskconbangalore.org.hkmsadhana.HistoryFragment.OnListFragmentInteractionListener;
import iskconbangalore.org.hkmsadhana.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHistoryRecyclerViewAdapter extends RecyclerView.Adapter<MyHistoryRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SadhanaUpdate> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyHistoryRecyclerViewAdapter(ArrayList<SadhanaUpdate> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
     //   holder.mItem = mValues.get(position);
       // holder.mIdView.setText(mValues.get(position).id);
        holder.mDateView.setText(mValues.get(position).updateDate);
        holder.mMAView.setText(mValues.get(position).MA);
        holder.mDAView.setText(mValues.get(position).DA);
        holder.mSBView.setText(mValues.get(position).SB);
        holder.mJPView.setText(mValues.get(position).Japa);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateView;
        public final TextView mMAView;
        public final TextView mDAView;
        public final TextView mSBView;
        public final TextView mJPView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
           // mIdView = (TextView) view.findViewById(R.id.id);
            mDateView = (TextView) view.findViewById(R.id.dateItem);
            mMAView = (TextView) view.findViewById(R.id.MAItem);
            mDAView = (TextView) view.findViewById(R.id.DAItem);
            mSBView = (TextView) view.findViewById(R.id.SBItem);
            mJPView = (TextView) view.findViewById(R.id.JPItem);
        }

      //  @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
}
}
