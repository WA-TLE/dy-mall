package com.hm.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hm.user.domain.dot.LoginFormDTO;
import com.hm.user.domain.po.User;
import com.hm.user.domain.vo.UserLoginVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author dy
 * @since 2023-05-05
 */
public interface IUserService extends IService<User> {

    UserLoginVO login(LoginFormDTO loginFormDTO);

    void deductMoney(String pw, Integer totalFee);
}
