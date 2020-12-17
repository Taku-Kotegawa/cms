package jp.co.stnet.cms.domain.service.common;

public interface IndexSharedService {

    void reindexing(String entityName) throws InterruptedException;

}
