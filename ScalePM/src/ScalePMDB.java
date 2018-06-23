import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;
public class ScalePMDB {
	
	private String sql;
	private ResultSet rs;
	private String[] workstations;
	private String[][] tblValues;
	private String[] rowValue;	 
	private static int indexWorkstations;
	private static int indexSites;
	private static String oldSN;
	private String url;	
	private int rowCount;
	private int columnCount;
	private ArrayList <String> dataList;
	private ScalePMIO IO;
	private String host;
	private String user;
	private String password;
	private String schema;
	private static String connString = "jdbc:mysql://10.52.0.127:3306/ETSInventoryControl?user=etsic&password=3ts1Crw";	

	
	
	
	public void ScaleDB(){
		IO = new ScalePMIO();
		
		rowCount =0;
		columnCount =0;
		indexWorkstations =0;
		indexSites=0;
		 
	}	
	//get site list
	public ResultSet getSitetList() {	
		System.out.println("getSiteList called");	
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(connString);	
			Statement st = conn.createStatement();
		    sql = "select Distinct(Site) from Workstations Where Category!=3";	
		    rs = st.executeQuery(sql); 		  
		    int z =1;
		    while(rs.next()){
		    	z++;    	
			}
		    System.out.println(z +" rows obtained");    
		    rowCount = z;	
		    System.out.println(z +" sites total");
		    rs = st.executeQuery(sql);	    
		    	    
		}catch (Exception e){
			e.printStackTrace();
		}
		return  rs;	
	}
	//load department list
	public ResultSet getDepartmentList(int panel, String site){	
		System.out.println("getDepartmentList called; site is: "+site);		
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		Statement st = conn.createStatement();
		String sql = null;
		if (panel==1){	
			sql ="Select Distinct(Department) from Workstations Where Category !=3 AND Site = '"+site+"'";			
		}else if (panel == 2){						    
			sql= "Select Distinct(Department) from Workstations Where Category = 1 OR Category=2";	    			
		}else if (panel==4){			 	 
			sql = "Select Distinct(Department) from Workstations";	
		}		
		rs = st.executeQuery(sql);
		int z =1;
		while(rs.next()){
			z++;  
			}		
		rowCount = z;		
			rs = st.executeQuery(sql);		
			System.out.println("Number of items: "+rowCount);
		return rs;
		}catch (Exception db){
			db.printStackTrace();
			return null;
		}
	}
	//retrieve list of workstations for a specific department on a specific facility
	public String[] getAllWorkstations(String department, String site){
		System.out.println("getAllWrorkstation called");
		//treat site for query
		if(site.equals("All")){
			site = " IS NOT NULL";
		}else{
			site=" = '"+site+"'";
		}
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(connString);	
			Statement st = conn.createStatement();
		    if(department!=null){
		    	sql = "select Workstations.Workstation from Workstations WHERE Workstations.Department = '"+department+"' AND Workstations.Category!='3' AND Workstations.Visible = 'TRUE' AND Workstations.Site"+site+" Order by  Workstation";	
		    }else{
		    sql = "select Workstations.Workstation from Workstations WHERE Workstations.Category!='3' AND Workstations.Visible = 'TRUE' AND Workstations.Site"+site+" Order by Department, Workstation";	
		    }
		    rs = st.executeQuery(sql);
		    int z =1;
		    while(rs.next()){
		    	z++;    	
			}
		    System.out.println(z +" rows obtained");    
		    rowCount = z;
		    rs = st.executeQuery(sql);    
		    ResultSetMetaData meta = rs.getMetaData();
		    columnCount = meta.getColumnCount();
		    rowValue = new String[z];
		    
		    int i=0;
		    while(rs.next()){
		    	rowValue[i] = rs.getString(1);
		    	i++;
		    }
		    
		   rs.close();
		   conn.close();
		   }
			catch(Exception ex) { 
			   ex.printStackTrace();		   
	       System.out.println("Database connection issue");   	
	       }
		 System.out.println("function complete");
		return  rowValue;	
	}
	//this method retrieves the last SN associated with a specific workstation
	public String lookUpLastSN(String department, String workstation ){
		System.out.println("lookuplastSN called");
		String scaleSN ="";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection  conn=DriverManager.getConnection(connString);	
			conn.setAutoCommit(false);
    	    PreparedStatement stmt = conn.prepareStatement("Select SN from Readings   Where Workstation = ? Order by date Desc");       
    	    stmt.setString(1,workstation);			    		   
		    rs = stmt.executeQuery();
		  conn.commit();
			if(rs.next()){
			scaleSN = rs.getString(1);	
			}
			System.out.println("results saved");
			rs.close();
		}catch (Exception ex){
			ex.printStackTrace();
			System.out.println("Unable to retrieve SN");
		}
		return scaleSN;
	}
	//this method replaced the current SN associated with a workstation
	public void setNewScale(String workstation, String newSN, int reason){
		System.out.println("setNewScale called");
		System.out.println("Workstation: "+workstation+", newSN: "+newSN+" ,oldSN= "+getOldSN()+"reason nr= "+reason);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection  conn=DriverManager.getConnection(connString);	
			conn.setAutoCommit(false);
	    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Scales.Workstation = 'Unknown', Scales.Condition='B' Where  Scales.SN = ?");       
	    stmt.setString(1,getOldSN());	   
	    //if scale was replaced
	    if (reason==2){	   
	    stmt.executeUpdate();
	    conn.commit();
	    System.out.println("1st update executed");
	    stmt.close();
	    conn.close();	   
	    conn=DriverManager.getConnection(connString);	
	    conn.setAutoCommit(false);
	    stmt = conn.prepareStatement("Update Scales Set  Scales.Workstation = ?,  Scales.Condition='G' Where  Scales.SN = ?");       
	    stmt.setString(1,workstation);
	    stmt.setString(2,newSN);	    
	    stmt.executeUpdate();
	    conn.commit();
	    System.out.println("2st update executed");
	    //if SN was mispelled
	    }else{
	    	try{
	    		conn.setAutoCommit(false);
	    	    stmt = conn.prepareStatement("Update Scales Set SN=? Where SN= ?");       
	    	    stmt.setString(1,newSN);	   
	    	    stmt.setString(2, oldSN);
	    	stmt.execute();
	    	conn.commit();
	    	System.out.println("changed SN");
	    	stmt.close();
	    	conn.close();
	    	}catch (Exception u){
	    		conn.setAutoCommit(false);
	    	    stmt = conn.prepareStatement("Insert into Scales (SN) values (?)");       
	    	    stmt.setString(1,newSN);		    	    
	    	stmt.execute();
	    	conn.commit();
	    	System.out.println("added new scale");
	    	stmt.close();
	    	conn.close();
	    	}
	    	try{
	    		conn=DriverManager.getConnection(connString);	
	    		conn.setAutoCommit(false);
    	    stmt = conn.prepareStatement("Update Scales Set Scales.Workstation = 'Unknown', Scales.Condition='B' Where Scales.SN = ?");       
    	    stmt.setString(1,getOldSN());	 	    	 
	    	 stmt.executeUpdate();
	    	 System.out.println("1st update executed");
	    	 conn.commit();
	    	 stmt.close();
	    	 conn.close();	    	 
	    	 conn=DriverManager.getConnection(connString);	
	    	 conn.setAutoCommit(false);
	    	    stmt = conn.prepareStatement("Update Scales Set Scales.Workstation = ?, Scales.Condition='G' Where Scales.SN = ?");       
	    	    stmt.setString(1,workstation);
	    	    stmt.setString(2,newSN);	 	    
	 	    stmt.executeUpdate();
	 	    conn.commit();
	 	    System.out.println("2st update executed");	    	
	    	}catch(Exception w){
	    		System.out.println("Unable to create new entry on scales index");
	    		w.printStackTrace();
	    		conn.close();
	    	}
	    }
	    stmt.close();
	    conn.close();		
	}catch (Exception ex){
		System.out.println("#1 Unable change SN on scales table");
		ex.printStackTrace();		
	}
		System.out.println("Success!");	
	}
