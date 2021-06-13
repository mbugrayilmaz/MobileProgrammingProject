package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;

import java.util.List;

@Dao
public interface DrawerDao {
    @Query("SELECT * FROM drawer")
    LiveData<List<Drawer>> getAll();

    @Query("DELETE FROM drawer WHERE drawer_name=:name")
    int deleteDrawer(String name);

    @Insert
    long insert(Drawer drawer);

    @Delete
    int delete(Drawer drawer);

    @Update
    int update(Drawer drawer);

    @Query("SELECT COUNT(*) FROM drawer WHERE drawer_name=:name")
    int count(String name);
}
