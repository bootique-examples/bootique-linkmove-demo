package io.bootique.cayenne.lm.auto;

import io.bootique.cayenne.lm.TDomain;
import io.bootique.cayenne.lm.TTag;
import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class _TArticle was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _TArticle extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<String> BODY = Property.create("body", String.class);
    public static final Property<LocalDateTime> PUBLISHED_ON = Property.create("publishedOn", LocalDateTime.class);
    public static final Property<Double> QUOTE_INDEX = Property.create("quoteIndex", Double.class);
    public static final Property<String> TITLE = Property.create("title", String.class);
    public static final Property<TDomain> DOMAIN = Property.create("domain", TDomain.class);
    public static final Property<List<TTag>> T_TAGS = Property.create("tTags", List.class);

    public void setBody(String body) {
        writeProperty("body", body);
    }
    public String getBody() {
        return (String)readProperty("body");
    }

    public void setPublishedOn(LocalDateTime publishedOn) {
        writeProperty("publishedOn", publishedOn);
    }
    public LocalDateTime getPublishedOn() {
        return (LocalDateTime)readProperty("publishedOn");
    }

    public void setQuoteIndex(double quoteIndex) {
        writeProperty("quoteIndex", quoteIndex);
    }
    public double getQuoteIndex() {
        Object value = readProperty("quoteIndex");
        return (value != null) ? (Double) value : 0;
    }

    public void setTitle(String title) {
        writeProperty("title", title);
    }
    public String getTitle() {
        return (String)readProperty("title");
    }

    public void setDomain(TDomain domain) {
        setToOneTarget("domain", domain, true);
    }

    public TDomain getDomain() {
        return (TDomain)readProperty("domain");
    }


    public void addToTTags(TTag obj) {
        addToManyTarget("tTags", obj, true);
    }
    public void removeFromTTags(TTag obj) {
        removeToManyTarget("tTags", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<TTag> getTTags() {
        return (List<TTag>)readProperty("tTags");
    }


}
