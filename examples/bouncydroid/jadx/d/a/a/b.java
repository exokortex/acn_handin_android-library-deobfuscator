package d.a.a;

import d.a.a.i.a;

public class b {

    /* renamed from: a  reason: collision with root package name */
    public byte[] f895a;

    /* renamed from: b  reason: collision with root package name */
    public int f896b;

    /* renamed from: c  reason: collision with root package name */
    public boolean f897c;

    /* renamed from: d  reason: collision with root package name */
    public a f898d;

    public int a() {
        ((a) this.f898d).a();
        return 16;
    }

    public void b() {
        int i = 0;
        while (true) {
            byte[] bArr = this.f895a;
            if (i < bArr.length) {
                bArr[i] = 0;
                i++;
            } else {
                this.f896b = 0;
                ((a) this.f898d).b();
                return;
            }
        }
    }
}
