package com.denofdevelopers.volumecontrol.screen.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.denofdevelopers.volumecontrol.util.AudioControlHelper;
import com.denofdevelopers.volumecontrol.view.VerticalSeekBar;
import com.denofdevelopers.volumecontroll.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.percentageText)
    TextView percentageText;
    @BindView(R.id.seekBar)
    VerticalSeekBar seekBar;
    @BindView(R.id.percentage10)
    View percentage10;
    @BindView(R.id.percentage20)
    View percentage20;
    @BindView(R.id.percentage30)
    View percentage30;
    @BindView(R.id.percentage40)
    View percentage40;
    @BindView(R.id.percentage50)
    View percentage50;
    @BindView(R.id.percentage60)
    View percentage60;
    @BindView(R.id.percentage70)
    View percentage70;
    @BindView(R.id.percentage80)
    View percentage80;
    @BindView(R.id.percentage90)
    View percentage90;
    @BindView(R.id.percentage100)
    View percentage100;
    @BindView(R.id.editTxtVolume)
    EditText editTxtVolume;
    @BindView(R.id.editTxtLines)
    EditText editTxtLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupUi();
    }

    private void setupUi() {
        updateVolumeControlBars(AudioControlHelper.getCurrentVolume());
        percentageText.setText(getString(R.string.volume_set_at, String.valueOf(AudioControlHelper.getCurrentVolume())));
        updateVolumeDisplay();
    }

    private void updateVolumeDisplay() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar bar) {
                Log.i(TAG, "SeekBar progress onStopTrackingTouch " + bar.getProgress());
            }

            public void onStartTrackingTouch(SeekBar bar) {
                Log.i(TAG, "SeekBar progress onStartTrackingTouch " + bar.getProgress());
            }

            public void onProgressChanged(SeekBar bar, int paramInt, boolean paramBoolean) {
                percentageText.setText(getString(R.string.volume_set_at, String.valueOf(paramInt)));
                updateVolumeControlBars(paramInt);
            }
        });
    }

    @OnClick(R.id.setVolumeBtn)
    public void onSetPercentageButtonClick() {
        processInputValue(editTxtVolume, false);
    }

    @OnClick(R.id.setLinesBtn)
    public void onSetLinesButtonClick() {
        processInputValue(editTxtLines, true);
    }

    private void processInputValue(EditText editText, boolean isLinesInput) {
        String inputString = editText.getText().toString();
        int inputPercentage = Integer.parseInt(inputString);

        if (!TextUtils.isEmpty(inputString)) {
            if (isLinesInput) {
                if (inputPercentage <= 10) {
                    inputPercentage = inputPercentage * 10;
                    updateVolumeControlBars(inputPercentage);
                    percentageText.setText(getString(R.string.volume_set_at, String.valueOf(inputPercentage)));
                    AudioControlHelper.setVolume(inputPercentage);
                    editTxtVolume.setText("");
                } else {
                    Toast.makeText(this, getString(R.string.value_0_10), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (inputPercentage <= 100) {
                    updateVolumeControlBars(inputPercentage);
                    percentageText.setText(getString(R.string.volume_set_at, String.valueOf(inputPercentage)));
                    AudioControlHelper.setVolume(inputPercentage);
                    editTxtLines.setText("");
                } else {
                    Toast.makeText(this, getString(R.string.value_0_100), Toast.LENGTH_SHORT).show();
                }
            }
        }
        hideKeyboard();
    }

    private void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
            }
        } catch (Exception e) {
            Log.w(TAG, e.getCause());
        }
    }

    private void setActiveColor(View view, boolean isActive) {
        if (isActive) {
            view.setBackgroundColor(getResources().getColor(R.color.active));
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.inactive));
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    AudioControlHelper.increaseVolume();
                    updateVolumeControlBars(AudioControlHelper.getCurrentVolume());
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    AudioControlHelper.decreaseVolume();
                    updateVolumeControlBars(AudioControlHelper.getCurrentVolume());
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    private void setPercentageText(int percentage) {
        percentageText.setText(getString(R.string.volume_set_at, String.valueOf(percentage)));
    }

    private void updateVolumeControlBars(int percentage) {
        if (percentage > 0 && percentage <= 10) {
            setActiveColor(percentage10, true);
        } else {
            setActiveColor(percentage10, false);
        }

        if (percentage > 10 && percentage <= 20) {
            setActiveColor(percentage10, true);
            setActiveColor(percentage20, true);
        } else {
            setActiveColor(percentage20, false);
        }
        if (percentage > 20 && percentage <= 30) {
            setActiveColor(percentage10, true);
            setActiveColor(percentage20, true);
            setActiveColor(percentage30, true);
        } else {
            setActiveColor(percentage30, false);
        }
        if (percentage > 30 && percentage <= 40) {
            setActiveColor(percentage10, true);
            setActiveColor(percentage20, true);
            setActiveColor(percentage30, true);
            setActiveColor(percentage40, true);
        } else {
            setActiveColor(percentage40, false);
        }
        if (percentage > 40 && percentage <= 50) {
            setActiveColor(percentage10, true);
            setActiveColor(percentage20, true);
            setActiveColor(percentage30, true);
            setActiveColor(percentage40, true);
            setActiveColor(percentage50, true);
        } else {
            setActiveColor(percentage50, false);
        }
        if (percentage > 50 && percentage <= 60) {
            setActiveColor(percentage10, true);
            setActiveColor(percentage20, true);
            setActiveColor(percentage30, true);
            setActiveColor(percentage40, true);
            setActiveColor(percentage50, true);
            setActiveColor(percentage60, true);
        } else {
            setActiveColor(percentage60, false);
        }
        if (percentage > 60 && percentage <= 70) {
            setActiveColor(percentage10, true);
            setActiveColor(percentage20, true);
            setActiveColor(percentage30, true);
            setActiveColor(percentage40, true);
            setActiveColor(percentage50, true);
            setActiveColor(percentage60, true);
            setActiveColor(percentage70, true);
        } else {
            setActiveColor(percentage70, false);
        }
        if (percentage > 70 && percentage <= 80) {
            setActiveColor(percentage10, true);
            setActiveColor(percentage20, true);
            setActiveColor(percentage30, true);
            setActiveColor(percentage40, true);
            setActiveColor(percentage50, true);
            setActiveColor(percentage60, true);
            setActiveColor(percentage70, true);
            setActiveColor(percentage80, true);
        } else {
            setActiveColor(percentage80, false);
        }
        if (percentage > 80 && percentage <= 90) {
            setActiveColor(percentage10, true);
            setActiveColor(percentage20, true);
            setActiveColor(percentage30, true);
            setActiveColor(percentage40, true);
            setActiveColor(percentage50, true);
            setActiveColor(percentage60, true);
            setActiveColor(percentage70, true);
            setActiveColor(percentage80, true);
            setActiveColor(percentage90, true);
        } else {
            setActiveColor(percentage90, false);
        }
        if (percentage > 90 && percentage <= 100) {
            setActiveColor(percentage10, true);
            setActiveColor(percentage20, true);
            setActiveColor(percentage30, true);
            setActiveColor(percentage40, true);
            setActiveColor(percentage50, true);
            setActiveColor(percentage60, true);
            setActiveColor(percentage70, true);
            setActiveColor(percentage80, true);
            setActiveColor(percentage90, true);
            setActiveColor(percentage100, true);
        } else {
            setActiveColor(percentage100, false);
        }
        AudioControlHelper.setVolume(percentage);
        setPercentageText(percentage);
    }
}
