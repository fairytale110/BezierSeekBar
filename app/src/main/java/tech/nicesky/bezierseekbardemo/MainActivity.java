package tech.nicesky.bezierseekbardemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import tech.nicesky.bezierseekbar.BezierSeekBar;
import tech.nicesky.bezierseekbar.OnSelectedListener;

public class MainActivity extends AppCompatActivity {
    AppCompatTextView txtStatus, txtStatus2;
    LinearLayout fram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = findViewById(R.id.txt_statue);
        txtStatus2 = findViewById(R.id.txt_statue2);
        fram = findViewById(R.id.fram);

        BezierSeekBar seekBar = findViewById(R.id.bsBar_test);

        checkWeight(seekBar.getValueSelected());

        seekBar.setSelectedListener(new OnSelectedListener() {
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

        BezierSeekBar seekBar = new BezierSeekBar(this);
        seekBar.setColorBall(Color.BLACK);
        seekBar.setColorLine(Color.BLACK);
        seekBar.setColorValueSelected(Color.WHITE);
        seekBar.setColorValue(Color.BLACK);
        seekBar.setColorBgSelected(Color.BLACK);
        seekBar.setValueMax(150);
        seekBar.setValueMin(20);
        seekBar.setValueSelected(60);
        seekBar.setUnit("mm");
        seekBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        seekBar.setSelectedListener(new OnSelectedListener() {
            @Override
            public void onSelected(int value) {
                checkLength(value);
            }
        });

        fram.addView(seekBar);
        checkLength(seekBar.getValueSelected());
    }
}
