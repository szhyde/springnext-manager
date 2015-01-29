<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../layouts/taglib.jsp" %>
<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="utf-8">
    <title>个人工作台</title>
</head>
<body>
<div id="J_Layout">
          
</div>
<script type="text/javascript">
        BUI.use(['layout'],function (Layout) {
            var layout = new Layout.Table({
          rows : 4,
          columns : 4
        }),
        control = new BUI.Component.Controller({
          render : '#J_Layout',
          elCls : 'layout-test',
          defaultChildCfg : {
            xclass : 'controller',
            tpl : '<div class="panel">\
              <div class="panel-header">\
                <h3>{title}</h3>\
              </div>\
              <div class="panel-body">\
                <p>{info}</p>\
              </div>\
            </div>'
          },
          children : [
            {
              layout : {
                row : 0
              },
              title : '1',
              info : '第1个'
            },{
              layout : {
                row : 0
              },
              title : '2',
              info : '第2个'
            },{
              layout : {
                row : 0
              },
              title : '3',
              info : '第3个'
            },{
              layout : {
                row : 0,
                rowspan : 4
              },
              title : '4',
              info : '第4个'
            },
 
            {
              layout : {
                row : 1,
                colspan : 2
              },
              title : '5',
              info : '第5个'
            },{
              layout : {
                row : 1
              },
              title : '6',
              info : '第6个'
            },
 
            {
              id:'7',
              layout : {
                row : 2
              },
              title : '7',
              info : '第7个'
            },{
              layout : {
                row : 2,
                colspan : 2,
                rowspan:2
              },
              id : '8',
              title : '8',
              info : '第8个'
            },
 
            {
              id:'9',
              layout : {
                row : 3
              },
              title : '9',
              info : '第9个'
            }
 
          ],
          plugins : [layout]
      });
 
      control.render();
    });
  </script>
</body>
</html>
