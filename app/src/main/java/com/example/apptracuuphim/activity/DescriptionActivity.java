package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.databinding.ActivityDescriptionBinding;

public class DescriptionActivity extends AppCompatActivity {
    private ActivityDescriptionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar.toolbar);

        binding.toolbar.toolbarTitle.setText(getIntent().getStringExtra("name"));

        binding.castBio.textViewBioCast.setText(getIntent().getStringExtra("bio"));

        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}