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

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.EventViewModel;

public class EventViewWearablesListAdapter extends ListAdapter<Wearable, EventViewWearablesListAdapter.ViewHolder> {
    private final Context context;
    private final EventViewModel eventViewModel;

    private final SimpleDateFormat dateFormat;
    private ArrayList<Long> pickedList;

    private Event event;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout linearLayout;
        private final ImageView imageView;
        private final TextView typeTextView;
        private final TextView colorTextView;
        private final TextView patternTextView;
        private final TextView purchaseDateTextView;
        private final TextView priceTextView;
        private final FloatingActionButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.eventViewWearablesRowItemLinearLayout);
            imageView = view.findViewById(R.id.eventViewWearablesRowItemImageView);
            typeTextView = view.findViewById(R.id.eventViewWearablesRowItemTypeTextView);
            colorTextView = view.findViewById(R.id.eventViewWearablesRowItemColorTextView);
            patternTextView = view.findViewById(R.id.eventViewWearablesRowItemPatternTextView);
            purchaseDateTextView = view.findViewById(R.id.eventViewWearablesRowItemPurchaseDateTextView);
            priceTextView = view.findViewById(R.id.eventViewWearablesRowItemPriceTextView);
            deleteButton = view.findViewById(R.id.eventViewWearablesDeleteButton);
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

    public EventViewWearablesListAdapter(@NonNull DiffUtil.ItemCallback<Wearable> diffCallback, Context context, Event event) {
        super(diffCallback);

        this.context = context;

        this.event = event;

        eventViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(EventViewModel.class);

        dateFormat = new SimpleDateFormat("dd MMM yyyy", context.getResources().getConfiguration().getLocales().get(0));
    }

    @NonNull
    @Override
    public EventViewWearablesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_view_wearables_row_item, viewGroup, false);

        return new EventViewWearablesListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewWearablesListAdapter.ViewHolder viewHolder, int position) {
        Wearable wearable = getItem(position);

        viewHolder.getImageView().setImageURI(Uri.fromFile(new File(wearable.imageUri)));
        viewHolder.getTypeTextView().setText(wearable.type);
        viewHolder.getColorTextView().setText(wearable.color);
        viewHolder.getPatternTextView().setText(wearable.pattern);
        viewHolder.getPurchaseDateTextView().setText(wearable.purchaseDate.format(
                DateTimeFormatter.ofPattern("d MMM yyyy",
                        viewHolder.getImageView().getResources().getConfiguration().getLocales().get(0))));
        viewHolder.getPriceTextView().setText(String.valueOf(wearable.price));

        viewHolder.deleteButton.setOnClickListener(l -> {
            eventViewModel.deleteEventWearable(event.id, wearable.id);
            pickedList.remove(wearable.id);
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

    public void setPickedList(ArrayList<Long> list) {
        pickedList = list;
    }
}
