package io.realm;

/* loaded from: classes.dex */
public interface OrderedCollectionChangeSet {
    Range[] getChangeRanges();

    int[] getChanges();

    Range[] getDeletionRanges();

    int[] getDeletions();

    Range[] getInsertionRanges();

    int[] getInsertions();

    /* loaded from: classes.dex */
    public static class Range {
        public final int length;
        public final int startIndex;

        public Range(int i, int i2) {
            this.startIndex = i;
            this.length = i2;
        }
    }
}
