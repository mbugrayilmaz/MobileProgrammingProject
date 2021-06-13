package tr.edu.yildiz.mustafabugrayilmaz.virdrobe.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Event implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "event_name")
    @NonNull
    public String name;

    @NonNull
    public String type;

    @ColumnInfo(name = "event_date")
    @NonNull
    public LocalDate eventDate;

    @NonNull
    public String location;

    public Event() {

    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    protected Event(Parcel in) {
        id = in.readLong();
        name = in.readString();
        type = in.readString();
        eventDate = LocalDate.ofEpochDay(in.readLong());
        location = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(type);
        if (eventDate == null) {
            parcel.writeLong(0);
        } else {
            parcel.writeLong(eventDate.toEpochDay());
        }
        parcel.writeString(location);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
