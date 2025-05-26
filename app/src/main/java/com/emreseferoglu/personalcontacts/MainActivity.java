package com.emreseferoglu.personalcontacts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper database;
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        i1 = findViewById(R.id.btnAdd);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        try {
            database = new DatabaseHelper(getApplicationContext());
        } catch (Exception error) {
            System.out.println("Veritabanı oluşturulurken bir hata oluştu" + error.toString());
        }

        i1.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            @SuppressLint("ResourceType") View view = getLayoutInflater().inflate(R.drawable.form_dialog, null);

            EditText etName = view.findViewById(R.id.et_name);
            EditText etPhone = view.findViewById(R.id.et_phone);
            EditText etEmail = view.findViewById(R.id.et_email);
            EditText etAddress = view.findViewById(R.id.et_address);

            builder.setView(view);
            builder.setTitle("Yeni Kişi");
            builder.setPositiveButton("Kaydet", (dialog, which) -> {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String address = etAddress.getText().toString();

                if(!name.isEmpty() && !phone.isEmpty()){
                    if(email.isEmpty() || address.isEmpty()){
                        database.addPerson(name,phone);
                        return;
                    }
                    database.addPerson(name,phone,email,address);
                }
                Toast.makeText(this, "Kişi eklendi...", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("İptal", (dialog, which) -> dialog.dismiss());
            builder.show();
        });





    }
}