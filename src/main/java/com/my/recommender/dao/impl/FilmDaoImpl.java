package com.my.recommender.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.my.recommender.dao.FilmDao;
import com.my.recommender.model.Film;
import com.my.recommender.model.User;

@Repository
public class FilmDaoImpl implements FilmDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.my.recommender.dao.impl.FilmDao#insert(com.my.recommender.model.Film)
	 */
	@Override
	public void insert(Film film) {
		String sql = "INSERT INTO films (title, yr, poster) VALUES (?, ?, ?)";
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, film.getTitle());
			ps.setInt(2, film.getYear());
			ps.setBinaryStream(3, film.getPoster());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.my.recommender.dao.impl.FilmDao#getAll()
	 */
	@Override
	public List<Film> getAll() {
		List<Film> films = new ArrayList<Film>();
		String sql = "SELECT * FROM films";
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Film film = new Film(
					rs.getInt("idFilm"),
					rs.getString("title"),
					rs.getInt("yr"),
					rs.getBinaryStream("poster")
				);
				films.add(film);
			}
			rs.close();
			ps.close();
			return films;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.my.recommender.dao.impl.FilmDao#getById(int)
	 */
	@Override
	public Film getById(int id) {
		String sql = "SELECT * FROM films WHERE idFilm = ?";
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			Film film = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				film = new Film(
					rs.getInt("idFilm"),
					rs.getString("title"),
					rs.getInt("yr"),
					rs.getBinaryStream("poster")
				);
			}
			rs.close();
			ps.close();
			return film;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.my.recommender.dao.impl.FilmDao#getFilmUsers(int)
	 */
	@Override
	public List<User> getFilmUsers(int filmId) {
		List<User> users = new ArrayList<User>();
		String sql = "SELECT users.idUser, users.name, users.password FROM users INNER JOIN ratings ON users.idUser = ratings.userId " + 
					 "WHERE ratings.filmId = ?";
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, filmId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User(
					rs.getInt(1),
					rs.getString(2),
					rs.getString(3)
				);
				users.add(user);
			}
			rs.close();
			ps.close();
			return users;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.my.recommender.dao.impl.FilmDao#remove(int)
	 */
	@Override
	public void remove(int id) {
		String sql = "DELETE FROM films WHERE idFilm = ?";
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
}
