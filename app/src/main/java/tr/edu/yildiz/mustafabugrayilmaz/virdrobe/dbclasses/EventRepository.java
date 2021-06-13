package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;

public class EventRepository {
    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAll();
    }

    public LiveData<Event> getById(long id) {
        return eventDao.getById(id);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<EventWithWearables>> getEventsWithWearables() {
        return eventDao.getEventsWithWearables();
    }

    public LiveData<List<EventWithWearables>> getEventWithWearablesById(long id) {
        return eventDao.getEventWithWearablesById(id);
    }

    public long insert(Event event) {
        return eventDao.insert(event);
    }

    public long update(Event event) {
        return eventDao.update(event);
    }

    public long delete(Event event) {
        return eventDao.delete(event);
    }
}
