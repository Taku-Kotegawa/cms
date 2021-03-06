package jp.co.stnet.cms.domain.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 画面表示におけるフィールド単位のアクセス制御を支援するユーティリティ。
 * Formクラスから「フィールド名__要素名」をキーとするhashMapを作成する。<br>
 * <br>
 * <br>
 * (使い方)<br>
 * 1. このクラスをインスタンスにする。
 * 2. set〜()で、フィールド単位の操作を変更する。
 * 3. asMap()で、hashMapを取得する。
 * <br>
 * (hashMapの構造)<br>
 * { fieldName__disabled: true, fieldName__readonly: false, ...}
 * <br>
 * <ul>
 * <li>__view: フィールドの値を表示する</li>
 * <li>__input: input要素を表示する</li>
 * <li>__disabled: input要素のdisabled属性を有効にする/しない</li>
 * <li>__readonly: input要素のreadonly属性を有効にする/しない</li>
 * <li>__hidden: input要素をhiddenにする</li>
 * </ul>
 */
public class StateMap {

    private final Map<String, Boolean> authMap = new HashMap<>();

    private final String DISABLED = "disabled";
    private final String READONLY = "readonly";
    private final String HIDDEN = "hidden";
    private final String INPUT = "input";
    private final String VIEW = "view";
    private final String[] attributes = {DISABLED, READONLY, HIDDEN, VIEW, INPUT};

    /**
     * 初期化
     *
     * @param clazz       Formクラス
     * @param includeKeys 追加するフィールド名のリスト
     * @param excludeKeys 除外するフィールド名のリスト
     * @throws IllegalArgumentException いずれかの引数がnullの場合
     */
    public StateMap(Class<?> clazz, List<String> includeKeys, List<String> excludeKeys) {

        if(clazz == null ) {
            throw new IllegalArgumentException("clazz must not be null.");
        }
        if( includeKeys == null ) {
            throw new IllegalArgumentException("includeKeys must not be null.");
        }
        if(excludeKeys == null) {
            throw new IllegalArgumentException("excludeKeys must not be null.");
        }

        List<String> filedNames = BeanUtils.getFieldList(clazz);
        filedNames.removeIf(excludeKeys::contains);
        filedNames.addAll(includeKeys);
        init(filedNames);
    }

    /**
     * fieldName__input → true
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setInputTrue(String fieldName) {
        return setAttribute(fieldName, INPUT, true);
    }

    /**
     * fieldName__input → false
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setInputFalse(String fieldName) {
        return setAttribute(fieldName, INPUT, false);
    }

    /**
     * fieldName__disabled → true
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setDisabledTrue(String fieldName) {
        return setAttribute(fieldName, DISABLED, true);
    }

    /**
     * fieldName__disabled → false
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setDisabledFalse(String fieldName) {
        return setAttribute(fieldName, DISABLED, false);
    }

    /**
     * fieldName__readonly → true
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setReadOnlyTrue(String fieldName) {
        return setAttribute(fieldName, READONLY, true);
    }

    /**
     * fieldName__readonly → false
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setReadOnlyFalse(String fieldName) {
        return setAttribute(fieldName, READONLY, false);
    }

    /**
     * fieldName__hidden → true
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setHiddenTrue(String fieldName) {
        return setAttribute(fieldName, HIDDEN, true);
    }

    /**
     * fieldName__hidden → false
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setHiddenFalse(String fieldName) {
        return setAttribute(fieldName, HIDDEN, false);
    }

    /**
     * fieldName__view → true
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setViewTrue(String fieldName) {
        return setAttribute(fieldName, VIEW, true);
    }

    /**
     * fieldName__view → false
     *
     * @param fieldName フィールド名
     * @throws IllegalArgumentException 指定したフィールド名が存在しない
     * @return StateMap
     */
    public StateMap setViewFalse(String fieldName) {
        return setAttribute(fieldName, VIEW, false);
    }

    /**
     * 全てのfieldName__disabled → true
     *
     * @return StateMap
     */
    public StateMap setDisabledTrueAll() {
        return setAttributeAll(DISABLED, true);
    }

