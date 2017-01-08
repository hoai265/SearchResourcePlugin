package searchAction;

import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import searchAction.model.SearchResultElement;

/**
 * Created by hoainguyen on 12/31/16.
 */
@State(
        name = "SearchConfiguration",
        storages = {@Storage(file = StoragePathMacros.WORKSPACE_FILE)}
)
public class SearchConfiguration extends ChooseByNameFilterConfiguration<SearchResultElement>{
    public static SearchConfiguration getInstance(Project project) {
        return ServiceManager.getService(project, SearchConfiguration.class);
    }

    @Override
    protected String nameForElement(SearchResultElement type) {
        return type.getName();
    }
}
