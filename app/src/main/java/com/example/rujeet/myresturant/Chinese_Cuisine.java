package com.example.rujeet.myresturant;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class Chinese_Cuisine extends AppCompatActivity {
    private View_Pager_Adapter view_pager_adapter;
    private ViewPager chinese_viewpager;
    private TabLayout chinese_tablayout;
    private Toolbar toolbar;
    private String CuisineId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese__cuisine);

        toolbar =  findViewById(R.id.chinese_toolbar);
        toolbar.setTitle("Chinese");
        setSupportActionBar(toolbar);

        CuisineId = getIntent().getStringExtra("cuisineid");
        Toast.makeText(getApplicationContext(),CuisineId,Toast.LENGTH_LONG).show();

        chinese_tablayout = findViewById(R.id.chinese_tablayout);
        chinese_viewpager = findViewById(R.id.chinese_viewpager);
        view_pager_adapter = new View_Pager_Adapter(getSupportFragmentManager());
        view_pager_adapter.addFragment(new Starter_Fragment(),"Starter");
        view_pager_adapter.addFragment(new Maincourse_Fragment(),"MainCourse");
        view_pager_adapter.addFragment(new Dessert_Fragment(),"Dessert");
        chinese_viewpager.setAdapter(view_pager_adapter);
        chinese_tablayout.setupWithViewPager(chinese_viewpager);
    }


}
