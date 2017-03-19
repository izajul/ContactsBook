package com.example.izajul.contactsbook.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.izajul.contactsbook.Adapter.FragmentAdapter;
import com.example.izajul.contactsbook.Fragments.Fevoret_Peopels_Fragment;
import com.example.izajul.contactsbook.Fragments.Group_peoples_Fragment;
import com.example.izajul.contactsbook.Fragments.PeoplesFragment;
import com.example.izajul.contactsbook.R;

public class FrontViewList extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SearchView searchView;
    LinearLayout searchLayout;
    CoordinatorLayout tabLayoutShow;

   private int[] tabIcons = {R.drawable.people, R.drawable.grouppeople, R.drawable.fevoretblack };// Initialize Tab Icon
    ImageButton addPeopleIB,searchIB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_view_list);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search For Contact");
// Set Tab on Action Bar...
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        tabLayoutShow = (CoordinatorLayout) findViewById(R.id.tabLayoutShow);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        addPeopleIB= (ImageButton) findViewById(R.id.addPeopleButton);
        searchIB= (ImageButton) findViewById(R.id.serarchButton);
        searchIB.getBackground().setAlpha(0);addPeopleIB.getBackground().setAlpha(0); // Set Button Background Alpha 0
// Acton For Touch AddPeople Button
        addPeopleIB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: addPeopleIB.getBackground().setAlpha(200);break;
                    case MotionEvent.ACTION_UP:
                        addPeopleIB.getBackground().setAlpha(0);
                        Intent intent = new Intent(FrontViewList.this,AddpeopleForm.class);
                        startActivity(intent);
                }return false;
            }
        });
// Action For Touch Search Button
        searchIB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: searchIB.getBackground().setAlpha(200);break;
                    case MotionEvent.ACTION_UP:
                        searchIB.getBackground().setAlpha(0);
                        searchLayout.setVisibility(View.VISIBLE);
                        tabLayoutShow.setVisibility(View.GONE);
                        searchView.onActionViewExpanded();
                }return false;
            }
        });
    }
// Setup ViewPager Method ......
    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new PeoplesFragment(), "ONE");
        adapter.addFragment(new Group_peoples_Fragment(), "TWO");
        adapter.addFragment(new Fevoret_Peopels_Fragment(), "THREE");
        viewPager.setAdapter(adapter);
    }
// Setup Tab Icon Method ......
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
}
