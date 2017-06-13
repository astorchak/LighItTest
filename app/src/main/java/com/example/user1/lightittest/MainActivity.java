package com.example.user1.lightittest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user1.lightittest.Model.Product;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "====================";

    private ListView listView;

    private ArrayAdapter<Product> arrayAdapter;

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lvProducts);

        inflater = this.getLayoutInflater();

        getProducts();

    }

    public void getProducts() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull final Response<List<Product>> response) {
                arrayAdapter = new ArrayAdapter<Product>(MainActivity.this, android.R.layout.simple_list_item_1, response.body()){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = inflater.inflate(R.layout.list_view_item, parent, false);
                        Product product = response.body().get(position);
                        TextView textViewProductTitle = (TextView) view.findViewById(R.id.productTitle);
                        textViewProductTitle.setText(product.getProductTitle());
                        return view;
                    }
                };
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.d(TAG, "Error on Failure " + t);
            }
        });
    }
}

