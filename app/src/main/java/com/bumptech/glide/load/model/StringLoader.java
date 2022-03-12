package com.bumptech.glide.load.model;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import java.io.File;
import java.io.InputStream;

/* loaded from: classes.dex */
public class StringLoader<Data> implements ModelLoader<String, Data> {
    private final ModelLoader<Uri, Data> uriLoader;

    public boolean handles(String str) {
        return true;
    }

    public StringLoader(ModelLoader<Uri, Data> modelLoader) {
        this.uriLoader = modelLoader;
    }

    public ModelLoader.LoadData<Data> buildLoadData(String str, int i, int i2, Options options) {
        Uri parseUri = parseUri(str);
        if (parseUri == null) {
            return null;
        }
        return this.uriLoader.buildLoadData(parseUri, i, i2, options);
    }

    @Nullable
    private static Uri parseUri(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith("/")) {
            return toFileUri(str);
        }
        Uri parse = Uri.parse(str);
        return parse.getScheme() == null ? toFileUri(str) : parse;
    }

    private static Uri toFileUri(String str) {
        return Uri.fromFile(new File(str));
    }

    /* loaded from: classes.dex */
    public static class StreamFactory implements ModelLoaderFactory<String, InputStream> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public ModelLoader<String, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new StringLoader(multiModelLoaderFactory.build(Uri.class, InputStream.class));
        }
    }

    /* loaded from: classes.dex */
    public static class FileDescriptorFactory implements ModelLoaderFactory<String, ParcelFileDescriptor> {
        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public void teardown() {
        }

        @Override // com.bumptech.glide.load.model.ModelLoaderFactory
        public ModelLoader<String, ParcelFileDescriptor> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new StringLoader(multiModelLoaderFactory.build(Uri.class, ParcelFileDescriptor.class));
        }
    }
}
