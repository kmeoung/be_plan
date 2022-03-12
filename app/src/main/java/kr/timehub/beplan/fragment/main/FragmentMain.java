package kr.timehub.beplan.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.objects.BaseItemDecoration;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanMain;
import kr.timehub.beplan.fragment.FragmentNewProject;
import kr.timehub.beplan.fragment.FragmentProjectDetail;
import kr.timehub.beplan.fragment.FragmentPutInTemplate;
import kr.timehub.beplan.fragment.projects.FragmentProject;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.utils.Comm_Prefs;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentMain extends BaseFragment implements BaseMainToolBar.OnToolbarClickListener, ActivityMain.IOnMainDataSendListener {
    public static final String HEADER_NAME = "SHARED PROJECT";
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_LIST = 1;
    @BindView(R.id.base_rv)
    RecyclerView baseRv;
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    private View mHeaderView;
    @BindView(R.id.tv_text)
    TextView mTvText;
    private Unbinder unbinder;
    private final int DELAY = 1000;
    private Bundle mSavedBundle = new Bundle();
    private boolean isResume = false;
    Handler handler = new Handler() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.7
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            ActivityMain activityMain = (ActivityMain) FragmentMain.this.getActivity();
            if (FragmentMain.this.mEtKeyword != null) {
                activityMain.httpPostMain();
            }
        }
    };

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarCloseClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarDrawerClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarMenuClicked(View view) {
    }

    public void showAcceptDialog(String str, final String str2) {
        DialogCommon.newInstance(getContext(), "프로젝트 초대", String.format("'%s'프로젝트에 참여하시겠습니까?", str), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.1
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                FragmentMain.this.httpPostProjectAccept(str2);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                FragmentMain.this.httpPostProjectRefuse(str2);
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
        DialogCommon.newInstance(getContext(), getString(R.string.dialog_delete_project_title), getString(R.string.dialog_delete_project_contents), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.2
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                FragmentMain.this.httpDeleteProjectDelete(str);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText(FragmentMain.this.getString(R.string.btn_yes));
                dialogCommon.getmBtnNegative().setText(FragmentMain.this.getString(R.string.btn_no));
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

    /* renamed from: kr.timehub.beplan.fragment.main.FragmentMain$3 */
    /* loaded from: classes.dex */
    public class AnonymousClass3 implements BaseRecyclerViewAdapterInterface {
        AnonymousClass3() {
            FragmentMain.this = r1;
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public int getItemCount() {
            return FragmentMain.this.mAdapter.size();
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public int getItemViewType(int i) {
            return FragmentMain.this.mAdapter.get(i) instanceof String ? 0 : 1;
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (getItemViewType(i) == 0) {
                onHeaderViewHolder(viewHolder, i);
            } else {
                onListViewHolder(viewHolder, i, FragmentMain.this.mAdapter.getHeaderSize() - i);
            }
        }

        @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i == 1) {
                return BaseViewHolder.newInstance(FragmentMain.this.getContext(), R.layout.listitem_main, viewGroup);
            }
            return BaseViewHolder.newInstance(FragmentMain.this.getContext(), R.layout.header_main, viewGroup);
        }

        public void onListViewHolder(RecyclerView.ViewHolder viewHolder, int i, int i2) {
            BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_more);
            if (FragmentMain.this.mAdapter.get(i) instanceof BeanMain.Project_List) {
                final BeanMain.Project_List project_List = (BeanMain.Project_List) FragmentMain.this.mAdapter.get(i);
                baseViewHolder.setText(R.id.tv_project, project_List.ProjectTitle);
                baseViewHolder.getView(R.id.iv_notice).setVisibility(8);
                imageView.setVisibility(0);
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        FragmentMain.this.replaceFragment(R.id.base_container, FragmentProject.newInstance(project_List, project_List.ProjectTitle, project_List.PSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.3.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(FragmentMain.this.getContext(), view);
                        popupMenu.getMenu().add("상세");
                        if (project_List.IsDelete) {
                            popupMenu.getMenu().add("삭제");
                        }
                        popupMenu.getMenu().add("담기");
                        popupMenu.inflate(R.menu.popup_menu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.3.2.1
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
                                        FragmentMain.this.replaceFragment(R.id.base_container, FragmentNewProject.newInstance(project_List), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                                        break;
                                    case 2:
                                        FragmentMain.this.replaceFragment(R.id.base_container, FragmentPutInTemplate.newInstance(project_List.PSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                                        break;
                                    case 3:
                                        if (project_List.Project_Member.size() <= 1) {
                                            FragmentMain.this.showDeleteProjectDialog(String.valueOf(project_List.PSEQ));
                                            break;
                                        } else {
                                            Toast.makeText(FragmentMain.this.getContext(), "멤버가 없는 프로젝트만 삭제가 가능합니다.", 0).show();
                                            break;
                                        }
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                });
            } else if (FragmentMain.this.mAdapter.get(i) instanceof BeanMain.Shared_Project_List) {
                final BeanMain.Shared_Project_List shared_Project_List = (BeanMain.Shared_Project_List) FragmentMain.this.mAdapter.get(i);
                baseViewHolder.setText(R.id.tv_project, shared_Project_List.ProjectTitle);
                String str = shared_Project_List.InviteState;
                imageView.setVisibility(0);
                if (str == null) {
                    baseViewHolder.getView(R.id.iv_notice).setVisibility(8);
                } else if (!shared_Project_List.IsInvite) {
                    baseViewHolder.getView(R.id.iv_notice).setVisibility(0);
                } else {
                    baseViewHolder.getView(R.id.iv_notice).setVisibility(8);
                }
                final BeanMain.Project_List project_List2 = new BeanMain.Project_List();
                project_List2.PSEQ = shared_Project_List.PSEQ;
                project_List2.Project_Type = shared_Project_List.Project_Type;
                project_List2.ProjectTitle = shared_Project_List.ProjectTitle;
                project_List2.Project_Member = shared_Project_List.Project_Member;
                project_List2.InviteState = shared_Project_List.InviteState;
                project_List2.MakeID = shared_Project_List.MakeID;
                project_List2.ModDate = shared_Project_List.ModDate;
                project_List2.ProjectCnt = shared_Project_List.ProjectCnt;
                project_List2.RegDate = shared_Project_List.RegDate;
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.3.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (shared_Project_List.IsInvite) {
                            FragmentMain.this.replaceFragment(R.id.base_container, FragmentProject.newInstance(project_List2, shared_Project_List.ProjectTitle, shared_Project_List.PSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                        } else {
                            FragmentMain.this.showAcceptDialog(shared_Project_List.ProjectTitle, String.valueOf(shared_Project_List.PSEQ));
                        }
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.3.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(FragmentMain.this.getContext(), view);
                        popupMenu.getMenu().add("상세");
                        if (shared_Project_List.IsDelete) {
                            popupMenu.getMenu().add("삭제");
                        }
                        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                        BeanMain.Project_List project_List3 = new BeanMain.Project_List();
                        project_List3.Project_Type = shared_Project_List.Project_Type;
                        project_List3.ProjectTitle = shared_Project_List.ProjectTitle;
                        project_List3.PSEQ = shared_Project_List.PSEQ;
                        project_List3.InviteState = shared_Project_List.InviteState;
                        project_List3.MakeID = shared_Project_List.MakeID;
                        project_List3.ModDate = shared_Project_List.ModDate;
                        project_List3.Project_Member = shared_Project_List.Project_Member;
                        project_List3.ProjectCnt = shared_Project_List.ProjectCnt;
                        project_List3.RegDate = shared_Project_List.RegDate;
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.3.4.1
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
                                        FragmentMain.this.replaceFragment(R.id.base_container, FragmentProjectDetail.newInstance(shared_Project_List.PSEQ), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                                        break;
                                    case 1:
                                        if (shared_Project_List.Project_Member.size() <= 1) {
                                            FragmentMain.this.showDeleteProjectDialog(String.valueOf(shared_Project_List.PSEQ));
                                            break;
                                        } else {
                                            Toast.makeText(FragmentMain.this.getContext(), "멤버가 없는 프로젝트만 삭제가 가능합니다.", 0).show();
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
            ((BaseViewHolder) viewHolder).itemView.setVisibility(4);
        }
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
        this.mAdapter.setAction(new AnonymousClass3());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.4
            @Override // android.support.v7.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                return FragmentMain.this.mAdapter.getItemViewType(i) == 0 ? 2 : 1;
            }
        });
        this.baseRv.addOnScrollListener(new AnonymousClass5(gridLayoutManager));
        this.baseRv.setLayoutManager(gridLayoutManager);
        this.baseRv.setAdapter(this.mAdapter);
        BaseItemDecoration baseItemDecoration = new BaseItemDecoration(0);
        baseItemDecoration.setSize((int) TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics()));
        this.baseRv.addItemDecoration(baseItemDecoration);
    }

    /* renamed from: kr.timehub.beplan.fragment.main.FragmentMain$5 */
    /* loaded from: classes.dex */
    public class AnonymousClass5 extends RecyclerView.OnScrollListener {
        private View headerView;
        final /* synthetic */ GridLayoutManager val$layoutManager;
        private int headerPosition = 0;
        private boolean isFirst = true;
        private Handler handler = new Handler();

        AnonymousClass5(GridLayoutManager gridLayoutManager) {
            FragmentMain.this = r1;
            this.val$layoutManager = gridLayoutManager;
        }

        private void getHeaderViewPosition() {
            for (int i = 0; i < FragmentMain.this.mAdapter.size(); i++) {
                if (FragmentMain.this.mAdapter.get(i) instanceof String) {
                    this.headerPosition = i;
                    return;
                }
            }
        }

        private boolean doFirstRequest(final RecyclerView recyclerView, final int i, final int i2) {
            if (!this.isFirst) {
                return false;
            }
            this.isFirst = false;
            this.handler.postDelayed(new Runnable() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.5.1
                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass5.this.onScrolled(recyclerView, i, i2);
                }
            }, 1L);
            return true;
        }

        @Override // android.support.v7.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            int findFirstVisibleItemPosition = this.val$layoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = this.val$layoutManager.findLastVisibleItemPosition();
            if (!doFirstRequest(recyclerView, i, i2)) {
                if (this.headerPosition >= FragmentMain.this.mAdapter.size() - 1 || !(FragmentMain.this.mAdapter.get(this.headerPosition) instanceof String)) {
                    getHeaderViewPosition();
                }
                if (this.headerPosition <= findFirstVisibleItemPosition) {
                    FragmentMain.this.mHeaderView.setVisibility(0);
                    FragmentMain.this.mHeaderView.bringToFront();
                    FragmentMain.this.mTvText.bringToFront();
                    ViewCompat.setY(FragmentMain.this.mHeaderView, 0.0f);
                }
                if (this.headerView == null) {
                    this.headerView = this.val$layoutManager.findViewByPosition(this.headerPosition);
                    if (this.headerView == null) {
                        return;
                    }
                }
                if (findLastVisibleItemPosition < this.headerPosition) {
                    FragmentMain.this.mHeaderView.setVisibility(8);
                } else if (findFirstVisibleItemPosition < this.headerPosition) {
                    FragmentMain.this.mHeaderView.setVisibility(0);
                    ViewCompat.setY(FragmentMain.this.mHeaderView, this.headerView.getY());
                }
            }
        }
    }

    private void setAddActionButton() {
        ((ActivityMain) getActivity()).setOnToolbarListener(this);
    }

    private void initHeader(View view, String str) {
        this.mHeaderView = view.findViewById(R.id.layout_header);
        this.mHeaderView.setBackgroundColor(-1);
        this.mTvText.setText(str);
        Utils.setTextColorGradient(getContext(), this.mTvText);
        this.mHeaderView.requestLayout();
    }

    private void bindTempData() {
        BeanMain.Project_List project_List = new BeanMain.Project_List();
        project_List.ModDate = "modDate";
        project_List.Project_Member = null;
        project_List.Project_Type = "m";
        project_List.ProjectCnt = 1;
        project_List.ProjectTitle = "Temp Data";
        project_List.PSEQ = 1;
        project_List.RegDate = "regDate";
        this.mAdapter.add(project_List);
        this.mAdapter.notifyDataSetChanged();
    }

    private void onAction(View view) {
        initAdapter();
        initHeader(view, HEADER_NAME);
        this.baseRv.setPadding(0, 0, (int) TypedValue.applyDimension(1, 12.0f, getResources().getDisplayMetrics()), 0);
        getActivity().setTitle("true</false</MY PROJECTS</true</false");
        setAddActionButton();
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_main, viewGroup, false);
        ((ActivityMain) getActivity()).setToolbarVisibleState(true, false, true, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        onAction(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        setMainSearch();
    }

    private void setMainSearch() {
        this.mEtKeyword.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.main.FragmentMain.6
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                FragmentMain.this.handler.removeMessages(0);
                FragmentMain.this.handler.sendEmptyMessageDelayed(0, 1000L);
            }
        });
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
        for (BeanMain.Project_List project_List : beanMain.Project_List) {
            this.mAdapter.add(project_List);
        }
        this.mAdapter.add(HEADER_NAME);
        for (BeanMain.Shared_Project_List shared_Project_List : beanMain.Shared_Project_List) {
            this.mAdapter.add(shared_Project_List);
        }
        this.mAdapter.notifyDataSetChanged();
    }
}
