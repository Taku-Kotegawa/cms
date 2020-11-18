package jp.co.stnet.cms.domain.common;


import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    /**
     * クラスのフィールド一覧を取得する
     *
     * @param clazz
     * @param parentClassName
     * @return
     */
    public static Map<String, String> getFileds(Class clazz, String parentClassName) {

        if (clazz == null) {
            throw new IllegalArgumentException();
        }

        Map<String, String> fieldsMap = new LinkedHashMap<>();

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

        return fieldsMap;
    }

    /**
     * 指定したアノテーションが設定されているフィールドを取得する
     *
     * @param clazz           クラス
     * @param parentClassName 親クラス(スーパークラス)
     * @param Class           アノテーションクラス
     * @return フォールド名とアノテーションの組み合わせMap
     */
    public static Map<String, Annotation> getFieldByAnnotation(Class clazz, String parentClassName, Class annotationClass) {

        if (clazz == null) {
            throw new IllegalArgumentException();
        }

        Map<String, Annotation> annotationMap = new LinkedHashMap<>();

        String prefix = "";
        if (parentClassName != null && !parentClassName.isEmpty()) {
            prefix = parentClassName + "-";
        }

        Map<String, String> fields = getFileds(clazz, parentClassName);

        for (String fieldName : fields.keySet()) {
            Field field = null;
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                continue;
            }
            Annotation annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                annotationMap.put(fieldName, annotation);
            }
        }

        return annotationMap;

    }


}
