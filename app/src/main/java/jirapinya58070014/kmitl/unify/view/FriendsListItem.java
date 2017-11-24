package jirapinya58070014.kmitl.unify.view;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jirapinya58070014.kmitl.unify.R;

public class FriendsListItem extends MyCustomViewGroup{

    private TextView userName;
    private ImageView profile_image;

    public FriendsListItem(@NonNull Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public FriendsListItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public FriendsListItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.friend_list_item, this);
    }

    private void initInstances() {
        userName = findViewById(R.id.showUserName);
        profile_image = findViewById(R.id.profile_image);

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        return new BundleSavedState(superState);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
    }

    public void setUserName(String text) {
        userName.setText(text);
    }

    public void setProfileImage(String path) {
            Glide.with(getContext())
                .load(path)
                .placeholder(R.drawable.profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_image);
    }
}
