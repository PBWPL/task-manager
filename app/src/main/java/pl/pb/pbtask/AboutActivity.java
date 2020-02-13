package pl.pb.pbtask;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

import maes.tech.intentanim.CustomIntent;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-25
 */

public class AboutActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        MaterialTextView version = findViewById(R.id.app_version);
        MaterialTextView author = findViewById(R.id.app_author);
        MaterialTextView email = findViewById(R.id.app_email);

        version.setText(getResources().getString(R.string.version) + ": " + getResources().getString(R.string.app_version));
        author.setText(getResources().getString(R.string.author) + ": " + getResources().getString(R.string.app_author));
        email.setText(getResources().getString(R.string.email) + ": " + getResources().getString(R.string.app_email));
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}