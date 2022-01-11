package edu.birzeit.hotelproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Receptionist;
import edu.birzeit.hotelproject.models.Room;


public class RoomActivity extends AppCompatActivity {
    private ListView roomListView;
    private RequestQueue queue;
    private Gson gson = new Gson();
    private List<String> rooms = new ArrayList<>();
    String url = "http://10.0.2.2/hotel_app_backend/controllers/RoomController/get.php";
    List<Room>roomList=new ArrayList<>();
    List<Room>singleRoom,doubleRoom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        BottomNavigationView BNV = findViewById(R.id.nav_id);

        BNV.setSelectedItemId(R.id.roomsBooking);

        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homepage:
                        startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.roomsBooking:
                        return true;

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.contactus:
                        startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), LogoutActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        queue = Volley.newRequestQueue(this);
        roomListView=findViewById(R.id.listRoom_id);
        GetData getData=new GetData();
        Thread thread=new Thread(getData);
        thread.start();

    }


    class GetData implements Runnable{

        @Override
        public void run() {
         getRooms();

        }
    }

    class CollectRoomType implements Runnable{

        @Override
        public void run() {
            singleRoom=new ArrayList<>();
            doubleRoom=new ArrayList<>();
            for (Room room : roomList) {
                if (room.getRoom_type().equalsIgnoreCase("single")){
                    singleRoom.add(room);
                }else{
                    doubleRoom.add(room);
                }
            }
            Log.d("length single room",singleRoom.size()+"");
        }
    }

    public void getRooms() {

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

            //Response is a string that needs to be converted to an a json object then json array
                    JSONObject  jsnobject =null;
                    JSONArray  jsonArray=null;
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsnobject = new JSONObject(response);
                            Log.d("room json object",jsnobject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonArray = jsnobject.getJSONArray("rooms");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONArray  finalJsonArray = jsonArray;
                        String g = finalJsonArray.toString();
                        Log.d("room messages",g);
                        Room[] roomsArray = gson.fromJson(g, Room[].class);

                        for (Room room : roomsArray) {
                            rooms.add("Number : " + room.getRoom_number() + " " + "Type :" + room.getRoom_type() + "  price : " + room.getRoom_price());
                            roomList.add(room);
                        }

                        String[]arr=new String[rooms.size()];
                        arr=rooms.toArray(arr);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                RoomActivity.this, android.R.layout.simple_list_item_1,
                                arr);
                        roomListView.setAdapter(adapter);
                        CollectRoomType collectRoomType=new CollectRoomType();
                        Thread thread=new Thread(collectRoomType);
                        thread.start();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }
}