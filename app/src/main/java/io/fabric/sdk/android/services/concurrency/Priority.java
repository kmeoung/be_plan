package io.fabric.sdk.android.services.concurrency;

/* loaded from: classes.dex */
public enum Priority {
    LOW,
    NORMAL,
    HIGH,
    IMMEDIATE;

    public static <Y> int compareTo(PriorityProvider priorityProvider, Y y) {
        Priority priority;
        if (y instanceof PriorityProvider) {
            priority = ((PriorityProvider) y).getPriority();
        } else {
            priority = NORMAL;
        }
        return priority.ordinal() - priorityProvider.getPriority().ordinal();
    }
}
