import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.format.Colour;
import jxl.write.Alignment;
import jxl.write.Label;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ScalePMIO {
	static int module;
	String filePath;
	WritableWorkbook wwb;
	WritableSheet sheet;
	WritableCellFormat wcf;
	public void exportTransaction(Object[][] exportTable, int rows, int mode) throws IOException{
		System.out.println("export Transaction() called (rows = "+rows+")");
		int columnIndex = 0;
		
		if(mode == 1){
			columnIndex = 8;
		}else if (mode == 2){
			columnIndex = 7;
		}else if(mode==3|| mode==4){
			columnIndex=6;
		}else if(mode == 9){
		columnIndex = 9;
		}else{
			columnIndex = 11;
		}
		filePath ="";
		module++;		
		JFileChooser chooser = new JFileChooser(); 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yy");
		LocalDate ldate = LocalDate.now();
		try{	
			//choose file location
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());		
			chooser.setFileFilter( new FileNameExtensionFilter("Excel Spreadsheet", "xls"));
			chooser.setSelectedFile(new File("ScaleReport["+dtf.format(ldate)+"]"));	
			int returnVal=0;
			if(module==1){
				if(returnVal == JFileChooser.APPROVE_OPTION){
				}
				chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());		
				chooser.setFileFilter( new FileNameExtensionFilter("Excel Spreadsheet", "xls"));
				if(columnIndex==11){
					chooser.setSelectedFile(new File("ScaleInventory["+dtf.format(ldate)+"]"));
				}else{
						chooser.setSelectedFile(new File("ScaleReport["+dtf.format(ldate)+"]"));
			}
			returnVal = chooser.showSaveDialog(chooser);		
			if(returnVal == JFileChooser.APPROVE_OPTION){					
				filePath = chooser.getSelectedFile().getAbsolutePath();	
				System.out.println("save path selected");
			}
				
			//format new file	
			wwb = Workbook.createWorkbook(new File(filePath+".xls"));
			sheet = wwb.createSheet("ScalePM", 0);
			wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));
			
			//autofit cells
			for (int c=1; c<columnIndex+1; c++){
				CellView cell = sheet.getColumnView(c);
				cell.setAutosize(true);
				sheet.setColumnView(c, cell);
			}
			
			if(mode==1){
			//Add cell format for headers
			wcf.setAlignment(jxl.format.Alignment.CENTRE);
			wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
			wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
			wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
			wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
			sheet.addCell(new jxl.write.Label(2, 1, "", wcf));
			wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));			
			wcf.setAlignment(jxl.format.Alignment.CENTRE);
			wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
			wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
			wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
			wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
			sheet.addCell(new jxl.write.Label(1, 1, "Date", wcf));
			sheet.addCell(new jxl.write.Label(2, 1, "Tech", wcf));
			sheet.addCell(new jxl.write.Label(3, 1, "Department", wcf));
			sheet.addCell(new jxl.write.Label(4, 1, "Workstation", wcf));
			sheet.addCell(new jxl.write.Label(5, 1, "Serial Number", wcf));
			sheet.addCell(new jxl.write.Label(6, 1, "Reading", wcf));
			sheet.addCell(new jxl.write.Label(7, 1, "Weight", wcf));
			wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));	
			wcf.setAlignment(jxl.format.Alignment.CENTRE);
			wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
			wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
			wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
			wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
			wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
			sheet.addCell(new jxl.write.Label(8, 1, "Notes", wcf));				
			//add cell data and format			
			for (int r=2; r<rows+2; r++){
				for (int c=1; c<9; c++){
					if (c!=1){
						if(c!=8){
							try{
							sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));
							}catch (Exception a){
								sheet.addCell(new jxl.write.Label(c, r, "0", wcf));
							}
							System.out.println("Cell added["+r+","+c+"]");
						}else{
							
								wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
								wcf.setBackground(Colour.LIGHT_ORANGE);
								wcf.setAlignment(jxl.format.Alignment.LEFT);
								wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
								wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
								wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);	
								try{
								sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));													
							}catch (Exception n){
								sheet.addCell(new jxl.write.Label(c, r+1,"0", wcf));
								wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));						
								wcf.setAlignment(jxl.format.Alignment.LEFT);
								wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
								wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);		
								wcf.setBackground(Colour.TAN);
							}
								System.out.println("Cell added["+r+","+c+"]");		
						}
					}
					else{
						wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
						wcf.setBackground(Colour.LIGHT_ORANGE);
						wcf.setAlignment(jxl.format.Alignment.LEFT);
						wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
						wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
						try{
						sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));
						}catch (Exception b){
							sheet.addCell(new jxl.write.Label(c, r, "0", wcf));
						}
						System.out.println("Cell added["+r+","+c+"]");
						wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
						wcf.setBackground( Colour.LIGHT_ORANGE);
						wcf.setAlignment(jxl.format.Alignment.LEFT);
						wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
						wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);						
					}
				}			
			}
			}else if (mode==2){				
				//Add cell format for headers
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
				wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
				sheet.addCell(new jxl.write.Label(2, 1, "", wcf));
				wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));			
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
				wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
				wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
				sheet.addCell(new jxl.write.Label(1, 1, "Date", wcf));
				sheet.addCell(new jxl.write.Label(2, 1, "Tech", wcf));					
				sheet.addCell(new jxl.write.Label(3, 1, "Workstation", wcf));
				sheet.addCell(new jxl.write.Label(4, 1, "Serial Number", wcf));
				sheet.addCell(new jxl.write.Label(5, 1, "Reading", wcf));
				sheet.addCell(new jxl.write.Label(6, 1, "Weight", wcf));
				wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));	
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
				wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
				wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
				sheet.addCell(new jxl.write.Label(7, 1, "Notes", wcf));				
				//add cell data and format			
				for (int r=2; r<rows+2; r++){
					for (int c=1; c<8; c++){
						if (c!=1){
							if(c!=7){
								try{
								sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));
								System.out.println("Cell added["+r+","+c+"]");
								}catch(Exception n){
									sheet.addCell(new jxl.write.Label(c, r, "0", wcf));
									System.out.println("Cell added["+r+","+c+"]");
								}
							}else{
								try{
									wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
									wcf.setBackground(Colour.LIGHT_ORANGE);
									wcf.setAlignment(jxl.format.Alignment.LEFT);
									wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
									wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
									wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);	
									sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));	
									System.out.println("Cell added["+r+","+c+"]");						
								}catch (Exception n){
									sheet.addCell(new jxl.write.Label(c, r+1,"0", wcf));
									wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));						
									wcf.setAlignment(jxl.format.Alignment.LEFT);
									wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
									wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);		
									wcf.setBackground(Colour.TAN);
								}
								
							}
						}
						else{
							try{
							wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
							wcf.setBackground(Colour.LIGHT_ORANGE);
							wcf.setAlignment(jxl.format.Alignment.LEFT);
							wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
							wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
							sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));
							System.out.println("Cell added["+r+","+c+"]");
							wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
							wcf.setBackground( Colour.LIGHT_ORANGE);
							wcf.setAlignment(jxl.format.Alignment.LEFT);
							wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
							wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
							}catch (Exception x){
								wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
								wcf.setBackground(Colour.LIGHT_ORANGE);
								wcf.setAlignment(jxl.format.Alignment.LEFT);
								wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
								wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
								sheet.addCell(new jxl.write.Label(c, r, "0", wcf));
								System.out.println("Cell added["+r+","+c+"]");
								wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
								wcf.setBackground( Colour.LIGHT_ORANGE);
								wcf.setAlignment(jxl.format.Alignment.LEFT);
								wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
								wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
									
							}
						}
					}			
				}
			}else if (mode==9){
				//Add cell format for headers
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
				wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
				sheet.addCell(new jxl.write.Label(2, 1, "", wcf));
				wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));			
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
				wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
				wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
				sheet.addCell(new jxl.write.Label(1, 1, "#", wcf));
				sheet.addCell(new jxl.write.Label(2, 1, "Down Since", wcf));				
				sheet.addCell(new jxl.write.Label(3, 1, "Scale", wcf));					
				sheet.addCell(new jxl.write.Label(4, 1, "Status", wcf));
				sheet.addCell(new jxl.write.Label(5, 1, "Symptom", wcf));
				sheet.addCell(new jxl.write.Label(6, 1, "Root Cause", wcf));
				sheet.addCell(new jxl.write.Label(7, 1, "Resolution", wcf));
				sheet.addCell(new jxl.write.Label(8, 1, "Repaired Date", wcf));
				wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));	
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
				wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
				wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
				sheet.addCell(new jxl.write.Label(9, 1, "tech", wcf));				
				//add cell data and format			
				for (int r=2; r<rows+2; r++){
					for (int c=1; c<10; c++){
						if (c!=1){
							if(c!=9){
								try{
								sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));								
								}catch( Exception n){
									sheet.addCell(new jxl.write.Label(c, r,"0", wcf));	
								}
								System.out.println("Cell added["+r+","+c+"]");
							}else{
								
									wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
									wcf.setBackground(Colour.LIGHT_ORANGE);
									wcf.setAlignment(jxl.format.Alignment.LEFT);
									wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
									wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
									wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);	
									try{
									sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));	
									System.out.println("Cell added["+r+","+c+"]");						
								}catch (Exception n){
									sheet.addCell(new jxl.write.Label(c, r+1,"0", wcf));
									wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));						
									wcf.setAlignment(jxl.format.Alignment.LEFT);
									wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
									wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);		
									wcf.setBackground(Colour.TAN);
								}
								
							}
						}
						else{							
							wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
							wcf.setBackground(Colour.LIGHT_ORANGE);
							wcf.setAlignment(jxl.format.Alignment.LEFT);
							wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
							wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
							try{
							sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));
							}catch( Exception m){								
								sheet.addCell(new jxl.write.Label(c, r, "0", wcf));							
							}
							wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
							wcf.setBackground( Colour.LIGHT_ORANGE);
							wcf.setAlignment(jxl.format.Alignment.LEFT);
							wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
							wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);								
							System.out.println("Cell added["+r+","+c+"]");
						}
					}			
				}	
			}else if (mode ==3|| mode ==4){				
				//Add cell format for headers
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
				wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
				sheet.addCell(new jxl.write.Label(2, 1, "", wcf));
				wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));			
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
				wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
				wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
				sheet.addCell(new jxl.write.Label(1, 1, "Date", wcf));
				sheet.addCell(new jxl.write.Label(2, 1, "Tech", wcf));
				if(mode==3){
				sheet.addCell(new jxl.write.Label(3, 1, "Serial Number", wcf));
				}else{
				sheet.addCell(new jxl.write.Label(3, 1, "Workstation", wcf));	
				}
				sheet.addCell(new jxl.write.Label(4, 1, "Reading", wcf));
				sheet.addCell(new jxl.write.Label(5, 1, "Weight", wcf));
				wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));	
				wcf.setAlignment(jxl.format.Alignment.CENTRE);
				wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
				wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
				wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
				wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
				wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
				sheet.addCell(new jxl.write.Label(6, 1, "Notes", wcf));				
				//add cell data and format			
				for (int r=2; r<rows+2; r++){
					for (int c=1; c<7; c++){
						if (c!=1){
							if(c!=6){
								try{
								sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));								
								}catch( Exception n){
									sheet.addCell(new jxl.write.Label(c, r,"0", wcf));	
								}
								System.out.println("Cell added["+r+","+c+"]");
							}else{
								
									wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
									wcf.setBackground(Colour.LIGHT_ORANGE);
									wcf.setAlignment(jxl.format.Alignment.LEFT);
									wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
									wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
									wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);	
									try{
									sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));	
									System.out.println("Cell added["+r+","+c+"]");						
								}catch (Exception n){
									sheet.addCell(new jxl.write.Label(c, r+1,"0", wcf));
									wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));						
									wcf.setAlignment(jxl.format.Alignment.LEFT);
									wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
									wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);		
									wcf.setBackground(Colour.TAN);
								}
								
							}
						}
						else{							
							wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
							wcf.setBackground(Colour.LIGHT_ORANGE);
							wcf.setAlignment(jxl.format.Alignment.LEFT);
							wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
							wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
							try{
							sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-1].toString(), wcf));
							}catch( Exception m){								
								sheet.addCell(new jxl.write.Label(c, r, "0", wcf));							
							}
							wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
							wcf.setBackground( Colour.LIGHT_ORANGE);
							wcf.setAlignment(jxl.format.Alignment.LEFT);
							wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
							wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);								
							System.out.println("Cell added["+r+","+c+"]");
						}
					}			
				}	
			
			}else{
					//Add cell format for headers
					wcf.setAlignment(jxl.format.Alignment.CENTRE);
					wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
					wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
					wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
					wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
					wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
					sheet.addCell(new jxl.write.Label(2, 1, "", wcf));
					wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));			
					wcf.setAlignment(jxl.format.Alignment.CENTRE);
					wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
					wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
					wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
					wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
					wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
					sheet.addCell(new jxl.write.Label(1, 1, "Index", wcf));
					sheet.addCell(new jxl.write.Label(2, 1, "Serial No.", wcf));
					sheet.addCell(new jxl.write.Label(3, 1, "Workstation", wcf));
					sheet.addCell(new jxl.write.Label(4, 1, "Cat", wcf));
					sheet.addCell(new jxl.write.Label(5, 1, "Condition", wcf));
					sheet.addCell(new jxl.write.Label(6, 1, "Date Checked", wcf));
					sheet.addCell(new jxl.write.Label(7, 1, "Cap.", wcf));
					sheet.addCell(new jxl.write.Label(8, 1, "Model", wcf));
					sheet.addCell(new jxl.write.Label(9, 1, "Manufacturer", wcf));
					sheet.addCell(new jxl.write.Label(10, 1, "Warranty Termination", wcf));
					sheet.addCell(new jxl.write.Label(11, 1, "Date Purchased", wcf));					
					wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false, UnderlineStyle.SINGLE_ACCOUNTING, Colour.WHITE));	
					wcf.setAlignment(jxl.format.Alignment.CENTRE);
					wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
					wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
					wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
					wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);
					wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.DOUBLE,jxl.format.Colour.BLACK);
					wcf.setBackground(Colour.DARK_GREEN, Pattern.PATTERN3);
					sheet.addCell(new jxl.write.Label(12, 1, "Other Details", wcf));				
					//add cell data and format			
					for (int r=2; r<rows+2; r++){
						for (int c=1; c<13; c++){
							if (c!=1){
								if(c!=12){
									try{
										sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-2].toString(), wcf));
										System.out.println("Cell added["+r+","+c+"]");
									} catch (Exception s){
										s.printStackTrace();
										sheet.addCell(new jxl.write.Label(c, r, "0", wcf));
										System.out.println("Cell added["+r+","+c+"]");
									}
								}else{
									try{
										wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
										wcf.setBackground(Colour.LIGHT_ORANGE);
										wcf.setAlignment(jxl.format.Alignment.LEFT);
										wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
										wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
										wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);	
										sheet.addCell(new jxl.write.Label(c, r, exportTable[r-2][c-2].toString(), wcf));	
										System.out.println("Cell added["+r+","+c+"]");						
									}catch (Exception n){
										sheet.addCell(new jxl.write.Label(c, r+1,"0", wcf));
										wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));						
										wcf.setAlignment(jxl.format.Alignment.LEFT);
										wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
										wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);		
										wcf.setBackground(Colour.TAN);
									}
									
								}
							}
							else{
								wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
								wcf.setBackground(Colour.LIGHT_ORANGE);
								wcf.setAlignment(jxl.format.Alignment.LEFT);
								wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);		
								wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
								sheet.addCell(new jxl.write.Label(c, r, "#"+String.valueOf(r-1), wcf));
								System.out.println("Cell added["+r+","+c+"]");
								wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Times"), 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));
								wcf.setBackground( Colour.LIGHT_ORANGE);
								wcf.setAlignment(jxl.format.Alignment.LEFT);
								wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
								wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM,jxl.format.Colour.GREY_50_PERCENT);						
							}
						}			
					}
			}
			
			}
		}catch (Exception ex){
			JOptionPane.showMessageDialog(null,"An error was occured, please try saving your document again. when overwritting a file, make sure that file is closed\nEror code 25");
			ex.printStackTrace();		
			wwb.write();
		}
			try {			
			//the last line
			wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false));				
			wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THICK,jxl.format.Colour.BLACK);
			for (int c=1;c<columnIndex+1;c++){
				sheet.addCell(new jxl.write.Label(c,rows+1, "", wcf));
				System.out.println("Cell added["+rows+1+","+c+"]");	
			}
			if(mode==5){				
				sheet.addCell(new jxl.write.Label( 11,rows+1 , "", wcf));
				System.out.println("Cell added["+rows+1+",11]");				
				sheet.addCell(new jxl.write.Label( 12,rows+1 , "", wcf));					
			}	
			wcf = new WritableCellFormat(new WritableFont(WritableFont.createFont("Arial"), 16,WritableFont.BOLD, false));
			wcf.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.NONE,jxl.format.Colour.BLACK);
			for(int c=0; c<13; c++){
			sheet.addCell(new jxl.write.Label( c,rows+2 , "", wcf));
			}
			wwb.write();
			wwb.close();
			} catch (WriteException e) {				
				e.printStackTrace();
				wwb.write();
				JOptionPane.showMessageDialog(null,"An error was occured, please try saving your document again. when overwritting a file, make sure that file is closed\nEror code 25");
			}
			
			
			return;
		
		
	
	
}
		
}

