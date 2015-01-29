<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="utf-8">
    <title>用户</title>
</head>
<body>
<div class="container">
  <div class="row">
    <form id="searchForm" class="form-horizontal">
      <div class="row">
        <div class="control-group span8">
          <label class="control-label">用户名：</label>
          <div class="controls">
            <input type="text" class="control-text" name="search_LIKE_loginName">
          </div>
        </div>
        <div class="control-group span8">
          <label class="control-label">姓名：</label>
          <div class="controls" >
            <input type="text" class="control-text" name="search_LIKE_userName">
          </div>
        </div>
        <div class="control-group span8">
          <label class="control-label">电子邮箱：</label>
          <div class="controls" >
            <input type="text" class="control-text" name="search_EQ_email" data-rules="{email:true}" >
          </div>
        </div>
      </div>
      <div class="row">
        <div class="control-group span8">
          <label class="control-label">用户状态：</label>
          <div class="controls">
            <select id="" name="search_EQ_userStatus">
              <option value="">所有</option>
              <option value="enable">启用</option>
              <option value="disable">禁用</option>
            </select>
          </div>
        </div>
        <div class="control-group span8">
          <label class="control-label">用户组：</label>
          <div class="controls">
            <input type="hidden" id="grouptid" name="search_EQ_group.tid">
			<input type="text" id="groupgroupName" class="control-text" readonly="readonly" name="group.groupName">
          </div>
        </div>
        <div class="span3 offset2">
          <button  type="button" id="btnSearch" class="button button-primary">搜索</button>
        </div>
      </div>
    </form>
    </div>
