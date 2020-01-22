package com.pandaz.usercenter;

import com.pandaz.usercenter.custom.constants.PermissionConstants;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.service.PermissionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限测试
 *
 * @author Carzer
 * @since 2019-11-25
 */
public class PermissionTest extends BasisUnitTest {

    private PermissionService permissionService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Test
//    @Transactional
    public void insert() {
        PermissionEntity p = new PermissionEntity();
        p.setName("查询用户");
//        p.setCode("843d1089ce4b78eb7164accadc70");
        p.setUrl("/user");
        p.setPriority(Byte.valueOf("0"));
        p.setBitResult(2);
        p.setRequestType(PermissionConstants.PERMISSION_GET);
        p.setOsCode(SysConstants.DEFAULT_SYS_CODE);
        p.setLevel(3);
        try {
            permissionService.insert(p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
