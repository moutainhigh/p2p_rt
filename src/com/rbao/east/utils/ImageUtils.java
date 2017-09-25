package com.rbao.east.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtils {
	// 缩略图
	public static void Thumbnail(String infile, String outfile, int width,
			int height, int quality) throws IOException, InterruptedException {

		BufferedImage thumbImage = null;
		BufferedOutputStream out = null;
		Image image = null;
		image = Toolkit.getDefaultToolkit().createImage(infile);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image, 0);
		mediaTracker.waitForID(0);
		int thumbWidth = width;
		int thumbHeight = height;
		double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		double imageRatio = (double) imageWidth / (double) imageHeight;
		if (thumbRatio < imageRatio) {
			thumbHeight = (int) (thumbWidth / imageRatio);
		} else {
			thumbWidth = (int) (thumbHeight * imageRatio);
		}
		thumbImage = new BufferedImage(thumbWidth, thumbHeight,
				BufferedImage.SCALE_SMOOTH);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
		out = new BufferedOutputStream(new FileOutputStream(outfile));
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
		quality = Math.max(0, Math.min(quality, 100));
		param.setQuality((float) quality / 100.0f, false);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(thumbImage);
		out.close();
		thumbImage = null;
		out = null;
		image = null;
	}

	/**
	 * 给图片添加水印、可设置水印图片旋转角度
	 * 
	 * @param iconPath
	 *            水印图片路径
	 * @param srcImgPath
	 *            源图片路径
	 * @param targerPath
	 *            目标图片路径
	 * @param degree
	 *            水印图片旋转角度
	 * @param width
	 *            宽度(与左相比)
	 * @param height
	 *            高度(与顶相比)
	 * @param clarity
	 *            透明度(小于1的数)越接近0越透明
	 */
	public static void waterMarkImageByIcon(String iconPath, String srcImgPath,
			String targerPath, Integer degree, Integer width, Integer height,
			float clarity) {
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
					srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			// 得到画笔对象
			// Graphics g= buffImg.getGraphics();
			Graphics2D g = buffImg.createGraphics();
			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(
					srcImg.getScaledInstance(srcImg.getWidth(null),
							srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
					null);
			if (null != degree) {
				// 设置水印旋转
				g.rotate(Math.toRadians(degree),
						(double) buffImg.getWidth() / 2,
						(double) buffImg.getHeight() / 2);
			}
			// 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = new ImageIcon(iconPath);
			// 得到Image对象。
			Image img = imgIcon.getImage();
			float alpha = clarity; // 透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 表示水印图片的位置
			g.drawImage(img, width, height, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			os = new FileOutputStream(targerPath);
			// 生成图片
			ImageIO.write(buffImg, "JPG", os);
		} catch (Exception e) {
			
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				
			}
		}
	}

	/**
	 * 给图片添加水印、可设置水印图片旋转角度
	 * 
	 * @param logoText
	 *            水印文字
	 * @param srcImgPath
	 *            源图片路径
	 * @param targerPath
	 *            目标图片路径
	 * @param degree
	 *            水印图片旋转角度
	 * @param width
	 *            宽度(与左相比)
	 * @param height
	 *            高度(与顶相比)
	 * @param clarity
	 *            透明度(小于1的数)越接近0越透明
	 */
	public static void waterMarkByText(String logoText, String srcImgPath,
			String targerPath, Integer degree, Integer width, Integer height,
			Float clarity) {
		// 主图片的路径
		InputStream is = null;
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
					srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			// 得到画笔对象
			// Graphics g= buffImg.getGraphics();
			Graphics2D g = buffImg.createGraphics();
			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(
					srcImg.getScaledInstance(srcImg.getWidth(null),
							srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
					null);
			if (null != degree) {
				// 设置水印旋转
				g.rotate(Math.toRadians(degree),
						(double) buffImg.getWidth() / 2,
						(double) buffImg.getHeight() / 2);
			}
			// 设置颜色
			g.setColor(Color.red);
			// 设置 Font
			g.setFont(new Font("宋体", Font.BOLD, 30));
			float alpha = clarity;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .
			g.drawString(logoText, width, height);
			g.dispose();
			os = new FileOutputStream(targerPath);
			// 生成图片
			ImageIO.write(buffImg, "JPG", os);
		} catch (Exception e) {
			
		} finally {
			try {
				if (null != is)
					is.close();
			} catch (Exception e) {
				
			}
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				
			}
		}
	}

	public static void main(String[] args) {
		String iconPath = "d:/22.jpg";
		String srcImgPath = "d:\\2.jpg";
		String targerPath = "d:/3.jpg";
		// 给图片添加水印
		ImageUtils.waterMarkImageByIcon(iconPath, srcImgPath, targerPath, 0, 0,
				0, 1.0f);
		// 给图片添加水印,水印旋转-45
		// ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath,
		// targerPath2, -45);
	}
}
