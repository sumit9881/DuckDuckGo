package com.example.sadhika.duckduckgo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements DDGResultFragment.OnListFragmentInteractionListener, ResultDetailFragment.OnFragmentInteractionListener{
    private static final String TAG = MainActivity.class.getName();
    private static final String TAG_RESULT_FRAG = "tag_result_frag";

    private EditText mEditTextSearch;
    private Button mButtonSearchCancel;
    DDGResultFragment  mResultFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSearchCancel = (Button) findViewById(R.id.search_cancel);
        mButtonSearchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditTextSearch.setText("");
            }
        });

        mResultFrag = (DDGResultFragment) getSupportFragmentManager().findFragmentByTag(TAG_RESULT_FRAG);

        mEditTextSearch = (EditText) findViewById(R.id.edittext_search);
        addSearchEditTextAction();
        if (null == mResultFrag || mResultFrag.isDetached())
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    private void addSearchEditTextAction() {
        mEditTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                mEditTextSearch.clearFocus();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mResultFrag = DDGResultFragment.newInstance(v.getText().toString());
                    getSupportFragmentManager().beginTransaction().add(R.id.main_layout, mResultFrag, TAG_RESULT_FRAG).addToBackStack(TAG_RESULT_FRAG).commit();

                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void showDDGResultDetailFragment(String url) {
        if (null !=  url) {
            ResultDetailFragment frag = ResultDetailFragment.newInstance(url);
            getSupportFragmentManager().beginTransaction().add(R.id.main_layout, frag).addToBackStack(null).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void giveFocusToSearchBox() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditTextSearch, InputMethodManager.SHOW_IMPLICIT);
        mEditTextSearch.requestFocus();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count != 0 && getSupportFragmentManager().getBackStackEntryAt(count - 1).getName().equals(TAG_RESULT_FRAG)) {
            giveFocusToSearchBox();
        }

        super.onBackPressed();
    }

}
