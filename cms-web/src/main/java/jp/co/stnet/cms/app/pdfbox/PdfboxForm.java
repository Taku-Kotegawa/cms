package jp.co.stnet.cms.app.pdfbox;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PdfboxForm {

    private String pdfFileName;

    private Integer startPage;

    private Integer endPage;

}
