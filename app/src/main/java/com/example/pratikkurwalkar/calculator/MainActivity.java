
package com.example.pratikkurwalkar.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText newNum;
    EditText result;
    TextView operation;

    private Double operand1 = null;

    private String pendingOperation = "=";

    private static final String KEY_OP = "=";
    private static final String KEY_VAL = "op1";

    private static boolean neg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNum = findViewById(R.id.newNumber);
        result = findViewById(R.id.result);
        operation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonAdd = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);

        Button buttonNeg = findViewById(R.id.buttonNeg);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {        //button reference passed 'v'
                Button any = (Button) v;
                newNum.append(any.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListiner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button any = (Button) v;
                String a = any.getText().toString();

                //a stores value of button

                String b = newNum.getText().toString();

                //b stores value from edittext view

                try {
                    Double doublevalue = Double.valueOf(b); //stores the value of the view
                    performOperation(doublevalue, a);//value of number and operation passed as argument
                }
                //Exception handling done in order to prevent application from crashing if operations performed on
                //just a decimal.
                catch (NumberFormatException e) {
                    newNum.setText("");
                }
                //text is set to blank if just decimal point entered for arithmetic op.

                pendingOperation = a;
                operation.setText(pendingOperation);
            }
        };
        buttonAdd.setOnClickListener(opListiner);
        buttonMinus.setOnClickListener(opListiner);
        buttonDivide.setOnClickListener(opListiner);
        buttonMultiply.setOnClickListener(opListiner);
        buttonEquals.setOnClickListener(opListiner);

        View.OnClickListener negationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (neg) {
                    neg = false;
                    newNum.setText("");
                } else {
                    neg = true;
                    newNum.setText("-");
                }
            }
        };

        buttonNeg.setOnClickListener(negationListener);

        Button buttonClr = findViewById(R.id.buttonClr);
        buttonClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operand1 = null;
                result.setText("");
                newNum.setText("");
                pendingOperation = "=";
                operation.setText("=");
            }
        });

        Button buttonDel = findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = newNum.getText().length();
                if (length > 0) {
                    newNum.getText().delete(length - 1, length);
                }

            }
        });

        ((TextView) findViewById(R.id.buttonSqr)).setText(Html.fromHtml("X<sup>2</sup>"));  //write x^2 on the button
        Button buttonSqr = findViewById(R.id.buttonSqr);
        buttonSqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newNum.length() != 0 || result.length() != 0) {
                    if (result.length() == 0 || newNum.length() != 0) {
                        String a = newNum.getText().toString();
                        Double sqrt = Math.pow(Double.valueOf(a), 2);
                        result.setText(sqrt.toString());
                        newNum.setText("");
                    } else {
                        String a = result.getText().toString();
                        Double sqrt = Math.pow(Double.valueOf(a), 2);
                        result.setText(sqrt.toString());
                    }

                }
            }
        });


    }

    private void performOperation(Double value, String op) {

        if (operand1 == null) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = op;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
            }
            neg = false;
        }
        result.setText(operand1.toString());
        newNum.setText("");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_OP, pendingOperation);
        if (operand1 != null)
            outState.putDouble(KEY_VAL, operand1);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(KEY_OP);
        operation.setText(pendingOperation);
        operand1 = savedInstanceState.getDouble(KEY_VAL);

    }
}