//this method inserts a record into the Readings table
public boolean InsertReadingRecord(String tech, String workstation, String SN, double reading, double weight, String notes){
	System.out.println("InsertReadingRecord called");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDateTime dt = LocalDateTime.now();
	String date = dtf.format(dt);
	System.out.println(date);
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
	    PreparedStatement stmt = conn.prepareStatement("Insert into Readings (Tech, SN, Workstation, Reading, Weight, Notes, Date) values (?,?,?,?,?,?,?);");       
	    stmt.setString(1,tech);
	    stmt.setString(2, SN);
	    stmt.setString(3, workstation);
	    stmt.setString(4, String.valueOf(reading));
	    stmt.setString(5, String.valueOf(weight));
	    stmt.setString(6, notes);	
	    stmt.setTimestamp(7,new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
	    stmt.execute();
	    conn.commit();	    
	    System.out.println("Insert createdRecord added");
	    conn.close();	    
	    conn=DriverManager.getConnection(connString);	
	    conn.setAutoCommit(false);	    
	    System.out.println("connection to database established");
	    double error = reading-weight;
	    if(this.getDBValue("Scales", "Category", SN)=="1"){	    	
	    if (Math.abs(error)<=0.02){
	    	stmt = conn.prepareStatement("Update Scales Set Scales.Condition='G', Scales.Last_checked=? Where Scales.SN = ?");
	    }else{
	    	stmt = conn.prepareStatement("Update Scales Set Scales.Condition='C', Scales.Last_checked=? Where Scales.SN = ?");	
	    }
	    }else{
	    	if (Math.abs(error)<=0.1){
	    		stmt = conn.prepareStatement("Update Scales Set Scales.Condition='G', Scales.Last_checked=? Where Scales.SN = ?");
	    	    }else{
	    	    	stmt = conn.prepareStatement(sql = "Update Scales Set Scales.Condition='C', Scales.Last_checked=? Where Scales.SN = ?");	
	    	    }
	    }	     
	    stmt.setString(1,date);	
	    stmt.setString(2,SN);	
	    System.out.println("Insert created");
	    stmt.executeUpdate();
	    conn.commit();
	    System.out.println("Updated scale according to new record");
	    stmt.close();
	    conn.close();
	}catch (Exception ex){
		System.out.println("failure to communicate with database \n");
		ex.printStackTrace();
		return false;
	}
	
	return true;
	
}
//for panel2:
public String[][] getDateReport1(String site, boolean checkBox){
	System.out.println("getDateReport1 called");
	//treat site for query
			if(site.equals("All")){
				site = " IS NOT NULL";
			}else{
				site=" = '"+site+"'";
			}
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
   Statement stmt = conn.createStatement();
    if (checkBox==true){   
  sql = "Select Readings.Date, Readings.Tech, Workstations.Department, Readings.Workstation, Readings.SN, Readings.Reading, Readings.Weight, Readings.Notes "
		  +"From Readings Left Join  Workstations ON Readings.Workstation = Workstations.Workstation "
		  +" Where Workstations.Site"+site+" ORDER BY 1 Desc, 3";
    }else{
    	sql= "Select Readings.Date, Readings.Tech, Workstations.Department, Readings.Workstation, Readings.SN, Readings.Reading, Readings.Weight, Readings.Notes "
    	    		+ "From Readings Left Join Workstations ON Readings.Workstation=Workstations.Workstation WHERE Workstations.SAP='TRUE' AND Workstations.Site"+site    		
    	    		+ "  Order by 1 Desc, 3";
    }
    
    rs = stmt.executeQuery(sql);
    conn.commit();
    int z =1;
    while(rs.next()){
    	z++;    	
	}
    System.out.println(z +" rows obtained");    
    rowCount = z;
    rs = stmt.executeQuery(sql); 
   
    
    ResultSetMetaData meta = rs.getMetaData();
    columnCount = meta.getColumnCount();
    
    //populate values into table
    tblValues = new String[getRowCount()][getColumnCount()];
	
	int o=0;
	int I =0;
	while(rs.next()){
		while (I!=8){
			try{
				if(I==0){
					tblValues[o][I]= rs.getString(1).substring(0, 10);
				}else{
		tblValues[o][I]= rs.getString(I+1);
				}
			}catch (Exception ex){
				tblValues[o][I]="0";
			}							
		I++;
		}
		I=0;
		o++;							
	}	 
	stmt.close();
	conn.close();
	
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();
}
	
