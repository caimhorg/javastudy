package com.javastudy.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class InteractiveWithAjax {

    protected static final Logger LOG = LoggerFactory
            .getLogger(InteractiveWithAjax.class);

    public static void printWriter(Object o, HttpServletResponse response) {
        PrintWriter out = null;
        response.setContentType("text/html;charset=utf-8");
        try {
            out = response.getWriter();
            if (o != null) {
                out.print(o);
            } else {
                out.print("");
            }
        } catch (IOException e) {
            LOG.error("Ajax 与前台交互失败", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                LOG.error("流关闭失败", e);
            }
        }
    }
}
