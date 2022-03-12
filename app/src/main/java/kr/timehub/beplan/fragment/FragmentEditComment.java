package kr.timehub.beplan.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.objects.BaseFragment;
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.BeanMultiPostFile;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanTaskDetail;
import kr.timehub.beplan.entry.plugin.BeanViewPager;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.http.HttpBindTempData;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentEditComment extends BaseFragment {
    private static final int REQ_EDIT_COMMENT = 97;
    private static final int REQ_MULTI_POST_FILE = 319;
    @BindView(R.id.base_vp)
    BaseFileViewPager mBaseVp;
    private BeanTaskDetail.Tasks_Comment mBean;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.ll_comment_contents)
    LinearLayout mLlCommentContents;
    @BindView(R.id.tv_add_img)
    TextView mTvAddImg;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    Unbinder unbinder;

    public static FragmentEditComment newInstance(BeanTaskDetail.Tasks_Comment tasks_Comment) {
        FragmentEditComment fragmentEditComment = new FragmentEditComment();
        fragmentEditComment.mBean = tasks_Comment;
        return fragmentEditComment;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_edit_comment, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        getActivity().setTitle("false</true</코멘트 수정</false</false");
        onAction();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void onAction() {
        this.mEtComment.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentEditComment.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() < 1) {
                    FragmentEditComment.this.mTvSave.setBackgroundResource(R.drawable.btn_gray_01);
                } else {
                    FragmentEditComment.this.mTvSave.setBackgroundResource(R.drawable.btn_grdt_01);
                }
            }
        });
        if (!TextUtils.isEmpty(this.mBean.Comment)) {
            this.mEtComment.setText(this.mBean.Comment);
        }
        if (this.mBean.CommentAddContentList != null && this.mBean.CommentAddContentList.size() > 0) {
            for (BeanTaskDetail.CommentAddContentList commentAddContentList : this.mBean.CommentAddContentList) {
                BeanViewPager beanViewPager = new BeanViewPager(commentAddContentList.ContentsPart, null, null, commentAddContentList.ContentsUrl, true);
                beanViewPager.setCACSEQ(commentAddContentList.CACSEQ);
                this.mBaseVp.add(beanViewPager);
            }
            if (this.mBaseVp.getAdapter() != null) {
                this.mBaseVp.getAdapter().notifyDataSetChanged();
            }
        }
    }

    private void httpPostSendComment() {
        ArrayList arrayList;
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String obj = this.mEtComment.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            Toast.makeText(getContext(), "댓글을 입력해 주세요", 0).show();
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = null;
        if (this.mBaseVp.getArray().size() > 0) {
            ArrayList arrayList5 = new ArrayList();
            ArrayList arrayList6 = new ArrayList();
            Iterator<BeanViewPager> it = this.mBaseVp.getArray().iterator();
            while (it.hasNext()) {
                BeanViewPager next = it.next();
                if (TextUtils.isEmpty(next.getServerUrl())) {
                    arrayList2.add(Integer.valueOf(next.getCACSEQ()));
                } else {
                    arrayList5.add(next.getServerUrl());
                    arrayList6.add(next.getType());
                }
            }
            arrayList4 = arrayList5;
            arrayList = arrayList6;
        } else {
            arrayList = null;
        }
        if (this.mBean.CommentAddContentList.size() > 0) {
            for (BeanTaskDetail.CommentAddContentList commentAddContentList : this.mBean.CommentAddContentList) {
                boolean z = true;
                Iterator it2 = arrayList2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (commentAddContentList.CACSEQ == ((Integer) it2.next()).intValue()) {
                            z = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (z) {
                    arrayList3.add(Integer.valueOf(commentAddContentList.CACSEQ));
                }
            }
        }
        beplanOkHttpClient.SendComment(getContext(), 97, String.valueOf(this.mBean.TCSEQ), String.valueOf(this.mBean.TSEQ), obj, arrayList4, null, arrayList, arrayList3, null, this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 97) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                }
            } else if (intExtra == REQ_MULTI_POST_FILE) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    for (String str3 : ((BeanMultiPostFile) gson.fromJson(str, (Class<Object>) BeanMultiPostFile.class)).ResultFilesResponse) {
                        if (str3.contains("@")) {
                            String substring = str3.substring(0, str3.indexOf("@"));
                            this.mBaseVp.add(new BeanViewPager("I", "", str3, HttpBindTempData.PostFileCommentImgUrl + substring, true));
                        }
                    }
                } else {
                    Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                }
                if (this.mBaseVp.getAdapter() != null) {
                    this.mBaseVp.getAdapter().notifyDataSetChanged();
                }
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            return;
        }
        if (i == 100 || intent != null) {
            if (this.mBaseVp.size() < 1) {
                this.mLlCommentContents.setVisibility(0);
                this.mBaseVp.initView(new BaseFileViewPager.IOnItemDeletedListener() { // from class: kr.timehub.beplan.fragment.FragmentEditComment.2
                    @Override // kr.timehub.beplan.base.widgets.BaseFileViewPager.IOnItemDeletedListener
                    public void submitItemDeleted(int i3) {
                        if (i3 < 1) {
                            FragmentEditComment.this.mLlCommentContents.setVisibility(8);
                        }
                    }
                });
                this.mBaseVp.attachButtonLeft(this.mIvLeft);
                this.mBaseVp.attachButtonRight(this.mIvRight);
            }
            ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            ArrayList<String> arrayList = new ArrayList<>();
            Iterator it = parcelableArrayListExtra.iterator();
            while (it.hasNext()) {
                arrayList.add(((Image) it.next()).getPath());
            }
            httpPostMuiltiPostFile(arrayList);
            super.onActivityResult(i, i2, intent);
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
            beplanOkHttpClient.multiPostFileComment(getContext(), REQ_MULTI_POST_FILE, arrayList2, this);
        }
    }

    @OnClick({R.id.tv_save})
    public void submitSave() {
        httpPostSendComment();
    }

    @OnClick({R.id.tv_add_img})
    public void submitAddImg() {
        showPhotoSelectDialog();
    }

    private void showPhotoSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("사진 가져오기").setCancelable(true).setPositiveButton("카메라", new DialogInterface.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentEditComment.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.callCamera(FragmentEditComment.this);
                dialogInterface.dismiss();
            }
        }).setNegativeButton("갤러리", new DialogInterface.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentEditComment.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.callImagePicker(FragmentEditComment.this);
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setOnShowListener(new DialogInterface.OnShowListener() { // from class: kr.timehub.beplan.fragment.FragmentEditComment.5
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