return tblValues;
}
public String[][] getDateReport2(String dateRequested,String site, boolean checkBox){
	System.out.println("getDateREport2 called");
	String date = dateRequested;
	String year ="";
	String day="";
	String month="";
	if(date.length()==8){		
			day = date.substring(3, 5);	
			month = date.substring(0,2);	
			year = date.substring(6, 8);
	} else if(date.length()==6){
			month ="0"+date.substring(0,1);
			day = "0"+date.substring(2, 3);
			year = date.substring(4,6);			
	}else if(date.length()==7){
		if(date.charAt(1)=='-'||date.charAt(1)=='/'||date.charAt(1)=='.'){
			month ="0"+date.substring(0,1);
			day = date.substring(2, 4);
			year = date.substring(5,7);	
		}else{
			month =date.substring(0,2);
			day = 0+date.substring(3, 4);
			year = date.substring(5,7);	
		}
		
	}else{
		JOptionPane.showMessageDialog(null, "Improper date size. Try again");
		return null;
	
	}

	date="20"+year+"-"+month+"-"+day;
	
	System.out.println("Date is "+date);
	//treat site for query
		if(site.equals("All")){
			site = " IS NOT NULL";
		}else{
			site=" = '"+site+"'";
		}
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false); 
    PreparedStatement stmt;
    if (checkBox == true){
    	stmt = conn.prepareStatement("Select Readings.Date, Readings.Tech, Workstations.Department, Readings.Workstation, Readings.SN, Readings.Reading, Readings.Weight, Readings.Notes "
    		+ "From Readings LEFT JOIN Workstations ON Readings.Workstation=Workstations.Workstation "    		
    		+ "WHERE Date between ? AND ?  AND Workstations.Site"+site+" Order by 1 Desc, 3");
    }else{
    	stmt = conn.prepareStatement("Select Readings.Date, Readings.Tech, Workstations.Department, Readings.Workstation, Readings.SN, Readings.Reading, Readings.Weight, Readings.Notes "
        		+ "From Readings LEFT JOIN Workstations ON Readings.Workstation=Workstations.Workstation "    		
        		+ "WHERE Date between ? AND ? AND Workstations.SAP = 'TRUE' AND  Workstations.Site"+site+" Order by 1 Desc, 3");
    }            
    stmt.setTimestamp(1,new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
    stmt.setTimestamp(2,new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
        
    rs = stmt.executeQuery();
    conn.commit(); 
   
    int z =1;
    while(rs.next()){
    	z++;    	
	}
    System.out.println(z +" rows obtained");    
    rowCount = z;     
   
    ResultSetMetaData meta = rs.getMetaData();
    columnCount = meta.getColumnCount();
    System.out.println("query executed");
    
    rs = stmt.executeQuery();
    conn.commit(); 
    
    //populate values into table
    tblValues = new String[getRowCount()][getColumnCount()];
	
	int o=0;
	int I =0;
	while(rs.next()){
		while (I!=8){
			try{
				if(I==0){
					tblValues[o][I]= rs.getString(1).substring(0, 10);
				}else{
		tblValues[o][I]= rs.getString(I+1);
				}
			}catch (Exception ex){
				tblValues[o][I]="0";
			}							
		I++;
		}
		I=0;
		o++;							
	}	
	stmt.close();
	conn.close();
    	
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();
}
	
return tblValues;
}

public String[][] getDateReport3(String date1, String date2,String site, boolean checkBox){
	System.out.println("getDateReport3 called");
	String date = date1;
	String year ="";
	String day="";
	String month="";
	if(date.length()==8){		
			day = date.substring(3, 5);	
			month = date.substring(0,2);	
			year = date.substring(6, 8);
	} else if(date.length()==6){
			month ="0"+date.substring(0,1);
			day = "0"+date.substring(2, 3);
			year = date.substring(4,6);			
	}else if(date.length()==7){
		if(date.charAt(1)=='-'||date.charAt(1)=='/'||date.charAt(1)=='.'){
			month ="0"+date.substring(0,1);
			day = date.substring(2, 4);
			year = date.substring(5,7);	
		}else{
			month =date.substring(0,2);
			day = 0+date.substring(3, 4);
			year = date.substring(5,7);	
		}
		
	}else{
		JOptionPane.showMessageDialog(null, "Improper date size. Try again");
	
	}
		
	date="20"+year+"-"+month+"-"+day;
	date1=date;
	date=date2;
	if(date.length()==8){		
		day = date.substring(3, 5);	
		month = date.substring(0,2);	
		year = date.substring(6, 8);
} else if(date.length()==6){
		month ="0"+date.substring(0,1);
		day = "0"+date.substring(2, 3);
		year = date.substring(4,6);			
}else if(date.length()==7){
	if(date.charAt(1)=='-'||date.charAt(1)=='/'||date.charAt(1)=='.'){
		month ="0"+date.substring(0,1);
		day = date.substring(2, 4);
		year = date.substring(5,7);	
	}else{
		month =date.substring(0,2);
		day = 0+date.substring(3, 4);
		year = date.substring(5,7);	
	}
	
}else{
	JOptionPane.showMessageDialog(null, "Improper date size. Try again");

}
	
	date="20"+year+"-"+month+"-"+day;
date2=date;
System.out.println("date1 is "+date1+"\ndate2 is "+date2);
//treat site for query
		if(site.equals("All")){
			site = " IS NOT NULL";
		}else{
			site=" = '"+site+"'";
		}
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false); 
    PreparedStatement stmt;
    if (checkBox==true){
    	stmt = conn.prepareStatement("Select  Readings.Date, Readings.Tech, Workstations.Department, Readings.Workstation, Readings.SN, Readings.Reading, Readings.Weight, Readings.Notes "
    		+ "From Readings LEFT JOIN Workstations ON Readings.Workstation=Workstations.Workstation "    		
    		+ "WHERE Date between ? AND ? AND Workstations.Site"+site+" Order by 1 Desc, 3");
    	 stmt.setTimestamp(1, new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(date1).getTime())); 
    	   stmt.setTimestamp(2,new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(date2).getTime()));
    	
    	    System.out.println("query created");
    	    rs = stmt.executeQuery();
    	    conn.commit();
    }else{
    	stmt = conn.prepareStatement( "Select Readings.Date, Readings.Tech, Workstations.Department, Readings.Workstation, Readings.SN, Readings.Reading, Readings.Weight, Readings.Notes "
    	    		+ "From Readings LEFT JOIN Workstations ON Readings.Workstation=Workstations.Workstation "    		
    	    		+ "WHERE Date between ? AND ?  AND Workstations.SAP = 'TRUE' AND Workstations.Site"+site+" Order by 1 Desc, 3");
    	 stmt.setTimestamp(1, new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(date1).getTime())); 
    	    stmt.setTimestamp(2,new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(date2).getTime()));
    	    System.out.println("query created");
    	    rs = stmt.executeQuery();
    	    conn.commit();
    }     		   
    
    int z =1;
    while(rs.next()){
    	z++;    	
	}
    System.out.println(z +" rows obtained");    
    rowCount = z;
        
    ResultSetMetaData meta = rs.getMetaData();
    columnCount = meta.getColumnCount();
   rs=stmt.executeQuery();
   
   //populate table with values
   
   JOptionPane.showMessageDialog(null, +getRowCount() +" rows obtained");
	tblValues = new String[getRowCount()][getColumnCount()];										
	int o=0;
	int I =0;
	while(rs.next()){
		while (I!=8){
			try{
				if(I==0){
					tblValues[o][I]= rs.getString(1).substring(0, 10);
				}else{
		tblValues[o][I]= rs.getString(I+1);
				}
			}catch (Exception ex){
				tblValues[o][I]="0";
			}							
		I++;
		}
		I=0;
		o++;							
	}								
					
	stmt.close();
	conn.close();
	
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();
}	
return tblValues;
}
//this method 
public String[][] getDepReport(String dep,String site, boolean checkBox){
	System.out.println("getDepReport called");
	//treat site for query
			if(site.equals("All")){
				site = " IS NOT NULL";
			}else{
				site=" = '"+site+"'";
			}
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		Statement stmt = conn.createStatement();
    if(checkBox==true){    	
    sql = "Select Readings.Date, Readings.Tech, Workstations.Workstation, Readings.SN, Readings.Reading, Readings.Weight, Readings.Notes "
    		+ "From Readings LEFT JOIN Workstations ON Readings.Workstation=Workstations.Workstation WHERE Workstations.Department = '"+dep+"' AND Workstations.Site"+site+"  Order by 1 Desc, 3";
    }else{
    	sql = "Select Readings.Date, Readings.Tech, Workstations.Workstation, Readings.SN, Readings.Reading, Readings.Weight, Readings.Notes "
        		+ "From Readings LEFT JOIN Workstations ON Readings.Workstation=Workstations.Workstation WHERE Workstations.Department = '"+dep+"' AND Workstations.SAP= 'TRUE' AND Workstations.Site"+site+" Order by 1 Desc, 3";
    }
    System.out.println("query created");
    rs = stmt.executeQuery(sql);    
    int z =1;
    while(rs.next()){
    	z++;    	
	}
    System.out.println(z +" rows obtained");    
    rowCount = z;
    rs = stmt.executeQuery(sql);    
    ResultSetMetaData meta = rs.getMetaData();
    columnCount = meta.getColumnCount();
    
    //populate table with values
    JOptionPane.showMessageDialog(null, +getRowCount() +" rows obtained");
	tblValues = new String[getRowCount()][getColumnCount()];										
	int o=0;
	int I =0;
	while(rs.next()){
		while (I!=7){
			try{
				if(I==0){
					tblValues[o][I]= rs.getString(1).substring(0, 10);
				}else{
		tblValues[o][I]= rs.getString(I+1);
				}
			}catch (Exception ex){
				tblValues[o][I]="0";
			}							
		I++;
		}
		I=0;
		o++;							
	}	
	
	stmt.close();
	conn.close();
	
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();}
	
