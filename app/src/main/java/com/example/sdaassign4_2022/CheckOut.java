package com.example.sdaassign4_2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * check out activity
 */
public class CheckOut extends AppCompatActivity {

    TextView mDisplaySummary;
    TextView mConfirm;
    Button mBtnSendOrder;
    Calendar mDateAndTime = Calendar.getInstance();
    int position;
    Button mDate;
    TextView mAvailability;
    SharedPreferences sharedPref;

    /**
     * create check out ui
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_out);

        //set the toolbar we have overridden
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        sharedPref = this.getApplicationContext().getSharedPreferences(Constants.ApplicationSP, Context.MODE_PRIVATE);
        //find the summary textview
        mDisplaySummary = findViewById(R.id.orderSummary);
        mConfirm = findViewById(R.id.confirm);
        mBtnSendOrder = findViewById(R.id.orderButton);
        mDate = findViewById(R.id.date);
        mAvailability = findViewById(R.id.availability);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        mBtnSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String borrowerId = sharedPref.getString(Constants.BorrowerId, "");
                DatabaseReference dbRefBooks = myRef.child(Constants.Books);
                DatabaseReference dbRefBook = dbRefBooks.child(Constants.Book + position);
                dbRefBook.child(Constants.BID).setValue(borrowerId);
                dbRefBook.child(Constants.CurrentDate).setValue(DateUtils.formatDateTime(CheckOut.this, mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
                Calendar exDtCal = mDateAndTime;
                exDtCal.add(Calendar.DATE, 14);
                dbRefBook.child(Constants.ExpireDate).setValue(DateUtils.formatDateTime(CheckOut.this, exDtCal.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
                mDisplaySummary.setText("Success");
            }
        });

        if (getIntent() != null && getIntent().hasExtra(Constants.BookName)) ;
        {
            mConfirm.setText(getResources().getString(R.string.check_out) + getIntent().getStringExtra(Constants.BookName));
            position = getIntent().getIntExtra(Constants.Position, 1) + 1;
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                HashMap data = (HashMap) dataSnapshot.getValue();
                HashMap r = (HashMap) data.get(Constants.Books);

                    HashMap book = (HashMap) r.get(Constants.Book + position);
                    Date currentDate, expiredDate;
                    if (book.containsKey(Constants.CurrentDate)) {
                        currentDate = getDate(book.get(Constants.CurrentDate).toString());
                    }

                    if (book.containsKey(Constants.ExpireDate)) {
                        expiredDate = getDate(book.get(Constants.ExpireDate).toString());
                        if (System.currentTimeMillis() >= expiredDate.getTime()) {
                   mDate.setEnabled(true);
                            mBtnSendOrder.setEnabled(true);
                            mAvailability.setText(getResources().getString(R.string.book_available));
                        }
                        else{
                            mDate.setEnabled(false);
                            mBtnSendOrder.setEnabled(false);

                            mAvailability.setText(getResources().getString(R.string.book_available_date)+ expiredDate);
                        }
                    }
                    else{
                        mDate.setEnabled(true);
                        mBtnSendOrder.setEnabled(true);
                        mAvailability.setText(getResources().getString(R.string.book_available));
                    }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CheckOut.this, getResources().getString(R.string.fail_to_get_data), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * on date clicked
     * @param v
     */
    public void onDateClicked(View v) {

        DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mDateAndTime.set(Calendar.YEAR, year);
                mDateAndTime.set(Calendar.MONTH, monthOfYear);
                mDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateAndTimeDisplay();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(CheckOut.this, mDateListener,
                mDateAndTime.get(Calendar.YEAR),
                mDateAndTime.get(Calendar.MONTH),
                mDateAndTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(mDateAndTime.getTimeInMillis());
        datePickerDialog.show();

    }

    /**
     * on option item selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * update summary text
     */
    private void updateDateAndTimeDisplay() {
        //date time year
        CharSequence currentTime = DateUtils.formatDateTime(this, mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);
        CharSequence SelectedDate = DateUtils.formatDateTime(this, mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
        String finalSummary = SelectedDate + " "+getResources().getString(R.string.current_time_is) + currentTime + " \n "+getResources().getString(R.string.username_is)+ " "+ sharedPref.getString(Constants.UserName, "") + " \n "+getResources().getString(R.string.borrower_id_is)+" "+sharedPref.getString(Constants.BorrowerId, "");

        mDisplaySummary.setText(finalSummary);
    }

    /**
     * convert string to date
     * @param dt
     * @return
     */
    private Date getDate(String dt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DateFormat);
        Date date = null;
        try {
            date = dateFormat.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
