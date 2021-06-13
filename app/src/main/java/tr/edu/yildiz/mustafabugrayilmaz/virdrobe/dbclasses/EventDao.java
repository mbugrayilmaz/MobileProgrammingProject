package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event WHERE id=:id")
    LiveData<Event> getById(long id);

    @Query("SELECT * FROM EventWearableCrossRef WHERE event_id=:eventId AND wearable_id=:wearableId")
    LiveData<EventWearableCrossRef> getEventWearableCrossRefByIds(long eventId, long wearableId);

    @Query("SELECT * FROM EventWearableCrossRef WHERE event_id=:eventId")
    LiveData<List<EventWearableCrossRef>> getEventWearableCrossRefByEventId(long eventId);

    @Query("SELECT * FROM event")
    LiveData<List<Event>> getAll();

    @Transaction
    @Query("SELECT * FROM event")
    LiveData<List<EventWithWearables>> getEventsWithWearables();

    @Query("SELECT wearable.* FROM wearable JOIN eventwearablecrossref WHERE wearable.id=wearable_id AND event_id=:id")
    LiveData<List<Wearable>> getWearablesByEventId(long id);

    @Query("INSERT INTO EventWearableCrossRef(event_id, wearable_id) VALUES(:eventId,:wearableId)")
    long insertEventWearable(long eventId, long wearableId);

    @Query("DELETE FROM EventWearableCrossRef WHERE event_id=:eventId AND wearable_id=:wearableId")
    int deleteEventWearable(long eventId, long wearableId);

    @Insert
    long insert(Event event);

    @Update
    int update(Event event);

    @Delete
    int delete(Event event);
}