package jirapinya58070014.kmitl.unify.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import jirapinya58070014.kmitl.unify.R;
import jirapinya58070014.kmitl.unify.model.Companions;


class Holder extends RecyclerView.ViewHolder {

    public ImageView image;
    Holder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.profile_image);
    }
}

public class CompanionsImageAdapter extends RecyclerView.Adapter<Holder> {
    private Context context;
    private List<Companions> companions;

    public CompanionsImageAdapter(Context context) {
        this.context = context;
        companions = new ArrayList<>();
    }

    public void setData(List<Companions> companions) {
        this.companions = companions;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            @SuppressLint("InflateParams") View itemView = inflater.inflate(R.layout.companions_image_list_item, null, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        //image
        ImageView image = holder.image;
        String imageUrl = companions.get(position).getImagePath();

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }

    @Override
    public int getItemCount() {
        return companions.size(); }

}

