package org.openlegacy.ide.eclipse.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.openlegacy.designtime.DesigntimeException;
import org.openlegacy.ide.eclipse.components.SnapshotComposite;
import org.openlegacy.ide.eclipse.util.PathsUtil;
import org.openlegacy.terminal.TerminalSnapshot;
import org.openlegacy.terminal.TerminalSnapshot.SnapshotType;
import org.openlegacy.terminal.modules.trail.TerminalPersistedTrail;
import org.openlegacy.utils.XmlSerializationUtil;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@SuppressWarnings("restriction")
public class TrailEditor extends MultiPageEditorPart implements IResourceChangeListener {

	private StructuredTextEditor editor;

	private SnapshotComposite snapshotComposite;

	private TableViewer tableViewer;

	private Composite trailComposite;

	public TrailEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	private void createXmlPage() {
		try {
			editor = new StructuredTextEditor();
			int index = addPage(editor, getEditorInput());
			setPageText(index, "Source");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error creating nested text editor", null, e.getStatus());
		}
	}

	void createTrailPage() {

		trailComposite = new Composite(getContainer(), SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		GridData gd = new GridData();
		trailComposite.setLayoutData(gd);
		trailComposite.setLayout(gridLayout);

		TerminalPersistedTrail terminalSessionTrail = loadTrail();

		if (terminalSessionTrail == null) {
			return;
		}

		createTable(trailComposite, terminalSessionTrail);

		snapshotComposite = new SnapshotComposite(trailComposite, terminalSessionTrail.getSnapshots().get(0));

		if (getPageCount() == 2) {
			removePage(0);
		}
		addPage(0, trailComposite);
		setPageText(0, "Snapshots");
	}

	private TerminalPersistedTrail loadTrail() {
		FileEditorInput input = (FileEditorInput)getEditorInput();

		File file = PathsUtil.toOsLocation(input.getFile());
		if (file.length() == 0) {
			return null;
		}

		TerminalPersistedTrail terminalSessionTrail = null;
		try {
			terminalSessionTrail = XmlSerializationUtil.deserialize(TerminalPersistedTrail.class, new FileInputStream(file));
		} catch (Exception e) {
			throw (new DesigntimeException("Unable to open trail file", e));
		}
		if (terminalSessionTrail.getSnapshots().size() == 0) {
			throw (new DesigntimeException("No spanshots found"));
		}
		return terminalSessionTrail;
	}

	private void createTable(final Composite composite, final TerminalPersistedTrail terminalSessionTrail) {
		tableViewer = new TableViewer(composite);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(terminalSessionTrail.getSnapshots().toArray());

		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		tableViewer.getTable().setLayoutData(data);
		tableViewer.getTable().addKeyListener(new KeyListener() {

			public void keyReleased(KeyEvent event) {}

			public void keyPressed(KeyEvent event) {
				// delete
				if (event.keyCode == SWT.DEL) {
					Table table = (Table)event.getSource();
					int selectionIndex = table.getSelectionIndex();
					terminalSessionTrail.getSnapshots().remove(selectionIndex);
					table.remove(selectionIndex);
					try {
						Element root = extractDocumentRoot();
						List<Element> snapshots = DomUtils.getChildElementsByTagName(root, "snapshot");
						root.removeChild(snapshots.get(selectionIndex));
					} catch (Exception e) {
						throw (new DesigntimeException(e));
					}
				}
			}

			@SuppressWarnings("restriction")
			private Element extractDocumentRoot() {
				IDocument document = editor.getDocumentProvider().getDocument(getEditorInput());
				Object model = StructuredModelManager.getModelManager().getExistingModelForRead(document);
				Element root = ((IDOMModel)model).getDocument().getDocumentElement();
				return root;
			}
		});
		TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE);
		column.getColumn().setWidth(100);
		column.getColumn().setText("Sequence:");
		column.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				TerminalSnapshot snapshot = (TerminalSnapshot)element;
				String direction = snapshot.getSnapshotType() == SnapshotType.INCOMING ? " (IN)" : " (OUT)";
				return String.valueOf("Screen " + snapshot.getSequence() + direction);
			}

		});
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				Object firstElement = ((StructuredSelection)selection).getFirstElement();
				snapshotComposite.setSnapshot((TerminalSnapshot)firstElement);

			}
		});
		refreshSnapshots();
	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void createPages() {
		FileEditorInput fileInput = (FileEditorInput)getEditorInput();
		setTitle(fileInput.getName());
		createTrailPage();
		createXmlPage();
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	/**
	 * Saves the multi-page editor's document.
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {

		getEditor(getPageCount() - 1).doSave(monitor);
		createTrailPage();
	}

	private void refreshSnapshots() {
		tableViewer.refresh();
		tableViewer.getTable().select(0);
	}

	/**
	 * Saves the multi-page editor's document as another file. Also updates the text for page 0's tab, and updates this multi-page
	 * editor's input to correspond to the nested editor's.
	 */
	@Override
	public void doSaveAs() {
		IEditorPart editor = getEditor(1);
		editor.doSaveAs();
		setPageText(1, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method checks that the input is an instance of
	 * <code>IFileEditorInput</code>.
	 */
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput)) {
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		}
		super.init(site, editorInput);
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart.
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (IWorkbenchPage page : pages) {
						if (((FileEditorInput)editor.getEditorInput()).getFile().getProject().equals(event.getResource())) {
							IEditorPart editorPart = page.findEditor(editor.getEditorInput());
							page.closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

}
