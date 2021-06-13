package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

public class WearableRepository {
    private WearableDao wearableDao;
    private LiveData<List<Wearable>> allWearables;

    public WearableRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        wearableDao = db.wearableDao();
        allWearables = wearableDao.getAll();
    }

    public LiveData<List<Wearable>> getAllWearables() {
        return allWearables;
    }

    public LiveData<List<Wearable>> getByDrawerId(long id) {
        return wearableDao.getByDrawerId(id);
    }

    public long insert(Wearable wearable) {
        Future<Long> future = AppDatabase.databaseWriteExecutor.submit(() -> wearableDao.insert(wearable));

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void update(Wearable wearable) {
        AppDatabase.databaseWriteExecutor.execute(() -> wearableDao.update(wearable));
    }

    public void delete(Wearable wearable) {
        AppDatabase.databaseWriteExecutor.execute(() -> wearableDao.delete(wearable));
    }
}
