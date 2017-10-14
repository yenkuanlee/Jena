import java.util.Date;
import java.io.Serializable;

public class LogEntry implements Serializable{
	public Date date;
	 public boolean query_type;
	 public String input;
	 public long elapsed_time;
	 }