<div class="search-grid-container">
<div id="grid"></div>
</div>
</div>
<div id="content" class="hide">
      <form id="save_Form" class="form-horizontal" action="${ctx}/user/save" method="post">
        <input type="hidden" name="tid" value="">
        <input type="hidden" id="isNew" name="isNew" value="">
        <div class="row">
        <div class="control-group span7">
          <label class="control-label">用户名：</label>
          <div class="controls">
            <input type="text" class="control-text" name="loginName" data-rules="{required:true}" >
          </div>
        </div>
        <div class="control-group span7">
          <label class="control-label">姓名：</label>
          <div class="controls" >
            <input type="text" class="control-text" name="userName" data-rules="{required:true}" >
          </div>
        </div>
      </div>
      <div class="row">
      	<div class="control-group span7">
          <label class="control-label">电子邮箱：</label>
          <div class="controls" >
            <input type="text" class="control-text" name="email" data-rules="{email:true,required:true}">
          </div>
        </div>
        <div class="control-group span7">
          <label class="control-label">用户状态：</label>
          <div class="controls">
            <select name="userStatus">
              <option value="enable">启用</option>
              <option value="disable">禁用</option>
            </select>
          </div>
        </div>
        </div>
       <div class="row">
        <div class="control-group span7">
          <label class="control-label">用户组：</label>
          <div class="controls">
          	<input type="hidden" id="savegrouptid" name="group.tid">
			<input type="text" id="savegroupgroupName" data-rules="{required:true}" class="control-text" readonly="readonly" name="group.groupName">
          </div>
        </div>
        </div>
      </form>
    </div>
    <script type="text/javascript">
    BUI.use('common/page');
    BUI.use(['common/search','bui/data','bui/tree','bui/extensions/treepicker','bui/overlay','bui/form'],function (Search,Data,Tree, TreePicker,Overlay,Form) {
        var enumObj = {"enable":"启用","disable":"禁用"},
          columns = [
              {title : '用户名',dataIndex :'loginName', width:100,renderer:function(value,obj){
            	  return Search.createLink({
                      id : 'userInfo' + obj.tid,
                      title : '用户信息',
                      text : value,
                      href : 'detail/example.html'
                    });
              }},
              {title : '姓名',dataIndex :'userName', width:100},
              {title : '电子邮箱',dataIndex : 'email',width:200},
              {title : '用户状态',dataIndex : 'userStatus',width:100,renderer:BUI.Grid.Format.enumRenderer(enumObj)},
              {title : '用户组',dataIndex : 'groupName',width:100},
              {title:'操作',dataIndex:'',width:200,renderer : function(value,obj){
                
                  editStr = '<span class="grid-command btn-edit" title="编辑用户信息">编辑</span>',
                  delStr = '<span class="grid-command btn-del" title="删除用户信息">删除</span>';//页面操作不需要使用Search.createLink
                return editStr + delStr;
              }}
            ],
          store = Search.createStore('${ctx}/user/search',{
              root:'data',
              totalProperty : 'total',
              pageSize:15,
              sortInfo : {
                  field : 'tid',
                  direction : 'DESC' //升序ASC，降序DESC
                },  
                remoteSort: true,// 开启异步排序
              proxy : {
            	  method : 'POST'
                },
                autoSync : true //保存数据后，自动更新
              }),
          gridCfg = Search.createGridCfg(columns,{
            tbar : {
              items : [
                {text : '<i class="icon-plus" style="margin-top: 3px;margin-right:5px;"></i>新建',btnCls : 'button',handler:addFunction},
                {text : '<i class="icon-trash" style="margin-top: 3px;margin-right:5px;"></i>删除',btnCls : 'button',handler : delFunction}
              ]
            },
            plugins : [BUI.Grid.Plugins.CheckSelection,BUI.Grid.Plugins.AutoFit] // 插件形式引入多选表格
          });
        store.on('exception',function (ev) {
            BUI.Message.Alert('请数据错误~');
          });
        var  search = new Search({
            store : store,
            gridCfg : gridCfg
          }),
          grid = search.get('grid');
        
        var form = new Form.HForm({
            srcNode : '#save_Form',
            submitType : 'ajax',
            callback : function(data) {
                if(data.success){
                	BUI.Message.Alert('保存成功');
                	dialog.close();
                	store.load();
                }else{
                	BUI.Message.Alert('保存失败');
                }
             }
          }).render();
        var dialog = new Overlay.Dialog({
            title:'用户信息编辑',
            width:600,
            height:170,
            //配置DOM容器的编号
            contentId:'content',
            buttons:[
                     {
                       text:'保存',
                       elCls : 'button button-primary',
                       handler : function(){
                         //do some thing
                    	   form.submit();
                       }
                     },{
                       text:'取消',
                       elCls : 'button',
                       handler : function(){
                    	   dialog.close();
                       }
                     }
                   ]
          });
        var treeStore = new Data.TreeStore({
			pidField : 'pid', //设置pid的字段名称
			root : {
	            id : '0',
	            text : '0',
	            checked : false
	          },
			url : '${ctx}/group/search',
			map : {
				groupName : 'text',
				tid:'id'
			},
			autoLoad : true
		});
        treeStore.on('exception',function (ev) {
            BUI.Message.Alert('网络异常~');
          });
    	new TreePicker({
			trigger : '#groupgroupName',  
	        valueField : '#grouptid', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
	        width:150,  //指定宽度
	        children : [new Tree.TreeList({
				idField : 'id',
				store : treeStore
			})] //配置picker内的列表
	      }).render();
    	
        var saveTreePicker = new TreePicker({
	        trigger : '#savegroupgroupName',  
	        valueField : '#savegrouptid', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
	        width:150,  //指定宽度
	        children : [new Tree.TreeList({
				idField : 'id',
				store : treeStore
			})] //配置picker内的列表
	      }).render();
        
        function addFunction(){
        	dialog.show();
        	saveTreePicker.setSelectedValue("");
        	form.set('initRecord',{});
        	$('#isNew').val('true');
        	var	loginNameField = form.getField('loginName');
        	loginNameField.set('remote',{
               url : '${ctx }/user/checkLoginName',
               dataType:'json',//默认为字符串
               callback : function(data){
                 if(data.success){
                  return '';
                 }else{
                  return data.msg;
                 }
               }
            });
         }
        
        //删除操作
        function delFunction(){
          var selections = grid.getSelection();
          delItems(selections);
        }
     
        function delItems(items){
          var ids = [];
          BUI.each(items,function(item){
            ids.push(item.tid);
            ids.push(item.tid);
          });
     		
          if(ids.length){
        	  BUI.Message.Confirm('确认要删除选中的记录么？',function(){
        		  $.ajax( {
        			      url:'${ctx}/user/delete',// 跳转到 action 
        			      traditional:true,
        			      data:{'idList':ids},  
        			      type:'post',  
        			      cache:false,  
        			      dataType:'json',
        			      success:function(data) {  
        			          if(data.success ==true ){  
        			        	  BUI.Message.Alert('删除成功');
        			        	  store.load();
        			          }else{  
        			        	  BUI.Message.Alert('删除失败');
        			          }  
        			       },  
        			       error : function() {  
        			            BUI.Message.Alert('删除失败');
        			       }  
        			  });
                },'question');
          }
        }
        
        function editItem(item){
            form.set('initRecord',item);
        	dialog.show();
        	saveTreePicker.setSelectedValue("");
        	saveTreePicker.setSelectedValue(""+item.groupID);
        	$('#isNew').val('false');
        	var	loginNameField = form.getField('loginName');
        	loginNameField.set('remote',null);
        }
     
        //监听事件，删除一条记录
        grid.on('cellclick',function(ev){
          var sender = $(ev.domTarget); //点击的Dom
          if(sender.hasClass('btn-del')){
            var record = ev.record;
            delItems([record]);
          }else if(sender.hasClass('btn-edit')){
        	  var record = ev.record;
        	  editItem([record][0]);
          }
        });
      });
    
</script>
</body>
</body>
</html>
