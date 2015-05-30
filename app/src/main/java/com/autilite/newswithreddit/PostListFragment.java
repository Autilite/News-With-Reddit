package com.autilite.newswithreddit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostListFragment.PostItemCallbacks} interface
 * to handle interaction events.
 * Use the {@link PostListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostListFragment extends Fragment {
    private static final String ARG_PARAM1 = "SUBREDDIT";
    private Handler handler;

    private ListView postsList;

    private String subreddit;
    private List<Post> posts;
    private SubredditPosts loposts;

    private PostItemCallbacks mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subreddit Parameter 1.
     * @return A new instance of fragment PostListFragment.
     */
    public static PostListFragment newInstance(String subreddit) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, subreddit);
        fragment.setArguments(args);
        fragment.loposts = new SubredditPosts(subreddit);
        fragment.subreddit = subreddit;
        return fragment;
    }

    public PostListFragment() {
        // Required empty public constructor
        handler = new Handler();
        posts = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subreddit = getArguments().getString(ARG_PARAM1);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);
        postsList = (ListView) view.findViewById(R.id.post_list);
        postsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post) parent.getItemAtPosition(position);
                mListener.onPostSelect(post);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        // This should run only once for the fragment as the
        // setRetainInstance(true) method has been called on
        // this fragment

        if (posts.size() == 0) {
            // Must execute network tasks outside the UI
            // thread. So create a new thread.

            new Thread() {
                public void run() {
                    posts.addAll(loposts.fetchPosts());

                    // UI elements should be accessed only in
                    // the primary thread, so we must use the
                    // handler here.

                    handler.post(new Runnable() {
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

    private void createAdapter(){
        // Make sure this fragment is still a part of the activity.
        if (getActivity() == null)
            return;

        ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(getActivity(), R.layout.fragment_post_item, posts) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.fragment_post_item, null);
                }

                TextView postTitle = (TextView) convertView.findViewById(R.id.post_title);
                TextView postStats = (TextView) convertView.findViewById(R.id.post_stats);
                TextView postDetails = (TextView) convertView.findViewById(R.id.post_author);

                String delim = " - ";
                Post post = posts.get(position);
                StringBuilder stats = new StringBuilder();
                stats.append(post.getNum_comments()).append(" comments")
                        .append(delim).append(post.getScore()).append(" pts");
                StringBuilder author = new StringBuilder();
                author.append(post.getAuthor())
                        .append(delim).append(post.getSubreddit())
                        .append(delim).append(post.getDomain());


                postTitle.setText(post.getTitle());
                postStats.setText(stats.toString());
                postDetails.setText(author.toString());
                return convertView;
            }
        };
        postsList.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Post post) {
        if (mListener != null) {
            mListener.onPostSelect(post);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PostItemCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PostItemCallbacks");
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
    public interface PostItemCallbacks {
        public void onPostSelect(Post post);
    }

}
