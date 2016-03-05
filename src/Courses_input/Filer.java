package Courses_input;

import java.io.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;


public class Filer {

   public static void Course_update(String ID,int departmentID,int level,int semester,String Name,String Intro,Connection conn) {
		
	    
    	try{
    		    PreparedStatement pst_user =  (PreparedStatement) conn.prepareStatement("INSERT INTO Course(course_id,department_id,level,semester,Course.name,Course.desc) VALUES(?,?,?,?,?,?)");
    			
    	            pst_user.setString(1, ID);
    	            pst_user.setInt(2, departmentID);
    	            pst_user.setInt(3, level);
    	            pst_user.setInt(4, semester);
    	            pst_user.setString(5, Name);
    	            pst_user.setString(6, Intro);
    	            pst_user.execute();
    				
    				
    			
    			
    	}
    	catch (Exception e)
        {
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
        }
	}	
		
   public static void Require_update(String CID,String PID,Connection conn) {
		
	    
   	try{
   		    PreparedStatement pst_user =  (PreparedStatement) conn.prepareStatement("INSERT INTO prerequisites(course_id,prerequisite) VALUES(?,?)");
   			
   	            pst_user.setString(1, CID);
   	            pst_user.setString(2, PID);
   	            
   	            pst_user.execute();
   				
   				
   			
   			
   	}
   	catch (Exception e)
       {
         System.err.println("Got an exception! ");
         System.err.println(e.getMessage());
       }
	}
		
		public static void main(String[] args){
			
			try{
				File file = new File("/Users/joseph/Desktop/tt.txt");
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String myUrl = "jdbc:mysql://is-server-027.ifsm.umbc.edu/UMBCHackthon";
		      
		    Connection conn = DriverManager.getConnection(myUrl, "joseph", "joseph");
			String line=null;
			int lineN=0;
			String CourseID=null,CourseName = null,RequireID=null,CourseContent;
			
			
			while ((line = br.readLine()) != null) {
			if(line.startsWith("MATH ")) {
				lineN=0;
				CourseID=line.split(" - ")[0].replace(" ", "");
				CourseName=line.split(" - ")[1];
				}
			else lineN++;
		    if(lineN==3) {
		    	CourseContent=line;
		    	Course_update(CourseID,3,1,2,CourseName,CourseContent,conn);
		    }
		    if(line.startsWith("Requirement Group")) {
		    	Pattern patt = Pattern.compile("[A-Z]+ [0-9]+[A-Z]?");
		    	Matcher m = patt.matcher(line);
		    	 
		        // if we find a match, get the group 
		        while (m.find())
		        {
		          
		        	RequireID=m.group().replace(" ", "");
		          
		          // print the group out for verification
		          Require_update(CourseID,RequireID,conn);
		        }
		    	
		    };
			
				
				
			}
			conn.close();
			br.close();
			
			}
			catch(Exception e){
				
				System.err.println("Got an exception! ");
			    e.printStackTrace();
				
				
			}
	        
	       
			
		}	
		
	
}