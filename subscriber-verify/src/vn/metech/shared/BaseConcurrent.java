package vn.metech.shared;

public abstract class BaseConcurrent implements Runnable {

	protected int intervalTime;

	protected BaseConcurrent(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	protected abstract void process();

	@Override
	public void run() {
		while ((intervalTime / 1000) > 0) {
			process();

			//
			sleep();
		}
	}

	protected void sleep() {
		try {
			Thread.sleep(intervalTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
