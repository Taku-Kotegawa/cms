package jp.co.stnet.cms.domain.common;

import java.beans.Introspector;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class AuthMapUtils {

    private static final String DISABLED = "disabled";
    private static final String READONLY = "readonly";
    private static final String HIDDEN = "hidden";
    private static final String INPUT = "input";
    private static final String VIEW = "view";

    private static final String[] attributes = {DISABLED, READONLY, HIDDEN, VIEW, INPUT};

    public static void setDisabledTrue(Map<String, Boolean> authMap, String fieldName) {
        setAttribute(authMap, fieldName, DISABLED, true);
    }

    public static void setDisabledFalse(Map<String, Boolean> authMap, String fieldName) {
        setAttribute(authMap, fieldName, DISABLED, false);
    }

    public static void setReadOnlyTrue(Map<String, Boolean> authMap, String fieldName) {
        setAttribute(authMap, fieldName, READONLY, true);
    }

    public static void setReadOnlyFalse(Map<String, Boolean> authMap, String fieldName) {
        setAttribute(authMap, fieldName, READONLY, false);
    }

    public static void setHiddenTrue(Map<String, Boolean> authMap, String fieldName) {
        setAttribute(authMap, fieldName, HIDDEN, true);
    }

    public static void setHiddenFalse(Map<String, Boolean> authMap, String fieldName) {
        setAttribute(authMap, fieldName, HIDDEN, false);
    }

    public static void setViewTrue(Map<String, Boolean> authMap, String fieldName) {
        setAttribute(authMap, fieldName, VIEW, true);
    }

    public static void setViewFalse(Map<String, Boolean> authMap, String fieldName) {
        setAttribute(authMap, fieldName, VIEW, false);
    }

    public static void setDisabledTrueAll(Map<String, Boolean> authMap) {
        setAttributeAll(authMap, DISABLED, true);
    }

    public static void setDisabledFalseAll(Map<String, Boolean> authMap) {
        setAttributeAll(authMap, DISABLED, false);
    }

    public static void setReadOnlyTrueAll(Map<String, Boolean> authMap) {
        setAttributeAll(authMap, READONLY, true);
    }

    public static void setReadOnlyFalseAll(Map<String, Boolean> authMap) {
        setAttributeAll(authMap, READONLY, false);
    }

    public static void setHiddenTrueAll(Map<String, Boolean> authMap) {
        setAttributeAll(authMap, HIDDEN, true);
    }

    public static void setHiddenFalseAll(Map<String, Boolean> authMap) {
        setAttributeAll(authMap, HIDDEN, false);
    }

    public static void setInputTrueAll(Map<String, Boolean> authMap) {

        setAttributeAll(authMap, INPUT, true);
    }

    public static void setInputFalseAll(Map<String, Boolean> authMap) {
        setAttributeAll(authMap, INPUT, false);
    }

    public static void setViewTrueAll(Map<String, Boolean> authMap) {
        setAttributeAll(authMap, VIEW, true);
    }

    public static void setViewFalseAll(Map<String, Boolean> authMap) {
        setAttributeAll(authMap, VIEW, false);
    }

    public static void addKey(Map<String, Boolean> authMap, String fieldName) {
        for (String attribute : attributes) {
            authMap.put(fieldName + "__" + attribute, new Boolean(false));
        }
    }

    private static void setAttributeAll(Map<String, Boolean> authMap, String attribute, Boolean status) {
        for (Map.Entry<String, Boolean> entry : authMap.entrySet()) {
            if (entry.getKey().endsWith(attribute)) {
                entry.setValue(status);
            }
        }
    }



    private static void setAttribute(Map<String, Boolean> authMap, String fieldName, String attribute, Boolean status) {
        String key = fieldName + "__" + attribute;
        if (authMap.get(key) != null) {
            authMap.put(key, status);
        } else {
            throw new IllegalArgumentException(key + " not found");
        }
    }

    public static Map<String, Boolean> initAuthMap(Class clazz) {
        return initAuthMap(clazz, new ArrayList<>(), new ArrayList<>());
    }


    public static Map<String, Boolean> initAuthMap(Class clazz, List<String> includeKeys, List<String> excludeKeys) {
        List<String> filedNames = getFileds(clazz);

        Iterator<String> it = filedNames.listIterator();
        while(it.hasNext()) {
            if (excludeKeys.contains(it.next())) {
                it.remove();
            }
        }
        filedNames.addAll(includeKeys);
        return initAuthMap(filedNames);
    }

    public static Map<String, Boolean> initAuthMap(List<String> fieldNames) {
        Map<String, Boolean> fMap = new LinkedHashMap<>();
        for (String fieldName : fieldNames) {
            for (String attribute : attributes) {
                fMap.put(fieldName + "__" + attribute, new Boolean(false));
            }
        }
        return fMap;
    }

    public static List<String> getFileds(Class clazz) {
        return getFileds(clazz, "");
    }

    public static List<String> getFileds(Class clazz, String parentClassName) {
        String prefix = "";
        if (parentClassName != null && !parentClassName.isEmpty()) {
            prefix = parentClassName + "-";
        }

        Method[] methods = clazz.getMethods();
        List<String> fieldNames = new ArrayList<>();
        for (Method m : methods) {
            if (m.getName().startsWith("set")) {
                Class fieldClass = m.getParameterTypes()[0];
                String fieldName = Introspector.decapitalize(m.getName().substring(3));

                if ("java.lang.String".equals(fieldClass.getName())
                        || "java.util.List".equals(fieldClass.getName())
                        || "java.util.Map".equals(fieldClass.getName())) {
                    // 何もしない

                } else {
                    fieldNames.addAll(getFileds(fieldClass, fieldName));
                }

                fieldNames.add(prefix + fieldName);
            }
        }
        return fieldNames;
    }

    public static String getSignature(Method m) {
        String sig;
        try {
            Field gSig = Method.class.getDeclaredField("signature");
            gSig.setAccessible(true);
            sig = (String) gSig.get(m);
            if (sig != null) return sig;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder("(");
        for (Class<?> c : m.getParameterTypes())
            sb.append((sig = Array.newInstance(c, 0).toString()), 1, sig.indexOf('@'));
        return sb.append(')')
                .append(
                        m.getReturnType() == void.class ? "V" :
                                (sig = Array.newInstance(m.getReturnType(), 0).toString()).substring(1, sig.indexOf('@'))
                )
                .toString();
    }

    // インスタンス化禁止
    private void AuthMapUtils() {
    }

}
