package jp.co.stnet.cms.app.common.interceptor;

import jp.co.stnet.cms.domain.model.common.AccessCounter;
import jp.co.stnet.cms.domain.service.common.AccessCounterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class BlockHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    AccessCounterService accessCounterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("preHandle : {} {} {}", request, request, handler);
        // Handlerメソッドが呼び出される前に行う処理を実装する
        // (実装は省略)

        // Handlerメソッドを呼び出す場合はtrueを返却する
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && modelAndView.hasView()) {
            Optional<AccessCounter> accessCounter = accessCounterService.findByUrl(request.getRequestURI());
            accessCounter.ifPresent(counter -> modelAndView.addObject("accessCount", counter.getCount()));
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String[] noCountUrl = {""};

        if (response.getContentType().contains("text/html")) {
            accessCounterService.countUp(request.getRequestURI());
        }
    }

}
