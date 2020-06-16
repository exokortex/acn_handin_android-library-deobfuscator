package b.a;

import android.os.Parcelable;
import java.lang.reflect.InvocationTargetException;

public abstract class a {
    public abstract void a();

    public abstract void a(Parcelable parcelable);

    public abstract void a(String str);

    public abstract void a(byte[] bArr);

    public abstract boolean a(int i);

    public abstract a b();

    public abstract void b(int i);

    public abstract void c(int i);

    public abstract byte[] d();

    public abstract int e();

    public abstract <T extends Parcelable> T f();

    public abstract String g();

    public boolean c() {
        return false;
    }

    public void i() {
    }

    public void b(byte[] b2, int fieldId) {
        b(fieldId);
        a(b2);
    }

    public void b(int val, int fieldId) {
        b(fieldId);
        c(val);
    }

    public void b(String val, int fieldId) {
        b(fieldId);
        a(val);
    }

    public void b(Parcelable p, int fieldId) {
        b(fieldId);
        a(p);
    }

    public int a(int def, int fieldId) {
        if (!a(fieldId)) {
            return def;
        }
        return e();
    }

    public String a(String def, int fieldId) {
        if (!a(fieldId)) {
            return def;
        }
        return g();
    }

    public byte[] a(byte[] def, int fieldId) {
        if (!a(fieldId)) {
            return def;
        }
        return d();
    }

    public <T extends Parcelable> T a(T def, int fieldId) {
        if (!a(fieldId)) {
            return def;
        }
        return f();
    }

    public void a(c p) {
        if (p == null) {
            a((String) null);
            return;
        }
        b(p);
        a subParcel = b();
        a(p, subParcel);
        subParcel.a();
    }

    public final void b(c p) {
        try {
            a(a((Class<? extends c>) p.getClass()).getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(p.getClass().getSimpleName() + " does not have a Parcelizer", e);
        }
    }

    public <T extends c> T h() {
        String name = g();
        if (name == null) {
            return null;
        }
        return a(name, b());
    }

    public static <T extends c> T a(String parcelCls, a versionedParcel) {
        Class<a> cls = a.class;
        try {
            return (c) Class.forName(parcelCls, true, cls.getClassLoader()).getDeclaredMethod("read", new Class[]{cls}).invoke((Object) null, new Object[]{versionedParcel});
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    public static <T extends c> void a(T val, a versionedParcel) {
        try {
            c(val).getDeclaredMethod("write", new Class[]{val.getClass(), a.class}).invoke((Object) null, new Object[]{val, versionedParcel});
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    public static <T extends c> Class c(T val) {
        return a((Class<? extends c>) val.getClass());
    }

    public static Class a(Class<? extends c> cls) {
        return Class.forName(String.format("%s.%sParcelizer", new Object[]{cls.getPackage().getName(), cls.getSimpleName()}), false, cls.getClassLoader());
    }
}
