package kr.timehub.beplan.utils;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import java.lang.reflect.InvocationTargetException;
import kr.timehub.beplan.entry.database.DbStopWatch;

/* loaded from: classes.dex */
public class BePlanRealmMigration implements RealmMigration {
    public static final int REALM_SCHEMA_VERSION = 1;

    @Override // io.realm.RealmMigration
    public void migrate(DynamicRealm dynamicRealm, long j, long j2) {
        while (j < j2) {
            Class<?> cls = getClass();
            try {
                cls.getDeclaredMethod("runMigrate" + j, DynamicRealm.class).invoke(cls, dynamicRealm);
            } catch (IllegalAccessException e) {
                ThrowableExtension.printStackTrace(e);
            } catch (NoSuchMethodException e2) {
                ThrowableExtension.printStackTrace(e2);
            } catch (InvocationTargetException e3) {
                ThrowableExtension.printStackTrace(e3);
            }
            j++;
        }
    }

    public static void runMigrate0(DynamicRealm dynamicRealm) {
        RealmResults<DynamicRealmObject> findAll = dynamicRealm.where(DbStopWatch.class.getSimpleName()).equalTo("endTime", (Integer) (-1)).equalTo("ell", (Integer) (-1)).findAll();
        for (int i = 0; i < findAll.size(); i++) {
            ((DynamicRealmObject) findAll.get(i)).deleteFromRealm();
        }
    }
}
