package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(indices = @Index(value = {"drawer_name"}, unique = true))
public class Drawer implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "drawer_name")
    @NonNull
    public String name;

    public Drawer() {
    }

    protected Drawer(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drawer drawer = (Drawer) o;
        return name.equals(drawer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Ignore
    public static final Creator<Drawer> CREATOR = new Creator<Drawer>() {
        @Override
        public Drawer createFromParcel(Parcel in) {
            return new Drawer(in);
        }

        @Override
        public Drawer[] newArray(int size) {
            return new Drawer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
