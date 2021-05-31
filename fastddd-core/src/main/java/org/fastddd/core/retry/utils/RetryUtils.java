package org.fastddd.core.retry.utils;

import org.apache.commons.lang3.StringUtils;
import org.fastddd.api.event.EventHandler;
import org.fastddd.api.retry.Retryable;
import org.fastddd.common.exception.SystemException;
import org.fastddd.common.factory.BeanFactory;
import org.fastddd.common.factory.FactoryBuilder;
import org.fastddd.common.invocation.Invocation;
import org.fastddd.common.utils.ReflectionUtils;
import org.fastddd.core.retry.constants.RetryConstants;
import org.fastddd.core.retry.constants.RetryLauncher;
import org.fastddd.core.retry.model.RetryContext;
import org.fastddd.core.retry.strategy.RetryStrategyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author: frank.li
 * @date: 2021/3/30
 */
public class RetryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryUtils.class);

    public static Object doWithRetry(Invocation invocation) {
        return RetryStrategyHelper.doWithRetry(invocation);
    }

    public static Retryable getRetryable(Invocation invocation) {
        EventHandler eventHandler = ReflectionUtils.getAnnotation(invocation.getMethod(), EventHandler.class);
        if (eventHandler != null) {
            return eventHandler.retryable();
        }
        return ReflectionUtils.getAnnotation(invocation.getMethod(), Retryable.class);
    }

    public static boolean canRetry(Retryable retryable, Throwable t, int retriedCount) {
        return retriedCount < retryable.maxAttempts() && retryForThrowable(retryable, t);
    }

    private static boolean retryForThrowable(Retryable retryable, Throwable t) {

        if (retryable.notRetryableForExceptions().length > 0) {
            Throwable current = t;
            while (current != null) {
                for (Class<?> notRetryableException : retryable.notRetryableForExceptions()) {
                    if (notRetryableException.isAssignableFrom(current.getClass())) {
                        return false;
                    }
                }
                current = current.getCause();
            }
        }
        return true;
    }



}