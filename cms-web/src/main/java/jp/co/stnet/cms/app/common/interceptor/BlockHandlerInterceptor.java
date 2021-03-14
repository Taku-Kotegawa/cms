package jp.co.stnet.cms.app.common.interceptor;

import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.model.common.AccessCounter;
import jp.co.stnet.cms.domain.repository.common.VariableRepository;
import jp.co.stnet.cms.domain.service.common.AccessCounterService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
//        log.info("postHandle : {} {} {} {}", request, response, handler, modelAndView);
        // Handlerメソッドが正常終了した後に行う処理を実装する(例外が発生した場合は、このメソッドは呼び出されない)
        // (実装は省略)

        try {
            if (modelAndView.hasView()) {
                AccessCounter accessCounter = accessCounterService.findByUrl(request.getRequestURI());
                modelAndView.addObject("accessCount", accessCounter.getCount());
            }
        } catch (ResourceNotFoundException e) {
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("afterCompletion : {} {} {} {}", request, response, handler, ex);
        // Handlerメソッドの呼び出しが完了した後に行う処理を実装する(例外が発生しても、このメソッドは呼び出される)
        // (実装は省略)

        String[] noCountUrl = {""};

        if (response.getContentType().contains("text/html")) {
            accessCounterService.countUp(request.getRequestURI().toString());
        }
    }

}
