package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.databinding.ActivityAdvancedSearchBinding;
import com.example.apptracuuphim.databinding.ActivityAllItemBinding;
import com.example.apptracuuphim.databinding.ActivityAllSeasonBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSeasonActivity extends AppCompatActivity {
    private ActivityAllSeasonBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllSeasonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}