package com.autilite.newswithreddit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.autilite.newswithreddit.LinkCommentFragment.LinkCommentListener} interface
 * to handle interaction events.
 * Use the {@link LinkCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinkCommentFragment extends Fragment {
    private static final String ARG_URL = "threadURL";

    private String mPermaUrl;

    private LinkCommentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param permaUrl
     *  The link's permanent url
     * @return A new instance of fragment LinkCommentFragment.
     */
    public static LinkCommentFragment newInstance(String permaUrl) {
        LinkCommentFragment fragment = new LinkCommentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, permaUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public LinkCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPermaUrl = getArguments().getString(ARG_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_link_comment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (LinkCommentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LinkCommentListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface LinkCommentListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
