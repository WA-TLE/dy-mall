package com.hmall.gateway.route;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.hmall.common.utils.CollUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @Author: dy
 * @Date: 2024/7/24 21:12
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicRouteLoader {
    public final RouteDefinitionWriter writer;
    public final NacosConfigManager nacosConfigManager;

    private final String dataId = "gateway-routes.json";
    private final String group = "DEFAULT_GROUP";

    private final Set<String> routeList = new HashSet<>();

    //  我们第一次项目最开始启动要加载路由, 所以我们要让他在项目启动后就执行这个方法
    @PostConstruct
    public void initRouteConfigListener() throws NacosException {
        //  1. 注册监听器,并且首次拉取配置
        String configInfo = nacosConfigManager.getConfigService()
                .getConfigAndSignListener(dataId, group, 5000, new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        //  监听到配置变更后, 更新配置
                        updateConfigInfo(configInfo);
                    }
                });
        updateConfigInfo(configInfo);
    }

    //  更新路由配置
    private void updateConfigInfo(String configInfo) {
        log.debug("监听到路由配置变更: {}", configInfo);

        //  1. 序列化 json 为对象
        List<RouteDefinition> routeDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);

        // 2.更新前先清空旧路由
        // 2.1.清除旧路由
        for (String routeId : routeList) {
            writer.delete(Mono.just(routeId)).subscribe();
        }
        routeList.clear();
        // 2.2.判断是否有新的路由要更新
        if (CollUtils.isEmpty(routeDefinitions)) {
            // 无新路由配置，直接结束
            return;
        }

        //  3. 跟新路由
        for (RouteDefinition routeDefinition : routeDefinitions) {
            writer.save(Mono.just(routeDefinition)).subscribe();
            routeList.add(routeDefinition.getId());
        }


    }


}
