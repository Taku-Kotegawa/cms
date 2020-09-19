package jp.co.stnet.cms.domain.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountImage {

    /**
     * ユーザ名
     */
    @Id
    private String username;

    /**
     *   拡張子
     */
    private String extension;

    /**
     *   ファイル本体
     */
    @Lob
    private byte[] body;
}
