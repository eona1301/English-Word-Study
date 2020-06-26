package project;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.VocaDAO;
import dao.VocaDAO.VocaDTO;

import project.SW;
import project.MV;

public class EWS extends JFrame{
	private int WIDTH = 1000;
	private int HEIGHT = 800;
	
	private Container contentPane;
	private ImageIcon logo = new ImageIcon("image/EWSlogo.png");	//EWS Logo ����
	private JLabel EWSlogo = new JLabel(logo);						//EWS ������ ���� Label
	private JButton StudyB = new JButton(new ImageIcon("image/StudyB.png"));	//'�����ϱ�'��ư
	private JButton MoreB = new JButton(new ImageIcon("image/moreviewB.png"));	//'������' ��ư
	private JPanel LogoP = new JPanel();	//Logo�� ��ġ�� Panel
	private JPanel ButtonP = new JPanel();	//Button�� ��ġ�� Panel
	private JPanel EmptyP = new JPanel();	//�� ������ ������� Panel
	
	public EWS()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/4, dim.height/8);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setSize(WIDTH, HEIGHT);
		contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new FlowLayout());
		
		StudyB.setPreferredSize(new Dimension(170, 175));
		StudyB.setBorderPainted(false);
		StudyB.setContentAreaFilled(false);
		StudyB.setOpaque(false);
		StudyB.setActionCommand("StudyB");
		StudyB.addActionListener(buttonClick);
		
		MoreB.setPreferredSize(new Dimension(170, 175));
		MoreB.setBorderPainted(false);
		MoreB.setContentAreaFilled(false);
		MoreB.setOpaque(false);		
		MoreB.setActionCommand("MoreB");
		MoreB.addActionListener(buttonClick);
		
		LogoP.setLayout(new BorderLayout());
		LogoP.setBackground(Color.WHITE);
		LogoP.setPreferredSize(new Dimension(WIDTH, 560));
		LogoP.add(EWSlogo, BorderLayout.SOUTH);
		
		EmptyP.setBackground(Color.WHITE);
		EmptyP.setPreferredSize(new Dimension(620, 170));

		ButtonP.setBackground(Color.WHITE);
		ButtonP.setPreferredSize(new Dimension(WIDTH, 200));
		ButtonP.add(MoreB);
		ButtonP.add(EmptyP);
		ButtonP.add(StudyB);
		
		contentPane.add(LogoP);
		contentPane.add(MoreB);
		contentPane.add(EmptyP);
		contentPane.add(StudyB);

		this.setTitle("[8��] English Word Study");
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		this.setVisible(true);
		/*
		VocaDAO vocaDao =new VocaDAO();
		//���о�Ë�
		ArrayList<VocaDTO> result= vocaDao.read("default");
		for(int i=0; i< result.size(); i++) {
			System.out.println("�ܾ� "+
			((VocaDTO)result.get(i)).getVoca()
			+" �� "+
			((VocaDTO)result.get(i)).getMean());
			
		}
		//Ʋ���ܾ� �����Ë�
		ArrayList<VocaDTO> result2= vocaDao.read("failed");
		for(int i=0; i< result2.size(); i++) {
			System.out.println("���� "+
			((VocaDTO)result2.get(i)).getVoca()
			+" �� "+
			((VocaDTO)result2.get(i)).getMean());
			
		}
		//�ܾ� �˻��Ҷ�
				ArrayList<VocaDTO> result3= vocaDao.read("�ܾ�1");
				if(result3.size()==0) {
					//�ܾ����
				}
				for(int i=0; i< result3.size(); i++) {
					System.out.println("�˻� : "+
					((VocaDTO)result3.get(i)).getVoca()
					+" �� "+
					((VocaDTO)result3.get(i)).getMean());
					
				}
		*/
	
	}
	
	public EWS(int in)
	{		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/4, dim.height/8);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setSize(WIDTH, HEIGHT);
		contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);	
		contentPane.setLayout(new FlowLayout());
		
		StudyB.setPreferredSize(new Dimension(170, 175));
		StudyB.setBorderPainted(false);
		StudyB.setContentAreaFilled(false);
		StudyB.setOpaque(false);
		StudyB.setActionCommand("StudyB");
		StudyB.addActionListener(buttonClick);
		
		MoreB.setPreferredSize(new Dimension(170, 175));
		MoreB.setBorderPainted(false);
		MoreB.setContentAreaFilled(false);
		MoreB.setOpaque(false);		
		MoreB.setActionCommand("MoreB");
		MoreB.addActionListener(buttonClick);
		
		LogoP.setLayout(new BorderLayout());
		LogoP.setBackground(Color.WHITE);
		LogoP.setPreferredSize(new Dimension(WIDTH, 560));
		LogoP.add(EWSlogo, BorderLayout.SOUTH);
		
		EmptyP.setBackground(Color.WHITE);
		EmptyP.setPreferredSize(new Dimension(620, 170));

		ButtonP.setBackground(Color.WHITE);
		ButtonP.setPreferredSize(new Dimension(WIDTH, 200));
		ButtonP.add(MoreB);
		ButtonP.add(EmptyP);
		ButtonP.add(StudyB);
		
		contentPane.add(LogoP);
		contentPane.add(MoreB);
		contentPane.add(EmptyP);
		contentPane.add(StudyB);

		this.setTitle("[8��] English Word Study");
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		this.setVisible(false);
	}
		
	private ActionListener buttonClick =new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{			
			if(e.getActionCommand().equals("StudyB")) 
			{
				SW sw = new SW();
				System.out.println("StudyBŬ��");
				//SW�� ȭ�� ��ȯ
				setVisible(false);
				sw.setVisible(true);
			}
			else if(e.getActionCommand().equals("MoreB")) 
			{
				MV mv = new MV();
				System.out.println("MoreBŬ��");
				//TW�� ȭ����ȯ
				setVisible(false);
				mv.setVisible(true);
			}
		}
	};
	
	public static void main(String[] args) 
	{
		new EWS();
	}
}
