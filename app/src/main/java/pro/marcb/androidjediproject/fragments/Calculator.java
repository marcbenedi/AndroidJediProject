package pro.marcb.androidjediproject.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import pro.marcb.androidjediproject.R;
import pro.marcb.androidjediproject.SupportClass.Constants;
import pro.marcb.androidjediproject.SupportClass.MiNotificationManager;

public class Calculator extends Fragment {

    private final static String TAG = "Calculator";
    private static SharedPreferences preferences;
    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private Button buttonDot, buttonDel, buttonDiv, buttonMul, buttonSub, buttonSum, buttonEq;
    private TextView textTempResult, textAllExpression;
    private HorizontalScrollView scrollView;
    private String expression = "";
    private LastPressed lastPressed = null;
    private View.OnClickListener numberClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
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
            switch (view.getId()) {
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

                    if (lastPressed == LastPressed.NUMBER) {
                        textAllExpression.setText(String.valueOf(new Tree(expression).eval()));
                        expression = "";
                        textTempResult.setText("");

                        Animation bottomToTop = AnimationUtils.loadAnimation(getContext(), R.anim.result_calculator);
                        textAllExpression.startAnimation(bottomToTop);
                        lastPressed = null;
                    }

                    break;
                case R.id.buttonDot:
                    if (lastPressed == LastPressed.NUMBER) {
                        expression += '.';
                        lastPressed = LastPressed.OPERATOR;
                    }
                    break;
                case R.id.buttonDel:
                    Log.v(TAG, expression + " " + expression.length());
                    if (expression.length() > 1) {
                        expression = expression.substring(0, expression.length() - 1);
                        textAllExpression.setText(expression);
                        char last = expression.charAt(expression.length() - 1);
                        if (last != '+' && last != '-' && last != 'x' && last != '/' && last != '.') {
                            lastPressed = LastPressed.NUMBER;
                            textAllExpression.setText(expression);
                            scrollView.fullScroll(View.FOCUS_RIGHT);

                            textTempResult.setText(String.valueOf(new Tree(expression).eval()));
                        } else lastPressed = LastPressed.OPERATOR;
                    } else {
                        expression = "";
                        textAllExpression.setText(expression);
                        scrollView.fullScroll(View.FOCUS_RIGHT);

                        textTempResult.setText("");
                        lastPressed = null;
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_calculator, container, false);
        setHasOptionsMenu(true);
        preferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
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

                //Intent d'animació guay que no ha funcionat
                //LinearLayout l = (LinearLayout) v.findViewById(R.id.results);
                //TypedValue outValue = new TypedValue();
                //getContext().getTheme().resolveAttribute(R.drawable.ripple_effect_clear, outValue, true);
                //l.setBackgroundResource(outValue.resourceId);

                expression = "";
                textTempResult.setText("");
                textAllExpression.setText("");
                lastPressed = null;

                Toast.makeText(getActivity(), "Cleared!", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        textTempResult = (TextView) v.findViewById(R.id.textActual);
        textAllExpression = (TextView) v.findViewById(R.id.textAcumulat);

        scrollView = (HorizontalScrollView) v.findViewById(R.id.scrollView);

        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }

        return v;
    }

