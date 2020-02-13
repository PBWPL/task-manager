package pl.pb.pbtask;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

import maes.tech.intentanim.CustomIntent;
import pl.pb.pbtask.Alarm.AlarmScheduler;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-02-05
 */

public class AddEditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String DEBUG_TAG = "AddEditTaskActivity";

    public static final String EXTRA_ID = "pl.pb.pbtask.EXTRA_ID";
    public static final String EXTRA_TITLE = "pl.pb.pbtask.EXTRA_TITLE";
    public static final String EXTRA_DIFFICULTY = "pl.pb.pbtask.EXTRA_DIFFICULTY";
    public static final String EXTRA_DATE = "pl.pb.pbtask.EXTRA_DATE";
    public static final String EXTRA_TIME = "pl.pb.pbtask.EXTRA_TIME";
    public static final String EXTRA_REPEAT = "pl.pb.pbtask.EXTRA_REPEAT";
    public static final String EXTRA_REPEAT_TYPE = "pl.pb.pbtask.EXTRA_REPEAT_TYPE";
    public static final String EXTRA_REPEAT_NUMBER = "pl.pb.pbtask.EXTRA_REPEAT_NUMBER";
    public static final String EXTRA_ACTIVE = "pl.pb.pbtask.EXTRA_ACTIVE";

    private static final long miliMinute = 60000L;
    private static final long miliHour = 3600000L;
    private static final long miliDay = 86400000L;
    private static final long miliWeek = 604800000L;

    int tmp_year, tmp_month, tmp_day, tmp_hour, tmp_minute, tmp_second;
    long repeat_time = 1;
    long timestamp;

    Uri uri;

    EditText edit_task_title;
    Button edit_task_date, edit_task_time;
    SwitchMaterial edit_task_repeat, edit_task_active;
    NumberPicker edit_task_repeat_number;
    Button button_save;
    TextView edit_task_repeat_type_text;
    RadioGroup edit_task_repeat_type;
    RadioButton edit_task_difficulty_1, edit_task_difficulty_2, edit_task_difficulty_3,
            edit_task_repeat_type_1, edit_task_repeat_type_2, edit_task_repeat_type_3,
            edit_task_repeat_type_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        edit_task_title = findViewById(R.id.edit_task_title);
        edit_task_difficulty_1 = findViewById(R.id.edit_task_difficulty_1);
        edit_task_difficulty_2 = findViewById(R.id.edit_task_difficulty_2);
        edit_task_difficulty_3 = findViewById(R.id.edit_task_difficulty_3);
        edit_task_repeat_number = findViewById(R.id.edit_task_repeat_number);
        edit_task_date = findViewById(R.id.edit_task_date);
        edit_task_time = findViewById(R.id.edit_task_time);
        edit_task_repeat = findViewById(R.id.edit_task_repeat);
        edit_task_active = findViewById(R.id.edit_task_active);
        edit_task_repeat_type_text = findViewById(R.id.edit_task_repeat_type_text);
        edit_task_repeat_type = findViewById(R.id.edit_task_repeat_type);
        edit_task_repeat_type_1 = findViewById(R.id.edit_task_repeat_type_1);
        edit_task_repeat_type_2 = findViewById(R.id.edit_task_repeat_type_2);
        edit_task_repeat_type_3 = findViewById(R.id.edit_task_repeat_type_3);
        edit_task_repeat_type_4 = findViewById(R.id.edit_task_repeat_type_4);
        button_save = findViewById(R.id.button_save);

        edit_task_repeat_number.setMinValue(1);
        edit_task_repeat_number.setMaxValue(10);

        Intent intent = getIntent();
        uri = intent.getData();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Task");
            edit_task_title.setText(intent.getStringExtra(EXTRA_TITLE));
            setDifficulty(intent.getStringExtra(EXTRA_DIFFICULTY));
            edit_task_repeat_number.setValue(Integer.parseInt(intent.getStringExtra(EXTRA_REPEAT_NUMBER)));
            edit_task_date.setText(intent.getStringExtra(EXTRA_DATE));
            edit_task_time.setText(intent.getStringExtra(EXTRA_TIME));
            if (intent.getStringExtra(EXTRA_REPEAT).equals("true")) {
                edit_task_repeat.setChecked(true);
            }
            if (intent.getStringExtra(EXTRA_ACTIVE).equals("true")) {
                edit_task_active.setChecked(true);
            }
            if (intent.getStringExtra(EXTRA_REPEAT_TYPE) != null) {
                setRepeatType(intent.getStringExtra(EXTRA_REPEAT_TYPE));
            }

        } else {
            setTitle("Add Task");
        }

        button_save.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            if (!edit_task_repeat.isChecked()) {
                if (edit_task_title.getText().toString().isEmpty()) {
                    edit_task_title.setError(getResources().getText(R.string.complete));
                    return;
                }
                if (getDifficulty() == null) {
                    Toast.makeText(this, getResources().getText(R.string.complete), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edit_task_date.getText().toString().trim().equals("Data") || edit_task_date.getText().toString().trim().equals("Date")) {
                    Toast.makeText(this, getResources().getText(R.string.complete), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edit_task_time.getText().toString().trim().equals("Czas") || edit_task_time.getText().toString().trim().equals("Time")) {
                    Toast.makeText(this, getResources().getText(R.string.complete), Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                if (edit_task_title.getText().toString().isEmpty()) {
                    edit_task_title.setError(getResources().getText(R.string.complete));
                    return;
                }
                if (getDifficulty() == null) {
                    Toast.makeText(this, getResources().getText(R.string.complete), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edit_task_date.getText().toString().trim().equals("Data") || edit_task_date.getText().toString().trim().equals("Date")) {
                    Toast.makeText(this, getResources().getText(R.string.complete), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edit_task_time.getText().toString().trim().equals("Czas") || edit_task_time.getText().toString().trim().equals("Time")) {
                    Toast.makeText(this, getResources().getText(R.string.complete), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getRepeatType() == null) {
                    Toast.makeText(this, getResources().getText(R.string.complete), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (TextUtils.isEmpty(edit_task_title.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String task_title = edit_task_title.getText().toString();
                String task_difficulty = getDifficulty();
                String task_repeat_number = String.valueOf(edit_task_repeat_number.getValue());
                String task_date = String.valueOf(edit_task_date.getText());
                String task_time = String.valueOf(edit_task_time.getText());
                String task_repeat, task_active;
                if (edit_task_repeat.isChecked()) {
                    task_repeat = "true";
                } else {
                    task_repeat = "false";
                }
                if (edit_task_active.isChecked()) {
                    task_active = "true";
                } else {
                    task_active = "false";
                }
                String task_repeat_type = getRepeatType();

                replyIntent.putExtra(EXTRA_TITLE, task_title);
                replyIntent.putExtra(EXTRA_DIFFICULTY, task_difficulty);
                replyIntent.putExtra(EXTRA_REPEAT_NUMBER, task_repeat_number);
                replyIntent.putExtra(EXTRA_DATE, task_date);
                replyIntent.putExtra(EXTRA_TIME, task_time);
                replyIntent.putExtra(EXTRA_REPEAT, task_repeat);
                replyIntent.putExtra(EXTRA_ACTIVE, task_active);
                replyIntent.putExtra(EXTRA_REPEAT_TYPE, task_repeat_type);
                replyIntent.putExtra(EXTRA_ACTIVE, task_active);

                int task_id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (task_id != -1) {
                    replyIntent.putExtra(EXTRA_ID, task_id);
                }
                setResult(RESULT_OK, replyIntent);

                if (task_title.isEmpty()) {
                    Toast.makeText(this, getResources().getText(R.string.complete), Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar tmp_calendar = Calendar.getInstance();
                tmp_calendar.set(Calendar.YEAR, tmp_year);
                tmp_calendar.set(Calendar.MONTH, tmp_month);
                tmp_calendar.set(Calendar.DATE, tmp_day);
                tmp_calendar.set(Calendar.HOUR_OF_DAY, tmp_hour);
                tmp_calendar.set(Calendar.MINUTE, tmp_minute);
                tmp_calendar.set(Calendar.SECOND, tmp_second);

                timestamp = tmp_calendar.getTimeInMillis();

                if (edit_task_active.isChecked()) {
                    if (edit_task_repeat.isChecked()) {
                        switch (task_repeat_type) {
                            case "Minute":
                                repeat_time = Integer.parseInt(task_repeat_number) * miliMinute;
                                break;
                            case "Hour":
                                repeat_time = Integer.parseInt(task_repeat_number) * miliHour;
                                break;
                            case "Day":
                                repeat_time = Integer.parseInt(task_repeat_number) * miliDay;
                                break;
                            case "Week":
                                repeat_time = Integer.parseInt(task_repeat_number) * miliWeek;
                                break;
                        }
                        new AlarmScheduler().cancelAlarm(getApplicationContext(), uri);
                        new AlarmScheduler().setRepeatAlarm(getApplicationContext(), timestamp, uri, repeat_time);
                    } else {
                        new AlarmScheduler().cancelAlarm(getApplicationContext(), uri);
                        new AlarmScheduler().setAlarm(getApplicationContext(), timestamp, uri);
                    }
                } else {
                    new AlarmScheduler().cancelAlarm(getApplicationContext(), uri);
                }
            }
            finish();
        });

        edit_task_repeat.setOnClickListener(v -> showRepeatOptions());

        edit_task_date.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        edit_task_time.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

        showRepeatOptions();
    }

    private void showRepeatOptions() {
        if (!edit_task_repeat.isChecked()) {
            edit_task_repeat_type_text.setVisibility(View.GONE);
            edit_task_repeat_type.setVisibility(View.GONE);
            edit_task_repeat_number.setVisibility(View.GONE);
        } else {
            edit_task_repeat_type_text.setVisibility(View.VISIBLE);
            edit_task_repeat_type.setVisibility(View.VISIBLE);
            edit_task_repeat_number.setVisibility(View.VISIBLE);
        }
    }

    public String getRepeatType() {
        if (edit_task_repeat_type_1.isChecked()) {
            return "minute";
        } else if (edit_task_repeat_type_2.isChecked()) {
            return "hour";
        } else if (edit_task_repeat_type_3.isChecked()) {
            return "day";
        } else if (edit_task_repeat_type_4.isChecked()) {
            return "week";
        }
        return null;
    }

    public void setRepeatType(String repeat_type) {
        switch (repeat_type) {
            case "minute":
                edit_task_repeat_type_1.setChecked(true);
                break;
            case "hour":
                edit_task_repeat_type_2.setChecked(true);
                break;
            case "day":
                edit_task_repeat_type_3.setChecked(true);
                break;
            case "week":
                edit_task_repeat_type_4.setChecked(true);
                break;
        }
    }

    public String getDifficulty() {
        if (edit_task_difficulty_1.isChecked()) {
            return "easy";
        } else if (edit_task_difficulty_2.isChecked()) {
            return "middle";
        } else if (edit_task_difficulty_3.isChecked()) {
            return "hard";
        }
        return null;
    }

    public void setDifficulty(String difficulty) {
        switch (difficulty) {
            case "easy":
                edit_task_difficulty_1.setChecked(true);
                break;
            case "middle":
                edit_task_difficulty_2.setChecked(true);
                break;
            case "hard":
                edit_task_difficulty_3.setChecked(true);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, day);
        tmp_year = year;
        tmp_month = month;
        tmp_day = day;
        edit_task_date.setText(day + "/" + month + "/" + year);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        tmp_hour = hourOfDay;
        tmp_minute = minute;
        tmp_second = 0;
        edit_task_time.setText(minute < 10 ? hourOfDay + ":0" + minute : hourOfDay + ":" + minute);
    }
}