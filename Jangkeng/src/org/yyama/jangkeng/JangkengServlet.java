package org.yyama.jangkeng;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yyama.jangkeng.action.JangKengAction;

public class JangkengServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /*
     * doGet
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doProc(req, resp);
    }

    /*
     * doPost
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doProc(req, resp);
    }

    /*
     * doProc
     */
    public void doProc(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            JangKengAction.doProc(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("サーブレット内でExceptionが発生しました。");
        }
    }
}
