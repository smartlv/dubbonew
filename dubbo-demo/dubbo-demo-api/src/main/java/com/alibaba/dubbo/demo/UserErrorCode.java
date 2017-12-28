package com.alibaba.dubbo.demo;

/**
 *用户模块错误码范围（7000 ~ 7999）
 */
public interface UserErrorCode
{
    ErrorCode STATE_ERROR = new ErrorCode(7001, "账户状态异常");
    ErrorCode NOT_REGISTED = new ErrorCode(7002, "账户未注册");
    ErrorCode UPDATE_FAILED = new ErrorCode(7003,"更新失败，请重试");
    ErrorCode REPEAT_SUBMIT = new ErrorCode(7004,"资料已经提交，请勿重复提交");
    ErrorCode STEP_ERROR_SUBMIT = new ErrorCode(7004,"请勿跨步骤操作");
    ErrorCode SLIDER_ERROR = new ErrorCode(7005,"滑块验证失败");
    ErrorCode CAPTCHA_ERROR = new ErrorCode(7007,"验证码有误");
    ErrorCode SEND_CODE_ERROR = new ErrorCode(7008,"发送失败");
    ErrorCode THIRD_TOKEN_INVAILD = new ErrorCode(7009,"第三方token已过期");
    ErrorCode NEED_BIND_PHONE_OR_EMAIL = new ErrorCode(7010,"未绑定手机号、邮箱");

    ErrorCode TICKET_INVAILD = new ErrorCode(7014,"无效的票据");
    ErrorCode USER_INVAILD = new ErrorCode(7015,"无效的用户");
    ErrorCode MODIFY_PASSWORD_ERROR = new ErrorCode(7016,"修改密码失败");
    ErrorCode BIND_PHONE_EMAIL_ERROR = new ErrorCode(7017,"绑定失败");
    ErrorCode HAS_REGISTER = new ErrorCode(7018,"帐号已注册");
    ErrorCode ILLEGAL = new ErrorCode(7019,"非法请求");
    ErrorCode CHANGE_BIND_SAME_ERROR = new ErrorCode(7020,"不能与原号码相同");
    ErrorCode CHANGE_BIND_SAME_EMAIL_ERROR = new ErrorCode(7021,"不能与原邮箱相同");
    ErrorCode GET_THIRD_TOKEN_ERROR = new ErrorCode(7022,"获取第三方token失败");
    ErrorCode TIMES_LIMIT_ERROR = new ErrorCode(7023,"操作次数限制");
    ErrorCode OCR_ERROR = new ErrorCode(7024,"OCR识别失败");
    ErrorCode SAVE_AUDIT_ERROR = new ErrorCode(7025,"当前开户状态错误,请重新审核");
    ErrorCode TOO_YOUNT_TO_OPEN = new ErrorCode(7026,"年龄小于21周岁，不符合开户年龄");
    ErrorCode REPEAT_NO = new ErrorCode(7027,"该证件号码重复,请重新输入");
    ErrorCode CHECK_LOGIN_PASSWORD_ERROR = new ErrorCode(7028,"登录密码错误");
    ErrorCode CHECK_TRADE_PASSWORD_ERROR = new ErrorCode(7029,"交易密码错误");
    ErrorCode CHECK_IDCARD_ERROR = new ErrorCode(7030,"身份证校验错误");
    ErrorCode HAS_NOT_REGISTER = new ErrorCode(7031,"帐号未注册");
    ErrorCode RESET_SLIDER = new ErrorCode(7032,"重置滑块");

    ErrorCode FAVORITES_STOCK_LIMIT = new ErrorCode(7033,"自选股票数量已达到上限！");
    ErrorCode FAVORITES_GROUP_LIMIT = new ErrorCode(7034,"自选分组数量已达到上限！");

    ErrorCode THIRD_USER_HAS_BINDED = new ErrorCode(7035, "第三方账号已被绑定");
    ErrorCode THIRD_USER_DUPLICATE_BINDED = new ErrorCode(7036, "不允许绑定同一平台的多个账号");

    ErrorCode HAS_NOT_PASS_MM = new ErrorCode(7037, "需先通过米盟审核");
    ErrorCode ACCOUNT_NOTICE_ERROR = new ErrorCode(7038, "发送账号通知异常");

    ErrorCode HAS_CUSTOMER_NO = new ErrorCode(7039, "该用户已存在客户号");
    ErrorCode DUP_CUSTOMER_NO = new ErrorCode(7040, "客户号重复");
    ErrorCode DUP_FUND_ACCOUNT = new ErrorCode(7041, "资金号重复");
    ErrorCode CAN_NOT_CANCEL = new ErrorCode(7042,"不能取消推送任务");
    ErrorCode SOURCE_INCOME_SUM = new ErrorCode(7043,"收入来源必须100%");
    ErrorCode CHECK_IB_RESP = new ErrorCode(7044,"检测ib返回文件异常");
    ErrorCode CUSTOMER_NO_EXIST = new ErrorCode(7045,"客户不存在");
}
