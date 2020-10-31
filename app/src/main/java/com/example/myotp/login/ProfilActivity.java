package com.example.myotp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.myotp.R;
import com.example.myotp.menu.Drawables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilActivity extends AppCompatActivity {
    TextView textView;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
        auth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.textView_profil);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profi Acount");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
    private void CheckUserProfil(){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null){
            textView.setText(firebaseUser.getEmail());
        }else {
            startActivity(new Intent(ProfilActivity.this, SignIn.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        CheckUserProfil();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int index = item.getItemId();
        if (index == R.id.action_logout){
            auth.signOut();
            CheckUserProfil();
        }
        return super.onOptionsItemSelected(item);
    }
}