package com.autilite.newswithreddit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.autilite.newswithreddit.data.Link;
import com.autilite.newswithreddit.fetcher.LinkFetcher;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.autilite.newswithreddit.LinkListFragment.LinkItemCallbacks} interface
 * to handle interaction events.
 * Use the {@link LinkListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinkListFragment extends Fragment {
    private static final String ARG_PARAM1 = "SUBREDDIT";

    private ListView linksList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String mSubreddit;
    private List<Link> links;
    private LinkFetcher fetcher;

    private LinkItemCallbacks mListener;
    private ProgressBar mProgressBar;
    private ViewGroup mContainer;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subreddit Parameter 1.
     * @return A new instance of fragment LinkListFragment.
     */
    public static LinkListFragment newInstance(String subreddit) {
        LinkListFragment fragment = new LinkListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, subreddit);
        fragment.setArguments(args);
        fragment.fetcher = new LinkFetcher(subreddit);
        fragment.mSubreddit = subreddit;
        return fragment;
    }

    public LinkListFragment() {
        // Required empty public constructor
        links = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSubreddit = getArguments().getString(ARG_PARAM1);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_link_list, container, false);
        mProgressBar = (ProgressBar) inflater.inflate(R.layout.progress_bar_circular, container, false);
        mContainer = container;

        mRecyclerView = (RecyclerView) view.findViewById(R.id.link_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new LinkAdapter(links);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (links.size() != 0) {
            return;
        }
        mContainer.addView(mProgressBar);
        new Thread() {
            public void run() {
                links.addAll(fetcher.fetch());

                // UI elements should be accessed only on the primary thread
                // Run adapter on the list view
                mRecyclerView.post(new Runnable() {
                    public void run() {
                        mContainer.removeView(mProgressBar);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LinkAdapter) mAdapter).setOnLinkClickListener(mListener);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (LinkItemCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LinkItemCallbacks");
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
    public interface LinkItemCallbacks {
        public void onLinkSelect(Link link);
    }

}
