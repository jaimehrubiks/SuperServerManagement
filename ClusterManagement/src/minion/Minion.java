package minion;

import java.io.IOException;
import java.net.ServerSocket;

import errors.ConnectionException;
import master.ClientThread;
import messages.Message;
import messages.MessageType;
import messages.MinionLogin;
import messages.MinionRegister;
import minion.TCPMinionClient;

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
		connect();
		if (status != MinionInfo.Status.LOGGEDIN) {
			retry();
		} else {
			workingState();
		}
	}

	private void workingState() {
		socket.receiveMessage();
		// spawn thread with the received message;
		boolean listening = true;

		while (listening) {
			Thread t = new Thread(new MinionWorker(socket.receiveMessage()));
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

		ms.readSettings();
		if (!ms.isRegistered()) {
			status = MinionInfo.Status.UNREGISTERED;
			register();
			if (!ms.isRegistered()) {
				ms.deleteSettings();
				return;
			}
		} else {
			status = MinionInfo.Status.REGISTERED;
		}

		login();

	}

	private void login() {
		MinionLogin login = new MinionLogin();
		socket.sendMessage(login);
		Message res = socket.receiveMessage();
		if (res.getMsgType() == MessageType.MINION_LOGIN) {
			MinionLogin ans = (MinionLogin) res;
			if (ans.isOk()) {
				status = MinionInfo.Status.LOGGEDIN;
				return;
			} else {
				System.out.println("Authentication Failed. Deleting previous credentials, and registering again.");
				ms.deleteSettings();

			}
		}
	}

	private void register() {
		MinionRegister message = new MinionRegister();
		socket.sendMessage(message);
		Message res = socket.receiveMessage();
		if (res.getMsgType() == MessageType.MINION_REGISTER) {
			MinionRegister ans = (MinionRegister) res;
			if (ans.isCorrect()) {
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
			connect();
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