package jirapinya58070014.kmitl.unify.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import jirapinya58070014.kmitl.unify.FirebaseConnection;
import jirapinya58070014.kmitl.unify.R;
import jirapinya58070014.kmitl.unify.model.Companions;
import jirapinya58070014.kmitl.unify.model.MyTrip;


public class TripActivity extends AppCompatActivity implements infoTripFragment.FragmentListener{

    private static final String USER_INFO = "user_info";
    private String id_User;
    private String name_User, imageUrl;
    private FirebaseConnection firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trip);
        getSharedPreferences();

        MyTrip trip = getIntent().getParcelableExtra("trip"); //รับ trip จาก main
        String openFragment = getIntent().getStringExtra("openFragment");
        String statusUser = getIntent().getStringExtra("statusUser");
        initFragment(savedInstanceState, trip, openFragment, statusUser); //ส่ง trip --> add_edit_TripFragment
    }

    private void initFragment(Bundle savedInstanceState, MyTrip trip, String openFragment, String statusUser) {
        firebase = new FirebaseConnection();
        if (savedInstanceState == null) {
            if (openFragment.equals("infoTrip")) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.infoContainer, infoTripFragment.newInstance(trip, statusUser))
                        .commit();
            } else if (openFragment.equals("addNew")) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.add_edit_Container, add_edit_TripFragment.newInstance(trip, statusUser))
                        .commit();
            }
        }
    }

    public void getSharedPreferences(){
        SharedPreferences shared = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);

        id_User = shared.getString("id_User", "");
        name_User =  shared.getString("name_User", "");
        imageUrl =  shared.getString("imageUrl", "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onJoinClicked(String id_Trip) {
        //add trips table
        firebase.getDatabase("trips").child(id_Trip).child("userID: "+id_User).setValue("join");

        //add companions table
        addDataCompanion(id_Trip);

        Toast.makeText(this, "Join Success!!", Toast.LENGTH_SHORT).show();
    }

    private void addDataCompanion(String id_Trip) {
        String idCompanions = firebase.getDatabase("companions").push().getKey();
        Companions companion = new Companions();
        companion.setId_Trip(id_Trip);
        companion.setId_User(id_User);
        companion.setName(name_User);
        companion.setImagePath(imageUrl);
        companion.setStatus("join");
        companion.setId_Companions(idCompanions);
        firebase.getDatabase("companions").child(idCompanions).setValue(companion);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}