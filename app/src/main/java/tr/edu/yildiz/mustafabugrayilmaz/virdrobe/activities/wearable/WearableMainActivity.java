package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.wearable;

import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.WearableViewModel;

public class WearableMainActivity extends AppCompatActivity {

    private RecyclerView wearableRecyclerView;
    private WearableViewModel wearableViewModel;
    private WearableListAdapter adapter;

    private Drawer drawer;

    ActivityResultLauncher<Pair<String, Wearable>> activityForResult = registerForActivityResult(new WearableResultContract(),
            wearable -> {
                if (wearable != null) {
                    wearableViewModel.insert(wearable);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearable_main);

        drawer = getIntent().getParcelableExtra("drawer");

        setTitle(drawer.name);

        wearableRecyclerView = findViewById(R.id.wearableRecyclerView);

        wearableRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WearableListAdapter(new WearableListAdapter.WearableDiff(), this);

        wearableRecyclerView.setAdapter(adapter);

        wearableViewModel = new ViewModelProvider(this).get(WearableViewModel.class);

        wearableViewModel.getByDrawerId(drawer.id).observe(this, adapter::submitList);
    }

    public void onAddWearable(View view) {
        Wearable wearable = new Wearable();

        wearable.drawerId = drawer.id;

        activityForResult.launch(new Pair<>("insert", wearable));
    }
}