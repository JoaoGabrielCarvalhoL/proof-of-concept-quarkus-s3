package br.com.joaogabriel.utils;

import java.net.URI;
import java.util.UUID;

public class UriUtils {

    private UriUtils() {}

    public static URI genereateURI(String path, UUID id) {
        return URI.create(String.format("/%s/%s", path, id));
    }
}
