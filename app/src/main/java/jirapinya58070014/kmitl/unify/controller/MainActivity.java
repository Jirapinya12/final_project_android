package jirapinya58070014.kmitl.unify.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import jirapinya58070014.kmitl.unify.R;
import jirapinya58070014.kmitl.unify.controller.adapter.SectionsPageAdapter;
import jirapinya58070014.kmitl.unify.model.MyTrip;

import static android.support.design.widget.NavigationView.*;

public class MainActivity extends AppCompatActivity implements MainFragment.FragmentListener, OnNavigationItemSelectedListener{

    private static final String USER_INFO = "user_info";
    private String name_User;
    private String email;
    private String imageUrl;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSharedPreferences();
        initInstances();
    }

    public void getSharedPreferences(){
        SharedPreferences shared = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        name_User =  shared.getString("name_User", "");
        email =  shared.getString("email", "");
        imageUrl =  shared.getString("imageUrl", "");
    }

    private void initInstances() {

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //Navigation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        new DownloadImage((ImageView) headerView.findViewById(R.id.profile_image)).execute(imageUrl);
        TextView nameText = headerView.findViewById(R.id.username);
        nameText.setText(name_User);
        TextView emailText = headerView.findViewById(R.id.email);
        emailText.setText(email);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onItemListClicked(MyTrip trip, String statusUser) {
        Intent intent = new Intent(this,TripActivity.class);
        intent.putExtra("trip",trip);
        intent.putExtra("statusUser",statusUser);
        intent.putExtra("openFragment", "infoTrip");
        startActivity(intent);
    }

    @Override
    public void onAddEditBtnClicked(MyTrip trip) {
        Intent intent = new Intent(this,TripActivity.class);
        intent.putExtra("trip",trip);
        intent.putExtra("openFragment", "addNew");
        startActivity(intent);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), "My Trips");
        adapter.addFragment(new findTripFragment(), "Find Trip");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_allTrips) {
            mViewPager.setCurrentItem(0);

        } else if (id == R.id.nav_join) {
            mViewPager.setCurrentItem(1);

        } else if (id == R.id.nav_addnew) {
            onAddEditBtnClicked(new MyTrip());

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            openLoginActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Show Image
    @SuppressLint("StaticFieldLeak")
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }


    //------------------------------ GO TO ANOTHER PAGE -----------------------------------//
    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
