package com.hmall.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.hmall.common.utils.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.hmall.common.contest.UserInfoConstant.USER_INFO;

/**
 * @Author: dy
 * @Date: 2024/1/27 22:53
 * @Description: SpringMVC 拦截器  我们仅仅是使用它来获取信息, 并不校验任何请求, 所以我们什么请求都要拦截
 */

public class UserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  1. 获取请求头中的用户信息
        String userId = request.getHeader(USER_INFO);

        //  2. 判断是否为 null
        if (StrUtil.isNotBlank(userId)) {
            //  当前请求的发出者已登录, 我们将信息存入 ThreadLocal 中
            UserContext.setUser(Long.valueOf(userId));
        }

        //  3. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //  释放当前线程的信息
        UserContext.removeUser();
    }
}
