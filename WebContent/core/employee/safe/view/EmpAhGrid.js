Ext.define("core.employee.safe.view.EmpAhGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.EmpAhGrid',
	//		minHeight: 400,
			autoScroll :true,
			store:"core.employee.safe.store.EmpAhStore",
			viewConfig: {
	            stripeRows: true,
	            enableTextSelection: true
       		 } ,
		    columns: [            
		   	  {xtype: 'rownumberer',minWidth: 50},
      //        { text: '项目号', dataIndex: 'project', align: 'left'},
		   	  { text: '一卡通号', dataIndex: 'yktcode', align: 'center' },
              { text: '姓名', dataIndex: 'psnname' },
              { text: '部门', dataIndex: 'empdept', align: 'left', minWidth: 80 },
              { text: '工作区域', dataIndex: 'empuserdept', align: 'left' },                        
              { text: '主管领导', dataIndex: 'empleader'},
              { text: '日期', dataIndex: 'create_time'},
              { text: '检查部门', dataIndex: 'dept'},
              { text: '检查人', dataIndex: 'operate'},
              { text: '是否违规', dataIndex: 'status',renderer:function(val,metadata){
               		if(val =='违规'){
              			 return '<span style="color:red">' + val + '</span>';
             //  			metadata.style = 'background-color:#CCFFFF;'; 
              		}else{
              		     return '<span style="color:green">' + val + '</span>';
              		}}},
              { text: '违规项', dataIndex: 'bonus'},
              { text: '备注', dataIndex: 'cknote'},
              { text: '图片',width:150, dataIndex: 'image',renderer:function(val){
              		if(val!=null){
              			 return "<a href="+"./upload/"+val+" target="+"_blank"+">"+val+"</a>";
              		}else{
              			return null;
              		}
              }}
              ],
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
			})