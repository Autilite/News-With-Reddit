package com.autilite.newswithreddit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.autilite.newswithreddit.data.Comment;

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
    private SubredditLinks srl;
    private ListView commentListView;

    private LinkCommentListener mListener;

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
        commentListView = (ListView) view.findViewById(R.id.link_comments);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        if (comments.size() == 0) {
            new Thread() {
                public void run() {
                    collapseComments(comments, srl.fetchTopLevelComments());
                    commentListView.post(new Runnable() {
                        @Override
                        public void run() {
                            createAdapter();
                        }
                    });
                }
            }.start();
        } else {
            createAdapter();
        }
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

    private void createAdapter() {
        if (getActivity() == null)
            return;

        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(getActivity(),
                R.layout.fragment_comment_item, comments) {
            // TODO fragment comment layout

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.fragment_comment_item, null);
                }

                TextView commentBody = (TextView) convertView.findViewById(R.id.comment_body);
                TextView commentAuthor = (TextView) convertView.findViewById(R.id.comment_author);
                TextView commentPoints = (TextView) convertView.findViewById(R.id.comment_points);
                TextView commentTime = (TextView) convertView.findViewById(R.id.comment_time_ago);

                Comment com = comments.get(position);
                commentBody.setText(com.getBody());
                commentAuthor.setText(com.getAuthor());
                if (com.isScore_hidden()) {
                    commentPoints.setText("[score hidden]");
                } else {
                    commentPoints.setText(com.getScore() + " points");
                }

                int padding = getResources().getDimensionPixelSize(R.dimen.comment_item_padding);
                int paddingLeft = padding + getResources().
                        getDimensionPixelSize(R.dimen.comment_item_level_padding)
                        * com.getLevel();
                convertView.setPadding(paddingLeft, padding, padding, padding);
                return convertView;
            }
        };
        commentListView.setAdapter(adapter);
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
