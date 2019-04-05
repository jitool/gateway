package com.executor.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.config.properties.RouteProperties;
import com.executor.gateway.core.ApiConfigManager;
import com.executor.gateway.core.constant.GatewayAttrTypeEnum;
import com.executor.gateway.core.constant.RESPONSE;
import com.executor.gateway.mapper.ApiConfigMapper;
import com.executor.gateway.mapper.GatewayAttrConfigMapper;
import com.executor.gateway.mapper.GatewayConfigMapper;
import com.executor.gateway.model.bo.ApiConfigBo;
import com.executor.gateway.model.po.ApiConfig;
import com.executor.gateway.model.po.GatewayAttrConfig;
import com.executor.gateway.model.po.GatewayConfig;
import com.executor.gateway.service.DynamicRouteService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/8 17:31
 * @Description:
 */
@Service
@Slf4j
public class DynamicRouteServiceImpl implements DynamicRouteService {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ApiConfigManager apiConfigManager;
    @Resource
    private GatewayConfigMapper gatewayConfigMapper;
    @Resource
    private ApiConfigMapper apiConfigMapper;
    @Resource
    private GatewayAttrConfigMapper gatewayAttrConfigMapper;
    @Autowired
    private RouteProperties routeProperties;

    private void notifyChanged() {
        applicationContext.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void loadRoutes() {
        //刷新路由配置
        this.refreshAllRoute();
        //刷新内存的api配置
        this.refreshApi();
        //通知刷新
        this.notifyChanged();
        log.info("路由资源加载完毕");
    }

    @Override
    public void refreshApi() {
        List<ApiConfig> apiConfigList = apiConfigMapper.queryAll();
        ApiConfigBo apiConfigBo;
        for (ApiConfig apiConfig : apiConfigList) {
            apiConfigBo = new ApiConfigBo();
            BeanUtils.copyProperties(apiConfig, apiConfigBo);
            apiConfigManager.putConfig(apiConfig.getPatternUrl(), apiConfigBo);
        }
    }

    private RouteDefinition loadRouteDefinition(GatewayConfig gatewayConfig) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(gatewayConfig.getServiceId());
        try {
            //必须要加上协议头才能访问
            URI uri;
            if (gatewayConfig.getUrl().startsWith("lb")) {
                uri = new URI(gatewayConfig.getUrl());
            } else {
                uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl()).build().toUri();
            }
            definition.setUri(uri);
        } catch (URISyntaxException e) {
            log.error("获取url异常", e);
        }
        //设置predicate和filter
        this.setPredicatesAndFilters(gatewayConfig, definition);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        return definition;
    }


    private void setPredicatesAndFilters(GatewayConfig gatewayConfig, RouteDefinition definition) {
        List<GatewayAttrConfig> gatewayAttrConfigs = gatewayAttrConfigMapper.queryAllByServiceId(gatewayConfig.getServiceId());
        if (gatewayAttrConfigs.isEmpty()) {
            throw new NullPointerException("gateway attrs can not allow empty please check");
        }
        List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
        List<FilterDefinition> filterDefinitions = new ArrayList<>();
        for (GatewayAttrConfig gatewayAttrConfig : gatewayAttrConfigs) {
            if (GatewayAttrTypeEnum.PREDICATE.getValue() == gatewayAttrConfig.getType()) {
                predicateDefinitions.add(this.getPredicateDefinition(gatewayAttrConfig));
            } else {
                filterDefinitions.add(this.getFilterDefinition(gatewayAttrConfig));
            }
        }
        definition.setPredicates(predicateDefinitions);
        definition.setFilters(filterDefinitions);
    }


    private PredicateDefinition getPredicateDefinition(GatewayAttrConfig gatewayAttrConfig) {
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(gatewayAttrConfig.getAttrName());
        if (!Strings.isNullOrEmpty(gatewayAttrConfig.getAttrArgs())) {
            Map<String, String> args = (Map<String, String>) JSON.parse(gatewayAttrConfig.getAttrArgs());
            predicateDefinition.setArgs(args);
        }
        return predicateDefinition;
    }


    private FilterDefinition getFilterDefinition(GatewayAttrConfig gatewayAttrConfig) {
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(gatewayAttrConfig.getAttrName());
        if (!Strings.isNullOrEmpty(gatewayAttrConfig.getAttrArgs())) {
            Map<String, String> args = (Map<String, String>) JSON.parse(gatewayAttrConfig.getAttrArgs());
            filterDefinition.setArgs(args);
        }
        return filterDefinition;
    }


    @Override
    public Integer updateRoute(GatewayConfig gatewayConfig) {
        if (gatewayConfig == null) {
            return RESPONSE.ERROR;
        }
        this.loadRouteDefinition(gatewayConfig);
        this.notifyChanged();
        return RESPONSE.SUCCESS;
    }

    @Override
    public Integer deleteRoute(String serviceId) {
        routeDefinitionWriter.delete(Mono.just(serviceId)).subscribe();
        this.notifyChanged();
        return RESPONSE.SUCCESS;
    }

    @Override
    public Integer refreshAllRoute() {
        List<GatewayConfig> gatewayConfigList = gatewayConfigMapper.queryAll();
        if (gatewayConfigList.isEmpty()) {
            log.warn("还没有配置任何路由");
            return RESPONSE.ERROR;
        }
        //开始装载spring cloud gateway 路由
        List<RouteDefinition> routeDefinitions = gatewayConfigList.stream().map(this::loadRouteDefinition).collect(Collectors.toList());
        routeProperties.setRoutes(routeDefinitions);
        //通知刷新
        this.notifyChanged();
        return RESPONSE.SUCCESS;
    }

}
