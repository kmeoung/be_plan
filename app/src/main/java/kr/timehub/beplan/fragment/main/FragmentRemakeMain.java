package kr.timehub.beplan.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.BaseLinearLayoutManager;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.entry.BeanMainHeader;
import kr.timehub.beplan.entry.BeanMainItem;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.fragment.FragmentAlerts;
import kr.timehub.beplan.fragment.FragmentNewProject;
import kr.timehub.beplan.fragment.FragmentProjectDetail;
import kr.timehub.beplan.fragment.FragmentPutInTemplate;
import kr.timehub.beplan.fragment.projects.FragmentProject;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Prefs;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentRemakeMain extends BaseFragment implements BaseMainToolBar.OnToolbarClickListener, ActivityMain.IOnMainDataSendListener {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_LIST = 1;
    private BeanMainItem beans;
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    private View mHeaderView;
    @BindView(R.id.tv_my_work_size)
    TextView mTvMyWorkSize;
    @BindView(R.id.tv_notification_size)
    TextView mTvNotificationSize;
    @BindView(R.id.tv_project_size)
    TextView mTvProjectSize;
    private Unbinder unbinder;
    private final int DELAY = 1000;
    private Bundle mSavedBundle = new Bundle();
    private boolean isResume = false;

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarCloseClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarDrawerClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarMenuClicked(View view) {
    }

    @OnClick({R.id.tv_notification_size})
    public void submitConfirm() {
        replaceFragment(R.id.base_container, FragmentAlerts.newInstance(), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
    }

    public void showAcceptDialog(String str, final String str2) {
        DialogCommon.newInstance(getContext(), "프로젝트 초대", String.format("'%s'프로젝트에 참여하시겠습니까?", str), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.main.FragmentRemakeMain.1
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                FragmentRemakeMain.this.httpPostProjectAccept(str2);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                FragmentRemakeMain.this.httpPostProjectRefuse(str2);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText("예");
                dialogCommon.getmBtnNegative().setText("아니오");
            }
        }).show();
    }

    public void showDeleteProjectDialog(final String str) {
        DialogCommon.newInstance(getContext(), getString(R.string.dialog_delete_project_title), getString(R.string.dialog_delete_project_contents), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.main.FragmentRemakeMain.2
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                FragmentRemakeMain.this.httpDeleteProjectDelete(str);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText(FragmentRemakeMain.this.getString(R.string.btn_yes));
                dialogCommon.getmBtnNegative().setText(FragmentRemakeMain.this.getString(R.string.btn_no));
            }
        }).show();
    }

    public void httpPostProjectAccept(String str) {
        new BeplanOkHttpClient().ProjectAccept(getContext(), ActivityMain.REQ_PROJECT_ACCEPT, str, this);
    }

    public void httpDeleteProjectDelete(String str) {
        new BeplanOkHttpClient().DeleteProject(getContext(), ActivityMain.REQ_PROJECT_DELETE, str, this);
    }

    public void httpPostProjectRefuse(String str) {
        new BeplanOkHttpClient().ProjectRefuse(getContext(), ActivityMain.REQ_PROJECT_REFUSE, str, this);
    }

    /* renamed from: kr.timehub.beplan.fragment.main.FragmentRemakeMain$3 */
    /* loaded from: classes.dex */
    public class AnonymousClass3 implements BaseRecyclerViewAdapterInterface {
        AnonymousClass3() {
            FragmentRemakeMain.this = r1;
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public int getItemCount() {
            return FragmentRemakeMain.this.mAdapter.size();
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public int getItemViewType(int i) {
            return FragmentRemakeMain.this.mAdapter.get(i) instanceof BeanMainHeader ? 0 : 1;
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (getItemViewType(i) == 0) {
                onHeaderViewHolder(viewHolder, i);
            } else {
                onListViewHolder(viewHolder, i);
            }
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i == 1) {
                return BaseViewHolder.newInstance(FragmentRemakeMain.this.getContext(), R.layout.listitem_remake_main, viewGroup);
            }
            return BaseViewHolder.newInstance(FragmentRemakeMain.this.getContext(), R.layout.header_remake_main, viewGroup);
        }

        public void onListViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
            final BeanMainItem beanMainItem = (BeanMainItem) FragmentRemakeMain.this.mAdapter.get(i);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_icon);
            TextView textView = (TextView) baseViewHolder.getView(R.id.tv_title);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_project_date);
            ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_more);
            ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_sell);
            ImageView imageView4 = (ImageView) baseViewHolder.getView(R.id.iv_notification);
            TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_project_maker);
            TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_member_size);
            TextView textView5 = (TextView) baseViewHolder.getView(R.id.tv_category_success_size);
            TextView textView6 = (TextView) baseViewHolder.getView(R.id.tv_category_size);
            if (beanMainItem.State == null) {
                imageView3.setVisibility(8);
            } else if (TextUtils.equals(beanMainItem.State, "S")) {
                imageView3.setVisibility(0);
            } else {
                imageView3.setVisibility(8);
            }
            if (TextUtils.equals(beanMainItem.Project_Type, "M")) {
                imageView.setImageResource(R.drawable.img_my);
                textView.setText(beanMainItem.ProjectTitle);
                textView2.setVisibility(4);
                imageView2.setVisibility(0);
                imageView4.setVisibility(8);
                textView3.setText(beanMainItem.MakeName);
                textView4.setText(String.valueOf(beanMainItem.MemberCnt));
                textView5.setText(String.valueOf(beanMainItem.OKTaskCnt));
                textView6.setText(String.valueOf(beanMainItem.TaskCnt));
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentRemakeMain.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        FragmentRemakeMain.this.replaceFragment(R.id.base_container, FragmentProject.newInstance(beanMainItem.getProjectList(), beanMainItem.ProjectTitle, beanMainItem.PSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentRemakeMain.3.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(FragmentRemakeMain.this.getContext(), view);
                        popupMenu.getMenu().add("상세");
                        if (beanMainItem.IsDelete) {
                            popupMenu.getMenu().add("삭제");
                        }
                        popupMenu.getMenu().add("담기");
                        popupMenu.inflate(R.menu.popup_menu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentRemakeMain.3.2.1
                            @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                char c;
                                String charSequence = menuItem.getTitle().toString();
                                int hashCode = charSequence.hashCode();
                                if (hashCode == 1464764) {
                                    if (charSequence.equals("담기")) {
                                        c = 2;
                                    }
                                    c = 65535;
                                } else if (hashCode == 1579159) {
                                    if (charSequence.equals("상세")) {
                                        c = 1;
                                    }
                                    c = 65535;
                                } else if (hashCode != 1580303) {
                                    if (hashCode == 1722580 && charSequence.equals("판매")) {
                                        c = 0;
                                    }
                                    c = 65535;
                                } else {
                                    if (charSequence.equals("삭제")) {
                                        c = 3;
                                    }
                                    c = 65535;
                                }
                                switch (c) {
                                    case 1:
                                        FragmentRemakeMain.this.replaceFragment(R.id.base_container, FragmentNewProject.newInstance(beanMainItem.getProjectList()), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                                        break;
                                    case 2:
                                        FragmentRemakeMain.this.replaceFragment(R.id.base_container, FragmentPutInTemplate.newInstance(beanMainItem.PSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                                        break;
                                    case 3:
                                        if (beanMainItem.Project_Member.size() <= 1) {
                                            FragmentRemakeMain.this.showDeleteProjectDialog(String.valueOf(beanMainItem.PSEQ));
                                            break;
                                        } else {
                                            Toast.makeText(FragmentRemakeMain.this.getContext(), "멤버가 없는 프로젝트만 삭제가 가능합니다.", 0).show();
                                            break;
                                        }
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                });
            } else if (TextUtils.equals(beanMainItem.Project_Type, "S")) {
                imageView.setImageResource(R.drawable.img_share);
                textView.setText(beanMainItem.ProjectTitle);
                textView2.setVisibility(4);
                imageView2.setVisibility(0);
                if (beanMainItem.InviteState == null) {
                    imageView4.setVisibility(8);
                } else if (!beanMainItem.IsInvite) {
                    imageView4.setVisibility(0);
                } else {
                    imageView4.setVisibility(8);
                }
                textView3.setText(beanMainItem.MakeName);
                textView4.setText(String.valueOf(beanMainItem.MemberCnt));
                textView5.setText(String.valueOf(beanMainItem.OKTaskCnt));
                textView6.setText(String.valueOf(beanMainItem.TaskCnt));
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentRemakeMain.3.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (beanMainItem.IsInvite) {
                            FragmentRemakeMain.this.replaceFragment(R.id.base_container, FragmentProject.newInstance(beanMainItem.getProjectList(), beanMainItem.ProjectTitle, beanMainItem.PSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                        } else {
                            FragmentRemakeMain.this.showAcceptDialog(beanMainItem.ProjectTitle, String.valueOf(beanMainItem.PSEQ));
                        }
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentRemakeMain.3.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(FragmentRemakeMain.this.getContext(), view);
                        popupMenu.getMenu().add("상세");
                        if (beanMainItem.IsDelete) {
                            popupMenu.getMenu().add("삭제");
                        }
                        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentRemakeMain.3.4.1
                            @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                char c;
                                String charSequence = menuItem.getTitle().toString();
                                int hashCode = charSequence.hashCode();
                                if (hashCode != 1579159) {
                                    if (hashCode == 1580303 && charSequence.equals("삭제")) {
                                        c = 1;
                                    }
                                    c = 65535;
                                } else {
                                    if (charSequence.equals("상세")) {
                                        c = 0;
                                    }
                                    c = 65535;
                                }
                                switch (c) {
                                    case 0:
                                        FragmentRemakeMain.this.replaceFragment(R.id.base_container, FragmentProjectDetail.newInstance(beanMainItem.PSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                                        break;
                                    case 1:
                                        if (beanMainItem.Project_Member.size() <= 1) {
                                            FragmentRemakeMain.this.showDeleteProjectDialog(String.valueOf(beanMainItem.PSEQ));
                                            break;
                                        } else {
                                            Toast.makeText(FragmentRemakeMain.this.getContext(), "멤버가 없는 프로젝트만 삭제가 가능합니다.", 0).show();
                                            break;
                                        }
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }
        }

        public void onHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
            BeanMainHeader beanMainHeader = (BeanMainHeader) FragmentRemakeMain.this.mAdapter.get(i);
            ((TextView) baseViewHolder.getView(R.id.tv_project_size)).setText(String.valueOf(beanMainHeader.getMyProjectSize()));
            ((TextView) baseViewHolder.getView(R.id.tv_my_work_size)).setText(String.valueOf(beanMainHeader.getMyWorkSize()));
            ((TextView) baseViewHolder.getView(R.id.tv_notification_size)).setText(String.valueOf(beanMainHeader.getMyNotificationSize()));
        }
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
        this.mAdapter.setAction(new AnonymousClass3());
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(getContext());
        baseLinearLayoutManager.setEnbleVerticalScrolling(true);
        this.mBaseRv.setLayoutManager(baseLinearLayoutManager);
        this.mBaseRv.setAdapter(this.mAdapter);
    }

    private void setAddActionButton() {
        ((ActivityMain) getActivity()).setOnToolbarListener(this);
    }

    private void initHeader(View view, String str) {
        this.mHeaderView = view.findViewById(R.id.layout_header);
        this.mHeaderView.setBackgroundColor(-1);
        this.mHeaderView.requestLayout();
    }

    private void onAction(View view) {
        initAdapter();
        getActivity().setTitle("true</false</메인</true</false");
        setAddActionButton();
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_remake_main, viewGroup, false);
        ((ActivityMain) getActivity()).setToolbarVisibleState(true, false, true, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        onAction(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarAddClicked(View view) {
        replaceFragment(R.id.base_container, new FragmentNewProject(), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        getActivity().setTitle("true</false</메인</true</false");
        ((ActivityMain) getActivity()).setmIOMainListener(this);
        ((ActivityMain) getActivity()).httpPostMain();
        this.isResume = true;
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        ((ActivityMain) getActivity()).setmIOMainListener(null);
    }

    public void httpPostMain() {
        new BeplanOkHttpClient().MainNewIndex(getContext(), ActivityMain.REQ_MAIN, this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        Gson gson = new Gson();
        if (i != 200) {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
            return;
        }
        BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
        if (intExtra == 99998) {
            Toast.makeText(getContext(), ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
            ((ActivityMain) getActivity()).httpPostMain();
        } else if (intExtra == 99997) {
            Toast.makeText(getContext(), ((BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class)).RtnValue, 0).show();
            ((ActivityMain) getActivity()).httpPostMain();
        } else if (intExtra == 99996) {
            BeanCommon beanCommon2 = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (TextUtils.equals(beanCommon2.RtnKey, "CMOK")) {
                Toast.makeText(getContext(), "성공적으로 삭제되었습니다", 0).show();
                ((ActivityMain) getActivity()).httpPostMain();
                return;
            }
            Toast.makeText(getContext(), beanCommon2.RtnValue, 0).show();
        }
    }

    @Override // kr.timehub.beplan.activity.ActivityMain.IOnMainDataSendListener
    public void send(BeanMain beanMain) {
        Comm_Prefs instance = Comm_Prefs.getInstance(getContext());
        instance.setUserEmail(beanMain.Email);
        instance.setUserName(beanMain.UserName);
        instance.setUserUrl(beanMain.ProfileImgUrl);
        this.mAdapter.clear();
        setHeader(new BeanMainHeader(beanMain.AllProjectList.size(), beanMain.MyTaskCnt, beanMain.NoCheckNotiCnt));
        for (BeanMain.AllProjectList allProjectList : beanMain.AllProjectList) {
            if (!allProjectList.IsInvite) {
                this.mAdapter.add(new BeanMainItem(allProjectList));
            }
        }
        for (BeanMain.AllProjectList allProjectList2 : beanMain.AllProjectList) {
            if (allProjectList2.IsInvite) {
                this.mAdapter.add(new BeanMainItem(allProjectList2));
            }
        }
    }

    private void setHeader(BeanMainHeader beanMainHeader) {
        this.mTvProjectSize.setText(String.valueOf(beanMainHeader.getMyProjectSize()));
        this.mTvMyWorkSize.setText(String.valueOf(beanMainHeader.getMyWorkSize()));
        this.mTvNotificationSize.setText(String.valueOf(beanMainHeader.getMyNotificationSize()));
    }
}
