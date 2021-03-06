package com.customer.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DimenRes;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.customer.data.home.HomeLiveGiftList;
import com.fh.module_base_resouce.R;
import com.glide.GlideUtil;
import com.lib.basiclib.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author  QinTian
 * @ Date  2020/8/27
 * @ Describe
 */
public class BottomGiftPageGridView extends FrameLayout {
    public final static int DEFAULT_PAGE_Size = 8;
    public final static int DEFAULT_NUM_COUNT = 8;
    public final static boolean DEFAULT_IS_ShOW_INDICATOR = true;
    public final static int DEFAULT_SELECTED_INDICTOR = R.drawable.shape_dot_selected;
    public final static int DEFAULT_UN_SELECTED_INDICTOR = R.drawable.shape_dot_normal;
    public final static int DEFAULT_VP_BACKGROUND = android.R.color.transparent;
    public final static int DEFAULT_ITEM_VIEW = R.layout.item_view;
    public final static int DEFAULT_INDICATOR_GRAVITY = 1;
    public final static int DEFAULT_INDICATOR_PADDING = 0;
    public final static int DEFAULT_INDICATOR_BACKGROUND = Color.WHITE;
    public final static int DEFAULT_VP_PADDING = 0;
    private Context mContext;
    private LayoutInflater mInflater;
    private View mContentView;
    private ViewPager mViewPager;
    private LinearLayout mLlDot;
    private List<HomeLiveGiftList> mDatas;
    private List mPagerList;
    private GridViewAdapter gridViewAdapter;
    /**
     * ????????????????????????
     */
    private int pageSize;
    /**
     * ????????????
     */
    private int pageCount;

    /**
     * ???????????????????????????
     */
    private int curIndex = 0;

    /**
     * ??????
     */
    private int numColumns = 0;
    /**
     * ???????????????
     */
    private int indicatorGravity;
    /**
     * ?????????????????????
     */
    private boolean isShowIndicator;
    /**
     * ?????????????????????ID
     */
    private int selectedIndicator;
    /**
     * ????????????????????????ID
     */
    private int unSelectedIndicator;
    /**
     * Item??????
     */
    private int mItemView;
    /**
     * ??????????????????
     */
    private int indicatorPaddingLeft;
    private int indicatorPaddingRight;
    private int indicatorPaddingTop;
    private int indicatorPaddingBottom;
    private int indicatorPadding;
    /**
     * ?????????????????????
     */
    private int indicatorBackground;


    /**
     * ViewPager??????
     */
    private int vpBackground;

    private int vpPadding;

    private int selectedPosition = 0;

    public BottomGiftPageGridView(Context context) {
        this(context, null, 0);
    }

