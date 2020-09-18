package jp.co.stnet.cms.domain.common;

import java.time.LocalDateTime;

public interface hasWhoColumnInterface {

    void setCreatedAt(LocalDateTime createdAt);

    void setCreatedBy(String createdBy);

    void setUpdatedAt(LocalDateTime updatedAt);

    void setUpdatedBy(String updatedBy);

}
