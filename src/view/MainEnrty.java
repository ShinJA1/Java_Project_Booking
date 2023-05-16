package view;

import java.sql.SQLException;

import controller.BookingController;
import dbConn.util.ConnectionHelper;

public class MainEnrty {
	public static void main(String[] args) throws SQLException {
		BookingController.connect();
		ConnectionHelper.getConnection("oracle");
		
		BookingController.menu();
	}
	
}
