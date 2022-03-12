package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.util.Base64;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class DataUrlLoader<Data> implements ModelLoader<String, Data> {
    private static final String BASE64_TAG = ";base64";
    private static final String DATA_SCHEME_IMAGE = "data:image";
    private final DataDecoder<Data> dataDecoder;

    /* loaded from: classes.dex */
    public interface DataDecoder<Data> {
        void close(Data data) throws IOException;

        Data decode(String str) throws IllegalArgumentException;

        Class<Data> getDataClass();
    }

    public DataUrlLoader(DataDecoder<Data> dataDecoder) {
        this.dataDecoder = dataDecoder;
    }

    public ModelLoader.LoadData<Data> buildLoadData(String str, int i, int i2, Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(str), new DataUriFetcher(str, this.dataDecoder));
    }

    public boolean handles(String str) {
        return str.startsWith(DATA_SCHEME_IMAGE);
    }

    /* loaded from: classes.dex */
    public static final class DataUriFetcher<Data> implements DataFetcher<Data> {
        private Data data;
        private final String dataUri;
        private final DataDecoder<Data> reader;

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cancel() {
        }

        public DataUriFetcher(String str, DataDecoder<Data> dataDecoder) {
            this.dataUri = str;
            this.reader = dataDecoder;
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void loadData(Priority priority, DataFetcher.DataCallback<? super Data> dataCallback) {
            try {
                this.data = this.reader.decode(this.dataUri);
                dataCallback.onDataReady((Data) this.data);
            } catch (IllegalArgumentException e) {
                dataCallback.onLoadFailed(e);
            }
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        public void cleanup() {
            try {
                this.reader.close(this.data);
            } catch (IOException unused) {
            }
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        @NonNull
        public Class<Data> getDataClass() {
            return this.reader.getDataClass();
        }

        @Override // com.bumptech.glide.load.data.DataFetcher
        @NonNull
        public DataSource getDataSource() {
            return DataSource.LOCAL;
        }
    }

    /* loaded from: classes.dex */
    public static final class StreamFactory implements ModelLoaderFactory<String, InputStream> {
        private final DataDecoder<InputStream> opener = new DataDecoder<InputStream>() { // from class: com.bumptech.glide.load.model.DataUrlLoader.StreamFactory.1
            @Override // com.bumptech.glide.load.model.DataUrlLoader.DataDecoder
            public InputStream decode(String str) {
                if (!str.startsWith(DataUrlLoader.DATA_SCHEME_IMAGE)) {
                    throw new IllegalArgumentException("Not a valid image data URL.");
                }
                int indexOf = str.indexOf(44);
                if (indexOf == -1) {
                    throw new IllegalArgumentException("Missing comma in data URL.");
                } else if (str.substring(0, indexOf).endsWith(DataUrlLoader.BASE64_TAG)) {
                    return new ByteArrayInputStream(Base64.decode(str.substring(indexOf + 1), 0));
                } else {
                    throw new IllegalArgumentException("Not a base64 image data URL.");
                }
            }

            public void close(InputStream inputStream) throws IOException {
                inputStream.close();
            }

            @Override // com.bumptech.glide.load.model.DataUrlLoader.DataDecoder
            public Class<InputStream> getDataClass() {
                return InputStream.class;
            }
        };

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public final void teardown() {
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public final ModelLoader<String, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new DataUrlLoader(this.opener);
        }
    }
}
