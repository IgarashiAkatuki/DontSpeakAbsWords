package com.project.controller;

import com.project.common.response.ErrorInfo;
import com.project.common.response.ResponseStatusCode;
import com.project.common.response.Result;
import com.project.common.utils.RegexUtils;
import com.project.service.Hanzi2EmojiService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
@ConditionalOnProperty(
        name = "config.enableHanzi2Pinyin",
        havingValue = "true"
)
public class Hanzi2EmojiController {

    @Autowired
    @Qualifier("hanzi2EmojiService")
    private Hanzi2EmojiService service;


    @GetMapping("/hanzi2Emoji/{str}")
    @ResponseBody
    @ApiOperation("将汉字转化为Emoji")
    public Result hanzi2Emoji(@Validated @PathVariable("str") String string){

        String str = string;
        str = str.trim();
        if (str.isEmpty() || str.length() > 200){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(),ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        str = RegexUtils.removeExtraSpace(str);
        if (str.isEmpty() || str.length() > 200){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(),ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        String s = service.Hanzi2Emoji(str);

        return Result.suc(s);
    }
}
