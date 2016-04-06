package com.eve0.fleetmob.crest.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

final class DateTimeDeserializer extends StdDeserializer<Long> {

    public DateTimeDeserializer() {
        super(Long.class);
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final String value = p.getCodec().readValue(p, String.class);
        if (StringUtils.isBlank(value)) {
            return 0l;
        }

        final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return f.parse(value).getTime();
        }
        catch (ParseException e) {
            throw new IOException(e);
        }
    }
}