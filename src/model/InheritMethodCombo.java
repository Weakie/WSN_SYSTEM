package model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

public abstract class InheritMethodCombo {

	public CCombo getCombo(Composite c) {
		CCombo combo = new CCombo(c, SWT.READ_ONLY | SWT.RIGHT);
		FormData fd_combo = new FormData();
		combo.setLayoutData(fd_combo);
		for(String item : this.getItems()){
			combo.add(item);
		}
		return combo;
	}

	public int getSelectIndex(String item){
		for(int i=0;i < this.getItems().length;i++){
			if(this.getItems()[i].equals(item)){
				return i;
			}
		}
		return -1;
	}
	public String getItem(int i) {
		if (i < getItems().length) {
			return getItems()[i];
		} else
			return "";
	}
	protected abstract String[] getItems();
}
