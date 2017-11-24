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

public class MytripsListItem extends MyCustomViewGroup{

    private TextView nameTrip;
    private TextView dateTrip;
    private ImageView imageTrip;

    public MytripsListItem(@NonNull Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public MytripsListItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public MytripsListItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.mytrips_list_item, this);
    }

    private void initInstances() {
        nameTrip = findViewById(R.id.nameTrip);
        dateTrip = findViewById(R.id.dateTrip);
        imageTrip = findViewById(R.id.imageTrip);

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

    public void setNameTrip(String text) {
        nameTrip.setText(text);
    }

    public void setDateTrip(String beginDateTrip,String EndDateTrip) {
        dateTrip.setText(String.format("%s - %s", beginDateTrip, EndDateTrip));
    }

    public void setImgTrip(String path) {
        Glide.with(getContext())
                .load(path)
                .placeholder(R.drawable.image_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageTrip);
    }

}
