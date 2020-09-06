package app.assem.lavillasweets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "SignActivity";

    public EditText editTextEmail;
    public EditText editTextPassword;
    public EditText editTextRewritePassword;
    public Button buttonLogin;
    public FirebaseAuth mAuth;
    public TextView textViewSignup;
    public boolean loginModeActive;
    public TextView textViewQuestion;
    public OrderForDirector adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogin = findViewById(R.id.button_login);
        textViewSignup = findViewById(R.id.text_view_signup);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        textViewQuestion = findViewById(R.id.text_view_question);
        mAuth = FirebaseAuth.getInstance();// enabling Firebase Authentication
        editTextRewritePassword = findViewById(R.id.edit_text_rewrite_password);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSignUpUser(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim());
                // if button pressed call this method
            }
        });
    }

    private void loginSignUpUser( String email, String password) { // this method will check is user registered or signed.
        if (loginModeActive) {  // if user signed up he will sign in
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }
                        }
                    });
            startActivity(new Intent(this,HomeActivity.class)); //in here we will start the new screen
        }
        else {
            // if user tapped the "Sign up" textview he will register
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }

                            // ...
                        }
                    });
            startActivity(new Intent(this,HomeActivity.class));
        }
    }

    public void toggleLoginMode(View view) {
        if (!loginModeActive) {
            loginModeActive = true;
            buttonLogin.setText("Login");
            textViewQuestion.setText("You don`t have an account?");
            textViewSignup.setText("Sign Up");
            editTextRewritePassword.setVisibility(View.GONE);
        } else {
            loginModeActive = false;
            buttonLogin.setText("Sign Up");
            textViewQuestion.setText("You have an account?");
            textViewSignup.setText("Log in");
            editTextRewritePassword.setVisibility(View.VISIBLE);
        }
    }


}
