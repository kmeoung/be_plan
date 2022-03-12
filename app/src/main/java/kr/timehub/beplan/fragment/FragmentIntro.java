package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import com.naver.temy123.baseproject.base.Http.HWOkHttpClient;
import java.util.Iterator;
import java.util.List;
import kr.timehub.beplan.FnMarketUpdate.DialogMarketVersionConfirm;
import kr.timehub.beplan.FnMarketUpdate.MarketUpdate;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.activity.ActivityPermission;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.utils.Comm_Params;
import kr.timehub.beplan.utils.Comm_Prefs;
import kr.timehub.beplan.utils.JLog;
import kr.timehub.beplan.utils.JPref;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Cookie;

/* loaded from: classes.dex */
public class FragmentIntro extends BaseFragment implements MarketUpdate.MarketUpdateTaskCallback {
    private static final int DELAYED = 1500;
    private DialogMarketVersionConfirm dialog;
    boolean isVersionCheckRunning = false;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.v_status)
    View mVStatus;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_intro, viewGroup, false);
        this.dialog = new DialogMarketVersionConfirm(getContext());
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setCancelable(false);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 0) {
            return;
        }
        if (i2 == -1) {
            startLoginPage();
        } else {
            getActivity().finish();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.isVersionCheckRunning || getActivity() == null || !Utils.isConnectedInternet(getActivity())) {
            nextStep();
            return;
        }
        this.isVersionCheckRunning = true;
        new MarketUpdate(getActivity(), this).execute(new Void[0]);
    }

    public void nextStep() {
        new Handler().postDelayed(new Runnable(this) { // from class: kr.timehub.beplan.fragment.FragmentIntro$$Lambda$0
            private final FragmentIntro arg$1;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.arg$1 = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.arg$1.lambda$nextStep$0$FragmentIntro();
            }
        }, 1500L);
    }

    public final /* synthetic */ void lambda$nextStep$0$FragmentIntro() {
        List<Cookie> cookiesList = HWOkHttpClient.getInstance(getContext()).getCookiesList();
        Comm_Prefs instance = Comm_Prefs.getInstance(getContext());
        if (!isGrantedPermissions()) {
            startActivityForResult(new Intent(getContext(), ActivityPermission.class), 0);
        } else if (cookiesList.size() > 1) {
            Iterator<Cookie> it = cookiesList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Cookie next = it.next();
                if (!next.name().contains("ASPXAUTH")) {
                    instance.setUserId(next.name());
                    break;
                }
            }
            getActivity().startActivity(new Intent(getActivity(), ActivityMain.class));
            getActivity().finish();
        } else {
            startLoginPage();
        }
    }

    private boolean isGrantedPermissions() {
        boolean z = false;
        for (String str : Comm_Params.APP_PERMISSIONS) {
            z = ActivityCompat.checkSelfPermission(getContext(), str) == 0;
            if (!z) {
                break;
            }
        }
        return (!z || Build.VERSION.SDK_INT < 23) ? z : Settings.canDrawOverlays(getContext());
    }

    private void startLoginPage() {
        replaceFragment(R.id.base_container, new FragmentRemakeLogin(), false, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
    }

    @Override // kr.timehub.beplan.FnMarketUpdate.MarketUpdate.MarketUpdateTaskCallback
    public void marketUpdate(int i) {
        this.isVersionCheckRunning = false;
        switch (i) {
            case 0:
                JLog.i("MARKET_UPDATE_NOTHING");
                nextStep();
                return;
            case 1:
                JLog.i("MARKET_UPDATE_REQUIRED");
                this.dialog.setOnDialogCallback(new DialogMarketVersionConfirm.DialogCallback() { // from class: kr.timehub.beplan.fragment.FragmentIntro.1
                    @Override // kr.timehub.beplan.FnMarketUpdate.DialogMarketVersionConfirm.DialogCallback
                    public void dialogCallbackOK() {
                        Utils.startGoogleMarket();
                    }

                    @Override // kr.timehub.beplan.FnMarketUpdate.DialogMarketVersionConfirm.DialogCallback
                    public void dialogCallbackCANCEL() {
                        if (FragmentIntro.this.getActivity() != null) {
                            FragmentIntro.this.getActivity().finish();
                        }
                    }
                });
                this.dialog.setType(i);
                Utils.displayDialog(this.dialog, getActivity());
                return;
            case 2:
                JLog.i("MARKET_UPDATE_OPTIONAL");
                this.dialog.setOnDialogCallback(new DialogMarketVersionConfirm.DialogCallback() { // from class: kr.timehub.beplan.fragment.FragmentIntro.2
                    @Override // kr.timehub.beplan.FnMarketUpdate.DialogMarketVersionConfirm.DialogCallback
                    public void dialogCallbackOK() {
                        Utils.startGoogleMarket();
                    }

                    @Override // kr.timehub.beplan.FnMarketUpdate.DialogMarketVersionConfirm.DialogCallback
                    public void dialogCallbackCANCEL() {
                        FragmentIntro.this.nextStep();
                    }
                });
                this.dialog.setType(i);
                if (JPref.isMarketUpdateDialogShow()) {
                    Utils.displayDialog(this.dialog, getActivity());
                    return;
                } else {
                    nextStep();
                    return;
                }
            default:
                return;
        }
    }
}
