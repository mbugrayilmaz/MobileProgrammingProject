package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.cabinet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.List;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Outfit;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Wearable;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.tools.Tools;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.OutfitViewModel;

public class CabinetOutfitListAdapter extends ListAdapter<Outfit, CabinetOutfitListAdapter.ViewHolder> {
    private Context context;

    private OutfitViewModel outfitViewModel;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout linearLayout;
        private final ImageView headImageView;
        private final ImageView faceImageView;
        private final ImageView upperBodyImageView;
        private final ImageView lowerBodyImageView;
        private final ImageView feetImageView;
        private final FloatingActionButton shareButton;
        private final FloatingActionButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.outfitRowItemLinearLayout);
            headImageView = view.findViewById(R.id.outfitRowItemHeadImageView);
            faceImageView = view.findViewById(R.id.outfitRowItemFaceImageView);
            upperBodyImageView = view.findViewById(R.id.outfitRowItemUpperBodyImageView);
            lowerBodyImageView = view.findViewById(R.id.outfitRowItemLowerBodyImageView);
            feetImageView = view.findViewById(R.id.outfitRowItemFeetImageView);
            shareButton = view.findViewById(R.id.outfitRowItemShareButton);
            deleteButton = view.findViewById(R.id.outfitRowItemDeleteButton);
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    }

    public CabinetOutfitListAdapter(@NonNull DiffUtil.ItemCallback<Outfit> diffCallback, Context context) {
        super(diffCallback);

        initialize(context);
    }

    @Override
    public CabinetOutfitListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.outfit_row_item, viewGroup, false);

        return new CabinetOutfitListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CabinetOutfitListAdapter.ViewHolder viewHolder, final int position) {
        Outfit outfit = getItem(position);

        LiveData<List<Wearable>> wearableList = outfitViewModel.getWearablesByOutfitId(outfit.id);

        wearableList.observe((LifecycleOwner) context, l -> {
            String headPath = getUri(l, outfit.head);
            String facePath = getUri(l, outfit.face);
            String upperBodyPath = getUri(l, outfit.upperBody);
            String lowerBodyPath = getUri(l, outfit.lowerBody);
            String feetPath = getUri(l, outfit.feet);

            if (headPath != null &&
                    facePath != null &&
                    upperBodyPath != null &&
                    lowerBodyPath != null &&
                    feetPath != null
            ) {
                Uri headUri = FileProvider.getUriForFile(context,
                        "tr.edu.yildiz.mustafabugrayilmaz.virdrobe.fileprovider",
                        new File(headPath));

                Uri faceUri = FileProvider.getUriForFile(context,
                        "tr.edu.yildiz.mustafabugrayilmaz.virdrobe.fileprovider",
                        new File(facePath));

                Uri upperBodyUri = FileProvider.getUriForFile(context,
                        "tr.edu.yildiz.mustafabugrayilmaz.virdrobe.fileprovider",
                        new File(upperBodyPath));

                Uri lowerBodyUri = FileProvider.getUriForFile(context,
                        "tr.edu.yildiz.mustafabugrayilmaz.virdrobe.fileprovider",
                        new File(lowerBodyPath));

                Uri feetUri = FileProvider.getUriForFile(context,
                        "tr.edu.yildiz.mustafabugrayilmaz.virdrobe.fileprovider",
                        new File(feetPath));

                viewHolder.headImageView.setImageURI(headUri);
                viewHolder.faceImageView.setImageURI(faceUri);
                viewHolder.upperBodyImageView.setImageURI(upperBodyUri);
                viewHolder.lowerBodyImageView.setImageURI(lowerBodyUri);
                viewHolder.feetImageView.setImageURI(feetUri);

                context.grantUriPermission("android.content", headUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.grantUriPermission("android.content", faceUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.grantUriPermission("android.content", upperBodyUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.grantUriPermission("android.content", lowerBodyUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.grantUriPermission("android.content", feetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                viewHolder.shareButton.setOnClickListener(l2 -> {
                    String path = Tools.saveImage(context, Tools.drawOutfit(context,
                            headUri.toString(), faceUri.toString(), upperBodyUri.toString(), lowerBodyUri.toString(), feetUri.toString()),
                            String.valueOf(System.currentTimeMillis() / 1000));

                    Uri outfitUri = FileProvider.getUriForFile(context, "tr.edu.yildiz.mustafabugrayilmaz.virdrobe.fileprovider", new File(path));

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, outfitUri);
                    shareIntent.setType("image/*");

                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    context.startActivity(Intent.createChooser(shareIntent, "Send outfit to"));
                });

                viewHolder.deleteButton.setOnClickListener(l3 ->
                        new AlertDialog.Builder(context)
                                .setTitle("Delete Outfit")
                                .setMessage("Are you sure you want to delete this outfit?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                                    outfitViewModel.delete(outfit);

                                    Toast.makeText(context, "Outfit deleted", Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .show());
            }
        });


    }

    public static class CabinetOutfitDiff extends DiffUtil.ItemCallback<Outfit> {

        @Override
        public boolean areItemsTheSame(@NonNull Outfit oldItem, @NonNull Outfit newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Outfit oldItem, @NonNull Outfit newItem) {
            return oldItem.head == newItem.head &&
                    oldItem.face == newItem.face &&
                    oldItem.upperBody == newItem.upperBody &&
                    oldItem.lowerBody == newItem.lowerBody &&
                    oldItem.feet == newItem.feet;
        }
    }

    private void initialize(Context context) {
        this.context = context;

        outfitViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(OutfitViewModel.class);
    }

    private String getUri(List<Wearable> list, long id) {
        for (Wearable wearable : list) {
            if (wearable.id == id) {
                return wearable.imageUri;
            }
        }

        return null;
    }
}
