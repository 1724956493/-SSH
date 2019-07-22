<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <title>上传文件</title>  
  </head>  
  <body>  
  <!-- enctype="multipart/form-data:指定对上传表单项进行URL编码 -->  
    <form action="./json/upload_uploadFileUtils" method="post" enctype="multipart/form-data">  
        <table align="center" border="1">  
                <caption><h2>上传</h2></caption>  
                <tr>  
                    <td>上传用户名：</td>  
                    <td><input type="text" name="username"/></td>  
                </tr><tr>  
                    <td>上传文件：</td>  
                    <td><input type="file" name="file"/></td>  
                </tr>  
                <tr align="center">  
                    <td colspan="2"><input type="submit" value="上传"/></td>  
                </tr>  
        </table>  
    </form>  
  </body>  
</html>  