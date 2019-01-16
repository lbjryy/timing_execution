package com.sourceSoft.digitalProjct.controller.common;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sourceSoft.digitalProjct.constant.SysConstant;
import com.sourceSoft.digitalProjct.controller.BaseController;
import com.sourceSoft.digitalProjct.model.system.SysMenu;
import com.sourceSoft.digitalProjct.model.system.SysUser;
import com.sourceSoft.digitalProjct.service.system.SysMenuService;
import com.sourceSoft.digitalProjct.service.system.SysRoleService;
import com.sourceSoft.digitalProjct.service.system.SysUserService;
import com.sourceSoft.digitalProjct.util.DateUtil;

@Controller
public class CommonController {
	
	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	SysMenuService sysMenuService;
	
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register(){
		return "register";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String loginDo(String username, String password, 
			ModelMap map, HttpServletRequest request) {
		if(StringUtils.isEmpty(username)){
			map.addAttribute("errorMsg", "抱歉，用户名不能为空");
			return "login";
		} 
		if(StringUtils.isEmpty(password)){
			map.addAttribute("errorMsg", "抱歉，密码不能为空");
			map.addAttribute("username", username);
			return "login";
		} 
		SysUser loginUser = sysUserService.loginCheck(username, password);
		if(loginUser == null){
			map.addAttribute("errorMsg", "抱歉，您输入的用户名或密码不正确");
			map.addAttribute("username", username);
			return "login";
		}
		if(!SysConstant.ValidState.VALID.equals(loginUser.getValidFlag())){
			map.addAttribute("errorMsg", "抱歉，您的账户已失效");
			map.addAttribute("username", username);
			return "login";
		}
		
		try{
			//清除session中的数据信息
			Enumeration<String> em = request.getSession().getAttributeNames();
			while(em.hasMoreElements()){
				request.getSession().removeAttribute(em.nextElement().toString());
			}
			// 用户信息
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			// 角色名称信息
			String roleNames = sysRoleService.getRoleNamesForSysUserById(loginUser.getId());
			session.setAttribute("roleNames", roleNames);
			// 获取用户有权限访问的菜单信息
			List<SysMenu> sysMenus = sysMenuService.findByUserId(loginUser.getId());
			session.setAttribute("sysMenus", sysMenus);
			for(int i = 0; sysMenus != null && i < sysMenus.size(); i++){
				SysMenu menu = sysMenus.get(i);
//				if(!"1".equals(menu.getField1()) && !"2".equals(menu.getField1())){
					session.setAttribute(menu.getMenuCode(), true);
//				}
			}

			// 更新用户的登录信息
			SysUser tmpUser = new SysUser();
			String now = DateUtil.getSysDateTimeString();
			String clientIp = BaseController.getClientIpAddr(request);
			tmpUser.setLastLoginTime(now);
			tmpUser.setLastLoginIp(clientIp);
			tmpUser.setId(loginUser.getId());
			this.sysUserService.updateBySelective(tmpUser);
		}catch(Exception e){
			e.printStackTrace();
			map.addAttribute("errorMsg", "抱歉，系统异常！");
			map.addAttribute("username", username);
			return "login";
		}
		// 返回页面路径
		return "redirect:index";
	}
	
	@RequestMapping("index")
	public String index(ModelMap map){
		map.addAttribute("subflagMap",SysConstant.SUBFLAG_MAP);
		return "index";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		// 清空session中的用户及用户权限信息
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		session.removeAttribute("roleNames");
		session.removeAttribute("sysMenus");
		//清除session中的数据信息
		Enumeration<String> em = request.getSession().getAttributeNames();
		while(em.hasMoreElements()){
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		return "redirect:login";
	}
}
