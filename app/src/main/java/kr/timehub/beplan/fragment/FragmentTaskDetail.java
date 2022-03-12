package kr.timehub.beplan.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.naver.temy123.baseproject.base.Widgets.BaseViewHolder;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.activity.ActivityViewContents;
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.BaseLinearLayoutManager;
import kr.timehub.beplan.base.widgets.BaseMainToolBar;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogCommon;
import kr.timehub.beplan.entry.BeanMultiPostFile;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanTaskDetail;
import kr.timehub.beplan.entry.plugin.BeanViewPager;
import kr.timehub.beplan.fragment.FragmentNewTask;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.http.HttpBindTempData;
import kr.timehub.beplan.utils.Comm_Prefs;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentTaskDetail extends BaseFragment implements BaseRecyclerViewAdapterInterface, BaseMainToolBar.OnToolbarClickListener {
    private static final int PICTURE_REQUEST_CODE = 200;
    private static final int REQ_DELETETASKCOMMENT = 646;
    BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_comment_viewpager)
    BaseFileViewPager mBaseCommentViewpager;
    @BindView(R.id.base_rv)
    RecyclerView mBaseRv;
    private BeanTaskDetail mBeanTask;
    @BindView(R.id.btn_upload)
    ImageButton mBtnUpload;
    @BindView(R.id.btn_write)
    ImageButton mBtnWrite;
    private int mCGSEQ;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.iv_comment_left)
    ImageView mIvCommentLeft;
    @BindView(R.id.iv_comment_right)
    ImageView mIvCommentRight;
    @BindView(R.id.ll_comment_contents)
    LinearLayout mLlCommentContents;
    private String mMakeId;
    private int mPSEQ;
    private String mRunnerId;
    private int mTSEQ;
    private String mTitle;
    Unbinder unbinder;
    private final int REQ_TASK_DETAIL = 1;
    private final int REQ_TASK_SEND_COMMENT = 2;
    private final int REQ_TASK_STATE = 3;
    private final int REQ_DELETE_TASK = 4;
    private final int REQ_MULTI_POST_FILE = 5;
    private Handler mainThreadHandler = new Handler();
    private int mImageLimit = 2;
    private boolean IsSendComment = false;
    private boolean isCheck = false;
    private boolean isBack = false;
    private String mTaskState = null;
    private final int TYPE_HEADER_TITLE = 0;
    private final int TYPE_HEADER_CONTENTS = 1;
    private final int TYPE_HEADER_LINK = 2;
    private final int TYPE_HEADER_RUNNER = 3;
    private final int TYPE_ITEM = 4;
    private boolean doNotWork = false;

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarAddClicked(View view) {
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarDrawerClicked(View view) {
    }

    public static final FragmentTaskDetail newInstance(int i, int i2, int i3, String str) {
        FragmentTaskDetail fragmentTaskDetail = new FragmentTaskDetail();
        fragmentTaskDetail.mPSEQ = i;
        fragmentTaskDetail.mCGSEQ = i2;
        fragmentTaskDetail.mTSEQ = i3;
        fragmentTaskDetail.mTitle = str;
        return fragmentTaskDetail;
    }

    private void httpPostTaskDetail() {
        new BeplanOkHttpClient().TaskDetail(getContext(), 1, String.valueOf(this.mTSEQ), this);
    }

    private void httpPostSendComment() {
        ArrayList arrayList;
        if (this.mEtKeyword.getText().length() > 0) {
            BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
            ArrayList arrayList2 = null;
            if (this.mBaseCommentViewpager.getArray().size() > 0) {
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                Iterator<BeanViewPager> it = this.mBaseCommentViewpager.getArray().iterator();
                while (it.hasNext()) {
                    BeanViewPager next = it.next();
                    arrayList3.add(next.getServerUrl());
                    arrayList4.add(next.getType());
                }
                arrayList2 = arrayList3;
                arrayList = arrayList4;
            } else {
                arrayList = null;
            }
            this.mBaseCommentViewpager.clear();
            this.mLlCommentContents.setVisibility(8);
            beplanOkHttpClient.SendComment(getContext(), 2, "0", String.valueOf(this.mTSEQ), this.mEtKeyword.getText().toString(), arrayList2, null, arrayList, null, null, this);
            this.mEtKeyword.setText("");
            Utils.hideKeyboard(getContext(), this.mEtKeyword);
            return;
        }
        Toast.makeText(getContext(), "댓글을 입력해주세요", 0).show();
    }

    public void httpPostUptTaskStateSuccess() {
        new BeplanOkHttpClient().UptTaskStateSuccess(getContext(), 3, String.valueOf(this.mTSEQ), this);
    }

    public void httpPostDeleteTask() {
        new BeplanOkHttpClient().DeleteTask(getContext(), 4, String.valueOf(this.mTSEQ), Comm_Prefs.getInstance(getContext()).getUserId(), this);
    }

    public void httpPostDeleteComment(int i) {
        new BeplanOkHttpClient().deleteTaskComment(getContext(), REQ_DELETETASKCOMMENT, i, this);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return BaseViewHolder.newInstance(getContext(), R.layout.header_task_detail_title, viewGroup);
        }
        if (i == 1) {
            return BaseViewHolder.newInstance(getContext(), R.layout.header_task_detail_contents, viewGroup);
        }
        if (i == 2) {
            return BaseViewHolder.newInstance(getContext(), R.layout.header_task_detail_link, viewGroup);
        }
        if (i == 3) {
            return BaseViewHolder.newInstance(getContext(), R.layout.header_task_detail_runner, viewGroup);
        }
        return BaseViewHolder.newInstance(getContext(), R.layout.listitem_project_detail, viewGroup);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        switch (getItemViewType(i)) {
            case 0:
                onBindViewHolderTitle(baseViewHolder, i);
                return;
            case 1:
                onBindViewHolderContents(baseViewHolder, i);
                return;
            case 2:
                onBindViewHolderLink(baseViewHolder, i);
                return;
            case 3:
                onBindViewHolderRunner(baseViewHolder, i);
                return;
            case 4:
                onBindViewHolderComments(baseViewHolder, i);
                return;
            default:
                return;
        }
    }

    private void onBindViewHolderTitle(BaseViewHolder baseViewHolder, int i) {
        final BeanTaskDetail beanTaskDetail = (BeanTaskDetail) this.mAdapter.get(i);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_check);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_business);
        final Comm_Prefs instance = Comm_Prefs.getInstance(getContext());
        ((TextView) baseViewHolder.getView(R.id.tv_business_title)).setText(beanTaskDetail.TaskTitle);
        if (beanTaskDetail.IsFinish) {
            this.isCheck = true;
        } else {
            this.isCheck = false;
        }
        if (this.isCheck) {
            imageView.setImageResource(R.drawable.btn_check_on);
        } else {
            imageView.setImageResource(R.drawable.btn_check_off);
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!TextUtils.isEmpty(beanTaskDetail.RunnerID) && Integer.parseInt(beanTaskDetail.RunnerID) == Integer.parseInt(instance.getUserId())) {
                    FragmentTaskDetail.this.httpPostUptTaskStateSuccess();
                }
            }
        });
        String str = "";
        if (beanTaskDetail.Tasks_Comment != null) {
            Collections.sort(beanTaskDetail.Tasks_Comment, new Comparator<BeanTaskDetail.Tasks_Comment>() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.2
                public int compare(BeanTaskDetail.Tasks_Comment tasks_Comment, BeanTaskDetail.Tasks_Comment tasks_Comment2) {
                    if (tasks_Comment2.TCSEQ > tasks_Comment.TCSEQ) {
                        return 1;
                    }
                    return tasks_Comment2.TCSEQ < tasks_Comment.TCSEQ ? -1 : 0;
                }
            });
            Iterator<BeanTaskDetail.Tasks_Comment> it = beanTaskDetail.Tasks_Comment.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                BeanTaskDetail.Tasks_Comment next = it.next();
                if (TextUtils.equals(next.Comment_Type, "C")) {
                    str = next.Comment;
                    break;
                }
            }
        }
        textView.setText(str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void onBindViewHolderContents(BaseViewHolder baseViewHolder, int i) {
        char c;
        int i2;
        final BeanTaskDetail.ContentsUrl contentsUrl = (BeanTaskDetail.ContentsUrl) this.mAdapter.get(i);
        String str = contentsUrl.ContentsUrl;
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_contents);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_more);
        String str2 = contentsUrl.ContentsPart;
        int hashCode = str2.hashCode();
        if (hashCode != 73) {
            switch (hashCode) {
                case 76:
                    if (str2.equals("L")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 77:
                    if (str2.equals("M")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } else {
            if (str2.equals("I")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                i2 = R.drawable.img_thumnail_camera;
                break;
            case 1:
                i2 = R.drawable.img_thumnail_video;
                break;
            case 2:
                i2 = R.drawable.img_thumnail_link;
                break;
            default:
                i2 = 0;
                break;
        }
        Glide.with(getContext()).asBitmap().load(str).apply(new RequestOptions().placeholder(i2)).into(imageView);
        if (contentsUrl.isMore()) {
            imageView2.setVisibility(0);
        } else {
            imageView2.setVisibility(8);
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ArrayList<String> arrayList = new ArrayList<>();
                for (BeanTaskDetail.ContentsUrl contentsUrl2 : contentsUrl.getContentList()) {
                    String str3 = "";
                    if (TextUtils.equals(contentsUrl2.ContentsPart, "I")) {
                        str3 = contentsUrl2.ContentsUrl;
                    } else if (TextUtils.equals(contentsUrl2.ContentsPart, "L")) {
                        str3 = "";
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        arrayList.add(str3);
                    }
                }
                Intent intent = new Intent(FragmentTaskDetail.this.getContext(), ActivityViewContents.class);
                intent.putStringArrayListExtra(ActivityViewContents.EXTRA_IMAGES, arrayList);
                intent.putExtra(ActivityViewContents.EXTRA_SELECT, contentsUrl.getPosition());
                FragmentTaskDetail.this.startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FragmentTaskDetail.this.mImageLimit += 2;
                FragmentTaskDetail.this.setTaskDetail(FragmentTaskDetail.this.mBeanTask);
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void onBindViewHolderLink(BaseViewHolder baseViewHolder, int i) {
        char c;
        int i2;
        final BeanTaskDetail.ContentsUrl contentsUrl = (BeanTaskDetail.ContentsUrl) this.mAdapter.get(i);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_link);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_url);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_more);
        String str = contentsUrl.ThumnailUrl;
        String str2 = contentsUrl.ContentsPart;
        int hashCode = str2.hashCode();
        if (hashCode != 73) {
            switch (hashCode) {
                case 76:
                    if (str2.equals("L")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 77:
                    if (str2.equals("M")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } else {
            if (str2.equals("I")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                i2 = R.drawable.img_thumnail_camera;
                break;
            case 1:
                i2 = R.drawable.img_thumnail_video;
                break;
            case 2:
                i2 = R.drawable.img_thumnail_link;
                break;
            default:
                i2 = 0;
                break;
        }
        Glide.with(getContext()).asBitmap().load(str).apply(new RequestOptions().placeholder(i2)).into(imageView);
        if (contentsUrl.isMore()) {
            imageView2.setVisibility(0);
        } else {
            imageView2.setVisibility(8);
        }
        textView.setText(contentsUrl.ContentsUrl);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FragmentTaskDetail.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(contentsUrl.ContentsUrl)));
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FragmentTaskDetail.this.mImageLimit += 2;
                FragmentTaskDetail.this.setTaskDetail(FragmentTaskDetail.this.mBeanTask);
            }
        });
    }

    private void onBindViewHolderRunner(BaseViewHolder baseViewHolder, int i) {
        BeanTaskDetail beanTaskDetail = (BeanTaskDetail) this.mAdapter.get(i);
        final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_photo);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_name);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_me);
        final ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_sender_photo);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_sender_name);
        ImageView imageView4 = (ImageView) baseViewHolder.getView(R.id.iv_sender_me);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_date);
        Comm_Prefs instance = Comm_Prefs.getInstance(getContext());
        if (TextUtils.equals(beanTaskDetail.RunnerID, instance.getUserId())) {
            imageView2.setVisibility(0);
        } else {
            imageView2.setVisibility(8);
        }
        if (TextUtils.equals(beanTaskDetail.MaKeID, instance.getUserId())) {
            imageView4.setVisibility(0);
        } else {
            imageView4.setVisibility(8);
        }
        if (TextUtils.isEmpty(beanTaskDetail.ProfileImgUrl)) {
            Glide.with(getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView);
        } else {
            Glide.with(getContext()).load(beanTaskDetail.ProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.7
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with(FragmentTaskDetail.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
                    return false;
                }
            }).into(imageView);
        }
        textView.setText(beanTaskDetail.RealName);
        String str = "";
        new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy. MM. dd");
        if (!TextUtils.isEmpty(beanTaskDetail.SDate)) {
            str = simpleDateFormat.format(Utils.ConvertDate(beanTaskDetail.SDate));
        }
        if (!TextUtils.isEmpty(beanTaskDetail.EDate)) {
            Date ConvertDate = Utils.ConvertDate(beanTaskDetail.EDate);
            str = (str + " ~ ") + simpleDateFormat.format(ConvertDate);
        }
        if (!TextUtils.isEmpty(str)) {
            textView3.setVisibility(0);
            textView3.setText(str);
        } else {
            textView3.setVisibility(8);
        }
        if (!TextUtils.isEmpty(beanTaskDetail.MakerProfileImgUrl)) {
            Glide.with(getContext()).load(beanTaskDetail.MakerProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.8
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Glide.with(FragmentTaskDetail.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView3);
                    return false;
                }
            }).into(imageView3);
        } else {
            Glide.with(getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(imageView3);
        }
        textView2.setText(beanTaskDetail.MakerRealName);
    }

    private void onBindViewHolderComments(BaseViewHolder baseViewHolder, int i) {
        final BeanTaskDetail.Tasks_Comment tasks_Comment = (BeanTaskDetail.Tasks_Comment) this.mAdapter.get(i);
        tasks_Comment.TSEQ = this.mTSEQ;
        String str = tasks_Comment.RealName;
        String str2 = tasks_Comment.Comment;
        String str3 = tasks_Comment.RegDate;
        Date ConvertDate = Utils.ConvertDate(tasks_Comment.RegDate);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_del_cmt);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_edit_cmt);
        if (tasks_Comment.IsDelete) {
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(8);
        }
        if (tasks_Comment.IsModify) {
            imageView2.setVisibility(0);
        } else {
            imageView2.setVisibility(8);
        }
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R.id.ll_comment_contents);
        ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_first);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.rl_second_img);
        ImageView imageView4 = (ImageView) baseViewHolder.getView(R.id.iv_second);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_img_size);
        LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R.id.ll_the_plural);
        ImageView imageView5 = (ImageView) baseViewHolder.getView(R.id.iv_one_thing);
        if (tasks_Comment.CommentAddContentList == null || tasks_Comment.CommentAddContentList.size() <= 0) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
            if (tasks_Comment.CommentAddContentList.size() == 1) {
                relativeLayout.setVisibility(8);
                linearLayout2.setVisibility(8);
                imageView5.setVisibility(0);
                Iterator<BeanTaskDetail.CommentAddContentList> it = tasks_Comment.CommentAddContentList.iterator();
                if (it.hasNext()) {
                    Glide.with(getContext()).asBitmap().load(it.next().ContentsUrl).into(imageView5);
                }
                imageView5.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.9
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (BeanTaskDetail.CommentAddContentList commentAddContentList : tasks_Comment.CommentAddContentList) {
                            arrayList.add(commentAddContentList.ContentsUrl);
                        }
                        Intent intent = new Intent(FragmentTaskDetail.this.getContext(), ActivityViewContents.class);
                        intent.putStringArrayListExtra(ActivityViewContents.EXTRA_IMAGES, arrayList);
                        FragmentTaskDetail.this.startActivity(intent);
                    }
                });
            } else {
                linearLayout2.setVisibility(0);
                imageView5.setVisibility(8);
                relativeLayout.setVisibility(0);
                if (tasks_Comment.CommentAddContentList.size() == 2) {
                    textView.setVisibility(8);
                    int i2 = 0;
                    while (true) {
                        if (i2 >= tasks_Comment.CommentAddContentList.size()) {
                            break;
                        }
                        BeanTaskDetail.CommentAddContentList commentAddContentList = tasks_Comment.CommentAddContentList.get(i2);
                        if (i2 != 0) {
                            Glide.with(getContext()).asBitmap().load(commentAddContentList.ContentsUrl).into(imageView4);
                            break;
                        } else {
                            Glide.with(getContext()).asBitmap().load(commentAddContentList.ContentsUrl).into(imageView3);
                            i2++;
                        }
                    }
                    imageView3.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.10
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            ArrayList<String> arrayList = new ArrayList<>();
                            for (BeanTaskDetail.CommentAddContentList commentAddContentList2 : tasks_Comment.CommentAddContentList) {
                                arrayList.add(commentAddContentList2.ContentsUrl);
                            }
                            Intent intent = new Intent(FragmentTaskDetail.this.getContext(), ActivityViewContents.class);
                            intent.putStringArrayListExtra(ActivityViewContents.EXTRA_IMAGES, arrayList);
                            FragmentTaskDetail.this.startActivity(intent);
                        }
                    });
                    imageView4.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.11
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            ArrayList<String> arrayList = new ArrayList<>();
                            for (BeanTaskDetail.CommentAddContentList commentAddContentList2 : tasks_Comment.CommentAddContentList) {
                                arrayList.add(commentAddContentList2.ContentsUrl);
                            }
                            Intent intent = new Intent(FragmentTaskDetail.this.getContext(), ActivityViewContents.class);
                            intent.putStringArrayListExtra(ActivityViewContents.EXTRA_IMAGES, arrayList);
                            intent.putExtra(ActivityViewContents.EXTRA_SELECT, 1);
                            FragmentTaskDetail.this.startActivity(intent);
                        }
                    });
                } else {
                    textView.setVisibility(0);
                    int i3 = 0;
                    while (true) {
                        if (i3 >= tasks_Comment.CommentAddContentList.size()) {
                            break;
                        }
                        BeanTaskDetail.CommentAddContentList commentAddContentList2 = tasks_Comment.CommentAddContentList.get(i3);
                        if (i3 != 0) {
                            Glide.with(getContext()).asBitmap().load(commentAddContentList2.ContentsUrl).into(imageView4);
                            break;
                        } else {
                            Glide.with(getContext()).asBitmap().load(commentAddContentList2.ContentsUrl).into(imageView3);
                            i3++;
                        }
                    }
                    textView.setText(String.format("+ %d 장", Integer.valueOf(tasks_Comment.CommentAddContentList.size() - 2)));
                    imageView3.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.12
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            ArrayList<String> arrayList = new ArrayList<>();
                            for (BeanTaskDetail.CommentAddContentList commentAddContentList3 : tasks_Comment.CommentAddContentList) {
                                arrayList.add(commentAddContentList3.ContentsUrl);
                            }
                            Intent intent = new Intent(FragmentTaskDetail.this.getContext(), ActivityViewContents.class);
                            intent.putStringArrayListExtra(ActivityViewContents.EXTRA_IMAGES, arrayList);
                            FragmentTaskDetail.this.startActivity(intent);
                        }
                    });
                    textView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.13
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            ArrayList<String> arrayList = new ArrayList<>();
                            for (BeanTaskDetail.CommentAddContentList commentAddContentList3 : tasks_Comment.CommentAddContentList) {
                                arrayList.add(commentAddContentList3.ContentsUrl);
                            }
                            Intent intent = new Intent(FragmentTaskDetail.this.getContext(), ActivityViewContents.class);
                            intent.putStringArrayListExtra(ActivityViewContents.EXTRA_IMAGES, arrayList);
                            FragmentTaskDetail.this.startActivity(intent);
                        }
                    });
                }
            }
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FragmentTaskDetail.this.replaceFragment(R.id.base_container, FragmentEditComment.newInstance(tasks_Comment), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FragmentTaskDetail.this.showDeleteComment(tasks_Comment.TCSEQ);
            }
        });
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월 dd일");
        final ImageView imageView6 = (ImageView) baseViewHolder.getView(R.id.iv_sender_photo);
        Glide.with(getContext()).load(tasks_Comment.ProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.16
            public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                FragmentTaskDetail.this.mainThreadHandler.post(new Runnable() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.16.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Glide.with(FragmentTaskDetail.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView6);
                    }
                });
                return false;
            }
        }).into(imageView6);
        Calendar instance = Calendar.getInstance();
        instance.setTime(ConvertDate);
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        String str4 = "오전";
        if (i4 >= 12) {
            str4 = "오후";
            if (i4 > 12) {
                i4 -= 12;
            }
        }
        String format = String.format("%s %d시%d분", str4, Integer.valueOf(i4), Integer.valueOf(i5));
        baseViewHolder.setText(R.id.tv_sender_name, str);
        baseViewHolder.setText(R.id.tv_comment, str2);
        baseViewHolder.setText(R.id.tv_comment_date, simpleDateFormat.format(ConvertDate));
        baseViewHolder.setText(R.id.tv_comment_time, format);
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemCount() {
        return this.mAdapter.size();
    }

    @Override // com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface
    public int getItemViewType(int i) {
        if (this.mAdapter.get(i) instanceof BeanTaskDetail) {
            switch (((BeanTaskDetail) this.mAdapter.get(i)).getType()) {
                case 0:
                    return 0;
                case 1:
                    return 3;
                default:
                    return -1;
            }
        } else if (this.mAdapter.get(i) instanceof BeanTaskDetail.Tasks_Comment) {
            return 4;
        } else {
            if (!(this.mAdapter.get(i) instanceof BeanTaskDetail.ContentsUrl)) {
                return -1;
            }
            BeanTaskDetail.ContentsUrl contentsUrl = (BeanTaskDetail.ContentsUrl) this.mAdapter.get(i);
            if (TextUtils.equals(contentsUrl.ContentsPart, "I")) {
                return 1;
            }
            return TextUtils.equals(contentsUrl.ContentsPart, "L") ? 2 : -1;
        }
    }

    public void setTaskDetail(BeanTaskDetail beanTaskDetail) {
        int i;
        this.mRunnerId = beanTaskDetail.RunnerID;
        this.mMakeId = beanTaskDetail.MaKeID;
        this.mTaskState = beanTaskDetail.TaskState;
        this.mAdapter.clear();
        this.mBeanTask = beanTaskDetail;
        if (beanTaskDetail.Tasks_Comment != null) {
            Collections.sort(beanTaskDetail.Tasks_Comment, new Comparator<BeanTaskDetail.Tasks_Comment>() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.17
                public int compare(BeanTaskDetail.Tasks_Comment tasks_Comment, BeanTaskDetail.Tasks_Comment tasks_Comment2) {
                    if (tasks_Comment2.TCSEQ > tasks_Comment.TCSEQ) {
                        return 1;
                    }
                    return tasks_Comment2.TCSEQ < tasks_Comment.TCSEQ ? -1 : 0;
                }
            });
            BeanTaskDetail beanTaskDetail2 = new BeanTaskDetail(beanTaskDetail);
            beanTaskDetail2.setType(0);
            this.mAdapter.add(beanTaskDetail2);
            if (this.mImageLimit >= beanTaskDetail.ContentsUrl.size()) {
                this.mImageLimit = beanTaskDetail.ContentsUrl.size();
            }
            int i2 = 0;
            for (int i3 = 0; i3 < this.mImageLimit; i3++) {
                BeanTaskDetail.ContentsUrl contentsUrl = beanTaskDetail.ContentsUrl.get(i3);
                contentsUrl.setContentList(beanTaskDetail.ContentsUrl);
                if (TextUtils.equals(contentsUrl.ContentsPart, "I")) {
                    contentsUrl.setPosition(i2);
                    i2++;
                }
                contentsUrl.setMore(false);
                if (i3 >= this.mImageLimit - 1 && beanTaskDetail.ContentsUrl.size() > this.mImageLimit) {
                    contentsUrl.setMore(true);
                }
                this.mAdapter.add(contentsUrl);
            }
            BeanTaskDetail beanTaskDetail3 = new BeanTaskDetail(beanTaskDetail);
            beanTaskDetail3.setType(1);
            this.mAdapter.add(beanTaskDetail3);
            i = this.mAdapter.size();
            for (BeanTaskDetail.Tasks_Comment tasks_Comment : beanTaskDetail.Tasks_Comment) {
                if (TextUtils.equals(tasks_Comment.Comment_Type, "R")) {
                    this.mAdapter.add(tasks_Comment);
                }
            }
        } else {
            i = -1;
        }
        this.mAdapter.notifyDataSetChanged();
        if (this.isBack) {
            if (beanTaskDetail.IsRequestOrTask != 1) {
                getActivity().onBackPressed();
                return;
            }
            this.isBack = false;
        }
        if (this.IsSendComment) {
            if (i != -1) {
                this.mBaseRv.scrollToPosition(i);
            }
            this.IsSendComment = false;
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 1) {
                BeanTaskDetail beanTaskDetail = (BeanTaskDetail) gson.fromJson(str, (Class<Object>) BeanTaskDetail.class);
                setTaskDetail(beanTaskDetail);
                ActivityMain activityMain = (ActivityMain) getActivity();
                if (beanTaskDetail.IsModify || beanTaskDetail.IsDelete) {
                    FragmentActivity activity = getActivity();
                    activity.setTitle("false</true</" + this.mTitle + "</false</true");
                } else {
                    FragmentActivity activity2 = getActivity();
                    activity2.setTitle("false</true</" + this.mTitle + "</false</false");
                }
                activityMain.setOnToolbarListener(this);
            } else if (intExtra == 2) {
                Toast.makeText(getContext(), "댓글달기 성공", 0).show();
                this.IsSendComment = true;
                httpPostTaskDetail();
            } else if (intExtra == 3) {
                httpPostTaskDetail();
            } else if (intExtra == 4) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    Toast.makeText(getContext(), "성공적으로 삭제되었습니다", 0).show();
                } else {
                    Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                }
                getActivity().onBackPressed();
            } else if (intExtra == 5) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    BeanMultiPostFile beanMultiPostFile = (BeanMultiPostFile) gson.fromJson(str, (Class<Object>) BeanMultiPostFile.class);
                    if (beanMultiPostFile.ResultFilesResponse != null && beanMultiPostFile.ResultFilesResponse.size() > 0) {
                        for (String str3 : beanMultiPostFile.ResultFilesResponse) {
                            if (str3.contains("@")) {
                                String substring = str3.substring(0, str3.indexOf("@"));
                                this.mBaseCommentViewpager.add(new BeanViewPager("I", "", str3, HttpBindTempData.PostFileCommentImgUrl + substring, true));
                            }
                        }
                    } else if (this.mBaseCommentViewpager.size() < 1) {
                        this.mLlCommentContents.setVisibility(8);
                    }
                } else {
                    Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                    if (this.mBaseCommentViewpager.size() < 1) {
                        this.mLlCommentContents.setVisibility(8);
                    }
                }
                if (this.mBaseCommentViewpager.getAdapter() != null) {
                    this.mBaseCommentViewpager.getAdapter().notifyDataSetChanged();
                }
            } else if (intExtra == REQ_DELETETASKCOMMENT) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    httpPostTaskDetail();
                } else {
                    Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                }
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_task_detail, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        httpPostTaskDetail();
        initAdapter();
        this.mLlCommentContents.setVisibility(8);
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter2(getContext());
        this.mAdapter.setAction(this);
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(getContext());
        baseLinearLayoutManager.setEnbleVerticalScrolling(true);
        this.mBaseRv.setLayoutManager(baseLinearLayoutManager);
        this.mBaseRv.setAdapter(this.mAdapter);
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarCloseClicked(View view) {
        getActivity().onBackPressed();
    }

    @Override // kr.timehub.beplan.base.widgets.BaseMainToolBar.OnToolbarClickListener
    public void onToolbarMenuClicked(View view) {
        if (this.mBeanTask != null) {
            PopupMenu popupMenu = new PopupMenu(getContext(), view, 5);
            if (this.mBeanTask.IsModify) {
                popupMenu.getMenu().add("수정");
            }
            if (this.mBeanTask.IsDelete) {
                popupMenu.getMenu().add("삭제");
            }
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.18
                @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem menuItem) {
                    char c;
                    int i;
                    String charSequence = menuItem.getTitle().toString();
                    int hashCode = charSequence.hashCode();
                    if (hashCode != 1580303) {
                        if (hashCode == 1591549 && charSequence.equals("수정")) {
                            c = 0;
                        }
                        c = 65535;
                    } else {
                        if (charSequence.equals("삭제")) {
                            c = 1;
                        }
                        c = 65535;
                    }
                    switch (c) {
                        case 0:
                            try {
                                i = Integer.parseInt(FragmentTaskDetail.this.mBeanTask.RunnerID);
                            } catch (NumberFormatException e) {
                                Log.e("FragmentTaskDetail", e.getMessage());
                                i = 0;
                            }
                            FragmentTaskDetail.this.replaceFragment(R.id.base_container, FragmentNewTask.newInstance(FragmentTaskDetail.this.mPSEQ, FragmentTaskDetail.this.mCGSEQ, FragmentTaskDetail.this.mTSEQ, i, true, new FragmentNewTask.onBackPressedListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.18.1
                                @Override // kr.timehub.beplan.fragment.FragmentNewTask.onBackPressedListener
                                public void onBackPressed(String str, String str2, String str3, boolean z) {
                                    FragmentTaskDetail.this.isBack = true;
                                }
                            }), true, R.anim.anim_from_right_to_here_fade_in, R.anim.anim_from_here_to_left_fade_out, R.anim.anim_from_left_to_here_fade_in, R.anim.anim_from_here_to_right_fade_out);
                            break;
                        case 1:
                            FragmentTaskDetail.this.showDeleteTaskDialog();
                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }

    public void showDeleteTaskDialog() {
        Comm_Prefs instance = Comm_Prefs.getInstance(getContext());
        if (!TextUtils.isEmpty(this.mRunnerId) && TextUtils.equals(this.mRunnerId, instance.getUserId())) {
            DialogCommon.newInstance(getContext(), getString(R.string.dialog_delete_task_title), getString(R.string.dialog_delete_task_contents), new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.19
                @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
                public void clickClose(DialogCommon dialogCommon) {
                }

                @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
                public void onPositive(DialogCommon dialogCommon, Button button) {
                    FragmentTaskDetail.this.httpPostDeleteTask();
                    dialogCommon.dismiss();
                }

                @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
                public void onNegative(DialogCommon dialogCommon, Button button) {
                    dialogCommon.dismiss();
                }

                @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
                public void onCreated(DialogCommon dialogCommon) {
                    dialogCommon.getmBtnPositive().setText(FragmentTaskDetail.this.getString(R.string.btn_yes));
                    dialogCommon.getmBtnNegative().setText(FragmentTaskDetail.this.getString(R.string.btn_no));
                }
            }).show();
        }
    }

    private void httpPostMuiltiPostFile(ArrayList<String> arrayList) {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        ArrayList<File> arrayList2 = new ArrayList<>();
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(new File(it.next()));
            }
            beplanOkHttpClient.multiPostFileComment(getContext(), 5, arrayList2, this);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != -1) {
            return;
        }
        if (i == 100 || intent != null) {
            if (this.mBaseCommentViewpager.size() < 1) {
                this.mLlCommentContents.setVisibility(0);
                this.mBaseCommentViewpager.initView(new BaseFileViewPager.IOnItemDeletedListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.20
                    @Override // kr.timehub.beplan.base.widgets.BaseFileViewPager.IOnItemDeletedListener
                    public void submitItemDeleted(int i3) {
                        if (i3 < 1) {
                            FragmentTaskDetail.this.mLlCommentContents.setVisibility(8);
                        }
                    }
                });
                this.mBaseCommentViewpager.attachButtonLeft(this.mIvCommentLeft);
                this.mBaseCommentViewpager.attachButtonRight(this.mIvCommentRight);
            }
            ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            ArrayList<String> arrayList = new ArrayList<>();
            Iterator it = parcelableArrayListExtra.iterator();
            while (it.hasNext()) {
                arrayList.add(((Image) it.next()).getPath());
            }
            httpPostMuiltiPostFile(arrayList);
        }
    }

    @OnClick({R.id.btn_write})
    public void submitWrite() {
        httpPostSendComment();
    }

    @OnClick({R.id.btn_upload})
    public void submitImgUpload() {
        showPhotoSelectDialog();
    }

    public void showDeleteComment(final int i) {
        DialogCommon.newInstance(getContext(), "댓글 삭제", "댓글을 삭제하시겠습니까?", new DialogCommon.DialogCommonListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.21
            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void clickClose(DialogCommon dialogCommon) {
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onPositive(DialogCommon dialogCommon, Button button) {
                FragmentTaskDetail.this.httpPostDeleteComment(i);
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onNegative(DialogCommon dialogCommon, Button button) {
                dialogCommon.dismiss();
            }

            @Override // kr.timehub.beplan.dialog.DialogCommon.DialogCommonListener
            public void onCreated(DialogCommon dialogCommon) {
                dialogCommon.getmBtnPositive().setText(FragmentTaskDetail.this.getString(R.string.btn_yes));
                dialogCommon.getmBtnNegative().setText(FragmentTaskDetail.this.getString(R.string.btn_no));
            }
        }).show();
    }

    private void showPhotoSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("사진 가져오기").setCancelable(true).setPositiveButton("카메라", new DialogInterface.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.23
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.callCamera(FragmentTaskDetail.this);
                dialogInterface.dismiss();
            }
        }).setNegativeButton("갤러리", new DialogInterface.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.22
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.callImagePicker(FragmentTaskDetail.this);
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setOnShowListener(new DialogInterface.OnShowListener() { // from class: kr.timehub.beplan.fragment.FragmentTaskDetail.24
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                alertDialog.getButton(-2).setTextColor(ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK));
                alertDialog.getButton(-1).setTextColor(ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK));
            }
        });
        create.show();
    }
}
