$(function(){
	
	// 监听搜索查询
	layui.form.on('submit(formSearch)', function(data) {
		var table = layui.table;
		table.reload('main_table', {
			where : data.field,
			page : {
				curr : 1
			}
		})
		return false;
	});

	// 监听工具条
	layui.table.on('tool(main_table)', function(obj) {
		tableToolRUD(obj);
	});
});

function tableToolRUD(obj){
	var data = obj.data;
	if (obj.event === 'del') {
		layer.confirm('确定要删除选择的数据吗？', function(index) {
			$.ajax({
				type : "post",
				url : './delete/' + data.tid,
				success : function(json) {
					obj.del();
					layer.close(index);
				}
			});
		});
	} else if (obj.event === 'edit') {
		x_admin_show('修改', './edit/' + data.tid, crudWidth, crudHeight, null, tableReload)
	} else if (obj.event === 'view') {
		x_admin_show('查看', './view/' + data.tid, otherWidth, otherHeight, null, tableReload)
	} else if (obj.event === 'set') {
		x_admin_show('设置', './set/' + data.tid, otherWidth, otherHeight, null, tableReload)
	}
}
//页面主表格重新加载方法
function tableReload() {
	layui.table.reload('main_table');
}

function add(){
	x_admin_show('增加','./add',crudWidth, crudHeight,null,tableReload)
}
// 批量删除
function batchDelete() {
	var checkStatus = layui.table.checkStatus('main_table');
	if (!checkStatus.data || checkStatus.data.length <= 0) {
		layer.msg("请选择要删除的数据行");
		return false;
	}

	layer.confirm('确定要提交选择的数据行吗？', {
		btn : [ '确定', '取消' ],
		title : '删除',
		yes : function(index, layero) { // 删除
			var ids = new Array();
			$.each(checkStatus.data, function(index, d) {
				ids.push(d.tid);
			});
			$.ajax({
				type : "post",
				url : './deleteAll',
				data : {
					"ids[]" : ids
				},
				success : function(json) {
					layer.msg('删除成功', {
						icon : 1
					});
					// 提交成功后的提示
					layui.table.reload('main_table', {
						page : {
							curr : 1
						}
					});

				}
			});
		}
	})
}