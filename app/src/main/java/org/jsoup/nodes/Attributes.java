package org.jsoup.nodes;

import cz.msebera.android.httpclient.message.TokenParser;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jsoup.SerializationException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Document;

/* loaded from: classes.dex */
public class Attributes implements Iterable<Attribute>, Cloneable {
    private static final String[] Empty = new String[0];
    private static final String EmptyString = "";
    private static final int GrowthFactor = 2;
    private static final int InitialCapacity = 4;
    static final int NotFound = -1;
    protected static final String dataPrefix = "data-";
    private int size = 0;
    String[] keys = Empty;
    String[] vals = Empty;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String checkNotNull(String str) {
        return str == null ? "" : str;
    }

    private void checkCapacity(int i) {
        Validate.isTrue(i >= this.size);
        int length = this.keys.length;
        if (length < i) {
            int i2 = length >= 4 ? this.size * 2 : 4;
            if (i <= i2) {
                i = i2;
            }
            this.keys = copyOf(this.keys, i);
            this.vals = copyOf(this.vals, i);
        }
    }

    private static String[] copyOf(String[] strArr, int i) {
        String[] strArr2 = new String[i];
        System.arraycopy(strArr, 0, strArr2, 0, Math.min(strArr.length, i));
        return strArr2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int indexOfKey(String str) {
        Validate.notNull(str);
        for (int i = 0; i < this.size; i++) {
            if (str.equals(this.keys[i])) {
                return i;
            }
        }
        return -1;
    }

    private int indexOfKeyIgnoreCase(String str) {
        Validate.notNull(str);
        for (int i = 0; i < this.size; i++) {
            if (str.equalsIgnoreCase(this.keys[i])) {
                return i;
            }
        }
        return -1;
    }

    public String get(String str) {
        int indexOfKey = indexOfKey(str);
        return indexOfKey == -1 ? "" : checkNotNull(this.vals[indexOfKey]);
    }

    public String getIgnoreCase(String str) {
        int indexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        return indexOfKeyIgnoreCase == -1 ? "" : checkNotNull(this.vals[indexOfKeyIgnoreCase]);
    }

    private void add(String str, String str2) {
        checkCapacity(this.size + 1);
        this.keys[this.size] = str;
        this.vals[this.size] = str2;
        this.size++;
    }

    public Attributes put(String str, String str2) {
        int indexOfKey = indexOfKey(str);
        if (indexOfKey != -1) {
            this.vals[indexOfKey] = str2;
        } else {
            add(str, str2);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void putIgnoreCase(String str, String str2) {
        int indexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        if (indexOfKeyIgnoreCase != -1) {
            this.vals[indexOfKeyIgnoreCase] = str2;
            if (!this.keys[indexOfKeyIgnoreCase].equals(str)) {
                this.keys[indexOfKeyIgnoreCase] = str;
                return;
            }
            return;
        }
        add(str, str2);
    }

    public Attributes put(String str, boolean z) {
        if (z) {
            putIgnoreCase(str, null);
        } else {
            remove(str);
        }
        return this;
    }

    public Attributes put(Attribute attribute) {
        Validate.notNull(attribute);
        put(attribute.getKey(), attribute.getValue());
        attribute.parent = this;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void remove(int i) {
        Validate.isFalse(i >= this.size);
        int i2 = (this.size - i) - 1;
        if (i2 > 0) {
            int i3 = i + 1;
            System.arraycopy(this.keys, i3, this.keys, i, i2);
            System.arraycopy(this.vals, i3, this.vals, i, i2);
        }
        this.size--;
        this.keys[this.size] = null;
        this.vals[this.size] = null;
    }

    public void remove(String str) {
        int indexOfKey = indexOfKey(str);
        if (indexOfKey != -1) {
            remove(indexOfKey);
        }
    }

    public void removeIgnoreCase(String str) {
        int indexOfKeyIgnoreCase = indexOfKeyIgnoreCase(str);
        if (indexOfKeyIgnoreCase != -1) {
            remove(indexOfKeyIgnoreCase);
        }
    }

    public boolean hasKey(String str) {
        return indexOfKey(str) != -1;
    }

    public boolean hasKeyIgnoreCase(String str) {
        return indexOfKeyIgnoreCase(str) != -1;
    }

    public int size() {
        return this.size;
    }

    public void addAll(Attributes attributes) {
        if (attributes.size() != 0) {
            checkCapacity(this.size + attributes.size);
            Iterator<Attribute> it = attributes.iterator();
            while (it.hasNext()) {
                put(it.next());
            }
        }
    }

    @Override // java.lang.Iterable
    public Iterator<Attribute> iterator() {
        return new Iterator<Attribute>() { // from class: org.jsoup.nodes.Attributes.1
            int i = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.i < Attributes.this.size;
            }

            @Override // java.util.Iterator
            public Attribute next() {
                Attribute attribute = new Attribute(Attributes.this.keys[this.i], Attributes.this.vals[this.i], Attributes.this);
                this.i++;
                return attribute;
            }

            @Override // java.util.Iterator
            public void remove() {
                Attributes attributes = Attributes.this;
                int i = this.i - 1;
                this.i = i;
                attributes.remove(i);
            }
        };
    }

    public List<Attribute> asList() {
        Object obj;
        ArrayList arrayList = new ArrayList(this.size);
        for (int i = 0; i < this.size; i++) {
            if (this.vals[i] == null) {
                obj = new BooleanAttribute(this.keys[i]);
            } else {
                obj = new Attribute(this.keys[i], this.vals[i], this);
            }
            arrayList.add(obj);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Map<String, String> dataset() {
        return new Dataset();
    }

    public String html() {
        StringBuilder sb = new StringBuilder();
        try {
            html(sb, new Document("").outputSettings());
            return sb.toString();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void html(Appendable appendable, Document.OutputSettings outputSettings) throws IOException {
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            String str = this.keys[i2];
            String str2 = this.vals[i2];
            appendable.append(TokenParser.SP).append(str);
            if (!Attribute.shouldCollapseAttribute(str, str2, outputSettings)) {
                appendable.append("=\"");
                if (str2 == null) {
                    str2 = "";
                }
                Entities.escape(appendable, str2, outputSettings, true, false, false);
                appendable.append(TokenParser.DQUOTE);
            }
        }
    }

    public String toString() {
        return html();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attributes attributes = (Attributes) obj;
        if (this.size == attributes.size && Arrays.equals(this.keys, attributes.keys)) {
            return Arrays.equals(this.vals, attributes.vals);
        }
        return false;
    }

    public int hashCode() {
        return (((this.size * 31) + Arrays.hashCode(this.keys)) * 31) + Arrays.hashCode(this.vals);
    }

    public Attributes clone() {
        try {
            Attributes attributes = (Attributes) super.clone();
            attributes.size = this.size;
            this.keys = copyOf(this.keys, this.size);
            this.vals = copyOf(this.vals, this.size);
            return attributes;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void normalize() {
        for (int i = 0; i < this.size; i++) {
            this.keys[i] = Normalizer.lowerCase(this.keys[i]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Dataset extends AbstractMap<String, String> {
        private final Attributes attributes;

        private Dataset(Attributes attributes) {
            this.attributes = attributes;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<String, String>> entrySet() {
            return new EntrySet();
        }

        public String put(String str, String str2) {
            String dataKey = Attributes.dataKey(str);
            String str3 = this.attributes.hasKey(dataKey) ? this.attributes.get(dataKey) : null;
            this.attributes.put(dataKey, str2);
            return str3;
        }

        /* loaded from: classes.dex */
        private class EntrySet extends AbstractSet<Map.Entry<String, String>> {
            private EntrySet() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Map.Entry<String, String>> iterator() {
                return new DatasetIterator();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                int i = 0;
                while (new DatasetIterator().hasNext()) {
                    i++;
                }
                return i;
            }
        }

        /* loaded from: classes.dex */
        private class DatasetIterator implements Iterator<Map.Entry<String, String>> {
            private Attribute attr;
            private Iterator<Attribute> attrIter;

            private DatasetIterator() {
                this.attrIter = Dataset.this.attributes.iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                while (this.attrIter.hasNext()) {
                    this.attr = this.attrIter.next();
                    if (this.attr.isDataAttribute()) {
                        return true;
                    }
                }
                return false;
            }

            @Override // java.util.Iterator
            public Map.Entry<String, String> next() {
                return new Attribute(this.attr.getKey().substring(Attributes.dataPrefix.length()), this.attr.getValue());
            }

            @Override // java.util.Iterator
            public void remove() {
                Dataset.this.attributes.remove(this.attr.getKey());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String dataKey(String str) {
        return dataPrefix + str;
    }
}
