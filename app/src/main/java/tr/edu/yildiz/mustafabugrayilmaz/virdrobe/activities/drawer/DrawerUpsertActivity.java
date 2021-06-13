package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.drawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes.Drawer;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.viewmodels.DrawerViewModel;

public class DrawerUpsertActivity extends AppCompatActivity {
    private String upsertType;

    private EditText nameEditText;
    private Button drawerUpsertButton;

    private Drawer drawer;

    private DrawerViewModel drawerViewModel;

    public void onUpsert(View view) {
        boolean doesExist;

        Intent resultIntent = new Intent();

        drawer.name = nameEditText.getText().toString();

        if (upsertType.equals("insert")) {
            doesExist = drawerViewModel.doesExist(drawer);

            if (doesExist) {
                Toast.makeText(this, "Drawer already exists", Toast.LENGTH_SHORT).show();
            } else {
                resultIntent.putExtra("drawer", drawer);

                setResult(RESULT_OK, resultIntent);

                finish();
            }
        } else {
            resultIntent.putExtra("drawer", drawer);

            setResult(RESULT_OK, resultIntent);

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_upsert);

        nameEditText = findViewById(R.id.drawerUpsertNameEditText);
        drawerUpsertButton = findViewById(R.id.drawerUpsertButton);

        drawerViewModel = new ViewModelProvider(this).get(DrawerViewModel.class);

        upsertType = getIntent().getStringExtra("upsertType");

        drawer = getIntent().getParcelableExtra("drawer");

        if (upsertType.equals("insert")) {
            setTitle("Add Drawer");
            drawerUpsertButton.setText("ADD DRAWER");
        } else {
            setTitle("Edit " + drawer.name);

            nameEditText.setText(drawer.name);

            drawerUpsertButton.setText("SAVE CHANGES");
        }
    }
}