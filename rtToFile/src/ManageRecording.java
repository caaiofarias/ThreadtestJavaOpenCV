
import org.apache.log4j.Logger;
import org.bytedeco.javacv.Frame;

public class ManageRecording implements Runnable {
	CircularBuffer<Frame> buffer = new CircularBuffer<Frame>(100);
	static Logger logger = Logger.getLogger("aa.central");
	Producer producer = new Producer(buffer,100);
	Consumer consumer = new Consumer(buffer);

	public void run() {
		// TODO Auto-generated method stub
		Thread t2 = new Thread(producer);
		logger.info("Thread produtor incializada");
		t2.start();

		Thread t = new Thread(consumer);
		logger.info("Thread consumidor incializada");
		t.start();
	}
	
	public static void main(String[] args) {
		ManageRecording rec = new ManageRecording();
		rec.run();

	}
}
      
