import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	  private final static String query = "delete from user where id = ? ";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Get PrintWriter
		PrintWriter pw = res.getWriter();
		//Set Content Type
		res.setContentType("text/html");
		//link the bootstrap
		pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		//get the values
		int id = Integer.parseInt(req.getParameter("id"));
		//load the jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//generate the connection
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt","root","Atharv@3563");
				PreparedStatement ps = con.prepareStatement(query);){
			//set the values
			ps.setInt(1, id);
			//execute the query
			int count = ps.executeUpdate();
			pw.println("<div class='card' style='margin:auto;width:400px;margin-top:200px'>");
			 if(count==1) {
	                pw.println("<h1 class='bg-danger text-light text-center'>Record Deleted Successfully</h1>");
	            }else {
	                pw.println("<h1 class='bg-danger text-light text-center'>Record Not Deleted</h1>");
	            }
			
		} catch (SQLException se) {
			 pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		 pw.println("<a href='home.html'><button class='btn btn-success d-grid gap-2 col-12 mt-3' >Home</button></a>");
	        pw.println("&nbsp; &nbsp;");
	        pw.println("<a href='showdata'><button class='btn btn-success d-grid gap-2 col-12'>Show User</button></a>");
	        pw.println("</div>");
	        //close the stream
	        pw.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
