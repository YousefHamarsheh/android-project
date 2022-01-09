package edu.birzeit.hotelproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.birzeit.hotelproject.MainActivity;
import edu.birzeit.hotelproject.R;

public class SignUpActivity extends AppCompatActivity {
    EditText dateformats;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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


    }
}