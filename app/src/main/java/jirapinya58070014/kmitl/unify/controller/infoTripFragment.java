package jirapinya58070014.kmitl.unify.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jirapinya58070014.kmitl.unify.FirebaseConnection;
import jirapinya58070014.kmitl.unify.R;
import jirapinya58070014.kmitl.unify.controller.adapter.CompanionsImageAdapter;
import jirapinya58070014.kmitl.unify.model.Companions;
import jirapinya58070014.kmitl.unify.model.MyTrip;

public class infoTripFragment extends Fragment implements View.OnClickListener ,OnMapReadyCallback {

    private String id_Trip, name, description, imagePath;
    private String time, beginDate, endDate;
    private String location, latitude, longitude;
    private Double lat, lng;

    private TextView btnBack, btnEdit;
    private TextView nameTrip;
    private ImageView imageTrip;
    private TextView showNameTrip, showDescriptionTrip;
    private TextView showDateTrip, showTimeTrip, showLocationTrip;
    private TextView companionsBtn;
    private Button joinBtn;

    private FirebaseConnection firebase;
    private RecyclerView companionsRecyclerView;
    private List<Companions> companions;
    private CompanionsImageAdapter adapter;

    private MyTrip trip;
    private String statusUser;
    private GoogleMap mGoogleMap;

    public infoTripFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trip = getArguments().getParcelable("trip");
        statusUser = getArguments().getString("statusUser");
    }

    public static infoTripFragment newInstance(MyTrip trip, String statusUser) {
        infoTripFragment fragment = new infoTripFragment();
        Bundle args = new Bundle();
        args.putParcelable("trip", trip);
        args.putString("statusUser", statusUser);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_trip, container, false);
        initInstances(rootView);
        checkStatus(statusUser);
        initMap(rootView);
        checkData();
        showCompanionsList();

        return rootView;
    }

    private void initInstances(View rootView) {
        firebase = new FirebaseConnection();
        companions = new ArrayList<>();

        companionsRecyclerView = rootView.findViewById(R.id.companionsRecyclerView);
        adapter = new CompanionsImageAdapter(getContext());
        nameTrip = rootView.findViewById(R.id.nameTrip);

        imageTrip = rootView.findViewById(R.id.imageTrip);
        showNameTrip = rootView.findViewById(R.id.showNameTrip);
        showDescriptionTrip = rootView.findViewById(R.id.showDescriptionTrip);
        showTimeTrip = rootView.findViewById(R.id.showTimeTrip);
        showDateTrip = rootView.findViewById(R.id.showDateTrip);
        showLocationTrip = rootView.findViewById(R.id.showLocationTrip);

        btnBack = rootView.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnEdit = rootView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);

        joinBtn = rootView.findViewById(R.id.joinBtn);
        joinBtn.setOnClickListener(this);

        companionsBtn = rootView.findViewById(R.id.companionsBtn);
        companionsBtn.setOnClickListener(this);

        id_Trip = trip.getId_Trip();
        name = trip.getName();
        description = trip.getDescription();
        beginDate = trip.getBeginDate();
        endDate = trip.getEndDate();
        time = trip.getTime();
        location = trip.getLocation();
        imagePath = trip.getImagePath();

        latitude = trip.getLatitude();
        longitude = trip.getLongitude();
        if(latitude != null & longitude != null) {
            lat = Double.parseDouble(latitude);
            lng = Double.parseDouble(longitude);
        }
    }

    //----------------------------- CHECK STATUS USER --------------------------------//

    private void checkStatus(String statusUser) {
        if(statusUser == null){
            joinBtn.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
        else if(statusUser.equals("owner")){
            joinBtn.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        }
        else if(statusUser.equals("join")){
            joinBtn.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
    }


    //--------------------------------- ON CLICK -------------------------------------//

    @Override
    public void onClick(View view) {
        if(view == btnBack){
            goToAllTripsActivity();
        }
        else if (view == btnEdit){
            goToAddNewTripFragment();
        }
        else if (view == companionsBtn){
            goToCompanionsTripFragment();
        }
        else if(view == joinBtn){
            goToAllTripsActivity();
            onJoinClicked(id_Trip);
        }
    }

    private void onJoinClicked(String id_Trip) {
        FragmentListener listener = (infoTripFragment.FragmentListener) getActivity();
        listener.onJoinClicked(id_Trip);
    }

    //--------------------------------- SET DATA -------------------------------------//

    private void checkData() {
        trip = getArguments().getParcelable("trip");

        if (!TextUtils.isEmpty(name)) {
            nameTrip.setText(name);
            showNameTrip.setText(name);
            showDescriptionTrip.setText(description);
            showDateTrip.setText(String.format("%s - %s", beginDate, endDate));
            showTimeTrip.setText(time);
            showLocationTrip.setText(location);

            Glide.with(getContext())
                    .load(imagePath)
                    .placeholder(R.drawable.image_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageTrip);
        } else {

            Glide.with(getContext())
                    .load(imagePath)
                    .placeholder(R.drawable.image_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageTrip);
        }
    }

    //------------------------------ SHOW COMPANIONS LIST ----------------------------------//
    private void showCompanionsList() {
        Query query = firebase.getDatabase("companions").orderByChild("id_Trip").equalTo(id_Trip);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companions.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Companions companion = postSnapshot.getValue(Companions.class);
                    companions.add(companion);
                }

                adapter.setData(companions);
                companionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                companionsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //--------------------------------- MAP VIEW -------------------------------------//

    private void initMap(View rootView) {
        MapView mMapView = rootView.findViewById(R.id.map);
        if (mMapView !=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if(latitude != null & longitude != null) {
            goToLocation(lat, lng);
        }
    }

    private void goToLocation(double lat, double lng) {
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(lat, lng)).zoom(16).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }

    //------------------------------ GO TO ANOTHER PAGE -----------------------------------//

    private void goToAllTripsActivity() {
       Intent refresh = new Intent(getActivity(), MainActivity.class);
       startActivity(refresh);
       getActivity().finish();
    }
    private void goToCompanionsTripFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.companionsContainer, CompanionsTripFragment.newInstance(trip, statusUser))
                .commit();
    }

    private void goToAddNewTripFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.add_edit_Container, add_edit_TripFragment.newInstance(trip, statusUser))
                .commit();
    }

    //------------------------------ implement interface -----------------------------------//
    interface FragmentListener {
        void onJoinClicked(String id_Trip);
    }

}
