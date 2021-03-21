package jp.co.stnet.cms.app.person;

import jp.co.stnet.cms.domain.model.common.FileManaged;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonSearchRow {

    /**
     * 内部ID
     */
    private Long id;

    /**
     * バージョン
     */
    private Long version;

    /**
     * ステータス
     */
    private String statusLabel;

    /**
     * 氏名
     */
    private String name;


    private String code;

    /**
     * 年齢
     */
    private Integer age;

    /**
     * 添付ファイル(FileManaged UUID)
     */
    private String attachedFile01Uuid;

    /**
     * 添付ファイル(FileManaged)
     */
    private FileManaged attachedFile01Managed;


    private String contentHighlight;

}
