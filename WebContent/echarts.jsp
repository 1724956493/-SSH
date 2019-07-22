<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/echarts.common.min.js"></script>
<script type="text/javascript" src="js/layer.js"></script>
</head>
<body>
<div id ='main' style="width: 2400px;height:600px;"></div>
<script type="text/javascript">

$(document).ready(function(){  
    var chart = document.getElementById('main');  
    var chartData = echarts.init(chart);  

    chartData.setOption({  
        title: {  
            text: '异步数据加载示例'  
        },  
        tooltip: {},  
        legend: {  
        	 data:['销量','访问数']
        },  
        xAxis: {  
            data: []  ,
        },  
        yAxis: {},  
        series: [{  
            name: '销量',  
            type: 'bar',  
            data: [] 
        },{  
            name: '访问数',  
            type: 'line', 
            data: []
        }]  
    });  
      
    $.get('data.json').done(function (data) {  
          
        console.dir(data);  
        // 填入数据  
        chartData.setOption({  
            xAxis: {  
                data: data.categories  
            },  
            series: [{  
                name: '销量',  
                data: data.data 
            },{  
                name: '访问数',  
                data: data.data2
            }]  
        });  
      
    });  
      
    function eConsole(param)   
    {  
    	layer.msg(param.name+"的"+param.seriesName+"是"+param.value);
    	console.dir(param);
    }  
      
    chartData.on("click",eConsole);  
});  
</script>
</body>
</html>