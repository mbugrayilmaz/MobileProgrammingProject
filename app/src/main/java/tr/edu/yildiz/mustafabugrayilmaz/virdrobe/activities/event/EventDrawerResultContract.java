package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;

public class EventDrawerResultContract extends ActivityResultContract<ArrayList<Long>, Wearable> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, ArrayList<Long> input) {
        Intent intent = new Intent(context, EventPickDrawerActivity.class);

        intent.putExtra("pickedList", input);

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
