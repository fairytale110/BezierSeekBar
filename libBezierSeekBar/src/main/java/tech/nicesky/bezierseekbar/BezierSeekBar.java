package tech.nicesky.bezierseekbar;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.text.DecimalFormat;

/**
 * @author fairytale110
 * @class tech/nicesky/bezierseekbar/BezierSeekBar.java:17
 * @date on 2018/10/12-下午1:53
 * @email fairytale110@foxmail.com
 * @description:
 */
public class BezierSeekBar extends View {

    private Context context;
    private DecimalFormat decimalFormat;
    private int mPaddingStart;
    private int mPaddingEnd;
    private int mPaddingTop;
    private int mPaddingBottom;

    private int width;
    private int height;

    /**
     * View默认高度
     */
    private int diameterDefault = 300;

    /**
     * 曲线弧高度
     */
    private float bezierHeight = 50F;

    private float circleRadiusMin = 15F;
    private float circleRadiusMax = circleRadiusMin * 1.5F;
    private float circleRadius = circleRadiusMin;

    /**
     * 圆与线的垂直距离
     */
    private float spaceToLine = circleRadiusMin * 2F;

    /**
     * 触摸点的坐标
     */
    private float fingerX, fingerXDefault, fingerYDefault;

    private float textSelectedSize = 20f;

    private float textSize = 12f;

    private int colorValue, colorValueSelected, colorLine, colorBall, colorBgSelected;

    /**
     * 贝塞尔线的path
     */
    private Path bezierPath;

    /**
     * 曲线画笔
     */
    private Paint bezierPaint, ballPaint, textPaint, textDownPaint;

    /***/
    private Point fingerPoint;

    private Paint txtSelectedBgPaint;

    private RectF bgRect;

    private String unit = "kg";
    private int valueMin, valueMax, valueSelected;
    private boolean animInFinshed = false;

    private ValueAnimator animatorFingerIn, animatorFingerOut;

    private OnSelectedListener selectedListener;

    /**
     * @param context
     */

    public BezierSeekBar(Context context) {
        super(context);
        init(context, null);

    }

    public BezierSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BezierSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BezierSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        decimalFormat = new DecimalFormat("#");
//        diameterDefault = (int) dp2px(context, 300);
//
//        this.circleRadiusMin = dp2px(context, 12F);
//        this.circleRadiusMax = circleRadiusMin * 1.5F;
//        this.circleRadius = circleRadiusMin;
//        this.spaceToLine =  circleRadiusMin *2F;

        textSelectedSize = dp2px(context, 20F);
        textSize = dp2px(context, 12F);

        this.valueMax = 200;
        this.valueMin = 30;

        this.colorBall = this.colorLine = this.colorValue = Color.BLACK;
        this.colorValueSelected = Color.WHITE;
        this.fingerX = 100F;

        initAttr(context, attrs);

        this.bgRect = new RectF();

        this.bezierPaint = new Paint();
        this.bezierPaint.setAntiAlias(true);
        this.bezierPaint.setStyle(Paint.Style.STROKE);
        this.bezierPaint.setColor(colorLine);
        this.bezierPaint.setStrokeWidth(2F);

        this.textPaint = new Paint();
        this.textPaint.setAntiAlias(true);
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setColor(colorValue);
        this.textPaint.setStrokeWidth(2F);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        this.textPaint.setTypeface(font);
        this.textPaint.setTextSize(textSelectedSize);

        this.txtSelectedBgPaint = new Paint();
        this.txtSelectedBgPaint.setAntiAlias(true);
        this.txtSelectedBgPaint.setColor(colorBgSelected);
        this.txtSelectedBgPaint.setStyle(Paint.Style.FILL);

        this.textDownPaint = new Paint();
        this.textDownPaint.setAntiAlias(true);
        this.textDownPaint.setStyle(Paint.Style.FILL);
        this.textDownPaint.setColor(colorValue);
        this.textDownPaint.setStrokeWidth(2F);
        this.textDownPaint.setTextSize(textSize);

        this.ballPaint = new Paint();
        this.ballPaint.setAntiAlias(true);
        this.ballPaint.setStyle(Paint.Style.FILL);
        this.ballPaint.setColor(colorBall);

        this.bezierPath = new Path();

        this.fingerXDefault = 200f;
        this.fingerYDefault = 200f;

        this.bezierPath.moveTo((float) fingerX, (float) 100);

        this.animatorFingerIn = ValueAnimator.ofFloat(0f, 1f);
        this.animatorFingerIn.setDuration(200L);
        this.animatorFingerIn.setInterpolator(new LinearInterpolator());

