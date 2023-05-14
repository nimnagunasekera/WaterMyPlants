package com.example.watermyplants;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;


public class AddFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextView datePickerEditText;
    private TextView timePickerEditText;
    private Date startingDate;
    private RadioGroup reminderFrequency;
    private RadioButton dailyRadioButton, weeklyRadioButton, everyOtherDayRadioButton;
    private Calendar reminderDate;
    private TextView nextReminderDateTextView;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private ImageView cameraBtn;
    private Button save, cancel;


    public interface NavigationListener {
        void replaceFragment(Fragment fragment);
    }

    private NavigationListener listener;

    public AddFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);


        datePickerEditText = view.findViewById(R.id.dateText);
        timePickerEditText = view.findViewById(R.id.timeText);
        reminderFrequency = view.findViewById(R.id.frequency);
        dailyRadioButton = view.findViewById(R.id.radioButton);
        everyOtherDayRadioButton = view.findViewById(R.id.radioButton2);
        weeklyRadioButton = view.findViewById(R.id.radioButton3);

        //buttons
        save = view.findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the date picker field is empty
                if (datePickerEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if the time picker field is empty
                if (timePickerEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please select a time", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if the reminder frequency radio group has no selection
                if (reminderFrequency.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getContext(), "Please select a reminder frequency", Toast.LENGTH_SHORT).show();
                    return;
                }
                // All fields are filled, so proceed with saving the data
                NavigationActivity activity = (NavigationActivity) getActivity();
                activity.replaceFragment(new HomeFragment());
            }
        });

        cancel = view.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity activity = (NavigationActivity) getActivity();
                activity.replaceFragment(new HomeFragment());
            }
        });


        //camera
        cameraBtn = view.findViewById(R.id.imageImageView);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });


        // Find the nextReminderDateTextView in a different layout file
        View otherLayout = inflater.inflate(R.layout.card_view, container, false);
        nextReminderDateTextView = otherLayout.findViewById(R.id.txtNextReminder);

        // Set the radio button selection based on the user's reminder frequency preference
        if (reminderFrequency.getCheckedRadioButtonId() == dailyRadioButton.getId()) {
            dailyRadioButton.setChecked(true);
        } else if (reminderFrequency.getCheckedRadioButtonId() == everyOtherDayRadioButton.getId()) {
            everyOtherDayRadioButton.setChecked(true);
        } else if (reminderFrequency.getCheckedRadioButtonId() == weeklyRadioButton.getId()) {
            weeklyRadioButton.setChecked(true);
        }

        //Time
        timePickerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the default time to the current time
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and show it
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Do something when the time is set

                        // Format the selected time as a string
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);

                        // Set the selected time in the TextView
                        TextView textView = getView().findViewById(R.id.timeText);
                        textView.setText(selectedTime);
                    }
                }, hour, minute, true); // Set the initial time values and the 24-hour format flag
                timePickerDialog.show();
            }
        });



        // Date
        datePickerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the current date as the default date in the picker
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Create a new instance of DatePickerDialog and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AddFragment.this, year, month, day);
                datePickerDialog.show();
            }
        });

        // ... do something with the views ...

        return view;
    }


    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera();
        }
    }


    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(requireContext(), "Camera Permission Required to Use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            cameraBtn.setImageBitmap(image);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Set the selected date to the EditText view
        TextView datePickerTextView = getView().findViewById(R.id.dateText);
        @SuppressLint("DefaultLocale") String date = String.format("%d/%d/%d", month + 1, dayOfMonth, year);
        datePickerTextView.setText(date);

        // Save the selected date in a Calendar object
        reminderDate = Calendar.getInstance();
        reminderDate.set(year, month, dayOfMonth);

        // Calculate and set the reminder if all fields are set
//        setReminder();

        // Create a date object for the selected starting date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        startingDate = calendar.getTime();

        // Calculate the next reminder date and time based on the selected starting date
        long reminderInterval = getReminderInterval();
        Date nextReminderDate = new Date(startingDate.getTime() + reminderInterval);

        // Convert the date to a string
        String nextReminderDateString = nextReminderDate.toString();

        // Set the string on the TextView
        nextReminderDateTextView.setText(nextReminderDateString);
    }

    private long getReminderInterval() {
        long reminderInterval = 0;
        int selectedRadioButtonId = reminderFrequency.getCheckedRadioButtonId();
        if (selectedRadioButtonId == dailyRadioButton.getId()) {
            reminderInterval = 24 * 60 * 60 * 1000; // 1 day in milliseconds
        } else if (selectedRadioButtonId == weeklyRadioButton.getId()) {
            reminderInterval = 7 * 24 * 60 * 60 * 1000; // 1 week in milliseconds
        } else if (selectedRadioButtonId == everyOtherDayRadioButton.getId()) {
            reminderInterval = 2 * 24 * 60 * 60 * 1000; // 2 days in milliseconds
        }
        return reminderInterval;
    }

//    private void setReminder() {
//        if (reminderDate == null, hourPicker == null, minutePicker == null) {
//            // Reminder date and/or time not set yet, do nothing
//            return;
//        }
//
//        // Get the selected radio button
//        int radioButtonId = reminderFrequency.getCheckedRadioButtonId();
//        RadioButton radioButton = reminderFrequency.findViewById(radioButtonId);
//        String reminderFrequencyText = radioButton.getText().toString();
//
//        // Calculate the reminder date and time based on the selected frequency and date/time
//        // ... your reminder calculation logic here ...
//
//        // Update the UI to display the new reminder date and time
//        // ... update the card view in the recycler view here ...
//
//        String frequency = "";
//        int selectedRadioButtonId = reminderFrequency.getCheckedRadioButtonId();
//        if (selectedRadioButtonId == dailyRadioButton.getId()) {
//            frequency = "daily";
//        } else if (selectedRadioButtonId == weeklyRadioButton.getId()) {
//            frequency = "weekly";
//        } else if (selectedRadioButtonId == everyOtherDayRadioButton.getId()) {
//            frequency = "every other day";
//        }
//
//        // Calculate the next reminder date and time based on the selected starting date
//        long reminderInterval = getReminderInterval();
//        Date nextReminderDate = new Date(startingDate.getTime() + reminderInterval);
//
//        // TODO: Set up a reminder using Android's AlarmManager
//
//        // Show a toast message to confirm that the reminder has been set
//        String toastMessage = "Reminder set for " + nextReminderDate.toString() + " (" + frequency + " frequency)";
//        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
//    }



}
