package jirapinya58070014.kmitl.unify.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

import jirapinya58070014.kmitl.unify.FirebaseConnection;
import jirapinya58070014.kmitl.unify.R;
import jirapinya58070014.kmitl.unify.model.UserProfile;

public class LoginActivity extends AppCompatActivity {

    private static final String USER_INFO = "user_info";
    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private LoginButton loginButton;
    private ProgressBar progressBar;

    private FirebaseConnection firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        animationLinearLayout();

        firebase = new FirebaseConnection();

        callbackManager = CallbackManager.Factory.create();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Collections.singletonList("email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    addUser(user);
                    goAllTripActivity();
                }
            }
        };
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void addUser(FirebaseUser user) {
        String id = user.getUid();
        String displayName = user.getDisplayName();
        String email = user.getEmail();
        String imageUrl = String.valueOf(user.getPhotoUrl());
        setUserinfo(id, displayName, email, imageUrl);

        UserProfile userProfile = new UserProfile();
        userProfile.setId_User(id);
        userProfile.setName(displayName);
        userProfile.setEmail(email);
        userProfile.setImageUrl(imageUrl);
        firebase.getDatabase("users").child(id).setValue(userProfile);
    }

    private void setUserinfo(String id, String displayName, String email, String imageUrl) {

        // Get SharedPreferences
        SharedPreferences shared = getSharedPreferences(USER_INFO,
                Context.MODE_PRIVATE);

        // Save SharedPreferences
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("id_User", id);
        editor.putString("name_User", displayName);
        editor.putString("email", email);
        editor.putString("imageUrl", imageUrl);
        editor.apply();
    }


    //------------------------------ GO TO ANOTHER PAGE -----------------------------------//
    private void goAllTripActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        Toast.makeText(this, "LOGIN SUCCESS !!", Toast.LENGTH_LONG).show();
    }

    //--------------------------------- ANIMATION -------------------------------------//
    private void animationLinearLayout() {
        LinearLayout linearLogo = (LinearLayout) findViewById(R.id.linearLogo);
        LinearLayout linearBtn = (LinearLayout) findViewById(R.id.linearBtn);
        LinearLayout linearCar = (LinearLayout) findViewById(R.id.linearCar);
        Animation uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        Animation downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        linearLogo.setAnimation(uptodown);
        linearBtn.setAnimation(uptodown);
        linearCar.setAnimation(downtoup);
    }
//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() == 0) {
//            this.finish();
//        } else {
//            getFragmentManager().popBackStack();
//        }
//    }
}