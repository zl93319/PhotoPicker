package com.alex.photopicker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alex.photopicker.R;
import com.alex.photopicker.utils.ImageUtil;
import com.alex.photopicker.utils.ViewToPicUtil;
import com.alex.photopicker.widget.PhotoEditView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private PhotoEditView mPhotoEditView;
    private List<String> mSelectPicList;
    private String mBigImagePath;
    private ScrollView mScrollView;
    private LinearLayout mContainer;
    private ImageView mIv;
    private View mDetailView;
    private TextView mDetailTvPraiseTo;
    private TextView mDetailTvPraiseText;
    private TextView mDetailTvPraiseName;
    private TextView mDetailTvPraiseTime;
    private LinearLayout mDetailLlImgs;
    private LinearLayout mDetailLlPraiseBorder;
    private int mStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDetailView();
    }

    private void initView() {
        mScrollView = (ScrollView) findViewById(R.id.sv);
        mIv = (ImageView) findViewById(R.id.iv);
         mContainer = (LinearLayout) findViewById(R.id.ll_container);
        mPhotoEditView = (PhotoEditView) findViewById(R.id.photo_edit);
        mPhotoEditView.setMaxCount(9, 4);
    }


    /**
     * 初始化详情视图
     * 做出一个1dp高的ScrollView,填充进去详情视图
     * 1. 为了提交产生一个大图做准备
     */
    private void initDetailView() {
        mDetailView = getLayoutInflater().inflate(R.layout.view_property_praise_detail, null);
        mDetailTvPraiseTo = (TextView) mDetailView.findViewById(R.id.tv_praise_to);//被表扬人
        mDetailTvPraiseText = (TextView) mDetailView.findViewById(R.id.tv_praise_text);//表扬信内容
        mDetailTvPraiseName = (TextView) mDetailView.findViewById(R.id.tv_praise_name);//表扬发送人
        mDetailTvPraiseTime = (TextView) mDetailView.findViewById(R.id.tv_praise_time);//表扬时间
        mDetailLlImgs = (LinearLayout) mDetailView.findViewById(R.id.ll_imgs);//表扬信图片
        mDetailLlPraiseBorder = (LinearLayout) mDetailView.findViewById(R.id.ll_praise_border);//表扬信边框
        mContainer.addView(mDetailView);
    }


    /**
     * 申请相机权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPhotoEditView.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: requestCode = " + requestCode + ",resultCode = " + resultCode);
        mPhotoEditView.onActivityResult(requestCode, resultCode, data);
        mSelectPicList = mPhotoEditView.getPhotoList();

        Log.i(TAG, "onActivityResult: " + mSelectPicList.toString());
    }

    public void publish(View view) {
        publishPushContent(2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                publishCreateImg();
            }
        }, 1000);
    }

    /**
     * 填充表扬信详情内容
     */
    private void publishPushContent(int style) {
        Log.i(TAG, "publishPushContent: start");
        mDetailTvPraiseTo.setText("享家用户");
        mDetailTvPraiseText.setText("青春不是年华，而是心境；青春不是桃面、丹唇、柔膝，而是深沉的意志，恢宏的想象，炙热的感情；青春是生命的深泉在涌流。\n" +
                "青春气贯长虹，勇锐盖过怯弱，进取压倒苟安。如此锐气，二十后生而有之，六旬男子则更多见。年岁有加，并非垂老，理想丢弃，方堕暮年。\n" +
                "岁月悠悠，衰微只及肌肤；热忱抛却，颓废必致灵魂。忧烦，惶恐，丧失自信，定使心灵扭曲，意气如灰。\n" +
                "无论年届花甲，抑或二八芳龄，心中皆有生命之欢乐，奇迹之诱惑，孩童般天真久盛不衰。人人心中皆有一台天线，只要你从天上人间接受美好、希望、欢乐、勇气和力量的信号，你就青春永驻，风华常存。\n" +
                "一旦天线下降，锐气便被冰雪覆盖，玩世不恭、自暴自弃油然而生，即使年方二十，实已垂垂老矣；然则只要树起天线，捕捉乐观信号，你就有望在八十高龄告别尘寰时仍觉年轻。");
        mDetailTvPraiseName.setText("享家app");
        mDetailTvPraiseTime.setText("2018-00-99");
        switch (style) {
            case 1:
                mStyle = 1;
                mScrollView.setBackgroundResource(R.drawable.praise_praise_bg_1);
                mDetailLlPraiseBorder.setBackground(getResources().getDrawable(R.drawable.praise_ractangle_bg_brown));
                break;
            case 2:
                mStyle = 2;
                mScrollView.setBackgroundResource(R.drawable.praise_praise_bg_2);
                mDetailLlPraiseBorder.setBackground(getResources().getDrawable(R.drawable.praise_ractangle_bg_black));
                break;
            case 3:
                mStyle = 3;
                mScrollView.setBackgroundResource(R.drawable.praise_praise_bg_3);
                mDetailLlPraiseBorder.setBackground(getResources().getDrawable(R.drawable.praise_ractangle_bg_black));
                break;
        }
        if (mSelectPicList != null && !mSelectPicList.isEmpty()) {
            publishPushImgs(mDetailLlImgs);
        }
        Log.i(TAG, "publishPushContent: end");
    }

    /**
     * 根据imgs数据的大小设置图片
     *
     * @param imgsContainer
     */
    private void publishPushImgs(LinearLayout imgsContainer) {
        Log.i(TAG, "publishPushImgs: start");
        if (mSelectPicList != null) {
            for (String imgPath : mSelectPicList) {
                ImageView iv = new ImageView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 5, 0, 5);
                iv.setLayoutParams(layoutParams);
                Glide.with(this).load(new File(imgPath)).into(iv);
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imgsContainer.addView(iv);
            }
        }
        Log.i(TAG, "publishPushImgs: end");

    }

    /**
     * 生成表扬信图片
     */
    private void publishCreateImg() {
        Log.i(TAG, "publishCreateImg: start");
        final Bitmap bitmap = ViewToPicUtil.getLinearLayoutBitmap(mContainer);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIv.setImageBitmap(bitmap);

            }
        });
        try {
            mBigImagePath = ImageUtil.saveBitmap(bitmap, "png");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //Glide.with(MainActivity.this).load(new File(mBigImagePath)).into(mIv);
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "publishCreateImg: end");
        }


    }

    public Bitmap getScrollViewBitmap(ScrollView scrollView) {
        Log.i(TAG, "getScrollViewBitmap: start");
        //scrollView.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
        //scrollView.fullScroll(ScrollView.FOCUS_UP);//滚动到顶部

        int h = 0;
        Bitmap bitmap;
        //Paint paint = new Paint();
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        bitmap = Bitmap.createBitmap(getScreenWidth(scrollView.getContext()), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#ffffff"));
//		Bitmap bg = BitmapFactory.decodeResource(getResources(), resId);
//		int width = bg.getWidth();
//		int height = bg.getHeight();
//		int left = 10;
//		int top = 0;
//		int ceil = (int) Math.ceil((float) h / (float) height);
//		for (int i = 0; i <= ceil + 1; i++) {
//			// canvas.drawBitmap(bg, left, top += height*i ,paint);
//		}

        scrollView.draw(canvas);
        Log.i(TAG, "getScrollViewBitmap: end");
        Log.i(TAG, "getScrollViewBitmap: width=" +
//				width + "height = " + height +
                "bitmap w=" + bitmap.getWidth() + " h = " + bitmap.getHeight());
        return bitmap;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
