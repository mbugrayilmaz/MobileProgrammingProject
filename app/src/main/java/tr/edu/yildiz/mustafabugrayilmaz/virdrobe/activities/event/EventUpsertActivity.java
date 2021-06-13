package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;

public class EventUpsertActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText typeEditText;
    private EditText dateEditText;
    private ImageButton eventUpsertPickDateButton;
    private EditText locationEditText;

    private Button eventUpsertButton;

    private String upsertType;
    private Event event;

    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_upsert);

        upsertType = getIntent().getStringExtra("upsertType");
        event = getIntent().getParcelableExtra("event");

        nameEditText = findViewById(R.id.eventUpsertNameEditText);
        typeEditText = findViewById(R.id.eventUpsertTypeEditText);
        dateEditText = findViewById(R.id.eventUpsertDateEditText);
        eventUpsertPickDateButton = findViewById(R.id.eventUpsertPickDateButton);
        locationEditText = findViewById(R.id.eventUpsertLocationEditText);
        eventUpsertButton = findViewById(R.id.eventUpsertButton);

        dateEditText.setKeyListener(null);

        eventUpsertPickDateButton.setOnClickListener(l ->
                new DatePickerDialog(this,
                        (datePicker, year, month, dayOfMonth) -> {
                            datePicker.updateDate(year, month, dayOfMonth);
                            setSelectedDate(year, month, dayOfMonth);
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                ).show());

        if (upsertType.equals("insert")) {
            setTitle("Add Event");

            eventUpsertButton.setText("ADD EVENT");

            selectedDate = LocalDate.now();
        } else {
            setTitle("Edit " + event.name);

            selectedDate = event.eventDate;

            nameEditText.setText(event.name);
            typeEditText.setText(event.type);
            dateEditText.setText(formatDate(selectedDate));
            locationEditText.setText(event.location);

            eventUpsertButton.setText("SAVE CHANGES");
        }

        dateEditText.setText(formatDate(selectedDate));
    }

    public void onUpsert(View view) {
        if (isValid()) {
            Intent resultIntent = new Intent();

            event.name = nameEditText.getText().toString();
            event.type = typeEditText.getText().toString();
            event.eventDate = selectedDate;
            event.location = locationEditText.getText().toString();

            resultIntent.putExtra("event", event);

            setResult(RESULT_OK, resultIntent);

            finish();
        }
    }

    private boolean isValid() {
        if (nameEditText.getText().toString().isEmpty() ||
                typeEditText.getText().toString().isEmpty() ||
                locationEditText.getText().toString().isEmpty()
        ) {
            Toast.makeText(this, "Please fill in every field", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setSelectedDate(int year, int month, int dayOfMonth) {
        selectedDate = LocalDate.of(year, month + 1, dayOfMonth);

        dateEditText.setText(formatDate(selectedDate));
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("d MMM yyyy", getResources().getConfiguration().getLocales().get(0)));
    }
}