package pl.pb.pbtask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    LinearLayout draw;
    View f_line_1, f_line_2, s_line_1, s_line_2, t_line_1, t_line_2;
    TextView app_name, app_autor;
    Animation top_anim, middle_anim, bottom_anim;

    int SLEEP_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loading);

        draw = (LinearLayout) findViewById(R.id.draw);
        f_line_1 = (View) findViewById(R.id.f_line_1);
        f_line_2 = (View) findViewById(R.id.f_line_2);
        s_line_1 = (View) findViewById(R.id.s_line_1);
        s_line_2 = (View) findViewById(R.id.s_line_2);
        t_line_1 = (View) findViewById(R.id.t_line_1);
        t_line_2 = (View) findViewById(R.id.t_line_2);
        app_name = (TextView) findViewById(R.id.app_name);
        app_autor = (TextView) findViewById(R.id.app_autor);
        top_anim = (Animation) AnimationUtils.loadAnimation(this, R.anim.top_anim);
        middle_anim = (Animation) AnimationUtils.loadAnimation(this, R.anim.middle_anim);
        bottom_anim = (Animation) AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        f_line_1.setAnimation(top_anim);
        f_line_2.setAnimation(top_anim);
        s_line_1.setAnimation(top_anim);
        s_line_2.setAnimation(top_anim);
        t_line_1.setAnimation(top_anim);
        t_line_2.setAnimation(top_anim);
        app_name.setAnimation(middle_anim);
        app_autor.setAnimation(bottom_anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, SLEEP_TIME);

    }

    @Override
    public void onBackPressed() {
    }
}
