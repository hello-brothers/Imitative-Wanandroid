package imitative.lh.com.wanandroid.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import imitative.lh.com.wanandroid.R;

public class ArcView extends View {

    private Paint paint;
    private float arc_height;
    private int arc_color;
    private int width;
    private int height;
    private Path mPath;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
        arc_height = typedArray.getDimension(R.styleable.ArcView_arc_height, 0);
        arc_color = typedArray.getColor(R.styleable.ArcView_arc_color, 0);
        typedArray.recycle();
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(arc_color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthModel == MeasureSpec.EXACTLY){
            width = widthSize;
        }
        if (heightModel == MeasureSpec.EXACTLY){
            height = heightSize;
        }

        setMeasuredDimension(width, (int) (height+arc_height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.moveTo(0, 0);

        mPath.lineTo(0, height);
        mPath.quadTo(width/2, height+arc_height, width, height);
        mPath.lineTo(width, 0);
        mPath.close();
        canvas.clipPath(mPath);
        canvas.drawPath(mPath, paint);
        super.onDraw(canvas);

    }
}
