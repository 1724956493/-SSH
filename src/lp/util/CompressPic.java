package lp.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CompressPic {
	// base64图片转成InputStream流对象。需要一个参数，base64编码的字符串，可由步骤一得到。

	public static String compressByBase64String(String base64string) {

		ByteArrayInputStream stream = null;
		BufferedImage image = null;
		ByteArrayOutputStream baos = null;
		try {

			BASE64Decoder decoder = new BASE64Decoder();
			BASE64Encoder encoder = new BASE64Encoder();
			baos = new ByteArrayOutputStream();
			byte[] bytes1 = decoder.decodeBuffer(base64string);

			stream = new ByteArrayInputStream(bytes1);
			image = ImageIO.read(stream);
			File toPic = new File("G:/2.jpg");
			Thumbnails.of(image).scale(1f).toFile(toPic);
			//image = Thumbnails.of(image).size(400, 500).asBufferedImage();
			image =Thumbnails.of(image).scale(1f).asBufferedImage();
			ImageIO.write(image, "png", baos);
			byte[] bytes =baos.toByteArray();
			return encoder.encodeBuffer(bytes).trim();
		} catch (Exception e) {

			// TODO: handle exception

		}

		return null;

	}

}
