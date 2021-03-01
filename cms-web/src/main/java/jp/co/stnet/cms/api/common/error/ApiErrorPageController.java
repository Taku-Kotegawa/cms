package jp.co.stnet.cms.api.common.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.RequestDispatcher;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("error")
@RestController
public class ApiErrorPageController {

    @Autowired
    ApiErrorCreator apiErrorCreator;

    private final Map<HttpStatus, String> errorCodeMap = new HashMap<>();

    public ApiErrorPageController() {
        errorCodeMap.put(HttpStatus.NOT_FOUND, "e.ex.fw.5001");
    }

    @RequestMapping
    public ResponseEntity<ApiError> handleErrorPage(WebRequest request) {
        HttpStatus httpStatus = HttpStatus.valueOf((Integer) request
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE, RequestAttributes.SCOPE_REQUEST));
        String errorCode = errorCodeMap.get(httpStatus);
        ApiError apiError = apiErrorCreator.createApiError(request, errorCode,
                httpStatus.getReasonPhrase());

        return ResponseEntity.status(httpStatus).body(apiError);
    }

}
