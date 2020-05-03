package com.example.focustime;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.focustime.history.History;

import java.util.Date;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    FragmentManager fm;
    Fragment mFragmentAtPos1;
    SwitchPageFragmentListener listener;

    private final class SwitchPageListener implements SwitchPageFragmentListener {

        @Override
        public void onSwitchToNextFragment(History history) {
            if(mFragmentAtPos1 == null){
                if (history != null){
                    mFragmentAtPos1 = DiaryFragment.newInstance(history);
                } else {
                    mFragmentAtPos1 = new HistoryFragment();
                }
            } else {
                fm.beginTransaction().remove(mFragmentAtPos1).commit();
                if(mFragmentAtPos1 instanceof HistoryFragment){
                    mFragmentAtPos1 = DiaryFragment.newInstance(history);
                } else {
                    mFragmentAtPos1 = new HistoryFragment();
                }
            }

            notifyDataSetChanged();
        }
    }

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.fm = fm;
        this.mNumOfTabs = behavior;
        listener = new SwitchPageListener();
        HistoryFragment.listener = listener;
        DiaryFragment.listener = listener;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FocusFragment();
            case 1:
                if(mFragmentAtPos1 == null){
                    mFragmentAtPos1 = new HistoryFragment();
                }
                return mFragmentAtPos1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (object instanceof HistoryFragment && mFragmentAtPos1 instanceof DiaryFragment){
            return POSITION_NONE;
        }
        if(object instanceof DiaryFragment && mFragmentAtPos1 instanceof HistoryFragment){
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }
}
