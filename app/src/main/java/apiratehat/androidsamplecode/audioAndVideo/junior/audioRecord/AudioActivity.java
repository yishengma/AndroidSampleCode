package apiratehat.androidsamplecode.audioAndVideo.junior.audioRecord;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import apiratehat.androidsamplecode.R;

public class AudioActivity extends AppCompatActivity {

    private TextView mTvState;
    private ExecutorService mExecutorService;
    private long mStartRecorderTime, mStopRecordTime;
    private volatile boolean mIsRecording = false;
    private AudioRecord mAudioRecord;
    private FileOutputStream mFileOutputStream;
    private File mAudioFile;
    private byte[] mBuffer;


    private static final int BUFFER_SIZE = 2048;
    private boolean mIsPlaying = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        mExecutorService = Executors.newSingleThreadExecutor();
        mBuffer = new byte[BUFFER_SIZE];


        initView();

    }

    private void initView() {
        mTvState = findViewById(R.id.tv_state);
         Button play = findViewById(R.id.btn_play);
        Button stop = findViewById(R.id.btn_stop);
        Button record = findViewById(R.id.btn_record);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recodeAudio();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recodeAudio();
            }
        });

        //注意申请权限

        if (ContextCompat.checkSelfPermission(AudioActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AudioActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO}, 1);
        }

    }


    private void recodeAudio() {
        if (mIsRecording) {
            mTvState.setText("开始录音");
            mIsRecording = false;

        } else {
            mTvState.setText("停止录音");
            mIsRecording = true;
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {


                    startRecord();
                }
            });
        }
    }

    private void startRecord() {
        if (!doStart()) {
            //提示错误的信息
            recordFail();
        }
    }

    private void stopRecord() {
        mIsRecording = false;
        if (!doStop()) {
            //提示错误的信息
            recordFail();
        }
    }

    private boolean doStop() {
        mAudioRecord.stop();
        mAudioRecord.release();
        mAudioRecord = null;


        mStopRecordTime = System.currentTimeMillis();
        final int send = (int) (mStopRecordTime - mStartRecorderTime) / 1000;
        if (send > 3) {
            mHandler.post(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    mTvState.setText("录音成功：" + send + "秒");

                }
            });
        } else {
            recordFail();
            return false;
        }
        return true;
    }


    private boolean doStart() {
        //下载文件存放的目录

        mStartRecorderTime = System.currentTimeMillis();
//        mAudioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
//                "/recorder/" + System.currentTimeMillis() + ".pcm");
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        //创建一个文件
        mAudioFile = new File(directory +  System.currentTimeMillis() + ".pcm");

        try {
            mAudioFile.createNewFile();
            mFileOutputStream = new FileOutputStream(mAudioFile);
            int audioSource = MediaRecorder.AudioSource.MIC;
            //所有android系统都支持
            int sampleRate = 44100;
            //单声道输入
            int channelConfig = AudioFormat.CHANNEL_IN_MONO;
            //PCM_16是所有android系统都支持的
            int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
            //计算AudioRecord内部buffer最小
            int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
            //buffer不能小于最低要求，也不能小于我们每次我们读取的大小。
            mAudioRecord = new AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, Math.max(minBufferSize, BUFFER_SIZE));


            //开始录音
            mAudioRecord.startRecording();


            while (mIsRecording) {
                int read = mAudioRecord.read(mBuffer, 0, BUFFER_SIZE/**/);
                if (read < 0) {
                    return false;
                } else {
                    mFileOutputStream.write(mBuffer, 0, read);
                }
            }

            //停止录音
            stopRecord();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (mAudioRecord != null) {
                mAudioRecord.release();
            }
        }

        return true;
    }


    private void recordFail() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTvState.setText("失败");
                mIsRecording = false;

            }
        });
    }


    private void play() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                if (!mIsPlaying) {
                    mIsPlaying = true;
                    doPlay();
                }
            }
        });
    }

    private void doPlay() {

        if (mAudioFile != null) {

            //音乐类型，扬声器播放
            int streamType = AudioManager.STREAM_MUSIC;
            //录音时采用的采样频率，所以播放时同样的采样频率
            int sampleRate = 44100;
            //单声道，和录音时设置的一样
            int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
            //录音时使用16bit，所以播放时同样采用该方式
            int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
            //流模式
            int mode = AudioTrack.MODE_STREAM;

            //计算最小buffer大小
            int minBufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);

            //构造AudioTrack  不能小于AudioTrack的最低要求，也不能小于我们每次读的大小
            //构造AudioTrack  可以播放没有原生的 pcm 流

            AudioTrack audioTrack = new AudioTrack(streamType, sampleRate, channelConfig, audioFormat,
                    Math.max(minBufferSize, BUFFER_SIZE), mode);

            //从文件流读数据
            FileInputStream inputStream = null;
            try {
                //循环读数据，写到播放器去播放
                inputStream = new FileInputStream(mAudioFile);

                //循环读数据，写到播放器去播放
                int read;
                //只要没读完，循环播放
                while ((read = inputStream.read(mBuffer)) > 0) {

                    int ret = audioTrack.write(mBuffer, 0, read);
                    //检查write的返回值，处理错误
                    switch (ret) {
                        case AudioTrack.ERROR_INVALID_OPERATION:
                        case AudioTrack.ERROR_BAD_VALUE:
                        case AudioManager.ERROR_DEAD_OBJECT:
                            playFail();
                            return;
                        default:
                            break;
                    }

                    audioTrack.play();
                }

            } catch (Exception e) {
                e.printStackTrace();
                //读取失败
                playFail();
            } finally {
                mIsPlaying = false;
                //关闭文件输入流
                if (inputStream != null) {
                    closeStream(inputStream);
                }
                //播放器释放
                resetQuietly(audioTrack);
            }



        }


    }

    private void closeStream(FileInputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetQuietly(AudioTrack audioTrack) {
        try {
            audioTrack.stop();
            audioTrack.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playFail() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTvState.setText("播放失败");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mExecutorService != null) {
            mExecutorService.shutdownNow();
        }
        if (mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }
    }


    //    private void createAudioRecord() {
//        //参数解析  sampleRateInHz 采样率，每秒钟能够采样的次数，采样率越高，音质越高
//        //          channelConfig  声道配置，声道设置：android支持双声道立体声和单声道。MONO单声道，STEREO立体声
//        //          audioFormat     //采样精度/编码制式和采样大小
//        //编码制式和采样大小：采集来的数据当然使用PCM编码(脉冲代码调制编码，即PCM编码。PCM通过抽样、量化、编码三个步骤将连续变化的模拟信号转换为数字编码。) android支持的采样大小16bit 或者8bit。当然采样大小越大，那么信息量越多，音质也越高，现在主流的采样大小都是16bit，在低质量的语音传输的时候8bit 足够了。
//        mRecordBufSize = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
//
//        //audioSource 音频的来源
//        //
//        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, mRecordBufSize);
//
//        mBuff = new byte[mRecordBufSize];
//        mIsRecording = true;
//
//    }
}

//播放音频 API 的 区别
//MediaPlayer可以播放多种格式的声音文件，例如MP3，AAC，WAV，OGG，MIDI等。MediaPlayer会在framework层创建对应的音频解码器。
// 而AudioTrack只能播放已经解码的PCM流，如果对比支持的文件格式的话则是AudioTrack只支持wav格式的音频文件，
// 因为wav格式的音频文件大部分都是PCM流。AudioTrack不创建解码器，所以只能播放不需要解码的wav文件。
//MediaPlayer ：MediaPlayer在framework层还是会创建AudioTrack，把解码后的PCM数流传递给AudioTrack，
// AudioTrack再传递给AudioFlinger进行混音，然后才传递给硬件播放,所以是MediaPlayer包含了AudioTrack。

//SoundPool 适用于一些短音频
//MediaPlayer 更加适合在后台长时间播放本地音乐文件或者在线的流式资源; SoundPool 则适合播放比较短的音频片段，比如游戏声音、按键声、铃声片段等等，
// 它可以同时播放多个音频; 而 AudioTrack 则更接近底层，提供了非常强大的控制能力，支持低延迟播放，适合流媒体和VoIP语音电话等场景。

