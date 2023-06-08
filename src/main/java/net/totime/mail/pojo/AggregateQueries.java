/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * 此版权个人开发者保留最终归属权与解释权，非版权所有者授权禁止商用与演绎.
 */

package net.totime.mail.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.totime.mail.dto.PaginationDTO;

/**
 * <p>
 * 泛型说明：
 *     <ul>
 *         <li>T：直接性查询条件对象</li>
 *         <li>C：基本查询条件对象</li>
 *         <li>R：模糊查询条件对象</li>
 *         <li>泛型对象可以为空，为空时不进行查询</li>
 *         <li>泛型对象必须是一个Bean</li>
 *         <li>泛型对象的属性<b>必须是基本数据类型</b></li>
 *     </ul>
 * </p>
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/02
 * @description 聚合查询对象
 * @since 1.0.0
 */
@Data
@ApiModel(value = "聚合查询对象")
public class AggregateQueries<T, C, R> {
    /**
     * 直接性查询条件对象(T是一个Bean)
     * <p>
     * 直接性查询对象如果存在，<b>模块查询条件直接失效</b>，场景如：<br/> <b>ID直接查询</b>、<b>手机号直接查询</b>
     * </p>
     */
    @ApiModelProperty(value = "直接性查询条件对象")
    private T queries;
    /**
     * 分页信息对象
     * <p>
     * 分页对象包含分页信息，<b>分页信息必须存在</b>，场景如：<br/> <b>分页查询</b>
     * </p>
     */
    @ApiModelProperty(value = "分页信息对象")
    private PaginationDTO pagination;
    /**
     * 基本查询条件对象(C是一个Bean)
     * <p>
     * 基本查询对象与直接性查询可以同时存在，<b>基本查询条件对象的查询条件会与直接性查询条件对象的查询条件进行组合</b>，场景如：<br/>
     * <b>直接性查询ID为10001的用户</b>，<b>基本性查询状态为true的用户</b>，结合后的查询条件为：<br/>
     * <b>查询ID为10001且状态为true的用户</b>
     * </p>
     */
    @ApiModelProperty(value = "基本查询条件对象")
    private C condition;
    /**
     * 模糊查询条件对象(R是一个Bean)
     * <p>
     * 模糊查询与直接性条件查询互斥，与基本查询条件对象互补，<b>模糊查询条件对象的查询条件会与基本查询条件对象的查询条件进行组合</b>，场景如：<br/>
     * <b>基本性查询状态为true的用户</b>，<b>模糊性查询用户名为张三的用户</b>，结合后的查询条件为：<br/>
     * <b>查询状态为true且用户名包含张三的用户</b>
     * </p>
     */
    @ApiModelProperty(value = "模糊查询条件对象")
    private R fuzzyQueries;
    /**
     * 排序字段
     * <p>
     * 排序字段可以为空，为空时不进行排序
     * </p>
     */
    @ApiModelProperty(value = "排序字段")
    private String sortField;
    /**
     * 排序方式
     * <p>
     * 排序方式可以为空，为空时默认为升序，0：升序，1：降序
     * </p>
     */
    @ApiModelProperty(value = "排序方式")
    private Integer sortType;

    /**
     * 是否存在直接性查询条件对象
     *
     * @return boolean true：存在，false：不存在
     */
    public boolean hasQueries() {
        return queries != null;
    }

    /**
     * 是否存在分页信息对象
     *
     * @return boolean true：存在，false：不存在
     */
    public boolean hasPagination() {
        return pagination != null;
    }

    /**
     * 是否存在基本查询条件对象
     *
     * @return boolean true：存在，false：不存在
     */
    public boolean hasCondition() {
        return condition != null;
    }

    /**
     * 是否存在模糊查询条件对象
     *
     * @return boolean true：存在，false：不存在
     */
    public boolean hasFuzzyQueries() {
        return fuzzyQueries != null;
    }

    /**
     * 是否存在排序字段
     *
     * @return boolean true：存在，false：不存在
     */
    public boolean hasSortField() {
        return sortField != null;
    }

    /**
     * 是否存在排序方式
     *
     * @return boolean true：存在，false：不存在
     */
    public boolean hasSortType() {
        return sortType != null;
    }
}
