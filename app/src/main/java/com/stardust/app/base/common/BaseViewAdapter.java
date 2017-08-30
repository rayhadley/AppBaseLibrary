package com.stardust.app.base.common;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.stardust.app.base.R;

import java.util.List;

public class BaseViewAdapter extends PagerAdapter {
	 private List<View> mListViews;

    /**
     * 滚动图片指示器-视图列表
     */
    private ImageView[] mImageViews = null;
    /**
     * 图片滚动当前图片下标
     */
    private int mImageIndex = 0;
    /**
     * 图片轮播指示器-个图
     */
    private ImageView mImageView = null;

    private Context mContext;
    /**
     * 手机密度
     */
    private float mScale;
     
     public BaseViewAdapter(List<View> mListViews) {
         this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
     }
     public BaseViewAdapter(Context context, List<View> mListViews) {
         this.mContext = context;
         this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
         mScale = context.getResources().getDisplayMetrics().density;
     }

     @Override
     public void destroyItem(ViewGroup container, int position, Object object)     {    
         container.removeView(mListViews.get(position));//删除页卡
     }


     @Override
     public Object instantiateItem(ViewGroup container, int position) {    //这个方法用来实例化页卡        
          container.addView(mListViews.get(position), 0);//添加页卡
          return mListViews.get(position);
     }

     @Override
     public int getCount() {            
         return  mListViews.size();//返回页卡的数量
     }
     
     @Override
     public boolean isViewFromObject(View arg0, Object arg1) {            
         return arg0==arg1;//官方提示这样写
     }

    /**
     * 显示页面指示器
     * @param viewPager
     * @param container 填装指示图片的view
     * @param imgSelectedId 选中显示的图片ID
     * @param imgUnSelectedId 未选中显示的图片ID
     * */
    public void showPageIndicator(ViewPager viewPager, ViewGroup container, int imgSelectedId, int imgUnSelectedId) {
        if(mListViews == null) return;
        // 清除所有子视图
        container.removeAllViews();
        int count = mListViews.size();
        mImageViews = new ImageView[count];
        for (int i = 0; i < count; i++) {
            mImageView = new ImageView(mContext);
            int imageParams = (int) (mScale * 20 + 0.5f);// XP与DP转换，适应不同分辨率
            int imagePadding = (int) (mScale * 5 + 0.5f);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(3, 0, 3, 0);
            mImageView.setLayoutParams(layout);
            //mImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
            mImageViews[i] = mImageView;
            if (i == 0) {
                mImageViews[i].setBackgroundResource(R.drawable.ic_point_selected);
            } else {
                mImageViews[i].setBackgroundResource(R.drawable.ic_point);
            }
            container.addView(mImageViews[i]);
        }

        viewPager.setOnPageChangeListener(new GuidePageChangeListener());

    }

    /**
     * 轮播图片状态监听器
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
//            if (state == ViewPager.SCROLL_STATE_IDLE)
//                startImageTimerTask(); // 开始下次计时
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {

//            if (index == 0 || index == mImageViews.length + 1) {
//                return;
//            }
//            // 设置图片滚动指示器背景
            mImageIndex = index;
//            index -= 1;
            mImageViews[index].setBackgroundResource(R.drawable.ic_point_selected);
            for (int i = 0; i < mImageViews.length; i++) {
                if (index != i) {
                    mImageViews[i].setBackgroundResource(R.drawable.ic_point);
                }
            }

        }

    }

}