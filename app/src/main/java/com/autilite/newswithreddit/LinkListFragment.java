package com.autilite.newswithreddit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.autilite.newswithreddit.data.Link;

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
    private Handler handler;

    private ListView linksList;

    private String subreddit;
    private List<Link> links;
    private SubredditLinks lolinks;

    private LinkItemCallbacks mListener;

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
        fragment.lolinks = new SubredditLinks(subreddit);
        fragment.subreddit = subreddit;
        return fragment;
    }

    public LinkListFragment() {
        // Required empty public constructor
        handler = new Handler();
        links = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.fragment_link_list, container, false);
        linksList = (ListView) view.findViewById(R.id.link_list);
        linksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Link link = (Link) parent.getItemAtPosition(position);
                mListener.onLinkSelect(link);
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

        if (links.size() == 0) {
            // Must execute network tasks outside the UI
            // thread. So create a new thread.

            new Thread() {
                public void run() {
                    links.addAll(lolinks.fetchLinks());

                    // UI elements should be accessed only in
                    // the primary thread, so we must use the
                    // handler here.

                    linksList.post(new Runnable() {
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

        ArrayAdapter<Link> adapter = new ArrayAdapter<Link>(getActivity(), R.layout.fragment_link_item, links) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.fragment_link_item, null);
                }

                TextView linkTitle = (TextView) convertView.findViewById(R.id.link_title);
                TextView linkStats = (TextView) convertView.findViewById(R.id.link_stats);
                TextView linkDetails = (TextView) convertView.findViewById(R.id.link_author);

                String delim = " - ";
                Link link = links.get(position);
                StringBuilder stats = new StringBuilder();
                stats.append(link.getNum_comments()).append(" comments")
                        .append(delim).append(link.getScore()).append(" pts");
                StringBuilder author = new StringBuilder();
                author.append(link.getAuthor())
                        .append(delim).append(link.getSubreddit())
                        .append(delim).append(link.getDomain());


                linkTitle.setText(link.getTitle());
                linkStats.setText(stats.toString());
                linkDetails.setText(author.toString());
                return convertView;
            }
        };
        linksList.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Link link) {
        if (mListener != null) {
            mListener.onLinkSelect(link);
        }
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