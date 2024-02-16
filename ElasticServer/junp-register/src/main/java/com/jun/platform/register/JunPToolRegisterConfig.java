package com.jun.platform.register;

import com.jun.platform.register.load.ToolLoad;
import com.jun.platform.register.unload.ToolUnload;

public class JunPToolRegisterConfig implements JunPToolRegister{
    @Override
    public ToolLoad toolLoad() {
        return null;
    }

    @Override
    public ToolUnload toolUnLoad() {
        return null;
    }
}
