package com.rbao.east.utils;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * excel操作类
 * @author LIUTQ
 *
 */
public class ExcelUtil {

	/**
	 * 创建excel文档并写入outStream输出流中
	 * @param outStream
	 * @param mainTitle
	 * @param titles
	 * @param contents
	 */
	/*public final static void buildExcel(OutputStream outStream,
			String mainTitle, String[] titles, List<String[]> contents) {
		int beginRow = 0;
		try {
			*//** **********创建工作簿************ *//*
			WritableWorkbook workbook = Workbook.createWorkbook(outStream);

			*//** **********创建工作表************ *//*
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			*//** **********设置纵横打印（默认为纵打）、打印纸***************** *//*
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			*//** ************设置单元格字体************** *//*
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			*//** ************以下设置三种单元格样式************ *//*
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			// excel大标题
			if (mainTitle != null && !mainTitle.equals("")) {
				sheet.mergeCells(0, 0, titles.length - 1, 0);
				sheet.addCell(new Label(0, 0, mainTitle, wcf_center));
				beginRow = beginRow + 1;
			}

			*//** ***************以下是EXCEL第一行列标题********************* *//*
			if (titles != null && titles.length > 1) {
				for (int i = 0; i < titles.length; i++) {
					sheet.addCell(new Label(i, beginRow, titles[i], wcf_center));
				}
				beginRow = beginRow + 1;
			}
			*//** ***************以下是EXCEL正文数据********************* *//*
			for (int i = 0; i < contents.size(); i++) {// row
				String[] rowContent = contents.get(i);
				for (int j = 0; j < titles.length; j++) { // cell
					String content = "";
					if (j < rowContent.length) {
						content = rowContent[j];
					}
					sheet.addCell(new Label(j, i + beginRow, content, wcf_left));
				}
			}
			workbook.write();
			workbook.close();

		} catch (Exception e) {
			
		}
	}*/
	
	public final static void buildExcel(OutputStream outStream,String mainTitle, String[] titles, List<String[]> contents) {
		buildExcel(outStream, mainTitle, null, titles, contents);
	}
	
	//添加副标题栏
	public final static void buildExcel(OutputStream outStream,String mainTitle, List<Map<Integer, String>> subTitles, String[] titles, List<String[]> contents) {
		try {
			//创建工作簿
			SXSSFWorkbook wb = new SXSSFWorkbook(15);
			//创建工作表
			Sheet sheet = wb.createSheet("Sheet1");
			
			int beginRow=0;
			
			
			/** 头部样式 **/
			//设置样式
			CellStyle style=wb.createCellStyle();
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setWrapText(false);
			// 生成一个字体
			Font font =wb.createFont();
			font.setFontHeightInPoints((short)10);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			
			/** 正文样式 **/
			//设置样式
			CellStyle style2=wb.createCellStyle();
			style2.setBorderBottom(CellStyle.BORDER_THIN);
			style2.setBorderLeft(CellStyle.BORDER_THIN);
			style2.setBorderRight(CellStyle.BORDER_THIN);
			style2.setBorderTop(CellStyle.BORDER_THIN);
			style2.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(false);
			// 生成一个字体
			Font font2 =wb.createFont();
			font2.setFontHeightInPoints((short)10);
			style2.setFont(font2);
			
			/** 主标题样式 **/
			//设置样式
			CellStyle styleMainTitle=wb.createCellStyle();
			styleMainTitle.setBorderBottom(CellStyle.BORDER_NONE);
			styleMainTitle.setBorderLeft(CellStyle.BORDER_NONE);
			styleMainTitle.setBorderRight(CellStyle.BORDER_NONE);
			styleMainTitle.setBorderTop(CellStyle.BORDER_NONE);
			styleMainTitle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			styleMainTitle.setAlignment(CellStyle.ALIGN_CENTER);
			styleMainTitle.setWrapText(false);
			styleMainTitle.setFont(font);	
			
			/** 副标题样式 **/
			//设置样式
			CellStyle styleSubTitle=wb.createCellStyle();
			styleSubTitle.setBorderBottom(CellStyle.BORDER_NONE);
			styleSubTitle.setBorderLeft(CellStyle.BORDER_NONE);
			styleSubTitle.setBorderRight(CellStyle.BORDER_NONE);
			styleSubTitle.setBorderTop(CellStyle.BORDER_NONE);
			styleSubTitle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			styleSubTitle.setAlignment(CellStyle.ALIGN_LEFT);
			styleSubTitle.setWrapText(false);
			styleSubTitle.setFont(font2);		
			
			if(mainTitle!=null && !"".equals(mainTitle)){
				sheet.addMergedRegion(new CellRangeAddress(beginRow, 0, 0, titles.length-1));
				Row row = sheet.createRow(beginRow);
				Cell cell = row.createCell(0);
				cell.setCellStyle(styleMainTitle);
				cell.setCellValue(mainTitle);
				beginRow++;
			}
			
			//副标题栏
			if (subTitles != null && subTitles.size() > 0) {
				for (Map<Integer, String> item : subTitles) {
					Row row = sheet.createRow(beginRow);
					for (Integer key : item.keySet()) {
						Cell cell = row.createCell(key);
						cell.setCellStyle(styleSubTitle);
						sheet.setColumnWidth(key, 2200);
						cell.setCellValue(item.get(key));
					}
					beginRow++;
				}
			}
			
			//创建标题
			if(titles!=null&&titles.length>0){
				Row row = sheet.createRow(beginRow);
				for (int i = 0; i < titles.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellStyle(style);
					sheet.setColumnWidth(i, 2200);
					cell.setCellValue(titles[i]);
				}
				beginRow++;
			}
			
			//创建Excel正文数据;
			if(contents!=null && !contents.isEmpty()){
				for (int i = 0; i < contents.size(); i++) {
					String[] rowContent = contents.get(i);
					Row row_value = sheet.createRow(beginRow);
					for (int j = 0; j < titles.length; j++) {
						Cell cell_value = row_value.createCell(j);       
						cell_value.setCellStyle(style2);
						cell_value.setCellValue(rowContent[j]);
					}
					beginRow++;
				}
			}
			
			wb.write(outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
