package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Outfit;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

public class OutfitRepository {
    private OutfitDao outfitDao;
    private LiveData<List<Outfit>> allOutfits;

    public OutfitRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        outfitDao = db.outfitDao();
        allOutfits = outfitDao.getAllOutfits();
    }

    public LiveData<Outfit> getById(long id) {
        return outfitDao.getById(id);
    }

    public LiveData<List<Wearable>> getWearablesByOutfitId(long id) {
        return outfitDao.getWearablesByOutfitId(id);
    }

    public LiveData<List<Outfit>> getAllOutfits() {
        return allOutfits;
    }

    public long insert(Outfit outfit) {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() ->
                outfitDao.insert(outfit), AppDatabase.databaseWriteExecutor);

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void update(Outfit outfit) {
        AppDatabase.databaseWriteExecutor.execute(() -> outfitDao.update(outfit));
    }

    public void delete(Outfit outfit) {
        AppDatabase.databaseWriteExecutor.execute(() -> outfitDao.delete(outfit));
    }
}
