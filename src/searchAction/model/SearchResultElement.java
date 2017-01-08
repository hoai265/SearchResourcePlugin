package searchAction.model;

import com.intellij.navigation.NavigationItem;

public interface SearchResultElement extends NavigationItem {

    String getName();

    String getTag();

    String getValue();

    String getParentDirName();

}