    /**
     * 全てのfieldName__disabled → false
     *
     * @return StateMap
     */
    public StateMap setDisabledFalseAll() {
        return setAttributeAll(DISABLED, false);
    }

    /**
     * 全てのfieldName__readonly → true
     *
     * @return StateMap
     */
    public StateMap setReadOnlyTrueAll() {
        return setAttributeAll(READONLY, true);
    }

    /**
     * 全てのfieldName__readonly → false
     *
     * @return StateMap
     */
    public StateMap setReadOnlyFalseAll() {
        return setAttributeAll(READONLY, false);
    }

    /**
     * 全てのfieldName__hidden → true
     *
     * @return StateMap
     */
    public StateMap setHiddenTrueAll() {
        return setAttributeAll(HIDDEN, true);
    }

    /**
     * 全てのfieldName__hidden → false
     *
     * @return StateMap
     */
    public StateMap setHiddenFalseAll() {
        return setAttributeAll(HIDDEN, false);
    }

    /**
     * 全てのfieldName__input → true
     *
     * @return StateMap
     */
    public StateMap setInputTrueAll() {
        return setAttributeAll(INPUT, true);
    }

    /**
     * 全てのfieldName__input → false
     *
     * @return StateMap
     */
    public StateMap setInputFalseAll() {
        return setAttributeAll(INPUT, false);
    }

    /**
     * 全てのfieldName__view → true
     *
     * @return StateMap
     */
    public StateMap setViewTrueAll() {
        return setAttributeAll(VIEW, true);
    }

    /**
     * 全てのfieldName__view → false
     *
     * @return StateMap
     */
    public StateMap setViewFalseAll() {
        return setAttributeAll(VIEW, false);
    }

    /**
     * フィールドを追加
     *
     * @param fieldName フィールド名
     * @return StateMap
     */
    public StateMap addField(String fieldName) {
        for (String attribute : attributes) {
            authMap.put(fieldName + "__" + attribute, Boolean.FALSE);
        }
        return this;
    }

    private StateMap setAttributeAll(String attribute, Boolean status) {
        for (Map.Entry<String, Boolean> entry : authMap.entrySet()) {
            if (entry.getKey().endsWith(attribute)) {
                entry.setValue(status);
            }
        }
        return this;
    }

    private StateMap setAttribute(String fieldName, String attribute, Boolean status) {
        String key = fieldName + "__" + attribute;
        if (authMap.get(key) != null) {
            authMap.put(key, status);
        } else {
            throw new IllegalArgumentException(key + " not found");
        }
        return this;
    }


    private void init(List<String> fieldNames) {
        for (String fieldName : fieldNames) {
            for (String attribute : attributes) {
                authMap.put(fieldName + "__" + attribute, Boolean.FALSE);
            }
        }
    }

//    public List<String> getFileds(Class clazz) {
//        return getFileds(clazz, "");
//    }
//
//    public List<String> getFileds(Class clazz, String parentClassName) {
//        List<String> fieldNames = new ArrayList<>();
//
//        if (clazz != null) {
//            String prefix = "";
//            if (parentClassName != null && !parentClassName.isEmpty()) {
//                prefix = parentClassName + "-";
//            }
//
//            Method[] methods = clazz.getMethods();
//            for (Method m : methods) {
//                if (m.getName().startsWith("set")) {
//                    Class fieldClass = m.getParameterTypes()[0];
//                    String fieldName = Introspector.decapitalize(m.getName().substring(3));
//
//                    if ("java.lang.String".equals(fieldClass.getName())
//                            || "java.util.List".equals(fieldClass.getName())
//                            || "java.util.Map".equals(fieldClass.getName())) {
//                        // 何もしない
//
//                    } else {
//                        fieldNames.addAll(getFileds(fieldClass, fieldName));
//                    }
//
//                    fieldNames.add(prefix + fieldName);
//                }
//            }
//        }
//
//        return fieldNames;
//    }


    public Map<String, Boolean> asMap() {
        return authMap;
    }
}
