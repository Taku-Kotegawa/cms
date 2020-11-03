package jp.co.stnet.cms.app.admin.upload;

import jp.co.stnet.cms.domain.model.common.FileManaged;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadForm {

    /**
     * アップロードファイル(Uuid)
     */
    private String uploadFileUuid;

    /**
     * アップロードファイル(Managed)
     */
    private FileManaged uploadFileManaged;

}
