package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.VocaDAO.VocaDTO;

public abstract class DAO<T> {
	//protected�� ��ӹ��� Ŭ���������� ���ٰ���.
		protected Connection con;
		protected java.sql.Statement st = null;
		protected ResultSet rs = null;
		public DAO() {
			try {

				con = null;
				con = DriverManager.getConnection("jdbc:mysql://localhost/eng_voca?useUnicode=true&characterEncoding=UTF-8","root", "apmsetup");
				
				st = null;
				st = con.createStatement();

			} catch (SQLException sqex) {
				System.out.println("SQLException: " + sqex.getMessage());
				System.out.println("SQLState: " + sqex.getSQLState());

			}
		}
		
		//������ �� xxxDAO Ŭ�������� �Ѵ�.
		public abstract void create(T sql);
		public abstract T update(String sql);
		public abstract ArrayList<T> read(String sql);
		public abstract void delete(String sql);
}
