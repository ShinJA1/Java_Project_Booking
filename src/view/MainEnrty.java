package view;

import java.sql.SQLException;

import controller.BookController4;
import controller.BookingController;
import dbConn.util.ConnectionHelper;


public class MainEnrty {
	public static void main(String[] args) throws SQLException {
		BookingController.connect();
		ConnectionHelper.getConnection("oracle");
		
//		BookingController.menu();
//		BookController2.main();
//		BookController3.main(args);
		BookController4.main(args);
	}
}