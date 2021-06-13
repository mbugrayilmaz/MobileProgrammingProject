package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.drawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;

public class DrawerResultContract extends ActivityResultContract<Pair<String, Drawer>, Drawer> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Pair<String, Drawer> input) {
        Intent drawerIntent = new Intent(context, DrawerUpsertActivity.class);

        drawerIntent.putExtra("upsertType", input.first);
        drawerIntent.putExtra("drawer", input.second);

        return drawerIntent;
    }

    @Override
    public Drawer parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null;
        }

        return intent.getParcelableExtra("drawer");
    }
}
