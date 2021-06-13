package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.R;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.cabinet.CabinetMainActivity;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.activities.drawer.DrawerMainActivity;
import tr.edu.yildiz.mustafabugrayilmaz.virdrobe.dbclasses.AppDatabase;

public class MainActivity extends AppCompatActivity {

    public AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = AppDatabase.getDatabase(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void manageDrawers(View view) {
        Intent intent = new Intent(this, DrawerMainActivity.class);

        startActivity(intent);
    }

    public void onCabinet(View view) {
        Intent intent = new Intent(this, CabinetMainActivity.class);

        startActivity(intent);
    }
}