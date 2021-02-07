package jp.co.stnet.cms.domain.service.authentication;

import javassist.NotFoundException;
import jp.co.stnet.cms.domain.model.authentication.EmailChangeRequest;
import jp.co.stnet.cms.domain.repository.authentication.EmailChangeRequestRepository;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessages;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jp.co.stnet.cms.domain.common.message.MessageKeys.E_SL_PR_5002;

@Service
@Transactional
public class EmailChangeServiceImpl implements  EmailChangeService {

    @Autowired
    PasswordGenerator passwordGenerator;

    @Resource(name = "passwordGenerationRules")
    List<CharacterRule> passwordGenerationRules;

    @Value("${security.tokenLifeTimeSeconds}")
    int tokenLifeTimeSeconds;

    @Value("${mail.from}")
    String mailFrom;

    @Autowired
    EmailChangeRequestRepository emailChangeRequestRepository;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    AccountSharedService accountSharedService;

    @Override
    public String createAndSendMailChangeRequest(String username, String mail) {

        // 暗証番号を生成
        String rowSecret = passwordGenerator.generatePassword(10,
                passwordGenerationRules);

        // トークンを生成
        String token = UUID.randomUUID().toString();

        // 暗証番号の有効期限
        LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(tokenLifeTimeSeconds);

        // 新しいメールアドレスと暗証番号を記録
        EmailChangeRequest emailChangeRequest = emailChangeRequestRepository.save(
                EmailChangeRequest.builder()
                        .token(token)
                        .username(username)
                        .secret(rowSecret)
                        .newMail(mail)
                        .expiryDate(expiryDate)
                        .build()
        );

        // 暗証番号を新しいメールアドレスに送信
        sendMail(mail, rowSecret);

        // トークンを返す
        return token;

    }

    private void sendMail(String to, String secret) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("メールアドレス変更の確認");
        message.setText("暗証番号: " + secret);
        message.setFrom(mailFrom);
        message.setTo(to);
        mailSender.send(message);
    }

    @Override
    public EmailChangeRequest findOne(String token) {
        return emailChangeRequestRepository.findById(token).orElse(null);
    }

    @Override
    public void changeEmail(String token) {

        // トークンで記録を検索
        EmailChangeRequest emailChangeRequest = findOne(token);

        // 記録が存在しない

        // 有効期限が存在している

        // 規定回数以上、暗証番号の確認に失敗している


    }

    @Override
    public void removeExpired(LocalDateTime date) {

    }
}
