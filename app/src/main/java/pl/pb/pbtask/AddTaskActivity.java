package pl.pb.pbtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        editText = findViewById(R.id.edit_word);

        Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String task = editText.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, task);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }
}
