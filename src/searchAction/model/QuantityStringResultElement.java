package searchAction.model;

public final class QuantityStringResultElement extends AbstractSearchResultElement {

    private final String quantity;
    private final String value;
    private final String parentDirName;
    private final String tag;

    public QuantityStringResultElement(String quantity, String value, String parentDirName, String tag) {
        this.quantity = quantity;
        this.value = value;
        this.parentDirName = parentDirName;
        this.tag = tag;
    }

    @Override
    public String getName() {
        return quantity;
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
