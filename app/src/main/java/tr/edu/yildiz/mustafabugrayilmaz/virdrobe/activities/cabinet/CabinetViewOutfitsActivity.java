package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.cabinet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.wearable.WearableListAdapter;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.OutfitViewModel;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.WearableViewModel;

public class CabinetViewOutfitsActivity extends AppCompatActivity {

    private OutfitViewModel outfitViewModel;
    private RecyclerView outfitRecyclerView;
    private CabinetOutfitListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_view_outfits);

        outfitViewModel = new ViewModelProvider(this).get(OutfitViewModel.class);

        outfitRecyclerView = findViewById(R.id.cabinetViewOutfitsRecyclerView);

        outfitRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CabinetOutfitListAdapter(new CabinetOutfitListAdapter.CabinetOutfitDiff(), this);

        outfitRecyclerView.setAdapter(adapter);

        outfitViewModel.getAllOutfits().observe(this, adapter::submitList);
    }
}