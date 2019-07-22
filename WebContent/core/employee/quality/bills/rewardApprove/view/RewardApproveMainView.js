Ext.define("core.employee.quality.bills.rewardApprove.view.RewardApproveMainView", {
			extend:"Ext.form.Panel",
			title:'奖励报告审批',
			alias : 'widget.RewardApproveMainView',	
			frame:true,
			layout:'border',
			
			tbar:[
				{xtype: 'button', text: '查询',ref:'search'}	,
				{xtype: 'button', text: '预览',ref:'review'},
				{xtype: 'button', text: '审批',ref:'approve'},
				{xtype: 'button', text: '上传',ref:'upload'}
			],	

			
			items:[
				{xtype:'RewardApproveGrid',width:'100%',height:'60%',region:'center'},
			],
						
			initComponent : function() {
				this.callParent(arguments);
			}
			
})