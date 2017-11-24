package jirapinya58070014.kmitl.unify.controller.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import jirapinya58070014.kmitl.unify.model.MyTrip;
import jirapinya58070014.kmitl.unify.view.MytripsListItem;

public class TripsAdapter extends BaseAdapter {

    private List<MyTrip> myTrips;

    public TripsAdapter(List<MyTrip> newsList) {
        myTrips = newsList;
    }

    @Override
    public int getCount() {
        if (myTrips == null)
            return 0;
        return myTrips.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MytripsListItem item;

        if (view != null)
            item = (MytripsListItem) view;
        else
            item = new MytripsListItem(viewGroup.getContext());

        MyTrip trip = myTrips.get(position);

        item.setNameTrip(trip.getName());
        item.setDateTrip(trip.getBeginDate(),trip.getEndDate());
        item.setImgTrip(trip.getImagePath());

        return item;
    }
}