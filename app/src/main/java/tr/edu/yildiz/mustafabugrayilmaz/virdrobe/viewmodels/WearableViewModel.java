package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.AppDatabase;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.WearableRepository;

public class WearableViewModel extends AndroidViewModel {
    private final WearableRepository repository;
    private final LiveData<List<Wearable>> allWearables;

    public WearableViewModel(@NonNull Application application) {
        super(application);
        repository = new WearableRepository(application);
        allWearables = repository.getAllWearables();
    }

    public LiveData<List<Wearable>> getByDrawerId(long id) {
        return repository.getByDrawerId(id);
    }

    public LiveData<List<Wearable>> getAllWearables() {
        return allWearables;
    }

    public long insert(Wearable wearable) {
        return repository.insert(wearable);
    }

    public void update(Wearable wearable) {
        repository.update(wearable);
    }

    public void delete(Wearable wearable) {
        repository.delete(wearable);
    }
}
