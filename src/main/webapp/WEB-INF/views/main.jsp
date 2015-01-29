<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="utf-8">
    <title>管理系统</title>
    <link href="${ctx }/assets/css/main.css" rel="stylesheet" type="text/css" />
 </head>
 <body>
  <div class="header">
    
      <div class="dl-title">
          <img alt="LOGO" src="${ctx}/assets/images/logo.png" style="height: 20px;margin: 0px;padding: 0px;"><span class="lp-title-port">SpringNext</span><span class="dl-title-text">管理系统</span>
      </div>

    <div class="dl-log"><span class="dl-log-user"><shiro:principal/></span><a href="${ctx }/logout" title="退出系统" class="dl-log-quit">[退出]</a></div>
  </div>
   <div class="content">
    <div class="dl-main-nav">
      
      <ul id="J_Nav"  class="nav-list ks-clear">
        <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">首页</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-order">系统设置</div></li>
      </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
   </div>
  <script>
    BUI.use('common/main',function(){
      var config = [{
          id:'user', 
          homePage : 'dashboard',
          menu:[{
              text:'工作台',
              items:[{id:'dashboard',text:'个人工作台',href:'${ctx }/main/dashboard'}]
            },{
                text:'个人设置',
                items:[
                  {id:'changepassword',text:'修改密码',href:'${ctx }/main/toChangePassword'}  
                ]
              }]
          },{
          id:'system', 
          menu:[{
              text:'权限配置',
              items:[
                {id:'user-manager',text:'用户',href:'${ctx }/user/list'},
                {id:'role-manager',text:'角色',href:'${ctx }/role/list'},
                {id:'second-menu',text:'权限',href:'${ctx }/permissions/list'},
                {id:'dyna-menu',text:'资源鉴权',href:'${ctx }/resources/list'}
              ]
            },{
              text:'系统设置',
              items:[
                {id:'group-manager',text:'组织结构管理',href:'${ctx }/group/tree'}
              ]
            }]
          }];
      new PageUtil.MainPage({
        modulesConfig : config
      });
    });
  </script>
 </body>
</html>
