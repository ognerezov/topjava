package ru.javawebinar.topjava.model;

import java.util.HashSet;
import java.util.Set;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public enum Role {
    ROLE_USER,
    ROLE_ADMIN;
    public static Set<Role> getAdmin(){
        Set<Role> result=new HashSet<Role>();
        result.add(ROLE_ADMIN);
        return result;
    }
}
