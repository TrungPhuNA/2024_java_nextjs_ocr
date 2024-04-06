package be.ocrapi.common;

import com.dslplatform.json.CompiledJson;
import com.fasterxml.jackson.annotation.JsonInclude;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.common.FieldViolation;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private T data;
    private Metadata meta = new Metadata();
    private String status;
    private String message;
    @CompiledJson
    BaseResponse(T data, Metadata meta, String status, String message) {
        this.data = data;
        this.meta = meta;
        this.status = status;
        this.message = message;
    }

    public BaseResponse() {
    }

    public static <T> BaseResponse<T> ofSucceeded() {
        return ofSucceeded((T) null);
    }

    @SuppressWarnings("unchecked")
    public static <T> BaseResponse<T> ofSucceeded(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.data = data;
        response.meta.code = Metadata.OK_CODE;
        response.status = "success";
        response.message = "Successfully!";
        return response;
    }

    public static <T> BaseResponse<List<T>> ofSucceeded(Page<T> data) {
        BaseResponse<List<T>> response = new BaseResponse<>();
        response.data = data.getContent();
        response.status = "success";
        response.meta.page = data.getNumber() + 1;
        response.meta.size = data.getSize();
        response.meta.page_size = data.getSize();
        response.meta.total = data.getTotalElements();
        return response;
    }
    public static BaseResponse<Void> ofFailed(BusinessErrorCode errorCode) {
        return ofFailed(errorCode, (String) null);
    }

    public static BaseResponse<Void> ofFailed(BusinessErrorCode errorCode, List<FieldViolation> errors) {
        return ofFailed(errorCode, null, errors);
    }

    public static BaseResponse<Void> ofFailed(BusinessErrorCode errorCode, String message) {
        return ofFailed(errorCode, message, null);
    }

    public static BaseResponse<Void> ofFailed(BusinessErrorCode errorCode, String message, List<FieldViolation> errors) {
        BaseResponse<Void> response = new BaseResponse<>();
        response.meta.code = "Error" + errorCode.getCode();
        response.meta.message = message;
        response.meta.errors = errors;
        response.status = "error";
        response.message = message;
        return response;
    }

    public static BaseResponse<Void> ofFailed(BusinessException exception) {
        return ofFailed(exception.getErrorCode(), exception.getMessage());
    }

    public T getData() {
        return data;
    }

    public Metadata getMeta() {
        return meta;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Metadata {
        public static final String OK_CODE = "Success" + 200;
        String code;
        Integer page;
        Integer size;
        Integer page_size;
        Long total;
        String message;
        List<FieldViolation> errors;

        public Metadata() {
        }
        @CompiledJson
        public Metadata(String code, Integer page, Integer size, Long total, String message, List<FieldViolation> errors) {
            this.code = code;
            this.page = page + 1;
            this.size = size;
            this.page_size = size;
            this.total = total;
            this.message = message;
            this.errors = errors;
        }

        public String getCode() {
            return code;
        }

        public Integer getPage() {
            return page;
        }

        public Integer getSize() {
            return size;
        }
        public Integer getPage_size() {
            return page_size;
        }

        public Long getTotal() {
            return total;
        }

        public String getMessage() {
            return message;
        }

    }
}