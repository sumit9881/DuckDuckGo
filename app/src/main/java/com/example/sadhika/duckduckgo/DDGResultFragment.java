package com.example.sadhika.duckduckgo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadhika.duckduckgo.pojos.DDGResponse;
import com.example.sadhika.duckduckgo.pojos.RelatedTopic;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DDGResultFragment extends Fragment {

    private static final String TAG = DDGResultFragment.class.getName();

    private static final String SEARCH_STRING = "search_string";
    private OnListFragmentInteractionListener mListener;
    private List<RelatedTopic> mSearchResults;

    RetrofitDuckDuckGoInterface mApiService;
    RecyclerView mRecyclerView;
    ContentLoadingProgressBar mLoadingBar;
    TextView mTextViewTitle;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DDGResultFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DDGResultFragment newInstance(String searchString) {
        DDGResultFragment fragment = new DDGResultFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_STRING, searchString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        String seachString = getArguments().getString(SEARCH_STRING, "");
        Call<DDGResponse> response = mApiService.getResults(seachString, "json");
        response.enqueue(new Callback<DDGResponse>() {
            @Override
            public void onResponse(Call<DDGResponse> call, Response<DDGResponse> response) {
                mLoadingBar.hide();
                mLoadingBar.setVisibility(View.GONE);
                mSearchResults = response.body().getRelatedTopics();
                mRecyclerView.setVisibility(View.VISIBLE);
                mTextViewTitle.setText(getString(R.string.search_results, mSearchResults.size()));
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(new DDGResultRecyclerViewAdapter(mSearchResults, mListener));

            }

            @Override
            public void onFailure(Call<DDGResponse> call, Throwable t) {
                mLoadingBar.hide();
                Toast.makeText(getActivity(), getString(R.string.search_failed), Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()...");

        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mApiService =
                (((DuckDuckGoApplication) getActivity().getApplication()).getmRetrofit()).create(RetrofitDuckDuckGoInterface.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ddg_result, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_result);
        mLoadingBar = (ContentLoadingProgressBar) view.findViewById(R.id.progressbar_loading);
        mTextViewTitle = (TextView) view.findViewById(R.id.tv_search_string);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new DDGResultRecyclerViewAdapter(mSearchResults, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void showDDGResultDetailFragment(String url);
     //   public void focusSearchBox();
    }

}