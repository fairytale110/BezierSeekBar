package tech.nicesky.bezierseekbardemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import tech.nicesky.bezierseekbar.BezierSeekBar;
import tech.nicesky.bezierseekbar.OnSelectedListener;

public class MainActivity extends AppCompatActivity {
    AppCompatTextView txtStatus, txtStatus2;
    LinearLayout fram;
    private Button btnSetSValue;

    BezierSeekBar seekBarInView;
    BezierSeekBar seekBarCreated;
    int max,min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = findViewById(R.id.txt_statue);
        txtStatus2 = findViewById(R.id.txt_statue2);
        fram = findViewById(R.id.fram);
        seekBarInView = findViewById(R.id.bsBar_test);
        findViewById(R.id.btn_setValueSelected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value2Show = (int)(Math.random()*(max-min+1)+min);
                seekBarInView.setValueSelected(value2Show);
                seekBarCreated.setValueSelected(value2Show);
            }
        });

        max = 150;
        min = 20;

        checkWeight(seekBarInView.getValueSelected());

        seekBarInView.setSelectedListener(new OnSelectedListener() {
            @Override
            public void onSelected(int value) {
                checkWeight(value);
            }
        });
        createBsbar();

    }

    private void checkWeight(int value) {
        if (value > 75) {
            txtStatus.setText("超重");
        } else if (value < 60) {
            txtStatus.setText("偏轻");
        } else {
            txtStatus.setText("平衡");
        }
    }

    private void checkLength(int value) {
        if (value > 85) {
            txtStatus2.setText("金箍棒");
        } else if (value < 50) {
            txtStatus2.setText("绣花针");
        } else {
            txtStatus2.setText("一般般");
        }
    }

    private void createBsbar() {
        fram.setBackgroundColor(Color.WHITE);

        seekBarCreated = new BezierSeekBar(this);
        seekBarCreated.setColorBall(Color.BLACK);
        seekBarCreated.setColorLine(Color.BLACK);
        seekBarCreated.setColorValueSelected(Color.WHITE);
        seekBarCreated.setColorValue(Color.BLACK);
        seekBarCreated.setColorBgSelected(Color.BLACK);
        seekBarCreated.setValueMax(150);
        seekBarCreated.setValueMin(20);
        seekBarCreated.setValueSelected(60);
        seekBarCreated.setUnit("mm");
        seekBarCreated.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        seekBarCreated.setSelectedListener(new OnSelectedListener() {
            @Override
            public void onSelected(int value) {
                checkLength(value);
            }
        });

        fram.addView(seekBarCreated);

        //checkLength(seekBar.getValueSelected());
    }
}
