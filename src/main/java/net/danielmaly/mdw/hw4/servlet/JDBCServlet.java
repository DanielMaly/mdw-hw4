package net.danielmaly.mdw.hw4.servlet;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCServlet extends HttpServlet {

    private DataSource dataSource;

    public JDBCServlet() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("jdbc/mimdw/hw4-mysql");
        }
        catch(NamingException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM records");
            ResultSet resultSet = stmt.executeQuery();

            PrintWriter writer = resp.getWriter();
            writer.println("<table border=1 width=100%>"
                    +"<tr>" +
                    "<th>id</th><th>type</th><th>location</th>" +
                    "<th>capacity</th><th>occupied</th><th>trip</th><th>person</th>" +
                    "</tr>");

            while(resultSet.next()){
                String id = "" + resultSet.getInt("id");
                String type = resultSet.getString("type");
                String location = resultSet.getString("location");
                String capacity = "" + resultSet.getInt("capacity");
                String occupied = "" + resultSet.getInt("occupied");
                String trip = "" + resultSet.getInt("trip");
                String person = resultSet.getString("person");

                StringBuilder formatStringBuilder = new StringBuilder("<tr>");
                for(int i = 0; i < 7; i++) {
                    formatStringBuilder.append("<td>%s</td>");
                }
                formatStringBuilder.append("</tr>\n");

                writer.format(formatStringBuilder.toString(), id, type, location, capacity, occupied, trip, person);
            }

            writer.println("</table>");

        } catch (SQLException e) {
           throw new IOException(e);
        }
    }
}
