package com.example.user1.lightittest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.user1.lightittest.Model.Product;
import com.example.user1.lightittest.Model.Review;
import com.koushikdutta.ion.Ion;

import java.io.Serializable;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ListViewRewiewsAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Review> mReviews;

    ListViewRewiewsAdapter(Context context, List<Review> object) {
        mContext = context;
        mReviews = object;
    }

    void addReviews(List<Review> list){
        mReviews = list;
    }

    @Override
    public int getCount() {
        if (mReviews != null){
            return mReviews.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;

        if (mInflater == null) {
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            view = mInflater.inflate(R.layout.list_view_reviews_item, parent, false);
        } else {
            view = convertView;
        }

        TextView textViewReviewText = (TextView) view.findViewById(R.id.reviewText);
        TextView textViewReviewAuthor = (TextView) view.findViewById(R.id.reviewAuthor);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBarListView);
        TextView textViewDate = (TextView) view.findViewById(R.id.tvDate);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            calendar.setTime(simpleDateFormat.parse(mReviews.get(position).getReviewDateTime()));
        } catch (ParseException e) {
            try {
                calendar.setTime(simpleDateFormat2.parse(mReviews.get(position).getReviewDateTime()));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

        textViewReviewText.setText(mReviews.get(position).getText());
        textViewReviewAuthor.setText(mReviews.get(position).getUser().getUserName());
        ratingBar.setRating(mReviews.get(position).getRate());
        textViewDate.setText(String.valueOf(calendar.get(Calendar.YEAR)) + "-" + String.valueOf(calendar.get(Calendar.MONTH)) + "-" +  String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        return view;
    }
}
