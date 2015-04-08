package la.com.mavin.udacityfinal.fragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import la.com.mavin.udacityfinal.R;
import la.com.mavin.udacityfinal.helper.MyHelper;

/**
 * Created by Adsavin on 4/8/2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private TextView txt;

    public DatePickerFragment() {
    }

    public void setTxt(TextView txt) {
        this.txt = txt;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog d = new DatePickerDialog(getActivity(), this, year, month, day);

        // Create a new instance of DatePickerDialog and return it
        return d;
    }

    @Override
    @TargetApi(11)
    public void onDateSet(DatePicker view, int year, int month, int day) {
        if(txt != null) {
            txt.setText(MyHelper.formatDateToShow(MyHelper.formatDate(year, month +1, day), "/"));
        }
    }

}
