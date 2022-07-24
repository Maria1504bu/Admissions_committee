package util;

import java.sql.ResultSet;

/**
 * Classes, which implements this interface map specific entity.
 * Implementations don`t move cursor via next() method,
 * but only extract information from the row in current cursor position.
 */
public interface EntityMapper<T> {
    /**
     * map db entity into application entity
     * @param rs from db query
     * @return mapped entity
     */
    public T mapEntity(ResultSet rs);
}
