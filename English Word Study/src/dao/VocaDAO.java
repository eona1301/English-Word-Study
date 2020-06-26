package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.VocaDAO.VocaDTO;
/**
 * �ܾ� DAO
 * */
public class VocaDAO extends DAO {
	/*���sub DAOŬ������ DAOŬ������ ��ӹ޴´�.
	 * DAO�� CRUD�۾� �� DB������ ���� �߻�Ŭ�����̴�.
	 * */
	public VocaDAO() {
		super();
	}
	public static class VocaDTO{
		private String voca, mean;
		private int idx;
		public VocaDTO(int idx,String voca, String mean) {
			super();
			this.voca = voca;
			this.mean = mean;
			this.idx =idx;
		}
	
		public int getIdx() {
			return idx;
		}
		public void setIdx() {
			this.idx=idx;
		}
		public String getVoca() {
			return voca;
		}

		public void setVoca(String voca) {
			this.voca = voca;
		}

		public String getMean() {
			return mean;
		}

		public void setMean(String mean) {
			this.mean = mean;
		}
	}

	@Override
	public void create(Object voca) {
		VocaDTO theVoca= (VocaDTO)voca;
		String sql ="INSERT INTO voca(voca,mean) VALUES('"+theVoca.voca+"','"+theVoca.mean+"');";
		try {
			int result = st.executeUpdate(sql);

			System.out.println("��ϵ� ������ ���� : "+result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public VocaDTO update(String sql) {
		
		try {
			int result = st.executeUpdate(sql);
			
			System.out.println("������Ʈ row "  + result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	/** type : �б����� default : �����Ұ��б� failed : Ʋ�����б� */
	public ArrayList<VocaDTO> read(String type) {
		ArrayList<VocaDTO> result= new ArrayList<VocaDTO>();
		try {
			String sql ="";
			
			if(type.equals("default")) {
				//�н� �� �� �ܾ �������� ����
				sql="SELECT * FROM voca WHERE passed is null or passed = 1 order by rand() limit 30;";
			}else if(type.equals("failed")){
				//Ʋ�� �ܾ�
				sql="SELECT * FROM voca WHERE passed = 0;";
			}else if(type.equals("random")) {
				//Ʋ�� �ܾ �������� ����
				sql="SELECT * FROM voca WHERE passed = 0 order by rand() limit 30;";	
			}
			else {
				//�ܾ�˻�
				sql="SELECT * FROM voca WHERE voca LIKE '"+type+"';";
			}
			rs = st.executeQuery(sql);

			//����� ������ ����
			
			while (rs.next()) {			
				result.add(new VocaDTO(rs.getInt("idx"), rs.getString("voca"),  rs.getString("mean")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void delete(String sql) {

	}
}
