package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Outfit;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.EventRepository;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.OutfitRepository;

public class OutfitViewModel extends AndroidViewModel {
    private OutfitRepository repository;
    private LiveData<List<Outfit>> allOutfits;

    public OutfitViewModel(@NonNull Application application) {
        super(application);
        repository = new OutfitRepository(application);
        allOutfits = repository.getAllOutfits();
    }

    public LiveData<Outfit> getById(long id) {
        return repository.getById(id);
    }

    public LiveData<List<Wearable>> getWearablesByOutfitId(long id) {
        return repository.getWearablesByOutfitId(id);
    }

    public LiveData<List<Outfit>> getAllOutfits() {
        return allOutfits;
    }

    public void insert(Outfit outfit) {
        repository.insert(outfit);
    }

    public void update(Outfit outfit) {
        repository.update(outfit);
    }

    public void delete(Outfit outfit) {
        repository.delete(outfit);
    }
}
