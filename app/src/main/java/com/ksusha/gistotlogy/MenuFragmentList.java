package com.ksusha.gistotlogy;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;


public class MenuFragmentList extends Fragment {
    private NavItemSelectedListener navItemSelectedListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        NavigationView navigationView = view.findViewById(R.id.vNavigation);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            navItemSelectedListener.onNavItemSelectedListener(menuItem); //передача нажатия через интерфейс в MainActivity
            return false;
        });
        return view;
    }

    public void setNavItemSelectedListener(NavItemSelectedListener navItemSelectedListener) { //присваивание интерфейса
        this.navItemSelectedListener = navItemSelectedListener;
    }
}