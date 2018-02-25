/*
 * 文件名：RepositoryException.java
 * 版权：Copyright by SpringNext
 * 描述：
 * 修改人：HyDe
 * 修改时间：2015年4月13日
 */

package org.springnext.manager.base.service;

/**
 * 
 * Repository没有初始化
 * 在继承BaseService的时,实现的initPagingAndSortingRepository方法返回了空值
 * 这个异常应该不会在上线时出现，在编码过程中就可以发现，并通过编码解决
 * @author HyDe
 * @version 2015年4月13日
 * @see UninitializedRepositoryException
 * @since
 */
public class UninitializedRepositoryException extends RuntimeException
{
    /**
     * 构造方法
     * @param message 异常消息
     */
    public UninitializedRepositoryException(String message)
    {
        super(message);
    }
    
    /**
     * UID
     */
    private static final long serialVersionUID = -5805855213391443906L;

}
