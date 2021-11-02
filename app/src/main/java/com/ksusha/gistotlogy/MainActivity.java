package com.ksusha.gistotlogy;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.ksusha.gistotlogy.adapter.DataAdapter;
import com.ksusha.gistotlogy.adapter.ListItem;
import com.ksusha.gistotlogy.adapter.RecViewOnClickListener;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavItemSelectedListener {
    private RecViewOnClickListener recViewOnClickListener;
    private DataAdapter dataAdapter;
    private List<ListItem> listData;
    private RecyclerView rcView;
    private String category = "";
    private SharedPreferences preferences;
    private final String ziro = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";


    @SuppressLint({"ResourceType", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupMenu();
        setRecOnClickListener();
        init();
    }


    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuFragmentList mMenuFragment = (MenuFragmentList) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuFragmentList();
            mMenuFragment.setNavItemSelectedListener(this);
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
    }

    private void upDateFavourite() {
        List<ListItem> listFavourites = new ArrayList<>();
        List<String[]> listDataArray = new ArrayList<>();
        listDataArray.add(getResources().getStringArray(R.array.skorocheno));
        listDataArray.add(getResources().getStringArray(R.array.theme_1));
        listDataArray.add(getResources().getStringArray(R.array.theme_2));
        listDataArray.add(getResources().getStringArray(R.array.theme_3));
        listDataArray.add(getResources().getStringArray(R.array.theme_4));
        listDataArray.add(getResources().getStringArray(R.array.theme_5));
        listDataArray.add(getResources().getStringArray(R.array.theme_6));
        listDataArray.add(getResources().getStringArray(R.array.theme_7));
        listDataArray.add(getResources().getStringArray(R.array.theme_8));
        listDataArray.add(getResources().getStringArray(R.array.theme_9));
        listDataArray.add(getResources().getStringArray(R.array.theme_10));
        listDataArray.add(getResources().getStringArray(R.array.theme_11));
        listDataArray.add(getResources().getStringArray(R.array.theme_12));
        String[] nameOfCategoryArray = {"skorocheno", "theme_1", "theme_2", "theme_3", "theme_4", "theme_5", "theme_6", "theme_7", "theme_8", "theme_9", "theme_10", "theme_11", "theme_12"};
        for (int i = 0; i < listDataArray.size(); i++) {
            for (int j = 0; j < listDataArray.get(i).length; j++) {
                String contains = preferences.getString(nameOfCategoryArray[i], ziro);
                if (contains != null) {
                    if (contains.charAt(j) == '1') {
                        ListItem item = new ListItem();
                        item.setText(listDataArray.get(i)[j]);
                        item.setPosition(j);
                        item.setCategory(nameOfCategoryArray[i]);
                        listFavourites.add(item);
                    }
                }
            }
        }
        dataAdapter.updateArray(listFavourites, true);
    }

    private void init() {
        preferences = getSharedPreferences("CATEGORY", MODE_PRIVATE);
        rcView = findViewById(R.id.rcView);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        listData = new ArrayList<>();
        String[] skorocheno = getResources().getStringArray(R.array.skorocheno);
        /*for (String s : skorocheno) {
            ListItem listItem = new ListItem();
            listItem.setText(s);
            listItem.setFavourite(false);
            listData.add(listItem);
        }*/
        dataAdapter = new DataAdapter(this, recViewOnClickListener, listData);
        upDateMainList(skorocheno, "skrocheno");
        rcView.setAdapter(dataAdapter);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onNavItemSelectedListener(MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.id_favourite:
                upDateFavourite();
                break;
            case R.id.id_skorocheno:
                upDateMainList(getResources().getStringArray(R.array.skorocheno), "skorocheno");
                break;
            case R.id.theme_1:
                upDateMainList(getResources().getStringArray(R.array.theme_1), "theme_1");
                break;
            case R.id.theme_2:
                upDateMainList(getResources().getStringArray(R.array.theme_2), "theme_2");
                break;
            case R.id.theme_3:
                upDateMainList(getResources().getStringArray(R.array.theme_3), "theme_3");
                break;
            case R.id.theme_4:
                upDateMainList(getResources().getStringArray(R.array.theme_4), "theme_4");
                break;
            case R.id.theme_5:
                upDateMainList(getResources().getStringArray(R.array.theme_5), "theme_5");
                break;
            case R.id.theme_6:
                upDateMainList(getResources().getStringArray(R.array.theme_6), "theme_6");
                break;
            case R.id.theme_7:
                upDateMainList(getResources().getStringArray(R.array.theme_7), "theme_7");
                break;
            case R.id.theme_8:
                upDateMainList(getResources().getStringArray(R.array.theme_8), "theme_8");
                break;
            case R.id.theme_9:
                upDateMainList(getResources().getStringArray(R.array.theme_9), "theme_9");
                break;
            case R.id.theme_10:
                upDateMainList(getResources().getStringArray(R.array.theme_10), "theme_10");
                break;
            case R.id.theme_11:
                upDateMainList(getResources().getStringArray(R.array.theme_11), "theme_11");
                break;
            case R.id.theme_12:
                upDateMainList(getResources().getStringArray(R.array.theme_12), "theme_12");
                break;
        }
    }

    private void upDateMainList(String[] array, String category) {
        this.category = category;
        StringBuilder favourite_category = new StringBuilder("");
        String tempCategory = preferences.getString(category, "none");
        if (tempCategory != null) {
            if (tempCategory.equals("none")) {
                for (int i = 0; i < array.length; i++) {
                    favourite_category.append("0").toString();
                }
                Log.d("MY", category + ": " + favourite_category);
                saveString(favourite_category.toString());
            } else {

            }
        }
        List<ListItem> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            ListItem listItem = new ListItem();
            listItem.setText(array[i]);
            listItem.setCategory(category);
            listItem.setPosition(i);
            list.add(listItem);
        }
        dataAdapter.updateArray(list, false);
    }

    private void setRecOnClickListener() {
        recViewOnClickListener = new RecViewOnClickListener() {
            @Override
            public void onItemClicked(int position) {
                String tempCategory = preferences.getString(category, "none");
                if (tempCategory != null) {
                    if (tempCategory.charAt(position) == '0') {
                        saveString(replaceCharAtPosition(position, '1', tempCategory));
                    } else {
                        saveString(replaceCharAtPosition(position, '0', tempCategory));
                    }
                }
            }
        };
    }

    private String replaceCharAtPosition(int position, char ziroOrOne, String placeChangedZiroOrOne) {
        char[] charArray = placeChangedZiroOrOne.toCharArray();
        charArray[position] = ziroOrOne;
        return new String(charArray);
    }

    private void saveString(String stringToSave) {
        SharedPreferences.Editor editorSave = preferences.edit();
        editorSave.putString(category, stringToSave);
        editorSave.apply();
        Log.d("MY", "My: " + preferences.getString(category, "none"));
    }
}