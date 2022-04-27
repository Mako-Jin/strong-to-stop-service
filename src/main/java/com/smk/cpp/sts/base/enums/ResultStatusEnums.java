package com.smk.cpp.sts.base.enums;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:35
 */
public enum ResultStatusEnums {

    /********************************************************************/
    /***                       成功操作 以32开头                         ***/
    /*********************************************************************/
    SUCCESS_STATUS(320000, "success.status", "SUCCESS_STATUS"),

    /////////////////////////////////////////////////
    //              文件消息 3201开头                //
    /////////////////////////////////////////////////
    FILE_SPEED_UPLOAD_DONE(320100, "file.speed.upload.done", "FILE_SPEED_UPLOAD_DONE"),
    FILE_UPLOAD_DOWN_PARTIAL(320101, "file.upload.down.partial", "FILE_UPLOAD_DOWN_PARTIAL"),

    /*********************************************************************/
    /***                       异常请求 以35开头                         ***/
    /*********************************************************************/
    INTERNAL_SERVER_ERROR(350000, "system.error", "INTERNAL_SERVER_ERROR"),
    
    /////////////////////////////////////////////////
    //              公共异常 3501开头                //
    /////////////////////////////////////////////////
    COMMON_PARAM_CHECK_ERROR(350101, "param.check.error", "COMMON_PARAM_CHECK_ERROR"),
    COMMON_ID_CANNOT_NULL(350102, "id.cannot.be.null", "COMMON_ID_CANNOT_NULL"),
    COMMON_DATA_SAVE_FAILED(350103, "data.save.failed", "COMMON_DATA_SAVE_FAILED"),
    COMMON_DATA_UPDATE_FAILED(350104, "data.update.failed", "COMMON_DATA_UPDATE_FAILED"),
    COMMON_DATA_DELETE_FAILED(350105, "data.delete.failed", "COMMON_DATA_DELETE_FAILED"),
    COMMON_DATA_IS_EXISTED(350106, "data.is.existed", "COMMON_DATA_IS_EXISTED"),

    /////////////////////////////////////////////////
    //              认证异常 3502开头                //
    /////////////////////////////////////////////////
    AUTHENTICATION_TOKEN_CHECK_FAILED(350201, "token.check.failed", "AUTHENTICATION_TOKEN_CHECK_FAILED"),
    AUTHENTICATION_IMAGE_CODE_ERROR(350202, "image.code.error", "AUTHENTICATION_IMAGE_CODE_ERROR"),

    /////////////////////////////////////////////////
    //              文件上传 3503开头                //
    /////////////////////////////////////////////////
    UPLOAD_FILE_IS_NULL(350301, "upload.file.is.null", "UPLOAD_FILE_IS_NULL"),
    FILE_DIRECTORY_CREATE_FAIL(350302, "file.directory.create.fail", "FILE_DIRECTORY_CREATE_FAIL"),
    STORAGE_NOT_SUFFICIENT(350303, "storage.not.sufficient", "STORAGE_NOT_SUFFICIENT"),
    CHECK_IDENTIFIER_ERROR(350304, "check.identifier.error", "CHECK_IDENTIFIER_ERROR"),
    UPLOAD_FILE_ERROR(350305, "upload.file.error", "UPLOAD_FILE_ERROR"),
    
    DOWNLOAD_FILE_ERROR(350350, "download.file.error", "DOWNLOAD_FILE_ERROR"),

    /////////////////////////////////////////////////
    //              用户管理 3504开头                //
    /////////////////////////////////////////////////
    USERNAME_PHONE_DUPLICATION(350401, "username.phone.duplication", "USERNAME_PHONE_DUPLICATION"),

    /*********************************************************************/
    /***                       资源问题 以34开头                         ***/
    /*********************************************************************/

    /////////////////////////////////////////////////
    //              地址问题 3400开头                //
    /////////////////////////////////////////////////
    REQUEST_METHOD_NOT_SUPPORT(340000, "request.method.not.support", "REQUEST_METHOD_NOT_SUPPORT"),

    /////////////////////////////////////////////////
    //              认证问题 3401开头                //
    /////////////////////////////////////////////////
    NOT_LOGIN_EXCEPTION(340100, "user.not.sign.in", "NOT_LOGIN_EXCEPTION"),
    NONCE_EXPIRED_EXCEPTION(340101, "token.is.expired", "NONCE_EXPIRED_EXCEPTION"),
    TOKEN_IS_EXPIRED(340102, "token.is.expired", "TOKEN_IS_EXPIRED"),
    IMAGE_CODE_IS_EXPIRED(340103, "image.code.expired", "IMAGE_CODE_IS_EXPIRED"),
    TOKEN_IS_NOT_EXPIRED(340104, "token.is.not.expired", "TOKEN_IS_NOT_EXPIRED"),
    TOKEN_IS_INVALID(340105, "token.is.invalid", "TOKEN_IS_INVALID"),
    BAD_CREDENTIALS(340106, "bad.credentials", "BAD_CREDENTIALS"),

    /////////////////////////////////////////////////
    //              权限问题 3403开头                //
    /////////////////////////////////////////////////
    RESOURCE_WITHOUT_PERMISSIONS(340300, "resource.without.permission", "RESOURCE_WITHOUT_PERMISSIONS"),

    /////////////////////////////////////////////////
    //              数据问题 3404开头                //
    /////////////////////////////////////////////////
    USER_IS_NOT_EXISTED(340400, "user.is.not.existed", "USER_IS_NOT_EXISTED"),
    MENU_IS_NOT_EXISTED(340401, "menu.is.not.existed", "MENU_IS_NOT_EXISTED"),
    FILE_HAS_NOT_UPLOAD(340410, "file.has.not.upload", "FILE_HAS_NOT_UPLOAD"),
    FILE_IS_NOT_EXIST(340420, "file.is.not.exist", "FILE_IS_NOT_EXIST"),
    ;

    ResultStatusEnums(int code, String message, String statusKey) {
        this.code = code;
        this.message = message;
        this.statusKey = statusKey;
    }

    private int code;

    private String message;
    
    private String statusKey;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }
}
