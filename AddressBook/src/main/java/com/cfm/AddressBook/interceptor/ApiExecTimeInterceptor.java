package com.cfm.AddressBook.interceptor;

import com.cfm.AddressBook.model.ExecTime;
import com.cfm.AddressBook.repo.ApiExecTimeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Component
@Log4j2
public class ApiExecTimeInterceptor implements HandlerInterceptor {

    @Autowired
    ApiExecTimeRepository execTimeRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {

        long startTime = System.currentTimeMillis();
        log.info("Interceptor Start time: {}",startTime);
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object object, Exception arg3) throws Exception {

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        ExecTime execTime = new ExecTime();
        execTime.setOperation(request.getMethod());
        execTime.setUri(request.getRequestURI());
        execTime.setDate(LocalDate.now());
        execTime.setExecutionTime(endTime - startTime);
        log.info("Total Execution Time: {}",execTime);
        execTimeRepository.save(execTime);
    }


}
