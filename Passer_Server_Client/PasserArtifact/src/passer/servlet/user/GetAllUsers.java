package passer.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


import passer.connection.UserDAO;
import passer.factory.DAOFactory;
import passer.models.User;


@WebServlet("/user/getallusers")
public class GetAllUsers extends HttpServlet{

	/**
	 * 得到用户群
	 */
	private static final long serialVersionUID = -8638577954293064285L;
	private UserDAO userDAO;
	
	public GetAllUsers(){
		userDAO = DAOFactory.getUserDAO();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("GetAllUsers的dopost了");
		
		req.setCharacterEncoding("UTF-8");
    	resp.setContentType("text/html; charset=utf-8");
    	resp.setCharacterEncoding("utf-8");
		PrintWriter writer = resp.getWriter();
		
		try{
			JSONArray jSrray= new JSONArray();
			JSONObject jsonReturn = new JSONObject();
			User[] users = userDAO.getAllUsers();
			jSrray = (JSONArray) JSONSerializer.toJSON( users );
			
			
			
			resp.setStatus(HttpServletResponse.SC_OK);
			writer.print(jSrray.toString());
			//判断密码是否正确
			
//			else{
//				System.out.println("用户名密码错误~");
//				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			}
				
		}
		
		
		catch(Exception e){
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			writer.println("{\"msg\":\"wrong\"}");
			e.printStackTrace();
		}
		finally{
			writer.flush();
			writer.close();
		}
	}
}
