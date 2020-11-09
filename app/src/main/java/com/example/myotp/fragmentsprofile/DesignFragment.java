package com.example.myotp.fragmentsprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.myotp.R;
import com.example.myotp.map.ActivityMaps;

public class DesignFragment extends Fragment {

    public DesignFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_design,container,false);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onBackPressed(){
        Intent homr = new Intent(getActivity(), HomesFragment.class);
        startActivity(homr);
    }
}
