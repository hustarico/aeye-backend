package _4.aeye.services;

import _4.aeye.entites.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Integer id);

    Role getRoleByName(String name);

    List<Role> getAllRoles();
}

