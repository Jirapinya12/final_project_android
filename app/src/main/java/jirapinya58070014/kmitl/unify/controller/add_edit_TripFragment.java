package jirapinya58070014.kmitl.unify.controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jirapinya58070014.kmitl.unify.FirebaseConnection;
import jirapinya58070014.kmitl.unify.R;
import jirapinya58070014.kmitl.unify.model.Companions;
import jirapinya58070014.kmitl.unify.model.MyTrip;
import jirapinya58070014.kmitl.unify.test.MyValidator;
import jirapinya58070014.kmitl.unify.test.dateTrip.ValidateDateTripEmpty;
import jirapinya58070014.kmitl.unify.test.dateTrip.ValidateDateTripNull;
import jirapinya58070014.kmitl.unify.test.descriptionTrip.ValidateDescriptionTripEmpty;
import jirapinya58070014.kmitl.unify.test.descriptionTrip.ValidateDescriptionTripNull;
import jirapinya58070014.kmitl.unify.test.locationTrip.ValidateLocationTripEmpty;
import jirapinya58070014.kmitl.unify.test.locationTrip.ValidateLocationTripNull;
import jirapinya58070014.kmitl.unify.test.nameTrip.ValidateNameTripEmpty;
import jirapinya58070014.kmitl.unify.test.nameTrip.ValidateNameTripNull;
import jirapinya58070014.kmitl.unify.test.timeTrip.ValidateTimeTripEmpty;
import jirapinya58070014.kmitl.unify.test.timeTrip.ValidateTimeTripNull;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.icu.util.Calendar.DAY_OF_MONTH;
import static android.icu.util.Calendar.HOUR_OF_DAY;
import static android.icu.util.Calendar.MINUTE;
import static android.icu.util.Calendar.MONTH;
import static android.icu.util.Calendar.YEAR;
import static android.icu.util.Calendar.getInstance;

public class add_edit_TripFragment extends Fragment implements View.OnClickListener ,OnMapReadyCallback {

    private String id_Trip, name, description, beginDate, endDate, time, imagePath, id_UserOwner;
    private String location, latitude, longitude;
    private Uri imagePathUri;
    private Double lat, lng;
    private Boolean valid = true;

    private static final String USER_INFO = "user_info";
    private String id_User, name_User, imageUrl;
    private String idCompanions;

    private FloatingActionButton btnSave, btnUpdate;
    private TextView btnBack, btnDelete;
    private TextView pickTime, pickBeginDate, pickEndDate, pickLocation;
    private ImageView imageTrip, editImage, imageTripPicker;
    private TextView nameTrip;
    private EditText edNameTrip, edDescriptionTrip;
    private Calendar dateTime = getInstance();

    private int PLACE_PICKER_REQUEST = 1;
    private int SELECT_PHOTO = 100;
    StorageReference imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;

    private FirebaseConnection firebase;

    private MyTrip trip;

    private GoogleMap mGoogleMap;

