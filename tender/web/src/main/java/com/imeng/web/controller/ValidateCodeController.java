package com.imeng.web.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.imeng.common.constant.SecurityConstants;
import com.imeng.common.model.Result;
import com.imeng.web.service.IValidateCodeService;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * 验证码提供
 * @author zlt
 * @date 2018/12/18
 */
@Api(tags = "验证码获取")
@Controller
public class ValidateCodeController {
    @Autowired
    private IValidateCodeService validateCodeService;

    @Autowired
    DefaultKaptcha defaultKaptcha;

    //改属性用于表示选择哪一种验证码控件  gif 或空 选择 GifCaptcha  否则选择 DefaultKaptcha
    @Value("valid-code.type")
    private String validType;

    /**
     * 创建验证码  支持两种类型
     *
     * @throws Exception
     */
    @ApiOperation(value = "图形验证码")
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{deviceId}")
    public void createCode(@PathVariable String deviceId, HttpServletResponse response) throws Exception {
        Assert.notNull(deviceId, "机器码不能为空");
        if(StringUtils.isEmpty(validType)||validType.equalsIgnoreCase("gif")) {
            // 设置请求头为输出图片类型
            CaptchaUtil.setHeader(response);
            // 三个参数分别为宽、高、位数
            GifCaptcha gifCaptcha = new GifCaptcha(145, 34, 4);
            // 设置类型：字母数字混合
            gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);
            // 保存验证码
            validateCodeService.saveImageCode(deviceId, gifCaptcha.text().toLowerCase());
            // 输出图片流
            gifCaptcha.out(response.getOutputStream());
        }else{
            //静态验证码

            byte[] captchaChallengeAsJpeg = null;
            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            try {
                //生产验证码字符串并保存到session中
                String createText = defaultKaptcha.createText();
                // 保存验证码
                validateCodeService.saveImageCode(deviceId, createText);
                //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
                BufferedImage challenge = defaultKaptcha.createImage(createText);
                ImageIO.write(challenge, "jpg", jpegOutputStream);
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
            captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            ServletOutputStream responseOutputStream =
                    response.getOutputStream();
            responseOutputStream.write(captchaChallengeAsJpeg);
            responseOutputStream.flush();
            responseOutputStream.close();

        }
    }

    /**
     * 发送手机验证码
     * 后期要加接口限制
     *
     * @param mobile 手机号
     * @return R
     */
    @ResponseBody
    @ApiOperation(value = "手机短信验证码")
    @GetMapping(SecurityConstants.MOBILE_VALIDATE_CODE_URL_PREFIX + "/{mobile}")
    public Result createCode(@PathVariable String mobile) {
        Assert.notNull(mobile, "手机号不能为空");
        return validateCodeService.sendSmsCode(mobile);
    }
}
