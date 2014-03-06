package passer.connection.impl;

import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import passer.connection.UserDAO;
import passer.dbc.DatabaseConnection;
import passer.models.User;

public class UserDAOImpl implements UserDAO {
	/**
	 * 添加该用户
	 */
	@Override
	public User addUser(User user) throws Exception {
		String sql = "insert into User ( UserId, UserName, Password, Career, City, Sex, Age) "
				+ " values (?,?,?,?,?,?,?)";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getCareer());
			pstmt.setString(5, user.getCity());
			pstmt.setString(6, user.getSex());
			pstmt.setInt(7, user.getAge());

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				user.setUserId(rs.getString(1));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return user;
	}

	/**
	 * 根据用户id得到用户
	 */
	@Override
	public User getUser(String userID) throws Exception {
		User user = new User();
		String sql = "select UserName, Sex, Age, City, Career, " + "Password"
				+ " from user where UserId = ?";
		DatabaseConnection dbc = null;
		System.out.println("在数据库进行 getUser查找：" + userID);
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, userID);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user.setUserId(userID);
				user.setUserName(rs.getString(1));
				if (rs.getString(2) != null)
					user.setSex(rs.getString(2));
				if (rs.getString(3) != null)
					user.setAge(rs.getInt(3));
				if (rs.getString(4) != null)
					user.setCity(rs.getString(4));
				if (rs.getString(5) != null)
					user.setCareer(rs.getString(5));
				user.setPassword(rs.getString(6));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return user;
	}

	@Override
	public void LevelUp(int userID) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 根据用户名得到用户User
	 */
	@Override
	public User getUserByName(String userName) throws Exception {
		User user = new User();
		String sql = "select UserId, UserName, Sex, Age, City, Career, "
				+ "Password" + " from user where UserName = ?";
		DatabaseConnection dbc = null;
		System.out.println("在数据库进行 getUserByName 查找" + userName);
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setSex(rs.getString(3));
				user.setAge(rs.getInt(4));
				user.setCity(rs.getString(5));
				user.setCareer(rs.getString(6));
				user.setPassword(rs.getString(7));
			}
		} catch (Exception e) {
			System.out.print(e);
			throw e;

		} finally {
			dbc.close();
		}
		return user;
	}

	/**
	 * 检查用户名是否可用，若可用返回true
	 */
	@Override
	public boolean checkUserId(String userId) throws Exception {
		boolean available = false;
		String sql = "select UserId from user where UserId = ?";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				available = false;
			} else {
				available = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return available;
	}

	/**
	 * 获取所有用户的所有信息
	 */
	@Override
	public User[] getAllUsers() throws Exception {
		ArrayList<User> users = new ArrayList<User>();

		String sql = "select UserId, UserName, Sex, Age, City, Career, "
				+ "Password" + " from user";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setSex(rs.getString(3));
				user.setAge(rs.getInt(4));
				user.setCity(rs.getString(5));
				user.setCareer(rs.getString(6));
				user.setPassword(rs.getString(7));

				users.add(user);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return users.toArray(new User[0]);
	}

	/**
	 * 更新用户的信息，包括密码
	 */
	@Override
	public void updateUser(User user) throws Exception {
		String sql = "update user set CompanyName = ?, FirstName = ?, LastName = ?, "
				+ "Addr = ?, City = ?, Zip = ?,Phone = ?, Mobile = ?, Email = ?, "
				+ "BeginDate = ?, ExpireDate = ?, AuthorityID = ?, UserName = ?, password = ?";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);

			// pstmt.setString(1, user.getCompanyName());
			// pstmt.setString(2, user.getFirstName());
			// pstmt.setString(3, user.getLastName());
			// pstmt.setString(4, user.getAddr());
			// pstmt.setString(5, user.getCity());
			// pstmt.setString(6, user.getZip());
			// pstmt.setString(7, user.getPhone());
			// pstmt.setString(8, user.getMobile());
			// pstmt.setString(9, user.getEmail());
			// pstmt.setString(10, user.getBeginDate());
			// pstmt.setString(11, user.getExpireDate());
			// pstmt.setInt(12, user.getAuthorityID());
			// pstmt.setString(13, user.getUserName());
			// pstmt.setString(14, user.getPassword());

			pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
	}

	/**
	 * 更新用户信息，不改变密码
	 */
	@Override
	public void updateUserExceptPassword(User user) throws Exception {
		String sql = "update user set CompanyName = ?, FirstName = ?, LastName = ?, "
				+ "Addr = ?, City = ?, Zip = ?,Phone = ?, Mobile = ?, Email = ?, "
				+ "BeginDate = ?, ExpireDate = ?, AuthorityID = ?, UserName = ?";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			// pstmt.setString(1, user.getCompanyName());
			// pstmt.setString(2, user.getFirstName());
			// pstmt.setString(3, user.getLastName());
			// pstmt.setString(4, user.getAddr());
			// pstmt.setString(5, user.getCity());
			// pstmt.setString(6, user.getZip());
			// pstmt.setString(7, user.getPhone());
			// pstmt.setString(8, user.getMobile());
			// pstmt.setString(9, user.getEmail());
			// pstmt.setString(10, user.getBeginDate());
			// pstmt.setString(11, user.getExpireDate());
			// pstmt.setInt(12, user.getAuthorityID());
			// pstmt.setString(13, user.getUserName());

			pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
	}

}
