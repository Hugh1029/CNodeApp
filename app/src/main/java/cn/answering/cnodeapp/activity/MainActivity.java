package cn.answering.cnodeapp.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cn.answering.cnodeapp.R;
import cn.answering.cnodeapp.controller.ActivityController;
import cn.answering.cnodeapp.fragment.HomeFragment;
import cn.answering.cnodeapp.fragment.InfoFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private Button infoButton;
    private Button homeButton;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar显示
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu1);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        infoButton = (Button) findViewById(R.id.info_button);
        homeButton = (Button) findViewById(R.id.home_button);
        infoButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        infoButtonPressed();
        manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = InfoFragment.newInstance();
            replaceFragment(fragment);
        }

        //navigationView的点击设置
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                }
                return false;
            }
        });

    }

    /**
     *
     * create the main activity's menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    /**
     * the menu item selected event
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.exist:
                ActivityController.finishAll();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * it is override click enven
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_button:
                infoButtonPressed();
                replaceFragment(InfoFragment.newInstance());
                break;
            case R.id.home_button:
                homeButtonPressed();
                replaceFragment(HomeFragment.nesInstance());
                break;
            default:
                break;
        }
    }

    /**
     * the function replace the fragment
     */
    private void replaceFragment(Fragment fragment){
            manager.beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
    }

    /**
     * the next two function is the button click and change thr style
     */
    public void infoButtonPressed(){
        infoButton.setTextColor(getResources().getColor(R.color.textColorPressed));
        homeButton.setTextColor(getResources().getColor(R.color.imageButtonBorder));
    }
    public void homeButtonPressed(){
        infoButton.setTextColor(getResources().getColor(R.color.imageButtonBorder));
        homeButton.setTextColor(getResources().getColor(R.color.textColorPressed));
    }
}
