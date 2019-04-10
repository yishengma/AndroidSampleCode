package apiratehat.androidsamplecode.audioAndVideo.junior.MediaCodec;

/**
 * Created by PirateHat on 2019/4/10.
 */

public class MediaCodec {

    //　编解码器能处理的数据类型为：压缩数据、原始音频数据和原始视频数据。
    // 你可以通过ByteBuffers能够处理这三种数据，但是需要你提供一个Surface，
    // 用于对原始的视频数据进行展示，这样也能提高编解码的性能。Surface使用的是本地的视频缓冲区
    // ，这个缓冲区不映射或拷贝到ByteBuffers。这样的机制让编解码器的效率更高。
    // 通常在使用Surface的时候，无法访问原始的视频数据，但是你可以使用ImageReader访问解码后的原始视频帧。
    // 在使用ByteBuffer的模式下，您可以使用Image类和getInput/OutputImage（int）访问原始视频帧。
}
