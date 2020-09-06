package app.assem.lavillasweets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private LinearLayout LogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
        LogOut = findViewById(R.id.linear_layout_log_out);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Settings"); // setting the action bar text to Settings.
        }
    }

    public void logOut(View view) { // log out method,
        FirebaseAuth.getInstance().signOut(); // mAuth will be == null in here
        startActivity(new Intent(this,MainActivity.class)); // we will kick him to the LoginActivity
    }
}
