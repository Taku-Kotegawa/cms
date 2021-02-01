package jp.co.stnet.cms.app.common.job;

import jp.co.stnet.cms.app.common.uploadfile.UploadFileForm;
import jp.co.stnet.cms.domain.common.exception.IllegalStateBusinessException;
import jp.co.stnet.cms.domain.common.message.MessageKeys;
import jp.co.stnet.cms.domain.model.authentication.LoggedInUser;
import jp.co.stnet.cms.domain.model.authentication.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.gfw.common.message.ResultMessages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("job")
public class JobController {

    private final String BASE_PATH = "job";
    private final String JSP_RESULT = BASE_PATH + "/result";
    private final String JSP_JOBLOG = BASE_PATH + "/joblog";
    private final String JSP_SUMMARY = BASE_PATH + "/summary";

    @Autowired
    JobExplorer jobExplorer;

    @ModelAttribute
    UploadFileForm setup() {
        return new UploadFileForm();
    }

    @GetMapping("summary")
    public String summary(Model model, @AuthenticationPrincipal LoggedInUser loggedInUser) {
        List<Map<String, Object>> jobSummary = new ArrayList<>();
        List<String> jobNames = jobExplorer.getJobNames();

        for (String jobName : jobNames) {
            Map map = new LinkedHashMap();
            JobInstance jobInstance = jobExplorer.getLastJobInstance(jobName);
            map.put("jobName", jobName);
            map.put("jobInstance", jobInstance);
            map.put("jobExecution", jobExplorer.getLastJobExecution(jobInstance));
            jobSummary.add(map);
        }

        model.addAttribute("jobSummary", jobSummary);
        return JSP_SUMMARY;
    }


    @GetMapping("result")
    public String result(Model model,
                         @RequestParam(value = "targetjob", required = false) String targetjob,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        boolean isAdmin = loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name()));

        List<String> jobList = jobExplorer.getJobNames();
        model.addAttribute("jobList", jobList);

        if (targetjob == null) {
            targetjob = jobList.get(0);
        }

        List<JobInstance> instances = jobExplorer.getJobInstances(targetjob, 0, 10);
        List<JobExecution> executions = new ArrayList<>();
        for (JobInstance i : instances) {
            executions.addAll(jobExplorer.getJobExecutions(i));
        }

        if (!isAdmin) {
            // TODO 一般ユーザは自分のジョブのみ参照可能
        }


        model.addAttribute("selectedJob", targetjob);
        model.addAttribute("jobResults", executions);
        return JSP_RESULT;
    }

    @GetMapping("joblog")
    public String jobLog(Model model, @RequestParam(value = "jobexecutionid") Long jobExecutionId,
                         @AuthenticationPrincipal LoggedInUser loggedInUser) {

        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);
        if (jobExecution == null) {
            throw new IllegalStateBusinessException(ResultMessages.error().add(MessageKeys.E_SL_FW_5001));
        }

        String jobName = jobExecution.getJobInstance().getJobName();
        String filePath = "/home/taku/Job_" + jobName + "_" + jobExecutionId + ".log";
        List<String> fileLines = new ArrayList<>();
        try {
            fileLines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            fileLines.add("ログファイルが見つかりません。");
        }

        model.addAttribute("jobName", jobName);
        model.addAttribute("jobExecutionId", jobExecutionId.toString());
        model.addAttribute("jobLog", fileLines);
        return JSP_JOBLOG;
    }

}
