package passer.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import passer.connection.UserDAO;
import passer.connection.impl.UserDAOImpl;
import passer.dbc.DatabaseConnection;
import passer.factory.DAOFactory;
import passer.models.User;

/**
 * 用户登录
 * 
 * @author Vincent
 * 
 */
@WebServlet("/user/login")
public class Login extends HttpServlet {

	/**
	 * AA
	 */
	private static final long serialVersionUID = 5993761121056747062L;
	private UserDAO userDAO;

	public Login() {
		userDAO = DAOFactory.getUserDAO();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Login的dopost了");

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter writer = resp.getWriter();

		try {
			String userS = req.getParameter("param");
			System.out.println("接受的req" + userS);
			JSONObject jsonUser = (JSONObject) JSONSerializer.toJSON(userS);
			User userFromC = User.toUserFromJSON(userS);
			User user = userDAO.getUser(userFromC.getUserId());
			// 判断密码是否正确
			if (user.getPassword().equals(userFromC.getPassword())) {
				System.out.println("密码对了~");
				JSONObject jsonReturn = (JSONObject) JSONSerializer
						.toJSON(user);
				resp.setStatus(HttpServletResponse.SC_OK);
				writer.print(jsonReturn.toString());

			} else {
				System.out.println("用户名密码错误~");
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}

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
