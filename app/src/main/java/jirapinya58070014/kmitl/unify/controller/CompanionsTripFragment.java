package jirapinya58070014.kmitl.unify.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jirapinya58070014.kmitl.unify.FirebaseConnection;
import jirapinya58070014.kmitl.unify.R;
import jirapinya58070014.kmitl.unify.controller.adapter.CompanionsAdapter;
import jirapinya58070014.kmitl.unify.model.Companions;
import jirapinya58070014.kmitl.unify.model.MyTrip;

public class CompanionsTripFragment extends Fragment implements View.OnClickListener {

    private FirebaseConnection firebase;
    private ListView listView;
    private List<Companions> companions;
    private CompanionsAdapter adapter;

    private FloatingActionButton inviteBtn;
    private String id_Trip;
    private String statusUser;
    private MyTrip trip;
    private TextView btnBack;

    public CompanionsTripFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trip = getArguments().getParcelable("trip");
        statusUser = getArguments().getString("statusUser");
    }

    public static CompanionsTripFragment newInstance(MyTrip trip, String statusUser) {
        CompanionsTripFragment fragment = new CompanionsTripFragment();
        Bundle args = new Bundle();
        args.putParcelable("trip", trip);
        args.putString("statusUser", statusUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_companions_trip, container, false);
        initInstances(rootView);
        showCompanionsList();
        return rootView;
    }


    private void initInstances(View rootView) {
        firebase = new FirebaseConnection();
        listView = rootView.findViewById(R.id.listView);
        companions = new ArrayList<>();

        btnBack = rootView.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        inviteBtn = rootView.findViewById(R.id.inviteBtn);
        inviteBtn.setOnClickListener(this);

        id_Trip = trip.getId_Trip();
    }


    //--------------------------------- ON CLICK -------------------------------------//
    @Override
    public void onClick(View view) {
        if (view == inviteBtn) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.inviteContainer, InviteFriendFragment.newInstance(trip, statusUser))
                    .commit();

        }else if (view == btnBack) {
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.companionsContainer)).commit();
            getFragmentManager().beginTransaction()
                    .replace(R.id.infoContainer, infoTripFragment.newInstance(trip, statusUser))
                    .commit();
        }
    }


    //----------------------------- show CompanionsList ---------------------------------//
    private void showCompanionsList() {
        Query query = firebase.getDatabase("companions").orderByChild("id_" +
                "Trip").equalTo(id_Trip);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companions.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Companions companion = postSnapshot.getValue(Companions.class);
                    companions.add(companion);
                }
                adapter = new CompanionsAdapter(companions);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
