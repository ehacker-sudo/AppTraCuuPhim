package com.example.apptracuuphim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.apptracuuphim.R;
import com.example.apptracuuphim.adapter.CreditAdapter;
import com.example.apptracuuphim.adapter.PeopleAdapter;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.api.MovieApi;
import com.example.apptracuuphim.databinding.ActivityCastBinding;
import com.example.apptracuuphim.databinding.ActivityCreditBinding;
import com.example.apptracuuphim.listener.CreditListener;
import com.example.apptracuuphim.model.Credit.Credit;
import com.example.apptracuuphim.resource.CreditsResource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditActivity extends AppCompatActivity {
    private ActivityCreditBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        FilmApi.film.getCredits(getIntent().getStringExtra("media_type"),getIntent().getIntExtra("id",37854),"en")
//                .enqueue(new Callback<CreditsResource>() {
//                    @Override
//                    public void onResponse(Call<CreditsResource> call, Response<CreditsResource> response) {
//                        CreditsResource creditsResource = response.body();
//
//                        binding.filmCast.title.setText("Dàn diễn viên");
//                        binding.filmCast.recycleviewFilm.setLayoutManager(
//                                new LinearLayoutManager(CreditActivity.this, RecyclerView.HORIZONTAL,false)
//                        );
//
//                        binding.filmCast.recycleviewFilm.setAdapter(
//                                new CreditAdapter(
//                                        CreditActivity.this,
//                                        creditsResource.getCast(),
//                                        new CreditListener() {
//                                            @Override
//                                            public void onClick(Credit credit) {
//                                                Intent intent = new Intent(CreditActivity.this, CastActivity.class);
//                                                intent.putExtra("id",credit.getId());
//                                                startActivity(intent);
//                                            }
//                                        }
//                                ));
//                    }
//
//                    @Override
//                    public void onFailure(Call<CreditsResource> call, Throwable t) {
//
//                    }
//                });

    }
}