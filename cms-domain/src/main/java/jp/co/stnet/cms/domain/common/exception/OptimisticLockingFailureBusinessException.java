package jp.co.stnet.cms.domain.common.exception;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;

public class OptimisticLockingFailureBusinessException extends BusinessException {
    public OptimisticLockingFailureBusinessException(ResultMessages messages) {
        super(messages);
    }
}
