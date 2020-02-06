package pl.pb.pbtask;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

import maes.tech.intentanim.CustomIntent;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-25
 */

public class AddEditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "AddEditTaskActivity";

    public static final String EXTRA_ID = "pl.pb.pbtask.EXTRA_ID";
    public static final String EXTRA_TITLE = "pl.pb.pbtask.EXTRA_TITLE";
    public static final String EXTRA_DIFFICULTY = "pl.pb.pbtask.EXTRA_DIFFICULTY";
    public static final String EXTRA_DATE = "pl.pb.pbtask.EXTRA_DATE";
    public static final String EXTRA_TIME = "pl.pb.pbtask.EXTRA_TIME";
    public static final String EXTRA_REPEAT = "pl.pb.pbtask.EXTRA_REPEAT";
    public static final String EXTRA_REPEAT_TYPE = "pl.pb.pbtask.EXTRA_REPEAT_TYPE";
    public static final String EXTRA_REPEAT_NUMBER = "pl.pb.pbtask.EXTRA_REPEAT_NUMBER";
    public static final String EXTRA_FINISH = "pl.pb.pbtask.EXTRA_FINISH";

    EditText edit_task_title;
    Button edit_task_date, edit_task_time;
    Switch edit_task_repeat, edit_task_finish;
    NumberPicker edit_task_repeat_number;
    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        edit_task_title = findViewById(R.id.edit_task_title);
        //edit_task_difficulty = findViewById(R.id.edit_task_difficulty);
        edit_task_repeat_number = findViewById(R.id.edit_task_repeat_number);
        edit_task_date = findViewById(R.id.edit_task_date);
        edit_task_time = findViewById(R.id.edit_task_time);
        edit_task_repeat = findViewById(R.id.edit_task_repeat);
        edit_task_finish = findViewById(R.id.edit_task_finish);
        //edit_task_repeat_type = findViewById(R.id.edit_task_repeat_type);
        button_save = findViewById(R.id.button_save);

        edit_task_repeat_number.setMinValue(1);
        edit_task_repeat_number.setMaxValue(10);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Task");
            edit_task_title.setText(intent.getStringExtra(EXTRA_TITLE));
            //edit_task_difficulty.setText(intent.getStringExtra(EXTRA_DIFFICULTY));
            edit_task_repeat_number.setValue(Integer.parseInt(intent.getStringExtra(EXTRA_TITLE)));
            edit_task_date.setText(intent.getStringExtra(EXTRA_DATE));
            edit_task_time.setText(intent.getStringExtra(EXTRA_TIME));
            edit_task_repeat.setText(intent.getStringExtra(EXTRA_REPEAT));
            edit_task_finish.setText(intent.getStringExtra(EXTRA_FINISH));
            //edit_task_repeat_type.setText(intent.getStringExtra(EXTRA_REPEAT_TYPE));
        } else {
            setTitle("Add Task");
        }


        button_save.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(edit_task_title.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String task_title = edit_task_title.getText().toString();
                String task_date = "12.12.2015";

                replyIntent.putExtra(EXTRA_TITLE, task_title);
                replyIntent.putExtra(EXTRA_DATE, task_date);

                int task_id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (task_id != -1) {
                    replyIntent.putExtra(EXTRA_ID, task_id);
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        edit_task_date.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        edit_task_time.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }

    private void saveTask() {
        String task_title = edit_task_title.getText().toString();
        int task_difficulty = 1;
        int task_repeat_number = edit_task_repeat_number.getValue();
        String task_date = edit_task_date.getText().toString();
        String task_time = edit_task_time.getText().toString();
        boolean task_repeat = Boolean.parseBoolean(edit_task_repeat.toString());
        boolean task_finish = Boolean.parseBoolean(edit_task_finish.toString());
        String task_repeat_type = "day";

        if (task_title.trim().isEmpty()) {
            Toast.makeText(this, "Uzupełnij", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        edit_task_date.setText(currentDateString);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        edit_task_time.setText(hourOfDay + ":" + minute);
    }
}
