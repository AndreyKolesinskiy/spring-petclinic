package guru.qa;

import guru.qa.owner.Owner;

import javax.sql.DataSource;
import java.sql.*;

public class OwnersManager {

	private DataSource ds = DataSourceProvider.INSTANCE.getDataSource();

	public int createOwner(Owner owner) {
		try (Connection connection = ds.getConnection();
			 PreparedStatement ps = connection.prepareStatement(
				 "INSERT INTO owners (first_name, last_name, address, city, telephone)\n" +
					 "VALUES (?, ?, ?, ?, ?)",
				 Statement.RETURN_GENERATED_KEYS
			 )) {
			ps.setString(1, owner.getFirstName());
			ps.setString(2, owner.getLastName());
			ps.setString(3, owner.getAddress());
			ps.setString(4, owner.getCity());
			ps.setString(5, owner.getTelephone());
			ps.executeUpdate();

			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void deleteOwner(int id) {
		try (Connection connection = ds.getConnection();
			 PreparedStatement ps = connection.prepareStatement(
				 "DELETE FROM owners WHERE id = ?"
			 )) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
