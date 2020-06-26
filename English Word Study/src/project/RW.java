package project;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.VocaDAO;
import dao.VocaDAO.VocaDTO;

public class RW extends JFrame{
	private int WIDTH = 1000;
	private int HEIGHT = 800;
	
	private Container contentPane;
	private JPanel TopP = new JPanel();
	private JPanel BottomP = new JPanel();
	private JPanel HomeP = new JPanel();
	private JPanel SRP = new JPanel();	//Save, Re-test Panel
	private JButton HomeB = new JButton(new ImageIcon("image/HomeB.png"));
	private JButton SaveB = new JButton(new ImageIcon("image/SaveB.png"));
	private JButton RetestB = new JButton(new ImageIcon("image/RetestB.png"));
	
	public RW()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/4, dim.height/8);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setSize(WIDTH, HEIGHT);
		contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);	
		contentPane.setLayout(new FlowLayout());

		HomeB.setPreferredSize(new Dimension(170, 170));
		HomeB.setBorderPainted(false);
		HomeB.setContentAreaFilled(false);
		HomeB.setOpaque(false);
		HomeB.setActionCommand("HomeB");
		HomeB.addActionListener(buttonClick);

		SaveB.setPreferredSize(new Dimension(170, 170));
		SaveB.setBorderPainted(false);
		SaveB.setContentAreaFilled(false);
		SaveB.setOpaque(false);
		SaveB.setActionCommand("SaveB");
		SaveB.addActionListener(buttonClick);
		
		RetestB.setPreferredSize(new Dimension(170, 170));
		RetestB.setBorderPainted(false);
		RetestB.setContentAreaFilled(false);
		RetestB.setOpaque(false);
		RetestB.setActionCommand("RetestB");
		RetestB.addActionListener(buttonClick);
		
		VocaDAO vocaDao =new VocaDAO();
		ArrayList<VocaDTO> result = vocaDao.read("failed");
		
		String []a = {"����","���ܾ�","��"};
        String [][]b = new String[result.size()][3];
        
        for(int i=0; i< result.size(); i++) 
        {
        	b[i][0] = (i + 1) + "";
        	b[i][1] = result.get(i).getVoca();
        	b[i][2] = result.get(i).getMean();
        }

        DefaultTableModel model = new DefaultTableModel(b,a);
        JTable table = new JTable(model);
        DefaultTableModel m = (DefaultTableModel)table.getModel();
        JScrollPane sc = new JScrollPane(table);
		
		TopP.setPreferredSize(new Dimension(WIDTH, 560));
		TopP.setBackground(Color.WHITE);
		TopP.add(sc);
		
		HomeP.setLayout(new FlowLayout(FlowLayout.LEFT));
		HomeP.setPreferredSize(new Dimension(WIDTH/2-20, 190));
		HomeP.setBackground(Color.WHITE);
		HomeP.add(HomeB);	
		
		SRP.setLayout(new FlowLayout(FlowLayout.RIGHT));
		SRP.setPreferredSize(new Dimension(WIDTH/2-20, 190));
		SRP.setBackground(Color.WHITE);
		SRP.add(SaveB);	
		SRP.add(RetestB);

		BottomP.setPreferredSize(new Dimension(WIDTH-20, 200));
		BottomP.setBackground(Color.WHITE);
		BottomP.add(HomeP);
		BottomP.add(SRP);
		
		contentPane.add(TopP);
		contentPane.add(BottomP);
		
		setTitle("[8��] English Word Study");
		setBackground(Color.WHITE);
		setResizable(false);
		setVisible(false);
	}
	
	private ActionListener buttonClick =new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getActionCommand().equals("HomeB")) 
			{
				EWS ews = new EWS(1);
				System.out.println("HomeBŬ��");
				//ESW�� ȭ�� ��ȯ
				setVisible(false);
				ews.setVisible(true);
			}
			else if(e.getActionCommand().equals("SaveB"))
			{
				
				System.out.println("SaveBŬ��");
				
				ArrayList<String> temps = new ArrayList<>();
				for(int i=0; i <100; i++)
					temps.add(i+"�ܾ� ");
				
				File mFile = new File("Incorrect_Word.txt");
				
				try 
				{
					mFile.createNewFile();
					FileOutputStream fos =new FileOutputStream(mFile);		
					
					VocaDAO vocaDao =new VocaDAO();
					
					//Ʋ���ܾ� �����ö�
					ArrayList<VocaDTO> result= vocaDao.read("failed");
					
					for(int i=0; i< result.size(); i++) 
					{
						String word = "Ʋ���ܾ� : "+ result.get(i).getVoca()+" : " +
								result.get(i).getMean()+"\r\n";//ĳ���� ����(�ٹٲ�)
						byte[] bWord = word.getBytes();
						fos.write(bWord,0,bWord.length);
						
					}					
					
					//���ۿ� �ִ� �����͸� ���Ϸ� ������
					fos.flush();
					//��ü�ݱ�
					fos.close();
					//�޸� ����
					fos=null;
					
					Common.showDialog("���������� Save �Ǿ����ϴ�.");
				} 
				catch (FileNotFoundException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Common.showDialog("Save�� �̷�� ���� �ʾҽ��ϴ�.");
				} 
				catch (IOException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Common.showDialog("Save�� �̷�� ���� �ʾҽ��ϴ�.");
				}
			}
			else if(e.getActionCommand().equals("RetestB")) 
			{
				System.out.println("RetestBŬ��");
				//TW�� ȭ����ȯ (��, �̷��� �� TW������ IW�� �����θ� ������ ģ��.)
				//Ʋ���ܾ� ������ ��				
				VocaDAO vocaDao =new VocaDAO();
				ArrayList<VocaDTO> result= vocaDao.read("random");
				
				for(int i=0; i< result.size(); i++) 
				{
					System.out.println("���� "+((VocaDTO)result.get(i)).getVoca()
					+" �� "+	((VocaDTO)result.get(i)).getMean());					
				}
				
				TW tw = new TW(result);
				tw.setVisible(true);
				setVisible(false);
			}			
		}
	};
}
