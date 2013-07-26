package view.setting;

import global.GlobalVariables;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import node.IrregularSensedNode;
import node.PaintedNode;

public class PanelNodeParamSetting extends JDialog implements ActionListener,
		DocumentListener {

	private static final long serialVersionUID = 1L;
	
	private String[] labelNames = { "Angle", "D", "S", "Ri", "Rc", "Rp" };
	private double[] defaultValues = { GlobalVariables.Angle,
			GlobalVariables.D, GlobalVariables.s, GlobalVariables.connectedRi,
			GlobalVariables.Rc, GlobalVariables.Rp, };
	private JTextField[] textFields;
	private JButton buttonSave = new JButton("保存");
	private JButton buttonView = new JButton("预览");

	private JTextField paintSizeField;
	private MyPanel paint = new MyPanel();

	private class MyPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private PaintedNode node = null;
		private double size;

		public void draw(PaintedNode node, double size) {
			this.node = node;
			this.size = size;
			this.repaint();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.clearRect(0, 0, getWidth(), getHeight());
			if (node == null)
				return;
			node.draw(g, size);
		}
	};

	public PanelNodeParamSetting() {
		setTitle("\u65E0\u7EBF\u4F20\u611F\u5668\u7F51\u7EDC\u611F\u77E5\u6A21\u578B\u8BBE\u7F6E");
		this.setGUI();
	}

	public PanelNodeParamSetting(JFrame owner, boolean modal) {
		super(owner, "设置节点属性", modal);
		this.setGUI();
	}

	private void setGUI() {
		JPanel panel1 = new JPanel();
		panel1.setBorder(new TitledBorder("传感器节点参数"));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		textFields = new JTextField[labelNames.length];
		for (int i = 0; i < labelNames.length; i++) {
			JPanel pTmp = new JPanel();
			pTmp.setLayout(new GridLayout(1, 2, 5, 5));
			JLabel label = new JLabel(labelNames[i], SwingConstants.CENTER);
			textFields[i] = new JTextField("" + defaultValues[i]);
			textFields[i].getDocument().addDocumentListener(this);
			textFields[i].setHorizontalAlignment(JTextField.RIGHT);
			pTmp.add(label);
			pTmp.add(textFields[i]);
			panel1.add(pTmp);
		}

		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder("传感器节点效果图"));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel2.add(paint);

		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 2, 5, 5));
		panel3.add(panel1);
		panel3.add(panel2);

		JPanel panel4 = new JPanel();
		panel4.setBorder(new TitledBorder("预览/保存"));
		panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel4.add(this.buttonView);
		buttonSave.setEnabled(false);
		panel4.add(this.buttonSave);

		JPanel panel5 = new JPanel();
		panel5.setLayout(new GridLayout(1, 2, 5, 5));
		paintSizeField = new JTextField("" + GlobalVariables.PaintSize);
		paintSizeField.setHorizontalAlignment(JTextField.RIGHT);
		paintSizeField.getDocument().addDocumentListener(this);
		panel5.add(new JLabel("显示大小/倍", SwingConstants.CENTER));
		panel5.add(paintSizeField);

		JPanel panel6 = new JPanel();
		panel6.setBorder(new TitledBorder("显示参数"));
		panel6.setLayout(new BorderLayout(5, 5));
		panel6.add(panel5, BorderLayout.SOUTH);

		JPanel panel7 = new JPanel();
		panel7.setLayout(new GridLayout(1, 2, 5, 5));
		panel7.add(panel6);
		panel7.add(panel4);
		this.buttonSave.addActionListener(this);
		this.buttonView.addActionListener(this);

		getContentPane().setLayout(new BorderLayout(5, 5));
		getContentPane().add(panel3, BorderLayout.CENTER);
		getContentPane().add(panel7, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(400, 260);
		this.setLocation(300, 200);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			int angle = (int) Double.parseDouble(textFields[0].getText().trim());
			int d = (int) Double.parseDouble(textFields[1].getText().trim());
			double s = Double.parseDouble(textFields[2].getText().trim());
			double ri = Double.parseDouble(textFields[3].getText().trim());
			double rc = Double.parseDouble(textFields[4].getText().trim());
			double rp = Double.parseDouble(textFields[5].getText().trim());
			double paintSize = Double.parseDouble(paintSizeField.getText().trim());
			
			if (arg0.getSource() == this.buttonSave) {
				//保存
				GlobalVariables.Angle = angle;
				GlobalVariables.D = d;
				GlobalVariables.s = s;
				GlobalVariables.connectedRi = ri;
				GlobalVariables.Rc = rc;
				GlobalVariables.Rp = rp;
				GlobalVariables.PaintSize = paintSize;
				//重置节点属性
				GlobalVariables.resetNodeProperties();
				this.buttonSave.setEnabled(false);
			} else if (arg0.getSource() == this.buttonView) {
				//显示节点感知半径
				IrregularSensedNode.setStaticValue(angle, d, s, ri, rc, rp);
				PaintedNode node = new IrregularSensedNode(80, 60);
				this.paint.draw(node, paintSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		this.buttonSave.setEnabled(true);
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		this.buttonSave.setEnabled(true);
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		this.buttonSave.setEnabled(true);
	}
	
	public static void main(String[] args) {
		try {
			PanelNodeParamSetting dialog = new PanelNodeParamSetting();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
