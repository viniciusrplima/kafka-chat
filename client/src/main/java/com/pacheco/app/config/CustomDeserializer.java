package com.pacheco.app.util;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CustomDeserializer extends JsonDeserializer {

    public CustomDeserializer() {
        super();
        addTrustedPackages("com.pacheco.app.model");
    }
}
