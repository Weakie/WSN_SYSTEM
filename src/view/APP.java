package view;

import global.CompareAlgorithmUtil;
import global.GlobalMainControl;
import global.GlobalVariables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import node.NodeList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BoxLayout;
import swing2swt.layout.FlowLayout;
import view.setting.AddNewAlgorithm;
import view.setting.AlgorithmParamSetting;
import view.setting.EnvironmentSetting;
import view.setting.PanelNodeParamSetting;
import algorithm.Algorithm;
import chart.BarCharts;
import chart.MultiBarCharts;
import chart.MultiLineCharts;
import constants.AlgorithmConstants;
import control.AppCompareControl;
import control.AppControl;
import control.AppDrawResultControl;
import control.AppPaintResultControl;

public class APP {

	protected Shell shlWsnSystem;
	//
	private Composite paramSetting;
	private Tree algorithmTree;
	//
	private Spinner scaleNumSpinner;
	private Spinner loopNumSpinner;
	private Label showParameter;
	private Spinner runTimeSpinner;
	private ToolBar toolBar;
	private ToolBar toolBar_1;
	private Menu menu;
	//
	Display display;
	private StyledText console;
	private StyledText output;
	private Label labelFinishPer;
	private Label labelRunning;
	private ProgressBar progressBar;
	//初始位置
	private PaintPanel initPosPaintPanel;
	private JScrollPane initPosScrollPanel;
	//
	private Tree algoShowTree;
	private JPanel drawResultPanel;
	private Tree algoCompTree;
	private JPanel appCompPanel;
	private Tree algoResultTree;
	private JPanel appResultPanel;
	//
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		GlobalVariables.init();
		try {
			APP window = new APP();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shlWsnSystem.open();
		shlWsnSystem.layout();
		while (!shlWsnSystem.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		GlobalMainControl.getInstance().stop();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlWsnSystem = new Shell();
		shlWsnSystem.setImage(SWTResourceManager.getImage("icon\\main.jpg"));
		shlWsnSystem.setSize(853, 577);
		shlWsnSystem.setText("WSN System");
		shlWsnSystem.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//菜单栏
		menu = new Menu(shlWsnSystem, SWT.BAR);
		shlWsnSystem.setMenuBar(menu);
		
		MenuItem menuItemFile = new MenuItem(menu, SWT.CASCADE);
		menuItemFile.setText("\u6587\u4EF6");
		
		Menu menu_1 = new Menu(menuItemFile);
		menuItemFile.setMenu(menu_1);
		
		MenuItem menuItem_1 = new MenuItem(menu_1, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dialog = new FileDialog(shlWsnSystem,SWT.SAVE);
				dialog.setFilterPath("result");// 设置默认的路径
				dialog.setText("保存运行结果");//设置对话框的标题
				dialog.setFileName("result");//设置默认的文件名
				dialog.setFilterNames(new String[] { "文本文件 (*.txt)", "序列化结果(*.seriresult)" });//设置扩展名
				dialog.setFilterExtensions(new String[] { "*.txt", "*.seriresult" });//设置文件扩展名
				String fileName = dialog.open();
				try {
					if(fileName!=null&&fileName.split("\\.").length>0){
						String extention = fileName.split("\\.")[1];
						if(extention.equals("seriresult")){
							GlobalMainControl.getInstance().saveRunResultSeri(fileName);
						}else if(extention.equals("txt")){
							GlobalMainControl.getInstance().saveRunResult(fileName);
						}
					}
				} catch (Exception e) {
		           e.printStackTrace();
		        }
			}
		});
		menuItem_1.setText("\u4FDD\u5B58\u8FD0\u884C\u7ED3\u679C");
		
