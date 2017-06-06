package io.bootique.lm.cayenne;


import io.bootique.lm.cayenne.auto._Article;

import java.util.Date;

public class Article extends _Article {

    private static final long serialVersionUID = 1L; 

    @Override
    protected void onPostAdd() {
		setPublishedOn(new Date());
    }

}