return tblValues;
}

//this method provides a report for a particular workstation
public String[][] getWorkstationReport(String workstation){
	System.out.println("getWorkstationReport called");
	System.out.println("Workstation Selected is: "+workstation);
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		Statement stmt = conn.createStatement();
    sql = "Select Date, Tech, SN, Reading, Weight, Notes "
    		+ "From Readings LEFT JOIN Workstations ON Readings.Workstation=Workstations.Workstation WHERE Readings.Workstation = '"+workstation+"' Order by 1 Desc";	
    rs = stmt.executeQuery(sql);   
    int z =1;
    while(rs.next()){
    	z++;    	
	}
    System.out.println(z +" rows obtained");    
    rowCount = z;
    rs = stmt.executeQuery(sql);    
    ResultSetMetaData meta = rs.getMetaData();
    columnCount = meta.getColumnCount();
    
    //populating table with values
    JOptionPane.showMessageDialog(null, getRowCount() +" rows obtained");
	tblValues = new String[getRowCount()][getColumnCount()];										
	int o=0;
	int I =0;
	while(rs.next()){
		while (I!=6){
			try{
				if(I==0){
					tblValues[o][I]= rs.getString(1).substring(0, 10);
				}else{
		tblValues[o][I]= rs.getString(I+1);
				}
			}catch (Exception ex){
				tblValues[o][I]="0";
			}							
		I++;
		}
		I=0;
		o++;							
	}	
	  
	stmt.close();
	conn.close();
	
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();
}	
return tblValues;
}
//this method provides a report for a single scale
public String[][] get1ScaleReport(String sn){
	System.out.println("get1ScaleReport called");
	System.out.println("Sn entered: "+sn);
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("Select Readings.Date, Readings.Tech, Readings.Workstation, Readings.Reading, Readings.Weight, Readings.Notes "
    		+ "From Readings LEFT JOIN Workstations ON Readings.Workstation=Workstations.Workstation WHERE Readings.SN = ? Order by 1 Desc");       
    stmt.setString(1,sn);       
    System.out.println("query created");
    rs = stmt.executeQuery(); 
    conn.commit();
    int z =1;
    while(rs.next()){
    	z++;    	
	}
    System.out.println(z +" rows obtained");    
    rowCount = z;
    rs = stmt.executeQuery(); 
    conn.commit();
    ResultSetMetaData meta = rs.getMetaData();
    columnCount = meta.getColumnCount();
    
    //getting values for table
    if (getRowCount()==0){
    	JOptionPane.showMessageDialog(null,"No data was found for that SN. Please check value entered and try again");
		return null;
	}else{
	JOptionPane.showMessageDialog(null, getRowCount() +" rows obtained");
	tblValues = new String[getRowCount()][getColumnCount()];
	}
	int o=0;
	int I =0;
	while(rs.next()){
		while (I!=6){
			try{
				if(I==0){
					tblValues[o][I]= rs.getString(1).substring(0, 10);
				}else{
		tblValues[o][I]= rs.getString(I+1);
				}
			}catch (Exception ex){
				tblValues[o][I]="0";
			}							
		I++;
		}
		I=0;
		o++;							
	}	  
	stmt.close();
	conn.close();
	
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();
}	
return tblValues;
}
public String[][] getScaleInventory(int category, String site){
	System.out.println("getScalesIventory() called");
	String restrictiveClause = null; //filters results on scale category and site 
	if (category == 0){
		restrictiveClause = "WHERE";
	}else if(category == 1){
		restrictiveClause = "WHERE Scales.Category=1 AND";
	}else if(category == 2){
		restrictiveClause = "WHERE Scales.Category=2 AND";
	}else if(category == 3){
		restrictiveClause = "WHERE Scales.Category=3 AND";
	}
	if (site.equals("Show all Sites")){
		restrictiveClause += " Site IS NOT NULL";
	}else{
		restrictiveClause += " Site = '"+site+"'";
	}
	
	
		
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		Statement stmt = conn.createStatement();    
    sql = "Select Scales.SN, Scales.Workstation, Scales.Category, Scales.Condition, Scales.Last_checked, Scales.Capacity, Scales.Model, Scales.Manufacturer, Scales.Warranty_end, Scales.Purchase_date, Scales.Dimensions "
    		+ "From Scales  LEFT JOIN Workstations  ON Scales.Workstation=Workstations.Workstation "+restrictiveClause+" Order by Scales.Category, Scales.Condition, Workstations.Department, Scales.Model";   
    System.out.println("query created");
    rs = stmt.executeQuery(sql);    
    int z =1;
    while(rs.next()){
    	z++;    	
	}
    System.out.println(z +" rows obtained");    
    rowCount = z;
    stmt.close();
    rs.close();
    stmt = conn.createStatement();
    rs = stmt.executeQuery(sql);    
    ResultSetMetaData meta = rs.getMetaData();
    columnCount = meta.getColumnCount();
    
    //populating the table values
    tblValues = new String[getRowCount()][12];
    int r=0;
	int c=0;	
	while(rs.next()){
		while (c!=11){
			try{			
			if (c==8||c==9){
				try{
				String datedb = rs.getString(c+1).substring(0, 10);
				tblValues[r][c]=datedb;
				}catch (Exception n){
					tblValues[r][c]=null;
				}
			}else if (c==3){
				try{
					char C = rs.getString(4).charAt(0);
					if(C == 'G'){
						tblValues[r][c]="Good";
					} else if (C=='C'){
						tblValues[r][c]="Check";
					}else if (C=='F'){
						tblValues[r][c]="Shipped";								
					}else if (C=='B'){
						tblValues[r][c]="Bad";
					}else if(C=='Z'){
						tblValues[r][c]="Decommissioned";
					}
					}catch (Exception o){
						System.out.println("value not accepted for ["+r+"]["+c+"]");
					}
				}else{
					try{
					tblValues[r][c]=rs.getString(c+1);
					}catch (Exception d){
						System.out.println("value not accepted for ["+r+"]["+c+"]");
					}
				}
			}catch (Exception ex){
			tblValues[r][c]="0";
			ex.printStackTrace();
			}
	c++;		
	}
c=0;
r++;
}			

    
    System.out.println("Table scaleInventory ready");  
	stmt.close();
	conn.close();
	
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();	
	
}	
	return tblValues;
}
public boolean updateDBRow(String sn, String workstation,String category, String condition, String capacity,String model, String manufacturer,String warranty, String purchased,String dimensions){
	System.out.println("updateDBRow() called\nSN is "+sn);
	String oldSN = "";
	boolean success = true;
	String errorMessage = "";
	try{
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("Select SN  FROM Scales WHERE Workstation = ?");       
    stmt.setString(1,workstation);   	   	    
    stmt.executeQuery();
  conn.commit();
    if (rs.next()){
    	oldSN = rs.getString(1);
    }
    stmt.close();
    if(!oldSN.equals(sn)){
    	 conn.setAutoCommit(false);
    	    stmt = conn.prepareStatement("Update Scales Set Workstation = 'Parts Room' WHERE SN = ?");       
    	    stmt.setString(1,oldSN);   	      	  
    	stmt.executeUpdate();
    	conn.commit();
    	stmt.close();
    	conn.setAutoCommit(false);
	    stmt = conn.prepareStatement("Update Scales Set Workstation = ? Where SN = ?");       
	    stmt.setString(1,workstation);   
	    stmt.setString(2,sn);    	
    	stmt.executeUpdate();
    	conn.commit();
    	stmt.close();
    }
	}catch (Exception a){
		a.printStackTrace();
		success = false;
		errorMessage +="\nUnable to change workstation assigned to scale";
	}
	try{		
		Connection conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
		    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Scales.Condition = ? WHERE SN =?");       
		    stmt.setString(1,condition);
		    stmt.setString(2,sn);	   	
    	stmt.executeUpdate();
    	conn.commit();
    	stmt.close();
	}catch (Exception b){
		b.printStackTrace();
		success = false;
		 errorMessage +="\nUnable to modify condition";
	}
	try{		
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
		    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Capacity = ? WHERE SN =?");       
		    stmt.setString(1,capacity);
		    stmt.setString(2,sn);
   	   	stmt.executeUpdate();
   	   	conn.commit();
   	stmt.close();
	}catch (Exception b){
		b.printStackTrace();
		success = false;
		 errorMessage +="\nCapacity not defined properly"
		 		+ "";
	}
	try{
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
		    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Model = ? WHERE SN =?");       
		    stmt.setString(1,model);
		    stmt.setString(2,sn);   	
   	stmt.executeUpdate();
   	conn.commit();
   	stmt.close();
	}catch (Exception b){
		b.printStackTrace();
		success = false;
		 errorMessage +="\nUnable to modify Model";
	}
    	
	try{
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
		    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Manufacturer = ? WHERE SN =?");       
		    stmt.setString(1,manufacturer);
		    stmt.setString(2,sn);  	
   	stmt.executeUpdate();
   	conn.commit();
   	stmt.close();
	}catch (Exception b){
		b.printStackTrace();
		success = false;
		 errorMessage +="\nUnable to modify manufacturer";
	}
	if(purchased!= null){
	try{
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
		    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Purchase_Date = ? WHERE SN =?");       
		   stmt.setTimestamp(1, new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(purchased).getTime()));
		    stmt.setString(2,sn);   
   	stmt.executeUpdate();
   	conn.commit();
   	stmt.close();
	}catch (Exception b){
		b.printStackTrace();
		success = false;
		 errorMessage +="\nUnable to modify purchased date";
	}
	}
	
	try{
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
		    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Dimensions = ? WHERE SN =?");       
		    stmt.setString(1,dimensions);
		    stmt.setString(2,sn);		   
		stmt.executeUpdate();
		conn.commit();
		stmt.close();
		}catch (Exception b){
			b.printStackTrace();
			success = false;
			errorMessage +="\nUnable to modify dimensions";
			}
	if((warranty!= null)){
try{
	Connection conn=DriverManager.getConnection(connString);	
	conn.setAutoCommit(false);
	    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Warranty_end=? WHERE SN =?");       
	    stmt.setTimestamp(1, new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(warranty).getTime())); 
	    stmt.setString(2,sn);	
	stmt.executeUpdate();
	conn.commit();
	stmt.close();
}catch (Exception b){
	b.printStackTrace();
	success = false;
	 errorMessage +="\nUnable to modify warranty termination date";
}
	}

