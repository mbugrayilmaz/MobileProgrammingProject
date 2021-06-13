package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Outfit;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

@Database(entities = {Drawer.class, Wearable.class, Outfit.class, Event.class, EventWearableCrossRef.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String databaseName = "virdrobe.db";

    private static volatile AppDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract DrawerDao drawerDao();

    public abstract WearableDao wearableDao();

    public abstract OutfitDao outfitDao();

    public abstract EventDao eventDao();

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    public static synchronized AppDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AppDatabase.class, databaseName).addCallback(roomDatabaseCallback).build();
                }
            }
        }

        return instance;
    }
}
