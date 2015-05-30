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
 * {@link com.autilite.newswithreddit.PostCommentFragment.PostCommentListener} interface
 * to handle interaction events.
 * Use the {@link PostCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostCommentFragment extends Fragment {
    private static final String ARG_URL = "threadURL";

    private String mPermaUrl;

    private PostCommentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param permaUrl
     *  The post's permanent url
     * @return A new instance of fragment PostCommentFragment.
     */
    public static PostCommentFragment newInstance(String permaUrl) {
        PostCommentFragment fragment = new PostCommentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, permaUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public PostCommentFragment() {
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
        return inflater.inflate(R.layout.fragment_post_comment, container, false);
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
            mListener = (PostCommentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PostCommentListener");
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
    public interface PostCommentListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
