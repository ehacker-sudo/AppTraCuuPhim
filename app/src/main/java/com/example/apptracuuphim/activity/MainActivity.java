package com.example.apptracuuphim.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.databinding.ActivityMainBinding;
import com.example.apptracuuphim.view.HomeFragment;
import com.example.apptracuuphim.view.MovieFragment;
import com.example.apptracuuphim.view.TvFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private boolean mIsSaved;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Fragment(new HomeFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.home_unpress));
            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.movie));
            menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.tv));
            Fragment(new HomeFragment());
            return true;
        } else if (id == R.id.movie) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.home));
            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.movie_unpress));
            menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.tv));
            Fragment(new MovieFragment());
            return true;
        } else if (id == R.id.tv) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.home));
            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.movie));
            menu.getItem(2).setIcon(getResources().getDrawable(R.drawable.tv_unpress));
            Fragment(new TvFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Fragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main,fragment);
        fragmentTransaction.commit();
    }
}