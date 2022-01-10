package edu.birzeit.hotelproject.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Customer;

public class customerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Customer> customersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);

        customersList = new ArrayList<>();
        fetchMovies();
    }


    private void fetchMovies() {

        String url = "https://www.json-generator.com/api/json/get/cfsXpFGwwO?indent=2";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("customer_id");
                                String name = jsonObject.getString("customer_name");
                                String username= jsonObject.getString("customer_username");
                                String password = jsonObject.getString("customer_password");
                                String visa=jsonObject.getString("customer_visa");
                                String phone=jsonObject.getString("customer_phone");
                                String dateOfBirth=jsonObject.getString("dateOfBirth");

                                Customer customer=new Customer(id,name,username,password,visa,phone,dateOfBirth);

                                customersList.add(customer);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            MovieAdapter adapter = new MovieAdapter(MainActivity.this , movieList);

//                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(customerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}