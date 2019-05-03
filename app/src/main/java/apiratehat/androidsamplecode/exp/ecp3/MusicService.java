package apiratehat.androidsamplecode.exp.ecp3;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    public MediaPlayer mediaPlayer;

    class MusicBinder extends Binder{
        public void play(){
            try{
                if (mediaPlayer == null){
                    AssetFileDescriptor descriptor = null;
                    descriptor = getAssets().openFd("music.mp3");

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(descriptor.getFileDescriptor(),
                            descriptor.getStartOffset(), descriptor.getLength());
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                }else {
                    int position = getCurrentProgress();
                    mediaPlayer.seekTo(position);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void pause(){
            if (mediaPlayer!=null && mediaPlayer.isPlaying()){
                mediaPlayer.pause();

            }else if (mediaPlayer!=null && !(mediaPlayer.isPlaying())){
                mediaPlayer.start();
            }
        }
    }
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return new MusicBinder();
    }

    public int getCurrentProgress(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            return mediaPlayer.getCurrentPosition();
        }else if (mediaPlayer!=null &&(!mediaPlayer.isPlaying())){
            return mediaPlayer.getCurrentPosition();
        }else {
            return 0;
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