        this.animatorFingerOut = ValueAnimator.ofFloat(1f, 0f);
        this.animatorFingerOut.setDuration(200L);
        this.animatorFingerOut.setInterpolator(new LinearInterpolator());

        this.animatorFingerIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();


//                //开始
//                bezierHeight = 0f;
//                circleRadius = circleRadiusMin;
//                spaceToLine = circleRadiusMin * 2;
//
//                //结束
//                bezierHeight = circleRadiusMax * 1.5F;
//                circleRadius = circleRadiusMax;
//                spaceToLine = 0;

                animInFinshed = (progress >= 0.15F);
                txtSelectedBgPaint.setAlpha((int) (255 * (progress - 0.15F)));

                if (progress >= 0.95F) {
                    textPaint.setColor(colorValueSelected);
                } else {
                    textPaint.setColor(colorValue);
                }

                bezierHeight = circleRadiusMax * 1.5F * progress;
                circleRadius = circleRadiusMin + (circleRadiusMax - circleRadiusMin) * progress;
                spaceToLine = circleRadiusMin * 2 * (1F - progress);
                postInvalidate();
            }
        });

        this.animatorFingerOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();

                animInFinshed = (progress >= 0.15F);
                txtSelectedBgPaint.setAlpha((int) (255 * (progress - 0.15F)));
                if (progress >= 0.95F) {
                    textPaint.setColor(colorValueSelected);
                } else {
                    textPaint.setColor(colorValue);
                }

                bezierHeight = circleRadiusMax * 1.5F * progress;
                circleRadius = circleRadiusMin + (circleRadiusMax - circleRadiusMin) * progress;
                spaceToLine = circleRadiusMin * 2 * (1F - progress);
                postInvalidate();
            }
        });
    }


    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BezierSeekBar);

            this.colorBall = attributes.getColor(R.styleable.BezierSeekBar_bsBar_color_ball, Color.BLACK);
            this.colorLine = attributes.getColor(R.styleable.BezierSeekBar_bsBar_color_line, Color.BLACK);
            this.colorValue = attributes.getColor(R.styleable.BezierSeekBar_bsBar_color_value, Color.BLACK);
            this.colorValueSelected = attributes.getColor(R.styleable.BezierSeekBar_bsBar_color_value_selected, Color.WHITE);
            this.colorBgSelected = attributes.getColor(R.styleable.BezierSeekBar_bsBar_color_bg_selected, Color.BLACK);
            this.valueMin = attributes.getInteger(R.styleable.BezierSeekBar_bsBar_value_min, 30);
            this.valueMax = attributes.getInteger(R.styleable.BezierSeekBar_bsBar_value_max, 150);
            this.valueSelected = attributes.getInteger(R.styleable.BezierSeekBar_bsBar_value_selected, 65);
            this.unit = attributes.getString(R.styleable.BezierSeekBar_bsBar_unit) + "";
            attributes.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMySize(this.diameterDefault, widthMeasureSpec);

        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            height = this.diameterDefault;
        } else {
            height = this.getMySize(this.diameterDefault, heightMeasureSpec);
        }
        setMeasuredDimension(width, height);

        this.fingerXDefault = width * (float) (this.valueSelected - valueMin) / (float) (this.valueMax - this.valueMin);
        this.fingerX = this.fingerXDefault;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mPaddingStart = getPaddingStart();
        mPaddingEnd = getPaddingEnd();
        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();

        bezierHeight = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //line
        bezierPath.reset();
        bezierPath.moveTo(0, (float) 2 * height / 3);
        bezierPath.lineTo(this.fingerX - circleRadiusMax * 2 * 3, (float) 2 * height / 3);

        //bezier
        bezierPath.moveTo(this.fingerX - circleRadiusMax * 2 * 3, (float) 2 * height / 3);
        bezierPath.cubicTo(this.fingerX - circleRadiusMax * 2 * 2, (float) 2 * height / 3, this.fingerX - circleRadiusMax * 2 * 1, (float) 2 * height / 3 - bezierHeight, this.fingerX, (float) 2 * height / 3 - bezierHeight);

        //bezier
        bezierPath.moveTo(this.fingerX, (float) 2 * height / 3 - bezierHeight);
        bezierPath.cubicTo(this.fingerX + circleRadiusMax * 2, (float) 2 * height / 3 - bezierHeight, this.fingerX + circleRadiusMax * 2 * 2, (float) 2 * height / 3, this.fingerX + circleRadiusMax * 2 * 3, (float) 2 * height / 3);

        //line
        bezierPath.lineTo(width, (float) 2 * height / 3);
        canvas.drawPath(bezierPath, bezierPaint);

        //ball
        canvas.drawCircle(this.fingerX, (float) 2 * height / 3 + spaceToLine + circleRadius, circleRadius, ballPaint);

        canvas.drawText("" + valueMin, 20F, (float) 2 * height / 3F + textSize, textDownPaint);
        canvas.drawText("" + valueMax, width - getTextWidth(textDownPaint, "200") - 20F, (float) 2 * height / 3F + dp2px(getContext(), 12F), textDownPaint);

        String text = valueSelected + unit;


        float valueX = this.fingerX - getTextWidth(textPaint, text) / 2F - 20F;
        float valueXend = fingerX + getTextWidth(textPaint, text) / 2F + 20F;
        if (valueX <= 0) {
            valueX = 0F;
            valueXend = getTextWidth(textPaint, text) + 40F;
        }

        if (valueXend >= width) {
            valueXend = width;
            valueX = width - getTextWidth(textPaint, text) - 40F;
        }
        if (animInFinshed) {
            bgRect.set(valueX,
                    (float) 2 * height / 3F - bezierHeight * 2 - 30F - getTextHeight(textPaint, text),
                    valueXend,
                    (float) 2 * height / 3F - bezierHeight * 2 + 10F);
            canvas.drawRoundRect(bgRect, 20F, 20F, txtSelectedBgPaint);
        }

        canvas.drawText(text, valueX + 20F, (float) 2 * height / 3F - bezierHeight * 2 - 15F, textPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.e("onTouchEvent","ACTION_DOWN");
                fingerX = event.getX();
                if (fingerX < 0F) fingerX = 0F;
                if (fingerX > width) fingerX = width;
                //在这里执行动画
                this.animatorFingerIn.start();
                break;

            case MotionEvent.ACTION_MOVE:
                //Log.e("onTouchEvent","ACTION_MOVE");
                fingerX = event.getX();
                if (fingerX < 0F) fingerX = 0F;
                if (fingerX > width) fingerX = width;
                postInvalidate();
                break;

            case MotionEvent.ACTION_UP:
                //在这里执行动画
                this.animatorFingerOut.start();
                break;
        }

        valueSelected = Integer.valueOf(decimalFormat.format(valueMin + (valueMax - valueMin) * fingerX / width));

        if (selectedListener != null) {
            selectedListener.onSelected(valueSelected);
        }

        return true;
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                mySize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                mySize = size;
                break;
            case MeasureSpec.EXACTLY:
                mySize = size;
                break;
        }
        return mySize;
    }

    private float px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return px / scale + 0.5f;
    }

    private float dp2px(Context context, float dp) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    private float getTextWidth(Paint paint, String str) {
        float iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (float) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    private float getTextHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return (float) rect.height();
    }

    ///////////////////////////////////////////////////////////////


    public void setValueMax(int valueMax) {
        this.valueMax = valueMax;
    }

    public void setValueMin(int valueMin) {
        this.valueMin = valueMin;
    }

    public int getValueMax() {
        return valueMax;
    }

    public int getValueMin() {
        return valueMin;
    }


    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
        this.textDownPaint.setColor(this.colorValue);
    }

    public int getColorValueSelected() {
        return colorValueSelected;
    }

    public void setColorValueSelected(int colorValueSelected) {
        this.colorValueSelected = colorValueSelected;
        this.textPaint.setColor(this.colorValue);
    }

    public int getColorLine() {
        return colorLine;
    }

    public void setColorLine(int colorLine) {
        this.colorLine = colorLine;
        this.bezierPaint.setColor(this.colorLine);
    }

    public int getColorBall() {
        return colorBall;
    }

    public void setColorBgSelected(int colorBgSelected) {
        this.colorBgSelected = colorBgSelected;
        this.txtSelectedBgPaint.setColor(this.colorBgSelected);
    }

    public void setValueSelected(int valueSelected) {
        this.valueSelected = valueSelected;
        this.fingerXDefault = width * (float) (this.valueSelected - valueMin) / (float) (this.valueMax - this.valueMin);
        this.fingerX = this.fingerXDefault;
        postInvalidate();
    }

    public void setColorBall(int colorBall) {
        this.colorBall = colorBall;
        this.ballPaint.setColor(this.colorBall);
    }

    public void setSelectedListener(OnSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    public int getValueSelected(){
        return valueSelected;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}