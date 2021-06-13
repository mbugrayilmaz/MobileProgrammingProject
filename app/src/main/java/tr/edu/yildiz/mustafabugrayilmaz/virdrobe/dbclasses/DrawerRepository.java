package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.room.InvalidationTracker;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;

public class DrawerRepository {
    private DrawerDao drawerDao;
    private LiveData<List<Drawer>> allDrawers;

    public DrawerRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        drawerDao = db.drawerDao();
        allDrawers = db.drawerDao().getAll();
    }

    public LiveData<List<Drawer>> getAllDrawers() {
        return allDrawers;
    }

    public void insert(Drawer drawer) {
        AppDatabase.databaseWriteExecutor.execute(() -> drawerDao.insert(drawer));
    }

    public void update(Drawer drawer) {
        AppDatabase.databaseWriteExecutor.execute(() -> drawerDao.update(drawer));
    }

    public void delete(Drawer drawer) {
        AppDatabase.databaseWriteExecutor.execute(() -> drawerDao.delete(drawer));
    }

    public int count(String name) {
        Future<Integer> count = AppDatabase.databaseWriteExecutor.submit(() -> drawerDao.count(name));
        try {
            return count.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean doesExist(Drawer drawer) {
        return count(drawer.name) > 0;
    }
}
