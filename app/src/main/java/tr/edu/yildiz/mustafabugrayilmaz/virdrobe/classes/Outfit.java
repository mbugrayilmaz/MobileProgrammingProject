package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Class for combining multiple wearables and representing them as an outfit
 */
@Entity(foreignKeys = {
        @ForeignKey(
                entity = Wearable.class,
                parentColumns = "id",
                childColumns = "head",
                onUpdate = CASCADE,
                onDelete = CASCADE),
        @ForeignKey(entity = Wearable.class,
                parentColumns = "id",
                childColumns = "face",
                onUpdate = CASCADE,
                onDelete = CASCADE),
        @ForeignKey(entity = Wearable.class,
                parentColumns = "id",
                childColumns = "upper_body",
                onUpdate = CASCADE,
                onDelete = CASCADE),
        @ForeignKey(entity = Wearable.class,
                parentColumns = "id",
                childColumns = "lower_body",
                onUpdate = CASCADE,
                onDelete = CASCADE),
        @ForeignKey(entity = Wearable.class,
                parentColumns = "id",
                childColumns = "feet",
                onUpdate = CASCADE,
                onDelete = CASCADE),
}, indices = {
        @Index("head"),
        @Index("face"),
        @Index("upper_body"),
        @Index("lower_body"),
        @Index("feet")})
public class Outfit implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long head;

    public long face;

    @ColumnInfo(name = "upper_body")
    public long upperBody;

    @ColumnInfo(name = "lower_body")
    public long lowerBody;

    public long feet;

    public Outfit(){

    }

    protected Outfit(Parcel in) {
        id = in.readLong();
        head = in.readLong();
        face = in.readLong();
        upperBody = in.readLong();
        lowerBody = in.readLong();
        feet = in.readLong();
    }

    public static final Creator<Outfit> CREATOR = new Creator<Outfit>() {
        @Override
        public Outfit createFromParcel(Parcel in) {
            return new Outfit(in);
        }

        @Override
        public Outfit[] newArray(int size) {
            return new Outfit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(head);
        parcel.writeLong(face);
        parcel.writeLong(upperBody);
        parcel.writeLong(lowerBody);
        parcel.writeLong(feet);
    }
}
