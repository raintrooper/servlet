import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(urlPatterns = {"/CoonPoolServlet"})
public class CoonPoolServlet extends HttpServlet {
    public Connection makeConnection() throws SQLException{
        Connection con=null;
        try{
            InitialContext ic=new InitialContext();
            DataSource ds=(DataSource)ic.lookup("jdbc/java");
            con = ds.getConnection();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return con;
    }
    
    public void service (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String tname = request.getParameter("t2_student");
        try{
            Connection con=makeConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from t2_student limit 10;");
            ResultSetMetaData rsmd=rs.getMetaData();
            int cCount = rsmd.getColumnCount();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet prueba</title>");            
            out.println("</head>");
            out.println("<body>");
            while (rs.next()){
                for(int i = 1; i <= cCount; i++ ){
                    out.println("<p>" + rs.getString(i) + "</p>");
                }
            }
            out.println("</body>");
            out.println("</html>");
            con.close();
        }catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CoonPoolServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CoonPoolServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        service(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
