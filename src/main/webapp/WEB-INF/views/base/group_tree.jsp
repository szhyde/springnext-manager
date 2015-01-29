<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../layouts/taglib.jsp"%>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="utf-8">
<title>组织结构</title>
<style type="text/css">
.test-menu {
	border: none;
}
</style>
</head>
<body>
	<div class="row" style="padding: 10px;">
		<div id="group_tree" class="panel span6"></div>
		<div id="group_info" class="panel span16">
			<form id="group_form" class="form-horizontal" action="${ctx }/group/save" method="post">
				<div class="row actions-bar" style="margin-left: 0px;margin-top: 0px;background-color: inherit;border-top: none;border-bottom: 1px solid #c3c3d6">
					    <div id="bar">
					          
					    </div>
			    </div>
				<div class="row">
					<div class="control-group span8">
						<label class="control-label">父级：</label>
						<div class="controls">
							<input type="hidden" id="isNew" name="isNew" value="">
							<input type="hidden" id="parenttid" name="parent.tid">
							<input type="text" id="parentgroupName" class="control-text disabled" name="parent.groupName" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="control-group span8">
						<label class="control-label">名称：</label>
						<div class="controls">
							<input type="hidden" id="tid" name="tid">
							<input type="text" class="control-text" id="groupName" name="groupName" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="row actions-bar" style="margin-left: 0px;background-color: inherit;">       
	                <div class="form-actions span13 offset3">
	                  <button type="button" id="saveBtn" class="button" disabled="disabled">保存</button>
	                  <button type="button" id="cancelBtn" class="button" disabled="disabled">取消</button>
	                </div>
            	</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		BUI.use([ 'bui/tree', 'bui/data','bui/toolbar','bui/extensions/treepicker','bui/form'], function(Tree, Data,Toolbar,TreePicker,Form) {
			var store = new Data.TreeStore({
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
			$('#group_tree').height($(this).height()-30);
			$('#group_info').height($(this).height()-30);
			$('#group_info').width($(this).width()-280);
			group_info
			//由于这个树，不显示根节点，所以可以不指定根节点
			var tree = new Tree.TreeList({
				render : '#group_tree',
				elCls : 'test-menu',
				idField : 'tid',
				store : store
			});
			tree.render();
			tree.on('itemclick',function(ev){
			        var item = ev.item;
			        
			        $('#parentgroupName').attr('disabled',true);
	            	$('#groupName').attr('disabled',true);
	            	$('#saveBtn').attr('disabled',true);
	            	$('#cancelBtn').attr('disabled',true);
			        
			        $('#tid').val(item.id);
			        $('#groupName').val(item.text);
			        
			        picker.setSelectedValue(item.pid);
			 });
			var  picker = new TreePicker({
		        trigger : '#parentgroupName',  
		        valueField : '#parenttid', //如果需要列表返回的value，放在隐藏域，那么指定隐藏域
		        width:150,  //指定宽度
		        children : [new Tree.TreeList({
					idField : 'id',
					store : store
				})] //配置picker内的列表
		      }).render();
			var form = new Form.HForm({ //创建表单
		        srcNode : '#group_form',
		        submitType : 'ajax',
		        callback : function(data){
		          if(data.success){
		              $('#parentgroupName').attr('disabled',true);
		            	$('#groupName').attr('disabled',true);
		            	$('#saveBtn').attr('disabled',true);
		            	$('#cancelBtn').attr('disabled',true);
		            	
		            	var root = store.get('root');
		            	store.reloadNode(root);
				        
		            }else{ //编辑数据
		              var record = this.serializeToObject();
		              store.update(record,true); //第二个参数标示使用匹配函数
		            }
		          }
		      }).render();
			var bar = new Toolbar.Bar({
	            render : '#bar',
	            elCls: 'toolbar',
	            children : [
	              {
	                xtype:'button',
	                btnCls : 'button ',
	                text:'<i class="icon-plus" style="margin-top: 3px;margin-right:5px;"/>增加',
	                listeners : {
	                  'click':function(event){
	                   		//$('#parenttid').val($('#tid').val());
	                   		//$('#parentgroupName').val($('#groupName').val());
	                   		$('#parentgroupName').attr('disabled',false);
	                   		$('#groupName').attr('disabled',false);
	                   		$('#saveBtn').attr('disabled',false);
	                   		$('#cancelBtn').attr('disabled',false);
	                   		
	                   		picker.setSelectedValue($('#tid').val());
	                   		$('#isNew').val('true');
	                   		$('#tid').val('');
	                   		$('#groupName').val('');
	                   		$('#tid').attr('disabled',true);
	                   		
	                  }
	                }
	              },
	              {
	                xtype:'separator',
	              },
	              {
	                xtype:'button',
	                btnCls : 'button ',
	                text:'<i class="icon-pencil" style="margin-top: 3px;margin-right:5px;"/>修改',
	                handler:function(event){
	                	$('#parentgroupName').attr('disabled',false);
                   		$('#groupName').attr('disabled',false);
                   		$('#saveBtn').attr('disabled',false);
                   		$('#cancelBtn').attr('disabled',false);
                   		$('#tid').attr('disabled',false);
                   		$('#isNew').val('false');
	                }
	                
	              },{
	                xtype:'separator'
	              },
	              {
	                xtype:'button',
	                btnCls : 'button ',
	                text:'<i class="icon-trash" style="margin-top: 3px;margin-right:5px;"/>删除',
	                handler:function(event){
	                	BUI.Message.Confirm('确认要删除'+$('#groupName').val()+'么？',function(){
	                		$.ajax( {
	          			      url:'${ctx}/group/delete/'+$('#tid').val(),// 跳转到 action 
	          			      traditional:true,
	          			      type:'post',  
	          			      cache:false,  
	          			      dataType:'json',
	          			      success:function(data) {  
	          			          if(data.success ==true ){  
	          			        	  BUI.Message.Alert('删除成功');
		          			          var root = store.get('root');
		        		              store.reloadNode(root);
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
	            ]
	          });
	          bar.render();
	          
			$("#cancelBtn").on("click", function(){
	        	$('#parentgroupName').attr('disabled',true);
            	$('#groupName').attr('disabled',true);
            	$('#saveBtn').attr('disabled',true);
            	$('#cancelBtn').attr('disabled',true);
            	
            	var item = tree.getSelected();
            	$('#tid').val(item.tid);
		        $('#groupName').val(item.text);
		        
		        picker.setSelectedValue(item.pid);
			});
			
			$("#saveBtn").on("click", function(){
				if($('#tid').val()==$('#parenttid').val()){
					BUI.Message.Alert('父级选择错误~');
					return;
				}
				form.submit();
			});
			
		}); 
	</script>
</body>
</body>
</html>
