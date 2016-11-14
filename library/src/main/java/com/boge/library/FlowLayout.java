package com.boge.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author boge
 * @version 1.0
 * @date 2016/11/14
 */

public class FlowLayout extends ViewGroup {
    /**
     * 存储行的集合，管理行
     */
    private List<Line> mLines = new ArrayList<>();

    /**
     * 水平和竖直的间距
     */
    private float vertical_space;
    private float horizontal_space;

    /***当前行*/
    private Line line;

    // 行的最大宽度，除去边距的宽度
    private int mMaxWidth;

    public FlowLayout(Context context) {
        this(context,null,0);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        vertical_space = typedArray.getDimension(R.styleable.FlowLayout_vertical_space, 0);
        horizontal_space = typedArray.getDimension(R.styleable.FlowLayout_horizontal_space, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLines.clear();
        line = null;

        // 获取总宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);

        mMaxWidth = width - getPaddingRight() - getPaddingLeft();
        // ******************** 测量孩子 ********************
        // 遍历获取孩子
        int childCount = this.getChildCount();
        Log.i("test", "count:"+childCount);
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 测量孩子的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //测量完需要将孩子添加到管理行的孩子的集合中，将行添加到管理行的集合中
            if(line == null){
                //第一次添加孩子
                line = new Line(mMaxWidth, horizontal_space);
                //添加孩子
                line.addView(childView);
                //添加行
                mLines.add(line);
            } else {
                if(line.canAddView(childView)){
                    line.addView(childView);
                } else {
                    //添加到下一行
                    line = new Line(mMaxWidth, horizontal_space);
                    //添加孩子
                    line.addView(childView);
                    //添加行
                    mLines.add(line);
                }
            }
        }

        // ******************** 测量自己 *********************
        // 测量自己只需要计算高度，宽度肯定会被填充满的
        int height = getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < mLines.size(); i++) {
            // 所有行的高度
            height += mLines.get(i).height;
        }
        // 所有竖直的间距
        height += (mLines.size()-1) * vertical_space;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean b, int l, int t, int r, int buttom) {
        // 这里只负责高度的位置，具体的宽度和子孩子的位置让具体的行去管理

        l = getPaddingLeft();
        t = getPaddingTop();

        for (int k = 0 ; k < mLines.size() ; k++){
            Line line = mLines.get(k);
            line.layout(l, t);

            // 更新高度
            t += line.height;
            if (k != mLines.size() - 1) {
                // 不是最后一条就添加间距
                t += vertical_space;
            }
        }
    }

    public void setAdapter(TagAdapter adapter){
        List<String> list = adapter.getmDataList();
        for (int i = 0 ; i < list.size() ; i++){
            View view = adapter.getView(i, null, this);
            addView(view);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onTagItemClick != null){
                        onTagItemClick.onItemCilck(view, finalI);
                    }
                }
            });
        }
        invalidate();
    }

    public interface OnTagItemClick{
        void onItemCilck(View view, int position);
    }

    private OnTagItemClick onTagItemClick;

    public void setOnTagItemClick(OnTagItemClick onTagItemClick) {
        this.onTagItemClick = onTagItemClick;
    }

    /**
     * 内部类，行管理器，管理每一行的孩子
     */
    public class Line{
        // 定义一个行的集合来存放子View
        private List<View> views = new ArrayList<>();
        // 行的最大宽度
        private int maxWidth;
        // 行中已经使用的宽度
        private int usedWidth;
        // 行的高度
        private int height;
        // 孩子之间的距离
        private float space;

        // 通过构造初始化最大宽度和边距
        public Line(int maxWidth, float horizontalSpace) {
            this.maxWidth = maxWidth;
            this.space = horizontalSpace;
        }

        /**
         * 添加孩子
         * @param childView
         */
        public void addView(View childView) {
            //获取到孩子节点的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            if(views.size() == 0){
                // 集合里没有孩子的时候
                if(childWidth > maxWidth){
                    usedWidth = maxWidth;
                } else {
                    usedWidth = childWidth;
                }
                height = childHeight;
            } else {
                usedWidth += childWidth + space;
                height = childHeight > height ? childHeight : height;
            }
            views.add(childView);
        }

        /**
         * 当前行是否能够添加孩子节点
         * @param childView
         * @return
         */
        public boolean canAddView(View childView) {
            //集合没有数据
            if(views.size() == 0)
                return true;
            //获取到孩子节点的宽和高
            int childWidth = childView.getMeasuredWidth();
            //孩子的宽是否大于该行剩下的宽
            return maxWidth - usedWidth - space > childWidth ? true : false;
        }

        /**
         * 指定孩子显示的位置
         *
         * @param t
         * @param l
         */
        public void layout(int l, int t) {
            //剩下的宽度平分
            int avg = (maxWidth - usedWidth) / views.size();
            for(View view : views){
                //获取到孩子节点的宽和高
                int childWidth = view.getMeasuredWidth();
                int childHeight = view.getMeasuredHeight();
                //把平分的宽度放到View中在重新测量
                view.measure(MeasureSpec.makeMeasureSpec(childWidth + avg, MeasureSpec.EXACTLY)
                            ,MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
                childWidth = view.getMeasuredWidth();
                // 指定位置
                view.layout(l, t, childWidth+l, childHeight+t);

                l += childWidth + space;
            }
        }
    }
}
