package com.thhh.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T>{
    //状态码
    private Integer code;
    //结果
    private String message;
    //返回的数据
    private T data;

    //自定义构造函数，因为不是每一次响应都有data
    public CommonResult(Integer code, String message){
        this(code, message, null);
    }
}
