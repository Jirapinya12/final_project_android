package jirapinya58070014.kmitl.unify.controller.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import jirapinya58070014.kmitl.unify.model.UserProfile;
import jirapinya58070014.kmitl.unify.view.FriendsListItem;

public class FriendsAdapter extends BaseAdapter {

    private List<UserProfile> users;

    public FriendsAdapter(List<UserProfile> users) {
        this.users = users;
    }

    @Override
    public int getCount() {
        if (users == null)
            return 0;
        return users.size();
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
        FriendsListItem item;
        if (view != null)
            item = (FriendsListItem) view;
        else
            item = new FriendsListItem(viewGroup.getContext());

        UserProfile user = users.get(position);

        item.setUserName(user.getName());
        item.setProfileImage(user.getImageUrl());
        return item;
    }
}