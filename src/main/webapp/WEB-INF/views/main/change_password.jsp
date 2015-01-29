<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="utf-8">
    <title>修改密码</title>
</head>
<body>
<div class="container">
	<form id="save_Form" class="form-horizontal" action="${ctx}/main/changePassword" method="post">
        <div class="row">
        <div class="control-group span7">
          <label class="control-label">旧密码：</label>
          <div class="controls">
            <input type="password" class="control-text" name="oldPassword" id="oldPassword" data-rules="{required:true}">
          </div>
        </div>
      </div>
      <div class="row">
      	<div class="control-group span7">
          <label class="control-label">新密码：</label>
          <div class="controls" >
            <input type="password" class="control-text" name="newPassword" id="newPassword" data-rules="{required:true}">
          </div>
        </div>
        </div>
       <div class="row">
        <div class="control-group span7">
          <label class="control-label">确认密码：</label>
          <div class="controls" >
            <input type="password" class="control-text" name="newPasswordEqualTo" id="newPasswordEqualTo" data-rules="{equalTo:'#newPassword',required:true}">
          </div>
        </div>
        </div>
        <div class="row form-actions actions-bar">
            <div class="span13 offset3 ">
              <button type="button" id="submit" class="button button-primary">修改</button>
            </div>
        </div>
      </form>
</div>
    <script type="text/javascript">
    BUI.use('common/page');
    BUI.use(['bui/form'],function (Form) {
        var form = new Form.HForm({
            srcNode : '#save_Form',
            submitType : 'ajax',
            callback : function(data) {
                if(data.success){
                	BUI.Message.Alert('修改成功');
                	$('#oldPassword').val('');
                	$('#newPassword').val('');
                	$('#newPasswordEqualTo').val('');
                }else{
                	BUI.Message.Alert('修改失败');
                }
             }
          }).render();
        
        $('#submit').click(function(){form.submit();});
      });
    
</script>
</body>
</body>
</html>
