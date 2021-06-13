package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.drawer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.wearable.WearableMainActivity;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.DrawerViewModel;

public class DrawerListAdapter extends ListAdapter<Drawer, DrawerListAdapter.ViewHolder> {
    private DrawerViewModel drawerViewModel;
    private Context context;

    private ActivityResultLauncher<Pair<String, Drawer>> updateActivityForResult;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final Button editButton;
        private final Button deleteButton;
        private final LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.drawerRowItemNameTextView);
            editButton = view.findViewById(R.id.drawerRowItemEditButton);
            deleteButton = view.findViewById(R.id.drawerRowItemDeleteButton);
            linearLayout = view.findViewById(R.id.drawerRowItemLinearLayout);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public Button getEditButton() {
            return editButton;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    }

    public DrawerListAdapter(@NonNull DiffUtil.ItemCallback<Drawer> diffCallback, Context context) {
        super(diffCallback);

        initialize(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.drawer_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Drawer drawer = getItem(position);

        viewHolder.getNameTextView().setText(drawer.name);

        viewHolder.getLinearLayout().setOnClickListener(l -> {
            Intent wearableIntent = new Intent(context, WearableMainActivity.class);

            wearableIntent.putExtra("drawer", drawer);

            context.startActivity(wearableIntent);
        });

        viewHolder.getDeleteButton().setOnClickListener(l ->
                new AlertDialog.Builder(context)
                        .setTitle("Delete " + drawer.name)
                        .setMessage("Are you sure you want to delete this drawer?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                            drawerViewModel.delete(drawer);

                            Toast.makeText(context, "Drawer \"" + drawer.name + "\" deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show());

        viewHolder.getEditButton().setOnClickListener(l ->
                updateActivityForResult.launch(new Pair<>("update", drawer)));
    }

    public static class DrawerDiff extends DiffUtil.ItemCallback<Drawer> {

        @Override
        public boolean areItemsTheSame(@NonNull Drawer oldItem, @NonNull Drawer newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Drawer oldItem, @NonNull Drawer newItem) {
            return oldItem.name.equals(newItem.name);
        }
    }

    private void initialize(Context context) {
        this.context = context;

        drawerViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(DrawerViewModel.class);

        updateActivityForResult = ((ActivityResultCaller) context).registerForActivityResult(new DrawerResultContract(),
                result -> {
                    if (result != null) {
                        drawerViewModel.update(result);
                    }
                });
    }
}
