package model;

import java.sql.*;

public class Payment {
	

	public Connection connect()
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/gbproject", "root", ""); 
	 //For testing
	 System.out.print("Successfully connected to dataBase and server "); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 
	}
	
	
	
	public String insertPayment(String nameoncard, String cardnumber, String expireddate, String cvv)
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database"; 
	 } 
	 // create a prepared statement
	 String query = " insert into payment(nameOnCard, cardNumber, expiredDate, cvv)"+ " values (?, ?, ?, ?)"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	// preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(1, nameoncard); 
	 preparedStmt.setInt(2,  Integer.parseInt(cardnumber)); 
	 //preparedStmt.setDouble(4, Double.parseDouble(price)); 
	 preparedStmt.setString(3, expireddate);
	 preparedStmt.setString(4, cvv);

	//execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Inserted successfully"; 
	 } 
	catch (Exception e) 
	 { 
	 output = "Error while inserting"; 
	 System.err.println(e.getMessage()); 
	 } 
	
	return output; 
	}

	
	public String readPayment() 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for reading."; } 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>card Name</th><th>Card Number</th>" +
	 "<th>Expried Date</th>" + 
	 "<th>CVV</th>" +
	 "<th>Edit</th><th>Delete</th></tr>"; 
	 
	 String query = "select * from payment"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String payId = Integer.toString(rs.getInt("payId")); 
	 String nameOncard = rs.getString("nameOncard"); 
	 String cardNumber = Integer.toString(rs.getInt("cardNumber")); 
	 String expiredDate = rs.getString("expiredDate"); 
	 String cvv = rs.getString("cvv"); 
	
	 // Add into the html table
	 output += "<tr><td>" + nameOncard + "</td>"; 
	 output += "<td>" + cardNumber + "</td>"; 
	 output += "<td>" + expiredDate + "</td>"; 
	 output += "<td>" + cvv + "</td>"; 
	 // buttons
	 output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
				+ "<td><form method=\"post\" action=\"PAYMENT.jsp\">"
				+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
				+ "<input name=\"payId\" type=\"hidden\" value=\"" + payId + "\">" + "</form></td></tr>";
	 } 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while reading the items."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	public String updatePayment(String ID, String nameoncard, String cardnumber, String expireddate, String cvv)
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for updating."; } 
	 // create a prepared statement
	 String query = "UPDATE payment SET nameOnCard=?,cardNumber=?,expiredDate=?,cvv=? WHERE payId=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setString(1, nameoncard); 
	 preparedStmt.setInt(2, Integer.parseInt(cardnumber));
	 //preparedStmt.setDouble(4, Double.parseDouble(price)); 
	 preparedStmt.setString(3, expireddate);
	 preparedStmt.setString(4, cvv); 
	 preparedStmt.setInt(5, Integer.parseInt(ID)); 

	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 String newPayment = readPayment();
	 output = "{\"status\":\"success\", \"data\": \"" +newPayment + "\"}"; 
	 } 
	 catch (Exception e) 
	 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
		 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	

	public String deletePayment(String ID)
	{
	 String output = "";
	try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
	 return "Error while connecting to the database for deleting.";
	 }
	 // create a prepared statement
	 String query = "delete from payment where payId=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(ID));

	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 String newPayment = readPayment();
	 output = "{\"status\":\"success\", \"data\": \"" +newPayment + "\"}";
	 }
	catch (Exception e)
	 {
		 output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
		 System.err.println(e.getMessage());
	 }
	return output;
	}
	 
}