try{
	Class.forName("com.mysql.jdbc.Driver");
	Connection  conn=DriverManager.getConnection(connString);	
	conn.setAutoCommit(false);
	    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Category=? WHERE SN =?");       
	    stmt.setString(1,category); 
	    stmt.setString(2, sn); 	
	stmt.executeUpdate();
	conn.commit();
	stmt.close();
}catch (Exception b){
	b.printStackTrace();
	success = false;
	 errorMessage +="\nUnable to modify warranty termination date";
}
try{
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDateTime dt = LocalDateTime.now();
	String date = dtf.format(dt);
	System.out.println(date);
	Connection  conn=DriverManager.getConnection(connString);	
	conn.setAutoCommit(false);
	    PreparedStatement stmt = conn.prepareStatement("Update Scales Set Last_checked =? WHERE SN =?");       
	    stmt.setString(1,date); 
	    stmt.setString(2,sn); 	
	stmt.executeUpdate();
	conn.commit();
	stmt.close();
}catch (Exception b){
	b.printStackTrace();
	success = false;
	 errorMessage +="\nUnable to modify warranty termination date";
}
	 if(success != true){
		 JOptionPane.showMessageDialog(null, errorMessage);
	 }
	 return success;
}

public boolean SNExists(String SN) {
	System.out.println(" SNExists() called\nSN is "+SN);
	boolean success = false;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("Select SN FROM Scales WHERE SN=?" );       
    stmt.setString(1,SN);     
    rs = stmt.executeQuery(); 
    conn.commit();
    System.out.println("query executed");
    int rowCount =0;
    while(rs.next()){
    	rowCount++;    	
	}
    System.out.println(rowCount +" rows obtained");
    if(rowCount>0){    
    success = true;    
    }else{
    	success= false;    	
    }
	}catch (Exception j){
		System.out.println("Unable to update row");
		j.printStackTrace();		
	}
 return success;
}
public boolean workstationExists(String workstation){
	System.out.println(" workstationExists() called\nworkstation is "+workstation);
	boolean success = false;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("Select Workstation FROM Workstations WHERE Workstation= ?" );       
    stmt.setString(1,workstation);     
    rs = stmt.executeQuery(); 
    conn.commit();
    System.out.println("query executed");
    int rowCount =0;
    while(rs.next()){
    	rowCount++;    	
	}
    System.out.println(rowCount +" rows obtained");
    if(rowCount>0){    
    success = true;
    }else{
    	success= false;
    }
	}catch (Exception j){
		System.out.println("Unable to update row");
		j.printStackTrace();		
	}
 return success;
}
public String[] EditScales(String value){	//value refers to a SN or workstation
	System.out.println("EditScales() called");
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("Select SN, Workstation, Category, Condition,  Manufacturer, Model, Capacity, Warranty_end, Purchase_date, Dimensions From Scales  WHERE SN = ? OR Workstation = ?");       
    stmt.setString(1,value);  
    stmt.setString(2,value);        
    rs = stmt.executeQuery(); 
    conn.commit();
    rowValue = new String[10];
    int i = 0;
   if(rs.next()){
    	rowValue[0] = rs.getString(1);
    	rowValue[1] = rs.getString(2);
    	rowValue[2] = rs.getString(3);
    	rowValue[3] = rs.getString(4);
    	rowValue[4] = rs.getString(5);
    	rowValue[5] = rs.getString(6);
    	rowValue[6] = rs.getString(7);
    	rowValue[7] = rs.getString(8);
    	rowValue[8] = rs.getString(9);
    }
  	stmt.close();
  	rs.close();
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();
}	
	return rowValue;
}
public boolean AddNewScale(String SN, String category){	
	System.out.println("Add New Scale() called");
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("Insert into Scales (SN, Category) values (?,?)");       
    stmt.setString(1,SN); 
    stmt.setString(2,category);    
    stmt.execute();    
    conn.commit();
    System.out.println("Insert executed"); 
    stmt.close();
    conn.close();   
    }catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();	
	return false;
}	
	return true;
}

