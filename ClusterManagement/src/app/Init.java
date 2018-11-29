package app;

import master.Master;
import minion.Minion;
import user.User;

public class Init {

	public static final int USER = 0;
	public static final int MASTER = 1;
	public static final int MINION = 2;

	private int mode;

	public Init(int mode) {
		this.mode = mode;
	}

	public void start(){
		switch(mode) {
			case Init.USER:
				System.out.println("[Init] Running in USER mode");
				User user = new User();
				break;
			case MASTER:
				System.out.println("[Init] Running in MASTER mode.");
				Master master = new Master();
				break;
			case MINION:
				System.out.println("[Init] Running in Minion mode");
				Minion minion = new Minion();
				break;
		}
	}

	public static void main(String[] args) {

		System.out.println("App Running");
		int mode;

		if (args.length == 1) {
			String arg = args[0].toLowerCase();
			switch(arg) {
			case "master":
				mode = Init.MASTER;
				break;
			case "minion":
				mode = Init.MINION;
				break;
			case "user":
				mode = Init.USER;
				break;
			default:
				mode = Init.MASTER;
				break;
			}
		}else {
			mode = Init.MASTER;
		}

		Init init = new Init(mode);
		init.start();

	}
}
