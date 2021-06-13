package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.drawer;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.DrawerViewModel;

public class DrawerMainActivity extends AppCompatActivity {

    private RecyclerView drawerRecyclerView;
    private DrawerViewModel drawerViewModel;

    private ActivityResultLauncher<Pair<String, Drawer>> addActivityForResult = registerForActivityResult(new DrawerResultContract(),
            result -> {
                if (result != null) {
                    addDrawer(result);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);

        setTitle("Drawers");

        drawerRecyclerView = findViewById(R.id.drawerRecyclerView);

        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DrawerListAdapter adapter = new DrawerListAdapter(new DrawerListAdapter.DrawerDiff(), this);

        drawerRecyclerView.setAdapter(adapter);

        drawerViewModel = new ViewModelProvider(this).get(DrawerViewModel.class);

        drawerViewModel.getAllDrawers().observe(this, adapter::submitList);
    }

    public void onAddDrawer(View view) {
        addActivityForResult.launch(new Pair<>("insert", new Drawer()));
    }

    private void addDrawer(Drawer drawer) {
        drawerViewModel.insert(drawer);

        Toast.makeText(this, "Drawer \"" + drawer.name + "\" added", Toast.LENGTH_SHORT).show();
    }
}