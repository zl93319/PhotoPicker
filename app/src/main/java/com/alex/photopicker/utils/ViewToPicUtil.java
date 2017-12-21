package com.alex.photopicker.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * copywrite 2015-2020 金地物业
 * 不能修改和删除上面的版权声明
 * 此代码属于信息与社区平台管理部门编写，在未经允许的情况下不得传播复制
 * 类描述:
 * ${CURSOR}
 * 2017-11-30 09:42
 * zhangchuanjia
 */


public class ViewToPicUtil {

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getScrollViewBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap

        bitmap = Bitmap.createBitmap(ScreenUtils.getScreenWidth(scrollView.getContext()), h,
                Bitmap.Config.ARGB_4444);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#ffffff"));
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 截图listview
     **/
    public static Bitmap getListViewBitmap(ListView listView, String picpath) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < listView.getChildCount(); i++) {
            h += listView.getChildAt(i).getHeight();
        }
        listView.getHeight();
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(listView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        listView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        return bitmap;
    }

    /**
     * 生成某个view的图片
     * 注意: 要等到View绘制完成后才能使用这个方法得到View
     *
     
     * @date 2017/3/20 上午10:34
     */
    public static Bitmap getViewDrawingCacheBitmap(View view) {
        view = view.getRootView();
        if (!view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        view.destroyDrawingCache();
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();
        view.setDrawingCacheEnabled(false);
        return bm;
    }

    /**
     * 生成某个LinearLayout的图片
     *
     
     * @date 2017/3/20 上午10:34
     */
    public static Bitmap getLinearLayoutBitmap(LinearLayout linearLayout) {
        int h = 0;
        // 获取LinearLayout实际高度
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).measure(0, 0);
            h += linearLayout.getChildAt(i).getMeasuredHeight();
        }
        linearLayout.measure(0, 0);
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(linearLayout.getMeasuredWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        linearLayout.draw(canvas);
        return bitmap;
    }

    /**
     *拼接图片
     * @param first 分享的长图
     * @param second  公司logo图
     * 
     *@date 2017/3/25 下午4:56
     */
    public static Bitmap add2Bitmap(Bitmap first, Bitmap second) {
        float scale = ((float) first.getWidth()) / second.getWidth();
        second = ImageUtil.scaleImg(second, scale);
        int width = first.getWidth();
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }

    /**
     * @param first 原始图
     * @param mark  水印图
     * @date 2017/3/25 下午4:58
     */
    public static Bitmap waterMark(Bitmap first, Bitmap mark) {
        float scale = ((float) first.getWidth()) / mark.getWidth();
        mark = ImageUtil.scaleImg(mark, scale);
        int height = first.getHeight();
        Canvas canvas = new Canvas(first);
        int h = 0;
        while (h < height + mark.getHeight()) {
            canvas.drawBitmap(mark, 0, h, null);
            h = h + mark.getHeight();
        }
        return first;
    }
}
