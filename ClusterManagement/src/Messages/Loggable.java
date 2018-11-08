package Messages;
import db.DBConnector;

public interface Loggable {
	
	public void toDatabase(DBConnector db);

}
