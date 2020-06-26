package project;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.VocaDAO;
import dao.VocaDAO.VocaDTO;
import project.TR;

class CompleteDlog extends JDialog
{	
	private int WIDTH = 300;
	private int HEIGHT = 200;
	
	private Container contentPane;
	private JLabel Message = new JLabel("<html>������ �������� ������ �Ϸ��ϼ̽��ϴ�!<br>�������� �������ڽ��ϴ�.</html>");	//�˸�â ����
	private JButton CheckB = new JButton("Ȯ��");		//�˸�â Ȯ�ι�ư
	private JPanel MessageP = new JPanel();			//�˸�â ������ ��ġ�� Panel
	public boolean result;
	
	public CompleteDlog()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/3, dim.height/4);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setSize(WIDTH, HEIGHT);
		contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);	
		contentPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		Message.setHorizontalAlignment(SwingConstants.CENTER);		

		CheckB.setActionCommand("CheckB");
		CheckB.addActionListener(buttonClick);

		MessageP.setLayout(new BorderLayout());
		MessageP.setPreferredSize(new Dimension(300, 120));
		MessageP.setBackground(Color.WHITE);
		MessageP.add(Message, BorderLayout.CENTER);	
		
		contentPane.add(MessageP);
		contentPane.add(CheckB);
		
		result = false;

		setTitle("���ܾ� ���� �Ϸ�");
		setBackground(Color.WHITE);
		setResizable(false);
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	private ActionListener buttonClick =new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getActionCommand().equals("CheckB")) 
			{
				result = true;
				setVisible(false);
			}			
		}
	};
	
	public static void main(String[] args) {
		new CompleteDlog();
	}
}

public class TW extends JFrame{
	private int WIDTH = 1000;
	private int HEIGHT = 800;
	
	private Container contentPane;
	public JLabel CountL= new JLabel("1/30");	//���� ��ܿ� 30���� ���°���� ���̴� ��
	private JLabel QuizL=new JLabel("���ݺ��� ������ �����ϰڽ��ϴ�.");	//����� ������ '���ܾ��� ��'�� ���̴� ��
	private JTextField WriteT = new JTextField("�� ���� ������ �����ϴ� ���ܾ ������");
	public JButton CheckB = new JButton("Ȯ��");	//�� ������ ������ �Է��ϰ� ������ Button
	private JPanel TopP = new JPanel();		//��°�� ��Ÿ���� ���� ��ġ�� Panel
	private JPanel MidleP = new JPanel();
	private JPanel BottomP = new JPanel();
	public int printCnt;
	
	public ArrayList<VocaDTO> vocas;

	
	public TW(ArrayList<VocaDTO> vocas) 
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/4, dim.height/8);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.vocas=vocas;
		setSize(WIDTH, HEIGHT);
		contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);	
		contentPane.setLayout(new FlowLayout());
		
		CountL.setFont(new Font("Conslas", Font.BOLD, 70));
		CountL.setForeground(new Color(85,142,213));

		QuizL.setFont(new Font("Conslas", Font.BOLD, 60));
		QuizL.setHorizontalAlignment(SwingConstants.CENTER);
		
		WriteT.setPreferredSize(new Dimension(300, 30));
		
		CheckB.setBackground(new Color(85,142,213));
		CheckB.setForeground(Color.WHITE);
		CheckB.setActionCommand("CheckB");
		CheckB.addActionListener(buttonClick);

		TopP.setLayout(new FlowLayout(FlowLayout.RIGHT));
		TopP.setPreferredSize(new Dimension(WIDTH-30, 80));
		TopP.setBackground(Color.WHITE);
		TopP.add(CountL);

		MidleP.setLayout(new BorderLayout());
		MidleP.setPreferredSize(new Dimension(WIDTH, 440));
		MidleP.setBackground(Color.WHITE);
		MidleP.add(QuizL, BorderLayout.CENTER);

		BottomP.setBackground(Color.WHITE);
		BottomP.add(WriteT);
		BottomP.add(CheckB);
		
		contentPane.add(TopP);
		contentPane.add(MidleP);
		contentPane.add(BottomP);
		
		QuizL.setText(vocas.get(0).getMean());
		printCnt = 0;

		setTitle("[8��] English Word Study");
		setBackground(Color.WHITE);
		setResizable(false);
		setVisible(false);
		
		for(int i=0; i <this.vocas.size(); i++) 
		{
			System.out.println(vocas.get(i).getVoca());
		}
		
		fails= new ArrayList<String>();
	}
	private int hitCnt=0;//�����
	
	//������
	public ArrayList<String> fails;
	
	private ActionListener buttonClick =new ActionListener()
	{		
		public void actionPerformed(ActionEvent e){	
			System.out.println((printCnt)+" : "+vocas.get(printCnt).getMean() 
					+ " "+vocas.get(printCnt).getVoca()
					+  " " + WriteT.getText().toString());
			int passed=1;
			if(vocas.get(printCnt).getVoca().equals(WriteT.getText().toString()))
			{
				hitCnt++;
				System.out.println("����");
			}
			else 
			{
				passed=0;
				//������ ����
				fails.add(vocas.get(printCnt).getMean());
				System.out.println("���� : "+vocas.get(printCnt).getMean());
			}

			
			
			VocaDAO vocaDAo =new VocaDAO();
			String sql ="UPDATE voca set passed = "+ passed + " WHERE idx = " + vocas.get(printCnt).getIdx();
			vocaDAo.update(sql);
			printCnt++;
			
			if(printCnt<=29)
			{				
				CountL.setText((printCnt + 1) + "/30");
				//���� ���ܾ� ���� QuizL�� ���			
				QuizL.setText(vocas.get(printCnt).getMean());
			}
			else
			{
				TR tr = new TR(vocas, fails);
				Common.showDialog("����Ϸ�! ����� �������ϴ�.");
				//���⼭ TRȭ������ �Ѿ��, vocas�� fails�� ���� �Ѱ��ش�.
				tr.setVisible(true);
				setVisible(false);		
			}
			WriteT.setText("");
		}
	};
}
