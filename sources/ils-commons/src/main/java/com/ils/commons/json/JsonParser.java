package com.ils.commons.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by mara on 10/21/15.
 */
@Service("jsonParser")
public class JsonParser {

    private static final Logger logger = Logger.getLogger(JsonParser.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public Object toObject(String jsonString, Class clazz){
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            String error = "An error occurred while converting json string: " + jsonString + " into an object of type " + clazz;
            logger.error(error, e);
            e.printStackTrace();
            throw new RuntimeException(error);
        }
    }

    public String toJson(Object message) {
        StringBuilder result = new StringBuilder();
        if (message != null) {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                result.append(ow.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                String error = "An error occurred while converting an object to json String";
                logger.error(error, e);
                throw new RuntimeException(error);
            }
        }
        return result.toString();
    }
}
