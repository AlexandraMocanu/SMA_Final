package com.alexandra.sma_final;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class UserTabAdapter extends FragmentStatePagerAdapter {

    private UserActivity userActivity;

    public UserTabAdapter(FragmentManager fm, UserActivity uA){
        super(fm);
        this.userActivity = uA;
    }

    @Override public Fragment getItem(int position) {

        if(userActivity.getUser() != null){
            switch (position){
                case 0: return new ActiveRequests();
                case 1: return new Score();
            }
        }
        else{
            return new ActiveRequests();
        }
        return null;
    }

    @Override
    public int getCount() {
        return userActivity.getUser() != null ? 2 : 1;
    }

    @Override public CharSequence getPageTitle(int position) {
        if(userActivity.getUser() != null) {
            switch (position) {
                case 0:
                    return "Active Requests";
                case 1:
                    return "Score";
                default:
                    return null;
            }
        }
        else{
            return "Active Requests";
        }
    }
}
