import java.awt.Container;

import java.util.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import com.hp.hpl.jena.query.Query ;
import com.hp.hpl.jena.query.QueryExecution ;
import com.hp.hpl.jena.query.QueryExecutionFactory ;
import com.hp.hpl.jena.query.QueryFactory ;
import com.hp.hpl.jena.query.ResultSet ;
import com.hp.hpl.jena.query.ResultSetFormatter ;

@SuppressWarnings("serial")
public class JenaTest extends JFrame implements ActionListener{
String x;
String y;
String temp;

JPanel panel = new JPanel(); //�s�W�@��Panel�e��
JScrollPane scroller = new JScrollPane(panel);

JButton count_btn = new JButton(); //�s�W�u�p��v�����s
JTextArea jTextArea1 = new JTextArea("",15,120); //�s�W��ܵ��G��r����
JTextField field = new JTextField(20); //�s�W�@�Ӥ�r��
Label a1 = new Label("The current sum is 0");
public JenaTest(){
//�]�w���ε{�������D
super("super pioneer");

//���o�����e��
Container c = this.getContentPane();
c.setLayout(new FlowLayout());
//�]�w��r�ؤ��T��
field.setText("Please input a keyword");
//�����r�ءA��K�ϥΪ̧��
field.selectAll();
//�]�w���s��r
count_btn.setText("enter");
//�]�w��r���Ҵ��ܰT��

jTextArea1.setText("");



//�N����[�JPanel��
panel.add(field);
panel.add(count_btn);
panel.add(jTextArea1);


//�NPanel�[�JContainer��
c.add(panel);

c.add(new JScrollPane(jTextArea1));//�ΦW�y�k

setContentPane(c);

//�إ߫��s����ť�ƥ�A�I�s�ۤv�o�Ӫ����O�A�h��@ActionListener
//�M�@��actionPerformed��k
count_btn.addActionListener(this);
}
private static String cut_lines(String input, int width){
	  String output="";
	  for(int i=0; i<input.length();) {
	   if(i+width<input.length()){
	     if(Character.isSpaceChar(input.charAt(i+width))){
	     output+=input.substring(i, i+width)+"\r\n";
	     i+=width+1;
	    }
	     else{
	     int j=i+width;
	     while(j>i && !Character.isSpaceChar(input.charAt(j))) j--;
	     if(j>i){
	       output+=input.substring(i, j)+"\r\n";
	      i=j+1;
	     }
	     else{ // Length of continuous non-spaces is larger than width.
	       output+=input.substring(i, i+width)+"\r\n";
	      i+=width+1;
	     }
	    }
	   }
	    else{
	    output+=input.substring(i, input.length());
	    break;
	   }
	  }
	  return output;
	 }
private static String construct_class_name(String input){
	  String output="";
	  if(input.length()>0){
	   boolean to_upper=true;
	    input=input.replaceAll("^\\s+", "").replaceAll("\\s+$", "").replaceAll("\\s+", "_");
	   for(int i=0; i<input.length(); i++){
	    if(to_upper){
	     output+=Character.toUpperCase(input.charAt(i) );
	     to_upper=false;
	    }
	     else{
	     output+=input.charAt(i) ;
	     if(input.charAt(i) =='_') to_upper=true;
	    }
	   }
	  }
	  return output;
	 }


ObjectInputStream oi;

private static void make_log(boolean q, String s, long t){
	
	LogEntry[] new_log;
	 ObjectOutputStream writer;
	 try{
try{	   ObjectInputStream reader=new ObjectInputStream(new FileInputStream("query.log"));

LogEntry[] old_log;
	  old_log=(LogEntry[])reader.readObject();
	  reader.close();
	  new_log=new LogEntry[old_log.length+1];
	   for(int i=0; i<old_log.length; i++) new_log[i]=old_log[i];
	  new_log[old_log.length]=new LogEntry();
	  new_log[old_log.length].query_type=q;
	  new_log[old_log.length].input=s;
	   new_log[old_log.length].elapsed_time=t;
	  new_log[old_log.length].date=new Date();
}
catch(IOException e){
new_log=new LogEntry[1];
	  new_log[0]=new LogEntry();
	  new_log[0].query_type=q;
	  new_log[0].input=s;
	   new_log[0].elapsed_time=t;
	  new_log[0].date=new Date();
}
	  writer=new ObjectOutputStream(new FileOutputStream("query.log"));
	  writer.writeObject(new_log);
	   writer.close();
	 }
catch(ClassNotFoundException e){}
catch(IOException e){}


	}

private static String[] get_predicate_names(final String[] input){
	  String[] output=input.clone();
	  for(int i=0; i<output.length; i++){
	   int x=output[i].lastIndexOf('/');
	   output[i]=output[i].substring(x+1, output[i].length());
	  }
	  return output;
	 }

class ActionLis extends JFrame implements ActionListener{
	JPanel panel = new JPanel(); //�s�W�@��Panel�e��
	JScrollPane scroller = new JScrollPane(panel);
	JButton count_btn = new JButton(); //�s�W�u�p��v�����s
	JTextArea jTextArea1 = new JTextArea("",15,120); //�s�W��ܵ��G��r����
	JTextArea jTextArea2 = new JTextArea("",15,120);
	JTextField field = new JTextField(20); //�s�W�@�Ӥ�r��
	Label a1 = new Label("The current sum is 0");
	
	
	public ActionLis(String s){
	super("���p�d��");
	Container c = this.getContentPane();
	c.setLayout(new FlowLayout());
	field.setText("Please input a keyword");
	field.selectAll();
	count_btn.setText("enter");
	y=s.substring(0, s.length()-1);
	panel.add(field);
	panel.add(count_btn);
	panel.add(jTextArea1);
	panel.add(jTextArea2);
	c.add(panel);

	c.add(new JScrollPane(jTextArea1));//�ΦW�y�k
	c.add(new JScrollPane(jTextArea2));
	setContentPane(c);

	count_btn.addActionListener(this);
	}
	
