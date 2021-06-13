package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.EventWithWearables;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.EventRepository;

public class EventViewModel extends AndroidViewModel {
    private final EventRepository repository;
    private final LiveData<List<Event>> allEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        repository = new EventRepository(application);
        allEvents = repository.getAllEvents();
    }

    public LiveData<Event> getById(long id) {
        return repository.getById(id);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<EventWithWearables>> getEventsWithWearables() {
        return repository.getEventsWithWearables();
    }

    public LiveData<List<EventWithWearables>> getEventWithWearablesById(long id) {
        return repository.getEventWithWearablesById(id);
    }

    public long insert(Event event) {
        return repository.insert(event);
    }
}
