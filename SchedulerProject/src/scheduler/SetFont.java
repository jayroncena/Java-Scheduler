package scheduler;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
class SetFont extends JFrame implements ActionListener{
	private Choice choiceFont, choiceStyles, choiceSizes;
	private String fonts[] = 
		      GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	private String[] styles={"Normal", "Bold", "Italic"};
	private String[] sizes={"8", "9", "10", "11", "12", "13", "14",
						"15", "16", "17", "18", "19" ,"20",
						"30", "40", "60", "72"};

	public SetFont(){
		super("font setting");
		
		JPanel FontPanel, ButtonPanel;
		JButton confirm, cancel, colorbtn;

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
		FontPanel = new JPanel();
		ButtonPanel = new JPanel();

		confirm = new JButton("OK");
		cancel = new JButton("Cancel");
		colorbtn = new JButton("Colors");
		
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		colorbtn.addActionListener(this);

		choiceFont=new Choice();
		choiceStyles=new Choice();
		choiceSizes=new Choice();		
		for(int i=0;i<fonts.length;i++){
			choiceFont.add(fonts[i]);
		}
		for(int i=0;i<styles.length;i++){
			choiceStyles.add(styles[i]);
		}
		for(int i=0;i<sizes.length;i++){
			choiceSizes.add(sizes[i]);
		}
		choiceFont.select(Memo.ta.getFont().getFamily());
		choiceStyles.select(Memo.ta.getFont().getStyle());
		choiceSizes.select(Integer.toString(Memo.ta.getFont().getSize()));
		
		FontPanel.setLayout(new GridLayout(2,3));
		FontPanel.add(new Label("font",Label.CENTER));
		FontPanel.add(new Label("style",Label.CENTER));
		FontPanel.add(new Label("size",Label.CENTER));
		FontPanel.add(choiceFont);
		FontPanel.add(choiceStyles);
		FontPanel.add(choiceSizes);

		ButtonPanel.setLayout(new GridLayout(3,1));
		ButtonPanel.add(confirm);
		ButtonPanel.add(cancel);
		ButtonPanel.add(colorbtn);


		add(ButtonPanel, BorderLayout.EAST);
		add(FontPanel, BorderLayout.CENTER);



		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		setBounds((int)width/2-125, (int)height/2-150, 300,100);
		setVisible(true);
		setResizable(false);
	}
		
	public void actionPerformed(ActionEvent e){
		//글꼴창 이벤트
		if(e.getActionCommand() == "OK"){
			Memo.ta.setFont(new Font(choiceFont.getSelectedItem()
									,choiceStyles.getSelectedIndex()
									,Integer.parseInt(choiceSizes.getSelectedItem())));
			dispose();
			//area.setFont(new Font(name, style, fontsize));//글자체, 스타일, size 적용
		}
		else if(e.getActionCommand() == "Cancel"){
			dispose();
		}
		else if(e.getActionCommand() == "Colors"){

			Color col = JColorChooser.showDialog(this, "Choose Color", Color.RED);
			Memo.ta.setForeground(col);

		}
	}
}