	public ActionLis(){

		
	}
	public void actionPerformed(ActionEvent e){
	    if(e.getActionCommand().contains("enter")){
	    	//System.out.println(field.getText());
long t1;
long t2;
t1=System.currentTimeMillis();
			String sparqlQueryString3= 
				  "SELECT * "+
				  "WHERE  "+
				  " { <http://dbpedia.org/resource/"+construct_class_name(field.getText())+"> <http://dbpedia.org/ontology/"+y+"> ?result}"
				  ;
			    Query query3 = QueryFactory.create(sparqlQueryString3);
			    QueryExecution qexec3 = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query3);
			    ResultSet results3 = qexec3.execSelect();
			    java.io.ByteArrayOutputStream baos3= new java.io.ByteArrayOutputStream();
			    ResultSetFormatter.outputAsCSV(baos3, results3);
			   
			    String answer3= baos3.toString();
			    String aaa3=cut_lines(answer3,220);
			    jTextArea2.setText("============="+temp+"=============\n"+aaa3);
				     qexec3.close() ;
				     t2=System.currentTimeMillis();
				     make_log(true,field.getText(),(t2-t1));
	    }
	    else{
		ActionLis k=new ActionLis(e.getActionCommand());
		k.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//�]�wframe��Size
		k.setSize(300,150);
		//�]�wframe���i��
		k.setVisible(true);
		String sparqlQueryString3= 
			  "SELECT * "+
			  "WHERE  "+
			  " { <http://dbpedia.org/resource/"+x+"> <http://dbpedia.org/ontology/"+y+"> ?result}"
			  ;
		    Query query3 = QueryFactory.create(sparqlQueryString3);
		    QueryExecution qexec3 = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query3);
		    ResultSet results3 = qexec3.execSelect();
		    java.io.ByteArrayOutputStream baos3= new java.io.ByteArrayOutputStream();
		    ResultSetFormatter.outputAsCSV(baos3, results3);
		   
		    String answer3= baos3.toString();
		    //System.out.println(answer);
		    // ResultSetFormatter.out(results); 
		    String aaa3=cut_lines(answer3,220);
		    temp=e.getActionCommand();
		    k.jTextArea1.setText("============="+e.getActionCommand()+"=============\n"+aaa3);
		    //String y=ResultSetFormatter.asText(results, query);
			     qexec3.close() ;
			     }//else
	}

	}




public static void main(String[] args) {

	JenaTest frame = new JenaTest();
//�]�wframe�������ƥ�DISPOSE_ON_CLOSE�G
//�N���������t�θ귽���񱼡A����Ҧ��������QDispose�ɡA
//�h�������ε{��
frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//�]�wframe��Size
frame.setSize(300,150);
//�]�wframe���i��
frame.setVisible(true);

}



@Override
public void actionPerformed(ActionEvent e) {
	
	String wtf=field.getText();
	x=construct_class_name(wtf);
	//x=field.getText();
	System.out.println(x);
	long time1=0;
	long time2=0;
	time1=System.currentTimeMillis();
	String sparqlQueryString1= 
		  "SELECT * "+
		  "WHERE  "+
		  " { <http://dbpedia.org/resource/"+x+"> <http://dbpedia.org/ontology/abstract> ?abstract  "+
		  "FILTER langMatches(lang(?abstract),\"en\")}"
		  ;
	    Query query = QueryFactory.create(sparqlQueryString1);
	    QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
	    ResultSet results = qexec.execSelect();
	    java.io.ByteArrayOutputStream baos= new java.io.ByteArrayOutputStream();
	    ResultSetFormatter.outputAsCSV(baos, results);
	   
	    String answer= baos.toString();
	    //System.out.println(answer);
	    // ResultSetFormatter.out(results); 
	    String aaa=cut_lines(answer,220);
	    jTextArea1.setText("\n"+aaa);
	    //String y=ResultSetFormatter.asText(results, query);
		     qexec.close() ;
		     time2=System.currentTimeMillis();
		     System.out.println("time: "+(time2-time1));
		     make_log(false,x,(time2-time1));
	//===============================================realtion
	     
	    // String y=field.getText();
	     String sparqlQueryString2= 
	     	  "SELECT distinct ?p "+
	     	  "WHERE  "+
	     	  " { <http://dbpedia.org/resource/"+x+"> ?p ?o  "+
	     	  "filter regex(?p,\"ontology/[^/]+$\")}"
	     	  ;
	     Query query2 = QueryFactory.create(sparqlQueryString2);
	     QueryExecution qexec2 = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query2);
	     ResultSet results2 = qexec2.execSelect();
	     java.io.ByteArrayOutputStream baos2= new java.io.ByteArrayOutputStream();
	     ResultSetFormatter.outputAsCSV(baos2, results2);
	     String answer2= baos2.toString();
	     qexec2.close() ;
	     String[] temp=answer2.split("\n");
	     String [] array=get_predicate_names(temp);
	     //String temp=cut_string_by_upper(answer2);
	     JButton [] b=new JButton[100];
	     for(int i=1;i<array.length-1;i++){
	     b[i]= new JButton();
	     add(b[i]);
	     b[i].setText(array[i]);
	     b[i].addActionListener(new ActionLis(array[i]));
	     }
	     
	     
	     


}

}

 

