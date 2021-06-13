package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Outfit;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

@Dao
public interface OutfitDao {
    @Query("SELECT * FROM outfit WHERE id=:id")
    LiveData<Outfit> getById(long id);

    @Query("SELECT * FROM outfit")
    LiveData<List<Outfit>> getAllOutfits();

    @Query("SELECT wearable.* FROM " +
            "wearable JOIN outfit " +
            "WHERE (wearable.id=outfit.head " +
            "OR wearable.id=outfit.face " +
            "OR wearable.id=outfit.upper_body " +
            "OR wearable.id=outfit.lower_body " +
            "OR wearable.id=outfit.feet) " +
            "AND outfit.id=:id")
    LiveData<List<Wearable>> getWearablesByOutfitId(long id);

    @Insert
    long insert(Outfit outfit);

    @Update
    int update(Outfit outfit);

    @Delete
    int delete(Outfit outfit);
}
