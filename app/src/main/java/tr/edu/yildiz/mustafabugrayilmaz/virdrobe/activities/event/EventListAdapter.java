package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.event;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Event;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.EventViewModel;

public class EventListAdapter extends ListAdapter<Event, EventListAdapter.ViewHolder> {
    private EventViewModel eventViewModel;
    private Context context;

    private ActivityResultLauncher<Pair<String, Event>> updateLauncher;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView typeTextView;
        private final TextView dateTextView;
        private final TextView locationTextView;
        private final FloatingActionButton wearablesButton;
        private final FloatingActionButton editButton;
        private final FloatingActionButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.eventRowItemNameEditText);
            typeTextView = view.findViewById(R.id.eventRowItemTypeEditText);
            dateTextView = view.findViewById(R.id.eventRowItemDateEditText);
            locationTextView = view.findViewById(R.id.eventRowItemLocationEditText);
            wearablesButton = view.findViewById(R.id.eventRowItemViewWearablesButton);
            editButton = view.findViewById(R.id.eventRowItemEditButton);
            deleteButton = view.findViewById(R.id.eventRowItemDeleteButton);
        }
    }

    public EventListAdapter(@NonNull DiffUtil.ItemCallback<Event> diffCallback, Context context) {
        super(diffCallback);

        initialize(context);
    }

    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_row_item, viewGroup, false);

        return new EventListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventListAdapter.ViewHolder viewHolder, final int position) {
        Event event = getItem(position);

        viewHolder.nameTextView.setText(event.name);
        viewHolder.typeTextView.setText(event.type);
        viewHolder.dateTextView.setText(event.eventDate.format(DateTimeFormatter.ofPattern("d MMM yyyy")));
        viewHolder.locationTextView.setText(event.location);

        viewHolder.editButton.setOnClickListener(l -> {
            updateLauncher.launch(new Pair<>("update", event));
        });

        viewHolder.deleteButton.setOnClickListener(l ->
                new AlertDialog.Builder(context)
                        .setTitle("Delete " + event.name)
                        .setMessage("Are you sure you want to delete this event?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                            eventViewModel.delete(event);

                            Toast.makeText(context, "Event \"" + event.name + "\" deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show());

        viewHolder.wearablesButton.setOnClickListener(l -> {
            Intent intent = new Intent(context, EventViewWearablesActivity.class);

            intent.putExtra("event", event);

            context.startActivity(intent);
        });

    }

    public static class EventDiff extends DiffUtil.ItemCallback<Event> {

        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.name.equals(newItem.name) &&
                    oldItem.type.equals(newItem.type) &&
                    oldItem.eventDate.equals(newItem.eventDate) &&
                    oldItem.location.equals(newItem.location);
        }
    }

    private void initialize(Context context) {
        this.context = context;

        eventViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(EventViewModel.class);

        updateLauncher = ((ActivityResultCaller) context).registerForActivityResult(new EventResultContract(), result -> {
            if (result != null) {
                eventViewModel.update(result);
            }
        });
    }
}
