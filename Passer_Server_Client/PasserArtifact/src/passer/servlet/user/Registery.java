package passer.servlet.user;

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
import net.sf.json.JsonConfig;

import passer.connection.UserDAO;
import passer.factory.DAOFactory;
import passer.models.User;

/**
 * 用户登录
 * 
 * @author Law
 * 
 */
@WebServlet("/user/registery")
public class Registery extends HttpServlet {

	/**
	 * AA
	 */
	private static final long serialVersionUID = 5993761121056747062L;
	private UserDAO userDAO;

	public Registery() {
		userDAO = DAOFactory.getUserDAO();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Registery的dopost了");

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter writer = resp.getWriter();

		try {
			String userS = req.getParameter("param");
			System.out.println("接受的req" + userS);
			User user =User.toUserFromJSON(userS);

			// 添加用户
			JSONObject jsonReturn = new JSONObject();
			if (userDAO.checkUserId(user.getUserId())) {
				user = userDAO.addUser(user);
				jsonReturn=(JSONObject)JSONSerializer.toJSON(user);
				System.out.println("成功添加用户~" + user.getUserId());
				resp.setStatus(HttpServletResponse.SC_OK);
			} else {
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
			writer.print(jsonReturn.toString());
		}

		catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			writer.println("{\"msg\":\"wrong\"}");
			e.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
		}
	}

}
