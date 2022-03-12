package kr.timehub.beplan;

import kr.timehub.beplan.activity.ActivityUsingNewProject;
import kr.timehub.beplan.fragment.FragmentNewTask;
import kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionCategoryDetail;
import kr.timehub.beplan.fragment.SideForm.mySubscription.FragmentMySubscriptionProjectDetail;

/* loaded from: classes.dex */
public class DeveloperNavigation {
    static {
        new FragmentNewTask();
        new ActivityUsingNewProject();
        new FragmentMySubscriptionProjectDetail();
        new FragmentMySubscriptionCategoryDetail();
    }
}
