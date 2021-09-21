package com.customer.component.panel.emotion;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.customer.component.panel.gif.GifManager;
import com.customer.data.UserInfoSp;
import com.lib.basiclib.utils.ToastUtils;
import com.lib.basiclib.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author  QinTian
 * @ Date  1/27/21
 * @ Describe
 */
public class EmotionViewS extends GridView {

    private static int sNumColumns = 0;
    private static int sNumRows = 0;
    private static int sPadding = 0;
    private static int sEmotionSize = 0;
    private EditText mEditText;

    public static int calSizeForContainEmotion(Context context, int width, int height) {
        sPadding = ViewUtils.INSTANCE.dp2px(5);
        sEmotionSize =  ViewUtils.INSTANCE.dp2px(50);
        sNumColumns = 5;
        sNumRows = 4;
        return sNumColumns * sNumRows;
    }

    public EmotionViewS(Context context, EditText editText) {
        super(context);
        this.mEditText = editText;
    }

    public void buildEmotions(final List<Emotion> data) {
        setNumColumns(sNumColumns);
        setPadding(sPadding, sPadding, sPadding, sPadding);
        setClipToPadding(false);
        setAdapter(new EmotionAdapter(getContext(), data));
        setOnItemClickListener((parent, view, position, id) -> {
            if (UserInfoSp.INSTANCE.getNobleLevel()>=6){
                Emotion emotion = data.get(position);
                int start = mEditText.getSelectionStart();
                Editable editable = mEditText.getEditableText();
                Spannable emotionSpannable = GifManager.INSTANCE.textWithGifKeyBord(emotion.text,getContext());
                editable.insert(start, emotionSpannable);
            }else ToastUtils.INSTANCE.showToast("需达到公爵解锁该表情包");

        });
    }

    public static class EmotionAdapter extends BaseAdapter {

        public List<Emotion> mEmotions;
        private Context mContext;

        public EmotionAdapter(Context context, List<Emotion> emotions) {
            if (emotions == null) {
                emotions = new ArrayList<>();
            }
            mEmotions = emotions;
            mContext = context;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(com.fh.basemodle.R.layout.vh_emotion_item_layout_v, parent, false);
            }
            ImageView imageView = view.findViewById(com.fh.basemodle.R.id.image);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            params.height = ViewUtils.INSTANCE.dp2px(40);
            params.width =ViewUtils.INSTANCE.dp2px(40);
            imageView.setLayoutParams(params);
            imageView.setImageResource(((Emotion) getItem(position)).drawableRes);
            return view;
        }

        @Override
        public int getCount() {
            return mEmotions.size();
        }

        @Override
        public Object getItem(int position) {
            return mEmotions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }
}