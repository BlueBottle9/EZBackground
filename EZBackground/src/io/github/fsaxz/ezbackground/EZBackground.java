package io.github.fsaxz.ezbackground;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class EZBackground implements IPropertyChangeListener , IStartup{
	private static ArrayList<String> Supported = new ArrayList<>();
	
		static {
			Supported.add(".png");
			Supported.add(".jpeg");
		}
	
			public static void setBackground(String path) {
				IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
				if (editorPart instanceof ITextEditor) {
				    ITextEditor textEditor = (ITextEditor) editorPart;
				    StyledText codeArea = (StyledText) textEditor.getAdapter(Control.class);
				    
				    Image image = new Image(Display.getCurrent(), path);
				    codeArea.setBackgroundImage(image);
				}
			}
			
			public static void applyBackground() {
					String path = Activator.getDefault().getPreferenceStore().getString("ImageFile");
					
					if (path == null || "".equals(path)) {
						return;
					}
					
					boolean flag = false;
					for (String s:Supported) {
						if (path.endsWith(s)) {
							flag = true;
						}
					}
					
					if (!flag) {
						return;
					}
				
					if(!new File(path).exists()) {
						return;
					}
					
					setBackground(path);
			}

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				applyBackground();
			}

			@Override
			public void earlyStartup() {
				IWorkbench workbench = PlatformUI.getWorkbench();
		        workbench.getDisplay().asyncExec(new Runnable() {
		            @Override
		            public void run() {
		                IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		                if (window != null) {
		                    IWorkbenchPage activePage = window.getActivePage();
		                    if (activePage != null) {
		                        activePage.addPartListener(new IPartListener2() {
		                            @Override
		                            public void partActivated(IWorkbenchPartReference partRef) {
		                                applyBackground();
		                            }
		                        });
		                    }
		                }
		            }
		        });
			}
}
