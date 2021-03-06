package apiratehat.androidsamplecode.audioAndVideo.junior.MediaExtractorAndMediaMuxer;

/**
 * Created by PirateHat on 2019/4/9.
 */

public class Activity {

//    MediaExtractor的作用是把音频和视频的数据进行分离。
//
//    主要API介绍：
//
//    setDataSource(String path)：即可以设置本地文件又可以设置网络文件
//    getTrackCount()：得到源文件通道数
//    getTrackFormat(int index)：获取指定（index）的通道格式
//    getSampleTime()：返回当前的时间戳
//    readSampleData(ByteBuffer byteBuf, int offset)：把指定通道中的数据按偏移量读取到ByteBuffer中；
//    advance()：读取下一帧数据
//    release(): 读取结束后释放资源

//    MediaMuxer的作用是生成音频或视频文件；还可以把音频与视频混合成一个音视频文件。
//
//    相关API介绍：
//
//    MediaMuxer(String path, int format)：path:输出文件的名称  format:输出文件的格式；当前只支持MP4格式；
//    addTrack(MediaFormat format)：添加通道；我们更多的是使用MediaCodec.getOutpurForma()或Extractor.getTrackFormat(int index)来获取MediaFormat;也可以自己创建；
//    start()：开始合成文件
//    writeSampleData(int trackIndex, ByteBuffer byteBuf, MediaCodec.BufferInfo bufferInfo)：把ByteBuffer中的数据写入到在构造器设置的文件中；
//    stop()：停止合成文件
//    release()：释放资源
}
