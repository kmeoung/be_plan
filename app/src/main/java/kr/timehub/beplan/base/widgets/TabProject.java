package kr.timehub.beplan.base.widgets;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import kr.timehub.beplan.R;

/* loaded from: classes.dex */
public class TabProject {
    public static final int TYPE_LEFT = 0;
    public static final int TYPE_MID = 1;
    public static final int TYPE_RIGHT = 2;
    private Context context;
    private TabLayout mTabLayout;

    public TabProject(Context context, TabLayout tabLayout) {
        this.context = context;
        this.mTabLayout = tabLayout;
    }

    public TabLayout.Tab createTab(int i, String str) {
        TabLayout.Tab newTab = this.mTabLayout.newTab();
        newTab.setCustomView(R.layout.tab_project);
        View view = (View) newTab.getCustomView().getParent();
        switch (i) {
            case 0:
                view.setBackgroundResource(R.drawable.tab_left);
                break;
            case 1:
                view.setBackgroundResource(R.drawable.tab_mid);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.tab_right);
                break;
        }
        newTab.setText(str);
        this.mTabLayout.addTab(newTab);
        return newTab;
    }
}
