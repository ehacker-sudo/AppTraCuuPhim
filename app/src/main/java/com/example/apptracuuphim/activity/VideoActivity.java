package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.databinding.ActivityFilmDetailBinding;
import com.example.apptracuuphim.databinding.ActivityVideoBinding;

public class VideoActivity extends AppCompatActivity {
    private ActivityVideoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}