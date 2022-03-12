package kr.timehub.beplan.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.naver.temy123.baseproject.base.Widgets.BaseFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import kr.timehub.beplan.R;
import kr.timehub.beplan.activity.ActivityMain;
import kr.timehub.beplan.utils.Comm_Prefs;

/* loaded from: classes.dex */
public class FragmentSettingPush extends BaseFragment {
    @BindView(R.id.switch_push)
    Switch mSwitchPush;
    @BindView(R.id.tv_end_date)
    TextView mTvEndTime;
    @BindView(R.id.tv_start_date)
    TextView mTvStartTime;
    Comm_Prefs prefs;
    Unbinder unbinder;
    private Calendar calStart = Calendar.getInstance();
    private Calendar calEnd = Calendar.getInstance();

    @OnClick({R.id.ll_save})
    public void submitClickSave() {
        this.prefs.setSettingPushStartTime(this.calStart.getTimeInMillis());
        this.prefs.setSettingPushEndTime(this.calEnd.getTimeInMillis());
        Comm_Prefs.getInstance(getContext()).setSettingPush(this.mSwitchPush.isChecked());
        Toast.makeText(getContext(), "저장되었습니다.", 0).show();
    }

    @OnClick({R.id.tv_start_date, R.id.tv_end_date})
    public void submitClickTime(final View view) {
        if (this.mSwitchPush.isChecked()) {
            final Calendar calendar = view.getId() == R.id.tv_start_date ? this.calStart : this.calEnd;
            new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() { // from class: kr.timehub.beplan.fragment.FragmentSettingPush.1
                @Override // android.app.TimePickerDialog.OnTimeSetListener
                public void onTimeSet(TimePicker timePicker, int i, int i2) {
                    calendar.set(11, i);
                    calendar.set(12, i2);
                    FragmentSettingPush.this.setDate(calendar, view);
                }
            }, calendar.get(11), calendar.get(12), true).show();
        }
    }

    @Override // com.naver.temy123.baseproject.base.Widgets.BaseFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_setting_push, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        this.prefs = Comm_Prefs.getInstance(getContext());
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((ActivityMain) getActivity()).setMyInfo(false);
        setSwitchView();
        setDefault();
    }

    public void setDate(Calendar calendar, View view) {
        ((TextView) view).setText(new SimpleDateFormat(getString(R.string.str_format_extended_work_time), Locale.getDefault()).format(calendar.getTime()));
    }

    private void setDefault() {
        long settingPushStartTime = this.prefs.getSettingPushStartTime();
        long settingPushEndTime = this.prefs.getSettingPushEndTime();
        if (settingPushStartTime != 0) {
            this.calStart.setTimeInMillis(settingPushStartTime);
        }
        if (settingPushEndTime != 0) {
            this.calEnd.setTimeInMillis(settingPushEndTime);
        }
        setDate(this.calStart, this.mTvStartTime);
        setDate(this.calEnd, this.mTvEndTime);
        if (this.prefs.getSettingPush()) {
            this.mSwitchPush.setChecked(true);
        }
    }

    private void setSwitchView() {
        this.mSwitchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: kr.timehub.beplan.fragment.FragmentSettingPush.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    FragmentSettingPush.this.mTvStartTime.setBackgroundColor(FragmentSettingPush.this.getContext().getResources().getColor(R.color.formshop_bg));
                    FragmentSettingPush.this.mTvEndTime.setBackgroundColor(FragmentSettingPush.this.getContext().getResources().getColor(R.color.formshop_bg));
                    FragmentSettingPush.this.mTvStartTime.setTextColor(FragmentSettingPush.this.getContext().getResources().getColor(R.color.davygrey));
                    FragmentSettingPush.this.mTvEndTime.setTextColor(FragmentSettingPush.this.getContext().getResources().getColor(R.color.davygrey));
                    return;
                }
                FragmentSettingPush.this.mTvStartTime.setBackgroundColor(FragmentSettingPush.this.getContext().getResources().getColor(R.color.light_gray));
                FragmentSettingPush.this.mTvEndTime.setBackgroundColor(FragmentSettingPush.this.getContext().getResources().getColor(R.color.light_gray));
                FragmentSettingPush.this.mTvStartTime.setTextColor(FragmentSettingPush.this.getContext().getResources().getColor(R.color.taupe_gray));
                FragmentSettingPush.this.mTvEndTime.setTextColor(FragmentSettingPush.this.getContext().getResources().getColor(R.color.taupe_gray));
            }
        });
    }
}
