package com.e.engapp.model;

import java.util.List;

public interface DatabaseInterface<T> {

    /**
     * @param object
     * @return
     */
    T get(T object);

    /**
     * @param object
     * @return
     */
    boolean save(T object);

    /**
     * @param object
     * @return
     */
    boolean update(T object);
}
