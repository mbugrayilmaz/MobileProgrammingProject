package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.DrawerViewModel;

public class EventDrawerListAdapter extends ListAdapter<Drawer, EventDrawerListAdapter.ViewHolder> {
    private DrawerViewModel drawerViewModel;
    private Context context;

    private ActivityResultLauncher<Pair<Drawer, ArrayList<Long>>> pickWearableActivityForResult;
    private ArrayList<Long> pickedList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.eventDrawerRowItemNameTextView);
            linearLayout = view.findViewById(R.id.eventDrawerRowItemLinearLayout);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    }

    public EventDrawerListAdapter(@NonNull DiffUtil.ItemCallback<Drawer> diffCallback, Context context, ArrayList<Long> pickedList) {
        super(diffCallback);

        initialize(context, pickedList);
    }

    @Override
    public EventDrawerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_drawer_row_item, viewGroup, false);

        return new EventDrawerListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventDrawerListAdapter.ViewHolder viewHolder, final int position) {
        Drawer drawer = getItem(position);

        viewHolder.getNameTextView().setText(drawer.name);

        viewHolder.getLinearLayout().setOnClickListener(l -> {
            pickWearableActivityForResult.launch(new Pair<>(drawer, pickedList));
        });


    }

    public static class EventDrawerDiff extends DiffUtil.ItemCallback<Drawer> {

        @Override
        public boolean areItemsTheSame(@NonNull Drawer oldItem, @NonNull Drawer newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Drawer oldItem, @NonNull Drawer newItem) {
            return oldItem.name.equals(newItem.name);
        }
    }

    private void initialize(Context context, ArrayList<Long> pickedList) {
        this.context = context;

        this.pickedList = pickedList;

        drawerViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(DrawerViewModel.class);

        pickWearableActivityForResult = ((ActivityResultCaller) context).registerForActivityResult(new EventWearableResultContract(),
                result -> {
                    if (result != null) {
                        Intent resultIntent = new Intent();

                        resultIntent.putExtra("wearable", result);

                        ((Activity) context).setResult(Activity.RESULT_OK, resultIntent);

                        ((Activity) context).finish();
                    }
                });
    }
}
