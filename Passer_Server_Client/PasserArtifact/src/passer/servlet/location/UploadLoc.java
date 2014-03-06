package passer.servlet.location;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


import passer.connection.UserDAO;
import passer.connection.UserLocDAO;
import passer.factory.DAOFactory;
import passer.models.User;
import passer.models.UserLoc;

/**
 * 更新地理位置
 * @author Law
 *
 */

@WebServlet("/location/uploadloc")
public class UploadLoc extends HttpServlet{
	/**
	 * AA
	 */
	private static final long serialVersionUID = 5993761121056747062L;
	private UserLocDAO userlocDAO;
	
	public UploadLoc(){
		userlocDAO = DAOFactory.getUserLocDAO();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Upload的dopost了");
		
		req.setCharacterEncoding("UTF-8");
    	resp.setContentType("text/html; charset=utf-8");
    	resp.setCharacterEncoding("utf-8");
		PrintWriter writer = resp.getWriter();
		try {
			String UserId;
			double Longitude=0;
			double Latitude=0;
			String City="";
			String Region="";
			boolean Valid=false;
			String userlocS = req.getParameter("param");
			System.out.println("接受的req" + userlocS);
			UserLoc userloc = UserLoc.toUserLocFromJSONstr(userlocS);
			// 添加用户
			JSONObject jsonReturn = new JSONObject();
			boolean b=userlocDAO.checkUserIdExisted(userloc.getUserId());
			System.out.println(b);
			if (b) {
				userlocDAO.updateUserLocationDimension(userloc.getUserId(), userloc.getLongitude(), userloc.getLatitude());
				System.out.println("成功更新用户位置~" + userloc.getUserId()+" "+userloc.getLongitude()+" "+userloc.getLatitude());
				HttpSession session = req.getSession();
				session.setAttribute("uId", userloc.getUserId());
				jsonReturn.put("SessionId", session.getId());
				resp.setStatus(HttpServletResponse.SC_OK);
			} else{
				userlocDAO.addUserLocation(userloc);
				System.out.println("成功添加用户位置~" + userloc.getUserId());
				resp.setStatus(HttpServletResponse.SC_OK);
			}
			writer.print(jsonReturn.toString());

			// System.out.println("用户名密码错误~");
			// resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		}

		catch (Exception e) {System.out.println("Upload的dopost exception了");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			writer.println("{\"msg\":\"wrong\"}");
			e.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
		}	
	}	
}
