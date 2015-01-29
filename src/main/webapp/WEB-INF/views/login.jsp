<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../layouts/taglib.jsp" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="${ctx }/assets/css/login.css" />
<link rel="shortcut icon" href="${ctx }/assets/images/favicon.ico" type="image/x-icon" />
<title>SpringNext登录</title>
<!-- KILLHAPPY. 2013-08-10 -->
</head>

<body>

<div id="login-page-container">
	<form action="${ctx}/login" method="post">
	<table class="login-table" cellpadding="0" cellspacing="0">
    	<tr>
            <td class="login-title">
                <h1><img alt="LOGO" src="${ctx}/assets/images/logo2.png" style="height: 20px;">SpringNext登录</h1>
                <p>版本 v1.0.1</p>
            </td>
            <td>
				
					<%
						String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
						if(error != null){
							%>
							<div class="login-error-tips">
							<%
							if(error.contains("DisabledAccountException")){
								out.print("用户已被屏蔽,请登录其他用户.");
							}
							else{
								out.print("用户名或密码错误");
							}
							%>
							</div>
							<%
						}
					%>
				
                
                <p class="logintitle">用户名: </p>
                <p class="loginform"><input class="txt" name="username" type="text" value="" autocomplete="off" tabindex="1" /></p>
                
                <p class="logintitle">密&#12288;码:</p>
                <p class="loginform"><input class="txt" name="password" type="password" value="" autocomplete="off" tabindex="2" /></p>
                
                <p class="logintitle" style="display:none;">验证码:</p>
                <p class="loginform" style="display:none;"><input class="txt v-code" name="v_code" type="v_code" value="" autocomplete="off" tabindex="3" /></p>
                                
                <p class="nofloat">
                    <input type="submit" class="button login-submit-button" value="登录" tabindex="4" />
                    
                </p>                
            </td> 
        </tr>
    </table>
    </form>
    <div class="login-page-foot">
		<div class="copy-right"><p><span class="c-icon">&copy;</span> 2014, SpringNext.</p></div>    
    </div>
</div>

</body>
</html>

