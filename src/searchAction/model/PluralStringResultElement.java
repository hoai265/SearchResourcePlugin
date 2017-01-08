package searchAction.model;

import java.util.LinkedList;
import java.util.List;

public final class PluralStringResultElement extends AbstractSearchResultElement {

    private final String tag;
    private String name;
    private final LinkedList<QuantityStringResultElement> quantities = new LinkedList<QuantityStringResultElement>();
    private final String parentDirName;

    public PluralStringResultElement(String name, List<QuantityStringResultElement> quantities, String parentDirName, String tag) {
        this.name = name;
        this.parentDirName = parentDirName;
        this.quantities.addAll(quantities);
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String getValue() {
        if (quantities.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < quantities.size(); i++) {
            QuantityStringResultElement quantity = quantities.get(i);
            builder.append(quantity.getValue());
            if (i < quantities.size() - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    @Override
    public String getParentDirName() {
        return parentDirName;
    }

}
