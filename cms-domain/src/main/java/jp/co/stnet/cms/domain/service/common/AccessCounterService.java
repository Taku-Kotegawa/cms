package jp.co.stnet.cms.domain.service.common;

import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.AccessCounter;
import jp.co.stnet.cms.domain.service.NodeIService;

public interface AccessCounterService extends NodeIService<AccessCounter, Long> {

    AccessCounter findByUrl(String url);

    Long countUp(String url);

    Boolean hasAuthority(String operation, LoggedInUser loggedInUser);

}
