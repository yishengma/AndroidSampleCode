package apiratehat.androidsamplecode.audioAndVideo.junior.MediaExtractorAndMediaMuxer;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import apiratehat.androidsamplecode.R;

public class ExtractorAndMuxerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extractor_and_muxer);
    }

    //将视频 1 的音频 和视频 2 的视频 合成
    private void combineVideos(String audioVideoPath, long audioStartTime, String videoPath, File combineFile) {


        MediaExtractor audioExtractor = new MediaExtractor();
        int mainAudioExtractorTrackIndex = -1; //视频1 的音频音轨
        int mainAudioMuxerTrackIndex = -1; //合成后的音频的音轨
        int mainAudioMaxInputSize = 0;//能获取音频的最大值

        MediaExtractor videoExtractor = new MediaExtractor();
        int frameExtractorTrackIndex = -1; // 视频 2 的视频轨
        int frameMuxerTrackIndex = -1;//合成后的视频的视频轨
        int frameMaxInputSize = 0;//能获取的视频的最大值

        int frameRate = 0;//视频的帧率

        long frameDuration = 0;

        MediaMuxer muxer = null; // 用于合成音频和视频

        try {
            muxer = new MediaMuxer(combineFile.getPath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            audioExtractor.setDataSource(audioVideoPath); //设置 视频 1  数据源
            int audioTrackCount = audioExtractor.getTrackCount();

            for (int i = 0; i < audioTrackCount; i++) {
                MediaFormat format = audioExtractor.getTrackFormat(i);
                String mimeType = format.getString(MediaFormat.KEY_MIME);
                if (mimeType.startsWith("audio/")) {
                    mainAudioExtractorTrackIndex = i;
                    mainAudioMuxerTrackIndex = muxer.addTrack(format);  //将音轨添加到 MediaMuxer 并返回新的轨道
                    mainAudioMaxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);//
                }
            }


            videoExtractor.setDataSource(videoPath);
            int trackCount = videoExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat format = videoExtractor.getTrackFormat(i);
                String mimeType = format.getString(MediaFormat.KEY_MIME);
                if (mimeType.startsWith("video/")) {
                    frameExtractorTrackIndex = i;
                    frameMuxerTrackIndex = muxer.addTrack(format);
                    frameMaxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                    frameRate = format.getInteger(MediaFormat.KEY_FRAME_RATE);
                    frameDuration = format.getLong(MediaFormat.KEY_DURATION);
                }
            }

            muxer.start();

            audioExtractor.selectTrack(mainAudioExtractorTrackIndex);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer buffer = ByteBuffer.allocate(mainAudioMaxInputSize);
            while (true) {
                int readSampleSize = audioExtractor.readSampleData(buffer, 0);
                if (readSampleSize < 0) {
                    audioExtractor.unselectTrack(mainAudioExtractorTrackIndex);
                    break;
                }

                long sampleTime = audioExtractor.getSampleTime();
                if (sampleTime < audioStartTime) {
                    audioExtractor.advance();
                    continue;
                }

                if (sampleTime > audioStartTime + frameDuration) {
                    break;
                }


                bufferInfo.size = readSampleSize;
                bufferInfo.offset = 0;
                bufferInfo.flags = audioExtractor.getSampleFlags();
                bufferInfo.presentationTimeUs = sampleTime - audioStartTime;


                muxer.writeSampleData(mainAudioMuxerTrackIndex, buffer, bufferInfo);

                audioExtractor.advance();

            }

            videoExtractor.selectTrack(frameExtractorTrackIndex);
            MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer videoByteBuffer = ByteBuffer.allocate(frameMaxInputSize);
            while (true) {
                int readSampleSize = videoExtractor.readSampleData(videoByteBuffer, 0); //检索当前编码的样本并将其存储在字节缓冲区中
                if (readSampleSize < 0) { //如果没有可获取的样本则退出循环
                    videoExtractor.unselectTrack(frameExtractorTrackIndex);
                    break;
                }
                //设置样本编码信息
                videoBufferInfo.size = readSampleSize;
                videoBufferInfo.offset = 0;
                videoBufferInfo.flags = videoExtractor.getSampleFlags();
                videoBufferInfo.presentationTimeUs += 1000 * 1000 / frameRate;

                muxer.writeSampleData(frameMuxerTrackIndex, videoByteBuffer, videoBufferInfo); //将样本写入
                videoExtractor.advance(); //推进到下一个样本，类似快进
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            audioExtractor.release();
            videoExtractor.release();
            if (muxer != null) {
                muxer.release();
            }
        }


    }


}
