package com.emreseferoglu.personalcontacts;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    TabLayout tabLayout;
    ViewPager viewPager;


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

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);





        try {
            database=this.openOrCreateDatabase("PersonelContacts",MODE_PRIVATE,null);
            String createTableQuerySql="CREATE TABLE IF NOT EXISTS contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, person_name text NOT NULL,phone_number text NOT NULL CHECK(length(phone_number) = 11),email text, address text,created_at TEXT DEFAULT CURRENT_TIMESTAMP)";
            database.execSQL(createTableQuerySql);
            Toast.makeText(this, "Veritabanı ve tablo oluşturuldu", Toast.LENGTH_LONG).show();
        } catch (Exception error){
            System.out.println("Veritabanı oluşturulurken bir hata oluştu"+error.toString());
        }






    }




}