package com.nathanieldoe.santa.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nathanieldoe.santa.model.Person;

import java.io.IOException;

/**
 * Created to customize the serialization of {@link com.nathanieldoe.santa.model.Exclusion}
 * to avoid recursive JSON structures.
 */
public class PersonExclusionSerializer extends StdSerializer<Person> {

    public PersonExclusionSerializer() {
        this(null);
    }

    public PersonExclusionSerializer(Class<Person> t) {
        super(t);
    }

    /**
     * See {@link Person} for field names
     */
    @Override
    public void serialize(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", person.getId());
        jsonGenerator.writeStringField("firstName", person.getFirstName());
        jsonGenerator.writeStringField("lastName", person.getLastName());
        jsonGenerator.writeEndObject();
    }

}
