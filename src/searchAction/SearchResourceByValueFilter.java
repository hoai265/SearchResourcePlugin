package searchAction;

import com.intellij.ide.util.gotoByName.ChooseByNameFilter;
import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.ide.util.gotoByName.FilteringGotoByModel;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import searchAction.model.SearchResultElement;

import javax.swing.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hoainguyen on 1/2/17.
 */
public class SearchResourceByValueFilter extends ChooseByNameFilter<SearchResultElement> {
    /**
     * A constructor
     *
     * @param popup               a parent popup
     * @param model               a model for popup
     * @param filterConfiguration storage for selected filter values
     * @param project             a context project
     */
    public SearchResourceByValueFilter(@NotNull ChooseByNamePopup popup, @NotNull FilteringGotoByModel<SearchResultElement> model, @NotNull ChooseByNameFilterConfiguration<SearchResultElement> filterConfiguration, @NotNull Project project) {
        super(popup, model, filterConfiguration, project);
    }

    @Override
    protected String textForFilterValue(@NotNull SearchResultElement value) {
        return value.getTag();
    }

    @Nullable
    @Override
    protected Icon iconForFilterValue(@NotNull SearchResultElement value) {
        return null;
    }

    @NotNull
    @Override
    protected Collection<SearchResultElement> getAllFilterValues() {
        //TODO apply filter
        List<SearchResultElement> result = new LinkedList<>();
        return  result;
    }
}
