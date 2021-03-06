package io.bootique.lm.cayenne.auto;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import io.bootique.lm.cayenne.SArticle;

/**
 * Class _STag was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _STag extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<String> COLOR = Property.create("color", String.class);
    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<SArticle> ARTICLE = Property.create("article", SArticle.class);

    public void setColor(String color) {
        writeProperty("color", color);
    }
    public String getColor() {
        return (String)readProperty("color");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void setArticle(SArticle article) {
        setToOneTarget("article", article, true);
    }

    public SArticle getArticle() {
        return (SArticle)readProperty("article");
    }


}
