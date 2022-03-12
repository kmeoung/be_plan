package kr.timehub.beplan.base.DropdownAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import java.util.List;
import kr.timehub.beplan.R;
import kr.timehub.beplan.base.widgets.TextView;
import kr.timehub.beplan.entry.plugin.BeanAddProjectMember;

/* loaded from: classes.dex */
public class DropdownAdapterRepSelect extends ArrayAdapter<BeanAddProjectMember.List> {
    private Context context;
    List<BeanAddProjectMember.List> data;

    public DropdownAdapterRepSelect(Context context, List<BeanAddProjectMember.List> list) {
        super(context, (int) R.layout.listitem_rep, list);
        this.data = null;
        this.context = context;
        this.data = list;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    @NonNull
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.listitem_rep, (ViewGroup) null);
        }
        ((ImageView) view.findViewById(R.id.iv_photo)).setVisibility(8);
        ((TextView) view.findViewById(R.id.tv_name)).setVisibility(8);
        ((TextView) view.findViewById(R.id.tv_email)).setVisibility(8);
        return view;
    }

    @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            Context context = this.context;
            Context context2 = this.context;
            view = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.listitem_rep, viewGroup, false);
        }
        if (view.getTag() == null) {
            NetworkHolder networkHolder = new NetworkHolder();
            networkHolder.setPhoto((ImageView) view.findViewById(R.id.iv_photo));
            networkHolder.setName((TextView) view.findViewById(R.id.tv_name));
            networkHolder.setEmail((TextView) view.findViewById(R.id.tv_email));
            view.setTag(networkHolder);
        }
        BeanAddProjectMember.List list = this.data.get(i);
        if (i == 0) {
            ((NetworkHolder) view.getTag()).getPhoto().setVisibility(8);
            ((NetworkHolder) view.getTag()).getName().setText("담당자를 선택해주세요");
            ((NetworkHolder) view.getTag()).getEmail().setVisibility(8);
        } else {
            ((NetworkHolder) view.getTag()).getPhoto().setVisibility(0);
            ((NetworkHolder) view.getTag()).getEmail().setVisibility(0);
            final ImageView photo = ((NetworkHolder) view.getTag()).getPhoto();
            if (TextUtils.isEmpty(list.ProfileImgUrl)) {
                Glide.with(getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(photo);
            } else {
                Glide.with(getContext()).load(list.ProfileImgUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).listener(new RequestListener<Drawable>() { // from class: kr.timehub.beplan.base.DropdownAdapter.DropdownAdapterRepSelect.1
                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        return false;
                    }

                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        Glide.with(DropdownAdapterRepSelect.this.getContext()).load(Integer.valueOf((int) R.drawable.img_user_profiles)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(photo);
                        return false;
                    }
                }).into(photo);
            }
            ((NetworkHolder) view.getTag()).getName().setText(list.RealName);
            ((NetworkHolder) view.getTag()).getEmail().setText(list.Email);
        }
        return view;
    }

    /* loaded from: classes.dex */
    private static class NetworkHolder {
        private TextView email;
        private TextView name;
        private ImageView photo;

        private NetworkHolder() {
        }

        public ImageView getPhoto() {
            return this.photo;
        }

        public void setPhoto(ImageView imageView) {
            this.photo = imageView;
        }

        public TextView getName() {
            return this.name;
        }

        public void setName(TextView textView) {
            this.name = textView;
        }

        public TextView getEmail() {
            return this.email;
        }

        public void setEmail(TextView textView) {
            this.email = textView;
        }
    }
}
