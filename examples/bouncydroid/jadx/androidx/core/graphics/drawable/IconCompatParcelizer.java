package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.IconCompat;
import b.a.a;

public class IconCompatParcelizer {
    public static IconCompat read(a parcel) {
        IconCompat obj = new IconCompat();
        obj.f787a = parcel.a(obj.f787a, 1);
        obj.f789c = parcel.a(obj.f789c, 2);
        obj.f790d = parcel.a(obj.f790d, 3);
        obj.e = parcel.a(obj.e, 4);
        obj.f = parcel.a(obj.f, 5);
        obj.g = (ColorStateList) parcel.a(obj.g, 6);
        obj.i = parcel.a(obj.i, 7);
        obj.c();
        return obj;
    }

    public static void write(IconCompat obj, a parcel) {
        parcel.i();
        parcel.c();
        obj.a(false);
        parcel.b(obj.f787a, 1);
        parcel.b(obj.f789c, 2);
        parcel.b(obj.f790d, 3);
        parcel.b(obj.e, 4);
        parcel.b(obj.f, 5);
        parcel.b((Parcelable) obj.g, 6);
        parcel.b(obj.i, 7);
    }
}
