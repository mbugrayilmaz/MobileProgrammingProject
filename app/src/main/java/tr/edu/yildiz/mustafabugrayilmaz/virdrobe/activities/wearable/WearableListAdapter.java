package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.wearable;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.WearableViewModel;

public class WearableListAdapter extends ListAdapter<Wearable, WearableListAdapter.ViewHolder> {

    private final Context context;
    private final WearableViewModel wearableViewModel;
    private final ActivityResultLauncher<Pair<String, Wearable>> activityForResult;

    private final SimpleDateFormat dateFormat;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout linearLayout;
        private final ImageView imageView;
        private final TextView typeTextView;
        private final TextView colorTextView;
        private final TextView patternTextView;
        private final TextView purchaseDateTextView;
        private final TextView priceTextView;
        private final Button deleteButton;

        public ViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.wearableRowItemLinearLayout);
            imageView = view.findViewById(R.id.wearableRowItemImageView);
            typeTextView = view.findViewById(R.id.wearableRowItemTypeTextView);
            colorTextView = view.findViewById(R.id.wearableRowItemColorTextView);
            patternTextView = view.findViewById(R.id.wearableRowItemPatternTextView);
            purchaseDateTextView = view.findViewById(R.id.wearableRowItemPurchaseDateTextView);
            priceTextView = view.findViewById(R.id.wearableRowItemPriceTextView);
            deleteButton = view.findViewById(R.id.wearableRowItemDeleteButton);
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTypeTextView() {
            return typeTextView;
        }

        public TextView getColorTextView() {
            return colorTextView;
        }

        public TextView getPatternTextView() {
            return patternTextView;
        }

        public TextView getPurchaseDateTextView() {
            return purchaseDateTextView;
        }

        public TextView getPriceTextView() {
            return priceTextView;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public WearableListAdapter(@NonNull DiffUtil.ItemCallback<Wearable> diffCallback, Context context) {
        super(diffCallback);

        this.context = context;

        wearableViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(WearableViewModel.class);

        activityForResult = ((ActivityResultCaller) context).registerForActivityResult(new WearableResultContract(),
                result -> {
                    if (result != null) {
                        wearableViewModel.update(result);
                    }
                });

        dateFormat = new SimpleDateFormat("dd MMM yyyy", context.getResources().getConfiguration().getLocales().get(0));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.wearable_row_item, viewGroup, false);

        return new WearableListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WearableListAdapter.ViewHolder viewHolder, int position) {
        Wearable wearable = getItem(position);

        viewHolder.getImageView().setImageURI(Uri.fromFile(new File(wearable.imageUri)));
        viewHolder.getTypeTextView().setText(wearable.type);
        viewHolder.getColorTextView().setText(wearable.color);
        viewHolder.getPatternTextView().setText(wearable.pattern);
        viewHolder.getPurchaseDateTextView().setText(wearable.purchaseDate.format(
                DateTimeFormatter.ofPattern("d MMM yyyy",
                        viewHolder.getImageView().getResources().getConfiguration().getLocales().get(0))));
        viewHolder.getPriceTextView().setText(String.valueOf(wearable.price));

        viewHolder.getDeleteButton().setOnClickListener(l ->
                new AlertDialog.Builder(context)
                        .setTitle("Delete Wearable")
                        .setMessage("Are you sure you want to delete this wearable?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                            wearableViewModel.delete(wearable);

                            Toast.makeText(context, "Wearable deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show());

        viewHolder.getLinearLayout().setOnClickListener(l ->
                activityForResult.launch(new Pair<>("update", wearable)));
    }

    public static class WearableDiff extends DiffUtil.ItemCallback<Wearable> {

        @Override
        public boolean areItemsTheSame(@NonNull Wearable oldItem, @NonNull Wearable newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Wearable oldItem, @NonNull Wearable newItem) {
            return oldItem.equals(newItem);
        }
    }
}
