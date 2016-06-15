package com.example.sadhika.duckduckgo;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sadhika.duckduckgo.pojos.RelatedTopic;

import java.util.List;


public class DDGResultRecyclerViewAdapter extends RecyclerView.Adapter<DDGResultRecyclerViewAdapter.ViewHolder> {

    private static final int RESULT_MAX_LEN = 100;
    private final List<RelatedTopic> mValues;
    private final DDGResultFragment.OnListFragmentInteractionListener mListener;

    public DDGResultRecyclerViewAdapter(List<RelatedTopic> items, DDGResultFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_result_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTitleView.setText(mValues.get(position).getText());

        final String text  = mValues.get(position).getText();

        if (null != text && text.length() >  RESULT_MAX_LEN ) {
            final SpannableString more = new SpannableString("More");
            final SpannableString less = new SpannableString("Less");


            ClickableSpan showMore = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    CharSequence charSequence = TextUtils.concat(text, " ", less);
                    holder.mTitleView.setText(charSequence);
                }
            };
            ClickableSpan showLess = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    CharSequence charSequence =TextUtils.concat(text.substring(0, RESULT_MAX_LEN), " ", more);
                    holder.mTitleView.setText(charSequence);
                }
            };

            more.setSpan(showMore, 0, more.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            less.setSpan(showLess, 0, less.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mTitleView.setMovementMethod(LinkMovementMethod.getInstance());
            holder.mTitleView.setText(TextUtils.concat(text.substring(0, RESULT_MAX_LEN), " ", more));
        } else {
            holder.mTitleView.setText(text);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    String url = mValues.get(holder.getAdapterPosition()).getFirstURL();
                    mListener.showDDGResultDetailFragment(url);
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
        public final TextView mTitleView;
        public RelatedTopic mItem;

        public ViewHolder(View view) {
            super(view);
            mTitleView = (TextView) view.findViewById(R.id.search_title);
            mView = view;

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
