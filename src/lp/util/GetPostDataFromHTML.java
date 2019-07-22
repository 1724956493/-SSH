package lp.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;

public class GetPostDataFromHTML {

	public static String getData(String url) {
		URL urlInstence;
		try {
			urlInstence = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlInstence.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			InputStream inStream = conn.getInputStream();
			byte[] data = StreamTool.inputStream2Byte(inStream);
			String result = new String(data, "UTF-8");
			return result;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public static String postData(String url, String postData) {
		URL urlInstence;
		try {
			urlInstence = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) urlInstence.openConnection();
			// 设置参数
			httpConn.setDoOutput(true); // 需要输出
			httpConn.setDoInput(true); // 需要输入
			httpConn.setUseCaches(false); // 不允许缓存
			httpConn.setRequestMethod("POST"); // 设置POST方式连接
			// 设置请求属性
			httpConn.setRequestProperty("Content-Type", "application/json");
			httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpConn.setRequestProperty("Charset", "UTF-8");
			// 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
			httpConn.connect();
			// 建立输入流，向指向的URL传入参数
			DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
			dos.writeBytes(postData);
			dos.flush();
			dos.close();
			// 获得响应状态
			int resultCode = httpConn.getResponseCode();
			if (HttpURLConnection.HTTP_OK == resultCode) {
				StringBuffer sb = new StringBuffer();
				String readLine = new String();
				BufferedReader responseReader = new BufferedReader(
						new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				while ((readLine = responseReader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				responseReader.close();
				 System.out.println(sb.toString());
				return sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * public static void main(String[] args) { Json json=new Json(); User
	 * user=new User(); user.setId("555");
	 * 
	 * String m=JSON.toJSONString(user);
	 * postData("http://172.16.118.125:8088/sshe/userAction!add.action",m); }
	 */

	/*
	 * // TEMPLE实例 将json格式的String转换成Bean Json json
	 * =JSON.parseObject(result,Json.class); if
	 * (!TextUtils.isEmpty((CharSequence) json.getObj())) { List<User> list =
	 * JSON.parseArray(json.getObj().toString(), User.class); Toast.makeText(
	 * WelcomeActivity.this, list.get(0).getId()+"和"+list.get(1).getId(),
	 * Toast.LENGTH_SHORT).show(); } else { Toast.makeText(
	 * WelcomeActivity.this, "没有数据", Toast.LENGTH_SHORT).show(); }
	 */
	public static String postData2(String url, String postData) {
		URL urlInstence;
		try {
			urlInstence = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlInstence.openConnection();
			conn.setConnectTimeout(5000);
			// 设置允许输出
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			// 设置User-Agent: Fiddler
			conn.setRequestProperty("ser-Agent", "Fiddler");
			// 设置contentType
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(postData.getBytes("utf-8"));//编码格式必须要统一为UTF-8
			os.close();
			// 服务器返回的响应码
			// int code = conn.getResponseCode();
			String tempLine = null;
			String result = "";
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) {
				result = result + tempLine.toString();
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}
}
