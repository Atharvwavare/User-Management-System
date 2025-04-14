import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/showdata")
public class ShowUserServlet extends HttpServlet {
	  private final static String query = "select id,name,email,mobile,dob,city,gender from user";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Get PrintWriter
		PrintWriter pw = res.getWriter();
		//Set Content Type
		res.setContentType("text/html");
		//link the bootstrap
		pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		pw.println("<marquee><h1 class='text-primary' style='margin-top:30px;'>Record of Users</h1></marquee>");
		
		//load the jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//generate the connection
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt","root","Atharv@3563");
				PreparedStatement ps = con.prepareStatement(query);){
			//ResultSet
			ResultSet rs = ps.executeQuery();
			 pw.println("<div style='margin:auto;width:1000px;margin-top:100px'>");
	            pw.println("<table class='table table-hover table-striped' style='font-size:1.1em '>");
	            pw.println("<h1 style='padding-left:400px;padding-bottom:20px'>User database</h1>");
	            pw.println("<tr>");
	            pw.println("<th>ID</th>");
	            pw.println("<th>Name</th>");
	            pw.println("<th>Email</th>");
	            pw.println("<th>Mobile No</th>");
	            pw.println("<th>DOB</th>");
	            pw.println("<th>City</th>");
	            pw.println("<th>Gender</th>");
	            pw.println("<th>Edit</th>");
	            pw.println("<th>Delete</th>");
	            pw.println("</tr>");
	            while(rs.next()) {
	                pw.println("<tr>");
	                pw.println("<td>"+rs.getInt(1)+"</td>");
	                pw.println("<td>"+rs.getString(2)+"</td>");
	                pw.println("<td>"+rs.getString(3)+"</td>");
	                pw.println("<td>"+rs.getString(4)+"</td>");
	                pw.println("<td>"+rs.getString(5)+"</td>");
	                pw.println("<td>"+rs.getString(6)+"</td>");
	                pw.println("<td>"+rs.getString(7)+"</td>");
	                pw.println("<td><a href='editurl?id="+rs.getInt(1)+"'>Edit</a></td>");
	                pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>Delete</a></td>");
	                pw.println("</tr>");
	            }
	            pw.println("</table>");
			
		
		} catch (SQLException se) {
			 pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		 pw.println("<a href='home.html'><button class='btn btn-success' style='margin-left:430px;font-family:sans-serif'>Home</button></a>");
		 pw.println("<button class='btn btn-danger' style='margin-left:10px;font-family:sans-serif' type='reset'>Cancel</button>");  
		 pw.println("</div>");
	        //close the stream
	        pw.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
