package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.wearable.WearableListAdapter;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.EventViewModel;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.WearableViewModel;

public class EventMainActivity extends AppCompatActivity {

    private RecyclerView eventRecyclerView;
    private EventViewModel eventViewModel;

    private final ActivityResultLauncher<Pair<String, Event>> addAction = registerForActivityResult(new EventResultContract(), result -> {
        if (result != null) {
            eventViewModel.insert(result);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);

        eventRecyclerView = findViewById(R.id.eventRecyclerView);

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        EventListAdapter adapter = new EventListAdapter(new EventListAdapter.EventDiff(), this);

        eventRecyclerView.setAdapter(adapter);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        eventViewModel.getAllEvents().observe(this, adapter::submitList);
    }

    public void onAddEvent(View view) {
        addAction.launch(new Pair<>("insert", new Event()));
    }
}