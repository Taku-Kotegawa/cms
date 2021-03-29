package jp.co.stnet.cms.domain.common;

import jp.co.stnet.cms.domain.model.example.LombokBuilderEntity;
import jp.co.stnet.cms.domain.model.example.LombokCallSuperEntity;
import jp.co.stnet.cms.domain.model.example.LombokChildEntity;
import jp.co.stnet.cms.domain.model.example.LombokEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.Introspector;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LombokTest {

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

    @Test
    @DisplayName("001_equalsの検証: ")
    void test001() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isTrue();

    }

    @Test
    @DisplayName("002_equalsの検証: ")
    void test002() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.setField101("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("003_equalsの検証: ")
    void test003() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.setField102(999);

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("004_equalsの検証: ")
    void test004() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getList103().remove(0);
        entity2.getList103().add("11111");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("005_equalsの検証: ")
    void test005() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getList103().add("11111");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("006_equalsの検証: ")
    void test006() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getMap104().put("1", "b");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("007_equalsの検証: ")
    void test007() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getMap104().put("0", "a");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("008_equalsの検証: ")
    void test008() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildField105().setField201("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("009_equalsの検証: ")
    void test009() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildField105().getFieldList202().add("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("010_equalsの検証: ")
    void test010() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildList106().get(0).setField201("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("011_equalsの検証: ")
    void test011() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildList106().get(0).getFieldList202().add("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("012_equalsの検証: ")
    void test012() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.getChildList106().get(0).setFieldList202(new ArrayList<>());
        entity2.getChildList106().get(0).getFieldList202().add("a2");
        entity2.getChildList106().get(0).getFieldList202().add("a1");
        entity2.getChildList106().get(0).getFieldList202().add("a3");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("013_equalsの検証: ")
    void test013() {

        // 比較するエンティティを準備
        LombokEntity entity1 = new LombokEntity();
        setValue(entity1, "a");
        LombokEntity entity2 = new LombokEntity();
        setValue(entity2, "a");

        //
        entity2.setField001("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("014_equalsの検証: ")
    void test014() {

        // 比較するエンティティを準備
        LombokCallSuperEntity entity1 = new LombokCallSuperEntity();
        setValue(entity1, "a");
        LombokCallSuperEntity entity2 = new LombokCallSuperEntity();
        setValue(entity2, "a");

        //
        entity2.setField001("changed");

        // equals で比較
        boolean actual = entity1.equals(entity2);

        System.out.println("entity1: " + entity1.toString());
        System.out.println("entity2: " + entity2.toString());

        // 検証
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("015_builderの検証: ")
    void test015() {
        LombokBuilderEntity lombokBuilderEntity = create("aaa");
        System.out.println(lombokBuilderEntity.toString());
    }

    @Test
    @DisplayName("016_リフレクションの検証: ")
    void test016() {
        Map<String, Boolean> filedAuthMap = initFiledAuthMap(getFileds(LombokEntity.class, ""));

        System.out.println(filedAuthMap);

    }

    private Map<String, Boolean> initFiledAuthMap(List<String> fieldNames) {

        String[] attributes = {"disabed", "readonly", "hidden"};
        Map<String, Boolean> fMap = new LinkedHashMap<>();

        for (String fieldName : fieldNames) {
            for (String attribute : attributes) {
                fMap.put(fieldName + "__" + attribute, new Boolean(false));
            }
        }
        return fMap;
    }

    private List<String> getFileds(Class clazz) {
        return getFileds(clazz, "");
    }

    private List<String> getFileds(Class clazz, String parentClassName) {

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


    private void setValue(LombokChildEntity entity, String value) {
        entity.setField201(value);
        List<String> field202 = new ArrayList<>();
        field202.add(value + "1");
        field202.add(value + "2");
        field202.add(value + "3");
        entity.setFieldList202(field202);
    }

    private void setValue(LombokEntity entity, String value) {

        entity.setField001(value);
        entity.setField101(value);
        entity.setField102(1);
        entity.setList103(new ArrayList<>());
        entity.setMap104(new LinkedHashMap<>());
        entity.setChildField105(new LombokChildEntity());
        setValue(entity.getChildField105(), value);

        entity.setChildList106(new ArrayList<>());
        entity.getChildList106().add(new LombokChildEntity());

        entity.getList103().add(value + "1");
        entity.getList103().add(value + "2");
        entity.getMap104().put("1", value);
        entity.getMap104().put("2", value);
        setValue(entity.getChildList106().get(0), value);
    }

    private void setValue(LombokCallSuperEntity entity, String value) {

        entity.setField001(value);
        entity.setField101(value);
        entity.setField102(1);
        entity.setList103(new ArrayList<>());
        entity.setMap104(new LinkedHashMap<>());
        entity.setChildField105(new LombokChildEntity());
        setValue(entity.getChildField105(), value);

        entity.setChildList106(new ArrayList<>());
        entity.getChildList106().add(new LombokChildEntity());

        entity.getList103().add(value + "1");
        entity.getMap104().put("1", value);
        setValue(entity.getChildList106().get(0), value);
    }

    private LombokBuilderEntity create(String value) {
        return LombokBuilderEntity.builder()
                .firstName(value)
                .lastName(value)
                .age(1)
                .build();
    }

}