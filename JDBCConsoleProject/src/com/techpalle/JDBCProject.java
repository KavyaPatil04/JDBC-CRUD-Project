package com.techpalle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * Project name : JDBC Console Project
 * Description : The main purpose of this project is to explore 
 * 				 JDBC apis for performing DML operations.
 */

public class JDBCProject {

	public static void main(String[] args) {
		//call creating funtion - so that it creates table
		Dao d = new Dao();
		/*
		 * lets ask user to input..
		 */
		Scanner sc = new Scanner(System.in);
		int option = 0;
		System.out.println("-----JDBC CONSOLE PROJECT WELCOMES YOU-----");
		
		boolean flag = true;
		int eno;
		String ename ;
		int esal;
		while(flag)
		{
			System.out.println("-----CHOOSE CORRECT OPTION-----");
			System.out.println("1: Create table");
			System.out.println("2: inserting values into table");
			System.out.println("3: update the row table");
			System.out.println("4: delete the row of table");
			System.out.println("5: display one row ");
			System.out.println("6: read all rows");
			System.out.println("0: exit");
			option = sc.nextInt();
			switch(option) {
			
			case 0: flag=false;
				    System.out.println("Exitint the system. Thank you!");
			        break;
			
			case 1: d.creating();
					break;
			
			case 2: System.out.println("enter enumber");
					eno = sc.nextInt();
					System.out.println("enter namer");
					ename = sc.next();
					System.out.println("enter salary");
					esal = sc.nextInt();
					d.inserting(eno, ename, esal);
					break;
			
			case 3: System.out.println("enter newsal");
					esal = sc.nextInt();
			        System.out.println("enter eno");
			        eno= sc.nextInt();
			        d.updating(eno, esal);
			        break;
			        
			case 4: System.out.println("enter employee number");
					eno = sc.nextInt();
					d.deleting(eno);
					break;
					
			case 5: System.out.println("enter eno value to display");
					eno = sc.nextInt();
					d.displayOne(eno);
					break;
			
					
			case 6: System.out.println("display employee table");
					d.display();
				
			
					
			
			} 
			
		}
		

	}

}
/*
 * let us take another class for JDBC code
 * class name : DAO - data access object- DAO layout
 * 				In this class we are going to write code for 5 operations
*/
class Dao 
{
	//In this method we will create employee table
	public void creating()
	{
		Connection c = null;
		Statement s= null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palle","root","Kavya@04");
			s = c.createStatement();
			s.executeUpdate("create table employee(eno int primary key, ename varchar(50),esal int);");
			System.out.println("TABLE IS CREATED SUCCESSFULLY");
			
		} 
		catch (ClassNotFoundException e) {
			System.out.println("DRIVER IS NOT PROPERLY LOADED");
			e.printStackTrace();
		} 
		catch (SQLException e) {
			System.out.println("SOMETHING WENT WRONG WITH DATABASE");
			e.printStackTrace();
		}
		finally
		{
			if(s!=null)
				try {
					s.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			if(c!=null)
				try {
					c.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		}
	}

	
	//in below method we will insert data into employee table
	public void inserting(int eno, String ename, int esal) {
		Connection c = null;
		PreparedStatement s = null; //we are getting dynamic values
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:/Palle","root","Kavya@04");
			
			String query = "insert into employee values(?,?,?)";
			s = c.prepareStatement(query);
			s.setInt(1, eno);
			s.setString(2, ename);
			s.setInt(3, esal);
			
			s.executeUpdate();
			System.out.println("SUCCESSFULLY INSERTED A ROW...");
			
		} catch (ClassNotFoundException e) {  //error related variable
			System.out.println("DRIVER IS NOT LOADED PROPERLY");
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SOMETHING WENT WRONG WITH DATABASE");
			e.printStackTrace();
		} catch(Exception e) {
			//IF SOME UNKNOWN EXCEPTION OCCURS THIS CATCH BLOCK WILL EXECUTE
			System.out.println("SOMETHING UNUSUAL THING HAPPENED");
		}
		finally 
		{
			if(s!=null)
				try {
					s.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(c!=null)
				try {
					c.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
 	}

	
	//In below method we will upadate .. for a given eno new salary
	public void updating(int eno, int newesal)
	{
		Connection c = null;
		PreparedStatement s = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:/Palle", "root","Kavya@04");
			
			String query = "update employee set esal=? where eno=?";
			s = c.prepareStatement(query);
			s.setInt(1, newesal);
			s.setInt(2, eno);
			s.executeUpdate();
			System.out.println("SUCCESSFULLY UPDATED A ROW...");
			
		} catch (ClassNotFoundException e) {
			System.out.println("DRIVER IS NOT LOADED PROPERLY");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SOMETHING WENT WRONG WITH DATABASE");
			e.printStackTrace();
		}
		finally
		{
			if(s!=null)
				try {
					s.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(c!=null)
				try {
					c.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}

	//In below below method we will delete.. for a given eno
	public void deleting(int eno)
	{
		Connection c = null;
		PreparedStatement s = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:/Palle","root","Kavya@04");
			
			String query = "delete from employee where eno=?";
			s = c.prepareStatement(query);
			s.setInt(1, eno);
			s.executeUpdate();
			System.out.println("SUCCESSFULLY DELETED A ROW...");
			
		} catch (ClassNotFoundException e) {
			System.out.println("DRIVER IS NOT LOADED PROPERLY");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SOMETHING WENT WRONG WITH DATABASE");
			e.printStackTrace();
		}
		finally
		{
			if(s!=null)
				try {
					s.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(c!=null)
				try {
					c.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	//in below method we will read one row from table
	public void displayOne(int eno)
	{
		Connection c=null;
		Statement s= null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:/Palle","root","Kavya@04");
			s = c.createStatement();
		    ResultSet rs = s.executeQuery("select * from employee where eno= "+eno);
			System.out.println("\n----EMPLOYEE TABLE DATA BELOW----\n");
			System.out.println("eno   ename   esal");
			System.out.println("---   ------  ----");
			while(rs.next())
			{
				System.out.println(rs.getInt(1) + "   "+rs.getString(2)+"   "+rs.getInt(3));
			}
			System.out.println("---------------------------------------------------------");
			
		} catch (ClassNotFoundException e) {
			System.out.println("DRIVER IS NOT LOADED PROPERLY");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SOMETHING WENT WRONG WITH DATABASE");
			e.printStackTrace();
		}
		
	}

	//In below method we will read all employees and display their details
	public void display()
	{
		Connection c= null;
		Statement s= null;
		ResultSet res =null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:/Palle","root","Kavya@04");
			s = c.createStatement();
		    res = s.executeQuery("select * from employee");
			System.out.println("\n----EMPLOYEE TABLE DATA BELOW----\n");
			System.out.println("eno   ename   esal");
			System.out.println("---   ------  ----");
			while(res.next())
			{
				System.out.println(res.getInt(1) + "   "+res.getString(2)+"   "+res.getInt(3));
			}
			System.out.println("---------------------------------------------------------");
			
		} catch (ClassNotFoundException e) {
			System.out.println("DRIVER IS NOT LOADED PROPERLY");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SOMETHING WENT WRONG WITH DATABASE");
			e.printStackTrace();
		}
		
		
		finally
		{
			if(s!=null)
				try {
					s.close();
				} catch (SQLException e) {
			
					e.printStackTrace();
				}
			if(c!=null)
				try {
					c.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			
		}
		}
}


