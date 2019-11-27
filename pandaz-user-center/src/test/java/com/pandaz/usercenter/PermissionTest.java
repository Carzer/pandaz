package com.pandaz.usercenter;

import com.pandaz.usercenter.custom.constants.PermissionConstants;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.service.PermissionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * pandaz:com.pandaz.usercenter
 * <p>
 * TODO
 *
 * @author Carzer
 * Date: 2019-11-25 14:41
 */
public class PermissionTest extends BasisUnitTest {
    @Autowired
    private PermissionService permissionService;

    @Test
    public void test(){
        PermissionEntity p = new PermissionEntity();
        p.setName("test");
        p.setCode("843d1089ce4b78eb7164accadc70");
        p.setUrl("/test");
        p.setPriority(Byte.valueOf("1"));
        p.setBitResult(2);
        p.setRequestType(PermissionConstants.PERMISSION_GET);
        try {
            permissionService.insert(p);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
