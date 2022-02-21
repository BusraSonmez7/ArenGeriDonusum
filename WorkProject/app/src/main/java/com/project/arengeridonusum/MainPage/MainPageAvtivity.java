package com.project.arengeridonusum.MainPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.project.arengeridonusum.MainPage.Fiyat.FiyatFragment;
import com.project.arengeridonusum.MainPage.Hakkimda.HakkimdaFragment;
import com.project.arengeridonusum.MainPage.Iletisim.IletisimFragment;
import com.project.arengeridonusum.MainPage.Oneri.OneriFragment;
import com.project.arengeridonusum.R;
import com.project.arengeridonusum.Users.UserLoginActivity;
import com.project.arengeridonusum.Users.UserProfile;

public class MainPageAvtivity extends AppCompatActivity {


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_avtivity);

        auth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar2);
        bottomNavigationView.setOnItemSelectedListener(nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FiyatFragment()).commit();
    }

    private NavigationBarView.OnItemSelectedListener nav = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.fiyatlar:

                    selectedFragment = new FiyatFragment();
                    break;
                case R.id.oneri:
                    selectedFragment = new OneriFragment();
                    break;
                case R.id.iletisim:
                    selectedFragment = new IletisimFragment();
                    break;
                case R.id.hakkinda:
                    selectedFragment = new HakkimdaFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cikis:
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.profil:
                Intent intent2 = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}