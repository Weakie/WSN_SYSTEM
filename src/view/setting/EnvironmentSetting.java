package view.setting;
import global.GlobalVariables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class EnvironmentSetting extends JDialog {
	private static final long serialVersionUID = -7617806712711325063L;
	
	private final JPanel contentPanel = new JPanel();
	private JFormattedTextField formattedTextField_0;
	private JFormattedTextField formattedTextField_1;
	private JFormattedTextField formattedTextField_2;
	private JFormattedTextField formattedTextField_3;
	private JFormattedTextField formattedTextField_4;
	private JFormattedTextField formattedTextField_5;
	private JFormattedTextField formattedTextField_6;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_0;
	private JCheckBox chckbxNewCheckBox_0;
	private JCheckBox chckbxNewCheckBox_1;
	private JButton saveButton;
	private JButton disposeButton;
	
	public EnvironmentSetting(){
		this.setGUI();
		this.setVisible(true);
	}
	
	public EnvironmentSetting(JFrame owner, boolean modal) {
		super(owner, "\u8FD0\u884C\u73AF\u5883\u8BBE\u7F6E", modal);
		this.setGUI();
		this.setVisible(true);
	}
	/**
	 * Create the dialog.
	 */
	private void setGUI() {
		setFont(new Font("DialogInput", Font.PLAIN, 13));
		setTitle("\u8FD0\u884C\u73AF\u5883\u8BBE\u7F6E");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 435, 262);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new GridLayout(1, 2, 5, 5));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 200, 0)), "\u8FD0\u884C\u73AF\u5883", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
			contentPanel.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(1, 2, 5, 5));
				{
					JLabel lblNewLabel = new JLabel("Node Size");
					lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
					lblNewLabel.setToolTipText("\u4F20\u611F\u5668\u8282\u70B9\u6570\u91CF");
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
					panel_1.add(lblNewLabel);
				}
				{
					formattedTextField_1 = new JFormattedTextField();
					formattedTextField_1.setBorder(new LineBorder(Color.GRAY));
					formattedTextField_1.setForeground(Color.BLACK);
					formattedTextField_1.setHorizontalAlignment(SwingConstants.RIGHT);
					formattedTextField_1.setFont(new Font("DialogInput", Font.PLAIN, 12));
					formattedTextField_1.setText(""+GlobalVariables.nodeSize);
					panel_1.add(formattedTextField_1);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(1, 2, 5, 5));
				{
					JLabel lblNewLabel_1 = new JLabel("Pos Max");
					lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 12));
					lblNewLabel_1.setToolTipText("\u611F\u77E5\u533A\u57DFX,Y\u6700\u5927\u503C");
					lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
					panel_1.add(lblNewLabel_1);
				}
				{
					formattedTextField_2 = new JFormattedTextField();
					formattedTextField_2.setBorder(new LineBorder(Color.GRAY));
					formattedTextField_2.setFont(new Font("DialogInput", Font.PLAIN, 12));
					formattedTextField_2.setText(GlobalVariables.posMax+"");
					formattedTextField_2.setHorizontalAlignment(SwingConstants.RIGHT);
					formattedTextField_2.setForeground(Color.BLACK);
					panel_1.add(formattedTextField_2);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(1, 2, 5, 5));
				{
					JLabel lblNewLabel_2 = new JLabel("Pos Min");
					lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 12));
					lblNewLabel_2.setToolTipText("\u611F\u77E5\u533A\u57DFX,Y\u6700\u5C0F\u503C");
					lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
					panel_1.add(lblNewLabel_2);
				}
				{
					formattedTextField_3 = new JFormattedTextField();
					formattedTextField_3.setBorder(new LineBorder(Color.GRAY));
					formattedTextField_3.setText(GlobalVariables.posMin+"");
					formattedTextField_3.setHorizontalAlignment(SwingConstants.RIGHT);
					formattedTextField_3.setForeground(Color.BLACK);
					formattedTextField_3.setFont(new Font("DialogInput", Font.PLAIN, 12));
					panel_1.add(formattedTextField_3);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(1, 2, 5, 5));
				{
					JLabel lblNewLabel_3 = new JLabel("Grid Size");
					lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 12));
					lblNewLabel_3.setToolTipText("\u611F\u77E5\u533A\u57DF\u7F51\u683C\u5927\u5C0F");
					lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
					panel_1.add(lblNewLabel_3);
				}
				{
					formattedTextField_4 = new JFormattedTextField();
					formattedTextField_4.setBorder(new LineBorder(Color.GRAY));
					formattedTextField_4.setText(GlobalVariables.grid+"");
					formattedTextField_4.setHorizontalAlignment(SwingConstants.RIGHT);
					formattedTextField_4.setForeground(Color.BLACK);
					formattedTextField_4.setFont(new Font("DialogInput", Font.PLAIN, 12));
					panel_1.add(formattedTextField_4);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(1, 2, 5, 5));
				{
					JLabel lblNewLabel_4 = new JLabel("Sensed Cth");
					lblNewLabel_4.setFont(new Font("Dialog", Font.PLAIN, 12));
					lblNewLabel_4.setToolTipText("\u611F\u77E5\u9600\u503C");
					lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
					panel_1.add(lblNewLabel_4);
				}
				{
					formattedTextField_5 = new JFormattedTextField();
					formattedTextField_5.setBorder(new LineBorder(Color.GRAY));
					formattedTextField_5.setText(GlobalVariables.SensedCth+"");
					formattedTextField_5.setHorizontalAlignment(SwingConstants.RIGHT);
					formattedTextField_5.setForeground(Color.BLACK);
					formattedTextField_5.setFont(new Font("DialogInput", Font.PLAIN, 12));
					panel_1.add(formattedTextField_5);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 200, 0)), "\u5176\u5B83", TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
			contentPanel.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EmptyBorder(2, 5, 2, 5));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(1, 2, 5, 5));
				{
					rdbtnNewRadioButton_0 = new JRadioButton("\u5355\u76EE\u6807");
					rdbtnNewRadioButton_0.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							formattedTextField_0.setEnabled(false);
							saveButton.setEnabled(true);
						}
					});
					rdbtnNewRadioButton_0.setForeground(Color.DARK_GRAY);
					rdbtnNewRadioButton_0.setFont(new Font("Dialog", Font.PLAIN, 12));
					rdbtnNewRadioButton_0.setSelected(true);
					rdbtnNewRadioButton_0.setHorizontalAlignment(SwingConstants.CENTER);
					rdbtnNewRadioButton_0.setToolTipText("\u53EA\u8003\u8651\u8986\u76D6\u7387");
					panel_1.add(rdbtnNewRadioButton_0);
				}
				{
					rdbtnNewRadioButton_1 = new JRadioButton("\u591A\u76EE\u6807");
					rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							formattedTextField_0.setEnabled(true);
							saveButton.setEnabled(true);
						}
					});
					rdbtnNewRadioButton_1.setFont(new Font("Dialog", Font.PLAIN, 12));
					rdbtnNewRadioButton_1.setHorizontalAlignment(SwingConstants.CENTER);
					rdbtnNewRadioButton_1.setToolTipText("\u7EFC\u5408\u8003\u8651\u8986\u76D6\u7387\u548C\u8282\u70B9\u7684\u79FB\u52A8\u8DDD\u79BB");
					panel_1.add(rdbtnNewRadioButton_1);
				}
				ButtonGroup bg = new ButtonGroup();
				bg.add(rdbtnNewRadioButton_0);
				bg.add(rdbtnNewRadioButton_1);
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(1, 2, 5, 5));
				{
					JLabel lblNewLabel_5 = new JLabel("f1(\u8986\u76D6\u7387\u6743\u91CD)");
					lblNewLabel_5.setFont(new Font("Dialog", Font.PLAIN, 12));
					lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
					panel_1.add(lblNewLabel_5);
				}
				{
					formattedTextField_6 = new JFormattedTextField();
					formattedTextField_6.setBorder(new LineBorder(Color.GRAY));
					formattedTextField_6.setFont(new Font("DialogInput", Font.PLAIN, 12));
					formattedTextField_6.setForeground(Color.BLACK);
					formattedTextField_6.setHorizontalAlignment(SwingConstants.RIGHT);
					formattedTextField_6.setText("1");
					panel_1.add(formattedTextField_6);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(1, 2, 5, 5));
				{
					JLabel lblNewLabel_6 = new JLabel("f2(1/\u79FB\u52A8\u8DDD\u79BB)");
					lblNewLabel_6.setFont(new Font("Dialog", Font.PLAIN, 12));
					panel_1.add(lblNewLabel_6);
					lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					formattedTextField_0 = new JFormattedTextField();
					formattedTextField_0.setBorder(new LineBorder(Color.GRAY));
					formattedTextField_0.setEnabled(false);
					formattedTextField_0.setText("100");
					formattedTextField_0.setHorizontalAlignment(SwingConstants.RIGHT);
					formattedTextField_0.setForeground(Color.BLACK);
					formattedTextField_0.setFont(new Font("DialogInput", Font.PLAIN, 12));
					panel_1.add(formattedTextField_0);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EmptyBorder(6, 5, 6, 5));
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(2, 1, 0, 0));
				{
					chckbxNewCheckBox_0 = new JCheckBox("\u663E\u793A\u8282\u70B9\u901A\u4FE1\u534A\u5F84");
					chckbxNewCheckBox_0.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							saveButton.setEnabled(true);
						}
					});
					chckbxNewCheckBox_0.setHorizontalAlignment(SwingConstants.RIGHT);
					if(GlobalVariables.paintSensedRi){
						chckbxNewCheckBox_0.setSelected(true);
					}
					chckbxNewCheckBox_0.setFont(new Font("DialogInput", Font.PLAIN, 12));
					panel_1.add(chckbxNewCheckBox_0);
				}
				{
					chckbxNewCheckBox_1 = new JCheckBox("\u663E\u793A\u8282\u70B9\u79FB\u52A8\u8F68\u8FF9");
					chckbxNewCheckBox_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							saveButton.setEnabled(true);
						}
					});
					panel_1.add(chckbxNewCheckBox_1);
					chckbxNewCheckBox_1.setHorizontalAlignment(SwingConstants.RIGHT);
					if(GlobalVariables.paintPath){
						chckbxNewCheckBox_1.setSelected(true);
					}
					chckbxNewCheckBox_1.setFont(new Font("DialogInput", Font.PLAIN, 12));
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				saveButton = new JButton("\u4FDD\u5B58");
				saveButton.setEnabled(false);
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean disableButton = true;
						try{
							int nodeSize = Integer.parseInt(formattedTextField_1.getText().trim());
							GlobalVariables.nodeSize = nodeSize;
							formattedTextField_1.setBorder(new LineBorder(Color.GRAY));
						}catch(Exception e1){
							formattedTextField_1.setBorder(new LineBorder(Color.RED));
							disableButton = false;
						}
						try{
							double posMax = Double.parseDouble(formattedTextField_2.getText().trim());
							GlobalVariables.posMax = posMax;
							formattedTextField_2.setBorder(new LineBorder(Color.GRAY));
						}catch(Exception e1){
							formattedTextField_2.setBorder(new LineBorder(Color.RED));
							disableButton = false;
						}
						try{
							double posMin = Double.parseDouble(formattedTextField_3.getText().trim());
							GlobalVariables.posMin = posMin;
							formattedTextField_3.setBorder(new LineBorder(Color.GRAY));
						}catch(Exception e1){
							formattedTextField_3.setBorder(new LineBorder(Color.RED));
							disableButton = false;
						}
						try{
							double gridSize = Double.parseDouble(formattedTextField_4.getText().trim());
							GlobalVariables.grid = gridSize;
							formattedTextField_4.setBorder(new LineBorder(Color.GRAY));
						}catch(Exception e1){
							formattedTextField_4.setBorder(new LineBorder(Color.RED));
							disableButton = false;
						}
						try{
							double sensedCth = Double.parseDouble(formattedTextField_5.getText().trim());
							GlobalVariables.SensedCth = sensedCth;
							formattedTextField_5.setBorder(new LineBorder(Color.GRAY));
						}catch(Exception e1){
							formattedTextField_5.setBorder(new LineBorder(Color.RED));
							disableButton = false;
						}
						try{
							double f1 = Double.parseDouble(formattedTextField_6.getText().trim());
							GlobalVariables.f1 = f1;
							formattedTextField_6.setBorder(new LineBorder(Color.GRAY));
						}catch(Exception e1){
							formattedTextField_6.setBorder(new LineBorder(Color.RED));
							disableButton = false;
						}
						try{
							double f2 = Double.parseDouble(formattedTextField_0.getText().trim());
							GlobalVariables.f2 = f2;
							formattedTextField_0.setBorder(new LineBorder(Color.GRAY));
						}catch(Exception e1){
							formattedTextField_0.setBorder(new LineBorder(Color.RED));
							disableButton = false;
						}
						GlobalVariables.singleObject = rdbtnNewRadioButton_0.isSelected();
						GlobalVariables.paintPath = chckbxNewCheckBox_1.isSelected();
						GlobalVariables.paintSensedRi = chckbxNewCheckBox_0.isSelected();
						//重置所有属性
						GlobalVariables.init();
						if(disableButton){
							saveButton.setEnabled(false);
						}
					}
				});
				saveButton.setFont(new Font("DialogInput", Font.PLAIN, 12));
				saveButton.setActionCommand("Save");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				disposeButton = new JButton("\u5173\u95ED");
				disposeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				disposeButton.setFont(new Font("DialogInput", Font.PLAIN, 12));
				disposeButton.setActionCommand("Dispose");
				buttonPane.add(disposeButton);
			}
		}
		{
			DocumentListener listener = new DocumentListener(){
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					saveButton.setEnabled(true);
				}
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					saveButton.setEnabled(true);
				}
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					saveButton.setEnabled(true);
				}
			};
			formattedTextField_0.getDocument().addDocumentListener(listener);
			formattedTextField_1.getDocument().addDocumentListener(listener);
			formattedTextField_2.getDocument().addDocumentListener(listener);
			formattedTextField_3.getDocument().addDocumentListener(listener);
			formattedTextField_4.getDocument().addDocumentListener(listener);
			formattedTextField_5.getDocument().addDocumentListener(listener);
			formattedTextField_6.getDocument().addDocumentListener(listener);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EnvironmentSetting dialog = new EnvironmentSetting();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
