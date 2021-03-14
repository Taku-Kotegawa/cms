package jp.co.stnet.cms.domain.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.terasoluna.gfw.common.codelist.EnumCodeList;

@AllArgsConstructor
@Getter
public enum FileType implements EnumCodeList.CodeListItem {

    PERSON("person",  "txt;csv;", "2"),
    SIMPLE_ENTITITY("simpleentity",  "png;jpg;gif;", "5"),
    FILE_UPLOAD("fileupload",  "png;jpg;gif;", "10"),
    DOCUMENT("document",  "pdf;", "100"),
    VARIABLE("variable",  "png;jpg;gif;", "10"),

    DEFAULT("default", "", "10");

    private final String label;

    private final String extensionPattern;
    private final String fileSize;

    @Override
    public String getCodeLabel() {
        return label;
    }

    @Override
    public String getCodeValue() {
        return label;
    }

    public static FileType getByValue(String value) {
        for (FileType fileType : FileType.values()) {
            if (fileType.getCodeValue().equals(value)) {
                return fileType;
            }
        }
        return null;
    }
}
