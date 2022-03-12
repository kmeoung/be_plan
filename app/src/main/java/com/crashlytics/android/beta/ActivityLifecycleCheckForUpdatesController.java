package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Activity;
import io.fabric.sdk.android.ActivityLifecycleManager;
import java.util.concurrent.ExecutorService;

@TargetApi(14)
/* loaded from: classes.dex */
public class ActivityLifecycleCheckForUpdatesController extends AbstractCheckForUpdatesController {
    private final ActivityLifecycleManager.Callbacks callbacks = new ActivityLifecycleManager.Callbacks() { // from class: com.crashlytics.android.beta.ActivityLifecycleCheckForUpdatesController.1
        @Override // io.fabric.sdk.android.ActivityLifecycleManager.Callbacks
        public void onActivityStarted(Activity activity) {
            if (ActivityLifecycleCheckForUpdatesController.this.signalExternallyReady()) {
                ActivityLifecycleCheckForUpdatesController.this.executorService.submit(new Runnable() { // from class: com.crashlytics.android.beta.ActivityLifecycleCheckForUpdatesController.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ActivityLifecycleCheckForUpdatesController.this.checkForUpdates();
                    }
                });
            }
        }
    };
    private final ExecutorService executorService;

    @Override // com.crashlytics.android.beta.UpdatesController
    public boolean isActivityLifecycleTriggered() {
        return true;
    }

    public ActivityLifecycleCheckForUpdatesController(ActivityLifecycleManager activityLifecycleManager, ExecutorService executorService) {
        this.executorService = executorService;
        activityLifecycleManager.registerCallbacks(this.callbacks);
    }
}
