package com.kerbores.gitea.client.request;

import java.io.Serial;
import java.util.HashMap;

/**
 * @author kerbores(kerbores@gmail.com)
 */
public class Parameters extends HashMap<String, Object> {

    @Serial
    private static final long serialVersionUID = 1L;

    public static Parameters NEW() {
        return new Parameters();
    }

    public Parameters add(String key, Object value) {
        put(key, value);
        return this;
    }
}
