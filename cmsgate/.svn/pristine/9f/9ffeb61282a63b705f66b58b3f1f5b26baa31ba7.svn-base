package com.uranus.platform.utils.exception;

import com.uranus.tools.psm.status.BaseStatus;
import com.uranus.tools.psm.status.BusinessCodeDefine;

import java.util.List;

public class PlatformExceptionFactory {

    private static class ExceptionBuilder{
        protected BusinessCodeDefine businessCode;
        protected String message;

        private ExceptionBuilder(BusinessCodeDefine businessCode) {
            this.businessCode = businessCode;
        }

        protected void initLocalParam(){
            this.businessCode = this.businessCode == null ? BaseStatus.SYSTEM_ERROR : this.businessCode;
            this.message = this.message == null ? this.businessCode.businessMessage() : this.message;
        }
    }

    public static class TestExceptionBuilder extends ExceptionBuilder{
        TestExceptionBuilder(BusinessCodeDefine businessCode) {
            super(businessCode);
        }

        public BusinessServiceException build(String errMessage){
            initLocalParam();
            return new BusinessServiceException(this.businessCode,errMessage);
        }
    }

    public static class CustomExceptionBuilder extends ExceptionBuilder{

        CustomExceptionBuilder(BusinessCodeDefine businessCode) {
            super(businessCode);
        }

        public BusinessServiceException build(String errMessage){
            initLocalParam();
            return new BusinessServiceException(this.businessCode,errMessage);
        }

        public BusinessServiceException build(Exception exception){
            initLocalParam();
            return new BusinessServiceException(this.businessCode,this.message,exception);
        }

        public BusinessServiceException build(Exception exception,String errMessage){
            initLocalParam();
            return new BusinessServiceException(this.businessCode,errMessage,exception);
        }
    }

    public static class JsonParseExceptionBuilder extends ExceptionBuilder {

        JsonParseExceptionBuilder(BusinessCodeDefine businessCode) {
            super(businessCode);
        }

        public BusinessServiceException build(){
            initLocalParam();
            return new JsonParseException(this.businessCode,this.message);
        }

        public BusinessServiceException build(String errMessage){
            initLocalParam();
            return new JsonParseException(this.businessCode,errMessage);
        }
        
        public BusinessServiceException build(Exception exception){
            initLocalParam();
            return new JsonParseException(this.businessCode,this.message,exception);
        }

        public BusinessServiceException build(String errMessage,Exception exception){
            initLocalParam();
            return new JsonParseException(this.businessCode,errMessage,exception);
        }
    }

    public static class InParamValidateExceptionBuilder extends ExceptionBuilder {
        InParamValidateExceptionBuilder(BusinessCodeDefine businessCode) {
            super(businessCode);
        }

        public BusinessServiceException build(){
            initLocalParam();
            return new ParamInValidateException(this.businessCode,this.message);
        }

        public BusinessServiceException build(String errMessage){
            initLocalParam();
            return new ParamInValidateException(this.businessCode,errMessage);
        }

        public BusinessServiceException build(List<String> errMessageList){
            initLocalParam();
            return new ParamInValidateException(this.businessCode,errMessageList);
        }
    }

    public static CustomExceptionBuilder exception(){
        return new CustomExceptionBuilder(BaseStatus.SYSTEM_ERROR);
    }

    public static CustomExceptionBuilder exception(BusinessCodeDefine businessCode){
        return new CustomExceptionBuilder(businessCode);
    }

    public static JsonParseExceptionBuilder jsonParseException(){
        return new JsonParseExceptionBuilder(BaseStatus.ILLEGAL_DATA);
    }

    public static JsonParseExceptionBuilder jsonParseException(BusinessCodeDefine businessCode){
        return new JsonParseExceptionBuilder(businessCode);
    }

    public static InParamValidateExceptionBuilder paramInValidateException(){
        return new InParamValidateExceptionBuilder(BaseStatus.ILLEGAL_DATA);
    }

    public static InParamValidateExceptionBuilder paramInValidateException(BusinessCodeDefine businessCode){
        return new InParamValidateExceptionBuilder(businessCode);
    }

    public static TestExceptionBuilder testException(BusinessCodeDefine businessCode){
        return new TestExceptionBuilder(businessCode);
    }

}
