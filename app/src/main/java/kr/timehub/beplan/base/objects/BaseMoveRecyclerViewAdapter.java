package kr.timehub.beplan.base.objects;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import java.util.Collections;
import kr.timehub.beplan.Interface.OnItemMoveListener;

/* loaded from: classes.dex */
public class BaseMoveRecyclerViewAdapter extends BaseRecyclerViewAdapter implements OnItemMoveListener {
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_LIST = 0;
    private final int DELAY = 1000;
    Handler handler = new Handler() { // from class: kr.timehub.beplan.base.objects.BaseMoveRecyclerViewAdapter.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            BaseMoveRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    };

    public BaseMoveRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override // kr.timehub.beplan.Interface.OnItemMoveListener
    public boolean onItemMove(int i, int i2) {
        this.handler.removeMessages(0);
        Collections.swap(getArray(), i, i2);
        notifyItemMoved(i, i2);
        this.handler.sendMessageDelayed(new Message(), 1000L);
        return false;
    }

    @Override // kr.timehub.beplan.Interface.OnItemMoveListener
    public void onItemDismiss(int i) {
        getArray().remove(i);
        notifyItemRemoved(i);
    }
}
