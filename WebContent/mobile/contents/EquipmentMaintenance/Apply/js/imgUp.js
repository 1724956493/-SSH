var imgUrl=null;
$(function() {

	$(".up-section").hide();
	var delParent;
	var defaults = {
		fileType: ["jpg", "png", "bmp", "jpeg"], // 上传文件的类�?
		fileSize: 1024 * 1024 * 1 // 上传文件的大�? 10M
	};
	/*点击图片的文本框*/
	$(".file").change(function() {
		$(".up-section").show();
		var idFile = $(this).attr("id");
		var file = document.getElementById(idFile);
		var imgContainer = $(this).parents(".z_photo"); //存放图片的父亲元� 
		var fileList = file.files; //获取的图片文�?
		
		var input = $(this).parent(); //文本框的父亲元素
		//var imgArr = [];
		//遍历得到的图片文�?
		/*fileList = validateUp(fileList);*/
		
			imgUrl = window.URL.createObjectURL(fileList[0]);
			
			$(".up-img").attr('src',imgUrl);
		
		setTimeout(function() {
			$(".up-section").removeClass("loading");
			$(".up-img").removeClass("up-opcity");
			 var image = new Image(); 
	            image.src = imgUrl; 
	            image.onload = function() { 	
	            var imgData = getBase64Image(image); 
	           	getsubmitData.image=imgData;
	             } 
		}, 450);
		
		$(".close-upimg").on("click", function(event) {
				event.preventDefault();
				event.stopPropagation();
				$(".up-img").attr('src',"");
				$(".file").val("");
				$(".up-section").hide();
				getsubmitData.image=null;
		});
	});
	
	
	
})

    //将图片压缩转成base64 
        function getBase64Image(img) { 
        	
            var canvas = document.createElement("canvas"); 
            
            var width = img.width; 
            var height = img.height; 
            
            // calculate the width and height, constraining the proportions 
           if (width > height) { 
                    height =800*height/width; 
                    width = 800; 
            } else { 
                	width = 800*width/height; 
                    height = 800; 
            } 
            
           
            canvas.width = width;   /*设置新的图片的宽度*/ 
            canvas.height = height; /*设置新的图片的长度*/ 
           
            var ctx = canvas.getContext("2d"); 
            ctx.drawImage(img, 0, 0, width, height); /*绘图*/ 
            var dataURL =  canvas.toDataURL("image/png", 0.8); 
         /*   $(".z_photo").append(canvas);*/
            return dataURL.replace("data:image/png;base64,", "");
            
        }  