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
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;

import DBHelper.DatabaseHandler;
import Orders.Order;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHandler databaseHandler;
    private EditText edit_text_yourname;
    private EditText edit_text_address;
    private EditText edit_text_confectionary_name;
    private EditText edit_text_date;
    private EditText edit_text_filling;
    private EditText edit_text_quantity;
    private Button button_make_an_order;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        databaseHandler = new DatabaseHandler(this);
        edit_text_address = findViewById(R.id.edit_text_address);
        edit_text_yourname = findViewById(R.id.edit_text_yourname);
        edit_text_confectionary_name = findViewById(R.id.edit_text_confectionary_name);
        edit_text_date = findViewById(R.id.edit_text_date);
        edit_text_filling = findViewById(R.id.edit_text_filling);
        edit_text_quantity = findViewById(R.id.edit_text_quantity);
        button_make_an_order = findViewById(R.id.button_make_an_order);
        button_make_an_order.setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_make_an_order:{
                databaseHandler.addOrder(new Order(
                        edit_text_yourname.getText().toString().trim(),
                        edit_text_address.getText().toString().trim(),
                        edit_text_date.getText().toString().trim(),
                        edit_text_filling.getText().toString().trim(),
                        edit_text_confectionary_name.getText().toString().trim(),
                        edit_text_quantity.getText().toString().trim()
                ));
                break;
            }
        }
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (actionBarDrawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void UserMenuSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_menu_orders: {
                intent = new Intent(this,OrderActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            }
            case R.id.nav_menu_orders_for_director:{
                intent = new Intent(this, OrderForDirector.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_menu_settings: {
                startActivity(new Intent(this,SettingsActivity.class));
                break;
            }
            default:
                break;
        }
    }

    protected void notifyDataSetChanged() {
    }
}
