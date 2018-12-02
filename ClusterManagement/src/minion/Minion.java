package minion;

import db.DBConnector;
import errors.ConnectionException;
import messages.Message;
import messages.MessageType;
import messages.MinionLogin;
import messages.MinionRegister;

public class Minion {

	private TCPMinionClient socket;
	private MinionStorage ms;

	private int retryTime = 5000;

	private MinionInfo.Status status = MinionInfo.Status.DISCONNECTED;

	public Minion() {
		ms = new MinionStorage("./minionstorage.txt");
		start();
	}

	private void start() {
		System.out.println("Minion started.");
		connect();
		if (status != MinionInfo.Status.LOGGEDIN) {
			System.out.println("Failed. Trying to start over.");
			retry();
		} else {
			System.out.println("Minion connected. Waiting for messages. Minion ID="+ms.getMinionId()+" Minion PWD="+ms.getMinionCode());
			workingState();
		}
	}

	private void workingState() {
		// spawn thread with the received message;
		boolean listening = true;

		while (listening) {
			Thread t = new Thread(new MinionWorker(socket.receiveMessage(), socket, ms));
			System.out.println("* Message Received. Spawning thread to process it.");
			t.start();
		}

	}

	private void connect() {
		status = MinionInfo.Status.DISCONNECTED;

		setupTCPConnection();
		if (status != MinionInfo.Status.CONNECTED) {
			socket.closeConnection();
			return;
		}
		System.out.println("Minion connected to server.");

		ms.readSettings();
		if (!ms.isRegistered()) {
			System.out.println("Minion storage file does not exist (or is invalid). Trying to register.");
			status = MinionInfo.Status.UNREGISTERED;
			register();
			if (!ms.isRegistered()) {
				System.out.println("Minion tried to register, but failed.");
				ms.deleteSettings();
				socket.closeConnection();
				return;
			}
			System.out.println("Minion registered correctly. Restarting.");
			socket.closeConnection();
			return;
		} else {
			System.out.println("Minion storage file found. Trying to login with these credentials.");
			status = MinionInfo.Status.REGISTERED;
		}

		login();

	}

	private void login() {
		MinionLogin login = new MinionLogin();
		login.setMinionId(ms.getMinionId());
		login.setMinionCode(ms.getMinionCode());
		login.toDatabase(new DBConnector());
		System.out.println("Sending login message to server");
		System.out.println(login.toString());
		socket.sendMessage(login);
		System.out.println("Login message sent. Waiting for response.");
		Message res = socket.receiveMessage();
		System.out.println("> Got answer.");
		if (res.getMsgType() == MessageType.MINION_LOGIN) {
			MinionLogin ans = (MinionLogin) res;
			if (ans.isOk()) {
				System.out.println("Authentication Success. Minion logged in correctly.");
				status = MinionInfo.Status.LOGGEDIN;
				return;
			} else {
				System.out.println("Authentication Failed. Deleting previous credentials, and registering again.");
				ms.deleteSettings();

			}
		}
	}

	private void register() {
		MinionRegister message = new MinionRegister(ms.getMinionId());
		socket.sendMessage(message);
		System.out.println("Sending register message to server.");
		Message res = socket.receiveMessage();
		System.out.println("> Got answer");
		if (res.getMsgType() == MessageType.MINION_REGISTER) {
			MinionRegister ans = (MinionRegister) res;
			if (ans.isCorrect()) {
				System.out.println("Registration message is correct. Saving auth data.");
				ms.setMinionId(ans.getMinionId());
				ms.setMinionCode(ans.getMinionCode());
				ms.setRegistered(true);
				ms.saveSettings();
				status = MinionInfo.Status.REGISTERED;
				return;
			}
		}

	}

	private void retry() {
		try {
			Thread.sleep(retryTime);
			start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void setupTCPConnection() {
		try {
			socket = new TCPMinionClient("localhost", 8001);
			status = MinionInfo.Status.CONNECTED;
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}

//	private void loadConfiguration() {
//		ms = new MinionStorage("./minionstorage.txt");
//		ms.readSettings();
//	}

	public void Test() {
		System.out.println("Running user in test mode");
		Message m = new Message();
		socket.sendMessage(m);
	}

}

class MinionInfo {
	enum Status {
		DISCONNECTED, CONNECTED, UNREGISTERED, REGISTERED, LOGGEDIN
	}
}