		MenuItem menuItem_4 = new MenuItem(menu_1, SWT.NONE);
		menuItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dialog = new FileDialog(shlWsnSystem,SWT.OPEN);
				dialog.setFilterPath("result");// 设置默认的路径
				dialog.setText("读取运行结果");//设置对话框的标题
				dialog.setFileName("result");//设置默认的文件名
				dialog.setFilterNames(new String[] {"序列化结果(*.seriresult)" });//设置扩展名
				dialog.setFilterExtensions(new String[] { "*.seriresult" });//设置文件扩展名
				String fileName = dialog.open();
				try {
					if(fileName!=null&&fileName.split("\\.").length>0){
						String extention = fileName.split("\\.")[1];
						if(extention.equals("seriresult")){
							GlobalMainControl.getInstance().readRunResultSeri(fileName);
						}
					}
				} catch (Exception e) {
		           e.printStackTrace();
		        }
			}
		});
		menuItem_4.setText("\u8BFB\u53D6\u8FD0\u884C\u7ED3\u679C");
		
		MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
		menuItem.setText("\u8BBE\u7F6E");
		
		Menu menu_2 = new Menu(menuItem);
		menuItem.setMenu(menu_2);
		
		MenuItem menuItem_2 = new MenuItem(menu_2, SWT.NONE);
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new PanelNodeParamSetting();
			}
		});
		menuItem_2.setText("\u4F20\u611F\u5668\u8282\u70B9\u8BBE\u7F6E");
		
		MenuItem menuItem_3 = new MenuItem(menu_2, SWT.NONE);
		menuItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new EnvironmentSetting();
			}
		});
		menuItem_3.setText("\u8FD0\u884C\u73AF\u5883\u8BBE\u7F6E");
		
		//主体
		Composite composite_11 = new Composite(shlWsnSystem, SWT.NONE);
		composite_11.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(composite_11, SWT.VERTICAL);
		
		Composite composite = new Composite(sashForm, SWT.EMBEDDED);
		composite.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		//上层设置
		CTabFolder tabFolder = new CTabFolder(composite, SWT.BORDER);
		tabFolder.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		tabFolder.setSimple(false);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		//算法设置
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("\u7B97\u6CD5\u8BBE\u7F6E");
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(composite_3);
		composite_3.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		SashForm sashForm_1 = new SashForm(composite_3, SWT.BORDER);
		sashForm_1.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		
		Composite composite_6 = new Composite(sashForm_1, SWT.NONE);
		composite_6.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		toolBar_1 = new ToolBar(composite_6, SWT.BORDER | SWT.FLAT | SWT.RIGHT);
		toolBar_1.setLayoutData(swing2swt.layout.BorderLayout.NORTH);
		
		ToolItem tltmNewItem_3 = new ToolItem(toolBar_1, SWT.NONE);
		tltmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				initPosScrollPanel.setVisible(false);
				GlobalVariables.resetInitPosition();
				initPosPaintPanel.draw(new NodeList());
				initPosScrollPanel.setVisible(true);
			}
		});
		tltmNewItem_3.setText("\u91CD\u65B0\u751F\u6210");
		
		ToolItem tltmRefresh = new ToolItem(toolBar_1, SWT.NONE);
		tltmRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				initPosScrollPanel.setVisible(false);
				initPosPaintPanel.draw(new NodeList());
				initPosScrollPanel.setVisible(true);
			}
		});
		tltmRefresh.setText("\u5237\u65B0");
		
		Composite composite_14 = new Composite(composite_6, SWT.EMBEDDED);
		composite_14.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		composite_14.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_10 = new Composite(composite_14, SWT.EMBEDDED);
		
		Frame frame = SWT_AWT.new_Frame(composite_10);
		initPosScrollPanel = new JScrollPane();
		frame.add(initPosScrollPanel, BorderLayout.CENTER);
		
		initPosPaintPanel = new PaintPanel();
		initPosPaintPanel.setForeground(Color.BLACK);
		initPosPaintPanel.setBackground(Color.LIGHT_GRAY);
		initPosPaintPanel.draw(new NodeList());
		initPosScrollPanel.setViewportView(initPosPaintPanel);
		
		Composite composite_13 = new Composite(sashForm_1, SWT.NONE);
		composite_13.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		toolBar = new ToolBar(composite_13, SWT.BORDER | SWT.RIGHT);
		toolBar.setLayoutData(swing2swt.layout.BorderLayout.NORTH);
		
		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.setWidth(31);
		tltmNewItem.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				algorithmTree.removeAll();
				//add new 
				AddNewAlgorithm dialog = new AddNewAlgorithm(shlWsnSystem,SWT.SYSTEM_MODAL);
				Object obj = dialog.open();
				if(obj != null){
					try {
						GlobalMainControl.getInstance().addAlgorithm((Map<String,Object>)obj);
					} catch (Exception e) {
						if(console != null&& !console.isDisposed()){
							console.append("\n"+"添加算法异常："+e+"\n");
							for(StackTraceElement error : e.getStackTrace()){
								console.append(error.toString()+"\n");
								console.setSelection(console.getCharCount());
							}
							console.setSelection(console.getCharCount());
						}
					}
				}
				Set<String> keySet = GlobalMainControl.getInstance().getConfigedAlgorithmNames();
				for(String key:keySet){
					TreeItem treeItem = new TreeItem(algorithmTree,SWT.NONE);
					treeItem.setText(0,key);
				}
				loopNumSpinner.setEnabled(false);
				scaleNumSpinner.setEnabled(false);
				showParameter.setText("");
			}
		});
		tltmNewItem.setText("\u6DFB\u52A0\u7B97\u6CD5");
		
		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// nothing selected
				if(algorithmTree.getSelection().length == 0)
					return;
				// remove&delete
				TreeItem item = algorithmTree.getSelection()[0];
				String algoName = item.getText(0); 
				GlobalMainControl.getInstance().deleteAlgorithm(algoName);
				GlobalMainControl.getInstance().deleteAlgoResult(algoName);
				algorithmTree.removeAll();
				// show all
				Set<String> keySet = GlobalMainControl.getInstance().getConfigedAlgorithmNames();
				for(String key:keySet){
					TreeItem treeItem = new TreeItem(algorithmTree,SWT.NONE);
					treeItem.setText(0,key);
				}
				loopNumSpinner.setEnabled(false);
				scaleNumSpinner.setEnabled(false);
				showParameter.setText("");
			}
		});
		tltmNewItem_1.setText("\u79FB\u9664\u7B97\u6CD5");
		
		ToolItem tltmNewItem_2 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// nothing selected
				if(algorithmTree.getSelection().length == 0)
					return;
				// get value
				AlgorithmParamSetting setting = (AlgorithmParamSetting)paramSetting;
				Map<String,Object> param = setting.saveParam();
				int loopNum = loopNumSpinner.getSelection();
				int scaleSize = scaleNumSpinner.getSelection();
				// save value
				TreeItem item = algorithmTree.getSelection()[0];
				String algoName = item.getText(0); 
				Algorithm algo = GlobalMainControl.getInstance().getAlgorithm(algoName);
				algo.getRunParams().getConvert().fromMap(param);
				algo.setLoopNum(loopNum);
				algo.setScaleNum(scaleSize);
				// show value
				showParameter.setText(" name="+algoName+"\n type="+algo.getName()+"\n loopNum="+algo.getLoopNum()+"\n scaleNum="+algo.getScaleNum()+"\n"+algo.getRunParams());
			}
		});
		tltmNewItem_2.setText("\u4FDD\u5B58\u8BBE\u7F6E");
		
		ToolItem tltmRun = new ToolItem(toolBar, SWT.NONE);
		tltmRun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				List<String> names = new ArrayList<String>();
				for(TreeItem item:algorithmTree.getItems()){
					if(item.getChecked()){
						names.add(item.getText(0));
					}
				}
				if(names.isEmpty())
					return;
				
				try {
					GlobalMainControl.getInstance().setAlgorithmRunTime(runTimeSpinner.getSelection());
					GlobalMainControl.getInstance().runAlgorihm(names, new APPControlImpl());
				} catch (Exception e) {
					if(console != null&& !console.isDisposed()){
						console.append("\n"+"算法运行异常："+e+"\n");
						for(StackTraceElement error : e.getStackTrace()){
							console.append(error.toString()+"\n");
							console.setSelection(console.getCharCount());
						}
						console.setSelection(console.getCharCount());
					}
				}
			}
		});
		tltmRun.setText("\u8FD0\u884C\u7B97\u6CD5");
		
		SashForm sashForm_2 = new SashForm(composite_13, SWT.NONE);
		sashForm_2.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		
		Composite composite_12 = new Composite(sashForm_2, SWT.NONE);
		composite_12.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		algorithmTree = new Tree(composite_12, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		algorithmTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				{
					// Identify the selected row
					TreeItem item = (TreeItem) e.item;
					String algoName = item.getText(0);
					Algorithm algo = GlobalMainControl.getInstance().getAlgorithm(algoName);
					AlgorithmParamSetting setting = (AlgorithmParamSetting) paramSetting;
					// show value
					setting.showParam(algo.getRunParams().getConvert());
					loopNumSpinner.setSelection(algo.getLoopNum());
					loopNumSpinner.setEnabled(true);
					scaleNumSpinner.setSelection(algo.getScaleNum());
					scaleNumSpinner.setEnabled(true);
					// show value
					showParameter.setText(" name=" + algoName + "\n type="
							+ algo.getName() + "\n loopNum="
							+ algo.getLoopNum() + "\n scaleNum="
							+ algo.getScaleNum() + "\n" + algo.getRunParams());
				}
			}
		});
		algorithmTree.setHeaderVisible(true);
		
		TreeColumn trclmnAlgorithm = new TreeColumn(algorithmTree, SWT.NONE);
		trclmnAlgorithm.setWidth(131);
		trclmnAlgorithm.setText("\u7B97\u6CD5\u5217\u8868");
		
		Composite composite_15 = new Composite(sashForm_2, SWT.BORDER);
		composite_15.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		Composite composite_5 = new Composite(composite_15, SWT.NONE);
		composite_5.setLayoutData(swing2swt.layout.BorderLayout.NORTH);
		
		loopNumSpinner = new Spinner(composite_5, SWT.BORDER);
		loopNumSpinner.setBounds(89, 10, 91, 23);
		loopNumSpinner.setEnabled(false);
		loopNumSpinner.setMaximum(10000);
		loopNumSpinner.setMinimum(1);
		
		scaleNumSpinner = new Spinner(composite_5, SWT.BORDER);
		scaleNumSpinner.setBounds(89, 39, 91, 23);
		scaleNumSpinner.setEnabled(false);
		scaleNumSpinner.setMaximum(10000);
		scaleNumSpinner.setMinimum(1);
		scaleNumSpinner.setTouchEnabled(true);
		
		CLabel lblLoopnum = new CLabel(composite_5, SWT.NONE);
		lblLoopnum.setBounds(10, 10, 73, 23);
		lblLoopnum.setText("\u8FED\u4EE3\u6B21\u6570");
		
		CLabel lblNewLabel = new CLabel(composite_5, SWT.NONE);
		lblNewLabel.setBounds(0, 66, 93, 23);
		lblNewLabel.setText("\u7B97\u6CD5\u8FD0\u884C\u53C2\u6570");
		
		CLabel lblNewLabel_1 = new CLabel(composite_5, SWT.NONE);
		lblNewLabel_1.setBounds(10, 39, 73, 23);
		lblNewLabel_1.setText("\u7B97\u6CD5\u89C4\u6A21");
		
		showParameter = new Label(composite_15, SWT.BORDER);
		showParameter.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		
		paramSetting = new AlgorithmParamSetting(sashForm_2, SWT.NONE);
		sashForm_2.setWeights(new int[] {144, 193, 190});
		
		Composite composite_16 = new Composite(composite_13, SWT.NONE);
		composite_16.setLayoutData(swing2swt.layout.BorderLayout.SOUTH);
		composite_16.setLayout(new GridLayout(5, false));
		
		Label label = new Label(composite_16, SWT.NONE);
		label.setText("\u72EC\u7ACB\u8FD0\u884C\u6B21\u6570");
		
		runTimeSpinner = new Spinner(composite_16, SWT.BORDER);
		GridData gd_runTimeSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_runTimeSpinner.widthHint = 46;
		runTimeSpinner.setLayoutData(gd_runTimeSpinner);
		runTimeSpinner.setMinimum(1);
		new Label(composite_16, SWT.NONE);
		
		Button btnStop = new Button(composite_16, SWT.NONE);
		btnStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GlobalMainControl.getInstance().stop();
			}
		});
		GridData gd_btnStop = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnStop.widthHint = 65;
		btnStop.setLayoutData(gd_btnStop);
		btnStop.setText("\u505C\u6B62");
		sashForm_1.setWeights(new int[] {287, 533});
		
		//运行结果
		CTabItem tbtmNewItem_4 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_4.setText("\u8FD0\u884C\u6548\u679C");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem_4.setControl(composite_2);
		composite_2.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		ToolBar toolBar_2 = new ToolBar(composite_2, SWT.BORDER | SWT.FLAT | SWT.RIGHT);
		toolBar_2.setLayoutData(swing2swt.layout.BorderLayout.NORTH);
		
		ToolItem tltmNewItem_4 = new ToolItem(toolBar_2, SWT.NONE);
		tltmNewItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				List<String> names = new ArrayList<String>();
				for(TreeItem item:algoShowTree.getItems()){
					if(item.getChecked()){
						names.add(item.getText(0));
					}
				}
				if(names.isEmpty()){
					drawResultPanel.setVisible(false);
					drawResultPanel.removeAll();
					drawResultPanel.setVisible(true);
					return;
				}
				try {
					GlobalMainControl.getInstance().drawNodesResult(names, new AppDrawControlImpl());
				} catch (Exception e) {
					if(console != null&& !console.isDisposed()){
						console.append("\n"+"显示运行结果出现异常:"+e+"\n");
						for(StackTraceElement error : e.getStackTrace()){
							console.append(error.toString()+"\n");
							console.setSelection(console.getCharCount());
						}
						console.setSelection(console.getCharCount());
					}
				}
			}
		});
		tltmNewItem_4.setText("\u663E\u793A\u9009\u4E2D\u7684\u7ED3\u679C");
	
		SashForm sashForm_3 = new SashForm(composite_2, SWT.NONE);
		sashForm_3.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
	
		Composite composite_18 = new Composite(sashForm_3, SWT.NONE);
		composite_18.setLayout(new swing2swt.layout.BorderLayout(0, 0));

		algoShowTree = new Tree(composite_18, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		algoShowTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				{
					TreeItem item = (TreeItem) e.item;
					String algoName = item.getText(0);
					try {
						GlobalMainControl.getInstance().drawNodesResult(Arrays.asList(algoName), new AppDrawControlImpl());
					} catch (Exception e1) {
						if(console != null&& !console.isDisposed()){
							console.append("\n"+"算法显示结果异常："+e1+"\n");
							for(StackTraceElement error : e1.getStackTrace()){
								console.append(error.toString()+"\n");
								console.setSelection(console.getCharCount());
							}
							console.setSelection(console.getCharCount());
						}
					}
				}
			}
		});

		TreeColumn trclmnAlgoShow = new TreeColumn(algoShowTree, SWT.NONE);
		trclmnAlgoShow.setWidth(131);
		trclmnAlgoShow.setText("Algorithm");
		
		Composite composite_19 = new Composite(composite_18, SWT.BORDER | SWT.NO_FOCUS);
		composite_19.setLayoutData(swing2swt.layout.BorderLayout.NORTH);
		composite_19.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		Composite composite_21 = new Composite(composite_19, SWT.NONE);
		composite_21.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		composite_21.setLayout(new GridLayout(1, false));
		
		Label lblNewLabel_2 = new Label(composite_21, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		lblNewLabel_2.setAlignment(SWT.CENTER);
		lblNewLabel_2.setText("\u7B97\u6CD5\u5217\u8868");
		
		Composite composite_20 = new Composite(composite_19, SWT.NONE);
		composite_20.setLayoutData(swing2swt.layout.BorderLayout.EAST);
		composite_20.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		ToolBar toolBar_3 = new ToolBar(composite_20, SWT.FLAT | SWT.RIGHT);
		
		ToolItem tltmRefresh_1 = new ToolItem(toolBar_3, SWT.NONE);
		tltmRefresh_1.setText("\u5237\u65B0");
		tltmRefresh_1.setToolTipText("Refresh");
		tltmRefresh_1.setImage(SWTResourceManager.getImage("icon\\refresh.JPG"));
		tltmRefresh_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				algoShowTree.removeAll();
				Set<String> keySet = GlobalMainControl.getInstance().getResultNames();
				for(String key:keySet){
					TreeItem treeItem = new TreeItem(algoShowTree,SWT.NONE);
					treeItem.setText(0,key);
				}
			}
		});
	
		Composite composite_17 = new Composite(sashForm_3, SWT.NONE);
		composite_17.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_22 = new Composite(composite_17, SWT.EMBEDDED);
		composite_22.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Frame frame_1 = SWT_AWT.new_Frame(composite_22);
		frame_1.setBackground(Color.WHITE);
		
		JScrollPane drawResultScrollPanel = new JScrollPane();
		frame_1.add(drawResultScrollPanel, BorderLayout.CENTER);
		
		drawResultPanel = new JPanel();
		drawResultScrollPanel.setViewportView(drawResultPanel);
		drawResultPanel.setLayout(new javax.swing.BoxLayout(drawResultPanel, javax.swing.BoxLayout.Y_AXIS));
		sashForm_3.setWeights(new int[] {148, 680});
		
		CTabItem tbtmNewItem_5 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_5.setText("\u8FD0\u884C\u7ED3\u679C");
		
		Composite composite_23 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem_5.setControl(composite_23);
		composite_23.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		ToolBar toolBar_6 = new ToolBar(composite_23, SWT.FLAT | SWT.RIGHT);
		toolBar_6.setLayoutData(swing2swt.layout.BorderLayout.NORTH);
		
		ToolItem tltmNewItem_7 = new ToolItem(toolBar_6, SWT.NONE);
		tltmNewItem_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(algoResultTree.getSelection()!=null&&algoResultTree.getSelection().length!=0){
					TreeItem item = algoResultTree.getSelection()[0];
					if(item == null){
						appCompPanel.setVisible(false);
						appCompPanel.removeAll();
						appCompPanel.setVisible(true);
						return;
					}
					String name = item.getText(0);
					try {
						GlobalMainControl.getInstance().paintAlgoResult(name, new AppPaintResultControlImpl());
					} catch (Exception e) {
						if(console != null && !console.isDisposed()){
							console.append("\n"+"画单个算法的每代最大值出现异常:"+e+"\n");
							for(StackTraceElement error : e.getStackTrace()){
								console.append(error.toString()+"\n");
								console.setSelection(console.getCharCount());
							}
							console.setSelection(console.getCharCount());
						}
					}
				}
			}
		});
		tltmNewItem_7.setText("\u663E\u793A\u6BCF\u4EE3\u6700\u5927\u503C");
		
		ToolItem toolItem_2 = new ToolItem(toolBar_6, SWT.NONE);
		toolItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(algoResultTree.getSelection()!=null&&algoResultTree.getSelection().length!=0){
					TreeItem item = algoResultTree.getSelection()[0];
					if(item == null){
						appCompPanel.setVisible(false);
						appCompPanel.removeAll();
						appCompPanel.setVisible(true);
						return;
					}
					String name = item.getText(0);
					try {
						GlobalMainControl.getInstance().paintAlgoResultByRuntime(name, new AppPaintResultControlImpl());
					} catch (Exception e) {
						if(console != null && !console.isDisposed()){
							console.append("\n"+"按运行次数显示单个算法柱状图出现异常:"+e+"\n");
							for(StackTraceElement error : e.getStackTrace()){
								console.append(error.toString()+"\n");
								console.setSelection(console.getCharCount());
							}
							console.setSelection(console.getCharCount());
						}
					}
				}
			}
		});
		toolItem_2.setText("\u6309\u8FD0\u884C\u6B21\u6570\u663E\u793A\u7ED3\u679C");
		
		SashForm sashForm_5 = new SashForm(composite_23, SWT.NONE);
		sashForm_5.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		
		Composite composite_31 = new Composite(sashForm_5, SWT.NONE);
		composite_31.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		Composite composite_32 = new Composite(composite_31, SWT.NONE);
		composite_32.setLayoutData(swing2swt.layout.BorderLayout.NORTH);
		composite_32.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		Composite composite_33 = new Composite(composite_32, SWT.NONE);
		composite_33.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		composite_33.setLayout(new GridLayout(1, false));
		
		Label lblNewLabel_3 = new Label(composite_33, SWT.NONE);
		lblNewLabel_3.setText("\u7B97\u6CD5\u5217\u8868");
		
		Composite composite_34 = new Composite(composite_32, SWT.NONE);
		composite_34.setLayoutData(swing2swt.layout.BorderLayout.EAST);
		composite_34.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		ToolBar toolBar_7 = new ToolBar(composite_34, SWT.FLAT | SWT.RIGHT);
		
		ToolItem tltmNewItem_8 = new ToolItem(toolBar_7, SWT.NONE);
		tltmNewItem_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				algoResultTree.removeAll();
				Set<String> keySet = GlobalMainControl.getInstance().getResultNames();
				for(String key:keySet){
					TreeItem treeItem = new TreeItem(algoResultTree,SWT.NONE);
					treeItem.setText(0,key);
				}
			}
		});
		tltmNewItem_8.setImage(SWTResourceManager.getImage("icon\\refresh.JPG"));
		tltmNewItem_8.setToolTipText("Refresh");
		tltmNewItem_8.setText("\u5237\u65B0");
		
		algoResultTree = new Tree(composite_31, SWT.BORDER);
		algoResultTree.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		
		Composite composite_30 = new Composite(sashForm_5, SWT.NONE);
		composite_30.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_35 = new Composite(composite_30, SWT.EMBEDDED);
		
		Frame frame_3 = SWT_AWT.new_Frame(composite_35);
		frame_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		frame_3.add(scrollPane_1, BorderLayout.CENTER);
		
		appResultPanel = new JPanel();
		appResultPanel.setBackground(Color.WHITE);
		scrollPane_1.setViewportView(appResultPanel);
		appResultPanel.setLayout(new javax.swing.BoxLayout(appResultPanel, javax.swing.BoxLayout.Y_AXIS));
		sashForm_5.setWeights(new int[] {162, 666});
		
		//算法比较
		CTabItem tbtmNewItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setText("\u7B97\u6CD5\u6BD4\u8F83");
		Composite composite_4 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem_1.setControl(composite_4);
		composite_4.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		ToolBar toolBar_4 = new ToolBar(composite_4, SWT.FLAT | SWT.RIGHT);
		toolBar_4.setLayoutData(swing2swt.layout.BorderLayout.NORTH);
		
		ToolItem tltmNewItem_5 = new ToolItem(toolBar_4, SWT.NONE);
		tltmNewItem_5.setToolTipText("\u6BD4\u8F83\u5404\u4E2A\u7B97\u6CD5N\u6B21\u8FD0\u884C\u4E2D\u7684\u9002\u5E94\u5EA6\u503C\uFF0C\u8986\u76D6\u7387\uFF0C\u79FB\u52A8\u8DDD\u79BB\uFF0C\u4EE5\u53CA\u8FD0\u884C\u65F6\u95F4\u7684\u53D8\u5316\u60C5\u51B5\uFF0C\u7528\u7EBF\u6027\u56FE\u8868\u793A");
		tltmNewItem_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				List<String> names = new ArrayList<String>();
				for(TreeItem item:algoCompTree.getItems()){
					if(item.getChecked()){
						names.add(item.getText(0));
					}
				}
				if(names.isEmpty()){
					appCompPanel.setVisible(false);
					appCompPanel.removeAll();
					appCompPanel.setVisible(true);
					return;
				}
				try {
					GlobalMainControl.getInstance().compareAlgoByRunTime(names, new AppShowCompControlImpl());
				} catch (Exception e) {
					if(console != null && !console.isDisposed()){
						console.append("\n"+"根据算法运行次数比较算法出现异常:"+e+"\n");
						for(StackTraceElement error : e.getStackTrace()){
							console.append(error.toString()+"\n");
							console.setSelection(console.getCharCount());
						}
						console.setSelection(console.getCharCount());
					}
				}
			}
		});
		tltmNewItem_5.setText("\u6309\u8FD0\u884C\u6B21\u6570\u6BD4\u8F83\u5404\u4E2A\u53C2\u6570");
		
		ToolItem toolItem = new ToolItem(toolBar_4, SWT.NONE);
		toolItem.setToolTipText("\u6BD4\u8F83\u7B97\u6CD5\u7684\u9002\u5E94\u5EA6\uFF0C\u8986\u76D6\u7387\uFF0C\u79FB\u52A8\u8DDD\u79BB\u5728\u6BCF\u6B21\u8FED\u4EE3\u8FC7\u7A0B\u4E2D\u7684\u6700\u5927\u503C");
		toolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				List<String> names = new ArrayList<String>();
				for(TreeItem item:algoCompTree.getItems()){
					if(item.getChecked()){
						names.add(item.getText(0));
					}
				}
				if(names.isEmpty()){
					appCompPanel.setVisible(false);
					appCompPanel.removeAll();
					appCompPanel.setVisible(true);
					return;
				}
				try {
					GlobalMainControl.getInstance().compareAlgoByLoop(names, new AppShowCompControlImpl());
				} catch (Exception e) {
					if(console != null&& !console.isDisposed()){
						console.append("\n"+"根据迭代次数比较第一次算法运行的 fit cov dis迭代每代最大值变化曲线出现异常:"+e+"\n");
						for(StackTraceElement error : e.getStackTrace()){
							console.append(error.toString()+"\n");
							console.setSelection(console.getCharCount());
						}
						console.setSelection(console.getCharCount());
					}
				}
			}
		});
		toolItem.setText("\u6309\u6BCF\u6B21\u8FED\u4EE3\u6700\u4F18\u503C\u6BD4\u8F83");
		
		ToolItem toolItem_1 = new ToolItem(toolBar_4, SWT.NONE);
		toolItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				List<String> names = new ArrayList<String>();
				for(TreeItem item:algoCompTree.getItems()){
					if(item.getChecked()){
						names.add(item.getText(0));
					}
				}
				if(names.isEmpty()){
					appCompPanel.setVisible(false);
					appCompPanel.removeAll();
					appCompPanel.setVisible(true);
					return;
				}
				try {
					GlobalMainControl.getInstance().compareAlgoByLoopMax(names, new AppShowCompControlImpl());
				} catch (Exception e) {
					if(console != null && !console.isDisposed()){
						console.append("\n"+"根据迭代次数比较第一次算法运行的 fit cov dis收敛曲线出现异常:"+e+"\n");
						for(StackTraceElement error : e.getStackTrace()){
							console.append(error.toString()+"\n");
							console.setSelection(console.getCharCount());
						}
						console.setSelection(console.getCharCount());
					}
				}
			}
		});
		toolItem_1.setText("\u7B97\u6CD5\u6536\u655B\u6027\u6BD4\u8F83");
		
		ToolItem tltmn = new ToolItem(toolBar_4, SWT.NONE);
		tltmn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				List<String> names = new ArrayList<String>();
				for(TreeItem item:algoCompTree.getItems()){
					if(item.getChecked()){
						names.add(item.getText(0));
					}
				}
				if(names.isEmpty()){
					appCompPanel.setVisible(false);
					appCompPanel.removeAll();
					appCompPanel.setVisible(true);
					return;
				}
				try {
					GlobalMainControl.getInstance().compareAlgoByCategory(names, new AppShowCompControlImpl());
				} catch (Exception e) {
					if(console != null && !console.isDisposed()){
						console.append("\n"+"根据最优,最差,平均,均方差,比较算法:"+e+"\n");
						for(StackTraceElement error : e.getStackTrace()){
							console.append(error.toString()+"\n");
							console.setSelection(console.getCharCount());
						}
						console.setSelection(console.getCharCount());
					}
				}
			}
		});
		tltmn.setToolTipText("\u6BD4\u8F83\u5404\u4E2A\u7B97\u6CD5\u8FD0\u884CN\u6B21\u540E\u7684\u7ED3\u679C\u7684\u6700\u4F18\uFF0C\u6700\u5DEE\uFF0C\u5E73\u5747\uFF0C\u5747\u65B9\u5DEE\uFF0C\u7528\u67F1\u72B6\u56FE\u8868\u793A");
		tltmn.setText("\u6BD4\u8F83\u5404\u7B97\u6CD5N\u6B21\u8FD0\u884C\u7ED3\u679C");
		
		SashForm sashForm_4 = new SashForm(composite_4, SWT.NONE);
		sashForm_4.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		
		Composite composite_25 = new Composite(sashForm_4, SWT.NONE);
		composite_25.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		Composite composite_26 = new Composite(composite_25, SWT.NONE);
		composite_26.setLayoutData(swing2swt.layout.BorderLayout.NORTH);
		composite_26.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		Composite composite_27 = new Composite(composite_26, SWT.NONE);
		composite_27.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		composite_27.setLayout(new GridLayout(1, false));
		
		Label lblAlgoritnmlist = new Label(composite_27, SWT.NONE);
		lblAlgoritnmlist.setText("\u7B97\u6CD5\u5217\u8868");
		
		Composite composite_28 = new Composite(composite_26, SWT.NONE);
		composite_28.setLayoutData(swing2swt.layout.BorderLayout.EAST);
		composite_28.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		ToolBar toolBar_5 = new ToolBar(composite_28, SWT.FLAT | SWT.RIGHT);
		
		ToolItem tltmNewItem_6 = new ToolItem(toolBar_5, SWT.NONE);
		tltmNewItem_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				algoCompTree.removeAll();
				Set<String> keySet = GlobalMainControl.getInstance().getResultNames();
				for(String key:keySet){
					TreeItem treeItem = new TreeItem(algoCompTree,SWT.NONE);
					treeItem.setText(0,key);
				}
			}
		});
		tltmNewItem_6.setToolTipText("Refresh\r\n");
		tltmNewItem_6.setImage(SWTResourceManager.getImage("icon\\refresh.JPG"));
		tltmNewItem_6.setText("\u5237\u65B0");
		
		algoCompTree = new Tree(composite_25, SWT.BORDER | SWT.CHECK);
		algoCompTree.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		
		Composite composite_24 = new Composite(sashForm_4, SWT.NONE);
		composite_24.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_29 = new Composite(composite_24, SWT.EMBEDDED);
		composite_29.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Frame frame_2 = SWT_AWT.new_Frame(composite_29);
		
		JScrollPane scrollPane = new JScrollPane();
		frame_2.add(scrollPane, BorderLayout.CENTER);
		
		appCompPanel = new JPanel();
		scrollPane.setViewportView(appCompPanel);
		appCompPanel.setLayout(new javax.swing.BoxLayout(appCompPanel, javax.swing.BoxLayout.Y_AXIS));
		sashForm_4.setWeights(new int[] {166, 662});
		
		
		//输出
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new swing2swt.layout.BorderLayout(0, 0));
		
		CTabFolder tabFolder_1 = new CTabFolder(composite_1, SWT.BORDER);
		tabFolder_1.setUnselectedCloseVisible(false);
		tabFolder_1.setUnselectedImageVisible(false);
		tabFolder_1.setSimple(false);
		tabFolder_1.setLayoutData(swing2swt.layout.BorderLayout.CENTER);
		tabFolder_1.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmNewItem_2 = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmNewItem_2.setText("\u63A7\u5236\u53F0");
		
		console = new StyledText(tabFolder_1, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		console.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
		console.setEditable(false);
		tbtmNewItem_2.setControl(console);
		
		CTabItem tbtmNewItem_3 = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmNewItem_3.setText("\u7ED3\u679C\u8F93\u51FA");
		
		output = new StyledText(tabFolder_1, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		output.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
		output.setEditable(false);
		tbtmNewItem_3.setControl(output);
		
		//底部
		Composite composite_7 = new Composite(composite_1, SWT.NONE);
		composite_7.setLayoutData(swing2swt.layout.BorderLayout.SOUTH);
		composite_7.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_8 = new Composite(composite_7, SWT.NONE);
		composite_8.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_9 = new Composite(composite_7, SWT.NONE);
		composite_9.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		labelRunning = new Label(composite_9, SWT.NONE);
		labelRunning.setAlignment(SWT.RIGHT);
		labelRunning.setText("\u6B63\u5728\u8FD0\u884C\uFF1A");
		
		labelFinishPer = new Label(composite_7, SWT.NONE);
		labelFinishPer.setAlignment(SWT.RIGHT);
		labelFinishPer.setText("\u5DF2\u5B8C\u62100%");
		
		progressBar = new ProgressBar(composite_7, SWT.NONE);
		sashForm.setWeights(new int[] {333, 138});

	}
	
	private class APPControlImpl implements AppControl{

		@Override
		public void setEnable(boolean enable){
			toolBar.setEnabled(enable);
			toolBar_1.setEnabled(enable);
			menu.setEnabled(enable);
		}

		@Override
		public void initProgressBar(int max) {
			progressBar.setMinimum(0);
			progressBar.setMaximum(max);
			progressBar.setSelection(0);
			labelFinishPer.setText("已完成"+(100*progressBar.getSelection()/progressBar.getMaximum())+"%");
		}

		@Override
		public void addProgressBar() {
			progressBar.setSelection(progressBar.getSelection()+1);
			labelFinishPer.setText("已完成"+(100*progressBar.getSelection()/progressBar.getMaximum())+"%");
		}
		
		@Override
		public void initPrintPanel(){
			console.setText("");
			output.setText("");
		}

		@Override
		public void print(String target, String message) {
			if(AlgorithmConstants.PRINT_CONSOLE.equals(target)){
				console.append("\n"+message);
				console.setSelection(console.getCharCount());
			}else if(AlgorithmConstants.PRINT_OUTPUT.equals(target)){
				output.append("\n"+message);
				output.setSelection(console.getCharCount());
			}
		}

		@Override
		public void setLabelRunning(String name) {
			labelRunning.setText("正在运行:"+name);
		}
	}
	
	private class AppDrawControlImpl implements AppDrawResultControl{

		@Override
		public void drawResults(String name,List<Map<String, Object>> resultList) {
			JPanel panelForAlgo = new JPanel();
			panelForAlgo.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
			int i=0;
			for (Map<String, Object> algoResult : resultList) {
				double fit = (Double) algoResult.get(AlgorithmConstants.RESULT_BESTFIT);
				double cov = (Double) algoResult.get(AlgorithmConstants.RESULT_BESTCOV);
				double dis = (Double) algoResult.get(AlgorithmConstants.RESULT_BESTDIS);
				long time = (Long) algoResult.get(AlgorithmConstants.RESULT_RUNTIME);
				String textHeader = "第"+(++i)+"次; 运行时间: "+time+" ms";
				String textFooter =	"Fit: "+((double)((int)(fit*100))/100)+"; Cov: "+(int)(cov*100)+" %; Dis: "+(int)dis;
				PaintPanel panel = new PaintPanel();
				panel.setName(name);
				panel.setShowedText(textHeader,textFooter);
				panel.setToolTipText("name: " + textHeader);
				panel.draw((NodeList) algoResult.get(AlgorithmConstants.RESULT_NODELIST));
				panel.addMouseListener(new java.awt.event.MouseAdapter(){
					public void mouseClicked(java.awt.event.MouseEvent e){
						PaintPanel panel = (PaintPanel) e.getSource();
						drawSingleResult(panel);
					}
				});
				panelForAlgo.add(panel);
			}
			drawResultPanel.add(panelForAlgo);
		}

		@Override
		public void removeAll() {
			drawResultPanel.removeAll();
		}

		@Override
		public void setVisable(boolean visable) {
			drawResultPanel.setVisible(visable);
		}
		
		private void drawSingleResult(PaintPanel panel){
			drawResultPanel.setVisible(false);
			drawResultPanel.removeAll();
			panel.reDraw(1.2);
			panel.addMouseListener(new java.awt.event.MouseAdapter(){
				public void mouseClicked(java.awt.event.MouseEvent e){
					PaintPanel panel = (PaintPanel) e.getSource();
					String name = panel.getName();
					if(name!=null){
						try {
							GlobalMainControl.getInstance().drawNodesResult(Arrays.asList(name), new AppDrawControlImpl());
						} catch (Exception e1) {
							if(console != null&& !console.isDisposed()){
								console.append("\n"+e1+"\n");
								for(StackTraceElement error : e1.getStackTrace()){
									console.append(error.toString()+"\n");
									console.setSelection(console.getCharCount());
								}
								console.setSelection(console.getCharCount());
							}
						}
					}
				}
			});
			drawResultPanel.add(panel);
			drawResultPanel.setVisible(true);
		}
	}
	
	private class AppShowCompControlImpl implements AppCompareControl{

		@Override
		public void showCompLineCharts(String title,String xName,Map<String, Map<String, List<Double>>> results) {
			Set<String> keySet = results.keySet();
			for(String chartName : keySet){
				 Map<String, List<Double>> chartParam = results.get(chartName);
				 String yName =  CompareAlgorithmUtil.yNames.get(chartName);
				 JPanel panel = MultiLineCharts.createXYLine(chartName+title, xName, yName, chartParam);
				 JPanel panelForAlgo = new JPanel();
				 panelForAlgo.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
				 panelForAlgo.add(panel);
				 appCompPanel.add(panelForAlgo);
			}
		}

		@Override
		public void removeAll() {
			appCompPanel.removeAll();
		}

		@Override
		public void setVisable(boolean visable) {
			appCompPanel.setVisible(visable);
		}

		@Override
		public void showCompBarCharts(String title,String xName, Map<String, Map<String, Map<String, Double>>> results) {
			Set<String> keySet = results.keySet();
			for(String chartName : keySet){
				 Map<String, Map<String, Double>> chartParam = results.get(chartName);
				 String yName =  CompareAlgorithmUtil.yNames.get(chartName);
				 JPanel panel = MultiBarCharts.createCategoryBar(chartName+title, xName, yName, chartParam);
				 JPanel panelForAlgo = new JPanel();
				 panelForAlgo.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
				 panelForAlgo.add(panel);
				 appCompPanel.add(panelForAlgo);
			}
		}
		
	}
	
	private class AppPaintResultControlImpl implements AppPaintResultControl{

		@Override
		public void showLineCharts(String title,String xName,Map<String, Map<String, List<Double>>> results) {
			Set<String> keySet = results.keySet();
			for(String chartName : keySet){
				 Map<String, List<Double>> chartParam = results.get(chartName);
				 String yName =  CompareAlgorithmUtil.yNames.get(chartName);
				 JPanel panel = MultiLineCharts.createXYLine(title+chartName, xName, yName, chartParam,false);
				 JPanel panelForAlgo = new JPanel();
				 panelForAlgo.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
				 panelForAlgo.add(panel);
				 appResultPanel.add(panelForAlgo);
			}
		}

		@Override
		public void removeAll() {
			appResultPanel.removeAll();
		}

		@Override
		public void setVisable(boolean visable) {
			appResultPanel.setVisible(visable);
		}

		@Override
		public void showBarCharts(String title,String xName,Map<String,List<Double>> results) {
			Set<String> keySet = results.keySet();
			for(String chartName : keySet){
				 List<Double> chartParam = results.get(chartName);
				 String yName =  CompareAlgorithmUtil.yNames.get(chartName);
				 JPanel panel = BarCharts.createCategoryBar(title+chartName, xName, yName, chartParam);
				 JPanel panelForAlgo = new JPanel();
				 panelForAlgo.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
				 panelForAlgo.add(panel);
				 appResultPanel.add(panelForAlgo);
			}
		}
	}
}
