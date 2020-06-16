package at.acn2020.bouncydroid;

import a.b.e.a.c;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import c.a.a.a;
import java.io.ByteArrayOutputStream;
import java.io.StringBufferInputStream;
import java.security.SecureRandom;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends c {
    public static final char[] p = "0123456789ABCDEF".toCharArray();
    public EditText n;
    public EditText o;

    public static String a(byte[] bytes) {
        char[] hexChars = new char[(bytes.length * 2)];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 255;
            char[] cArr = p;
            hexChars[j * 2] = cArr[v >>> 4];
            hexChars[(j * 2) + 1] = cArr[v & 15];
        }
        return new String(hexChars);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.n = (EditText) findViewById(R.id.plaintext);
        this.o = (EditText) findViewById(R.id.ciphertext);
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        new SecretKeySpec(keyBytes, "AES");
    }

    public void encrypt(View view) {
        String pt = this.n.getText().toString();
        Log.d("test", pt);
        a encrypter = new a();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encrypter.a(new StringBufferInputStream(pt), baos);
        this.o.setText(a(baos.toByteArray()), TextView.BufferType.EDITABLE);
    }
}
