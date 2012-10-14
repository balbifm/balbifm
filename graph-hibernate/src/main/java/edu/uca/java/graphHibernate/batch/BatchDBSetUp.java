package edu.uca.java.graphHibernate.batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchDBSetUp {

	private static final int N_NODES = 100000;
	private static final int N_RELATIONS = 10000000;

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		Class.forName("org.postgresql.Driver");
		String sql = "insert into usergraph (id, username) values (?, ?)";
		Connection connection = null;
		connection = DriverManager
				.getConnection("jdbc:postgresql://localhost:5432/myDB",
						"postgres", "maxpayne");
		PreparedStatement ps = connection.prepareStatement(sql);

		final int batchSize = 1000;
		int count = 0;

		for (int i = 1; i <= N_NODES; i++) {
			ps.setLong(1, i);
			ps.setString(2, "" + i);
			ps.addBatch();

			if (++count % batchSize == 0) {
				ps.executeBatch();
			}
		}

		ps.executeBatch();

		sql = "INSERT INTO friendship(id, userone_id, usertwo_id) VALUES (?, ?, ?)";
		ps = connection.prepareStatement(sql);
		int random, random2;

		for (int i = 1; i <= N_RELATIONS; i++) {
			random = (int) (Math.random() * ((N_NODES) + 1)) + 1;
			random2 = (int) (Math.random() * ((N_NODES) + 1)) + 1;
			if (random <= N_NODES && random2 <= N_NODES) {
				ps.setLong(1, i);
				ps.setLong(2, random);
				ps.setLong(3, random2);
				ps.addBatch();

				if (++count % batchSize == 0) {
					ps.executeBatch();
				}
				if (i % 50000 == 0)
					System.out.println(i);
			}
		}

		ps.executeBatch(); // insert remaining records
		ps.close();
		connection.close();

	}
}