    public BottomGiftPageGridView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BottomGiftPageGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initViews(context);
    }


    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PageGridView);
        pageSize = typedArray.getInteger(R.styleable.PageGridView_pageSize, DEFAULT_PAGE_Size);
        numColumns = typedArray.getInteger(R.styleable.PageGridView_numColumns, DEFAULT_NUM_COUNT);
        isShowIndicator = typedArray.getBoolean(R.styleable.PageGridView_isShowIndicator, DEFAULT_IS_ShOW_INDICATOR);
        selectedIndicator = typedArray.getResourceId(R.styleable.PageGridView_selectedIndicator, DEFAULT_SELECTED_INDICTOR);
        unSelectedIndicator = typedArray.getResourceId(R.styleable.PageGridView_unSelectedIndicator, DEFAULT_UN_SELECTED_INDICTOR);
        mItemView = typedArray.getResourceId(R.styleable.PageGridView_itemView, DEFAULT_ITEM_VIEW);
        indicatorGravity = typedArray.getInt(R.styleable.PageGridView_indicatorGravity, DEFAULT_INDICATOR_GRAVITY);
        indicatorPaddingLeft = typedArray.getDimensionPixelOffset(R.styleable.PageGridView_indicatorPaddingLeft, DEFAULT_INDICATOR_PADDING);
        indicatorPaddingRight = typedArray.getDimensionPixelOffset(R.styleable.PageGridView_indicatorPaddingRight, DEFAULT_INDICATOR_PADDING);
        indicatorPaddingTop = typedArray.getDimensionPixelOffset(R.styleable.PageGridView_indicatorPaddingTop, DEFAULT_INDICATOR_PADDING);
        indicatorPaddingBottom = typedArray.getDimensionPixelOffset(R.styleable.PageGridView_indicatorPaddingBottom, DEFAULT_INDICATOR_PADDING);
        indicatorPadding = typedArray.getDimensionPixelOffset(R.styleable.PageGridView_indicatorPadding, -1);
        indicatorBackground = typedArray.getColor(R.styleable.PageGridView_indicatorBackground, DEFAULT_INDICATOR_BACKGROUND);
        vpBackground = typedArray.getResourceId(R.styleable.PageGridView_vpBackground, DEFAULT_VP_BACKGROUND);
        vpPadding = typedArray.getDimensionPixelOffset(R.styleable.PageGridView_vpPadding, DEFAULT_VP_PADDING);
        typedArray.recycle();
    }

    private void initViews(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mContentView = mInflater.inflate(R.layout.vp_gridview, this, true);
        mViewPager = mContentView.findViewById(R.id.view_pager);
        mViewPager.setBackgroundResource(vpBackground);
        mViewPager.setPadding(vpPadding, vpPadding, vpPadding, vpPadding);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //????????????ViewPager
        float rate = (float) pageSize / (float) numColumns;
        int rows = (int) Math.ceil(rate);
        View itemView = mInflater.inflate(mItemView, this, false);

        ViewGroup.LayoutParams itemLayoutParams = itemView.getLayoutParams();
        int itemHeight = itemLayoutParams.height;
        layoutParams.height = rows * itemHeight;
        layoutParams.height += vpPadding * 2;

        mViewPager.setLayoutParams(layoutParams);
        mLlDot = mContentView.findViewById(R.id.ll_dot);
        if (indicatorGravity == 0) {
            mLlDot.setGravity(Gravity.LEFT);
        } else if (indicatorGravity == 1) {
            mLlDot.setGravity(Gravity.CENTER);
        } else if (indicatorGravity == 2) {
            mLlDot.setGravity(Gravity.RIGHT);
        }
        if (indicatorPadding != -1) {
            mLlDot.setPadding(indicatorPadding, indicatorPadding, indicatorPadding, indicatorPadding);
        } else {
            mLlDot.setPadding(indicatorPaddingLeft, indicatorPaddingTop, indicatorPaddingRight, indicatorPaddingBottom);
        }
        mLlDot.setBackgroundColor(indicatorBackground);

    }


    public void setData(List<HomeLiveGiftList> data) {
        this.mDatas = data;
        //????????????=??????/????????????????????????
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        curIndex = 0;
        for (int i = 0; i < pageCount; i++) {
            // ??????????????????inflate??????????????????
            GridView gridView = new GridView(mContext);
            gridView.setNumColumns(numColumns);
            gridView.setOverScrollMode(OVER_SCROLL_NEVER);
            gridViewAdapter = new GridViewAdapter(mContext, mDatas, i, pageSize);
            gridView.setAdapter(gridViewAdapter);

            mPagerList.add(gridView);
            gridViewAdapter.setOnItemClickListener((position, name) -> {
                int pos = position + curIndex * pageSize;
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(pos, gridViewAdapter.mDatas.get(position));
                }
            });
        }
        //???????????????
        mViewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        setCurrentItem(selectedPosition);


    }

    /**
     * ????????????
     */
    public void setOvalLayout() {
        if (mLlDot.getChildCount() > 0) mLlDot.removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(mInflater.inflate(R.layout.dot, null));
            ImageView imageView = mLlDot.getChildAt(i).findViewById(R.id.v_dot);
            imageView.setImageResource(unSelectedIndicator);
        }
        // ????????????
        ImageView imageView = mLlDot.getChildAt(selectedPosition).findViewById(R.id.v_dot);
        imageView.setImageResource(selectedIndicator);
        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int position) {
                // ??????????????????
                ImageView lastImageView = mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot);
                lastImageView.setImageResource(unSelectedIndicator);
                // ????????????
                ImageView nextImageView = mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot);
                nextImageView.setImageResource(selectedIndicator);
                curIndex = position;
            }
        });
    }

    class ViewPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public ViewPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return (mViewList.get(position));
        }

        @Override
        public int getCount() {
            if (mViewList == null)
                return 0;
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    class GridViewAdapter extends BaseAdapter {
        private List<HomeLiveGiftList> mDatas;
        private LayoutInflater inflater;

        private OnItemClickListener mOnItemClickListener;

        /**
         * ????????????,???0??????(??????????????????)
         */
        private int curIndex;
        /**
         * ????????????????????????
         */
        private int pageSize;

        public GridViewAdapter(Context context, List<HomeLiveGiftList> mDatas, int curIndex, int pageSize) {
            inflater = LayoutInflater.from(context);
            this.mDatas = mDatas;
            this.curIndex = curIndex;
            this.pageSize = pageSize;
        }

        public void setData(List<HomeLiveGiftList> data) {
            this.mDatas = data;
            notifyDataSetChanged();
        }

        /**
         * ??????????????????????????????????????????????????????,???????????????????????????????????????????????????????????????pageSize,???????????????????????????????????????,(?????????????????????????????????????????????item)
         */
        @Override
        public int getCount() {
            return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);

        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position + curIndex * pageSize);
        }

        @Override
        public long getItemId(int position) {
            return position + curIndex * pageSize;
        }


        @SuppressLint("SetTextI18n")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null) {
                convertView = inflater.inflate(mItemView, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.itemView = convertView;
                viewHolder.iv = convertView.findViewById(R.id.im_item_icon);
                viewHolder.tv = convertView.findViewById(R.id.tv_item_name);
                viewHolder.tvPrise= convertView.findViewById(R.id.tvGiftPrise);
                viewHolder.pagerGridContainer = convertView.findViewById(R.id.pagerGridContainer);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            /**
             * ??????View??????????????????????????????????????????position = position + curIndex * pageSize
             */
            int pos = position + curIndex * pageSize;
            if (null != viewHolder.tv) {
                viewHolder.tv.setTextColor(ViewUtils.INSTANCE.getColor(R.color.white));
                viewHolder.tv.setText(mDatas.get(pos).getName());
            }
            if (null != viewHolder.iv) {
                if (isFirst) {
                    GlideUtil.INSTANCE.loadImage(getContext(),mDatas.get(pos).getIcon(), viewHolder.iv);
                }

            }
            if (null != viewHolder.tvPrise) {
                viewHolder.tvPrise.setText(mDatas.get(pos).getAmount()+" ??????");
            }
            if (null != viewHolder.pagerGridContainer) {
                if (clickPosition.equals(mDatas.get(pos).getName())) {
                    viewHolder.pagerGridContainer.setBackground(ViewUtils.INSTANCE.getDrawable(R.drawable.shape_home_live_chat_gif_selected_bg));
                } else viewHolder.pagerGridContainer.setBackground(null);
            }
            viewHolder.itemView.setOnClickListener(view -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(position, mDatas.get(pos));
                }
            });
            return convertView;
        }


        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        class ViewHolder {
            public View itemView;
            public TextView tv,tvPrise;
            public ImageView iv;
            public LinearLayout pagerGridContainer;
        }
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, HomeLiveGiftList homeLiveGiftList);
    }


    /**
     * dp???px
     */
    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }

    public int getDimensionPixelOffset(@DimenRes int resId) {
        return mContext.getResources().getDimensionPixelOffset(resId);
    }

    class OnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    public ViewPager getViewPager() {
        return mViewPager;
    }

    public void setCurrentItem(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        mViewPager.setCurrentItem(selectedPosition);
        //????????????
        if (isShowIndicator && pageCount > 1) {
            setOvalLayout();
        } else {
            if (mLlDot.getChildCount() > 0) mLlDot.removeAllViews();
            mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
                public void onPageSelected(int position) {
                    curIndex = position;
                }
            });
        }


    }

    public String clickPosition = "";

    public Boolean isFirst = true;


    public List<HomeLiveGiftList> getDatas() {
        return mDatas;
    }

    public HomeLiveGiftList getItem(int position) {
        return mDatas.get(position);
    }

    public void notifyAllData(String name) {
        this.isFirst = false;
        this.clickPosition = name;
        if (gridViewAdapter == null) return;
        gridViewAdapter.notifyDataSetChanged();
    }
}