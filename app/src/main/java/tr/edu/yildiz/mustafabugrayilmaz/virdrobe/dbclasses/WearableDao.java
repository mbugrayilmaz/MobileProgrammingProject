package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

@Dao
public interface WearableDao {
    @Query("SELECT * FROM wearable WHERE id=:id")
    LiveData<Wearable> getById(long id);

    @Query("SELECT * FROM wearable WHERE drawer_id=:drawerId")
    LiveData<List<Wearable>> getByDrawerId(long drawerId);

    @Insert
    long insert(Wearable wearable);

    @Update
    int update(Wearable wearable);

    @Delete
    int delete(Wearable wearable);

    @Query("SELECT * FROM wearable")
    LiveData<List<Wearable>> getAll();
}