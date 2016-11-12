package com.example.azadljy.location;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;


public class MainActivity extends Activity {
    SpeechRecognizer mIat;
    RecognizerListener mRecoListener;
    TextView status;
    EditText content;
    boolean isStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = (TextView) findViewById(R.id.textView);
        content = (EditText) findViewById(R.id.textView2);
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(this, null);
        //2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        //3.开始听写   mIat.startListening(mRecoListener);
        //听写监听器
        mRecoListener = new RecognizerListener() {
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {

            }

            @Override
            public void onBeginOfSpeech() {
                status.setText("正在识别中");
            }

            @Override
            public void onEndOfSpeech() {
                status.setText("结束");
                mIat.startListening(mRecoListener);
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                status.setText("获得结果");
                content.append(JsonParser.parseIatResult(recognizerResult.getResultString()) + "\n");
                Log.e("TAG", "onResult: " + recognizerResult.getResultString());
            }

            @Override
            public void onError(SpeechError speechError) {
                status.setText("错误：" + speechError.getPlainDescription(true));
                mIat.startListening(mRecoListener);
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };

    }

    public void go(View view) {
        mIat.startListening(mRecoListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIat.stopListening();
    }
}
