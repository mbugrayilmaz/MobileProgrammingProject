package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.cabinet;

import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.OutfitViewModel;

public class CabinetMainActivity extends AppCompatActivity {

    private OutfitViewModel outfitViewModel;
    private RecyclerView outfitRecyclerView;
    private CabinetOutfitListAdapter adapter;

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

        outfitRecyclerView = findViewById(R.id.cabinetViewOutfitsRecyclerView);

        outfitRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CabinetOutfitListAdapter(new CabinetOutfitListAdapter.CabinetOutfitDiff(), this);

        outfitRecyclerView.setAdapter(adapter);

        outfitViewModel.getAllOutfits().observe(this, adapter::submitList);
    }

    public void onAddOutfit(View view) {
        createOutfitLauncher.launch(null);
    }
}