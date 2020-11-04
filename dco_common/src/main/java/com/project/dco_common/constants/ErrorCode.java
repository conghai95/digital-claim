package com.project.dco_common.constants;

import com.project.dco_common.api.HttpStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

@Component
public class ErrorCode {

    public static HttpStatusResponse E001;

    public static HttpStatusResponse E002;

    public static HttpStatusResponse E003;

    public static HttpStatusResponse E004;

    public static HttpStatusResponse N001;

    public static HttpStatusResponse N002;

    public static HttpStatusResponse N003;

    public static HttpStatusResponse N004;

    @Autowired
    Environment env;

    @PostConstruct
    private void init() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field ele: fields) {
            try {
                if ("env".equals(ele.getName())) {
                    continue;
                }
                HttpStatusResponse field = new HttpStatusResponse();
                field.setCode(env.getProperty("error." + ele.getName().toLowerCase() + ".code"));
                field.setHttp(Integer.valueOf(env.getProperty("error." + ele.getName().toLowerCase() + ".http")));
                field.setMessage(env.getProperty("error." + ele.getName().toLowerCase() + ".message"));
                ele.set(this, field);
            } catch (Exception e) {
                try {
                    ele.set(ErrorCode.class, null);
                } catch (IllegalAccessException illegalAccessException) {
                }
            }
        }
    }
}
