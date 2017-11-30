package jirapinya58070014.kmitl.unify.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import jirapinya58070014.kmitl.unify.controller.adapter.FriendsAdapter;
import jirapinya58070014.kmitl.unify.model.Companions;
import jirapinya58070014.kmitl.unify.model.MyTrip;
import jirapinya58070014.kmitl.unify.model.UserProfile;

public class InviteFriendFragment extends Fragment implements View.OnClickListener {

    private FirebaseConnection firebase;
    private ListView listView;
    private List<UserProfile> users;
    private FriendsAdapter adapter;

    private String id_Trip;
    private String statusUser;
    private MyTrip trip;
    private ImageView closeBtn;

    public InviteFriendFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trip = getArguments().getParcelable("trip");
        statusUser = getArguments().getString("statusUser");
    }

    public static InviteFriendFragment newInstance(MyTrip trip, String statusUser) {
        InviteFriendFragment fragment = new InviteFriendFragment();
        Bundle args = new Bundle();
        args.putParcelable("trip", trip);
        args.putString("statusUser", statusUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invite_friend, container, false);
        initInstances(rootView);
        showFriendList();
        return rootView;
    }

    private void initInstances(View rootView) {
        firebase = new FirebaseConnection();
        listView = rootView.findViewById(R.id.listView);
        users = new ArrayList<>();

        closeBtn = rootView.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(this);

        id_Trip = trip.getId_Trip();
    }

    @Override
    public void onClick(View view) {
        if (view == closeBtn) {
            closeInviteFriendFragment();
        }
    }

    //--------------------------------- SHOW FRIEND LIST -------------------------------------//
    private void showFriendList() {
        Query query = firebase.getDatabase("users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserProfile user = postSnapshot.getValue(UserProfile.class);
                    showTripList(user);
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        UserProfile user = users.get(i);
                        showDialogInvite(user);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void showTripList(final UserProfile user) {
        Query query = firebase.getDatabase("trips").orderByChild("id_Trip").equalTo(id_Trip);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(null == postSnapshot.child("userID: "+user.getId_User()).getValue()){
                        users.add(user);
                        adapter = new FriendsAdapter(users);
                        listView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //--------------------------------- Invite Friend -------------------------------------//
    public void showDialogInvite(final UserProfile user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Invite Friend");
        builder.setMessage("Are you sure you want to invite "+ user.getName()+" to join trip?");

        builder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                users.clear();

                //add trips table
                firebase.getDatabase("trips").child(id_Trip).child("userID: "+user.getId_User()).setValue("join");

                //add companions table
                addDataCompanion(user);

                closeInviteFriendFragment();

            }
        });
        builder.setNegativeButton("CANCEL", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void addDataCompanion(UserProfile user) {
        String idCompanions = firebase.getDatabase("companions").push().getKey();
        Companions companion = new Companions();
        companion.setId_Trip(id_Trip);
        companion.setId_User(user.getId_User());
        companion.setName(user.getName());
        companion.setImagePath(user.getImageUrl());
        companion.setStatus("join");
        companion.setId_Companions(idCompanions);
        firebase.getDatabase("companions").child(idCompanions).setValue(companion);

    }

    //------------------------------ GO TO ANOTHER PAGE -----------------------------------//
    private void closeInviteFriendFragment() {
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.companionsContainer)).commit();
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.inviteContainer)).commit();
        getActivity().finish();
    }
}
