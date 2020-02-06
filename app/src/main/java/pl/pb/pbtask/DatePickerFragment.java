package pl.pb.pbtask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

/*
 * Created by AndroidStudio.
 * User: piotrbec
 * Date: 2020-01-25
 */

public class DatePickerFragment extends DialogFragment {
    private static final String TAG = "DatePickerFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(Objects.requireNonNull(getActivity()), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }
}