public boolean updateDBRow(String table, String column, String value, String pkValue) {
	System.out.println("updateDBRow called");
	String pk ="";
	if(table=="Scales"){
		pk= "SN";
	}else{
	pk="Workstation"; 	
	}
	try{  	
	
	System.out.println("updateDBRow() called\npk is "+pk);
	Class.forName("com.mysql.jdbc.Driver");
	Connection  conn=DriverManager.getConnection(connString);	
	System.out.println("connection to database established");
    Statement stmt = conn.createStatement();  
    sql = "Update "+table+" Set "+table+"."+column+" = '"+value+"' Where "+table+"."+pk+" = '"+pkValue+"'";
    stmt.executeUpdate(sql);
    System.out.println("Update successful");
	stmt.close();
	conn.close();
	}catch (Exception e){
		e.printStackTrace();
		return false;
	}
	return true;
}
public boolean updateDBRow(String workstation, String department, String site, int category) {
	try{
	System.out.println("updateDBRow() called");
	Class.forName("com.mysql.jdbc.Driver");
	Connection conn=DriverManager.getConnection(connString);	
	Statement stmt = conn.createStatement();  
    sql = "Update Workstations Set Department='"+department+"', Site= '"+site+"', Visible = 'true', Category= '"+String.valueOf(category)+"' Where Workstation = '"+workstation+"'";
    stmt.executeUpdate(sql);
    System.out.println("Update successful");
	stmt.close();
	conn.close();
	}catch (Exception e){
		e.printStackTrace();
		return false;
	}
	return true;
	
}
public String getDBValue (String table, String column, String pk){
	System.out.println("getDBValue called");
	int operation = (table == "Scales")? 1 : 2;
	String returnValue="";
	try{
	System.out.println("getDBValue() called\nSN is "+pk);
	Class.forName("com.mysql.jdbc.Driver");
	Connection conn=DriverManager.getConnection(connString);	
	conn.setAutoCommit(false);
    PreparedStatement stmt;
    if (operation == 1){
    	stmt = conn.prepareStatement( "Select Scales.Workstation, Scales.Manufacturer, Scales.Model, Scales.Capacity, Scales.Purchase_date, Scales.Warranty_end, Scales.Condition, Scales.Dimensions, Scales.Last_checked, Scales.Category FROM Scales Where Scales.SN =?");
        }else{
        	stmt = conn.prepareStatement( "Select Department,SAP, Visible FROM Workstations Where Workstation =?");
        }      
    stmt.setString(1, pk);     
    rs=stmt.executeQuery();
    conn.commit();
    System.out.println("Query successful");
    if (rs.next()){
    	returnValue = rs.getString(column);
    }
	stmt.close();
	conn.close();
	}catch (Exception e){
		e.printStackTrace();
	}
	return returnValue;
}
public String[][] getWorkstationIventory(int category, String site){
	System.out.println("getworkstationIventory() called");
	String restrictiveClause = null;
	if (category == 0){
		restrictiveClause = "WHERE ";
	}else if(category == 1){
		restrictiveClause = "WHERE Workstations.Category=1 AND";
	}else if(category == 2){
		restrictiveClause = "WHERE Workstations.Category=2 AND";
	}else if(category == 3){
		restrictiveClause = "WHERE Workstations.Category=3 AND";		
	}
	if(site.equals("Show all Sites")){
		restrictiveClause +=" Workstations.Site IS NOT NULL";
	}else{
		restrictiveClause +=" Workstations.Site = '"+site+"'";
	}
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		Statement stmt = conn.createStatement();    
    sql = "Select Workstations.Workstation, Scales.SN, Workstations.Department, Workstations.Site, Workstations.Category "
    		+ "From Workstations LEFT JOIN Scales  ON Scales.Workstation=Workstations.Workstation "+restrictiveClause+" Order by 4, 3 "; 
    rs = stmt.executeQuery(sql);    
    int z =1;
    while(rs.next()){
    	z++;    	
	}
    System.out.println(z +" rows obtained");    
    rowCount = z;
    rs = stmt.executeQuery(sql);    
    ResultSetMetaData meta = rs.getMetaData();
    columnCount = meta.getColumnCount();
  
    
    //populating values into table
    tblValues = new String[getRowCount()][5];
    int r=0;
	int c=0;
	while(rs.next()){
		while (c!=5){
			try{	
				tblValues[r][c]=rs.getString(c+1);							
			}catch (Exception ex){
			tblValues[r][c]="0";
			ex.printStackTrace();
			}
	c++;		
	}
c=0;
r++;
}				

    
	stmt.close();
	conn.close();
	
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();
}	
	return tblValues;	
}
//get information for the station to be edited
public String[] editStations(String workstation){
	System.out.println("editStations called");
	
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("Select Workstations.Workstation, Scales.SN, Workstations.Department, Workstations.Site, Workstations.Category "
    		+ "From Workstations LEFT JOIN Scales  ON Scales.Workstation=Workstations.Workstation Where Workstations.Workstation = ?");       
    stmt.setString(1, workstation);     
    rs = stmt.executeQuery();
    conn.commit();
    int z =1;
    while(rs.next()){
    	z++;    	
	}
    System.out.println(z +" rows obtained");    
    rowCount = z;
    rs = stmt.executeQuery();    
    ResultSetMetaData meta = rs.getMetaData();
    columnCount = meta.getColumnCount();
    rowValue = new String[6];
if (rs.next()){
	rowValue[0] = rs.getString(1);
	rowValue[1] = rs.getString(2);
	rowValue[2] = rs.getString(3);
	rowValue[3] = rs.getString(4);
	rowValue[4] = rs.getString(5);	
}
	stmt.close();
	conn.close();
	
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();
}	
	return rowValue;	

}
//get all sites

