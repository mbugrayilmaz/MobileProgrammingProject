package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.cabinet;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Outfit;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.OutfitViewModel;

public class CabinetMainActivity extends AppCompatActivity {

    private OutfitViewModel outfitViewModel;

    private final ActivityResultLauncher<Void> createOutfitLauncher = registerForActivityResult(
            new CabinetCreateOutfitResultContract(), result -> {
                if (result != null) {
                    outfitViewModel.insert(result);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_main);

        outfitViewModel = new ViewModelProvider(this).get(OutfitViewModel.class);
    }

    public void onViewOutfits(View view) {
        Intent intent = new Intent(this, CabinetViewOutfitsActivity.class);

        startActivity(intent);
    }

    public void onCreateOutfit(View view) {
        createOutfitLauncher.launch(null);
    }
}