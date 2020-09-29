package jp.co.stnet.cms.domain.common;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    public static Map<String, String> getFileds(Class clazz, String parentClassName) {
        Map<String, String> fieldsMap = new LinkedHashMap<>();

        if (clazz != null) {
            String prefix = "";
            if (parentClassName != null && !parentClassName.isEmpty()) {
                prefix = parentClassName + "-";
            }

            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                if (m.getName().startsWith("set")) {
                    Class fieldClass = m.getParameterTypes()[0];
                    String fieldName = Introspector.decapitalize(m.getName().substring(3));

                    if ("java.lang.String".equals(fieldClass.getName())
                            || "java.util.List".equals(fieldClass.getName())
                            || "java.util.Map".equals(fieldClass.getName())) {
                        // 何もしない

                    } else {
                        fieldsMap.putAll(getFileds(fieldClass, fieldName));
                    }

                    fieldsMap.put(prefix + fieldName, fieldClass.getName());
                }
            }
        }

        return fieldsMap;
    }

}