    public add_edit_TripFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trip = getArguments().getParcelable("trip");
    }

    public static add_edit_TripFragment newInstance(MyTrip trip, String statusUser) {
        add_edit_TripFragment fragment = new add_edit_TripFragment();
        Bundle args = new Bundle();
        args.putParcelable("trip", trip);
        args.putString("statusUser", statusUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_trip, container, false);
        initInstances(rootView);
        checkData();
        initMap(rootView);
        getSharedPreferences();
        return rootView;
    }

    public void getSharedPreferences(){
        SharedPreferences shared = getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        id_User = shared.getString("id_User", "");
        name_User =  shared.getString("name_User", "");
        imageUrl =  shared.getString("imageUrl", "");
    }

    private void initInstances(View rootView) {
        firebase = new FirebaseConnection();
        btnSave = rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnUpdate = rootView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnDelete = rootView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        btnBack = rootView.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        pickLocation = rootView.findViewById(R.id.pickLocation);
        pickLocation.setOnClickListener(this);

        nameTrip = rootView.findViewById(R.id.nameTrip);
        edNameTrip = rootView.findViewById(R.id.edNameTrip);
        edDescriptionTrip = rootView.findViewById(R.id.edDescriptionTrip);
        imageTrip = rootView.findViewById(R.id.imageTrip);
        imageTripPicker = rootView.findViewById(R.id.imageTripPicker);

        editImage = rootView.findViewById(R.id.editImage);
        editImage.setOnClickListener(this);

        pickTime = rootView.findViewById(R.id.pickTime);
        pickTime.setOnClickListener(this);
        pickBeginDate = rootView.findViewById(R.id.pickBeginDate);
        pickBeginDate.setOnClickListener(this);
        pickEndDate = rootView.findViewById(R.id.pickEndDate);
        pickEndDate.setOnClickListener(this);

        id_Trip = trip.getId_Trip();
        name = trip.getName();
        description = trip.getDescription();
        beginDate = trip.getBeginDate();
        endDate = trip.getEndDate();
        time = trip.getTime();
        location = trip.getLocation();
        imagePath = trip.getImagePath();
        id_UserOwner = trip.getId_UserOwner();
        latitude = trip.getLatitude();
        longitude = trip.getLongitude();
        if(latitude != null & longitude != null) {
            lat = Double.parseDouble(latitude);
            lng = Double.parseDouble(longitude);
        }
    }

    //--------------------------------- ON CLICK -------------------------------------//
    @Override
    public void onClick(View view) {
        if (view == editImage) {
            addImage();
        } else if (view == btnSave) {
            if(validateInput() == true) {
                id_Trip = firebase.getDatabase("trips").push().getKey();
                addDataCompanion();
                addDataTrip();
                uploadImage();
                Toast.makeText(getActivity(),"Saved successfully.", Toast.LENGTH_LONG).show();
            }
        } else if (view == btnUpdate) {
            if(validateInput() == true){
                updateDataTrip();
                uploadImage();
            }
        } else if (view == btnDelete) {
            showDialogDelete();
        }else if (view == pickBeginDate){
            updateBeginDate();
        }else if(view == pickEndDate){
            updateEndDate();
        } else if (view == pickTime){
            updateTime();
        }else if(view == btnBack){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }else if(view == pickLocation){
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent;
            try {
                intent = builder.build((Activity) getContext());
                startActivityForResult(intent,PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //pickImage
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            imagePathUri = data.getData();
            imageTripPicker.setImageURI(imagePathUri);
            imagePath = imagePathUri.toString();
        }

        //pickMap
        if(requestCode == PLACE_PICKER_REQUEST)
        {
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,getContext());
                //nameLocation
                String nameLocation = String.valueOf(place.getName());
                pickLocation.setText(nameLocation);

                //address
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
                goToLocation(lat, lng);

                latitude = Double.toString(lat);
                longitude = Double.toString(lng);
            }
        }
    }

    //--------------------------------- SET DATA -------------------------------------//
    private void checkData() {
        trip = getArguments().getParcelable("trip");

        if (!TextUtils.isEmpty(name)) {

            nameTrip.setText(name);
            edNameTrip.setText(name);
            edDescriptionTrip.setText(description);
            pickBeginDate.setText(beginDate);
            pickEndDate.setText(endDate);
            pickTime.setText(time);
            pickLocation.setText(location);

            Glide.with(getContext())
                    .load(imagePath)
                    .placeholder(R.drawable.image_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageTrip);

            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

        } else {

            Glide.with(getContext())
                    .load(imagePath)
                    .placeholder(R.drawable.image_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageTrip);

            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
    }

    //------------------------------------ IMAGE -----------------------------------------//
    private void addImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, false);
        startActivityForResult(Intent.createChooser(photoPickerIntent,"Complete Action Using"), SELECT_PHOTO);
    }

    public void uploadImage() {
        if (imagePathUri != null) {
            imageRef = firebase.getStorage("images/" + id_Trip + ".png");

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMax(100);
            progressDialog.setMessage("Uploading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setCancelable(false);

            uploadTask = imageRef.putFile(imagePathUri);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.incrementProgressBy((int) progress);
                }
            });

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getContext(), "Error in uploading!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    imagePath = downloadUrl.toString();
                    firebase.getDatabase("trips").child(id_Trip).child("imagePath").setValue(imagePath);
                    progressDialog.dismiss();
                    getActivity().finish();
                }
            });
        }
        else{
            getActivity().finish();
        }
    }

    //--------------------------------- DELETE TRIP -------------------------------------//
    private void deleteTrip() {
        removeCompanion(id_Trip);
        Toast.makeText(getActivity(), "Trip Deleted", Toast.LENGTH_LONG).show();
    }

    private void deleteImageStorage() {
        imageRef = firebase.getStorage("images/" + id_Trip + ".png");
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: deleted file");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "onFailure: did not delete file");
            }
        });
    }
    private void removeCompanion(final String id_Trip) {
        Query query = firebase.getDatabase("companions").orderByChild("id_Trip").equalTo(id_Trip);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Companions companions = postSnapshot.getValue(Companions.class);
                    idCompanions = companions.getId_Companions();
                    firebase.getDatabase("companions").child(idCompanions).removeValue();
                }
                //removing
                firebase.getDatabase("trips").child(id_Trip).removeValue();
                getActivity().finish();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void showDialogDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm Delete");
        builder.setIcon(R.drawable.delete);
        builder.setMessage("Are you sure you want to delete this trip?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteImageStorage();
                deleteTrip();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    //--------------------------------- DATE & TIME -------------------------------------//
    //Date
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateBeginDate(){
        new DatePickerDialog(getContext(), bDate, dateTime.get(YEAR),dateTime.get(MONTH),dateTime.get(DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener bDate = new DatePickerDialog.OnDateSetListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(YEAR, year);
            dateTime.set(MONTH, monthOfYear);
            dateTime.set(DAY_OF_MONTH, dayOfMonth);
            pickBeginDate.setText(new StringBuilder()
                    .append(dayOfMonth).append("/").append(monthOfYear).append("/").append(year));
        }
    };

    private void updateEndDate(){
        new DatePickerDialog(getContext(), eDate, dateTime.get(YEAR),dateTime.get(MONTH),dateTime.get(DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener eDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(YEAR, year);
            dateTime.set(MONTH, monthOfYear);
            dateTime.set(DAY_OF_MONTH, dayOfMonth);
            pickEndDate.setText(new StringBuilder()
                    .append(dayOfMonth).append("/").append(monthOfYear).append("/").append(year));
        }
    };

    //Time
    private void updateTime(){
        new TimePickerDialog(getContext(), t, dateTime.get(HOUR_OF_DAY),dateTime.get(MINUTE),true).show();
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(HOUR_OF_DAY, hourOfDay);
            dateTime.set(MINUTE, minute);
            pickTime.setText(new StringBuilder()
                    .append(hourOfDay).append(":").append(minute));
        }

    };


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

    //--------------------------------- UPDATE DATA -------------------------------------//
    private void updateDataTrip() {
            HashMap<String, Object> postValues = new HashMap<>();

            postValues.put("name", name);
            postValues.put("id_Trip", id_Trip);
            postValues.put("description", description);
            postValues.put("beginDate", beginDate);
            postValues.put("endDate", endDate);
            postValues.put("time", time);
            postValues.put("location", location);
            postValues.put("imagePath", imagePath);
            postValues.put("id_User", id_User);
            postValues.put("latitude", latitude);
            postValues.put("longitude", longitude);

            firebase.getDatabase("trips").child(id_Trip).updateChildren(postValues);
    }

    //--------------------------------- ADD DATA -------------------------------------//
    private void addDataTrip() {
            MyTrip trip = new MyTrip();
            trip.setId_Trip(id_Trip);
            trip.setId_UserOwner(id_User);
            trip.setName(name);
            trip.setDescription(description);
            trip.setBeginDate(beginDate);
            trip.setEndDate(endDate);
            trip.setTime(time);
            trip.setLocation(location);
            trip.setImagePath(imagePath);
            trip.setLatitude(latitude);
            trip.setLongitude(longitude);
            trip.setId_UserOwner(id_UserOwner);

            firebase.getDatabase("trips").child(id_Trip).setValue(trip);
            firebase.getDatabase("trips").child(id_Trip).child("id_User").setValue(id_User);
            firebase.getDatabase("trips").child(id_Trip).child("userID: " + id_User).setValue("join");
    }

    private void addDataCompanion() {
        idCompanions = firebase.getDatabase("companions").push().getKey();
        Companions companion = new Companions();
        companion.setId_User(id_User);
        companion.setName(name_User);
        companion.setImagePath(imageUrl);
        companion.setId_Trip(id_Trip);
        companion.setStatus("owner");
        companion.setId_Companions(idCompanions);
        firebase.getDatabase("companions").child(idCompanions).setValue(companion);
    }

    //------------------------------- VALIDATE INPUT -----------------------------------//

    private boolean validateInput() {
        valid = true;

        name = edNameTrip.getText().toString();
        description = edDescriptionTrip.getText().toString();
        beginDate = pickBeginDate.getText().toString();
        endDate = pickEndDate.getText().toString();
        time = pickTime.getText().toString();
        location = pickLocation.getText().toString();

        //validatorsName
        List<MyValidator> validatorsName = new ArrayList<>();
        validatorsName.add(new ValidateNameTripNull());
        validatorsName.add(new ValidateNameTripEmpty());

        for (MyValidator validator: validatorsName) {
            if(validator.isValid(name)) {
                Toast.makeText(getActivity(), validator.getErrorMessage(), Toast.LENGTH_LONG).show();
                valid = false;
            }
        }

        //validatorsDescription
        List<MyValidator> validatorsDescription = new ArrayList<>();
        validatorsDescription.add(new ValidateDescriptionTripNull());
        validatorsDescription.add(new ValidateDescriptionTripEmpty());

        for (MyValidator validator: validatorsDescription) {
            if(validator.isValid(description)) {
                Toast.makeText(getActivity(), validator.getErrorMessage(), Toast.LENGTH_LONG).show();
                valid = false;
            }
        }

        //validatorsDate
        List<MyValidator> validatorsDate = new ArrayList<>();
        validatorsDate.add(new ValidateDateTripNull());
        validatorsDate.add(new ValidateDateTripEmpty());

        for (MyValidator validator: validatorsDate) {
            if(validator.isValid(beginDate) || validator.isValid(endDate)) {
                Toast.makeText(getActivity(), validator.getErrorMessage(), Toast.LENGTH_LONG).show();
                valid = false;
            }
        }

        //validatorsTime
        List<MyValidator> validatorsTime = new ArrayList<>();
        validatorsTime.add(new ValidateTimeTripNull());
        validatorsTime.add(new ValidateTimeTripEmpty());

        for (MyValidator validator: validatorsTime) {
            if(validator.isValid(time)) {
                Toast.makeText(getActivity(), validator.getErrorMessage(), Toast.LENGTH_LONG).show();
                valid = false;
            }
        }

        //validatorsLocation
        List<MyValidator> validatorsLocation = new ArrayList<>();
        validatorsLocation.add(new ValidateLocationTripNull());
        validatorsLocation.add(new ValidateLocationTripEmpty());

        for (MyValidator validator: validatorsLocation) {
            if(validator.isValid(location)) {
                Toast.makeText(getActivity(), validator.getErrorMessage(), Toast.LENGTH_LONG).show();
                valid = false;
            }
        }

        return valid;
    }
}
