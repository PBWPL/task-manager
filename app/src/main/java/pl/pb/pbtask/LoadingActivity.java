package pl.pb.pbtask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import maes.tech.intentanim.CustomIntent;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-25
 */

public class LoadingActivity extends AppCompatActivity {
    private static final String TAG = "LoadingActivity";

    // TODO: Add ProgressBar - LOW
    // https://www.youtube.com/watch?v=hSfN_aYKkzo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

        init();
    }

    private void init() {
        View f_line_1 = findViewById(R.id.f_line_1);
        View f_line_2 = findViewById(R.id.f_line_2);
        View s_line_1 = findViewById(R.id.s_line_1);
        View s_line_2 = findViewById(R.id.s_line_2);
        View t_line_1 = findViewById(R.id.t_line_1);
        View t_line_2 = findViewById(R.id.t_line_2);
        TextView app_name = findViewById(R.id.app_name);
        TextView app_author = findViewById(R.id.app_autor);
        Animation top_anim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        Animation middle_anim = AnimationUtils.loadAnimation(this, R.anim.middle_anim);
        Animation bottom_anim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        f_line_1.setAnimation(top_anim);
        f_line_2.setAnimation(top_anim);
        s_line_1.setAnimation(top_anim);
        s_line_2.setAnimation(top_anim);
        t_line_1.setAnimation(top_anim);
        t_line_2.setAnimation(top_anim);
        app_name.setAnimation(middle_anim);
        app_author.setAnimation(bottom_anim);

        int SLEEP_TIME = 5000;
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LoadingActivity.this, DashboardActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            CustomIntent.customType(LoadingActivity.this, "up-to-bottom");
        }, SLEEP_TIME);
        finishActivity(1);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "click back button");
    }
}