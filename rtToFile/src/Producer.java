
import org.apache.log4j.Logger;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class Producer implements Runnable {
	private CircularBuffer<Frame> buffer = new CircularBuffer<Frame>(100);
	protected static Logger logger = Logger.getLogger("aa.central");
	int maxSize;
	String SOURCE_RTSP = "rtsp://admin:12345@10.81.18.50/live4.sdp";

	public Producer(CircularBuffer<Frame> buffer, int size) {
		this.buffer = buffer;
		this.maxSize = size;
	}


	public void run() {

		try{
			logger.debug("this logger show");
			avutil.av_log_set_level(avutil.AV_LOG_QUIET);
			FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(SOURCE_RTSP);
			logger.debug("this doesn't");
			grabber.setOption("stimeout", "10000000");
			grabber.setImageWidth(640);
			grabber.setImageHeight(480);
			grabber.setFrameRate(15);
			grabber.setVideoOption("threads", "8");
			grabber.setVideoCodec(avcodec.AV_CODEC_ID_AAC);
			try{
				grabber.start();
			}
			catch(Exception e){
				e.printStackTrace();
				grabber.close();
			}
			logger.debug("Gravação startada");
			//CanvasFrame canvas = new CanvasFrame("Camera");
			while(true){
				synchronized (this) {
				Frame frame = grabber.grab().clone();
				long timestamp = frame.timestamp;
				//canvas.showImage(frame);
				if(frame == null) continue;
				buffer.enqueue(frame);
				System.out.println(buffer.getCurrentQueueSize());

			}
			}
		}
		catch (Exception e){
			
			e.printStackTrace();
		}
	}
}
