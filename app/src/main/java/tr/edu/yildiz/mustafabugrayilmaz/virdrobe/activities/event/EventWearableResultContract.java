package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import java.util.ArrayList;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

public class EventWearableResultContract extends ActivityResultContract<Pair<Drawer, ArrayList<Long>>, Wearable> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Pair<Drawer, ArrayList<Long>> input) {
        Intent intent = new Intent(context, EventPickWearableActivity.class);

        intent.putExtra("drawer", input.first);

        intent.putExtra("pickedList", input.second);

        return intent;
    }

    @Override
    public Wearable parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null;
        }

        return intent.getParcelableExtra("wearable");
    }
}
