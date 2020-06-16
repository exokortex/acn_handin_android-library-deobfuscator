package d.a.a.k;

import d.a.a.c;

public class a implements c {

    /* renamed from: a  reason: collision with root package name */
    public byte[] f903a;

    public a(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        this.f903a = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, i2);
    }

    public byte[] a() {
        return this.f903a;
    }
}
