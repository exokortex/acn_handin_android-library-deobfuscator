package b.a;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

public class b extends a {

    /* renamed from: a  reason: collision with root package name */
    public final SparseIntArray f887a;

    /* renamed from: b  reason: collision with root package name */
    public final Parcel f888b;

    /* renamed from: c  reason: collision with root package name */
    public final int f889c;

    /* renamed from: d  reason: collision with root package name */
    public final int f890d;
    public final String e;
    public int f;
    public int g;

    public b(Parcel p) {
        this(p, p.dataPosition(), p.dataSize(), "");
    }

    public b(Parcel p, int offset, int end, String prefix) {
        this.f887a = new SparseIntArray();
        this.f = -1;
        this.g = 0;
        this.f888b = p;
        this.f889c = offset;
        this.f890d = end;
        this.g = offset;
        this.e = prefix;
    }

    public final int d(int fieldId) {
        int fid;
        do {
            int i = this.g;
            if (i >= this.f890d) {
                return -1;
            }
            this.f888b.setDataPosition(i);
            int size = this.f888b.readInt();
            fid = this.f888b.readInt();
            this.g += size;
        } while (fid != fieldId);
        return this.f888b.dataPosition();
    }

    public boolean a(int fieldId) {
        int position = d(fieldId);
        if (position == -1) {
            return false;
        }
        this.f888b.setDataPosition(position);
        return true;
    }

    public void b(int fieldId) {
        a();
        this.f = fieldId;
        this.f887a.put(fieldId, this.f888b.dataPosition());
        c(0);
        c(fieldId);
    }

    public void a() {
        int i = this.f;
        if (i >= 0) {
            int currentFieldPosition = this.f887a.get(i);
            int position = this.f888b.dataPosition();
            this.f888b.setDataPosition(currentFieldPosition);
            this.f888b.writeInt(position - currentFieldPosition);
            this.f888b.setDataPosition(position);
        }
    }

    public a b() {
        Parcel parcel = this.f888b;
        int dataPosition = parcel.dataPosition();
        int i = this.g;
        if (i == this.f889c) {
            i = this.f890d;
        }
        return new b(parcel, dataPosition, i, this.e + "  ");
    }

    public void a(byte[] b2) {
        if (b2 != null) {
            this.f888b.writeInt(b2.length);
            this.f888b.writeByteArray(b2);
            return;
        }
        this.f888b.writeInt(-1);
    }

    public void c(int val) {
        this.f888b.writeInt(val);
    }

    public void a(String val) {
        this.f888b.writeString(val);
    }

    public void a(Parcelable p) {
        this.f888b.writeParcelable(p, 0);
    }

    public int e() {
        return this.f888b.readInt();
    }

    public String g() {
        return this.f888b.readString();
    }

    public byte[] d() {
        int len = this.f888b.readInt();
        if (len < 0) {
            return null;
        }
        byte[] bytes = new byte[len];
        this.f888b.readByteArray(bytes);
        return bytes;
    }

    public <T extends Parcelable> T f() {
        return this.f888b.readParcelable(getClass().getClassLoader());
    }
}
