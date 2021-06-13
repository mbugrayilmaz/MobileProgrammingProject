package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.cabinet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Outfit;

public class CabinetCreateOutfitResultContract extends ActivityResultContract<Void, Outfit> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void input) {
        return new Intent(context, CabinetCreateOutfitActivity.class);
    }

    @Override
    public Outfit parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null;
        }

        return intent.getParcelableExtra("outfit");
    }
}
