package com.rbao.east.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.rbao.east.common.Constants;

public final class GeneratedQrCodeUtil {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;


	/**
	 * 
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}
	
	/**
	 * 生成二维码 
	 * @param text 需要生成的二维码的内容
	 * @param width 二维码宽度
	 * @param height 二维码高度
	 * @return 生成的二维码路径
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String generatedQrCode(String text,int width,int height){
		/** 构建图片保存的目录 **/
		String filePathDir = UploadUtils.getRelatedPath();
		
		/** 得到图片保存目录的真实路径 **/
		String fileRealPathDir = UploadUtils.getRealPath();
	
		/** 获取文件的后缀 **/
		String suffix = "jpeg";
		
		 /**使用UUID生成文件名称**/
		String fileImageName = UUID.randomUUID().toString() + "."+suffix;// 构建文件名称
		
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = fileRealPathDir + File.separator + fileImageName;
		
		String resultFilePath = filePathDir + "/" + fileImageName;
		
		// 二维码的图片格式
		HashMap hints = new HashMap();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, Constants.UTF8);
		
		// 因过滤器 XssHttpServletRequestWrapper将全部半角字符替换成了全角字符如 “:” 会被替换成“：”需要注意  如有需要可自行替换
		//	text = text.replace("：", ":");
		try {
			File newFile = new File(fileName);

			BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
					BarcodeFormat.QR_CODE, width, height, hints);
			// 生成二维码
			writeToFile(bitMatrix, suffix, newFile);
			
			ImageUtils.Thumbnail(fileName, fileRealPathDir + File.separator
					+ "mid_" + fileImageName, 320, 240, 100);	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (StringUtils.isNotBlank(resultFilePath)) {
			resultFilePath = resultFilePath.replaceAll("\\\\", "/");
		}
		// 返回文件保存路径
		return resultFilePath;
	}
}