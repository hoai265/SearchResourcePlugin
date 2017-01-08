package searchAction.model;

public final class ResourceResultElement extends AbstractSearchResultElement {

    private final String name;
    private final String value;
    private final String parentDirName;
    private final String tag;

    public ResourceResultElement(String name, String value, String parentDirName, String tag) {
        this.value = value;
        this.name = name;
        this.parentDirName = parentDirName;
        this.tag = tag;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getParentDirName() {
        return parentDirName;
    }

}
