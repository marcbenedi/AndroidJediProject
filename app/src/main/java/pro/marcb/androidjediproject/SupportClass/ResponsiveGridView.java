package pro.marcb.androidjediproject.SupportClass;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/* https://developer.android.com/reference/android/view/ViewGroup.html */
public class ResponsiveGridView extends ViewGroup {

    private int mRowCount;
    private int mColCount;

    public ResponsiveGridView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        final TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.rowCount, android.R.attr.columnCount}, 0, 0);

        //Obtenim els atributs "rowCount" i "columnCount" si estan definits o els assignem 1 en cas contrari
        mRowCount = a.getInt(0, 1);
        mColCount = a.getInt(1, 1);

        a.recycle();
    }

    public void setmRowCount(int mRowCount) {
        this.mRowCount = mRowCount;
    }

    //public ResponsiveGridView(Context context){super(context, null);}

    public void setmColCount(int mColCount) {
        this.mColCount = mColCount;
    }
//
//    public ResponsiveGridView(Context context, AttributeSet attrs, int defStyle){
//        super(context, attrs, defStyle);
//
//        final TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.rowCount, android.R.attr.columnCount}, defStyle, 0);
//
//        //Obtenim els atributs "rowCount" i "columnCount" si estan definits o els assignem 1 en cas contrari
//        mRowCount = a.getInt(0, 1);
//        mColCount = a.getInt(1, 1);
//
//        a.recycle();
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();

        //Calculem les mides de la carta
        final int colWidth = Math.round((float) (right - left - paddingLeft - paddingRight) / mColCount);
        final int rowHeight = Math.round((float) (bottom - top - paddingTop - paddingBottom) / mRowCount);

        int iRow = 0, iCol = 0;
        for (int i = 0; i < getChildCount(); ++i) {
            final View child = getChildAt(i);
            //if(child.getVisibility() == View.GONE) continue;

            final MarginLayoutParams childLP = (MarginLayoutParams) child.getLayoutParams();

            final int childTop = iRow * rowHeight + paddingTop + childLP.topMargin;
            final int childBottom = childTop + rowHeight - childLP.topMargin - childLP.bottomMargin;
            final int childLeft = iCol * colWidth + paddingLeft + childLP.leftMargin;
            final int childRight = childLeft + colWidth - childLP.rightMargin - childLP.leftMargin;

            final int childWidth = childRight - childLeft;
            final int childHeight = childBottom - childTop;
            if (child.getMeasuredWidth() != childWidth || child.getMeasuredHeight() != childHeight) {
                child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
            }
            child.layout(childLeft, childTop, childRight, childBottom);

            iRow = (iRow + (iCol + 1) / mColCount) % mRowCount;
            iCol = (iCol + 1) % mColCount;
        }
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

}
