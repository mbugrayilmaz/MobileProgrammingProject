package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.cabinet.CabinetDrawerListAdapter;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.DrawerViewModel;

public class EventPickDrawerActivity extends AppCompatActivity {

    private RecyclerView drawerRecyclerView;
    private DrawerViewModel drawerViewModel;

    private ArrayList<Long> pickedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pick_drawer);

        setTitle("Drawers");

        pickedList = (ArrayList<Long>) getIntent().getSerializableExtra("pickedList");

        drawerRecyclerView = findViewById(R.id.eventDrawerRecyclerView);

        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CabinetDrawerListAdapter adapter = new CabinetDrawerListAdapter(new CabinetDrawerListAdapter.CabinetDrawerDiff(), this, pickedList);

        drawerRecyclerView.setAdapter(adapter);

        drawerViewModel = new ViewModelProvider(this).get(DrawerViewModel.class);

        drawerViewModel.getAllDrawers().observe(this, adapter::submitList);
    }
}