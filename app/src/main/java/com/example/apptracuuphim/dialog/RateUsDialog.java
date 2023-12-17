package com.example.apptracuuphim.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.apptracuuphim.R;
import com.example.apptracuuphim.activity.CreditActivity;
import com.example.apptracuuphim.activity.FilmDetailActivity;
import com.example.apptracuuphim.api.FilmApi;
import com.example.apptracuuphim.databinding.ActivityFilmDetailBinding;
import com.example.apptracuuphim.databinding.RateUsDialogBinding;
import com.example.apptracuuphim.listener.FilmClickListener;
import com.example.apptracuuphim.model.Film.Film;
import com.example.apptracuuphim.resource.RateResource;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateUsDialog extends BottomSheetDialog {
    private Context context;
    private RateUsDialogBinding binding;
    protected int ratingApp = 0;
    protected Film film;
    protected FilmClickListener filmClickListener;
    public RateUsDialog(@NonNull Context context,Film film,FilmClickListener filmClickListener) {
        super(context);
        this.context = context;
        this.film = film;
        this.filmClickListener = filmClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = RateUsDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                film.setRate_value(ratingApp);
                if (ratingApp > 0) {
                    FilmApi.film.AddRating(film.getMedia_type(),film.getId(),ratingApp)
                                .enqueue(new Callback<RateResource>() {
                                    @Override
                                    public void onResponse(Call<RateResource> call, Response<RateResource> response) {
                                        RateResource rateResource = response.body();
                                        if (rateResource.isSuccess()) {
                                            Toast.makeText(context,"Đánh giá thành công", Toast.LENGTH_SHORT).show();
                                            filmClickListener.onClickItemMovie(film);
                                        }
                                        else {
                                            Toast.makeText(context,rateResource.getStatus_message(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<RateResource> call, Throwable t) {
                                        Toast.makeText(context,t.getMessage(), Toast.LENGTH_SHORT).show();
                                        t.printStackTrace();
                                    }
                                });
                    dismiss();
                }
                else {
                    dismiss();
                }
            }
        });

        Glide.with(getContext()).load("https://image.tmdb.org/t/p/original" + film.getPoster_path()).into(binding.filmPoster);
        binding.filmName.setText(film.getName());
        binding.rateLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingApp = (int)rating;
                if (ratingApp != 0) {
                    binding.filmBg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.item_rate_bg));
                    binding.rateBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_back_blue));
                    binding.tvRate.setText(String.valueOf((int)rating));
                }
            }
        });
    }
}
