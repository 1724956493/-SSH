Ext.define("core.employee.quality.xunjian.view.QualityGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.QualityGrid',
			autoScroll :true,
			store:"core.employee.quality.xunjian.store.QualityStore",
		    columns: [            
		   	  {xtype: 'rownumberer',minWidth: 50},
		   	  {text:'序列号',dataIndex:'uuidrp',hidden:true},
              { text: '项目号', dataIndex: 'project', align: 'center',items: { xtype: 'GridSearchText'}},
              { text: '一卡通号', dataIndex: 'yktcode', align: 'center',items: { xtype: 'GridSearchText'}},
              { text: '姓名', dataIndex: 'psnname', align: 'center' ,items: { xtype: 'GridSearchText'}},
              { text: '所属部门', dataIndex: 'empdept', align: 'center', minWidth: 80,items: { xtype: 'GridSearchText'}},
              { text: '工作部门', dataIndex: 'empuserdept', align: 'center' ,items: { xtype: 'GridSearchText'}},                        
              { text: '主管领导', dataIndex: 'empleader', items: { xtype: 'GridSearchText'}},
              { text: '日期', dataIndex: 'create_time', align: 'center' ,minWidth: 120 },
              { text: '检查部门', dataIndex: 'dept', align: 'center',items :{xtype:'GridSearchText'}},
              { text: '检查人', dataIndex: 'operate',items    : {xtype: 'GridSearchText'}},
              { text: '是否违规', dataIndex: 'status',renderer:function(val,metadata){
               		if(val =='违规'){
              			 return '<span style="color:red">' + val + '</span>';
             //  			metadata.style = 'background-color:#CCFFFF;'; 
              		}else{
              		     return '<span style="color:green">' + val + '</span>';
              		}
              }},
              { text: '违规项', dataIndex: 'bonus', align: 'left'},
              { text: '备注', dataIndex: 'cknote', align: 'left'},
              { text: '是否缴费关闭', dataIndex: 'paystatus',renderer:function(val,metadata){
               		if(val == 0){
              			 return '<span style="color:red">' + '未缴费关闭' + '</span>';
             //  			metadata.style = 'background-color:#CCFFFF;'; 
              		}else{
              		     return '<span style="color:green">' + '已缴费关闭' + '</span>';
              		}
              }},
              { text: '图片',width:150, align: 'center', dataIndex: 'image',renderer:function(val){
              		if(val!=null){
              			 return "<a href="+"./upload/"+val+" target="+"_blank"+">"+val+"</a>";
              		}else{
              			return null;
              		}
              }}
              ],

/*        features: [{
            id: 'group',
            ftype: 'grouping',
            groupHeaderTpl:  Ext.create('Ext.XTemplate','检查人:{name}:共{rows.length}条记录,有{[this.countaa(values)]}种错误',{
            			countaa:function(values){
            				var n =[];
            				for(var i =0 ;i <values.children.length;i++){
            						if (n.indexOf(values.children[i].get("bonus")) == -1) {
            							n.push(values.children[i].get("bonus"));}
            				}          	
            				return n.length
            			}           	
            }),
            hideGroupedHeader: true,
            enableGroupingMenu: false,
            startCollapsed :true
        }],*/
			
		initComponent : function() {
				this.callParent(arguments);}
			
})