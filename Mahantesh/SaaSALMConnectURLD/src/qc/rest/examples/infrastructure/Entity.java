/*
 * Decompiled with CFR 0_110.
 */
package qc.rest.examples.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"fields"})
@XmlRootElement(name="Entity")
public class Entity {
   // @XmlElement(name="Fields", required=1)
    protected Fields fields;
   // @XmlAttribute(name="Type", required=1)
    protected String type;

    public Entity(Entity entity) {
        this.type = new String(entity.getType());
        this.fields = new Fields(entity.getFields());
    }

    public Entity() {
    }

    public Fields getFields() {
        return this.fields;
    }

    public void setFields(Fields value) {
        this.fields = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getFieldValue(String fieldName) {
        String fieldValue = null;
        List<Fields.Field> fieldList = this.getFields().getField();
        for (Fields.Field field : fieldList) {
            if (!fieldName.equals(field.getName())) continue;
            fieldValue = field.getValue().get(0).toString();
        }
        return fieldValue;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"field"})
    public static class Fields {
       // @XmlElement(name="Field", required=1)
        protected List<Field> field;

        public Fields(Fields fields) {
            this.field = new ArrayList<Field>(fields.getField());
        }

        public Fields() {
        }

        public List<Field> getField() {
            if (this.field == null) {
                this.field = new ArrayList<Field>();
            }
            return this.field;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"value"})
        public static class Field {
          //  @XmlElement(name="Value", required=1)
            protected List<String> value;
         //   @XmlAttribute(name="Name", required=1)
            protected String name;

            public List<String> getValue() {
                if (this.value == null) {
                    this.value = new ArrayList<String>();
                }
                return this.value;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String value) {
                this.name = value;
            }
        }

    }

}

