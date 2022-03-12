package kr.timehub.beplan.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter2;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import cz.msebera.android.httpclient.HttpStatus;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.DropdownAdapter.DropdownAdapterRepSelect;
import kr.timehub.beplan.base.widgets.BaseFileViewPager;
import kr.timehub.beplan.base.widgets.Button;
import kr.timehub.beplan.base.widgets.EditText;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.dialog.DialogUrlLink;
import kr.timehub.beplan.entry.BeanMultiPostFile;
import kr.timehub.beplan.entry.plugin.BeanAddProjectMember;
import kr.timehub.beplan.entry.plugin.BeanCommon;
import kr.timehub.beplan.entry.plugin.BeanPostFile;
import kr.timehub.beplan.entry.plugin.BeanViewPager;
import kr.timehub.beplan.http.BeplanOkHttpClient;
import kr.timehub.beplan.http.HttpBindTempData;
import kr.timehub.beplan.utils.Utils;
import okhttp3.Call;

/* loaded from: classes.dex */
public class FragmentNewCategory2 extends BaseFragment {
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int REQ_FILE_MULTIFILE = 279;
    private BaseRecyclerViewAdapter2 mAdapter;
    @BindView(R.id.base_vp)
    BaseFileViewPager mBaseVp;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.et_business_comment)
    EditText mEtBusinessComment;
    @BindView(R.id.et_business_content)
    EditText mEtBusinessContent;
    @BindView(R.id.et_categoty_title)
    EditText mEtCategotyTitle;
    private Uri mImageUri;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.iv_limit_switch)
    ImageView mIvLimitSwitch;
    @BindView(R.id.iv_me)
    ImageView mIvMe;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.ll_cancel)
    LinearLayout mLlCancel;
    @BindView(R.id.ll_select_rep)
    LinearLayout mLlSelectRep;
    private int mPSEQ;
    private String mPhotoPath;
    @BindView(R.id.rl_rep)
    RelativeLayout mRlRep;
    private List<BeanAddProjectMember.List> mRunnerList;
    @BindView(R.id.sp_dropdown)
    Spinner mSpDropdown;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_link)
    TextView mTvLink;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_photo)
    TextView mTvPhoto;
    @BindView(R.id.tv_rep)
    TextView mTvRep;
    @BindView(R.id.tv_term_finish_date)
    TextView mTvTermFinishDate;
    @BindView(R.id.tv_term_start_date)
    TextView mTvTermStartDate;
    @BindView(R.id.tv_video)
    TextView mTvVideo;
    Unbinder unbinder;
    private final int REQ_ADD_CATEGORY = 11;
    private final int REQ_PROJECT_MEMER_LIST = 2;
    private final int REQ_FILE_POSTFILE = 3;
    private boolean limit_switch = false;
    private int CGTitleLength = 0;
    private int TTitleLength = 0;
    private int TContentsLength = 0;
    Calendar mStartCal = Calendar.getInstance();
    Calendar mFinishCal = Calendar.getInstance();
    private int selectMember = -1;
    private String currentImageType = "";
    private boolean mIsCamera = false;
    private int mSelectMember = -1;

    public static FragmentNewCategory2 newInstance(int i) {
        FragmentNewCategory2 fragmentNewCategory2 = new FragmentNewCategory2();
        fragmentNewCategory2.mPSEQ = i;
        return fragmentNewCategory2;
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_new_category, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        getActivity().setTitle("false</true</카테고리 만들기</false</false");
        this.mTvVideo.setVisibility(8);
        httpPostAddProjectMember();
        onAction();
    }

    private void showPhotoSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 2131755305);
        builder.setTitle("사진 가져오기").setCancelable(true).setPositiveButton("카메라", new DialogInterface.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.callCamera(FragmentNewCategory2.this);
                dialogInterface.dismiss();
            }
        }).setNegativeButton("갤러리", new DialogInterface.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.callImagePicker(FragmentNewCategory2.this);
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setOnShowListener(new DialogInterface.OnShowListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.3
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                alertDialog.getButton(-2).setTextColor(ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK));
                alertDialog.getButton(-1).setTextColor(ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK));
            }
        });
        create.show();
    }

    private void initAdapter(String str, String str2, String str3, String str4) {
        this.mBaseVp.attachButtonLeft(this.mIvLeft);
        this.mBaseVp.attachButtonRight(this.mIvRight);
        this.mBaseVp.add(new BeanViewPager(str, str2, str3, str4, true));
        if (this.mBaseVp.getAdapter() != null) {
            this.mBaseVp.getAdapter().notifyDataSetChanged();
        }
    }

    private void onAction() {
        this.mTvTermStartDate.setText(String.format("%04d. %02d. %02d", Integer.valueOf(this.mStartCal.get(1)), Integer.valueOf(this.mStartCal.get(2) + 1), Integer.valueOf(this.mStartCal.get(5))));
        this.mTvTermFinishDate.setText(String.format("%04d. %02d. %02d", Integer.valueOf(this.mFinishCal.get(1)), Integer.valueOf(this.mFinishCal.get(2) + 1), Integer.valueOf(this.mFinishCal.get(5))));
        this.mLlCancel.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FragmentNewCategory2.this.mLlSelectRep.setVisibility(8);
                FragmentNewCategory2.this.mRlRep.setVisibility(0);
                FragmentNewCategory2.this.mSpDropdown.setSelected(false);
                FragmentNewCategory2.this.mSpDropdown.setSelection(0);
                FragmentNewCategory2.this.mSelectMember = 0;
            }
        });
        this.mIvLimitSwitch.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (FragmentNewCategory2.this.limit_switch) {
                    FragmentNewCategory2.this.limit_switch = false;
                    FragmentNewCategory2.this.setDateImage();
                    return;
                }
                FragmentNewCategory2.this.limit_switch = true;
                FragmentNewCategory2.this.setDateImage();
            }
        });
        this.mTvLink.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DialogUrlLink.newInstance(FragmentNewCategory2.this.getContext(), new DialogUrlLink.DialogUrlLinkInterface() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.6.1
                    @Override // kr.timehub.beplan.dialog.DialogUrlLink.DialogUrlLinkInterface
                    public void onItemClicked(Dialog dialog, String str, Bitmap bitmap) {
                        FragmentNewCategory2.this.saveTempImage(str, bitmap);
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        this.mEtCategotyTitle.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.7
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                FragmentNewCategory2.this.CGTitleLength = charSequence.length();
                if (FragmentNewCategory2.this.CGTitleLength <= 0 || FragmentNewCategory2.this.TTitleLength <= 0) {
                    FragmentNewCategory2.this.mBtnSave.setBackgroundResource(R.drawable.btn_gray_02);
                } else {
                    FragmentNewCategory2.this.mBtnSave.setBackgroundResource(R.drawable.btn_grdt_02);
                }
            }
        });
        this.mEtBusinessContent.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.8
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                FragmentNewCategory2.this.TTitleLength = charSequence.length();
                if (FragmentNewCategory2.this.CGTitleLength <= 0 || FragmentNewCategory2.this.TTitleLength <= 0) {
                    FragmentNewCategory2.this.mBtnSave.setBackgroundResource(R.drawable.btn_gray_02);
                } else {
                    FragmentNewCategory2.this.mBtnSave.setBackgroundResource(R.drawable.btn_grdt_02);
                }
            }
        });
        this.mEtBusinessComment.addTextChangedListener(new TextWatcher() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.9
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                FragmentNewCategory2.this.TContentsLength = charSequence.length();
                if (FragmentNewCategory2.this.CGTitleLength <= 0 || FragmentNewCategory2.this.TTitleLength <= 0) {
                    FragmentNewCategory2.this.mBtnSave.setBackgroundResource(R.drawable.btn_gray_02);
                } else {
                    FragmentNewCategory2.this.mBtnSave.setBackgroundResource(R.drawable.btn_grdt_02);
                }
            }
        });
    }

    private void httpPostMakeTask() {
        ArrayList arrayList;
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str = "";
        String str2 = "";
        String str3 = "";
        if (this.limit_switch) {
            str2 = String.format("%04d-%02d-%02d", Integer.valueOf(this.mStartCal.get(1)), Integer.valueOf(this.mStartCal.get(2) + 1), Integer.valueOf(this.mStartCal.get(5)));
            str3 = String.format("%04d-%02d-%02d", Integer.valueOf(this.mFinishCal.get(1)), Integer.valueOf(this.mFinishCal.get(2) + 1), Integer.valueOf(this.mFinishCal.get(5)));
        }
        ArrayList arrayList2 = null;
        if (this.mBaseVp.getArray().size() > 0) {
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            Iterator<BeanViewPager> it = this.mBaseVp.getArray().iterator();
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
        if (this.mSelectMember > -1 && this.mRunnerList.size() > 0) {
            str = String.valueOf(this.mRunnerList.get(this.mSelectMember).MemberId);
        }
        String obj = this.mEtCategotyTitle.getText().toString();
        String obj2 = this.mEtBusinessContent.getText().toString();
        String obj3 = this.mEtBusinessComment.getText().toString();
        if (obj.length() < 1) {
            Toast.makeText(getContext(), getString(R.string.alert_plz_input_all_info), 0).show();
        } else if (obj2.length() < 1) {
            Toast.makeText(getContext(), getString(R.string.alert_plz_input_all_info), 0).show();
        } else if (TextUtils.isEmpty(str)) {
            Toast.makeText(getContext(), "담당자를 선택해 주세요", 0).show();
        } else {
            beplanOkHttpClient.MakeCategory(getContext(), 11, String.valueOf(this.mPSEQ), null, obj, obj2, str, str2, str3, obj3, null, arrayList2, arrayList, null, this);
        }
    }

    public void setDateImage() {
        if (this.limit_switch) {
            this.mIvLimitSwitch.setImageResource(R.drawable.btn_swtch_on);
            this.mTvTermStartDate.setBackgroundResource(R.drawable.input_bg);
            this.mTvTermStartDate.setTextColor(getResources().getColor(R.color.textColor));
            this.mTvTermFinishDate.setBackgroundResource(R.drawable.input_bg);
            this.mTvTermFinishDate.setTextColor(getResources().getColor(R.color.textColor));
            this.mTvTermFinishDate.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentNewCategory2.this.getContext(), new DatePickerDialog.OnDateSetListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.10.1
                        @Override // android.app.DatePickerDialog.OnDateSetListener
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            FragmentNewCategory2.this.mTvTermFinishDate.setText(String.format("%04d. %02d. %02d", Integer.valueOf(i), Integer.valueOf(i2 + 1), Integer.valueOf(i3)));
                            FragmentNewCategory2.this.mFinishCal.set(i, i2, i3);
                        }
                    }, FragmentNewCategory2.this.mFinishCal.get(1), FragmentNewCategory2.this.mFinishCal.get(2), FragmentNewCategory2.this.mFinishCal.get(5));
                    datePickerDialog.getDatePicker().setMinDate(FragmentNewCategory2.this.mStartCal.getTimeInMillis() - 1000);
                    if (FragmentNewCategory2.this.limit_switch) {
                        datePickerDialog.show();
                    }
                }
            });
            this.mTvTermStartDate.setOnClickListener(new View.OnClickListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.11
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(FragmentNewCategory2.this.getContext(), new DatePickerDialog.OnDateSetListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.11.1
                        @Override // android.app.DatePickerDialog.OnDateSetListener
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            int i4 = i2 + 1;
                            FragmentNewCategory2.this.mTvTermStartDate.setText(String.format("%04d. %02d. %02d", Integer.valueOf(i), Integer.valueOf(i4), Integer.valueOf(i3)));
                            FragmentNewCategory2.this.mStartCal.set(i, i2, i3);
                            if (FragmentNewCategory2.this.mStartCal.getTimeInMillis() > FragmentNewCategory2.this.mFinishCal.getTimeInMillis()) {
                                FragmentNewCategory2.this.mTvTermFinishDate.setText(String.format("%04d. %02d. %02d", Integer.valueOf(i), Integer.valueOf(i4), Integer.valueOf(i3)));
                                FragmentNewCategory2.this.mFinishCal.set(i, i2, i3);
                            }
                        }
                    }, FragmentNewCategory2.this.mStartCal.get(1), FragmentNewCategory2.this.mStartCal.get(2), FragmentNewCategory2.this.mStartCal.get(5));
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    if (FragmentNewCategory2.this.limit_switch) {
                        datePickerDialog.show();
                    }
                }
            });
            return;
        }
        this.mIvLimitSwitch.setImageResource(R.drawable.btn_swtch_off);
        this.mTvTermStartDate.setBackgroundResource(R.drawable.input_bg_d);
        this.mTvTermStartDate.setTextColor(getResources().getColor(R.color.grayColor));
        this.mTvTermFinishDate.setBackgroundResource(R.drawable.input_bg_d);
        this.mTvTermFinishDate.setTextColor(getResources().getColor(R.color.grayColor));
    }

    private void CustomDropDownSet(BeanAddProjectMember beanAddProjectMember) {
        if (beanAddProjectMember.List != null && beanAddProjectMember.List.size() > 0) {
            final ArrayList arrayList = new ArrayList();
            arrayList.add(new BeanAddProjectMember.List());
            arrayList.addAll(beanAddProjectMember.List);
            this.mRunnerList = beanAddProjectMember.List;
            this.mSpDropdown.setAdapter((SpinnerAdapter) new DropdownAdapterRepSelect(getContext(), arrayList));
            Utils.setDropDownHeight(this.mSpDropdown, HttpStatus.SC_INTERNAL_SERVER_ERROR);
            this.mSpDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.12
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    int i2 = i - 1;
                    if (i2 > -1) {
                        FragmentNewCategory2.this.mRlRep.setVisibility(8);
                        FragmentNewCategory2.this.mLlSelectRep.setVisibility(0);
                        BeanAddProjectMember.List list = (BeanAddProjectMember.List) arrayList.get(i);
                        if (TextUtils.isEmpty(list.ProfileImgUrl)) {
                            Glide.with(FragmentNewCategory2.this.getContext()).asBitmap().load(Integer.valueOf((int) R.drawable.img_user_profiles)).into(FragmentNewCategory2.this.mIvPhoto);
                        } else {
                            Glide.with(FragmentNewCategory2.this.getContext()).load(list.ProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.fragment.FragmentNewCategory2.12.1
                                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                                    return false;
                                }

                                @Override // com.bumptech.glide.request.RequestListener
                                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                                    Glide.with(FragmentNewCategory2.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(FragmentNewCategory2.this.mIvPhoto);
                                    return false;
                                }
                            }).into(FragmentNewCategory2.this.mIvPhoto);
                        }
                        FragmentNewCategory2.this.mTvName.setText(list.RealName);
                        FragmentNewCategory2.this.mTvEmail.setText(list.Email);
                        FragmentNewCategory2.this.mSelectMember = i2;
                        return;
                    }
                    adapterView.setSelected(false);
                }
            });
        }
    }

    private void httpPostAddProjectMember() {
        new BeplanOkHttpClient().AddProjectMember(getContext(), 2, String.valueOf(this.mPSEQ), "", this);
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback
    public void onUIResponsed(Call call, Intent intent, String str, String str2, int i) {
        super.onUIResponsed(call, intent, str, str2, i);
        int intExtra = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);
        if (i == 200) {
            Gson gson = new Gson();
            BeanCommon beanCommon = (BeanCommon) gson.fromJson(str, (Class<Object>) BeanCommon.class);
            if (intExtra == 11) {
                if (beanCommon != null) {
                    if (TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                        Toast.makeText(getContext(), "카테고리 추가 성공", 0).show();
                    } else {
                        Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                    }
                }
                getActivity().onBackPressed();
            } else if (intExtra == 2) {
                CustomDropDownSet((BeanAddProjectMember) gson.fromJson(str, (Class<Object>) BeanAddProjectMember.class));
            } else if (intExtra == 3) {
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK")) {
                    String str3 = ((BeanPostFile) gson.fromJson(str, (Class<Object>) BeanPostFile.class)).Result;
                    if (str3.contains("@")) {
                        String str4 = "";
                        String str5 = "";
                        if (TextUtils.equals(this.currentImageType, "I")) {
                            str5 = HttpBindTempData.PostFileImageUrl + str3.substring(0, str3.indexOf("@"));
                        } else if (TextUtils.equals(this.currentImageType, "L")) {
                            String[] split = str3.split("@");
                            String str6 = split[split.length - 2];
                            str4 = split[0];
                            str5 = HttpBindTempData.PostFileAddLinkUrl + str6;
                        }
                        initAdapter(this.currentImageType, str4, str3, str5);
                    }
                }
            } else if (intExtra == REQ_FILE_MULTIFILE) {
                if (TextUtils.equals(beanCommon.RtnKey, "CMOK") || TextUtils.equals(beanCommon.RtnKey, "DAOK")) {
                    for (String str7 : ((BeanMultiPostFile) gson.fromJson(str, (Class<Object>) BeanMultiPostFile.class)).ResultFilesResponse) {
                        if (str7.contains("@")) {
                            initAdapter("I", "", str7, HttpBindTempData.PostFileImageUrl + str7.substring(0, str7.indexOf("@")));
                        }
                    }
                    return;
                }
                Toast.makeText(getContext(), beanCommon.RtnValue, 0).show();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.error_server_not_success), 0).show();
        }
    }

    @OnClick({R.id.btn_save})
    public void onClick() {
        httpPostMakeTask();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void httpPostFile(ArrayList<String> arrayList) {
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        ArrayList<File> arrayList2 = new ArrayList<>();
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(new File(it.next()));
            }
            this.currentImageType = "I";
            beplanOkHttpClient.multiPostFileTask(getContext(), REQ_FILE_MULTIFILE, arrayList2, this);
        }
    }

    public void saveTempImage(String str, Bitmap bitmap) {
        File file;
        BeplanOkHttpClient beplanOkHttpClient = new BeplanOkHttpClient();
        String str2 = Environment.getExternalStorageDirectory().getPath() + "/";
        if (bitmap != null) {
            Utils.SaveBitmapToFileCache(bitmap, str2, "tempProfile.jpeg");
            file = new File(str2 + "tempProfile.jpeg");
        } else {
            file = null;
        }
        this.currentImageType = "L";
        beplanOkHttpClient.PostFile(getContext(), 3, str, null, file, "L", "T", this);
    }

    @Override // android.support.v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != -1) {
            return;
        }
        if (i == 100 || intent != null) {
            ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            ArrayList<String> arrayList = new ArrayList<>();
            Iterator it = parcelableArrayListExtra.iterator();
            while (it.hasNext()) {
                arrayList.add(((Image) it.next()).getPath());
            }
            httpPostFile(arrayList);
        }
    }

    @OnClick({R.id.tv_photo})
    public void submitPhoto() {
        showPhotoSelectDialog();
    }
}
