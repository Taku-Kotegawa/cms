package jp.co.stnet.cms.domain.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class BeanUtils {

    private BeanUtils() {}

    public static void setWhoColumn(hasWhoColumnInterface record) {

        // ログインユーザを取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();//get logged in username

        // 現在日時を取得
        LocalDateTime now = LocalDateTime.now();

        record.setCreatedAt(now);
        record.setUpdatedAt(now);
        record.setCreatedBy(username);
        record.setUpdatedBy(username);


    }

}
