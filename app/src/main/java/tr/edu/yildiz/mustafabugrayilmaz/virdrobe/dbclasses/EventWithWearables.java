package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

public class EventWithWearables {
    @Embedded
    public Event event;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(EventWearableCrossRef.class)
    )
    public List<Wearable> wearables;
}
