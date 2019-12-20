package com.centerm.sinopecsdktest;

/**
 * @author qzhhh on 2019-05-05 11:16
 */
public class ModuleBean {
    String title;
    Class targetClass;

    public ModuleBean(String title, Class targetClass) {
        this.title = title;
        this.targetClass = targetClass;
    }

    public String getTitle() {
        return title;
    }

    public Class getTargetClass() {
        return targetClass;
    }
}
