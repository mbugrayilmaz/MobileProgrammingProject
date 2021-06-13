package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"event_id", "wearable_id"}, unique = true))
public class EventWearableCrossRef {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "event_id")
    @NonNull
    public long eventId;

    @ColumnInfo(name = "wearable_id")
    @NonNull
    public long wearableId;
}
