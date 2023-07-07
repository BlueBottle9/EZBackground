package io.github.fsaxz.ezbackground;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class Preference extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public Preference() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Select your background image file here");
	}
	
	@Override
	public void init(IWorkbench arg0) {
		
	}

	@Override
	protected void createFieldEditors() {
		addField(new FileFieldEditor("ImageFile", "Path to your image file for background:", true, 0, getFieldEditorParent()));
	}

}
