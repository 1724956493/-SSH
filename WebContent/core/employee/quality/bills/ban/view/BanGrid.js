Ext.define("core.employee.quality.bills.ban.view.BanGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.BanGrid',
			autoScroll :true,
			minheight:250,
			store:"core.employee.quality.bills.ban.store.BanStore",
				
		columns: [            
		   	  {xtype: 'rownumberer',minWidth: 30},
		   	  {hidden: true, dataIndex: 'uuid'},
              {text: '单据号', dataIndex: 'billcode', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '事故标题', dataIndex: 'billhead', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '项目号', dataIndex: 'project', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '项目对象', dataIndex: 'projectobj', align: 'center'},
              {text: '主责部门', dataIndex: 'dept', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '外协单位', dataIndex: 'wbdept', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '发生日期', dataIndex: 'createdate', align: 'center'},
              {text: '整改意见书编号', dataIndex: 'panding', align: 'center'},
              {text: '上传时间', dataIndex: 'ts', align: 'center'},
              {text: '周', dataIndex: 'diaocha', align: 'center',items: { xtype: 'GridSearchText'}},
              {text:'审核状态',dataIndex:'appstatus',align:'center',renderer:function(val,metadata){
	            	if(val == '1'){
	          			 return '<span style="color:blue">' + '审核通过' + '</span>'; 
	          		}else if(val == '2'){
	          		     return '<span style="color:green">' + '通报不处罚' + '</span>';
	          		}else if(val == '3'){
	          		     return '<span style="color:red">' + '审核不通过' + '</span>';
	          		}else{
	          		     return '未审核';
	          		}
              }},
              {text: '文档上传', dataIndex: 'scanfilename', align: 'center',renderer:function(val,metadata){
            	  if(val!=null){
           			 return "<a href="+"./upload/pdf/"+val+" target="+"_blank"+">"+val+"</a>";
           		}else{
           			return null;
           		}         
              }},
              {text: '操作人', dataIndex: 'operator', align: 'center',items: { xtype: 'GridSearchText'}}
            ],
			
		initComponent : function() {		
			this.callParent(arguments);
		}
			
})