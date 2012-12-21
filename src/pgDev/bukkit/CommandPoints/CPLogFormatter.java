package pgDev.bukkit.CommandPoints;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

//This custom formatter formats parts of a log record to a single line
class CPLogFormatter extends Formatter

{

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");

	// This method is called for every log records
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer(5);
		buf.append(calcDate(rec.getMillis()) + " ");
		buf.append(formatMessage(rec) + "\n");
		return buf.toString();
	}

	private String calcDate(long millisecs) {
		Date resultdate = new Date(millisecs);
		return dateFormat.format(resultdate);
	}

	// This method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h) {
		return " ";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h) {
		return "";
	}
}