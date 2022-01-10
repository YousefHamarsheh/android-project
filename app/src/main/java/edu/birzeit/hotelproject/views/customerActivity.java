package edu.birzeit.hotelproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Customer;
import edu.birzeit.hotelproject.models.Receptionist;

public class customerActivity extends AppCompatActivity {
    List<Customer>customerList;
    List<String> customerStrings;
    Gson gson=new Gson();
    ListView listView;
    String cusString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer);
        Intent intent=getIntent();
        cusString=intent.getStringExtra(ReceptionMenue.CUSTOMERS_MESSAGE);
        listView=findViewById(R.id.lstcustomers);
        getCustomers();

    }

    public void getCustomers(){
        customerList=new ArrayList<>();
        customerStrings =new ArrayList<>();

                        Customer[]customersArray=gson.fromJson(cusString,Customer[].class);

                        for (Customer customer : customersArray) {
                            customerStrings.add(customer.getCustomer_name()+ " " + customer.getCustomer_username());
                            customerList.add(customer);
                        }

                        String[]arr=new String[customerStrings.size()];
                        arr=customerStrings.toArray(arr);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                customerActivity.this, android.R.layout.simple_list_item_1,
                                arr);
                        listView.setAdapter(adapter);


    }



}