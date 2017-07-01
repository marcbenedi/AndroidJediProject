package pro.marcb.androidjediproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import pro.marcb.androidjediproject.R;

public class Calculator extends Fragment{

    private final static String TAG = "Calculator";

    private int[] numbers = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                         R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
                        };

    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private Button buttonDot, buttonDel, buttonDiv, buttonMul, buttonSub, buttonSum, buttonEq;

    private TextView textActual, textAcumulat;
    private HorizontalScrollView scrollView;

    private boolean opPressed = false;
    private double number = 0;
    private boolean dot = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calculator, container, false);

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

        textActual = (TextView) v.findViewById(R.id.textActual);
        textAcumulat = (TextView) v.findViewById(R.id.textAcumulat);

        scrollView = (HorizontalScrollView) v.findViewById(R.id.scrollView);

        if (savedInstanceState != null){
            restoreState(savedInstanceState);
        }

        return v;
    }

    private void restoreState(Bundle instance){

    }

    private void updateTextView(char c){
        textActual.setText(textActual.getText().toString()+c);
        textAcumulat.setText(textAcumulat.getText().toString()+c);
        scrollView.fullScroll(View.FOCUS_RIGHT);
    }

    private View.OnClickListener numberClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button0:
                    updateTextView('0');
                    break;
                case R.id.button1:
                    updateTextView('1');
                    break;
                case R.id.button2:
                    updateTextView('2');
                    break;
                case R.id.button3:
                    updateTextView('3');
                    break;
                case R.id.button4:
                    updateTextView('4');
                    break;
                case R.id.button5:
                    updateTextView('5');
                    break;
                case R.id.button6:
                    updateTextView('6');
                    break;
                case R.id.button7:
                    updateTextView('7');
                    break;
                case R.id.button8:
                    updateTextView('8');
                    break;
                case R.id.button9:
                    updateTextView('9');
                    break;
            }
        }
    };

    private View.OnClickListener opClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.buttonDiv:
                    updateTextView('/');
                    break;
                case R.id.buttonMul:
                    updateTextView('x');
                    break;
                case R.id.buttonSub:
                    updateTextView('-');
                    break;
                case R.id.buttonPlus:
                    updateTextView('+');
                    break;
                case R.id.buttonEq:
                    updateTextView('=');
                    break;
                case R.id.buttonDot:
                    updateTextView('.');
                    break;
                case R.id.buttonDel:
                    updateTextView('D');
                    break;
            }
        }
    };

    private boolean isNumber(int id){
        for (int i:numbers) {
            if (i == id)return true;
        }
        return false;
    }
}
