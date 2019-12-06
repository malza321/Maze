import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Maze extends JFrame {
	MyLinkedStack x = new MyLinkedStack();
	MyLinkedStack y = new MyLinkedStack();

	static int i= (int)(Math.random()*10);
	static int j= (int)(Math.random()*10);
	static int z= 1;

	JPanel contentPane;
	JLabel background = new JLabel("");
	static JLabel cell[][] = new JLabel[10][10];
	static boolean block[][] = new boolean[12][12]; //10,10 밖은 벽으로 사용하기위
	static boolean direction[][][] = new boolean[12][12][5]; //1top 2right 3bottom 4left
	JButton btnNewButton2 = new JButton("Findway");
	JButton btnNewButton = new JButton("");
	public Maze() {

		setAlwaysOnTop(true);
		setTitle("Maze");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1150, 720);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		for(int i=0; i<12 ; i++){
			for(int j=0; j<12 ; j++){ //벽 방향 
				block[i][j] = false;
				direction[i][j][1]=false;
				direction[i][j][2]=false;
				direction[i][j][3]=false;
				direction[i][j][4]=false;
			}
		}
		for(int i=0 ; i<12 ; i++){ //10,10 밖의 칸들을 벽으로 
			block[0][i]=true;
			block[i][0]=true;
			block[i][11]=true;
			block[11][i]=true;
		}

		for(int i=0; i<10 ; i++){
			for(int j=0; j<10 ; j++){
				cell[i][j] = new JLabel("");
				cell[i][j].setBounds(405+45*j, 105+45*i, 40, 40);
				cell[i][j].setOpaque(true);
				cell[i][j].setBackground(Color.white);
				contentPane.add(cell[i][j]);
				cell[i][j].setVisible(true);
			}
		}
		background.setBounds(400, 100, 455, 455);
		background.setOpaque(true);
		background.setBackground(Color.black);
		contentPane.add(background);


		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton2.setVisible(false);
				findway();
			}


		});
		btnNewButton2.setBounds(540, 570, 200, 80);
		btnNewButton2.setVisible(false);
		contentPane.add(btnNewButton2);

		btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setVisible(false);
				reset();
				generator();
			}


		});
		btnNewButton.setBounds(540, 570, 200, 80);
		contentPane.add(btnNewButton);
		setVisible(true);

	}

	void reset(){
		for(int i=0; i<12 ; i++){
			for(int j=0; j<12 ; j++){
				block[i][j] = false;
				direction[i][j][1]=false;
				direction[i][j][2]=false;
				direction[i][j][3]=false;
				direction[i][j][4]=false;
			}
		}
		for(int i=0 ; i<12 ; i++){
			block[0][i]=true;
			block[i][0]=true;
			block[i][11]=true;
			block[11][i]=true;
		}

		for(int i=0; i<10 ; i++){
			for(int j=0; j<10 ; j++){		
				cell[i][j].setBounds(405+45*j, 105+45*i, 40, 40);
				cell[i][j].setBackground(Color.white);
				cell[i][j].setVisible(true);
			}
		}
		i= (int)(Math.random()*10);
		j= (int)(Math.random()*10);
	}
	void findway(){
		new Thread(){

			@Override
			public void run(){
				cell[0][0].setBackground(Color.red);
				i=0; //길찾기 0,0부터 
				j=0;
				block[i+1][j+1] = true; //지나간길은 true
				x.push(j); //스택에 저장 
				y.push(i);
				while(true){// 위쪽부터 오른쪽,아래,왼쪽 순서로 벽 확인후 진입.
					if(i>=0 && i<10 && direction[i+1][j+1][1]==true && block[i][j+1]==false){
						x.push(j);
						y.push(i);
						i = i-1;
						block[i+1][j+1]=true;
						cell[i][j].setBackground(Color.RED);
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else if(j>=0 && j<10 && direction[i+1][j+1][2]==true && block[i+1][j+2]==false){
						x.push(j);
						y.push(i);
						j = j+1;
						block[i+1][j+1]=true;
						cell[i][j].setBackground(Color.RED);
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else if(i>=0 && i<10 && direction[i+1][j+1][3]==true && block[i+2][j+1]==false){
						x.push(j);
						y.push(i);
						i = i+1;
						block[i+1][j+1]=true;
						cell[i][j].setBackground(Color.RED);
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else if(j>=0 && j<10 && direction[i+1][j+1][4]==true && block[i+1][j]==false){
						x.push(j);
						y.push(i);
						j = j-1;
						block[i+1][j+1]=true;
						cell[i][j].setBackground(Color.RED);
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if(i==9 && j==9)break;
					//왔던길을 제외한 벽3개가 막혔거나 벽2개와 왔던길2개, 벽한개와 왔던길 3개, 왔던길 4개면 다시 뒤로돌아감//
					while((direction[i+1][j+1][1]==false &&direction[i+1][j+1][2]==false &&direction[i+1][j+1][3]==false)||
							(direction[i+1][j+1][1]==false &&direction[i+1][j+1][2]==false &&direction[i+1][j+1][4]==false)||
							(direction[i+1][j+1][1]==false &&direction[i+1][j+1][3]==false &&direction[i+1][j+1][4]==false)||
							(direction[i+1][j+1][2]==false &&direction[i+1][j+1][3]==false &&direction[i+1][j+1][4]==false)||
							(direction[i+1][j+1][1]==false && direction[i+1][j+1][3]==false && block[i+1][j]==true && block[i+1][j+2]==true)||
							(direction[i+1][j+1][2]==false && direction[i+1][j+1][4]==false && block[i][j+1]==true && block[i+2][j+1]==true)||
							(direction[i+1][j+1][1]==false && direction[i+1][j+1][2]==false && block[i+1][j]==true && block[i+2][j+1]==true)||
							(direction[i+1][j+1][1]==false && direction[i+1][j+1][4]==false && block[i+1][j+2]==true && block[i+2][j+1]==true)||
							(direction[i+1][j+1][2]==false && direction[i+1][j+1][3]==false && block[i][j+1]==true && block[i+1][j]==true)||
							(direction[i+1][j+1][3]==false && direction[i+1][j+1][4]==false && block[i][j+1]==true && block[i+1][j+2]==true)||
							(direction[i+1][j+1][1]==false && block[i+1][j+2]==true && block[i+2][j+1]==true && block[i+1][j]==true)||
							(direction[i+1][j+1][2]==false && block[i][j+1]==true && block[i+2][j+1]==true && block[i+1][j]==true)||
							(direction[i+1][j+1][3]==false && block[i][j+1]==true && block[i+1][j+2]==true && block[i+1][j]==true)||
							(direction[i+1][j+1][4]==false && block[i][j+1]==true && block[i+1][j+2]==true && block[i+2][j+1]==true)||
							(block[i][j+1]==true && block[i+1][j+2]==true && block[i+2][j+1]==true && block[i+1][j]==true))
					{
						cell[i][j].setBackground(Color.gray);		
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						j=x.pop();
						i=y.pop();

					}

				}
				btnNewButton.setText("restart");
				btnNewButton.setVisible(true);


			}


		}.start();
	}

	void generator() {
		new Thread(){
			@Override
			public void run(){
				cell[i][j].setBackground(Color.cyan); //0,0부터 9,9 까지 랜덤으로 한곳에서 시작 
				block[i+1][j+1] = true;
				x.push(j); 
				y.push(i);
				while(x.top!=null){//모든 길이 true가 될때까지 반복.

					z=(int)(Math.random()*4)+1; // 1top , 2right, 3bottom , 4left
					while(true){
						if(i>=0 && i<10 && z==1 && block[i][j+1]==false){
							x.push(j); //지나가면서 스택에 저
							y.push(i);
							direction[i+1][j+1][1]=true;//벽 뚫을시 true
							i = i-1;
							cell[i][j].setBounds(405+45*j, 105+45*i, 40, 45);
							cell[i][j].setBackground(Color.CYAN);
							block[i+1][j+1]=true; //왔던길 true
							direction[i+1][j+1][3]=true; //위쪽벽을 뚫었으니 위쪽셀 입장에서는 아래가 뚫린것.


							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}


							break;
						}else if(j>=0 && j<10 && z==2 && block[i+1][j+2]==false){
							x.push(j);
							y.push(i);
							direction[i+1][j+1][2]=true;
							j = j+1;
							cell[i][j].setBounds(400+45*j, 105+45*i, 45, 40);
							cell[i][j].setBackground(Color.cyan);
							block[i+1][j+1]=true;
							direction[i+1][j+1][4]=true;

							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							break;
						}else if(i>=0 && i<10 && z==3 && block[i+2][j+1]==false){
							x.push(j);
							y.push(i);
							direction[i+1][j+1][3]=true;
							i = i+1;
							cell[i][j].setBounds(405+45*j, 100+45*i, 40, 45);
							cell[i][j].setBackground(Color.CYAN);
							block[i+1][j+1]=true;
							direction[i+1][j+1][1]=true;

							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							break;
						}else if(j>=0 && j<10 && z==4 && block[i+1][j]==false){
							x.push(j);
							y.push(i);
							direction[i+1][j+1][4]=true;
							j = j-1;
							cell[i][j].setBounds(405+45*j, 105+45*i, 45, 40);
							cell[i][j].setBackground(Color.cyan);
							block[i+1][j+1]=true;
							direction[i+1][j+1][2]=true;

							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							break;
						}
						z= (z+1)%5;
					}
					//3면이 지나간 길이면 스택에서 하나씩 꺼내면서 후퇴.
					while(block[i][j+1]==true && block[i+2][j+1]==true && block[i+1][j]==true && block[i+1][j+2]==true){
						cell[i][j].setBackground(Color.gray);		
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(x.top==null)break;					
						j=x.pop();
						i=y.pop();

					}


				}
				btnNewButton2.setVisible(true);
				for(int i=0; i<12 ; i++){
					for(int j=0; j<12 ; j++){
						block[i][j] = false;
					}
				}
				for(int i=0 ; i<12 ; i++){
					block[0][i]=true;
					block[i][0]=true;
					block[i][11]=true;
					block[11][i]=true;
				}

			}


		}.start();

	}

	class Node{
		int data;
		Node next;
	}

	class MyLinkedStack{
		Node top;
		void push(int item) {
			Node n = new Node();
			n.data = item;
			n.next = top; 
			top = n; 
		}
		int pop() {
			int data = top.data; 
			top = top.next;
			return data;
		}
	}

	public static void main(String[] args) {
		new Maze();

	}
}
