package jp.co.stnet.cms.domain.service.authentication;


import jp.co.stnet.cms.domain.common.Constants;
import jp.co.stnet.cms.domain.model.authentication.Account;
import jp.co.stnet.cms.domain.model.authentication.AccountImage;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.common.FileManaged;
import jp.co.stnet.cms.domain.repository.authentication.AccountImageRepository;
import jp.co.stnet.cms.domain.repository.authentication.AccountRepository;
import jp.co.stnet.cms.domain.service.AbstractNodeService;
import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional
public class AccountServiceImpl extends AbstractNodeService<Account, String> implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountImageRepository accountImageRepository;

    @Autowired
    FileManagedSharedService fileManagedSharedService;

    protected AccountServiceImpl() {
        super(Account.class);
    }

    @Override
    protected JpaRepository<Account, String> getRepository() {
        return accountRepository;
    }

    @Override
    public Account invalid(String id) {
        Account account = findById(id);
        account.setStatus(Constants.STATUS.INVALID);
        return save(account);
    }

    @Override
    @PostAuthorize("returnObject == true")
    public Boolean hasAuthority(String Operation, LoggedInUser loggedInUser) {
        return true;
    }

    @Override
    public Account save(Account account, String ImageUuid) {

        if (ImageUuid != null) {
            FileManaged fileManaged = fileManagedSharedService.findByUuid(ImageUuid);

            AccountImage image = AccountImage.builder()
                    .username(account.getUsername())
                    .extension(StringUtils.getFilenameExtension(fileManaged.getOriginalFilename()))
                    .body(fileManagedSharedService.getFile(fileManaged.getFid()))
                    .build();
            accountImageRepository.save(image);
        }

        return getRepository().save(account);
    }


}
