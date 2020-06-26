package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.VocaDAO;
import dao.VocaDAO.VocaDTO;
import javafx.scene.web.HTMLEditorSkin.Command;
import project.TW;

public class SW extends JFrame{
	private int WIDTH = 1000;
	private int HEIGHT = 800;
	
	private Container contentPane;
	private JLabel VocaL = new JLabel("���ܾ� ���θ� �����մϴ�.");				//�������� ������ ���ܾ ����� Label
	public JButton TestB = new JButton(new ImageIcon("image/TestB.png"));	//'����ġ��'��ư (��, 30��° ��������� visible is false)
	private JButton NextB = new JButton(new ImageIcon("image/NextB.png")); 	//'�����ܾ�'��ư 
	private JPanel TopP = new JPanel();		//���ܾ�� ���ܾ� ���� ������ Panel
	private JPanel StudyP = new JPanel();	//������ ���ܾ�� ���ܾ ��ġ�� Panel
	private JPanel BottomP = new JPanel();	//Button�� ��ġ�� Panel
	public int printCnt;	//label�� ��µ� Ƚ�� (��, �̰� 30�̻��϶� visible is true)
	public int IndexCnt;
	
	public SW()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/4, dim.height/8);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setSize(WIDTH, HEIGHT);
		contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);	
		contentPane.setLayout(new FlowLayout());
		
		TestB.setPreferredSize(new Dimension(170, 170));
		TestB.setBorderPainted(false);
		TestB.setContentAreaFilled(false);
		TestB.setOpaque(false);
		TestB.setVisible(false);	//if(print_cnt >=30) TestB.setVisible(true);
		TestB.setActionCommand("TestB");
		TestB.addActionListener(buttonClick);
		
		NextB.setPreferredSize(new Dimension(170, 170));
		NextB.setBorderPainted(false);
		NextB.setContentAreaFilled(false);
		NextB.setOpaque(false);
		NextB.setActionCommand("NextB");
		NextB.addActionListener(buttonClick);
		
		VocaL.setFont(new Font("Conslas", Font.BOLD, 50));

		StudyP.setPreferredSize(new Dimension(WIDTH-100, 300));
		StudyP.setBackground(Color.WHITE);
		StudyP.add(VocaL);

		TopP.setLayout(new BorderLayout());
		TopP.setPreferredSize(new Dimension(WIDTH, 560));
		TopP.setBackground(Color.WHITE);
		TopP.add(StudyP,BorderLayout.SOUTH);	
		
		BottomP.setLayout(new FlowLayout(FlowLayout.RIGHT));
		BottomP.setPreferredSize(new Dimension(WIDTH-40, 190));
		BottomP.setBackground(Color.WHITE);
		BottomP.add(TestB);
		BottomP.add(NextB);
		
		contentPane.add(TopP);
		contentPane.add(BottomP);
		
		
		//Timer�� VocaL�� MeanL�� 7�ʴ� �Ѱ� ��� (�̶��� print_cnt++;)		
		setTitle("[8��] English Word Study");
		setBackground(Color.WHITE);
		setResizable(false);
		setVisible(false);
		
		//�ܾ���
		VocaDAO vocaDao =new VocaDAO();
		//�̷��� 30���� �������� �����̵ȴ�.
		result= vocaDao.read("default");
		
	
		for(int i=0; i< result.size(); i++) {
			System.out.println(((VocaDTO)result.get(i)).getVoca()+"  "+
			((VocaDTO)result.get(i)).getMean());
		}
		
		//7�ʸ��� Ÿ�̸��̺�Ʈ�߻�
		mTimer.schedule(mTimerTask, 7000, 7000);
		
		//â �����Ͽ����� Ÿ�̸� ��ü �ʱ�ȭ
		//�̺�Ʈ������, ��ưŬ���ҋ� �۾��ϴ°Ŷ� ����ѿ���, â������ �̺�Ʈ�߻�
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	mTimerTask.cancel();
            	mTimer.cancel();
                e.getWindow().dispose();
            }
        });
		IndexCnt = 0;
		printCnt = 1;
	}
	
	public static ArrayList<VocaDTO> result;
	public Timer mTimer=new Timer();
	public TimerTask mTimerTask=new TimerTask() {
		@Override
		public void run() {
			//7�ʸ��� �� �۾�	
			
			//Array�� ���� ���� �� �ٽ� ó������ �ٲپ� ��
			if(IndexCnt==30)
				IndexCnt=0;
			
			//�ܾ �ѹ��� �������� �� 
			if(printCnt==30)
				TestB.setVisible(true);
			
			//�ܾ�ѱ���ڵ��ۼ�
			VocaL.setText(result.get(IndexCnt).getVoca() +"  "+ result.get(IndexCnt).getMean());			
			System.out.println("�̺�Ʈ�߻� " + IndexCnt+" ����Ʈ �߻� "+ printCnt);
			
			IndexCnt++;
			printCnt++;				
		}
	};
	
	
	private ActionListener buttonClick =new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("TestB"))
			{
				TW tw = new TW(result);
				System.out.println("TestBŬ��");	
				//Timer ����
				mTimerTask.cancel();
            	mTimer.cancel();
            	
				// TW�� ȭ�� ��ȯ
				setVisible(false);	
				tw.setVisible(true);
			}
			else if(e.getActionCommand().equals("NextB"))
			{						
				if(IndexCnt==30)
					IndexCnt=0;
				
				if(printCnt==30)
					TestB.setVisible(true);
				
				VocaL.setText(result.get(IndexCnt%30).getVoca() +"  "+ result.get(IndexCnt%30).getMean());
				System.out.print("NextBŬ�� : ");
				System.out.println("�̺�Ʈ�߻� " + IndexCnt +" ����Ʈ �߻� "+ printCnt);
				printCnt++;
				IndexCnt++;
			}			
		}
	};
}
