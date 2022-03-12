package android.support.v4.app;

import java.util.List;

/* loaded from: classes.dex */
public class FragmentManagerNonConfig {
    private final List<FragmentManagerNonConfig> mChildNonConfigs;
    private final List<Fragment> mFragments;

    public FragmentManagerNonConfig(List<Fragment> list, List<FragmentManagerNonConfig> list2) {
        this.mFragments = list;
        this.mChildNonConfigs = list2;
    }

    public List<Fragment> getFragments() {
        return this.mFragments;
    }

    public List<FragmentManagerNonConfig> getChildNonConfigs() {
        return this.mChildNonConfigs;
    }
}
