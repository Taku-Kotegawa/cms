package jp.co.stnet.cms.app.common.interceptor;

import jp.co.stnet.cms.domain.common.StringUtils;
import jp.co.stnet.cms.domain.model.common.Variable;
import jp.co.stnet.cms.domain.model.common.VariableType;
import jp.co.stnet.cms.domain.repository.common.VariableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
public class BlockHandlerInterceptor implements HandlerInterceptor {


    @Autowired
    VariableRepository variableRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandle : {} {} {}" , request, request, handler);
        // Handlerメソッドが呼び出される前に行う処理を実装する
        // (実装は省略)

        // Handlerメソッドを呼び出す場合はtrueを返却する
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("postHandle : {} {} {} {}" , request, response, handler, modelAndView);
        // Handlerメソッドが正常終了した後に行う処理を実装する(例外が発生した場合は、このメソッドは呼び出されない)
        // (実装は省略)

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("afterCompletion : {} {} {} {}" , request, response, handler, ex);
        // Handlerメソッドの呼び出しが完了した後に行う処理を実装する(例外が発生しても、このメソッドは呼び出される)
        // (実装は省略)
    }

}
