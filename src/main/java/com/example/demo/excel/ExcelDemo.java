package com.example.demo.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDemo {
	
	
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) {
		//参考网址  https://blog.csdn.net/hjf_1291030386/article/details/75288220
		try {
			excelImport();
			excelExport();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 数据导出到Excel
	 * @author liusa
	 * @date 2019/04/18 19:01
	 * @throws ParseException
	 */
	public static void excelExport() throws ParseException {
		List<ExcelEntity> list  = new ArrayList<ExcelEntity>();
		
		//构造数据-待写入Excel的内容
		for(int i=0;i<5;i++) {
			ExcelEntity entity = new ExcelEntity("刘飒"+i,18,"1999-09-09");
			list.add(entity);
		}
		
		Workbook workbook = new HSSFWorkbook(); 
		//创建工作表单
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet("test"); 
		
		//合并单元格前3列 - - CellRangeAddress参数:起始行，截至行，起始列， 截至列  
//		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3)); 
		
		for(int rowNum=0;rowNum<list.size();rowNum++) {
			//行
			Row row = sheet.createRow(rowNum);
			//单元格
			row.createCell(0).setCellValue(list.get(rowNum).getName());
			row.createCell(1).setCellValue(list.get(rowNum).getAge());
			row.createCell(2,CellType.NUMERIC).setCellValue(list.get(rowNum).getBrithday());
		}

		//输出Excel文件  
		FileOutputStream output;
		try {
			output = new FileOutputStream("D://AFolder/BIM/workbook.xls");
			workbook.write(output);  
			output.flush();  
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	
	/**
	 * Excel数据导入
	 * @author liusa
	 * @date 2019/04/18 19:02
	 */
	public static void excelImport() {
		
		File excelFile = new File("D://AFolder/BIM/test.xlsx"); // 创建文件对象  
        try {
			FileInputStream in = new FileInputStream(excelFile);// 文件流 
			
			 Workbook workbook = null;
		        if(excelFile.getName().endsWith("xls")){ 
		        	workbook = new HSSFWorkbook(in);  
		        }else if(excelFile.getName().endsWith("xlsx")){  
		        	workbook = new XSSFWorkbook(in);  
		        }
		        Sheet sheet = workbook.getSheetAt(0);   // 遍历第几个Sheet，从0开始 
		        
		        for(Row r:sheet) {

		        	//普通文本类型
		        	String str = r.getCell(0).getStringCellValue();
		        	//数值型
		        	Double doub = r.getCell(1).getNumericCellValue();
		        	int number = doub.intValue();
		        	//日期型
		        	Date date = r.getCell(2).getDateCellValue();
		        	//date --> String 的转换
		        	String dateStr = format.format(date);
		        	
		        	System.out.println(str+" "+number+" "+dateStr);
		        }
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} 
	}
	
	
	
	
	
	
	
	
	
	
	
	
        
        
	}