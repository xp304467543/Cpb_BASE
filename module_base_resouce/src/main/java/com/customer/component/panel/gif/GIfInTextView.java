package com.customer.component.panel.gif;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.ArrayMap;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.customer.component.panel.emotion.EmotionDrawable;
import com.customer.component.panel.emotion.Emotions;
import com.customer.component.recyclegif.gif.DrawableTarget;
import com.customer.component.recyclegif.gif.GifIsoheightImageSpan;
import com.customer.component.recyclegif.gif.ProxyDrawable;
import com.fh.module_base_resouce.R;
import com.lib.basiclib.utils.LogUtils;
import com.lib.basiclib.utils.ViewUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifDrawable;

import static com.customer.component.recyclegif.gif.GifSpanUtilKt.createResizeGifDrawableSpan;

/**
 * 该工具可将gif添加入textView
 * 使用了以下工具类
 * {@link AnimatedGifDrawable}
 */
public class GIfInTextView {

    //    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP = new ArrayMap<>();
    public static ArrayMap<String, String> EMOTION_CLASSIC_MAP2 = new ArrayMap<>();

    private static HashMap<String, Drawable> drawableCacheMap = new HashMap<>();

    private static List<EmotionDrawable> emotions = new ArrayList<>();

    static {
        EMOTION_CLASSIC_MAP2.put(":G1:", "g1.gif");
        EMOTION_CLASSIC_MAP2.put(":G2:", "g2.gif");
        EMOTION_CLASSIC_MAP2.put(":G3:", "g3.gif");
        EMOTION_CLASSIC_MAP2.put(":G4:", "g4.gif");
        EMOTION_CLASSIC_MAP2.put(":G5:", "g5.gif");
        EMOTION_CLASSIC_MAP2.put(":G6:", "g6.gif");
        EMOTION_CLASSIC_MAP2.put(":G7:", "g7.gif");
        EMOTION_CLASSIC_MAP2.put(":G8:", "g8.gif");
        EMOTION_CLASSIC_MAP2.put(":G9:", "g9.gif");
        EMOTION_CLASSIC_MAP2.put(":G10:", "g10.gif");
        EMOTION_CLASSIC_MAP2.put(":G11:", "g11.gif");
        EMOTION_CLASSIC_MAP2.put(":G12:", "g12.gif");
        EMOTION_CLASSIC_MAP2.put(":G13:", "g13.gif");
        EMOTION_CLASSIC_MAP2.put(":G14:", "g14.gif");
        EMOTION_CLASSIC_MAP2.put(":G15:", "g15.gif");
        EMOTION_CLASSIC_MAP2.put(":G16:", "g16.gif");
        EMOTION_CLASSIC_MAP2.put(":G17:", "g17.gif");
        EMOTION_CLASSIC_MAP2.put(":G18:", "g18.gif");
        EMOTION_CLASSIC_MAP2.put(":G19:", "g19.gif");
        EMOTION_CLASSIC_MAP2.put(":G20:", "g20.gif");
        EMOTION_CLASSIC_MAP2.put(":G21:", "g21.gif");
        EMOTION_CLASSIC_MAP2.put(":G22:", "g22.gif");
        EMOTION_CLASSIC_MAP2.put(":G23:", "g23.gif");
        EMOTION_CLASSIC_MAP2.put(":G24:", "g24.gif");
        EMOTION_CLASSIC_MAP2.put(":G25:", "g25.gif");
        EMOTION_CLASSIC_MAP2.put(":G26:", "g26.gif");
        EMOTION_CLASSIC_MAP2.put(":G27:", "g27.gif");
        EMOTION_CLASSIC_MAP2.put(":G28:", "g28.gif");
        EMOTION_CLASSIC_MAP2.put(":G29:", "g29.gif");
        EMOTION_CLASSIC_MAP2.put(":G30:", "g30.gif");
        EMOTION_CLASSIC_MAP2.put(":G31:", "g31.gif");
        EMOTION_CLASSIC_MAP2.put(":G32:", "g32.gif");
        EMOTION_CLASSIC_MAP2.put(":G33:", "g33.gif");
        EMOTION_CLASSIC_MAP2.put(":G34:", "g34.gif");
        EMOTION_CLASSIC_MAP2.put(":G35:", "g35.gif");
        EMOTION_CLASSIC_MAP2.put(":G36:", "g36.gif");
        EMOTION_CLASSIC_MAP2.put(":G37:", "g37.gif");
        EMOTION_CLASSIC_MAP2.put(":G38:", "g38.gif");


        EMOTION_CLASSIC_MAP2.put(":bowtie:", "bowtie.png");
        EMOTION_CLASSIC_MAP2.put(":smiley:", "smiley.png");
        EMOTION_CLASSIC_MAP2.put(":kissing_heart:", "kissing_heart.png");
        EMOTION_CLASSIC_MAP2.put(":satisfied:", "satisfied.png");
        EMOTION_CLASSIC_MAP2.put(":stuck_out_tongue_closed_eyes:", "stuck_out_tongue_closed_eyes.png");
        EMOTION_CLASSIC_MAP2.put(":stuck_out_tongue:", "stuck_out_tongue.png");

        EMOTION_CLASSIC_MAP2.put(":smile:", "smile.png");
        EMOTION_CLASSIC_MAP2.put(":relaxed:", "relaxed.png");
        EMOTION_CLASSIC_MAP2.put(":kissing_closed_eyes:", "kissing_closed_eyes.png");
        EMOTION_CLASSIC_MAP2.put(":grin:", "grin.png");
        EMOTION_CLASSIC_MAP2.put(":grinning:", "grinning.png");
        EMOTION_CLASSIC_MAP2.put(":sleeping:", "sleeping.png");

        EMOTION_CLASSIC_MAP2.put(":laughing:", "laughing.png");
        EMOTION_CLASSIC_MAP2.put(":smirk:", "smirk.png");
        EMOTION_CLASSIC_MAP2.put(":flushed:", "flushed.png");
        EMOTION_CLASSIC_MAP2.put(":wink:", "wink.png");
        EMOTION_CLASSIC_MAP2.put(":kissing:", "kissing.png");
        EMOTION_CLASSIC_MAP2.put(":worried:", "worried.png");

        EMOTION_CLASSIC_MAP2.put(":blush:", "blush.png");
        EMOTION_CLASSIC_MAP2.put(":heart_eyes:", "heart_eyes.png");
        EMOTION_CLASSIC_MAP2.put(":relieved:", "relieved.png");
        EMOTION_CLASSIC_MAP2.put(":stuck_out_tongue_winking_eye:", "stuck_out_tongue_winking_eye.png");
        EMOTION_CLASSIC_MAP2.put(":kissing_smiling_eyes:", "kissing_smiling_eyes.png");
        EMOTION_CLASSIC_MAP2.put(":frowning:", "frowning.png");


        EMOTION_CLASSIC_MAP2.put(":H1:", "h1.gif");
        EMOTION_CLASSIC_MAP2.put(":H2:", "h2.gif");
        EMOTION_CLASSIC_MAP2.put(":H3:", "h3.gif");
        EMOTION_CLASSIC_MAP2.put(":H4:", "h4.gif");
        EMOTION_CLASSIC_MAP2.put(":H5:", "h5.gif");
        EMOTION_CLASSIC_MAP2.put(":H6:", "h6.gif");
        EMOTION_CLASSIC_MAP2.put(":H7:", "h7.gif");
        EMOTION_CLASSIC_MAP2.put(":H8:", "h8.gif");
        EMOTION_CLASSIC_MAP2.put(":H9:", "h9.gif");
        EMOTION_CLASSIC_MAP2.put(":H10:", "h10.gif");
        EMOTION_CLASSIC_MAP2.put(":H11:", "h11.gif");
        EMOTION_CLASSIC_MAP2.put(":H12:", "h12.gif");
        EMOTION_CLASSIC_MAP2.put(":H13:", "h13.gif");
        EMOTION_CLASSIC_MAP2.put(":H14:", "h14.gif");
        EMOTION_CLASSIC_MAP2.put(":H15:", "h15.gif");
        EMOTION_CLASSIC_MAP2.put(":H16:", "h16.gif");
        EMOTION_CLASSIC_MAP2.put(":H17:", "h17.gif");
        EMOTION_CLASSIC_MAP2.put(":H18:", "h18.gif");
        EMOTION_CLASSIC_MAP2.put(":H19:", "h19.gif");
        EMOTION_CLASSIC_MAP2.put(":H20:", "h20.gif");

    }


