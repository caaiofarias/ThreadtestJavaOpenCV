
import java.awt.image.BufferedImage;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

public class Consumer implements Runnable {
	CircularBuffer<Frame> buffer;

	private FFmpegFrameRecorder recorder;

	public Consumer(CircularBuffer<Frame> buffer){

		this.buffer = buffer;
		
	}


	public void run() {
		try{
			while(true){
				synchronized (buffer) {					
					while(buffer.isEmpty()){
						Thread.sleep(3000);
					}

					if(buffer.getCurrentQueueSize() == 100){
						//reading the audio extracted from event, creating the file and merging audio and video
						//FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("AA_LABS_20191125064408_ch0.flac");
						FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("teste.mp4",640,480);
						recorder.setFrameRate(15);
						recorder.setVideoOption("tune", "zerolatency");
				        recorder.setVideoOption("preset", "ultrafast");
						//recorder.setSampleRate(grabber.getSampleRate());
						
					
				        //recorder.setAudioBitrate(192000);
				        //recorder.setSampleRate(48000);
				        //recorder.setAudioChannels(2);
				        //qrecorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
						//grabber.setFormat("flac");
						//grabber.setAudioChannels(1);
						
						
						//grabber.start();
						recorder.start();
						
						while(!buffer.isEmpty()){
							recorder.record(buffer.dequeue());
							//recorder.record(grabber.grabSamples());
						}
						System.out.println("Arquivo gerado!");
						recorder.stop();
						//grabber.stop();
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}