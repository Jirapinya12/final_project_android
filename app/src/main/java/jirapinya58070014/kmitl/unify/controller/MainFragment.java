package jirapinya58070014.kmitl.unify.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jirapinya58070014.kmitl.unify.FirebaseConnection;
import jirapinya58070014.kmitl.unify.R;
import jirapinya58070014.kmitl.unify.controller.adapter.TripsAdapter;
import jirapinya58070014.kmitl.unify.model.Companions;
import jirapinya58070014.kmitl.unify.model.MyTrip;


public class MainFragment extends Fragment {

    private static final String USER_INFO = "user_info";
    private FirebaseConnection firebase;
    private ListView listView;
    private List<MyTrip> myTrips;
    private TripsAdapter adapter;

    private String id_User;
    private String path;
    private String statusUser;

    public MainFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_trips, container, false);
        animationLinearLayout(rootView);
        initInstances(rootView);
        getSharedPreferences();
        showTripList();
        return rootView;
    }

    private void initInstances(View rootView) {
        firebase = new FirebaseConnection();
        listView = rootView.findViewById(R.id.listView);
        myTrips = new ArrayList<>();

        FloatingActionButton addBtn = rootView.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTrip trip = new MyTrip();
                openAddEditTripFragment(trip);
            }
        });
    }

    public void getSharedPreferences(){
        SharedPreferences shared = getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        id_User = shared.getString("id_User", "");
        path = "userID: "+id_User;
    }

    //------------------------------- SHOW TRIP LIST -----------------------------------//
    private void showTripList() {
        Query query = firebase.getDatabase("trips").orderByChild(path).equalTo("join");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myTrips.clear();
                statusUser=null;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    MyTrip trip = postSnapshot.getValue(MyTrip.class);
                    myTrips.add(trip);
                }
                adapter = new TripsAdapter(myTrips);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MyTrip trip = myTrips.get(i);
                        getStatusUser(trip);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getStatusUser(final MyTrip trip) {
        Query query = firebase.getDatabase("companions").orderByChild("id_Trip").equalTo(trip.getId_Trip());
        query.addValueEventListener(new ValueEventListener(

        ) {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Companions companions = postSnapshot.getValue(Companions.class);
                    if(companions.getId_User().equals(id_User)){
                        statusUser = companions.getStatus();
                        openTripFragment(trip, statusUser);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //------------------------------ implement interface -----------------------------------//
    interface FragmentListener {
        void onItemListClicked(MyTrip trip, String statusUser);
        void onAddEditBtnClicked(MyTrip trip);
    }

    //------------------------------ GO TO ANOTHER PAGE -----------------------------------//
    private void openAddEditTripFragment(MyTrip trip) {
        FragmentListener listener = (FragmentListener) getActivity();
        listener.onAddEditBtnClicked(trip); //Call onAddNewBtnClicked in MainActivity
    }

    private void openTripFragment(MyTrip trip, String statusUser){
        FragmentListener listener = (FragmentListener) getActivity();
        listener.onItemListClicked(trip, statusUser); //Call onItemListClicked in MainActivity
    }

    //------------------------------ animationLinearLayout -----------------------------------//
    private void animationLinearLayout(View rootView) {
        RelativeLayout relativeListview = rootView.findViewById(R.id.relativeListview);
        Animation downtoup = AnimationUtils.loadAnimation(getContext(), R.anim.downtoup);
        relativeListview.setAnimation(downtoup);
    }
}