public boolean AddNewWorkstation(String workstation, String category) {
	System.out.println("Add New Workstation() called");
	try{
		Class.forName("com.mysql.jdbc.Driver");		
		Connection conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("Insert into Workstations (Workstation, Category, Visible, SAP) values ( ?, ?, 'TRUE', ?)");       
    stmt.setString(1, workstation);
    stmt.setString(2, category);
    if(category.equals("1")){
    	stmt.setString(3, "true");
    }else{
    	stmt.setString(3, "false");
    }
    stmt.execute(); 
    conn.commit();    
    stmt.close();
    conn.close();
      
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();	
	return false;
}	
	return true;
}
public boolean removeStation(String station) {
	System.out.println("Add New Workstation() called");
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("DELETE FROM Workstations WHERE Workstation = ?");       
    stmt.setString(1, station);  
   stmt.execute();
   conn.commit();
    stmt.close();
    conn.close();
     
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();	
	return false;
}	
	return true;
}
public String getScaleOnStation(String stationName) {
	System.out.println("getScaleOnStation called");
	String sn="";
	try{
		Class.forName("com.mysql.jdbc.Driver");
		 Connection conn=DriverManager.getConnection(connString);	
		 Statement st = conn.createStatement();
	    sql = "select SN FROM Scales Where Workstation = '"+stationName+"'";	
	    rs = st.executeQuery(sql); 	
	    if(rs.next()){
	    	sn=rs.getString(1);
	    }
	}catch (Exception n){
		n.printStackTrace();
	}
	return sn;
}

