package jp.co.stnet.cms.domain.common.converter;

import com.github.dozermapper.core.DozerConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * dozer用 java.util.Date <=> java.time.LocalDateTime の変換
 */
public class DateToLocalDateTimeConverter extends DozerConverter<Date, LocalDateTime> {

    public DateToLocalDateTimeConverter() {
        super(Date.class, LocalDateTime.class);
    }

    @Override
    public LocalDateTime convertTo(Date source, LocalDateTime destination) {
        if (source == null) {
            return null;
        }
        return LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public Date convertFrom(LocalDateTime source, Date destination) {
        if (source == null) {
            return null;
        }
        ZonedDateTime zdt = source.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

}
