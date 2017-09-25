package com.rbao.east.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtils {
	
	public static final String TAG_TENDERS = "%tenderList%";
	
	// 表格的设置
	private static final int spacing = 1;

	// 表格的设置
	private static final int padding = 1;
	
	private static final String OWNERPASSWORD="rb1234p2p"; 

	/**
	 * 创建PDF文件
	 * 
	 * @param file
	 *            临时文件
	 * @return 成功/失败
	 */
	public static boolean createPDFFile(File file,List<String[]> list,String content) {
				
		Document document = new Document(PageSize.A4);
		try {
			// 使用PDFWriter进行写文件操作
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			// 中文字体
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font fontChinese = new Font(bfChinese, 12, Font.BOLD);
			// 创建有colNumber(8)列的表格
			PdfPTable datatable = null;
			if(list != null && list.size()>0){
				datatable = new PdfPTable(list.get(0).length);
				// 定义表格的宽度
		//		int[] cellsWidth = { 5, 4, 4, 4, 5, 5, 5, 5 };
		//		datatable.setWidths(cellsWidth);
				// 表格的宽度百分比
				datatable.setWidthPercentage(100);
				datatable.getDefaultCell().setPadding(padding);
				datatable.getDefaultCell().setBorderWidth(spacing);
				// 设置表格的底色
				datatable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
				datatable.getDefaultCell().setHorizontalAlignment(
						Element.ALIGN_CENTER);
				// 添加表头元素
//				for (int i = 0; i < tableHeader.length; i++) {
//					datatable.addCell(new Paragraph(tableHeader[i], fontChinese));
//				}
				// 添加列表
				for(int j=0;j<list.size();j++){
					String[] tab = list.get(j);
					for(int i=0;i<tab.length;i++){
						datatable.addCell(new Paragraph(tab[i], fontChinese));
					}
				}
			}
			
			if(content.contains(TAG_TENDERS)){
				String[] contents = content.split(TAG_TENDERS);
				Paragraph pf = new Paragraph(contents[0], fontChinese);
		//		pf.setAlignment(Element.ALIGN_CENTER);
		//		pf.setSpacingAfter(10f);
				document.add(pf);	
				if(datatable != null)
					document.add(datatable);
				document.add(new Paragraph(contents[1], fontChinese));
			}else{
				document.add(new Paragraph(content, fontChinese));
			}
			
			document.close();
			return true;
		} catch (Exception e) {
			
			return false;
		}

	}
	/**
	 * 
	* @Title: waterMarkTwo
	* @Description: 两个印章的方法
	* @return void    返回类型
	* @throws
	 */
	public static void waterMarkTwo(String inputFile, String imageFile,String imageFile2,
			String outputFile) {
		try {
			PdfReader reader = new PdfReader(inputFile, "WorldPass".getBytes());// 选择需要印章的pdf
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
					outputFile));// 加完印章后的pdf
			PdfContentByte over = stamp.getOverContent(1);// 设置在第几页打印印章
			Image image = Image.getInstance(imageFile);// 选择图片
			image.setAlignment(1);
			image.scaleAbsolute(220, 220);// 控制图片大小
			image.setAbsolutePosition(100, 520);// 控制图片位置
			over.addImage(image);
			
			PdfContentByte over2 = stamp.getOverContent(7);// 设置在第几页打印印章
			Image image2 = Image.getInstance(imageFile2);// 选择图片
			image2.setAlignment(2);
			image2.scaleAbsolute(220, 220);// 控制图片大小
			image2.setAbsolutePosition(280, 450);// 控制图片位置
			over2.addImage(image2);
			
			stamp.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	public static void waterMark(String inputFile, String imageFile,
			String outputFile) {
		try {
			PdfReader reader = new PdfReader(inputFile, "WorldPass".getBytes());// 选择需要印章的pdf
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
					outputFile));// 加完印章后的pdf
			PdfContentByte over = stamp.getOverContent(1);// 设置在第几页打印印章
			Image image = Image.getInstance(imageFile);// 选择图片
			image.setAlignment(1);
			image.scaleAbsolute(220, 220);// 控制图片大小
			image.setAbsolutePosition(100, 520);// 控制图片位置
			over.addImage(image);
			
			/*PdfContentByte over2 = stamp.getOverContent(7);// 设置在第几页打印印章
			Image image2 = Image.getInstance(imageFile);// 选择图片
			image2.setAlignment(2);
			image2.scaleAbsolute(220, 220);// 控制图片大小
			image2.setAbsolutePosition(280, 450);// 控制图片位置
			over2.addImage(image2);
			*/
			stamp.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	/**
	 * pdf加密
	 * @param inputFile
	 * @param imageFile
	 * @param outputFile
	 * @param password
	 */
	public static void waterMark(String inputFile, String imageFile,
			String outputFile,String password) {
		try {
			PdfReader reader = new PdfReader(inputFile, "WorldPass".getBytes());// 选择需要印章的pdf
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
					outputFile));// 加完印章后的pdf
			// 设置密码
			stamp.setEncryption(PdfWriter.ENCRYPTION_AES_128, password, OWNERPASSWORD, PdfWriter.AllowPrinting);
			PdfContentByte over = stamp.getOverContent(1);// 设置在第几页打印印章  
			Image image = Image.getInstance(imageFile);// 选择图片
			image.setAlignment(1);
			image.scaleAbsolute(220, 220);// 控制图片大小
			image.setAbsolutePosition(200, 300);// 控制图片位置
			over.addImage(image);
			stamp.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}

	}
	

	
	
	/**
     * 汉语中数字大写
     */
    private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆",
            "伍", "陆", "柒", "捌", "玖" };
    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元",
            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
            "佰", "仟" };
    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";
    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";
    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = 2;
    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

    /**
     * 把输入的金额转换为汉语中人民币的大写
     * 
     * @param numberOfMoney
     *            输入的金额
     * @return 对应的汉语大写
     */
    public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        int signum = numberOfMoney.signum();
        // 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        //这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
        // 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }
}