public String getWorkstation(String columnName, String value) {
	System.out.println("getWorkstation called");
	String station="";
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		Statement st = conn.createStatement();
	    sql = "select Workstation FROM Workstations Where "+columnName+" = '"+value+"'";	
	    rs = st.executeQuery(sql); 	
	    if(rs.next()){
	    	station=rs.getString(1);
	    }
	}catch (Exception n){
		n.printStackTrace();
	}
	return station;
}	
//retrieves data about all troubleshooting records to be displayed on panel5
public String[][] getRepairRecords(int category, String site) {
	System.out.println("getrepairRecords() called");
	String station="";
	if (site.equals("Show all Sites")){
		site = "IS NOT NULL";
	}else{
		site="='"+site+"'";
	}
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		Statement st = conn.createStatement();
	   	
	    if(category==1){
	    	sql = "Select Repairs.RepairID, Repairs.Down_Since, Repairs.SN, Repairs.Repair_Status, Repairs.Symptom, Repairs.Root_Cause, Repairs.Resolution, Repairs.Repaired_Date, Repairs.Repaired_By  FROM Repairs LEFT JOIN Scales ON Repairs.SN = Scales.SN LEFT JOIN Workstations ON Workstations.Workstation=Scales.Workstation WHERE Scales.Category=1 AND Workstations.site "+site+" ORDER BY 1 DESC";	
	    }else if (category==2){
	    	sql = "Select Repairs.RepairID, Repairs.Down_Since, Repairs.SN, Repairs.Repair_Status, Repairs.Symptom, Repairs.Root_Cause, Repairs.Resolution, Repairs.Repaired_Date, Repairs.Repaired_By FROM Repairs LEFT JOIN Scales ON Repairs.SN = Scales.SN LEFT JOIN Workstations ON Workstations.Workstation=Scales.Workstation  WHERE Scales.Category=2 AND Workstations.Site  "+site+" ORDER BY 1 DESC";
	    }else if (category==3){
	    	sql = "Select Repairs.RepairID, Repairs.Down_Since, Repairs.SN, Repairs.Repair_Status, Repairs.Symptom, Repairs.Root_Cause, Repairs.Resolution, Repairs.Repaired_Date, Repairs.Repaired_By FROM Repairs LEFT JOIN Scales ON Repairs.SN = Scales.SN LEFT JOIN Workstations ON Workstations.Workstation=Scales.Workstation  WHERE Scales.Category=3 AND Workstations.Site  "+site+" ORDER BY 1 DESC";
	    }else{
	    	sql = "Select Repairs.RepairID, Repairs.Down_Since, Repairs.SN, Repairs.Repair_Status, Repairs.Symptom, Repairs.Root_Cause, Repairs.Resolution, Repairs.Repaired_Date, Repairs.Repaired_By FROM Repairs LEFT JOIN Scales ON Repairs.SN = Scales.SN LEFT JOIN Workstations ON Workstations.Workstation=Scales.Workstation  WHERE Workstations.Site "+site+" ORDER BY 1 DESC";	
	    }
	    rs = st.executeQuery(sql); 	
	    int z =1;
	    while(rs.next()){
	    	z++;    	
		}
	    System.out.println(z +" rows obtained");    
	    rowCount = z;	    
	    rs = st.executeQuery(sql);
	    
	    //generating table with data
	    tblValues = new String[getRowCount()][9];		
		int r=0;
		int c=0;
		while(rs.next()){
			while (c!=9){
				try{							
				
				if (c==1||c==7){
					try{
					String datedb = rs.getString(c+1).substring(0, 10);
					tblValues[r][c]=datedb;
					}catch (Exception n){
						tblValues[r][c]=null;
					}
				}else if (c==3){
						char C = rs.getString(4).charAt(0);
						if(C == '1'){
							tblValues[r][c]="N Inspected";
						} else if (C=='2'){
							tblValues[r][c]="In Progress";
						}else if (C=='3'){
							tblValues[r][c]="RMA Req.";								
						}else if (C=='4'){
							tblValues[r][c]="Shipped Out";
						}else if(C=='5'){
							tblValues[r][c]="Completed";
						}else{
							tblValues[r][c]="Unknown";
						}
					}else{
						tblValues[r][c]=rs.getString(c+1);
					}
				}catch (Exception ex){
				tblValues[r][c]="0";
				ex.printStackTrace();
				}
		c++;		
		}
	c=0;
	r++;
	}				
	rs.close();
	    
	}catch (Exception n){
		n.printStackTrace();
	}	
	return tblValues;
}
//retrieves a troubleshooting record to load data into objects on panel5
public String[] getRepairRecords(String ID){
	System.out.println("getrepairRecords() called");
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
	    PreparedStatement stmt = conn.prepareStatement("Select Down_Since, SN, Repair_Status, Symptom,Root_Cause, Resolution, Repaired_Date, Repaired_By  FROM Repairs  WHERE RepairID = ?");       
	    stmt.setInt(1, Integer.parseInt(ID)); 
	    rs = stmt.executeQuery();	    
	    conn.commit();
	    rowValue = new String[8];
		if (rs.next()){
			rowValue[0] = rs.getString(1);
			rowValue[1] = rs.getString(2);	
			rowValue[2] = rs.getString(3);	
			rowValue[3] = rs.getString(4);	
			rowValue[4] = rs.getString(5);	
			rowValue[5] = rs.getString(6);	
			rowValue[6] = rs.getString(7);	
			rowValue[7] = rs.getString(8);	
			
		}
		for (int i = 0; i<8; i++){
			System.out.println(rowValue[i]);
		}
		rs.close();
		stmt.close();
	}catch (Exception n){
		n.printStackTrace();
	}
	return rowValue;
}
//checks if record requested for update exists 
public boolean repairRecordExists(String RID) {
	System.out.println("repairRecordExists() called");
	boolean success = false;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = conn.prepareStatement("Select * FROM Repairs WHERE RepairID = ?");       
    stmt.setInt(1, Integer.parseInt(RID));  
   rs = stmt.executeQuery();
   conn.commit();   
   while(rs.next()){
   	rowCount++;    	
	}   
   if(rowCount>0){    
   success = true;
   }else{
   	success= false;
   }
    stmt.close();
    conn.close();        
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();	
	return false;
}	
	return success;
}
//adds troubleshooting records to db
public boolean addRepairRecord(String sn, String status, String tech, String inDate, String outDate, String symptom, String rootCause, String resolution, int RID) {
	System.out.println("addRepairRecord() called");
	boolean success = false;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection  conn=DriverManager.getConnection(connString);	
		conn.setAutoCommit(false);
    PreparedStatement stmt = null;
    if(RID==0){
    stmt = conn.prepareStatement("INSERT INTO Repairs(Repair_Status, Repaired_By) Values (?,?)");
    }else{
    stmt = conn.prepareStatement("Update Repairs Set Repair_Status=?, Repaired_By=? WHERE RepairID=?");  
    stmt.setInt(3, RID);   
    }   
    stmt.setString(1, String.valueOf((status)));
    stmt.setString(2, tech);   
    stmt.execute();
	conn.commit();
	stmt.close();
	conn.close();
	System.out.println("status and tech populated");
	conn=DriverManager.getConnection(connString);	
	stmt = conn.prepareStatement("Select MAX(RepairID) FROM Repairs"); 
    rs = stmt.executeQuery();
    if(rs.next());
    String repairID = String.valueOf(rs.getInt(1));
    rs.close();
    stmt.close();
    if(sn.length()!=0){
    	conn=DriverManager.getConnection(connString);	
    	conn.setAutoCommit(false);
        stmt = conn.prepareStatement("Update Repairs Set SN = ? WHERE RepairID = ? "); 
    	stmt.setString(1, sn); 
    	stmt.setString(2, repairID); 
	stmt.execute();
	conn.commit();
	stmt.close();
	conn.close();
	System.out.println("SN populated");
    }
    if(symptom.length()!=0){
    	conn=DriverManager.getConnection(connString);	
    	conn.setAutoCommit(false);
        stmt = conn.prepareStatement("Update Repairs Set Symptom = ? WHERE  RepairID =  ? "); 
    	stmt.setString(1, symptom); 
    	stmt.setString(2, repairID); 
	stmt.execute();
	conn.commit();
	stmt.close();
	conn.close();
	System.out.println("symptom populated");
    }
    if(rootCause.length()!=0){
    	conn=DriverManager.getConnection(connString);	
    	conn.setAutoCommit(false);
        stmt = conn.prepareStatement("Update Repairs Set Root_Cause = ? WHERE  RepairID =  ? "); 
    	stmt.setString(1, rootCause); 
    	stmt.setString(2, repairID); 
	stmt.execute();
	conn.commit();
	stmt.close();
	conn.close();
	System.out.println("rootCause populated");
    }
    if(resolution.length()!=0){
    	conn=DriverManager.getConnection(connString);	
    	conn.setAutoCommit(false);
        stmt = conn.prepareStatement("Update Repairs Set Resolution = ? WHERE  RepairID =  ? "); 
    	stmt.setString(1, resolution); 
    	stmt.setString(2, repairID); 
	stmt.execute();
	conn.commit();
	stmt.close();
	conn.close();
	System.out.println("resolution populated");
    }  
    if(outDate.length()!=0){    
    	conn=DriverManager.getConnection(connString);	
    	conn.setAutoCommit(false);
    	stmt = conn.prepareStatement("Update Repairs Set Repaired_Date = ? WHERE   RepairID =  ? ");    
   stmt.setTimestamp(1, new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(outDate).getTime()));
   stmt.setString(2, repairID); 
    stmt.execute();
    conn.commit(); 
    System.out.println("outDate populated");
    }       
    if(inDate.length()!=0){
    	conn=DriverManager.getConnection(connString);	
    	conn.setAutoCommit(false);
        stmt = conn.prepareStatement("Update Repairs Set Down_Since = ? WHERE   RepairID = ?"); 
    	stmt.setTimestamp(1, new Timestamp( new SimpleDateFormat("yyyy-MM-dd").parse(inDate).getTime())); 
    	stmt.setString(2, repairID); 
	stmt.execute();
	conn.commit();
	stmt.close();
	conn.close();
	System.out.println("inDate populated");
    }    
    success = true;
}catch (Exception ex){
	System.out.println("failure to communicate with database \n");
	ex.printStackTrace();	
	return false;
}	
	return success;
}

//getters and setters:
public void setOldSN(String SN){
	oldSN=SN;
}
public String getOldSN(){
	return oldSN;
	}
public int getRowCount(){
	return rowCount;
}
public int getColumnCount(){
	return columnCount;
}
}




