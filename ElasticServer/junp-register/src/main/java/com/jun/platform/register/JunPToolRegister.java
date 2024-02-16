package com.jun.platform.register;

import com.jun.platform.register.load.ToolLoad;
import com.jun.platform.register.unload.ToolUnload;

/**
 * 该类为工具注册功能的接口类。 该类定义工具加载、剔除以及升级
 * <p>
 *     默认的配置为 {@link JunPToolRegisterConfig}
 *     如果你想定制则可以通过继承该类定制，但通常不推荐
 * </p>
 */
public interface JunPToolRegister {

    ToolLoad toolLoad();

    ToolUnload toolUnLoad();
}
