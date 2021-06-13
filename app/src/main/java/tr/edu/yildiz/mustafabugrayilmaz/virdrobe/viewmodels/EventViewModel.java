package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.AppDatabase;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.EventWearableCrossRef;
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

    public LiveData<EventWearableCrossRef> getEventWearableCrossRefByIds(long eventId, long wearableId) {
        return repository.getEventWearableCrossRefByIds(eventId, wearableId);
    }

    public LiveData<List<EventWearableCrossRef>> getEventWearableCrossRefByEventId(long eventId) {
        return repository.getEventWearableCrossRefByEventId(eventId);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<EventWithWearables>> getEventsWithWearables() {
        return repository.getEventsWithWearables();
    }

    public LiveData<List<Wearable>> getWearablesByEventId(long id) {
        return repository.getWearablesByEventId(id);
    }

    public void insert(Event event) {
        repository.insert(event);
    }

    public void update(Event event) {
        repository.update(event);
    }

    public void delete(Event event) {
        repository.delete(event);
    }

    public void insertEventWearable(long eventId, long wearableId) {
        repository.insertEventWearable(eventId, wearableId);
    }

    public void deleteEventWearable(long eventId, long wearableId) {
        repository.deleteEventWearable(eventId, wearableId);
    }
}
