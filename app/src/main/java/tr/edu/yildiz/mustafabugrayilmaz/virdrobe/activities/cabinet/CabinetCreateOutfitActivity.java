package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.cabinet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Outfit;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

public class CabinetCreateOutfitActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton headImageButton, faceImageButton, upperBodyImageButton, lowerBodyImageButton, feetImageButton;

    private String category;
    private Wearable head, face, upperBody, lowerBody, feet;
    private String headUri, faceUri, upperBodyUri, lowerBodyUri, feetUri;

    private final ArrayList<Long> pickedList = new ArrayList<>();

    private final ActivityResultLauncher<ArrayList<Long>> pickWearableLauncher = registerForActivityResult(new CabinetDrawerResultContract(), result -> {
        if (result != null) {
            onActivityResult(result);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_create_outfit);

        headImageButton = findViewById(R.id.cabinetCreateHeadImageButton);
        faceImageButton = findViewById(R.id.cabinetCreateFaceImageButton);
        upperBodyImageButton = findViewById(R.id.cabinetCreateUpperBodyImageButton);
        lowerBodyImageButton = findViewById(R.id.cabinetCreateLowerBodyImageButton);
        feetImageButton = findViewById(R.id.cabinetCreateFeetImageButton);

        headImageButton.setOnClickListener(this);
        faceImageButton.setOnClickListener(this);
        upperBodyImageButton.setOnClickListener(this);
        lowerBodyImageButton.setOnClickListener(this);
        feetImageButton.setOnClickListener(this);

        pickedList.add(-1L);
        pickedList.add(-1L);
        pickedList.add(-1L);
        pickedList.add(-1L);
        pickedList.add(-1L);
    }

    public void onSaveOutfit(View view) {
        if (isValid()) {
            Intent intent = new Intent();

            Outfit outfit = new Outfit();

            outfit.head = head.id;
            outfit.face = face.id;
            outfit.upperBody = upperBody.id;
            outfit.lowerBody = lowerBody.id;
            outfit.feet = feet.id;

            intent.putExtra("outfit", outfit);

            setResult(Activity.RESULT_OK, intent);

            finish();
        }
    }

    private boolean isValid() {
        if (headUri == null ||
                faceUri == null ||
                upperBodyUri == null ||
                lowerBodyUri == null ||
                feetUri == null) {
            Toast.makeText(this, "Please choose image for all parts", Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        System.out.println(viewId);

        final int head = R.id.cabinetCreateHeadImageButton;
        final int face = R.id.cabinetCreateFaceImageButton;
        final int upperBody = R.id.cabinetCreateUpperBodyImageButton;
        final int lowerBody = R.id.cabinetCreateLowerBodyImageButton;
        final int feet = R.id.cabinetCreateFeetImageButton;

        switch (viewId) {
            case head:
                category = "head";

                break;

            case face:
                category = "face";

                break;

            case upperBody:
                category = "upperBody";

                break;

            case lowerBody:
                category = "lowerBody";

                break;

            case feet:
                category = "feet";

                break;

            default:
                break;
        }

        pickWearableLauncher.launch(pickedList);
    }

    private void onActivityResult(Wearable result) {
        int index = 0;

        switch (category) {
            case "head":

                head = result;

                headUri = result.imageUri;

                headImageButton.setImageURI(Uri.parse(headUri));

                index = 0;

                break;

            case "face":
                face = result;

                faceUri = result.imageUri;

                faceImageButton.setImageURI(Uri.parse(faceUri));

                index = 1;

                break;

            case "upperBody":
                upperBody = result;

                upperBodyUri = result.imageUri;

                upperBodyImageButton.setImageURI(Uri.parse(upperBodyUri));

                index = 2;

                break;

            case "lowerBody":
                lowerBody = result;

                lowerBodyUri = result.imageUri;

                lowerBodyImageButton.setImageURI(Uri.parse(lowerBodyUri));

                index = 3;

                break;

            case "feet":
                feet = result;

                feetUri = result.imageUri;

                feetImageButton.setImageURI(Uri.parse(feetUri));

                index = 4;

                break;

            default:
                break;
        }

        pickedList.set(index, result.id);
    }

}