package view.setting;

import global.GlobalMainControl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import constants.AlgorithmConstants;

import swing2swt.layout.BorderLayout;

public class AddNewAlgorithm extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Combo combo_1;
	private Combo combo;
	private Combo combo_2;
	private CLabel label;
	private CLabel label_2;
	private CLabel label_3;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddNewAlgorithm(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		shell.setSize(370, 287);
		shell.setText("\u6DFB\u52A0\u7B97\u6CD5");
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new FormLayout());
		
		CLabel lblName = new CLabel(composite, SWT.NONE);
		FormData fd_lblName = new FormData();
		fd_lblName.top = new FormAttachment(0, 25);
		fd_lblName.left = new FormAttachment(0, 24);
		lblName.setLayoutData(fd_lblName);
		lblName.setText("\u7B97\u6CD5\u5B9E\u4F8B\u540D\u79F0:");
		
		text = new Text(composite, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.bottom = new FormAttachment(lblName, 0, SWT.BOTTOM);
		fd_text.left = new FormAttachment(lblName, 46);
		text.setLayoutData(fd_text);
		
		CLabel lblAlogrithmType = new CLabel(composite, SWT.NONE);
		FormData fd_lblAlogrithmType = new FormData();
		fd_lblAlogrithmType.top = new FormAttachment(lblName, 17);
		fd_lblAlogrithmType.left = new FormAttachment(lblName, 0, SWT.LEFT);
		lblAlogrithmType.setLayoutData(fd_lblAlogrithmType);
		lblAlogrithmType.setText("\u7B97\u6CD5\u5B9E\u4F8B\u7C7B\u578B:");
		
		combo = new Combo(composite, SWT.READ_ONLY);
		FormData fd_combo = new FormData();
		fd_combo.bottom = new FormAttachment(lblAlogrithmType, 0, SWT.BOTTOM);
		fd_combo.left = new FormAttachment(text, 0, SWT.LEFT);
		fd_combo.right = new FormAttachment(100, -25);
		combo.setLayoutData(fd_combo);
		for(String name : GlobalMainControl.algorithms.keySet()){
			combo.add(name);
		}
		
		CLabel lblRunTimes = new CLabel(composite, SWT.NONE);
		FormData fd_lblRunTimes = new FormData();
		fd_lblRunTimes.top = new FormAttachment(lblAlogrithmType, 26);
		fd_lblRunTimes.left = new FormAttachment(lblName, 0, SWT.LEFT);
		lblRunTimes.setLayoutData(fd_lblRunTimes);
		lblRunTimes.setText("\u7B97\u6CD5\u8FED\u4EE3\u6B21\u6570:");
		
		combo_1 = new Combo(composite, SWT.NONE);
		combo_1.setItems(new String[] {"10", "20", "30", "50", "70", "80", "100", "150", "200", "300", "500", "1000"});
		FormData fd_combo_1 = new FormData();
		fd_combo_1.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_combo_1.bottom = new FormAttachment(lblRunTimes, 0, SWT.BOTTOM);
		fd_combo_1.left = new FormAttachment(text, 0, SWT.LEFT);
		combo_1.setLayoutData(fd_combo_1);
		
		CLabel lblScale = new CLabel(composite, SWT.NONE);
		FormData fd_lblScale = new FormData();
		fd_lblScale.top = new FormAttachment(lblRunTimes, 27);
		fd_lblScale.left = new FormAttachment(lblName, 0, SWT.LEFT);
		lblScale.setLayoutData(fd_lblScale);
		lblScale.setText("\u7B97\u6CD5\u8FD0\u884C\u89C4\u6A21:");
		
		combo_2 = new Combo(composite, SWT.NONE);
		combo_2.setItems(new String[] {"10", "20", "30", "50", "70", "80", "100", "150", "200", "300", "500", "1000"});
		FormData fd_combo_2 = new FormData();
		fd_combo_2.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_combo_2.bottom = new FormAttachment(lblScale, 0, SWT.BOTTOM);
		fd_combo_2.left = new FormAttachment(text, 0, SWT.LEFT);
		combo_2.setLayoutData(fd_combo_2);
		
		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				label.setText("");
				label_2.setText("");
				label_3.setText("");
				Map<String,Object> param = new HashMap<String,Object>();
				String name = text.getText();
				String type = combo.getText();
				String runTimeStr = combo_1.getText();
				String scaleStr = combo_2.getText();
				try{
					String regex = "^[a-zA-Z_]+[a-zA-Z0-9_]*";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(name);
					if(!m.matches()){
						throw new Exception();
					}
					param.put(AlgorithmConstants.ALGORITHM_NAME, name);
				}catch(Exception e){
					label.setText("*");
					return;
				}
				try{
					int runTime = Integer.parseInt(runTimeStr);
					param.put(AlgorithmConstants.ALGORITHM_LOOPNUM, runTime);
				}catch(Exception e){
					label_2.setText("*");
					return;
				}
				try{
					int scale = Integer.parseInt(scaleStr);
					param.put(AlgorithmConstants.ALGORITHM_SCALE, scale);
				}catch(Exception e){
					label_3.setText("*");
					return;
				}
				if(type == null){
					return;
				}
				param.put(AlgorithmConstants.ALGORITHM_TYPE, type);
				
				result = param;
				shell.dispose();
			}
		});
		FormData fd_btnAdd = new FormData();
		fd_btnAdd.left = new FormAttachment(0, 233);
		btnAdd.setLayoutData(fd_btnAdd);
		btnAdd.setText("\u6DFB\u52A0");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		fd_btnAdd.top = new FormAttachment(btnCancel, 0, SWT.TOP);
		fd_btnAdd.right = new FormAttachment(btnCancel, -6);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				result = null;
				shell.dispose();
			}
		});
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.bottom = new FormAttachment(100, -22);
		fd_btnCancel.left = new FormAttachment(0, 289);
		fd_btnCancel.right = new FormAttachment(100, -25);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("\u53D6\u6D88");
		
		label = new CLabel(composite, SWT.NONE);
		fd_text.right = new FormAttachment(label, -33);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 372);
		fd_label.right = new FormAttachment(100, 8);
		fd_label.bottom = new FormAttachment(lblName, 0, SWT.BOTTOM);
		label.setLayoutData(fd_label);
		label.setText("");
		
		label_2 = new CLabel(composite, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_2.setText("");
		FormData fd_label_2 = new FormData();
		fd_label_2.right = new FormAttachment(label, 0, SWT.RIGHT);
		fd_label_2.top = new FormAttachment(lblRunTimes, 0, SWT.TOP);
		fd_label_2.left = new FormAttachment(0, 372);
		label_2.setLayoutData(fd_label_2);
		
		label_3 = new CLabel(composite, SWT.NONE);
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_3.setText("");
		FormData fd_label_3 = new FormData();
		fd_label_3.right = new FormAttachment(label, 0, SWT.RIGHT);
		fd_label_3.top = new FormAttachment(lblScale, 0, SWT.TOP);
		fd_label_3.left = new FormAttachment(0, 372);
		label_3.setLayoutData(fd_label_3);

	}
}
