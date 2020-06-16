package d.a.a.j;

import d.a.a.a;
import d.a.a.b;
import d.a.a.e;
import d.a.a.g;
import java.security.SecureRandom;

public class c extends b {
    public a e;

    public c(a aVar) {
        this(aVar, new b());
    }

    public c(a aVar, a aVar2) {
        this.f898d = aVar;
        this.e = aVar2;
        ((d.a.a.i.a) aVar).a();
        this.f895a = new byte[16];
        this.f896b = 0;
    }

    public int a(int i) {
        int i2 = i + this.f896b;
        byte[] bArr = this.f895a;
        int length = i2 % bArr.length;
        return length == 0 ? Math.max(0, i2 - bArr.length) : i2 - length;
    }

    public int a(byte[] bArr, int i) {
        int a2;
        int i2;
        ((d.a.a.i.a) this.f898d).a();
        if (this.f897c) {
            if (this.f896b != 16) {
                i2 = 0;
            } else if (i + 32 <= bArr.length) {
                ((d.a.a.i.a) this.f898d).a(this.f895a, 0, bArr, i);
                this.f896b = 0;
                i2 = 16;
            } else {
                b();
                throw new g("output buffer too short");
            }
            ((b) this.e).a(this.f895a, this.f896b);
            ((d.a.a.i.a) this.f898d).a(this.f895a, 0, bArr, i + i2);
            a2 = i2 + 16;
        } else if (this.f896b == 16) {
            a aVar = this.f898d;
            byte[] bArr2 = this.f895a;
            ((d.a.a.i.a) aVar).a(bArr2, 0, bArr2, 0);
            this.f896b = 0;
            try {
                a2 = 16 - ((b) this.e).a(this.f895a);
                System.arraycopy(this.f895a, 0, bArr, i, a2);
            } catch (Throwable th) {
                b();
                throw th;
            }
        } else {
            b();
            throw new e("last block incomplete in decryption");
        }
        b();
        return a2;
    }

    public int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i2 >= 0) {
            a();
            int a2 = a(i2);
            if (a2 <= 0 || a2 + i3 <= bArr2.length) {
                byte[] bArr3 = this.f895a;
                int length = bArr3.length;
                int i4 = this.f896b;
                int i5 = length - i4;
                int i6 = 0;
                if (i2 > i5) {
                    System.arraycopy(bArr, i, bArr3, i4, i5);
                    ((d.a.a.i.a) this.f898d).a(this.f895a, 0, bArr2, i3);
                    this.f896b = 0;
                    i2 -= i5;
                    i += i5;
                    i6 = 16;
                    while (i2 > this.f895a.length) {
                        ((d.a.a.i.a) this.f898d).a(bArr, i, bArr2, i3 + i6);
                        i6 += 16;
                        i2 -= 16;
                        i += 16;
                    }
                }
                System.arraycopy(bArr, i, this.f895a, this.f896b, i2);
                this.f896b += i2;
                return i6;
            }
            throw new g("output buffer too short");
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }

    public void a(boolean z, d.a.a.c cVar) {
        this.f897c = z;
        b();
        if (cVar instanceof d.a.a.k.b) {
            d.a.a.k.b bVar = (d.a.a.k.b) cVar;
            a aVar = this.e;
            bVar.b();
            ((b) aVar).a((SecureRandom) null);
            a aVar2 = this.f898d;
            bVar.a();
            ((d.a.a.i.a) aVar2).a(z, (d.a.a.c) null);
            return;
        }
        ((b) this.e).a((SecureRandom) null);
        ((d.a.a.i.a) this.f898d).a(z, cVar);
    }
}
