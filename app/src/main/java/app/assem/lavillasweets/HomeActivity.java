package app.assem.lavillasweets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity { // this is home screen.

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.nav_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.nav_menu);
        View navView = navigationView.inflateHeaderView(R.layout.nav_header);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                UserMenuSelected(menuItem);
                return true;
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (actionBarDrawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void UserMenuSelected(MenuItem menuItem) { // when user select something he will start the new screen
        switch (menuItem.getItemId()) {
            case R.id.nav_menu_orders: { // if user selected orders activity
                intent = new Intent(this,OrderActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_menu_orders_for_director:{ // if user selected orders for director(it in real shows to the director only)
                intent = new Intent(this, OrderForDirector.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_menu_settings: { // if user selected setting screen, it will start the settings screen
                startActivity(new Intent(this,SettingsActivity.class));
                break;
            }
            default:
                break;
        }
    }
}
