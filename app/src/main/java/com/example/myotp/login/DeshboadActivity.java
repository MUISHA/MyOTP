package com.example.myotp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myotp.Apropos;
import com.example.myotp.Design;
import com.example.myotp.R;
import com.example.myotp.fragmentsprofile.HomesFragment;
import com.example.myotp.fragmentsprofile.ProfilsFragment;
import com.example.myotp.map.Permission;
import com.example.myotp.otp.SendOPTActivity;
import com.example.myotp.server.scripts.Server;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeshboadActivity extends AppCompatActivity {
   // TextView textView;
    FirebaseAuth auth;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deshboad);
        auth = FirebaseAuth.getInstance();
        //textView = findViewById(R.id.textView_profil);

        actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        BottomNavigationView navigationView = findViewById(R.id.navigation_profile);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        //Open Default
        actionBar.setTitle("Home");


    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectFragment = null;
                    switch (menuItem.getItemId())
                    {

                        case R.id.nav_homes :
                            actionBar.setTitle("Home");
                            selectFragment = new HomesFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containers, selectFragment).commit();
                            break;
                        case R.id.profil_users :
                            actionBar.setTitle("Profil");
                            selectFragment = new ProfilsFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containers, selectFragment).commit();
                            break;
                        case R.id.nav_calling :
                            actionBar.setTitle("Sending sos");
                            Intent calling = new Intent(DeshboadActivity.this, SendOPTActivity.class);
                            startActivity(calling);
                            break;
                        case R.id.nav_addLocation :
                            actionBar.setTitle("Add Entreprise Location");
                            Intent servers = new Intent(DeshboadActivity.this, Server.class);
                            startActivity(servers);
                            break;
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containers, selectFragment).commit();
                    return true;
                }
            };
    private void CheckUserProfil(){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null){
            //textView.setText(firebaseUser.getEmail());
        }else {
            startActivity(new Intent(DeshboadActivity.this, SignIn.class));
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
        if (index == R.id.action_appropos){
            actionBar.setTitle("Appropos");
            startActivity(new Intent(this, Apropos.class));
            finish();
        }
        if (index == R.id.action_design){
            actionBar.setTitle("Design");
            startActivity(new Intent(this, Design.class));
            finish();
        }
        if (index == R.id.action_getmap){
            actionBar.setTitle("Maps");
            startActivity(new Intent(this, Permission.class));
            finish();
        }
        if (index == R.id.action_exist){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.nam_exit);
            builder.setMessage(R.string.etesvousur);

            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}