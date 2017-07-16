package pro.marcb.androidjediproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pro.marcb.androidjediproject.R;

public class Calculator extends Fragment{

    private final static String TAG = "Calculator";

    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private Button buttonDot, buttonDel, buttonDiv, buttonMul, buttonSub, buttonSum, buttonEq;

    private TextView textTempResult, textAllExpression;
    private HorizontalScrollView scrollView;

    private String expression = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_calculator, container, false);

        button0 = (Button) v.findViewById(R.id.button0);
        button0.setOnClickListener(numberClick);
        button1 = (Button) v.findViewById(R.id.button1);
        button1.setOnClickListener(numberClick);
        button2 = (Button) v.findViewById(R.id.button2);
        button2.setOnClickListener(numberClick);
        button3 = (Button) v.findViewById(R.id.button3);
        button3.setOnClickListener(numberClick);
        button4 = (Button) v.findViewById(R.id.button4);
        button4.setOnClickListener(numberClick);
        button5 = (Button) v.findViewById(R.id.button5);
        button5.setOnClickListener(numberClick);
        button6 = (Button) v.findViewById(R.id.button6);
        button6.setOnClickListener(numberClick);
        button7 = (Button) v.findViewById(R.id.button7);
        button7.setOnClickListener(numberClick);
        button8 = (Button) v.findViewById(R.id.button8);
        button8.setOnClickListener(numberClick);
        button9 = (Button) v.findViewById(R.id.button9);
        button9.setOnClickListener(numberClick);
        buttonDiv = (Button) v.findViewById(R.id.buttonDiv);
        buttonDiv.setOnClickListener(opClick);
        buttonMul = (Button) v.findViewById(R.id.buttonMul);
        buttonMul.setOnClickListener(opClick);
        buttonSub = (Button) v.findViewById(R.id.buttonSub);
        buttonSub.setOnClickListener(opClick);
        buttonSum = (Button) v.findViewById(R.id.buttonPlus);
        buttonSum.setOnClickListener(opClick);
        buttonEq = (Button) v.findViewById(R.id.buttonEq);
        buttonEq.setOnClickListener(opClick);
        buttonDot = (Button) v.findViewById(R.id.buttonDot);
        buttonDot.setOnClickListener(opClick);
        buttonDel = (Button) v.findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(opClick);

        buttonDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                LinearLayout l = (LinearLayout) v.findViewById(R.id.results);
                TypedValue outValue = new TypedValue();
                getContext().getTheme().resolveAttribute(R.drawable.ripple_effect_clear, outValue, true);
                l.setBackgroundResource(outValue.resourceId);

                expression = "";
                textTempResult.setText("");
                textAllExpression.setText("");

                return false;
            }
        });

        textTempResult = (TextView) v.findViewById(R.id.textActual);
        textAllExpression = (TextView) v.findViewById(R.id.textAcumulat);

        scrollView = (HorizontalScrollView) v.findViewById(R.id.scrollView);

        if (savedInstanceState != null){
            restoreState(savedInstanceState);
        }

        return v;
    }

    private void restoreState(Bundle instance){
        expression = instance.getString("expression");
        textAllExpression.setText(expression);
        scrollView.fullScroll(View.FOCUS_RIGHT);

        textTempResult.setText(String.valueOf(new Tree(expression).eval()));
    }

    private void numberPressed(char c){
        expression += String.valueOf(c);

        textAllExpression.setText(expression);
        scrollView.fullScroll(View.FOCUS_RIGHT);

        textTempResult.setText(String.valueOf(new Tree(expression).eval()));

    }

    private void opPressed(char op){
        expression += String.valueOf(op);
        textAllExpression.setText(expression);
    }

    private View.OnClickListener numberClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button0:
                    numberPressed('0');
                    break;
                case R.id.button1:
                    numberPressed('1');
                    break;
                case R.id.button2:
                    numberPressed('2');
                    break;
                case R.id.button3:
                    numberPressed('3');
                    break;
                case R.id.button4:
                    numberPressed('4');
                    break;
                case R.id.button5:
                    numberPressed('5');
                    break;
                case R.id.button6:
                    numberPressed('6');
                    break;
                case R.id.button7:
                    numberPressed('7');
                    break;
                case R.id.button8:
                    numberPressed('8');
                    break;
                case R.id.button9:
                    numberPressed('9');
                    break;
            }
        }
    };

    private View.OnClickListener opClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.buttonDiv:
                    opPressed('/');
                    break;
                case R.id.buttonMul:
                    opPressed('x');
                    break;
                case R.id.buttonSub:
                    opPressed('-');
                    break;
                case R.id.buttonPlus:
                    opPressed('+');
                    break;
                case R.id.buttonEq:
                    textAllExpression.setText(String.valueOf(new Tree(expression).eval()));
                    expression = "";
                    textTempResult.setText("");

                    Animation bottomToTop = AnimationUtils.loadAnimation(getContext(), R.anim.result_calculator);
                    textAllExpression.startAnimation(bottomToTop);

                    //TODO:
                    break;
                case R.id.buttonDot:
                    expression +='.';
                    break;
                case R.id.buttonDel:
                    //TODO: onLongClickListener
                    expression = expression.substring(0, expression.length()-1);
                    textAllExpression.setText(expression);
                    char last = expression.charAt(expression.length()-1);
                    if (last != '+' && last != '-' && last != 'x' && last != '/'){
                        textAllExpression.setText(expression);
                        scrollView.fullScroll(View.FOCUS_RIGHT);

                        textTempResult.setText(String.valueOf(new Tree(expression).eval()));
                    }
                    break;
            }
        }
    };

    private class Tree {
        //TODO: Problem with op followed by op i.e: 2x-4
        private Tree left;
        private Tree right;
        private boolean isNumber;
        private double number;
        private char op;

        public Tree(String s) {

            int index;
            int index2;
            int lastFound;
            String left;
            String right;

            //Base case: A number
            if (isNumeric(s)) {
                left = null;
                right = null;
                isNumber = true;
                number = Double.valueOf(s);
            } else {
                //We search the last + or /
                index = searchFor('+', s);
                index2 = searchFor('-', s);

                lastFound = index < index2 ? index2 : index;

                if (lastFound != -1) {

                    left = s.substring(0, lastFound);
                    right = s.substring(lastFound + 1, s.length());

                    this.left = left.length()==0? null : new Tree(left);
                    this.right = right.length()==0? null : new Tree(right);

                    isNumber = false;
                    op = s.charAt(lastFound);

                } else {
                    //Search for x or /

                    index = searchFor('x', s);
                    index2 = searchFor('/', s);

                    lastFound = index < index2 ? index2 : index;

                    if (lastFound != -1) {

                        left = s.substring(0, lastFound );
                        right = s.substring(lastFound + 1, s.length());

                        this.left = left.length()==0? null : new Tree(left);
                        this.right = right.length()==0? null : new Tree(right);

                        isNumber = false;
                        op = s.charAt(lastFound);

                    }

                }

            }

        }

        private boolean isNumeric(String str)
        {
            return str.matches("[+-]?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
        }

        private int searchFor(char c, String s){
            int size = s.length();
            int index = -1;
            boolean found = false;
            for (int i = size-1; i >= 0 && !found; --i){
                char el = s.charAt(i);
                if (c == el) {
                    found = true;
                    index = i;
                }
            }
            return index;
        }

        public void print(){
            String print = isNumber? String.valueOf(number) : String.valueOf(op);
            System.out.println(print);
            if (left != null) left.print();
            if (right != null) right.print();

        }

        public double eval() {

            if (isNumber) return number;
            else {
                double left = this.left.eval();
                double right = this.right.eval();
                double result;
                switch (op){
                    case '+':
                        result = left + right;
                        break;
                    case '-':
                        result = left - right;
                        break;
                    case 'x':
                        result = left * right;
                        break;
                    case '/':
                        result = left / right;
                        break;
                    default:
                        return 0;
                }

                return result;
            }

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("expression",expression);
    }

    private int max(int a, int b){
        return a > b? a:b;
    }

}
