package org.kkp.controller;

import com.google.code.kaptcha.Producer;
import org.kkp.core.http.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Klaus
 * @since 2022/1/12
 **/
@RestController("/captcha")
public class CaptchaController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 获取验证码
     *
     * @return
     */
    @GetMapping("/captchaImage")
    public RestResponse<String> image() {
        // todo 验证码开关， 验证码开关如果是关了就直接没有验证码了


        return RestResponse.success();
    }

}
