package searchAction;

import com.intellij.ide.util.gotoByName.ChooseByNameBase;
import com.intellij.ide.util.gotoByName.DefaultChooseByNameItemProvider;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.JDOMUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import searchAction.model.PluralStringResultElement;
import searchAction.model.QuantityStringResultElement;
import searchAction.model.ResourceResultElement;
import searchAction.model.SearchResultElement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hoainguyen on 12/31/16.
 */
public class SearchResultItemProvider extends DefaultChooseByNameItemProvider {
    private static final CharSequence XML = ".xml";
    private static final String TAG_RESOURCES = "resources";
    private static final String TAG_PLURALS = "plurals";
    private static final String TAG_ITEM = "item";
    private static final String TAG_QUANTITY = "quantity";
    private static final String TAG_NAME = "name";
    private static final String VALUE_FOLDER = "app/src/main/res/value";
    private final Project project;

    public SearchResultItemProvider(@NotNull Project project, @Nullable PsiElement context) {
        super(context);
        this.project = project;
    }

    @Override
    public boolean filterElements(@NotNull ChooseByNameBase base, @NotNull String pattern, boolean everywhere, @NotNull ProgressIndicator indicator, @NotNull Processor<Object> consumer) {
        Collection<SearchResultElement> elements = getAllFilterItems();

        if (elements != null) {
            for (SearchResultElement element : elements) {
                String value = element.getValue();

                if (value == null) {
                    return false;
                }

                if (value.toLowerCase().contains(pattern.toLowerCase()) && !consumer.process(element)) {
                    return false;
                }
            }
        }

        return false;
    }

    private Collection<SearchResultElement> getAllFilterItems() {

        List<SearchResultElement> result = new LinkedList<>();
        List<PsiFile> fileList = new ArrayList<PsiFile>();
        String[] filenames = FilenameIndex.getAllFilenames(project);
        for (String filename : filenames) {
            if (filename.contains(XML)) {
                PsiFile[] files = FilenameIndex.getFilesByName(project, filename, GlobalSearchScope.projectScope(project));
                for (PsiFile file : files) {
                    if (file.getVirtualFile().getPath().contains(VALUE_FOLDER)) {
                        fileList.add(file);
                    }

                }
            }
        }

        for (PsiFile psiFile : fileList) {
            if (psiFile != null) {
                PsiDirectory dir = psiFile.getParent();
                String parentDirName = dir != null ? dir.getName() : "";

                try {
                    InputStream is = new ByteArrayInputStream(psiFile.getText().getBytes());

                    Document doc = JDOMUtil.loadDocument(is);
                    Element root = doc.getRootElement();
                    Element resources = root.getChild(TAG_RESOURCES);
                    if (resources != null) root = resources;

                    if (root.getName().equals(TAG_RESOURCES)) {
                        List<Element> elements = root.getChildren();
                        for (Element element : elements) {
                            if (TAG_PLURALS.equals(element.getName())) {
                                List<QuantityStringResultElement> quantities = new LinkedList<>();
                                List<Element> items = element.getChildren(TAG_ITEM);
                                for (Element item : items) {
                                    String key = item.getAttributeValue(TAG_QUANTITY);
                                    String value = item.getText();
                                    quantities.add(new QuantityStringResultElement(key, value, parentDirName, item.getName()));
                                }
                                result.add(new PluralStringResultElement(element.getAttributeValue(TAG_NAME), quantities, parentDirName, element.getName()));
                            } else {
                                String key = element.getAttributeValue(TAG_NAME);
                                String value = element.getText();
                                result.add(new ResourceResultElement(key, value, parentDirName, element.getName()));
                            }
                        }
                    }
                } catch (JDOMException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return result;
    }
}
