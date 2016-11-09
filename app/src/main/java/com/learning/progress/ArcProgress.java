package com.learning.progress;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liqilin on 2016/11/9.
 */

public class ArcProgress extends View {

    private static final float FULL_ANGLE = 280;

    private Context mContext;
    private Paint mBgPaint;
    private Paint mPgPaint;
    private float mRingWidth;
    private RectF mBgOvalRect;
    private Shader mPgShader;

    private float mProgress = 0.5f;

    public ArcProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public ArcProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public ArcProgress(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        mRingWidth = dip2px(mContext, 16);
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setAntiAlias(true); //消除锯齿
        mBgPaint.setStyle(Paint.Style.STROKE); //绘制空心圆
        mBgPaint.setStrokeWidth(mRingWidth); //设置进度条宽度
        mBgPaint.setColor(Color.parseColor("#FFFFFF")); //设置进度条颜色
        mBgPaint.setStrokeJoin(Paint.Join.ROUND);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND); //设置圆角

        mPgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPgPaint.setAntiAlias(true); //消除锯齿
        mPgPaint.setStyle(Paint.Style.STROKE); //绘制空心圆
        mPgPaint.setStrokeWidth(mRingWidth-dip2px(mContext, 6)); //设置进度条宽度
        mPgPaint.setColor(Color.parseColor("#FFAE24")); //设置进度条颜色
        mPgPaint.setStrokeJoin(Paint.Join.ROUND);
        mPgPaint.setStrokeCap(Paint.Cap.ROUND); //设置圆角
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int sideLength = Math.min(widthSpecSize, heightSpecSize);
        setMeasuredDimension(sideLength, sideLength);

        mBgOvalRect = new RectF(mRingWidth / 2, mRingWidth / 2,
                getWidth() - mRingWidth / 2, getHeight() - mRingWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startAngle = FULL_ANGLE * -23 / 28;
        canvas.drawArc(mBgOvalRect, startAngle, FULL_ANGLE, false, mBgPaint);

        float progress = FULL_ANGLE * mProgress;
        int[] colors = {ContextCompat.getColor(mContext, R.color.yellow_2),
                ContextCompat.getColor(mContext, R.color.yellow_3),
                ContextCompat.getColor(mContext, R.color.yellow_1),
                ContextCompat.getColor(mContext, R.color.yellow_2)};
        float[] pos = {0, 0.25f, 0.3f, 1};// 0 and 1 both joint at the circle's right edge
        mPgShader = new SweepGradient(mBgOvalRect.centerX(), mBgOvalRect.centerY(), colors, pos);
        mPgPaint.setShader(mPgShader);

        canvas.drawArc(mBgOvalRect, startAngle, progress, false, mPgPaint);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setProgress(float progress) {
        mProgress = progress;
        invalidate();
    }

    public float getProgress() {
        return mProgress;
    }
}
