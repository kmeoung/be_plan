package com.naver.temy123.baseproject.base.Widgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes.dex */
public abstract class BaseFragment extends Fragment implements OnHwNetworkCallback {
    @Override // android.support.v4.app.Fragment
    @Nullable
    public abstract View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onFailed(Intent intent, IOException iOException) {
    }

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onNetworkResponsed(Call call, Intent intent, Response response, String str, int i) {
    }

    public void onPageChanged(ViewPager viewPager, boolean z) {
    }

    @Override // com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
    }

    @Deprecated
    public void replaceFragmentFromParent(int i, Fragment fragment) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(i, fragment);
        beginTransaction.commit();
    }

    public void replaceFragment(int i, Fragment fragment, boolean z) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(i, fragment);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.commit();
    }

    public void replaceFragment(int i, Fragment fragment, boolean z, int i2, int i3) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(i2, i3);
        beginTransaction.replace(i, fragment);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.commit();
    }

    public void replaceFragment(int i, Fragment fragment, boolean z, int i2, int i3, int i4, int i5) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(i2, i3, i4, i5);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.replace(i, fragment);
        beginTransaction.commit();
    }

    public void replaceFragmentFromChild(int i, Fragment fragment, boolean z) {
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.replace(i, fragment);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.commit();
    }

    public void replaceFragmentFromChild(int i, Fragment fragment, boolean z, int i2, int i3) {
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(i2, i3);
        beginTransaction.replace(i, fragment);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.commit();
    }

    public void replaceFragmentFromChild(int i, Fragment fragment, boolean z, int i2, int i3, int i4, int i5) {
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(i2, i3, i4, i5);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.replace(i, fragment);
        beginTransaction.commit();
    }

    @Override // android.support.v4.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (getView() != null) {
            onPageChanged(z);
        }
    }

    public void onPageChanged(boolean z) {
        onPageChanged(null, z);
    }
}
