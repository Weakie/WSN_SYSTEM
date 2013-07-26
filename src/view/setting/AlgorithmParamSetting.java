package view.setting;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.InheritMethodCombo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import algorithm.ParamConvertAdapter;

public class AlgorithmParamSetting extends Composite {

	private Tree tree;
	private TreeEditor editor;
	private TreeItem[] treeItem;

	List<Object> types;
	List<String> keys;
	Map<String,Object> param;
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AlgorithmParamSetting(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	/**
	 * Create contents of the window
	 */
	private void createContents() {
		setLayout(new FillLayout(SWT.HORIZONTAL));

		{
			tree = new Tree(this, SWT.BORDER | SWT.FULL_SELECTION);
			tree.setSortDirection(SWT.UP);
			tree.setLinesVisible(true);
			tree.setHeaderVisible(true);
		}
		
		editor = new TreeEditor(tree);
		
		{
			TreeColumn newColumnTreeColumn = new TreeColumn(tree,
					SWT.CENTER);
			newColumnTreeColumn.setWidth(99);
			newColumnTreeColumn.setText("属性名称");

			TreeColumn newColumnTreeColumn_1 = new TreeColumn(tree,
					SWT.RIGHT);
			newColumnTreeColumn_1.setWidth(139);
			newColumnTreeColumn_1.setText("属性值");
		}
		editor.horizontalAlignment = SWT.RIGHT;
		editor.grabHorizontal = true;
	}
	
	public void showParam(ParamConvertAdapter adapter){
		tree.removeAll();
		this.setVisible(false);
		
		param = adapter.toMap();
		keys = adapter.getKeyByIndex();
		types = adapter.getType();
		this.treeItem = new TreeItem[param.size()];
		for (int i=0;i<keys.size();i++) {
			String key = keys.get(i);
			treeItem[i] = new TreeItem(tree, SWT.NONE);
			treeItem[i].setText(0, key);
			Object type = types.get(i);
			if(type!=null){
				String v = ((InheritMethodCombo)type).getItem((Integer)param.get(key));
				treeItem[i].setText(1, v);
			}else{
				treeItem[i].setText(1, param.get(key).toString());
			}
		}
		
		tree.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}

				// Identify the selected row
				TreeItem item = (TreeItem) e.item;

				// new editor control
				Control newEditor = null;
				int index = keys.indexOf(item.getText(0));
				if (types.get(index) != null) {
					//下拉框属性
					CCombo newCombo = ((InheritMethodCombo)types.get(index)).getCombo(tree);
					newCombo.select(((InheritMethodCombo)types.get(index)).getSelectIndex(item.getText(1)));
					newCombo.addSelectionListener(new SelectionAdapter(){
						@Override
						public void widgetSelected(SelectionEvent arg0) {
							CCombo combo = (CCombo) editor.getEditor();
							if(editor.getItem()!=null && !editor.getItem().isDisposed() && combo!=null){
								editor.getItem().setText(1, combo.getText());
							}
						}
						
					});
					newCombo.addFocusListener(new FocusAdapter(){
						@Override
						public void focusLost(FocusEvent arg0) {
							CCombo source = (CCombo) arg0.getSource();
							source.setVisible(false);
							source.dispose();
						}
					});
					newEditor = newCombo;
				} else {
					//普通属性
					Text newText = new Text(tree, SWT.BORDER | SWT.RIGHT);
					newText.setText(item.getText(1));
					newText.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent me) {
							Text text = (Text) editor.getEditor();
							if(editor.getItem()!=null && !editor.getItem().isDisposed() && text!=null){
								editor.getItem().setText(1, text.getText());
							}
						}
					});
					newText.addFocusListener(new FocusAdapter(){
						@Override
						public void focusLost(FocusEvent arg0) {
							Text source = (Text) arg0.getSource();
							source.setVisible(false);
						}
					});
					newText.selectAll();
					newText.setFocus();
					newEditor = newText;
				}
				// add new editor for TreeEditor
				editor.setEditor(newEditor, item, 1);
			}
		});
		this.setVisible(true);
	}
	
	public Map<String,Object> saveParam(){
		Map<String,Object> param = new HashMap<String,Object>();
		if(treeItem == null){
			return Collections.emptyMap();
		}
		for(int i=0;i<treeItem.length;i++){
			String key = treeItem[i].getText(0);
			String value = treeItem[i].getText(1);
			if (types.get(keys.indexOf(key)) != null) {
				param.put(key, ((InheritMethodCombo)types.get(keys.indexOf(key))).getSelectIndex(value)+"");
			}
			else{
				param.put(key, value);
			}
		}
		return param;
	}

	@Override
	protected void checkSubclass() {
	}

}
