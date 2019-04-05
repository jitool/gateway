package com.executor.gateway.core.rule;

import com.netflix.loadbalancer.*;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/2 0002 17:25
 * @Description: 灰度负载均衡
 */
public class GrayMetadataBalanceRule extends PredicateBasedRule{
    private  CompositePredicate predicate;

    public GrayMetadataBalanceRule() {
        super();
        predicate = createCompositePredicate(new GrayMetadataPredicate(), new AvailabilityPredicate(this,null));
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return predicate;
    }
    private CompositePredicate createCompositePredicate(GrayMetadataPredicate p1, AvailabilityPredicate p2) {
        return CompositePredicate.withPredicates(p1, p2)
                .build();

    }
}
