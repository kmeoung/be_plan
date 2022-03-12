package kr.timehub.beplan.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Point;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import java.util.Iterator;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityDialog;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.activity.ActivityTimer;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanStopWatch;
import kr.timehub.beplan.entry.database.DbStopWatch;
import kr.timehub.beplan.utils.Comm_Prefs;
import kr.timehub.beplan.utils.NotificationHelper;

/* loaded from: classes.dex */
public class ServiceClock extends Service {
    public static final String EXTRA_BEAN_OBJECT = "EXTRA_BEAN_OBJECT";
    public static final String EXTRA_LONG_TIME = "EXTRA_LONG_TIME";
    public static final String EXTRA_RUNNING_TYPE = "EXTRA_RUNNING_TYPE";
    public static final String TYPE_HIDE_TIMER = "TYPE_HIDE_TIMER";
    public static final String TYPE_ONLY_CLOSE_DIALOG = "TYPE_ONLY_CLOSE_DIALOG";
    public static final String TYPE_RUNNING_RUN = "TYPE_RUNNING_RUN";
    public static final String TYPE_RUNNING_STOP = "TYPE_RUNNING_STOP";
    public static final String TYPE_SHOW_TIMER = "TYPE_SHOW_TIMER";
    public static final int TYPE_STOPWATCH = 1;
    public static final int TYPE_TIMER = 0;
    private long clickTime;
    private int initX;
    private int initY;
    private long mBaseTimer;
    private BeanStopWatch mBean;
    private long mEll;
    private Handler mHandler;
    @BindView(R.id.ll_timer)
    LinearLayout mLlTimer;
    onTimerListener mOnTimerListener;
    @BindView(R.id.tv_mili)
    TextView mTvMili;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    private WindowManager.LayoutParams params;
    protected View rootView;
    private WindowManager windowManager;
    private int hour = 999999;
    private int minute = 999999;
    private int second = 999999;
    private int milisecond = 999999;
    private boolean isShowBtn = false;
    private boolean isShowDialog = false;
    private int mClockType = -1;
    private boolean isRunning = false;
    private final IBinder mBinder = new LocalBinder();
    private float mPrevX = 0.0f;
    private float mPrevY = 0.0f;
    private boolean isTimerMoving = false;
    Handler ActivityMoveHandler = new Handler() { // from class: kr.timehub.beplan.service.ServiceClock.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            ServiceClock.this.isTimerMoving = true;
        }
    };
    private Handler.Callback mHandlerCallback = new Handler.Callback() { // from class: kr.timehub.beplan.service.ServiceClock.3
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (ServiceClock.this.mClockType) {
                case 0:
                    ServiceClock.this.mTvTime.setText(ServiceClock.this.getTimerEllapse());
                    if (ServiceClock.this.hour > 0) {
                        ServiceClock.this.mTvMili.setText(String.format("%02d", Integer.valueOf(ServiceClock.this.second)));
                    } else {
                        ServiceClock.this.mTvMili.setText(String.format("%02d", Integer.valueOf(ServiceClock.this.milisecond)));
                    }
                    if (ServiceClock.this.getmOnTimerListener() != null) {
                        ServiceClock.this.getmOnTimerListener().onTimer(ServiceClock.this.mBean, ServiceClock.this.isRunning);
                    }
                    if (!ServiceClock.this.checkTime()) {
                        ServiceClock.this.startTimerHandler();
                        break;
                    } else {
                        ServiceClock.this.sendAlarm();
                        ServiceClock.this.stopClockService();
                        break;
                    }
                case 1:
                    ServiceClock.this.mTvTime.setText(ServiceClock.this.getStopWatchEllapse());
                    if (ServiceClock.this.hour > 0) {
                        ServiceClock.this.mTvMili.setText(String.format("%02d", Integer.valueOf(ServiceClock.this.second)));
                    } else {
                        ServiceClock.this.mTvMili.setText(String.format("%02d", Integer.valueOf(ServiceClock.this.milisecond)));
                    }
                    if (ServiceClock.this.getmOnTimerListener() != null) {
                        ServiceClock.this.getmOnTimerListener().onTimer(ServiceClock.this.mBean, ServiceClock.this.isRunning);
                    }
                    ServiceClock.this.startTimerHandler();
                    break;
            }
            return false;
        }
    };

    /* loaded from: classes.dex */
    public interface onTimerListener {
        void onTimer(BeanStopWatch beanStopWatch, boolean z);
    }

    /* loaded from: classes.dex */
    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public void setTimer(onTimerListener ontimerlistener) {
            ServiceClock.this.setmOnTimerListener(ontimerlistener);
        }

        public ServiceClock getService() {
            return ServiceClock.this;
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            return 2;
        }
        String stringExtra = intent.getStringExtra(EXTRA_RUNNING_TYPE);
        if (TextUtils.isEmpty(stringExtra)) {
            stopClockService();
        } else if (TextUtils.equals(stringExtra, TYPE_RUNNING_RUN)) {
            long longExtra = intent.getLongExtra(EXTRA_LONG_TIME, -1L);
            if (longExtra < 1) {
                this.mClockType = 1;
            } else {
                this.mClockType = 0;
            }
            this.mBean = (BeanStopWatch) intent.getSerializableExtra(EXTRA_BEAN_OBJECT);
            startClockService(longExtra, this.mClockType);
            Intent intent2 = new Intent(getApplicationContext(), ActivityMain.class);
            intent2.addFlags(268435456);
            intent2.addFlags(67108864);
            startForeground(10, NotificationHelper.getInstance(this).createBigTextNotification("Beplan 타이머가 실행중 입니다.", "", PendingIntent.getActivity(getApplicationContext(), 10, intent2, 134217728)));
            this.params.x = this.initX;
            this.params.y = this.initY;
            if (Build.VERSION.SDK_INT < 23) {
                this.windowManager.addView(this.rootView, this.params);
                this.isShowBtn = true;
            } else if (Settings.canDrawOverlays(this) && this.rootView.getParent() == null && intent != null) {
                this.windowManager.addView(this.rootView, this.params);
                this.isShowBtn = true;
            }
        } else if (TextUtils.equals(stringExtra, TYPE_RUNNING_STOP)) {
            stopClockService();
        } else if (TextUtils.equals(stringExtra, TYPE_SHOW_TIMER)) {
            if (this.isRunning) {
                showView();
            }
        } else if (TextUtils.equals(stringExtra, TYPE_HIDE_TIMER)) {
            if (this.isRunning) {
                removeView();
            }
        } else if (TextUtils.equals(stringExtra, TYPE_ONLY_CLOSE_DIALOG)) {
            this.isShowDialog = false;
        } else {
            stopClockService();
        }
        return 2;
    }

    private void showView() {
        if (Build.VERSION.SDK_INT < 23) {
            this.windowManager.addView(this.rootView, this.params);
            this.isShowBtn = true;
        } else if (Settings.canDrawOverlays(this) && this.rootView.getParent() == null) {
            this.windowManager.addView(this.rootView, this.params);
            this.isShowBtn = true;
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.windowManager = (WindowManager) getSystemService("window");
        if (Build.VERSION.SDK_INT >= 26) {
            this.params = new WindowManager.LayoutParams(-2, -2, 2038, 6815752, -3);
        } else {
            this.params = new WindowManager.LayoutParams(-2, -2, 2003, 6815752, -3);
        }
        this.rootView = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.popup_timer, (ViewGroup) null);
        ButterKnife.bind(this, this.rootView);
        Point screenSize = getScreenSize();
        this.mLlTimer.measure(1073741824, 1073741824);
        int applyDimension = (int) TypedValue.applyDimension(1, 150.0f, getResources().getDisplayMetrics());
        this.initX = (screenSize.x - applyDimension) / 2;
        this.initY = (screenSize.y - applyDimension) / 2;
        setDraggable();
    }

    public Point getScreenSize() {
        Display defaultDisplay = this.windowManager.getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point;
    }

    private void setDraggable() {
        this.rootView.setOnTouchListener(new View.OnTouchListener() { // from class: kr.timehub.beplan.service.ServiceClock.2
            private float initialTouchX;
            private float initialTouchY;
            private int initialX;
            private int initialY;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        this.initialX = ServiceClock.this.params.x;
                        this.initialY = ServiceClock.this.params.y;
                        this.initialTouchX = motionEvent.getRawX();
                        this.initialTouchY = motionEvent.getRawY();
                        ServiceClock.this.ActivityMoveHandler.sendEmptyMessageDelayed(0, 150L);
                        ServiceClock.this.clickTime = System.currentTimeMillis();
                        return true;
                    case 1:
                        if (ServiceClock.this.isTimerMoving) {
                            ServiceClock.this.isTimerMoving = false;
                        } else {
                            if (!ServiceClock.this.isShowDialog) {
                                Intent intent = new Intent(ServiceClock.this.getApplicationContext(), ActivityDialog.class);
                                intent.setFlags(268435456);
                                ServiceClock.this.startActivity(intent);
                                ServiceClock.this.isShowDialog = true;
                            }
                            ServiceClock.this.ActivityMoveHandler.removeMessages(0);
                        }
                        return true;
                    case 2:
                        if (ServiceClock.this.isTimerMoving) {
                            ServiceClock.this.params.x = this.initialX + ((int) (motionEvent.getRawX() - this.initialTouchX));
                            ServiceClock.this.params.y = this.initialY + ((int) (motionEvent.getRawY() - this.initialTouchY));
                            if (ServiceClock.this.rootView != null) {
                                ServiceClock.this.windowManager.updateViewLayout(ServiceClock.this.rootView, ServiceClock.this.params);
                            }
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void removeView() {
        if (this.rootView != null && this.windowManager != null && this.isShowBtn) {
            this.windowManager.removeView(this.rootView);
            this.isShowBtn = false;
        }
    }

    public onTimerListener getmOnTimerListener() {
        return this.mOnTimerListener;
    }

    public void setmOnTimerListener(onTimerListener ontimerlistener) {
        this.mOnTimerListener = ontimerlistener;
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void restartTimer() {
        Comm_Prefs instance = Comm_Prefs.getInstance(getApplicationContext());
        this.mClockType = instance.getTimerType();
        instance.setEll(this.mBaseTimer);
        switch (this.mClockType) {
            case 0:
                long timerStartTime = instance.getTimerStartTime();
                long ell = instance.getEll() - (instance.getTimerStartTime() - SystemClock.elapsedRealtime());
                long j = 0;
                if (ell >= 0) {
                    j = ell;
                }
                this.mBaseTimer = timerStartTime - j;
                getTimerEllapse();
                break;
            case 1:
                getStopWatchEllapse();
                break;
        }
        startTimerHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkTime() {
        return this.hour <= 0 && this.minute <= 0 && this.second <= 0 && this.milisecond <= 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTimerHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(this.mHandlerCallback);
        }
        this.mHandler.sendEmptyMessage(0);
    }

    private void removeTimerHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(this.mHandlerCallback);
        }
        this.mHandler.removeMessages(0);
    }

    public void startClockService(long j, int i) {
        if (i == 0 || i == 1) {
            Comm_Prefs instance = Comm_Prefs.getInstance(getApplicationContext());
            this.mClockType = i;
            switch (this.mClockType) {
                case 0:
                    this.mBaseTimer = SystemClock.elapsedRealtime() + j;
                    instance.setTimerStartTime(this.mBaseTimer);
                    instance.setEll(0L);
                    instance.setTimerType(this.mClockType);
                    this.hour = 999999;
                    this.minute = 999999;
                    this.second = 999999;
                    this.milisecond = 999999;
                    getTimerEllapse();
                    startTimerHandler();
                    break;
                case 1:
                    this.mBaseTimer = SystemClock.elapsedRealtime();
                    instance.setTimerStartTime(this.mBaseTimer);
                    instance.setEll(0L);
                    instance.setTimerType(this.mClockType);
                    this.hour = -1;
                    this.minute = -1;
                    this.second = -1;
                    this.milisecond = -1;
                    getStopWatchEllapse();
                    if (this.mBean != null) {
                        this.mBean.setStartTime(System.currentTimeMillis());
                        Realm defaultInstance = Realm.getDefaultInstance();
                        defaultInstance.executeTransaction(new Realm.Transaction() { // from class: kr.timehub.beplan.service.ServiceClock.4
                            @Override // io.realm.Realm.Transaction
                            public void execute(Realm realm) {
                                realm.copyToRealm((Realm) new DbStopWatch(ServiceClock.this.mBean.getPSEQ(), ServiceClock.this.mBean.getCGSEQ(), ServiceClock.this.mBean.getTSEQ(), ServiceClock.this.mBean.getpTitle(), ServiceClock.this.mBean.getCgTitle(), ServiceClock.this.mBean.gettTitle(), ServiceClock.this.mBean.getStartTime(), ServiceClock.this.mBean.getEndTime(), ServiceClock.this.mBean.getEll()));
                            }
                        });
                        defaultInstance.close();
                        startTimerHandler();
                        break;
                    } else {
                        Toast.makeText(this, "Data Sendding failed", 0).show();
                        return;
                    }
            }
            this.isRunning = true;
            return;
        }
        Toast.makeText(this, "diffrent Service Type", 0).show();
        stopSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAlarm() {
        Intent intent = new Intent(getApplicationContext(), ActivityTimer.class);
        intent.addFlags(268435456);
        if (this.mBean != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ActivityTimer.EXTRA_BEAN, this.mBean);
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void stopClockService() {
        this.mBaseTimer = SystemClock.elapsedRealtime() + 0;
        Comm_Prefs instance = Comm_Prefs.getInstance(getApplicationContext());
        instance.setTimerStartTime(this.mBaseTimer);
        instance.setEll(0L);
        if (this.mClockType == 1) {
            this.mBean.setEndTime(System.currentTimeMillis());
            this.mBean.setEll(this.mEll);
            Realm defaultInstance = Realm.getDefaultInstance();
            defaultInstance.executeTransaction(new Realm.Transaction() { // from class: kr.timehub.beplan.service.ServiceClock.5
                @Override // io.realm.Realm.Transaction
                public void execute(Realm realm) {
                    Iterator it = realm.where(DbStopWatch.class).findAll().iterator();
                    while (it.hasNext()) {
                        DbStopWatch dbStopWatch = (DbStopWatch) it.next();
                        if (dbStopWatch.getEll() < 0) {
                            dbStopWatch.setEndTime(ServiceClock.this.mBean.getEndTime());
                            dbStopWatch.setEll(ServiceClock.this.mBean.getEll());
                            realm.copyToRealm((Realm) dbStopWatch);
                            return;
                        }
                    }
                }
            });
            defaultInstance.close();
        }
        this.isRunning = false;
        this.isShowDialog = false;
        if (getmOnTimerListener() != null) {
            getmOnTimerListener().onTimer(this.mBean, this.isRunning);
        }
        removeTimerHandler();
        stopForeground(true);
        stopSelf();
        removeView();
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public String getStopWatchEllapse() {
        this.mEll = SystemClock.elapsedRealtime() - this.mBaseTimer;
        this.hour = (((int) this.mEll) / 1000) / 3600;
        this.minute = ((int) ((this.mEll / 1000) / 60)) % 60;
        this.second = ((int) (this.mEll / 1000)) % 60;
        this.milisecond = ((int) (this.mEll % 1000)) / 10;
        if (this.hour < 1) {
            return String.format("%02d:%02d", Integer.valueOf(this.minute), Integer.valueOf(this.second));
        }
        return String.format("%02d:%02d", Integer.valueOf(this.hour), Integer.valueOf(this.minute));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getTimerEllapse() {
        this.mEll = this.mBaseTimer - SystemClock.elapsedRealtime();
        this.hour = (int) ((this.mEll / 1000) / 3600);
        this.minute = ((int) ((this.mEll / 1000) / 60)) % 60;
        this.second = ((int) (this.mEll / 1000)) % 60;
        this.milisecond = ((int) (this.mEll % 1000)) / 10;
        Comm_Prefs.getInstance(getApplicationContext()).setEll(this.mEll);
        if (this.hour < 1) {
            return String.format("%02d:%02d", Integer.valueOf(this.minute), Integer.valueOf(this.second));
        }
        return String.format("%02d:%02d", Integer.valueOf(this.hour), Integer.valueOf(this.minute));
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
        this.isShowBtn = false;
        this.isShowDialog = false;
        this.mBean = null;
        removeView();
        removeTimerHandler();
    }
}
