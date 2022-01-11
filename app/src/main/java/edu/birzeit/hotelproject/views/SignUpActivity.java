package edu.birzeit.hotelproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.birzeit.hotelproject.MainActivity;
import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Customer;
import edu.birzeit.hotelproject.services.LoginService;

public class SignUpActivity extends AppCompatActivity {
    EditText dateformats;
    int year, month, day;
    private String name;
    EditText nameEditText,usernameEditText,passwordEditText,visaEditText,phoneEditText;
    private String username;
    private String password;
    private String visa;
    private String phone;
    private String dateOfBirth;
    private String customerMessage;
    private List<Customer>customerList;
    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent intent=getIntent();
        customerMessage=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        dateformats = findViewById(R.id.date_format);
        Calendar cal = Calendar.getInstance();
        dateformats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateformats.setText(SimpleDateFormat.getDateInstance().format(cal.getTime()));
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        findViewById(R.id.text_signIn_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        setViews();


    }

    private void setViews() {
        nameEditText=findViewById(R.id.nameId);
        usernameEditText=findViewById(R.id.usernameId);
        passwordEditText=findViewById(R.id.passwordId);
        visaEditText=findViewById(R.id.visaId);
        phoneEditText=findViewById(R.id.phoneId);
    }
    private void getFieldsString(){
        name=nameEditText.getText().toString();
        username=usernameEditText.getText().toString();
        password=passwordEditText.getText().toString();
        visa=passwordEditText.getText().toString();
        phone=phoneEditText.getText().toString();
        dateOfBirth=dateformats.getText().toString();
    }


    public void createAccount(View view) {
        getFieldsString();
        if (name.length()==0 || username.length()==0 || password.length()==0 || visa.length()==0 || phone.length()==0 || dateOfBirth.length()==0) {
            Toast.makeText(SignUpActivity.this, "please fill required data", Toast.LENGTH_LONG).show();
        }else {
            getCustomers();
            if (LoginService.isCustomer(username,password,customerList)){
                Toast.makeText(SignUpActivity.this, "this is already existed account", Toast.LENGTH_LONG).show();
            }else {
                if (LoginService.isUsernameUsed(username,customerList)){
                    Toast.makeText(SignUpActivity.this, "username is already used ,use another username", Toast.LENGTH_LONG).show();
                }
            }
        }



    }
    private void getCustomers(){
        customerList=new ArrayList<>();
        Customer[]customerArray=gson.fromJson(customerMessage,Customer[].class);
        for (Customer customer : customerArray) {
            customerList.add(customer);
        }

    }


    class PostCustomer implements Runnable{

        @Override
        public void run() {
            String url="http://127.0.0.1/hotel_app_backend/controllers/customerController/post.php";
        }
    }
}