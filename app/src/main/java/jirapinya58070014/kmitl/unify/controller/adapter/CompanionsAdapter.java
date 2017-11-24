package jirapinya58070014.kmitl.unify.controller.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import jirapinya58070014.kmitl.unify.model.Companions;
import jirapinya58070014.kmitl.unify.view.CompanionsListItem;

public class CompanionsAdapter extends BaseAdapter {

    private List<Companions> companions;

    public CompanionsAdapter(List<Companions> companions) {
        this.companions = companions;
    }

    @Override
    public int getCount() {
        if (companions == null)
            return 0;
        return companions.size();
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
        CompanionsListItem item;
        if (view != null)
            item = (CompanionsListItem) view;
        else
            item = new CompanionsListItem(viewGroup.getContext());

        Companions companion = companions.get(position);

        item.setUserName(companion.getName());
        item.setShowOwner(companion.getStatus());
        item.setProfileImage(companion.getImagePath());

        return item;
    }
}