package kr.timehub.beplan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseListFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BeanNotification;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.fragment.main.FragmentRemakeMain;
import kr.timehub.beplan.fragment.projects.FragmentProject;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Prefs;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentAlerts extends BaseListFragment<BeanNotification.NotificationList> {
    public static boolean isCheck = false;
    private final int REQ_NOTIFICATION = 1;
    private final int READ_NOTIFICATION = 2;
    public boolean isJoin = false;

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_null, viewGroup, false);
        getActivity().setTitle("true</false</알림 0</false</false");
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        httpPostNotification();
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment, android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public static final FragmentAlerts newInstance() {
        return new FragmentAlerts();
    }

    private void setNotifications(BeanNotification beanNotification) {
        if (beanNotification.NotificationList.size() > 0) {
            List<BeanNotification.NotificationList> list = beanNotification.NotificationList;
            Collections.sort(list, new Comparator<BeanNotification.NotificationList>() { // from class: kr.timehub.beplan.fragment.FragmentAlerts.1
                public int compare(BeanNotification.NotificationList notificationList, BeanNotification.NotificationList notificationList2) {
                    if (notificationList2.NSEQ > notificationList.NSEQ) {
                        return 1;
                    }
                    return notificationList2.NSEQ < notificationList.NSEQ ? -1 : 0;
                }
            });
            clear();
            for (BeanNotification.NotificationList notificationList : list) {
                boolean z = notificationList.IsRead;
                add(notificationList);
            }
            getActivity().setTitle(String.format("true</false</알림 %d</false</false", Integer.valueOf(beanNotification.NoCheckNotiCnt)));
        }
    }

    private void httpPostNotification() {
        new BeplanOkHttpClient().Notification(getContext(), 1, Comm_Prefs.getInstance(getContext()).getUserId(), this);
    }

    public void httpPostReadNotification(int i) {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String.valueOf(i);
        beplanOkHttpClient.checkNotification(getContext(), 2, String.valueOf(i), this);
    }

    public void httpPostProjectAccept(String str) {
        new BeplanOkHttpClient().ProjectAccept(getContext(), ActivityMain.REQ_PROJECT_ACCEPT, str, this);
    }

    public void httpPostProjectRefuse(String str) {
        new BeplanOkHttpClient().ProjectRefuse(getContext(), ActivityMain.REQ_PROJECT_REFUSE, str, this);
    }

    public void showAcceptDialog(String str, final String str2) {
        DialogCommon.newInstance(getContext(), "프로젝트 초대", String.format("'%s'프로젝트에 참여하시겠습니까?", str), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.FragmentAlerts.2
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                FragmentAlerts.this.httpPostProjectAccept(str2);
                FragmentAlerts.this.isJoin = true;
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                FragmentAlerts.this.httpPostProjectRefuse(str2);
                FragmentAlerts.this.isJoin = false;
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText("예");
                dialogCommon.getmBtnNegative().setText("아니오");
            }
        }).show();
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseListFragment
    public BaseRecyclerViewAdapterInterface initListInterface(BaseFragment baseFragment, RecyclerView recyclerView) {
        return new BaseRecyclerViewAdapterInterface() { // from class: kr.timehub.beplan.fragment.FragmentAlerts.3
            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemViewType(int i) {
                return 0;
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return BaseViewHolder.newInstance(FragmentAlerts.this.getContext(), R.layout.listitem_alerts, viewGroup);
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                String str;
                BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
                final BeanNotification.NotificationList notificationList = FragmentAlerts.this.get(i);
                String str2 = notificationList.Part;
                String str3 = notificationList.SubTitle;
                String str4 = notificationList.SenderName;
                Date ConvertDate = Utils.ConvertDate(notificationList.RegDate);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar instance = Calendar.getInstance();
                instance.setTime(ConvertDate);
                int i2 = instance.get(11);
                int i3 = instance.get(12);
                int i4 = instance.get(13);
                if (i2 >= 12) {
                    str = "오후";
                    if (i2 > 12) {
                        i2 -= 12;
                    }
                } else {
                    str = "오전";
                }
                String format = String.format("%s %s %d:%02d:%02d", simpleDateFormat.format(ConvertDate), str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
                baseViewHolder.setText(R.id.tv_comment_status, str3);
                baseViewHolder.setText(R.id.tv_comment_content, "");
                baseViewHolder.setText(R.id.tv_sender_name, str4);
                baseViewHolder.setText(R.id.tv_send_date, format);
                if (notificationList.IsRead) {
                    baseViewHolder.setTextColor(R.id.tv_comment_status, Integer.valueOf(FragmentAlerts.this.getResources().getColor(R.color.textColor_2)));
                } else {
                    baseViewHolder.setTextColor(R.id.tv_comment_status, Integer.valueOf(FragmentAlerts.this.getResources().getColor(R.color.black)));
                }
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentAlerts.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (notificationList.IsGoToHome.equals("true")) {
                            FragmentAlerts.this.replaceFragment(R.id.base_container, new FragmentRemakeMain(), false);
                        } else if (notificationList.Part.equals("P_A") && (!notificationList.IsRead)) {
                            FragmentAlerts.this.showAcceptDialog(notificationList.ProjectTitle, String.valueOf(notificationList.PartSEQ));
                            if (FragmentAlerts.this.isJoin) {
                                FragmentAlerts.this.httpPostReadNotification(notificationList.NSEQ);
                            }
                        } else {
                            FragmentAlerts.this.httpPostReadNotification(notificationList.NSEQ);
                            FragmentAlerts.this.replaceFragment(R.id.base_container, FragmentProject.newInstance(notificationList.PartSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                        }
                    }
                });
            }

            @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
            public int getItemCount() {
                return FragmentAlerts.this.size();
            }
        };
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            if (intExtra == 1) {
                BeanNotification beanNotification = (BeanNotification) gson.fromJson(str, (Class<Object>) BeanNotification.class);
                if (!isCheck) {
                    setNotifications(beanNotification);
                }
            } else if (intExtra == 2) {
                httpPostNotification();
            } else if (intExtra == 99998) {
                Toast.makeText(getContext(), ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
                ((ActivityMain) getActivity()).httpPostMain();
            } else if (intExtra == 99997) {
                Toast.makeText(getContext(), ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
                ((ActivityMain) getActivity()).httpPostMain();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }
}
