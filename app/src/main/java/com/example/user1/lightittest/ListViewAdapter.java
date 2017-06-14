package com.example.user1.lightittest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user1.lightittest.Model.Product;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class ListViewAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Product> mProducts;

    ListViewAdapter(Context context, List<Product> object) {
        mContext = context;
        mProducts = object;
    }

    void addProducts(List<Product> list){
        mProducts = list;
    }

    @Override
    public int getCount() {
        if (mProducts != null){
            return mProducts.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
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
            view = mInflater.inflate(R.layout.list_view_item, parent, false);
        } else {
            view = convertView;
        }

        TextView textViewProductTitle = (TextView) view.findViewById(R.id.productTitle);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgProductsList);
        textViewProductTitle.setText(mProducts.get(position).getProductTitle());
        Ion.with(imageView)
                .load(ApiClient.STATIC_URL + mProducts.get(position).getImageName());

        return view;
    }
}
