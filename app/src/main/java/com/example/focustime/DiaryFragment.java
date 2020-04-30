package com.example.focustime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.focustime.focus.FocusManager;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DiaryFragment extends Fragment {
    static SwitchPageFragmentListener listener;

    public DiaryFragment(SwitchPageFragmentListener listener) {
        DiaryFragment.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button returnBtn = view.findViewById(R.id.button);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSwitchToNextFragment();
            }
        });
    }
}
