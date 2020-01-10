package com.hy.ssm.lanxin.test;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class ResponseWrapper extends HttpServletResponseWrapper {
    private PrintWriter cachedWriter;
    private CharArrayWriter bufferedWriter;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        bufferedWriter=new CharArrayWriter();
        cachedWriter=new PrintWriter(bufferedWriter);
    }

    public PrintWriter getWriter(){
        return cachedWriter;
    }

    public String getResult(){
        return bufferedWriter.toString();
    }


}
