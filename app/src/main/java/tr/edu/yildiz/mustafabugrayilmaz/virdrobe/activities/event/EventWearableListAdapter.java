package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.WearableViewModel;

public class EventWearableListAdapter extends ListAdapter<Wearable, EventWearableListAdapter.ViewHolder> {
    private final Context context;
    private final WearableViewModel wearableViewModel;

    private final SimpleDateFormat dateFormat;
    private ArrayList<Long> pickedList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout linearLayout;
        private final ImageView imageView;
        private final TextView typeTextView;
        private final TextView colorTextView;
        private final TextView patternTextView;
        private final TextView purchaseDateTextView;
        private final TextView priceTextView;

        public ViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.eventWearableRowItemLinearLayout);
            imageView = view.findViewById(R.id.eventWearableRowItemImageView);
            typeTextView = view.findViewById(R.id.eventWearableRowItemTypeTextView);
            colorTextView = view.findViewById(R.id.eventWearableRowItemColorTextView);
            patternTextView = view.findViewById(R.id.eventWearableRowItemPatternTextView);
            purchaseDateTextView = view.findViewById(R.id.eventWearableRowItemPurchaseDateTextView);
            priceTextView = view.findViewById(R.id.eventWearableRowItemPriceTextView);
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

    }

    public EventWearableListAdapter(@NonNull DiffUtil.ItemCallback<Wearable> diffCallback, Context context, ArrayList<Long> pickedList) {
        super(diffCallback);

        this.context = context;

        this.pickedList = pickedList;

        wearableViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(WearableViewModel.class);

        dateFormat = new SimpleDateFormat("dd MMM yyyy", context.getResources().getConfiguration().getLocales().get(0));
    }

    @NonNull
    @Override
    public EventWearableListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_wearable_row_item, viewGroup, false);

        return new EventWearableListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventWearableListAdapter.ViewHolder viewHolder, int position) {
        Wearable wearable = getItem(position);

        viewHolder.getImageView().setImageURI(Uri.fromFile(new File(wearable.imageUri)));
        viewHolder.getTypeTextView().setText(wearable.type);
        viewHolder.getColorTextView().setText(wearable.color);
        viewHolder.getPatternTextView().setText(wearable.pattern);
        viewHolder.getPurchaseDateTextView().setText(wearable.purchaseDate.format(
                DateTimeFormatter.ofPattern("d MMM yyyy",
                        viewHolder.getImageView().getResources().getConfiguration().getLocales().get(0))));
        viewHolder.getPriceTextView().setText(String.valueOf(wearable.price));

        viewHolder.getLinearLayout().setOnClickListener(l -> {
            if (!pickedList.contains(wearable.id)) {
                Intent resultIntent = new Intent();

                resultIntent.putExtra("wearable", wearable);

                ((Activity) context).setResult(Activity.RESULT_OK, resultIntent);

                ((Activity) context).finish();
            } else {
                Toast.makeText(context, "You already selected this", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class EventWearableDiff extends DiffUtil.ItemCallback<Wearable> {

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
