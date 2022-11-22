package com.midsummra.listener;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Hashtable;
import java.util.UUID;

@WebListener
public class UserSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {

        UUID uuid = UUID.randomUUID();

        // 获取session以及Servlet上下文对象
        HttpSession session = sessionEvent.getSession();
        ServletContext context = session.getServletContext();

        Object attribute = context.getAttribute("sessionSet");

        if (ObjectUtils.isEmpty(attribute)){
            Hashtable<String, HttpSession> sessionSet = new Hashtable<>();
            sessionSet.put(uuid.toString(),session);

            context.setAttribute("sessionSet",sessionSet);
            context.setAttribute("sessionSetSize",sessionSet.size());


        }else {
            Hashtable<String,HttpSession> sessionSet = (Hashtable<String,HttpSession>)attribute;
            sessionSet.put(uuid.toString(),session);
            context.setAttribute("sessionSetSize",sessionSet.size());
        }


    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {

        HttpSession session = sessionEvent.getSession();
        ServletContext context = session.getServletContext();
        Object attribute = context.getAttribute("sessionSetSize");
        if (ObjectUtils.isEmpty(attribute)){
            context.setAttribute("sessionSetSize",0);
        }else {
            int size = (int)attribute;
            if (size <= 0){
                context.setAttribute("sessionSetSize",0);
            }else {
                int temp = (int)context.getAttribute("sessionSetSize");
                context.setAttribute("sessionSetSize",temp-1);
            }
        }
    }
}
