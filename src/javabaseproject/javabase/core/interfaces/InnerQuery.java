package javabaseproject.javabase.core.interfaces;

import javabaseproject.javabase.core.database.querybuilders.query.DB;

public interface InnerQuery {
    DB<?> query();
}
