package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;

public class EventResultContract extends ActivityResultContract<Pair<String, Event>, Event> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Pair<String, Event> input) {
        Intent intent = new Intent(context, EventUpsertActivity.class);

        intent.putExtra("upsertType", input.first);
        intent.putExtra("event", input.second);

        return intent;
    }

    @Override
    public Event parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null;
        }

        return intent.getParcelableExtra("event");
    }
}
