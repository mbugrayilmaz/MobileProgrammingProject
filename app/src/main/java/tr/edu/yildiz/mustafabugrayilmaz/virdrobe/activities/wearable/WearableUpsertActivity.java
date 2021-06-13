package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.wearable;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.tools.Tools;

public class WearableUpsertActivity extends AppCompatActivity {

    private String upsertType;

    private ProgressBar wearableUpsertProgressBar;
    private ImageView wearableUpsertImageView;
    private Button wearableUpsertPickImageButton;
    private EditText wearableUpsertPriceEditText;
    private EditText wearableUpsertTypeEditText;
    private EditText wearableUpsertColorEditText;
    private EditText wearableUpsertPatternEditText;
    private EditText wearableUpsertPurchaseDateEditText;
    private ImageButton wearableUpsertPickDateButton;
    private Button wearableUpsertButton;
    private LinearLayout wearableUpsertLoadingLinearLayout;

    private LocalDate selectedDate;

    private Wearable wearable;

    private Uri imageUri;

    private boolean imagePicked = false, isDefault = false;

    ActivityResultLauncher<String> getContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            result -> {
                if (result != null) {
                    imageUri = result;

                    CompletableFuture.runAsync(() -> wearableUpsertImageView.setImageURI(imageUri)).whenComplete((r, e) ->
                            wearableUpsertProgressBar.setVisibility(View.INVISIBLE));

                    imagePicked = true;

                    isDefault = false;
                } else {
                    wearableUpsertProgressBar.setVisibility(View.INVISIBLE);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearable_upsert);

        wearableUpsertProgressBar = findViewById(R.id.wearableUpsertProgressBar);
        wearableUpsertImageView = findViewById(R.id.wearableUpsertImageView);
        wearableUpsertPickImageButton = findViewById(R.id.wearableUpsertPickImageButton);
        wearableUpsertPriceEditText = findViewById(R.id.wearableUpsertPriceEditText);
        wearableUpsertTypeEditText = findViewById(R.id.wearableUpsertTypeEditText);
        wearableUpsertPurchaseDateEditText = findViewById(R.id.wearableUpsertPurchaseDateEditText);
        wearableUpsertColorEditText = findViewById(R.id.wearableUpsertColorEditText);
        wearableUpsertPatternEditText = findViewById(R.id.wearableUpsertPatternEditText);
        wearableUpsertPickDateButton = findViewById(R.id.wearableUpsertPickDateButton);
        wearableUpsertButton = findViewById(R.id.wearableUpsertButton);
        wearableUpsertLoadingLinearLayout = findViewById(R.id.wearableUpsertLoadingLinearLayout);

        wearableUpsertPurchaseDateEditText.setKeyListener(null);

        wearableUpsertPickDateButton.setOnClickListener(l ->
                new DatePickerDialog(this,
                        (datePicker, year, month, dayOfMonth) -> {
                            datePicker.updateDate(year, month, dayOfMonth);
                            setSelectedDate(year, month - 1, dayOfMonth);
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                ).show());

        upsertType = getIntent().getStringExtra("upsertType");

        wearable = getIntent().getParcelableExtra("wearable");

        if (upsertType.equals("insert")) {
            isDefault = true;

            wearableUpsertButton.setText("ADD WEARABLE");

            imageUri = Uri.parse("android.resource://tr.edu.yildiz.mustafabugrayilmaz.virdrobe/" + R.drawable.default_wearable);

            CompletableFuture.runAsync(() -> wearableUpsertImageView.setImageURI(imageUri)).whenComplete((r, e) ->
                    wearableUpsertProgressBar.setVisibility(View.INVISIBLE));

            selectedDate = LocalDate.now();
        } else if (upsertType.equals("update")) {
            isDefault = false;

            wearableUpsertButton.setText("SAVE CHANGES");

            imageUri = Uri.fromFile(new File(wearable.imageUri));

            wearableUpsertImageView.setImageURI(imageUri);
            wearableUpsertPriceEditText.setText(String.valueOf(wearable.price));
            wearableUpsertTypeEditText.setText(wearable.type);
            wearableUpsertColorEditText.setText(wearable.color);
            wearableUpsertPatternEditText.setText(wearable.pattern);

            selectedDate = wearable.purchaseDate;
        }

        wearableUpsertPurchaseDateEditText.setText(formatDate(selectedDate));
    }

    public void onUpsert(View view) {
        if (isValid()) {
            Intent resultIntent = new Intent();

            CompletableFuture<String> future;

            if (imagePicked) {
                wearableUpsertLoadingLinearLayout.setVisibility(View.VISIBLE);

                wearable.refreshImage = true;

                future = CompletableFuture.supplyAsync(() ->
                        Tools.saveImage(this, imageUri.toString()));

                future.whenComplete((n, t) -> {
                    try {
                        if (upsertType.equals("update")) {
                            Tools.deleteFile(wearable.imageUri);
                        }

                        wearable.imageUri = future.get();

                        sendResult(resultIntent);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                sendResult(resultIntent);
            }
        }
    }

    private void sendResult(Intent resultIntent) {
        wearable.type = wearableUpsertTypeEditText.getText().toString();
        wearable.color = wearableUpsertColorEditText.getText().toString();
        wearable.pattern = wearableUpsertPatternEditText.getText().toString();
        wearable.purchaseDate = selectedDate;
        wearable.price = Double.parseDouble(wearableUpsertPriceEditText.getText().toString());

        resultIntent.putExtra("wearable", wearable);

        setResult(RESULT_OK, resultIntent);

        finish();
    }

    public void onPickImage(View view) {
        getContent.launch("image/*");

        wearableUpsertProgressBar.setVisibility(View.VISIBLE);
    }

    private void setSelectedDate(int year, int month, int dayOfMonth) {
        selectedDate = LocalDate.of(year, month + 1, dayOfMonth);

        wearableUpsertPurchaseDateEditText.setText(formatDate(selectedDate));
    }

    private boolean isValid() {
        if (wearableUpsertTypeEditText.getText().toString().isEmpty()
                || wearableUpsertColorEditText.getText().toString().isEmpty()
                || wearableUpsertPriceEditText.getText().toString().isEmpty()
                || wearableUpsertPatternEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (isDefault) {
            Toast.makeText(this, "Please pick an image", Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("d MMM yyyy", getResources().getConfiguration().getLocales().get(0)));
    }
}