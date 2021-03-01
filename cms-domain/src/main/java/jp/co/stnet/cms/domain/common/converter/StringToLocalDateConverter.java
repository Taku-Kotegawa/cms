package jp.co.stnet.cms.domain.common.converter;

import com.github.dozermapper.core.DozerConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * dozer用コンバーター(yyyy/MM/dd)
 */
public class StringToLocalDateConverter extends DozerConverter<String, LocalDate> {

    static final private String DATE_FORMAT = "yyyy/MM/dd";

    public StringToLocalDateConverter() {
        super(String.class, LocalDate.class);
    }

    @Override
    public LocalDate convertTo(String source, LocalDate destination) {
        if (source == null) {
            return null;
        }
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    @Override
    public String convertFrom(LocalDate source, String destination) {
        if (source == null) {
            return null;
        }
        return source.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
