package passer.connection.impl;

import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import passer.connection.UserLocDAO;
import passer.dbc.DatabaseConnection;
import passer.models.UserLoc;

public class UserLocDAOImpl implements UserLocDAO {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 得到用户位置 没有返回0,0
	 */
	@Override
	public Dimension getUserLocationDimension(String userId) throws Exception {
		Dimension userloc = new Dimension(0, 0);
		String sql = "select Longitude, Latitude from location where userid = ?";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				userloc.setSize(rs.getInt(1), rs.getInt(2));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return userloc;
	}

	@Override
	public UserLoc getUserLocation(String userId) throws Exception {
		UserLoc userloc = new UserLoc();
		String sql = "select UserId, Longitude, Latitude, Datetime, City, Region, Valid"
				+ " from location where userid = ?";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				userloc.setUserId(userId);
				userloc.setLongitude(rs.getInt(2));
				userloc.setLatitude(rs.getInt(3));
				userloc.setDatetime(sdf.parse(rs.getString(4)));
				userloc.setCity(rs.getString(5));
				userloc.setRegion(rs.getString(6));
				userloc.setValid(rs.getBoolean(7));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return userloc;
	}

	/**
	 * 添加用户位置
	 */
	@Override
	public String addUserLocationDimension(String userId, double longitude,
			double latitude) throws Exception {

		String sql = "insert into Location ( UserId, Longitude, Latitude) "
				+ " values (?,?,?)";
		DatabaseConnection dbc = null;
		String UserId = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, userId);
			pstmt.setDouble(2, longitude);
			pstmt.setDouble(3, latitude);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				UserId = rs.getString(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return UserId;
	}

	@Override
	public String addUserLocation(UserLoc userloc) throws Exception {
		String sql = "insert into Location ( UserId, Longitude, Latitude, Datetime, City, Region, Valid ) "
				+ " values (?,?,?,?,?,?,?)";
		DatabaseConnection dbc = null;
		String UserId = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, userloc.getUserId());
			pstmt.setDouble(2, userloc.getLongitude());
			pstmt.setDouble(3, userloc.getLatitude());
			pstmt.setString(4, sdf.format(new Date()));
			pstmt.setString(5, userloc.getCity());
			pstmt.setString(6, userloc.getRegion());
			pstmt.setBoolean(7, userloc.getValid());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				UserId = rs.getString(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return UserId;
	}

	/**
	 * 更新用户位置
	 */
	@Override
	public UserLoc updateUserLocationDimension(String userId, double longitude,
			double latitude) throws Exception {
		String sql = "update Location set Longitude = ?, Latitude = ? , Datetime= ? where UserId= ?";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setDouble(1, longitude);
			pstmt.setDouble(2, latitude);
			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			pstmt.setString(3, sdf.format(new Date()));
			pstmt.setString(4, userId);

			// Location=rs.getString("GroupNum");
			// Calendar CT=Calendar.getInstance();
			// ConnectionTime=rs.getString("ConnectTime");
			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// java.util.Date dt = sdf.parse(ConnectionTime);
			// CT.setTime(dt);
			//
			// double hour=CT.get(CT.HOUR_OF_DAY);
			// hour=hour+CT.get(CT.MINUTE)/60.0;
			// int day= CT.get(CT.DAY_OF_YEAR);

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
		return null;
	}

	@Override
	public void updateUserLocation(UserLoc userloc) throws Exception {
		String sql = "update location set UserId = ?, Longitude =? , Latitude = ?,"
				+" Datetime = ?, City = ? , Region = ?, Valid = ?";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setString(1,userloc.getUserId());
			pstmt.setDouble(2, userloc.getLongitude());
			pstmt.setDouble(3, userloc.getLatitude());
			pstmt.setString(4, sdf.format(new Date()));
			pstmt.setString(5, userloc.getCity());
			pstmt.setString(6, userloc.getRegion());
			pstmt.setBoolean(7, userloc.getValid());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return;

	}

	@Override
	public boolean checkUserIdExisted(String userId) throws Exception {
		boolean available = false;
		String sql = "select UserId from location where UserId = ?";
		DatabaseConnection dbc = null;
		try {
			dbc = new DatabaseConnection();
			PreparedStatement pstmt = dbc.getConnection().prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				available = true;
			} else {
				available = false;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbc.close();
		}
		return available;
	}

}
