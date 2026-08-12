package com.example.relax;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    private TextView moodHappy, moodSad, moodAngry, moodNeutral, moodTired, tvHistoryContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moodHappy = findViewById(R.id.moodHappy);
        moodSad = findViewById(R.id.moodSad);
        moodAngry = findViewById(R.id.moodAngry);
        moodNeutral = findViewById(R.id.moodNeutral);
        moodTired = findViewById(R.id.moodTired);
        tvHistoryContent = findViewById(R.id.tvHistoryContent);

        SharedPreferences prefs = getSharedPreferences("mood_data", MODE_PRIVATE);
        updateHistory(prefs);

        // Обработка нажатий с анимацией
        moodHappy.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            saveMood(prefs, "😊");
        });
        moodSad.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            saveMood(prefs, "😞");
        });
        moodAngry.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            saveMood(prefs, "😡");
        });
        moodNeutral.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            saveMood(prefs, "😐");
        });
        moodTired.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            saveMood(prefs, "😴");
        });
    }

    private void saveMood(SharedPreferences prefs, String mood) {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        prefs.edit().putString(today, mood).apply();
        Toast.makeText(this, "✅ Настроение сохранено: " + mood, Toast.LENGTH_SHORT).show();
        updateHistory(prefs);
    }

    private void updateHistory(SharedPreferences prefs) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (int i = 6; i >= 0; i--) {
            long time = System.currentTimeMillis() - i * 24L * 60L * 60L * 1000L;
            String date = sdf.format(new Date(time));
            String mood = prefs.getString(date, "—");
            sb.append("• ").append(date).append("  ").append(mood).append("\n");
        }

        tvHistoryContent.setText(sb.toString());
    }
}