package c.a.a;

import d.a.a.j.c;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class a {

    /* renamed from: a  reason: collision with root package name */
    public c f891a;

    /* renamed from: b  reason: collision with root package name */
    public c f892b;

    /* renamed from: c  reason: collision with root package name */
    public byte[] f893c;

    /* renamed from: d  reason: collision with root package name */
    public byte[] f894d;
    public byte[] e;

    public a() {
        this.f893c = new byte[16];
        this.f894d = new byte[512];
        this.e = null;
        this.e = "SECRET_1SECRET_2SECRET_3".getBytes();
        a();
    }

    public final void a() {
        c cVar = new c(new d.a.a.i.a());
        this.f891a = cVar;
        cVar.a(true, (d.a.a.c) new d.a.a.k.a(this.e));
        c cVar2 = new c(new d.a.a.i.a());
        this.f892b = cVar2;
        cVar2.a(false, (d.a.a.c) new d.a.a.k.a(this.e));
    }

    public void a(InputStream in, OutputStream out) {
        while (true) {
            try {
                int read = in.read(this.f893c);
                int noBytesRead = read;
                if (read >= 0) {
                    out.write(this.f894d, 0, this.f891a.a(this.f893c, 0, noBytesRead, this.f894d, 0));
                    int i = noBytesRead;
                } else {
                    out.write(this.f894d, 0, this.f891a.a(this.f894d, 0));
                    out.flush();
                    return;
                }
            } catch (IOException e2) {
                System.out.println(e2.getMessage());
                return;
            }
        }
    }
}
