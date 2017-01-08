package searchAction;

import com.intellij.ide.util.gotoByName.FilteringGotoByModel;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import searchAction.model.SearchResultElement;

import javax.swing.*;

/**
 * Created by hoainguyen on 12/31/16.
 */
public class SearchFilterModel extends FilteringGotoByModel<SearchResultElement> {

    protected SearchFilterModel(@NotNull Project project, @NotNull ChooseByNameContributor[] contributors) {
        super(project, contributors);
    }

    public SearchFilterModel(@NotNull Project project) {
        super(project, Extensions.getExtensions(ChooseByNameContributor.SYMBOL_EP_NAME));
    }

    @Nullable
    @Override
    protected SearchResultElement filterValueFor(NavigationItem item) {
        return item instanceof SearchResultElement ? (SearchResultElement) item : null;
    }

    @Override
    public String getPromptText() {
        return "Enter resource value";
    }

    @Override
    public String getNotInMessage() {
        return "No matches found!";
    }

    @Override
    public String getNotFoundMessage() {
        return "Not found!";
    }

    @Nullable
    @Override
    public String getCheckBoxName() {
        return null;
    }

    @Override
    public char getCheckBoxMnemonic() {
        return SystemInfo.isMac ? 'P' : 'n';
    }

    @Override
    public boolean loadInitialCheckBoxState() {
        return false;
    }

    @Override
    public void saveInitialCheckBoxState(boolean state) {

    }

    @NotNull
    @Override
    public String[] getSeparators() {
        return new String[]{"/", "\\"};
    }

    @Nullable
    @Override
    public String getFullName(Object element) {
        if (element instanceof SearchResultElement) {
            return ((SearchResultElement) element).getValue();
        }

        return getElementName(element);
    }

    @Override
    public ListCellRenderer getListCellRenderer() {
        return new ResultElementListCellRenderer();
    }

    @Override
    public boolean willOpenEditor() {
        return true;
    }
}
