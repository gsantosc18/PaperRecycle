package com.e.engapp.model;

import java.util.List;

public interface DatabaseInterface<T> {
    /**
     * @return
     */
    List<T> list();

    /**
     * @param object
     * @return
     */
    T get(Object object);

    /**
     * @param object
     * @return
     */
    boolean save(T object);

    /**
     * @param object
     * @param param
     * @return
     */
    T update(T object, Object param);
}
