package jp.co.stnet.cms.domain.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedMail implements Serializable {

    /**
     * 送信者アドレス
     */
    private String from;
    /**
     * 宛先アドレス
     */
    private String to;
    /**
     * 件名
     */
    private String subject;

    /**
     * 本文
     */
    private String text;
}
