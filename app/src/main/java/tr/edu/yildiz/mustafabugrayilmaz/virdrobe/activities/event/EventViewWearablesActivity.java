package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.EventViewModel;

public class EventViewWearablesActivity extends AppCompatActivity {

    private EventViewModel eventViewModel;
    private RecyclerView wearableRecyclerView;
    private EventViewWearablesListAdapter adapter;
    private Event event;
    private final ArrayList<Long> pickedList = new ArrayList<>();
    private final ActivityResultLauncher<ArrayList<Long>> pickDrawer = registerForActivityResult(new EventDrawerResultContract(), result -> {
        if (result != null) {
            pickedList.add(result.id);
            eventViewModel.insertEventWearable(event.id, result.id);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_wearables);

        event = getIntent().getParcelableExtra("event");

        setTitle(event.name);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        wearableRecyclerView = findViewById(R.id.eventViewWearablesRecyclerView);

        wearableRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EventViewWearablesListAdapter(new EventViewWearablesListAdapter.EventWearableDiff(), this, event);

        wearableRecyclerView.setAdapter(adapter);

        eventViewModel.getWearablesByEventId(event.id).observe(this,
                eventWithWearables -> {

                    pickedList.clear();

                    eventWithWearables.forEach(wearable -> pickedList.add(wearable.id));

                    adapter.submitList(eventWithWearables);

                    adapter.setPickedList(pickedList);
                });
    }

    public void onAddEventWearable(View view) {
        pickDrawer.launch(pickedList);
    }
}