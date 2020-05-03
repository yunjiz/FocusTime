package com.example.focustime;

import android.os.Parcelable;

import com.example.focustime.history.History;

public interface SwitchPageFragmentListener {
    void onSwitchToNextFragment(History history);
}
