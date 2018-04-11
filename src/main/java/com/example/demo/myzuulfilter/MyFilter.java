package com.example.demo.myzuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Administrator on 2018/4/11 0011.
 */
@Component
public class MyFilter extends ZuulFilter {
    //pre：路由之前
    @Override
    public String filterType() {
        return "pre";
    }

    //filterOrder：过滤的顺序
    @Override
    public int filterOrder() {
        return 0;
    }

    //shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
    //这里必须写true，否则就会抛出异常
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            System.out.println("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }

        System.out.println("ok");
        return null;

    }
}
