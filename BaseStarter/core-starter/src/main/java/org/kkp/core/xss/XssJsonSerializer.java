package org.kkp.core.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.kkp.core.CoreConstant;
import org.kkp.core.util.CoreUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Klaus
 * @since 2021/12/23
 **/
public class XssJsonSerializer extends JsonSerializer<String> {

    /**
     * Method for accessing type of Objects this serializer can handle.
     * Note that this information is not guaranteed to be exact -- it
     * may be a more generic (super-type) -- but it should not be
     * incorrect (return a non-related type).
     * <p>
     * Default implementation will return null, which essentially means
     * same as returning <code>Object.class</code> would; that is, that
     * nothing is known about handled type.
     * <p>
     */
    @Override
    public Class<String> handledType() {
        return String.class;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            HttpServletRequest request = CoreUtil.getHttpServletRequest();
            if (request != null && request.getRequestURI().contains(CoreConstant.PARAMETER_STRING_BATCH_PATH_ROOT)) {
                jsonGenerator.writeString(value);
            } else {
                jsonGenerator.writeString(XssUtil.xssEncode(value));
            }
        }
    }
}
