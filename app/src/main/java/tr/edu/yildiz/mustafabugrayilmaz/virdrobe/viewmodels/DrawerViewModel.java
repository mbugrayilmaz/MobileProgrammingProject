package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.DrawerRepository;

public class DrawerViewModel extends AndroidViewModel {
    private final DrawerRepository repository;

    private final LiveData<List<Drawer>> allDrawers;

    public DrawerViewModel(@NonNull Application application) {
        super(application);
        repository = new DrawerRepository(application);
        allDrawers = repository.getAllDrawers();
    }

    public LiveData<List<Drawer>> getAllDrawers() {
        return allDrawers;
    }

    public void insert(Drawer drawer) {
        repository.insert(drawer);
    }

    public void update(Drawer drawer) {
        repository.update(drawer);
    }

    public void delete(Drawer drawer) {
        repository.delete(drawer);
    }

    public int count(String name) {
        return repository.count(name);
    }

    public boolean doesExist(Drawer drawer){
        return repository.doesExist(drawer);
    }
}
