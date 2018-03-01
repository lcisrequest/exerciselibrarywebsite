package com.example.lcdemo.modular.auth.filter;

public enum  ActionLogEnum {
    ADMIN_USERS_DISABLEUSER("/admin/users/DisableUser", "禁用了用户"),
    ADMIN_USERS_DELETEUSER("/admin/users/DeleteUser", "删除了用户"),
    ADMIN_USERS_UPDATEPAWERS("/admin/users/updatePawers", "更新了用户权限"),
    ADMIN_USERS_INSERTROLE("/admin/users/insertRole", "添加了角色"),
    ADMIN_USERS_DELETEROLE("/admin/users/deleteRole", "删除了角色"),
    ADMIN_USERS_INSERADMIN("/admin/users/inserAdmin", "添加了管理员"),
    ADMIN_USERS_UPDATEADMIN("/admin/users/updateAdmin", "更新了管理员"),
    ADMIN_USERS_DELETEADMIN("/admin/users/deleteAdmin", "删除了管理员"),
    ADMIN_USERS_UPDATEUSERINFO("/admin/users/updateUserInfo", "更新用户信息");

    /**
     * 路由
     */
    private final String router;

    /**
     * 操作
     */
    private final String action;

    ActionLogEnum(final String router, final String action) {
        this.router = router;
        this.action = action;
    }

    public String getRouter() {
        return router;
    }

    public String getAction() {
        return action;
    }
}