    private void restoreState(Bundle instance) {
        expression = instance.getString(Constants.CALCULATOR_BUNDLE_INSTANCE.EXP);
        lastPressed = (LastPressed) instance.getSerializable(Constants.CALCULATOR_BUNDLE_INSTANCE.LAST);
        textAllExpression.setText(expression);
        scrollView.fullScroll(View.FOCUS_RIGHT);

        Log.v(TAG, "OnRestoreState " + expression);
        if (expression.matches("[+-]?\\d+(.\\d+)?([-+/x][+-]?\\d+(.\\d+)?)+"))
            Log.v(TAG, "matches");
        if (!expression.isEmpty() && expression.matches("[+-]?\\d+(.\\d+)?([-+/x][+-]?\\d+(.\\d+)?)+"))
            textTempResult.setText(String.valueOf(new Tree(expression).eval()));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void numberPressed(char c) {

        lastPressed = LastPressed.NUMBER;

        expression += String.valueOf(c);

        textAllExpression.setText(expression);
        scrollView.fullScroll(View.FOCUS_RIGHT);

        Double temp = new Tree(expression).eval();
        textTempResult.setText(String.valueOf(temp));

        if (temp.isNaN() || temp.isInfinite()) {
            MiNotificationManager.sendNotification(getActivity());
        }

    }

    private void opPressed(char op) {

        if (lastPressed == LastPressed.NUMBER || (lastPressed == null && (op == '+' || op == '-'))) {
            lastPressed = LastPressed.OPERATOR;

            expression += String.valueOf(op);
            textAllExpression.setText(expression);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.CALCULATOR_BUNDLE_INSTANCE.EXP, expression);
        outState.putSerializable(Constants.CALCULATOR_BUNDLE_INSTANCE.LAST, lastPressed);
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calc, menu);
        super.onCreateOptionsMenu(menu, inflater);
        /*String notificationtype = preferences.getString(Constants.SHARED_PREFERENCES.NOTIFICATION_TYPE, Constants.SHARED_PREFERENCES.TOAST);
        if (notificationtype.equals(Constants.SHARED_PREFERENCES.TOAST)){
            MenuItem toast = menu.getItem(R.id.toast);
            toast.setChecked(true);
            MenuItem state = menu.getItem(R.id.estado);
            state.setChecked(false);
        }
        else{
            MenuItem toast = menu.getItem(R.id.toast);
            toast.setChecked(false);
            MenuItem state = menu.getItem(R.id.estado);
            state.setChecked(true);
        }
*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = preferences.edit();

        int id = item.getItemId();
        if (id == R.id.toast) {
            editor.putString(Constants.SHARED_PREFERENCES.NOTIFICATION_TYPE, Constants.SHARED_PREFERENCES.TOAST);
            editor.apply();
            return true;
        } else if (id == R.id.estado) {
            editor.putString(Constants.SHARED_PREFERENCES.NOTIFICATION_TYPE, Constants.SHARED_PREFERENCES.STATE);
            editor.apply();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private enum LastPressed {NUMBER, OPERATOR}

    private class Tree {
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

                if (lastFound != -1 && lastFound > 0) {

                    left = s.substring(0, lastFound);
                    right = s.substring(lastFound + 1, s.length());

                    this.left = left.length() == 0 ? null : new Tree(left);
                    this.right = right.length() == 0 ? null : new Tree(right);

                    isNumber = false;
                    op = s.charAt(lastFound);

                } else {
                    //Search for x or /

                    index = searchFor('x', s);
                    index2 = searchFor('/', s);

                    lastFound = index < index2 ? index2 : index;

                    if (lastFound != -1) {

                        left = s.substring(0, lastFound);
                        right = s.substring(lastFound + 1, s.length());

                        this.left = left.length() == 0 ? null : new Tree(left);
                        this.right = right.length() == 0 ? null : new Tree(right);

                        isNumber = false;
                        op = s.charAt(lastFound);

                    }

                }

            }

        }

        private boolean isNumeric(String str) {
            return str.matches("[+-]?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
        }

        private int searchFor(char c, String s) {
            int size = s.length();
            int index = -1;
            boolean found = false;
            for (int i = size - 1; i >= 0 && !found; --i) {
                char el = s.charAt(i);
                if (c == el) {
                    found = true;
                    index = i;
                }
            }
            return index;
        }

        public void print() {
            String print = isNumber ? String.valueOf(number) : String.valueOf(op);
            System.out.println(print);
            if (left != null) left.print();
            if (right != null) right.print();

        }

        public double eval() {

            if (isNumber) return number;
            else {
                System.out.println("Printejo");
                this.print();
                double left = this.left.eval();
                double right = this.right.eval();
                double result;
                switch (op) {
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

}
