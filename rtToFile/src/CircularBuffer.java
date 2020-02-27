
@SuppressWarnings("unchecked")
public class CircularBuffer<E> {

	private E[] circularQueue;
	private int head;
	private int tail;
	private int currentQueueSize;

	public int getCurrentQueueSize() {
		return currentQueueSize;
	}

	public void setCurrentQueueSize(int currentQueueSize) {
		this.currentQueueSize = currentQueueSize;
	}

	public CircularBuffer(int size) {
		circularQueue = (E[])new Object[size];
		head =-1;
		tail = -1;
		currentQueueSize = 0;
	}

	public void enqueue(E item){
		if(isFull()){
			tail = -1;
		}

		tail = (tail+1)% circularQueue.length;
		circularQueue[tail]= item;
		currentQueueSize++;

		if(head == -1){
			head++;
		}
	}

	public E dequeue() throws Exception{
		if(isEmpty()){
		}

		E dequeuedElement = circularQueue[head];
		//circularQueue[head] = null;
		head = (head+1)%circularQueue.length;
		currentQueueSize--;

		return dequeuedElement;

	}

	public boolean isEmpty() {
		if(currentQueueSize == 0){
			return true;
		}
		return false;
	}

	public boolean isFull() {
		if(currentQueueSize == circularQueue.length){
			return true;
		}
		return false;
	}

}
