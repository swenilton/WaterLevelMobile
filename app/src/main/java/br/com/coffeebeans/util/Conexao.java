package br.com.coffeebeans.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
	private static Connection connection;
	private static Statement statement;

	public static Connection conectar(String sistema) throws Exception {
		String conexao = "";
		String usuario = "";
		String senha = "";

		if (sistema == "mysql") {
			conexao = "jdbc:mysql://localhost/waterlevel";
			usuario = "root";
			senha = "root";
		} else if (sistema == "oracle") {
			/*
			 * conexao = "jdbc:oracle:thin:@0.0.0.0:1521:XE"; usuario = "";
			 * senha = "";
			 */
		} else if (sistema == "sqlserver") {
			/*
			 * conexao =
			 * "jdbc:sqlserver://localhost:1433;databaseName=WaterLevel";usuario
			 * = ""; senha = "";
			 */
		} else {
			conexao = null;
		}
		if (connection == null || connection.isClosed()  ) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(conexao, usuario,
						senha);
			} catch (SQLException e) {
				connection.close();
				throw new Exception("SQLException => ConnectionManager: "
						+ e.getMessage());
			}
		}

		return connection;

	}

	/**
	 * Atencao, este metodo fecha o Statement!
	 * 
	 * @throws SQLException
	 */
	public synchronized static void liberaStatement() throws SQLException {
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			throw new SQLException("SQLException => ConnectionManager: "
					+ e.getMessage());
		}
	}

	public static void liberaRecursos() throws SQLException {
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			throw new SQLException("SQLException => ConnectionManager: "
					+ e.getMessage());
		}
		try {
			statement.executeUpdate("SHUTDOWN COMPACT");
			liberaStatement();
		} catch (SQLException e) {
			throw new SQLException("SQLException => ConnectionManager: "
					+ e.getMessage());
		}
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			throw new SQLException("SQLException => ConnectionManager: "
					+ e.getMessage());
		}

	}

}
