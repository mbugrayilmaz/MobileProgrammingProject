package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

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

    public LiveData<EventWearableCrossRef> getEventWearableCrossRefByIds(long eventId, long wearableId) {
        return eventDao.getEventWearableCrossRefByIds(eventId, wearableId);
    }

    public LiveData<List<EventWearableCrossRef>> getEventWearableCrossRefByEventId(long eventId) {
        return eventDao.getEventWearableCrossRefByEventId(eventId);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<EventWithWearables>> getEventsWithWearables() {
        return eventDao.getEventsWithWearables();
    }

    public LiveData<List<Wearable>> getWearablesByEventId(long id) {
        return eventDao.getWearablesByEventId(id);
    }

    public void insert(Event event) {
        AppDatabase.databaseWriteExecutor.execute(() -> eventDao.insert(event));
    }

    public void update(Event event) {
        AppDatabase.databaseWriteExecutor.execute(() -> eventDao.update(event));
    }

    public void delete(Event event) {
        AppDatabase.databaseWriteExecutor.execute(() -> eventDao.delete(event));
    }

    public void insertEventWearable(long eventId, long wearableId) {
        AppDatabase.databaseWriteExecutor.execute(() -> eventDao.insertEventWearable(eventId, wearableId));
    }

    public void deleteEventWearable(long eventId, long wearableId) {
        AppDatabase.databaseWriteExecutor.execute(() -> eventDao.deleteEventWearable(eventId, wearableId));
    }
}
