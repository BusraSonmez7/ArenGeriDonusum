package com.project.arengeridonusum.Yonetim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.project.arengeridonusum.R;
import com.project.arengeridonusum.Users.UserLoginActivity;
import com.project.arengeridonusum.Users.UserProfile;
import com.project.arengeridonusum.Yonetim.Hakkimda.HakkimdaFragment;
import com.project.arengeridonusum.Yonetim.Iletisim.IletisimFragment;
import com.project.arengeridonusum.Yonetim.Oneri.OneriFragment;
import com.project.arengeridonusum.Yonetim.UrunEkleme.UrunFragment;
import com.project.arengeridonusum.Yonetim.Urunler.UrunListesiFragment;

public class YonetimMainPage extends AppCompatActivity {

    private FirebaseAuth auth;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonetim_main_page);

        frameLayout = findViewById(R.id.fragment_container);

        auth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar2);
        bottomNavigationView.setOnItemSelectedListener(nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new UrunFragment()).commit();

        Intent intent = getIntent();
        String fragmentk = (String) intent.getStringExtra("fragment");
        if(fragmentk==null){
        }
        else {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new UrunListesiFragment()).commit();
        }

    }

    private NavigationBarView.OnItemSelectedListener nav = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.urunekle:

                    selectedFragment = new UrunFragment();
                    break;
                case R.id.urunList:
                    selectedFragment = new UrunListesiFragment();
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