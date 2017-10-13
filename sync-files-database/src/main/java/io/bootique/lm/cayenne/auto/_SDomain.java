package io.bootique.lm.cayenne.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import io.bootique.lm.cayenne.SArticle;

/**
 * Class _SDomain was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _SDomain extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<String> DOMAIN_HOST = Property.create("domainHost", String.class);
    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<List<SArticle>> S_ARTICLES = Property.create("sArticles", List.class);

    public void setDomainHost(String domainHost) {
        writeProperty("domainHost", domainHost);
    }
    public String getDomainHost() {
        return (String)readProperty("domainHost");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void addToSArticles(SArticle obj) {
        addToManyTarget("sArticles", obj, true);
    }
    public void removeFromSArticles(SArticle obj) {
        removeToManyTarget("sArticles", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<SArticle> getSArticles() {
        return (List<SArticle>)readProperty("sArticles");
    }


}