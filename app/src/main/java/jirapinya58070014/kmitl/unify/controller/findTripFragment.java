package jirapinya58070014.kmitl.unify.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

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

public class findTripFragment extends Fragment implements View.OnClickListener {

    private FirebaseConnection firebase;
    private ListView listView;
    private List<MyTrip> myTrips;
    private TripsAdapter adapter;

    private EditText edNameTrip;
    private ImageView searchBtn;

    private static final String USER_INFO = "user_info";
    private String id_User;
    private String statusUser;

    public findTripFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_trip, container, false);
        getSharedPreferences();
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        firebase = new FirebaseConnection();
        listView = rootView.findViewById(R.id.listView);
        myTrips = new ArrayList<>();
        edNameTrip = rootView.findViewById(R.id.edNameTrip);

        searchBtn = rootView.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
    }

    public void getSharedPreferences(){
        SharedPreferences shared = getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        id_User = shared.getString("id_User", "");
    }

    @Override
    public void onClick(View view) {
        if(view == searchBtn) {
            String name_Trip = edNameTrip.getText().toString();
            showTripList(name_Trip);
        }
    }

    //--------------------------------- SHOW TRIP LIST -------------------------------------//
    private void showTripList(String name_Trip) {
        Query query = firebase.getDatabase("trips").orderByChild("name").equalTo(name_Trip);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myTrips.clear();
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
                        getStatusUser(trip, trip.getId_Trip());
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //getStatus
    private void getStatusUser(final MyTrip trip, String id_Trip) {
        statusUser=null;
        Query query = firebase.getDatabase("companions").orderByChild("id_Trip").equalTo(id_Trip);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Companions companions = postSnapshot.getValue(Companions.class);
                    if(companions.getId_User().equals(id_User)){
                        statusUser = companions.getStatus();
                    }
                }
                openTripFragment(trip, statusUser);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //------------------------------ GO TO ANOTHER PAGE -----------------------------------//
    private void openTripFragment(MyTrip trip, String statusUser){
        MainFragment.FragmentListener listener = (MainFragment.FragmentListener) getActivity();
        listener.onItemListClicked(trip, statusUser);
    }

}