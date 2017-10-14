import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class admin{
 private static byte L=1, M=2, N=4, S=8, X=16;
 private static long count_max_delay(final LogEntry[] usage, boolean query_type){
  long answer=0;
  if(usage!=null){
   if(usage[0].query_type==query_type) answer=usage[0].elapsed_time;
   for(int i=1; i<usage.length; i++) if(usage[i].query_type==query_type && usage[i].elapsed_time>answer) answer=usage[i].elapsed_time;
  }
  return answer;
 }
 private static double count_mean_delay(final LogEntry[] usage, boolean query_type){
  if(usage!=null){
   long answer=0, count=0;
   for(int i=0; i<usage.length; i++){
    if(usage[i].query_type==query_type){
     answer+=usage[i].elapsed_time;
     count++;
    }
   }
   return Math.round(answer/(double)count);
  }
  return 0;
 }
 private static long count_min_delay(final LogEntry[] usage, boolean query_type){
  long answer=0;
  if(usage!=null){
   answer=Long.MAX_VALUE;
   for(int i=0; i<usage.length; i++) if(usage[i].query_type==query_type && usage[i].elapsed_time<answer) answer=usage[i].elapsed_time;
  }
  return answer;
 }
 private static double count_sigma_delay(final LogEntry[] usage, boolean query_type){
  if(usage!=null){
   long count=0;
   double answer=0, mean=count_mean_delay(usage, query_type);
   for(int i=0; i<usage.length; i++){
    if(usage[i].query_type==query_type){
     answer+=Math.pow(usage[i].elapsed_time-mean, 2);
     count++;
    }
   }
   return Math.round(100*Math.sqrt(answer/(double)count))/100;
  }
  return 0;
 }
 private static byte read_flags(final String flag_string){
  byte flags=0;
  if(flag_string!=null){
   for(int i=0; i<flag_string.length(); i++){
    switch(flag_string.charAt(i)){
     case 'L':
     case 'l': flags|=L; break;
     case 'M':
     case 'm': flags|=M; break;
     case 'N':
     case 'n': flags|=N; break;
     case 'S':
     case 's': flags|=S; break;
     case 'X':
     case 'x': flags|=X; break;
    }
   }
  }
  return flags;
 }
 private static LogEntry[] read_log(final String file_name){
  LogEntry[] result=null;
  try{
   ObjectInputStream reader=new ObjectInputStream(new FileInputStream(file_name));
   result=(LogEntry[])reader.readObject();
   reader.close();
  }
  catch(ClassNotFoundException e){
   System.err.println("The log file is corrupted.");
   e.printStackTrace();
  }
  catch(IOException e){
   System.err.println("Cannot open the log file.");
   e.printStackTrace();
  }
  return result;
 }
 public static void main(String[] args){
  byte flags=0;
  LogEntry[] usage=read_log("query.log");
  if(args!=null) flags=read_flags(args[0]);
  if(usage!=null){
   if((flags&L)!=0){
    SimpleDateFormat date_format=new SimpleDateFormat("MM/dd/yyyy");
    for(int i=0; i<usage.length; i++){
     System.out.printf("%d: %s ", i, date_format.format(usage[i].date));
     if(usage[i].query_type) System.out.print("(F)");
     else System.out.print("(R)");
     System.out.printf(" %.3f ", usage[i].elapsed_time/1000.0);
     System.out.println(usage[i].input);
    }
   }
   if((flags&X)!=0){
    System.out.println("Maximum elapsed time: (in seconds)");
    System.out.print("(F): ");
    System.out.println(Double.toString(count_max_delay(usage, false)/1000.0));
    System.out.print("(R): ");
    System.out.println(Double.toString(count_max_delay(usage, true)/1000.0));
   }
   if((flags&N)!=0){
    System.out.println("Minimum elapsed time: (in seconds)");
    System.out.print("(F): ");
    System.out.println(Double.toString(count_min_delay(usage, false)/1000.0));
    System.out.print("(R): ");
    System.out.println(Double.toString(count_min_delay(usage, true)/1000.0));
   }
   if((flags&M)!=0){
    System.out.println("Mean of elapsed time: (in seconds)");
    System.out.print("(F): ");
    System.out.println(Double.toString(count_mean_delay(usage, false)/1000.0));
    System.out.print("(R): ");
    System.out.println(Double.toString(count_mean_delay(usage, true)/1000.0));
   }
   if((flags&S)!=0){
    System.out.println("Standard deviation of elapsed time: (in seconds)");
    System.out.print("(F): ");
    System.out.println(Double.toString(count_sigma_delay(usage, false)/1000.0));
    System.out.print("(R): ");
    System.out.println(Double.toString(count_sigma_delay(usage, true)/1000.0));
   }
  }
 }
}
