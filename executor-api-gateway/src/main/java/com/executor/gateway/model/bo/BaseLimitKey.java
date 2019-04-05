package com.executor.gateway.model.bo;

import com.executor.gateway.core.constant.LimitLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/28 0028 16:27
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseLimitKey implements Serializable {
    private String apiUri;
    //限制key
    private String limitBiz;
    private int burstCapacity;
    private int replenishRate;
}
