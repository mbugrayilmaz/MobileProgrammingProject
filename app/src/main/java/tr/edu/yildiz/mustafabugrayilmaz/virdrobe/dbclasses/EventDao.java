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

@Dao
public interface EventDao {
    @Query("SELECT * FROM event WHERE id=:id")
    LiveData<Event> getById(long id);

    @Query("SELECT * FROM event")
    LiveData<List<Event>> getAll();

    @Transaction
    @Query("SELECT * FROM event")
    LiveData<List<EventWithWearables>> getEventsWithWearables();

    @Transaction
    @Query("SELECT * FROM event WHERE id=:id")
    LiveData<List<EventWithWearables>> getEventWithWearablesById(long id);

    @Insert
    long insert(Event event);

    @Update
    int update(Event event);

    @Delete
    int delete(Event event);
}