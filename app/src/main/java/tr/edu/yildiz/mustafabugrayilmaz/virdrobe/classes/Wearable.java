package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Drawer.class,
        parentColumns = "id",
        childColumns = "drawer_id",
        onDelete = CASCADE,
        onUpdate = CASCADE
), indices = @Index("drawer_id"))
public class Wearable implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String type;

    @NonNull
    public String color;

    @NonNull
    public String pattern;

    @ColumnInfo(name = "purchase_date")
    @NonNull
    public LocalDate purchaseDate;

    @NonNull
    public double price;

    @ColumnInfo(name = "image_uri")
    @NonNull
    public String imageUri;

    @ColumnInfo(name = "drawer_id")
    public long drawerId;

    @Ignore
    public boolean refreshImage = false;

    public Wearable() {
    }

    protected Wearable(Parcel in) {
        id = in.readLong();
        type = in.readString();
        color = in.readString();
        pattern = in.readString();
        purchaseDate = LocalDate.ofEpochDay(in.readLong());
        price = in.readDouble();
        imageUri = in.readString();
        drawerId = in.readLong();
        refreshImage = in.readInt() == 1;
    }

    public static final Creator<Wearable> CREATOR = new Creator<Wearable>() {
        @Override
        public Wearable createFromParcel(Parcel in) {
            return new Wearable(in);
        }

        @Override
        public Wearable[] newArray(int size) {
            return new Wearable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(type);
        parcel.writeString(color);
        parcel.writeString(pattern);
        if (purchaseDate == null) {
            parcel.writeLong(0);
        } else {
            parcel.writeLong(purchaseDate.toEpochDay());
        }
        parcel.writeDouble(price);
        parcel.writeString(imageUri);
        parcel.writeLong(drawerId);
        parcel.writeInt(refreshImage ? 1 : 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wearable wearable = (Wearable) o;
        return Double.compare(wearable.price, price) == 0 &&
                drawerId == wearable.drawerId &&
                type.equals(wearable.type) &&
                color.equals(wearable.color) &&
                pattern.equals(wearable.pattern) &&
                purchaseDate.equals(wearable.purchaseDate) &&
                imageUri.equals(wearable.imageUri) &&
                refreshImage == wearable.refreshImage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color, pattern, purchaseDate, price, imageUri, drawerId, refreshImage);
    }

    @Override
    public String toString() {
        return "Wearable{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", pattern='" + pattern + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", price=" + price +
                ", imageUri='" + imageUri + '\'' +
                ", drawerId=" + drawerId +
                ", refreshImage=" + refreshImage +
                '}';
    }
}
