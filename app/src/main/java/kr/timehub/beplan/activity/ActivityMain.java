package kr.timehub.beplan.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseActivity;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.entry.BeanStopWatch;
import kr.timehub.beplan.entry.common.BeanDrawerList;
import kr.timehub.beplan.entry.common.BeanDrawerTitle;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.fragment.FragmentAlerts;
import kr.timehub.beplan.fragment.FragmentMyInfo;
import kr.timehub.beplan.fragment.FragmentSetting;
import kr.timehub.beplan.fragment.FragmentTeamManagement;
import kr.timehub.beplan.fragment.SideForm.common.FragmentForm;
import kr.timehub.beplan.fragment.main.FragmentDrawer;
import kr.timehub.beplan.fragment.main.FragmentDrawerLayout;
import kr.timehub.beplan.fragment.main.FragmentRemakeMain;
import kr.timehub.beplan.fragment.projects.FragmentProject;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.service.ServiceClock;
import kr.timehub.beplan.utils.Comm_Params;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class ActivityMain extends BaseActivity implements BaseMainToolBar.OnToolbarClickListener, FragmentDrawerLayout.OnDrawerClickListener {
    public static final int REQ_MAIN = 99999;
    public static final int REQ_PROJECT_ACCEPT = 99998;
    public static final int REQ_PROJECT_DELETE = 99996;
    public static final int REQ_PROJECT_REFUSE = 99997;
    @BindView(R.id.base_container)
    FrameLayout mBaseContainer;
    @BindView(R.id.base_toolbar)
    BaseMainToolBar mBaseToolbar;
    IOnMainDataSendListener mIODrawerListener;
    IOnMainDataSendListener mIOMainListener;
    @BindView(R.id.layout_drawer)
    DrawerLayout mLayoutDrawer;
    @BindView(R.id.layout_slide_menu)
    FrameLayout mLayoutSlideMenu;
    private BaseMainToolBar.OnToolbarClickListener mOnToolbarListener;
    private ServiceClock mServiceClock;
    @BindView(R.id.v_state)
    View mVState;
    private boolean isBind = false;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: kr.timehub.beplan.activity.ActivityMain.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ServiceClock.LocalBinder localBinder = (ServiceClock.LocalBinder) iBinder;
            localBinder.setTimer(null);
            ActivityMain.this.mServiceClock = localBinder.getService();
            ActivityMain.this.isBind = true;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            ActivityMain.this.isBind = false;
        }
    };
    private BroadcastReceiver mReceiverDrawer = new BroadcastReceiver() { // from class: kr.timehub.beplan.activity.ActivityMain.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            String action = intent.getAction();
            int hashCode = action.hashCode();
            if (hashCode == -1641004708) {
                if (action.equals(Comm_Params.ACTION_MAIN_DRAWER_CLOSE)) {
                    c = 0;
                }
                c = 65535;
            } else if (hashCode != -1235250530) {
                if (hashCode == -1160953274 && action.equals(Comm_Params.ACTION_MAIN_DRAWER_OPEN)) {
                    c = 1;
                }
                c = 65535;
            } else {
                if (action.equals(Comm_Params.PUSH_RECEIVE)) {
                    c = 2;
                }
                c = 65535;
            }
            switch (c) {
                case 0:
                    ActivityMain.this.mLayoutDrawer.closeDrawers();
                    return;
                case 1:
                    ActivityMain.this.mLayoutDrawer.openDrawer(3);
                    return;
                case 2:
                    String stringExtra = intent.getStringExtra("seq");
                    if (stringExtra != null) {
                        ActivityMain.this.replaceFragment(R.id.base_container, FragmentProject.newInstance(Integer.parseInt(stringExtra)), false, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    public boolean slideMenuServerSync = false;
    private String currentKeyword = "";

    /* loaded from: classes.dex */
    public interface IOnMainDataSendListener {
        void send(BeanMain beanMain);
    }

    private void checkIntent() {
        String str = "seq";
        String str2 = "nseq";
        String str3 = "type";
        String str4 = SettingsJsonConstants.PROMPT_TITLE_KEY;
        Intent intent = getIntent();
        if (intent.getStringExtra("seq") != null) {
            str3 = intent.getStringExtra("type");
            str = intent.getStringExtra("seq");
            str2 = intent.getStringExtra("nseq");
            if (str3.equals("P_A")) {
                str4 = getIntent().getStringExtra("ProjectTitle");
            }
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                str = extras.getString("seq");
                str2 = extras.getString("nseq");
                str3 = extras.getString("type");
                if (str3.equals("P_A")) {
                    str4 = extras.getString("ProjectTitle");
                }
            }
        }
        if (str3.equals("P_A")) {
            showAcceptDialog(str4, String.valueOf(str));
            httpPostReadNotification(Integer.parseInt(str2));
        } else if (!str3.equals("type")) {
            int parseInt = Integer.parseInt(str);
            httpPostReadNotification(Integer.parseInt(str2));
            replaceFragment(R.id.base_container, FragmentProject.newInstance(parseInt), false, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        }
    }

    private void httpPostReadNotification(int i) {
        new BeplanOkHttpClient().checkNotification(getApplicationContext(), 382, String.valueOf(i), this);
    }

    public void httpPostProjectRefuse(String str) {
        new BeplanOkHttpClient().ProjectRefuse(getApplicationContext(), REQ_PROJECT_REFUSE, str, this);
    }

    public void httpPostProjectAccept(String str) {
        new BeplanOkHttpClient().ProjectAccept(getApplicationContext(), REQ_PROJECT_ACCEPT, str, this);
    }

    private void showAcceptDialog(String str, final String str2) {
        DialogCommon.newInstance(this, "프로젝트 초대", String.format("'%s'프로젝트에 참여하시겠습니까?", str), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.activity.ActivityMain.2
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                ActivityMain.this.httpPostProjectAccept(str2);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                ActivityMain.this.httpPostProjectRefuse(str2);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText("예");
                dialogCommon.getmBtnNegative().setText("아니오");
            }
        }).show();
    }

    public void setTimerListener(ServiceClock.onTimerListener ontimerlistener) {
        this.mServiceClock.setmOnTimerListener(ontimerlistener);
    }

    private void bindService() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class) && !this.isBind) {
            bindService(new Intent(getApplicationContext(), ServiceClock.class), this.mConnection, 1);
        }
    }

    private void unbindService() {
        if (this.mConnection != null && this.isBind) {
            unbindService(this.mConnection);
            this.isBind = false;
        }
    }

    public void startTimer(int i, int i2, int i3, String str, String str2, String str3, long j) {
        Intent intent = new Intent(this, ServiceClock.class);
        intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_RUNNING_RUN);
        intent.putExtra(ServiceClock.EXTRA_LONG_TIME, j);
        BeanStopWatch beanStopWatch = new BeanStopWatch(i, i2, i3, str, str2, str3, -1L, -1L, -1L);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ServiceClock.EXTRA_BEAN_OBJECT, beanStopWatch);
        intent.putExtras(bundle);
        startService(intent);
        bindService();
    }

    public void stopTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_RUNNING_STOP);
            startService(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        hideTimer();
        unbindService();
    }

    public void showTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_SHOW_TIMER);
            startService(intent);
        }
    }

    public void hideTimer() {
        if (Utils.isMyServiceRunning(this, ServiceClock.class)) {
            Intent intent = new Intent(this, ServiceClock.class);
            intent.putExtra(ServiceClock.EXTRA_RUNNING_TYPE, ServiceClock.TYPE_HIDE_TIMER);
            startService(intent);
        }
    }

    public void setMyInfo(boolean z) {
        this.mBaseToolbar.setMyInfo(z);
    }

    public String getCurrentKeyword() {
        return this.currentKeyword;
    }

    public void setCurrentKeyword(String str) {
        this.currentKeyword = str;
    }

    public void httpPostMain() {
        new BeplanOkHttpClient().MainNewIndex(this, REQ_MAIN, this);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mLayoutDrawer.isDrawerOpen(3)) {
            this.mLayoutDrawer.closeDrawer(3);
            return;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment findFragmentById = supportFragmentManager.findFragmentById(R.id.base_container);
        if (findFragmentById != null) {
            FragmentManager childFragmentManager = findFragmentById.getChildFragmentManager();
            Fragment findFragmentById2 = childFragmentManager.findFragmentById(R.id.base_project_container);
            Fragment findFragmentById3 = childFragmentManager.findFragmentById(R.id.base_category_container);
            supportFragmentManager.getBackStackEntryCount();
            if (supportFragmentManager.getBackStackEntryCount() < 1) {
                getFragmentManager().findFragmentById(R.id.base_container);
                if (findFragmentById instanceof FragmentRemakeMain) {
                    super.onBackPressed();
                }
                replaceFragment(R.id.base_container, new FragmentRemakeMain(), false);
            } else if (findFragmentById2 == null && findFragmentById3 == null) {
                super.onBackPressed();
            } else {
                childFragmentManager.popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void initFragments() {
        replaceFragment(R.id.base_container, new FragmentRemakeMain(), false);
        replaceFragment(R.id.layout_slide_menu, FragmentDrawer.newInstance(this), false);
    }

    private void initActionBar() {
        this.mBaseToolbar.setOnToolbarClickListener(this);
    }

    public BaseMainToolBar getmBaseToolbar() {
        return this.mBaseToolbar;
    }

    @Override // android.app.Activity
    public void setTitle(CharSequence charSequence) {
        String valueOf = String.valueOf(charSequence);
        if (valueOf.contains("</")) {
            String[] split = charSequence.toString().split("</");
            boolean parseBoolean = Boolean.parseBoolean(split[0]);
            boolean parseBoolean2 = Boolean.parseBoolean(split[1]);
            boolean parseBoolean3 = Boolean.parseBoolean(split[3]);
            boolean parseBoolean4 = Boolean.parseBoolean(split[4]);
            String str = split[2];
            this.mBaseToolbar.setToolbarVisibleState(parseBoolean, parseBoolean2, parseBoolean3, parseBoolean4);
            this.mBaseToolbar.setTitle(str);
        } else if (valueOf.contains("/>")) {
            String[] split2 = charSequence.toString().split("/>");
            String str2 = split2[0];
            int parseInt = Integer.parseInt(split2[1]);
            this.mBaseToolbar.setTitle(str2);
            this.mBaseToolbar.setTypePlan(parseInt, Boolean.parseBoolean(split2[2]), Boolean.parseBoolean(split2[3]), Boolean.parseBoolean(split2[4]), Boolean.parseBoolean(split2[5]));
        } else {
            this.mBaseToolbar.setToolbarVisibleState(true, false, true, false);
            this.mBaseToolbar.setTitle(charSequence);
        }
    }

    @Override // android.app.Activity
    public void setTitle(int i) {
        this.mBaseToolbar.setTitle(i);
    }

    public void setToolbarVisibleState(boolean z, boolean z2, boolean z3, boolean z4) {
        this.mBaseToolbar.setToolbarVisibleState(z, z2, z3, z4);
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarDrawerClicked(View view) {
        this.mLayoutDrawer.openDrawer(3);
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarAddClicked(View view) {
        if (this.mOnToolbarListener != null) {
            this.mOnToolbarListener.onToolbarAddClicked(this.mBaseToolbar);
        }
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarMenuClicked(View view) {
        if (this.mOnToolbarListener != null) {
            this.mOnToolbarListener.onToolbarMenuClicked(this.mBaseToolbar);
        }
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarCloseClicked(View view) {
        onBackPressed();
    }

    @Override // kr.timehub.beplan.fragment.main.FragmentDrawerLayout.OnDrawerClickListener
    public void onDrawerProjectClicked(View view, BeanDrawerList beanDrawerList) {
        sendBroadcast(new Intent(Comm_Params.ACTION_MAIN_DRAWER_CLOSE));
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        for (int i = 0; i < supportFragmentManager.getBackStackEntryCount(); i++) {
            supportFragmentManager.popBackStack();
        }
        BeanMain.Project_List project_List = new BeanMain.Project_List();
        project_List.Project_Member = beanDrawerList.getProject_members();
        project_List.ProjectTitle = beanDrawerList.getTitle();
        project_List.PSEQ = beanDrawerList.getSeq();
        if (project_List.IsInvite) {
            replaceFragment(R.id.base_container, FragmentProject.newInstance(project_List, beanDrawerList.getTitle(), beanDrawerList.getSeq()), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        }
    }

    @Override // kr.timehub.beplan.fragment.main.FragmentDrawerLayout.OnDrawerClickListener
    public void onDrawerProfileClicked(View view, int i) {
        Fragment fragment;
        this.mLayoutDrawer.closeDrawer(3);
        switch (i) {
            case 0:
                fragment = new FragmentAlerts();
                break;
            case 1:
                fragment = new FragmentTeamManagement();
                break;
            case 2:
                fragment = new FragmentSetting();
                break;
            case 3:
                fragment = new FragmentMyInfo();
                break;
            default:
                fragment = null;
                break;
        }
        if (fragment != null && !Utils.isEqualsVisibleFragment(getSupportFragmentManager(), R.id.base_container, fragment)) {
            if (i == 3) {
                replaceFragment(R.id.base_container, fragment, true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                return;
            }
            getSupportFragmentManager().popBackStack((String) null, 1);
            replaceFragment(R.id.base_container, fragment, true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
        }
    }

    @Override // kr.timehub.beplan.fragment.main.FragmentDrawerLayout.OnDrawerClickListener
    public void onDrawerTitleClicked(View view, BeanDrawerTitle beanDrawerTitle) {
        if (beanDrawerTitle.getTitle().equals("메인")) {
            this.mLayoutDrawer.closeDrawer(3);
            getSupportFragmentManager().popBackStack((String) null, 1);
            replaceFragment(R.id.base_container, new FragmentRemakeMain(), false);
        }
        if (beanDrawerTitle.getTitle().equals(FragmentTeamManagement.FRAGMENT_MY_TEAM)) {
            this.mLayoutDrawer.closeDrawer(3);
            getSupportFragmentManager().popBackStack((String) null, 1);
            replaceFragment(R.id.base_container, FragmentTeamManagement.newInstance(FragmentTeamManagement.FRAGMENT_MY_TEAM), false);
        }
        if (beanDrawerTitle.getTitle().equals(FragmentForm.FRAGMENT_MY_FORM)) {
            this.mLayoutDrawer.closeDrawer(3);
            getSupportFragmentManager().popBackStack((String) null, 1);
            replaceFragment(R.id.base_container, FragmentForm.newInstance(FragmentForm.FRAGMENT_MY_FORM), false);
        }
        if (beanDrawerTitle.getTitle().equals(FragmentForm.FRAGMENT_MY_SUBSCRIPTION)) {
            this.mLayoutDrawer.closeDrawer(3);
            getSupportFragmentManager().popBackStack((String) null, 1);
            replaceFragment(R.id.base_container, FragmentForm.newInstance(FragmentForm.FRAGMENT_MY_SUBSCRIPTION), false);
        }
        if (beanDrawerTitle.getTitle().equals(FragmentForm.FRAGMENT_FORM_SHOP)) {
            this.mLayoutDrawer.closeDrawer(3);
            getSupportFragmentManager().popBackStack((String) null, 1);
            replaceFragment(R.id.base_container, FragmentForm.newInstance(FragmentForm.FRAGMENT_FORM_SHOP), false);
        }
    }

    public void replaceMySubscriptionProject(int i) {
        this.mLayoutDrawer.closeDrawer(3);
        getSupportFragmentManager().popBackStack((String) null, 1);
        replaceFragment(R.id.base_container, FragmentForm.newInstance(FragmentForm.FRAGMENT_MY_SUBSCRIPTION, i), false);
    }

    public void replaceMySubscriptionCategory() {
        this.mLayoutDrawer.closeDrawer(3);
        getSupportFragmentManager().popBackStack((String) null, 1);
        replaceFragment(R.id.base_container, FragmentForm.newInstance(FragmentForm.FRAGMENT_MY_SUBSCRIPTION, true), false);
    }

    public void replaceMyForm() {
        this.mLayoutDrawer.closeDrawer(3);
        getSupportFragmentManager().popBackStack((String) null, 1);
        replaceFragment(R.id.base_container, FragmentForm.newInstance(FragmentForm.FRAGMENT_MY_FORM), false);
    }

    public BaseMainToolBar.OnToolbarClickListener getmOnToolbarListener() {
        return this.mOnToolbarListener;
    }

    public void setOnToolbarListener(BaseMainToolBar.OnToolbarClickListener onToolbarClickListener) {
        this.mOnToolbarListener = onToolbarClickListener;
    }

    private void registerReceiverDrawer() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Comm_Params.ACTION_MAIN_DRAWER_OPEN);
        intentFilter.addAction(Comm_Params.ACTION_MAIN_DRAWER_CLOSE);
        intentFilter.addAction(Comm_Params.PUSH_RECEIVE);
        registerReceiver(this.mReceiverDrawer, intentFilter);
    }

    private void unregisterReceiverDrawer() {
        unregisterReceiver(this.mReceiverDrawer);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        updateStatusbarTranslate(this.mVState);
        initFragments();
        registerReceiverDrawer();
        checkIntent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        initActionBar();
        onAction();
        showTimer();
        bindService();
    }

    private void onAction() {
        this.mLayoutDrawer.addDrawerListener(new DrawerLayout.DrawerListener() { // from class: kr.timehub.beplan.activity.ActivityMain.4
            @Override // android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerClosed(View view) {
            }

            @Override // android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerSlide(View view, float f) {
            }

            @Override // android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerStateChanged(int i) {
            }

            @Override // android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerOpened(View view) {
                ActivityMain.this.httpPostMain();
                ActivityMain.this.slideMenuServerSync = true;
            }
        });
    }

    public boolean isSlideMenuServerSync() {
        return this.slideMenuServerSync;
    }

    public void setSlideMenuServerSync(boolean z) {
        this.slideMenuServerSync = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        unregisterReceiverDrawer();
        super.onDestroy();
    }

    public IOnMainDataSendListener getmIODrawerListener() {
        return this.mIODrawerListener;
    }

    public void setmIODrawerListener(IOnMainDataSendListener iOnMainDataSendListener) {
        this.mIODrawerListener = iOnMainDataSendListener;
    }

    public IOnMainDataSendListener getmIOMainListener() {
        return this.mIOMainListener;
    }

    public void setmIOMainListener(IOnMainDataSendListener iOnMainDataSendListener) {
        this.mIOMainListener = iOnMainDataSendListener;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseActivity, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        Gson gson = new Gson();
        if (str.contains("<Error>") || str.contains("<html>")) {
            Toast.makeText(this, "로그인 정보를 얻어오지 못했습니다.\n로그인페이지로 이동합니다.", 0).show();
            Utils.Logout(this, this);
        } else if (intExtra == 99999) {
            BeanMain beanMain = (BeanMain) gson.fromJson(str, (Class<Object>) BeanMain.class);
            if (this.mLayoutDrawer.isDrawerOpen(3)) {
                if (getmIODrawerListener() != null) {
                    getmIODrawerListener().send(beanMain);
                }
                if (TextUtils.isEmpty(getCurrentKeyword()) && getmIOMainListener() != null) {
                    getmIOMainListener().send(beanMain);
                }
            } else if (getmIOMainListener() != null) {
                getmIOMainListener().send(beanMain);
            }
        } else if (intExtra == 99998) {
            Toast.makeText(this, ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
            httpPostMain();
        } else if (intExtra == 99997) {
            Toast.makeText(this, ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
            httpPostMain();
        } else if (intExtra == 99996) {
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                Toast.makeText(this, "성공적으로 삭제되었습니다", 0).show();
                httpPostMain();
                return;
            }
            Toast.makeText(this, beanCommon.RtnValue, 0).show();
        }
    }
}
