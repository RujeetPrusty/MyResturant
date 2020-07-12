package com.example.rujeet.myresturant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rujeet.myresturant.Models.Cuisine;
import com.example.rujeet.myresturant.Models.Users;
import com.example.rujeet.myresturant.Viewholder.CuisineViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Navigation_Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private View_Pager_Adapter view_pager_adapter;
//    private ViewPager viewPager;
//    private TabLayout tabLayout;
//    private List<Starter_Model> CuisineDataList;
//    Cuisine_Recyclerview_Adapter cuisine_recyclerview_adapter;

    RecyclerView recyclerView;
    DatabaseReference databaseReference,userDbRef;
    FirebaseRecyclerOptions<Cuisine> options;
    FirebaseRecyclerAdapter<Cuisine, CuisineViewHolder> adapter;
    private FirebaseAuth firebaseAuth;
    private String userPhonkey;
    private TextView UserName,UserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        user data in header
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser User = firebaseAuth.getCurrentUser();
        userPhonkey = User.getPhoneNumber();
//        Log.d("userPhonkey",userPhonkey);
        View headerView = navigationView.getHeaderView(0);
        UserName = (TextView) headerView.findViewById(R.id.username);
        UserEmail = (TextView) headerView.findViewById(R.id.useremail);


        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");

        userDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.child(userPhonkey).getValue(Users.class);
                UserName.setText(users.getName());
                UserEmail.setText(users.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = findViewById(R.id.cuisinerecyclerview);
        recyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cuisine");

        options = new FirebaseRecyclerOptions.Builder<Cuisine>().setQuery(databaseReference,Cuisine.class).build();

        adapter = new FirebaseRecyclerAdapter<Cuisine, CuisineViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CuisineViewHolder holder, final int position, @NonNull Cuisine model) {
                Picasso.get().load(model.getImage()).into(holder.cuisineImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
                holder.cuisineName.setText(model.getCuisinename());

               holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cuisineid = adapter.getRef(position).getKey();
                        Intent intent = new Intent(Navigation_Drawer.this,MenuList.class);
                        intent.putExtra("cuisineid",cuisineid);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CuisineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cuisine_layout,viewGroup,false);

                return new CuisineViewHolder(view);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        recyclerView.setAdapter(adapter);


//        tabLayout = findViewById(R.id.tablayout);
//        viewPager = findViewById(R.id.view_pager);
//        view_pager_adapter = new View_Pager_Adapter(getSupportFragmentManager());
//        view_pager_adapter.addFragment(new Starter_Fragment(),"Starter");
//        view_pager_adapter.addFragment(new Maincourse_Fragment(),"MainCourse");
//        view_pager_adapter.addFragment(new Dessert_Fragment(),"Dessert");
//        viewPager.setAdapter(view_pager_adapter);
//        tabLayout.setupWithViewPager(viewPager);


//        CuisineDataList = new ArrayList<>();
//        CuisineDataList.add(new Starter_Model(R.drawable.chinese,"Chinese"));
//        CuisineDataList.add(new Starter_Model(R.drawable.indian,"Indian"));
//        CuisineDataList.add(new Starter_Model(R.drawable.italian1,"Italian"));
//        cuisine_recyclerview_adapter = new Cuisine_Recyclerview_Adapter(this,CuisineDataList);

//        cuisinerecyclerView.setAdapter(cuisine_recyclerview_adapter);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_tablebooking) {

        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(Navigation_Drawer.this,Cart.class);
            startActivity(intent);

        } else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(Navigation_Drawer.this,ContactUs.class);
            startActivity(intent);

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Navigation_Drawer.this,Otp_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
