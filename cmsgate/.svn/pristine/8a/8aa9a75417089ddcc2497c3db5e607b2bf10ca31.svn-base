package com.uranus.platform.utils.exception.handler.impl;

import com.uranus.platform.utils.exception.BusinessServiceException;
import com.uranus.platform.utils.exception.handler.PlatformExceptionLogHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintExceptionHandlerImpl implements PlatformExceptionLogHandler {
    @Override
    public void handleExceptionLog(BusinessServiceException serviceException) {
        log.error(serviceException.printLog(true));
    }

    @Override
    public void handleExceptionLog(Exception exception) {
        exception.printStackTrace();
    }
}
