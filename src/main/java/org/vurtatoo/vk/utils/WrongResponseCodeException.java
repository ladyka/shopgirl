package org.vurtatoo.vk.utils;

import java.io.IOException;

public class WrongResponseCodeException extends IOException {
    private static final long serialVersionUID = 6L;

    public WrongResponseCodeException(String message) {
        super(message);
    }

}
