package fr.enseirb.t2.telecomtheque.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

// this custom formatter formats parts of a log record to a single line
class HtmlFormatter extends Formatter {
  // this method is called for every log records
  public String format(LogRecord rec) {
    StringBuffer buf = new StringBuffer(1000);
    
    if (rec.getLevel().intValue() == Level.WARNING.intValue()) {
      buf.append("<tr class='warning'>\n");
    } else if (rec.getLevel().intValue() == Level.SEVERE.intValue()){
       buf.append("<tr class='danger'>");
    } else if (rec.getLevel().intValue() == Level.INFO.intValue()){
        buf.append("<tr class='info'>");
     }
    buf.append("<td>\n");
    buf.append(rec.getLevel());
    buf.append("</td>\n");
    buf.append("\t<td>");
    buf.append(calcDate(rec.getMillis()));
    buf.append("</td>\n");
    buf.append("\t<td>");
    buf.append(formatMessage(rec));
    buf.append("</td>\n");
    buf.append("</tr>\n");

    return buf.toString();
  }

  private String calcDate(long millisecs) {
    SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
    Date resultdate = new Date(millisecs);
    return date_format.format(resultdate);
  }

  // this method is called just after the handler using this
  // formatter is created
  public String getHead(Handler h) {
      return "<!DOCTYPE html>"
          + "<html>\n"
          + "<meta charset='UTF-8'>"
          + "<head>\n"
          + " <meta charset='UTF-8'>\n"
          + "<link rel='stylesheet' href='/bootstrap.min.css' />\n"
          + "</head>\n"
          + "<body>\n"
          + "<div class='container'>\n"
          + "<h4>" + (new Date()) + "</h4>\n"
          + "<table class='table table-condensed'>\n"
          + "<thead><tr><th>Level</th><th>Time</th> <th>Message</th></tr></thead>\n"
          + "<tbody>\n"
          + "\n";
    }

  // this method is called just after the handler using this
  // formatter is closed
  public String getTail(Handler h) {
    return "</table></div></body></html>";
  }
} 
