package vn.metech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.metech.service.IRoleService;
import vn.metech.shared.ActionResult;

@RestController
@RequestMapping("/user/role")
public class RoleController {

  private IRoleService roleService;

  public RoleController(IRoleService roleService) {
    this.roleService = roleService;
  }

  @GetMapping("/list-all")
  public ActionResult getAllRoles() {
    return new ActionResult(roleService.getAllRoles());
  }

}
