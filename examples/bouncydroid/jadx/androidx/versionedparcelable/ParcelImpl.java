package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import b.a.b;
import b.a.c;

public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new a();

    /* renamed from: b  reason: collision with root package name */
    public final c f886b;

    public ParcelImpl(Parcel in) {
        this.f886b = new b(in).h();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        new b(dest).a(this.f886b);
    }

    public static class a implements Parcelable.Creator<ParcelImpl> {
        public ParcelImpl createFromParcel(Parcel in) {
            return new ParcelImpl(in);
        }

        public ParcelImpl[] newArray(int size) {
            return new ParcelImpl[size];
        }
    }
}
