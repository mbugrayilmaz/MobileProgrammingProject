package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.cabinet.CabinetWearableListAdapter;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.WearableViewModel;

public class EventPickWearableActivity extends AppCompatActivity {
    private RecyclerView wearableRecyclerView;
    private WearableViewModel wearableViewModel;
    private EventWearableListAdapter adapter;

    private Drawer drawer;

    private ArrayList<Long> pickedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pick_wearable);

        drawer = getIntent().getParcelableExtra("drawer");

        pickedList = (ArrayList<Long>) getIntent().getSerializableExtra("pickedList");

        setTitle(drawer.name);

        wearableRecyclerView = findViewById(R.id.eventWearableRecyclerView);

        wearableRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EventWearableListAdapter(new CabinetWearableListAdapter.CabinetWearableDiff(), this, pickedList);

        wearableRecyclerView.setAdapter(adapter);

        wearableViewModel = new ViewModelProvider(this).get(WearableViewModel.class);

        wearableViewModel.getByDrawerId(drawer.id).observe(this, adapter::submitList);
    }
}