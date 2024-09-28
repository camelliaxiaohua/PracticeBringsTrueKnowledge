package camellia.cloudstorage.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Datetime: 2024/9/28下午4:35
 * @author: Camellia.xioahua
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -819471127050336312L;

    /**
     * 响应状态码
     */
    private int statusCode;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应消息
     */
    private String massage;


    public BaseResponse(int statusCode, T data, String massage){
        this.statusCode = statusCode;
        this.data = data;
        this.massage = massage;
    }

    public BaseResponse(int statusCode, String massage){
        this.statusCode = statusCode;
        this.massage = massage;
    }

    public BaseResponse(int statusCode){
        this.statusCode = statusCode;
    }

}
