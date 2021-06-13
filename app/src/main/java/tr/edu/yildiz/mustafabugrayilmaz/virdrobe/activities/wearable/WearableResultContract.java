package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.wearable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

public class WearableResultContract extends ActivityResultContract<Pair<String, Wearable>, Wearable> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Pair<String, Wearable> input) {
        Intent wearableIntent = new Intent(context, WearableUpsertActivity.class);

        wearableIntent.putExtra("upsertType", input.first);
        wearableIntent.putExtra("wearable", input.second);

        return wearableIntent;
    }

    @Override
    public Wearable parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null;
        }

        return intent.getParcelableExtra("wearable");
    }
}
