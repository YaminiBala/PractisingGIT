/*
 * Decompiled with CFR 0_110.
 */
package qc.rest.examples.infrastructure;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class EntityMarshallingUtils {
    private EntityMarshallingUtils() {
    }

    public static <T> T marshal(Class<T> c, String xml) throws JAXBException {
        Object res;
        if (c == xml.getClass()) {
            res = xml;
        } else {
            JAXBContext ctx = JAXBContext.newInstance(c);
            Unmarshaller marshaller = ctx.createUnmarshaller();
            res = marshaller.unmarshal(new StringReader(xml));
        }
        return (T)res;
    }

    public static <T> String unmarshal(Class<T> c, Object o) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(c);
        Marshaller marshaller = ctx.createMarshaller();
        StringWriter entityXml = new StringWriter();
        marshaller.marshal(o, entityXml);
        String entityString = entityXml.toString();
        return entityString;
    }
}

