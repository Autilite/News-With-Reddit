package com.autilite.newswithreddit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.autilite.newswithreddit.data.Comment;
import com.autilite.newswithreddit.data.Link;
import com.autilite.newswithreddit.fetcher.SubredditLinks;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.autilite.newswithreddit.LinkCommentFragment.LinkCommentListener} interface
 * to handle interaction events.
 * Use the {@link LinkCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinkCommentFragment extends Fragment {
    private static final String ARG_URL = "link_id";
    private static final String ARG_SUBREDDIT = "link_subreddit";

    private String mLinkId;
    private String mSubreddit;

    private List<Comment> comments;
    private Link link;
    private SubredditLinks srl;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private LinkCommentListener mListener;

    private ProgressBar mProgressBar;
    private ViewGroup mContainer;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param linkId
     *  The link's id
     * @param subreddit
     *  The subreddit the link belongs to
     *
     * @return A new instance of fragment LinkCommentFragment.
     */
    public static LinkCommentFragment newInstance(String subreddit, String linkId) {
        LinkCommentFragment fragment = new LinkCommentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, linkId);
        args.putString(ARG_SUBREDDIT, subreddit);
        fragment.setArguments(args);
        fragment.srl = new SubredditLinks(subreddit, linkId);
        return fragment;
    }

    public LinkCommentFragment() {
        // Required empty public constructor
        comments = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLinkId = getArguments().getString(ARG_URL);
            mSubreddit = getArguments().getString(ARG_SUBREDDIT);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_link_comment, container, false);
        mProgressBar = (ProgressBar) inflater.inflate(R.layout.progress_bar_circular, container, false);
        container.addView(mProgressBar);
        mContainer = container;

        // Setup the comments view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.comment_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (comments.size() == 0) {
            fetchComments();
        }
    }

    private void fetchComments() {
        new Thread() {
            public void run() {
                collapseComments(comments, srl.fetchTopLevelComments());
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        link = srl.getLink();
                        mAdapter = new CommentAdapter(getActivity(), link, comments);
                        mRecyclerView.setAdapter(mAdapter);
                        mContainer.removeView(mProgressBar);
                    }
                });
            }
        }.start();
    }

    /**
     * Collapses a list of linked list into one in depth first search
     * @param acc
     *      The list which accumulates the comments
     * @param toAdd
     *      The linked list to collapse
     */
    private void collapseComments(List<Comment> acc, List<Comment> toAdd) {
        for (Comment c : toAdd) {
            acc.add(c);
            collapseComments(acc, c.getReplies());
        }
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
