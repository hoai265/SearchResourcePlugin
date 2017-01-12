package searchAction;

import com.intellij.featureStatistics.FeatureUsageTracker;
import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNameFilter;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.UndoConfirmationPolicy;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import searchAction.model.SearchResultElement;

import static com.intellij.openapi.application.ApplicationManager.getApplication;

/**
 * Created by hoainguyen on 1/2/17.
 */
public class SearchResourcesAction extends GotoActionBase implements DumbAware {

    boolean isXmlFile = false;

    @Override
    protected void gotoActionPerformed(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        if (project == null) return;

        FeatureUsageTracker.getInstance().triggerFeatureUsed("navigation.popup.file");
        isXmlFile = isXmlFile(CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext()));

        final SearchFilterModel searchFilterModel = new SearchFilterModel(project);
        GotoActionCallback<SearchResultElement> callback = new GotoActionCallback<SearchResultElement>() {
            @Override
            protected ChooseByNameFilter<SearchResultElement> createFilter(@NotNull ChooseByNamePopup popup) {
                return new SearchResourceByValueFilter(popup, searchFilterModel, SearchConfiguration.getInstance(project), project);
            }

            @Override
            public void elementChosen(ChooseByNamePopup popup, Object element) {
                if (element != null && element instanceof SearchResultElement) {
                    insertToEditor(project, (SearchResultElement) element);
                }
            }
        };

        SearchResultItemProvider itemProvider = new SearchResultItemProvider(project, getPsiContext(e));
        showNavigationPopup(e, searchFilterModel, callback, "resource matching pattern", true, true, itemProvider);
    }

    public void insertToEditor(Project project, SearchResultElement element) {
        CommandProcessor.getInstance().executeCommand(project, () -> getApplication().runWriteAction(() -> {
            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            if (editor != null) {
                int offset = editor.getCaretModel().getOffset();
                Document document = editor.getDocument();
                String key = isXmlFile ?
                        "@" + element.getTag() + "/" + element.getName()
                        : "R." + element.getTag() + "." + element.getName();
                if (key != null) {
                    document.insertString(offset, key);
                    editor.getCaretModel().moveToOffset(offset + key.length());
                }
            }
        }), "InsertResultToEditor", "", UndoConfirmationPolicy.DO_NOT_REQUEST_CONFIRMATION);
    }

    private static boolean isXmlFile(@Nullable VirtualFile file) {
        return file != null && file.getName().endsWith(".xml");
    }
}
