package d.a.a.j;

import d.a.a.f;
import java.security.SecureRandom;

public class b implements a {
    public int a(byte[] bArr) {
        byte b2 = bArr[bArr.length - 1] & 255;
        byte b3 = (byte) b2;
        boolean z = (b2 > bArr.length) | (b2 == 0);
        for (int i = 0; i < bArr.length; i++) {
            z |= (bArr.length - i <= b2) & (bArr[i] != b3);
        }
        if (!z) {
            return b2;
        }
        throw new f("pad block corrupted");
    }

    public int a(byte[] bArr, int i) {
        byte length = (byte) (bArr.length - i);
        while (i < bArr.length) {
            bArr[i] = length;
            i++;
        }
        return length;
    }

    public void a(SecureRandom secureRandom) {
    }
}