    public static void initDefault(Context context) {
        for (Map.Entry<String, String> entry : EMOTION_CLASSIC_MAP2.entrySet()) {
            Drawable drawable = null;
            try {
                drawable =  new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(context.getAssets().open(entry.getValue())));
            } catch (IOException e) {
                e.printStackTrace();
            }
//                    new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(context.getAssets().open(entry.getValue())));
            drawableCacheMap.put(entry.getKey(), drawable);
        }
    }

    private static Pattern sPatternEmotion = Pattern.compile("\\:(.*?)\\:");


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static SpannableStringBuilder textToGif(String str, Context context) {
        //以下为将表情转换为gif的代码
        Matcher matcherEmotion = sPatternEmotion.matcher(str);
        SpannableStringBuilder spannableString = new SpannableStringBuilder(str);
        while (matcherEmotion.find()) {
            String tempText = matcherEmotion.group();
            String gif = EMOTION_CLASSIC_MAP2.get(matcherEmotion.group());
            try {
//                Drawable gifDrawable;
//                if (drawableCacheMap.containsKey(tempText)) {
//                    gifDrawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(context.getAssets().open(gif)));
//                } else {
//                    gifDrawable = new GifDrawable(context.getAssets(),gif);
////                            new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(context.getAssets().open(gif)));
//                    drawableCacheMap.put(tempText, gifDrawable);
//                }
                ProxyDrawable proxyDrawable =new ProxyDrawable();
//
                if (gif != null) {
                    Glide.with(context)
                            .load(gif)
                            .into(new DrawableTarget(proxyDrawable));
                }
                Spannable spannable = createResizeGifDrawableSpan(proxyDrawable, "[c]");
                spannableString.append(spannable);
//                ImageSpan spannable = new GifIsoheightImageSpan(Objects.requireNonNull(ViewUtils.INSTANCE.getDrawable(R.drawable.h3)));
//
//                spannableString.append(tempText, spannable, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.INSTANCE.e("-------->?????");
            }
        }
        return spannableString;
    }


    private static Boolean isGif(String key) {
        if (Emotions.EMOTIONS.containsKey(key) || Emotions.EMOTIONS_TWO.containsKey(key)) {
            return true;
        }
        return false;
    }

    private static Boolean isBigGif(String key) {
        if (Emotions.EMOTIONS_TWO.containsKey(key)) {
            return true;
        }
        return false;
    }


}
