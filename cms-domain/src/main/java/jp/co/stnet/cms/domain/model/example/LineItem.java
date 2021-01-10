package jp.co.stnet.cms.domain.model.example;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class LineItem {

    private Long itemNo;

    private String itemName;

    private Integer unitPrise;

    private Integer itemNumber;

}
