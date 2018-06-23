import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.UIManager;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JToggleButton;

public class ScalePMGUI extends JFrame{
	
	private ScalePMIO IO;
	private ScalePMDB db;		
	private String[] workstationsCollection;
	private Object[] deps;
	private String[] rowValue;
	private Object[][] tblValues;
	private static int badReadings;
	private boolean newScale;
	private int changeSN;
	private Timer timer;
	
	private  String newSN;
	private String dbNotes;
	private String techName;	
	private  String dep;
	private String SN;
	private String workstation;
	private double reading;
	private double weight;	
	private int operationInProgress;
	private int exportMode;
	private String site;
	
	
	private JFrame frame;
	//on panel1:
	private JPanel panel1;	
	private JButton btnKeyboard;
	JTextField txtID;	
	private JTextField txtSN;
	private JTextField txtReading;
	private JTextField txtWeight;	
	private JComboBox boxDep;
	private JComboBox boxWorkstation;
	private JComboBox boxFilterSite1;
	private JButton btnID;
	private JButton btnSNYes;
	private JButton btnSNNo;
	private JButton btnAdjYes;
	private JButton btnAdjNo;
	private JButton btnNext;
	private JButton btnExit;
	private JButton btnStartOver;
	private JButton btnDone; 
	private JButton btnExportToExcel;	
	private JButton btnProceed;
	private ButtonGroup groupCheck;
	private JRadioButton check1Panel1;
	private JRadioButton check3Panel1;
	private JRadioButton check2Panel1;
	private JRadioButton check5Panel1;
	private JRadioButton check4Panel1;
	private JLabel lblSite;
	private JLabel lblReason;
	private JLabel lblAdj;
	private JLabel lblLbs;
	private JLabel lblSNVerification;
	private JLabel lblTitle;	
	private JLabel lblID;
	private JLabel lblDepartment;
	private JLabel lblWorkstation;
	private JLabel lblSN;
	private JLabel lblReading;
	private JLabel lblNewSN;
	private JLabel lblWeight;
	private JLabel lblLbs2;
	//on panel2:
	private JPanel panel2;	
	private JTextPane lblDateInstructions;	
	private JTextField txtScaleReport;
	private JTextField txtDate1;
	private JTextField txtDate2;
	private JScrollPane stblReports;
	private JTable tblReports;	
	private JComboBox boxDepReport;
	private JComboBox boxReports;	
	private JComboBox boxWorkstationReport;
	private JComboBox boxMSModel;
	private JComboBox boxFilterSite2;
	private JButton btnGenerateReport;
	private JButton btnKeyboard2;
	private JCheckBox checkIncludeNonSAP;
	private JLabel lblRecords;
	private JLabel lblSelectReport;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JLabel lblReports;
	private JLabel lblSite2;
	//Panel3:
	private JPanel panel3;
	private JTable tblScaleInventory;
	private JScrollPane stblScaleInventory;
	private JButton btnKeyboard3;
	private JButton btnMSQuickAction;	 //MS stands for manage scales
	private JButton btnMSUpdate;
	private JButton btnMSRefresh;
	private JButton btnMSExport;
	private JComboBox boxMSQuickAction;
	private JComboBox boxMSCategory;
	private JComboBox boxMSCondition;
	private JComboBox boxMSCap;
	private JComboBox boxMSManufacturer;
	private JComboBox boxFilterSite3;
	private ButtonGroup groupCheck3;
	private JRadioButton check1Panel3;
	private JRadioButton check2Panel3;
	private JRadioButton check3Panel3;	
	private JRadioButton check4Panel3;
	private JTextField txtMSQuickAction;
	private JTextField txtMSSN;
	private JTextField txtMSWorkstation;
	private JTextField txtMSWarranty;
	private JTextField txtMSPurchased;
	private JTextField txtMSDimensions;	
	private JLabel lblMSEditData;
	private JLabel lblMSQuickAction;
	private JLabel lblMSSN;
	private JLabel lbltitle3;
	//panel4:
	private JPanel panel4;
	private JTable tblManageWorkstations;	
	private JScrollPane stblManageWorkstations;
	private JButton btnKeyboard4;
	private JButton btnMWQuickAction;      //MW stands for manage workstations
	private JButton btnMWUpdate;
	private JButton btnMWRefresh;	
	private JComboBox boxMWQuickAction;
	private ButtonGroup groupCheck4;
	private JRadioButton check1Panel4;
	private JRadioButton check2Panel4;
	private JRadioButton check3Panel4;
	private JRadioButton check4Panel4;	
	private JComboBox boxMWDep;
	private JComboBox boxMWSAP;
	private JComboBox boxMWCategory;
	private JComboBox boxMWSite;
	private JComboBox boxFilterSite4;
	private JTextField txtMWWorkstation;
	private JTextField txtMWQuickAction;
	private JTextArea areaCategory;
	private JLabel lblMWEditData;
	private JLabel lblMWQuickAction;
	private JLabel lbltitle4;
	private JLabel lblMWWorkstation;
	//panel5	
	private JPanel panel5;
	private JTable tblRepairs;	
	private JScrollPane stblRepairs;
	private ButtonGroup groupCheck5;
	private JRadioButton checkRRNewUpdate;	
	private JRadioButton check1Panel5;
	private JRadioButton check2Panel5;
	private JRadioButton check3Panel5;
	private JRadioButton check4Panel5;
	private JComboBox boxRRStatus;
	private JComboBox boxFilterSite5;
	private JButton btnRRAddRecord;
	private JButton btnRRRefresh;
	private JButton btnRRExport;
	private JButton btnKeyboard5;
	private JRadioButton checkToday;
	private JTextField txtDownSince;
	private JTextField txtRepairedDate;
	private JTextField txtSymptom;
	private JTextField txtRRID;
	private JTextField txtRootCause;
	private JTextField txtRepairedBy;
	private JTextField txtResolution;
	private JTextField txtRRSN;
	private JLabel lblTitle5;
	private JLabel lblRRRecords;	
	private JLabel lblRRID;
	private JLabel lblDownSince;
	private JLabel lblRRStatus;
	private JLabel lblSymptom;	
	private JLabel lblResolution;
	private JLabel lblRepairedDate;
	private JLabel lblRepairedBy;
	private JLabel lblRRSN;	
	private JLabel lblRootCause;
	private JLabel lblDateRepaired;

	public ScalePMGUI() {
	//instantiation of objects:
		db = new ScalePMDB();
		IO = new ScalePMIO();		
		newSN = null;		
		dbNotes="";
		operationInProgress =0;
		badReadings = 0;
		newScale = false;
		
		
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Scale Inventory\\icon4.ico"));
		frame.setBounds(375, 180, 760, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setTitle("Scale Inventory Manager v.2.4.0");
		
		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tab);
		
		groupCheck = new ButtonGroup();
		
		//panel1 components:
		
		panel1 = new JPanel();
		panel1.setBackground(SystemColor.activeCaption);
		panel1.setBounds(10, 11, 908, 647);		
		panel1.setLayout(null);
		tab.add(panel1);   
		tab.setTitleAt(0, "Scale PM");
		tab.setEnabledAt(0, true);		
		
		lblTitle = new JLabel("Scale Preventive Maintenance");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial Black", Font.BOLD, 30));
		lblTitle.setBounds(29, 11, 564, 60);
		panel1.add(lblTitle);
		
		lblID = new JLabel("Technician:");
		lblID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblID.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblID.setBounds(48, 108, 109, 29);
		panel1.add(lblID);
		
		lblSite = new JLabel("Site:");
		lblSite.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSite.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblSite.setBounds(48, 67, 109, 29);
		panel1.add(lblSite);
		
		lblDepartment = new JLabel("Department:");
		lblDepartment.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDepartment.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblDepartment.setBounds(48, 162, 109, 29);
		panel1.add(lblDepartment);
		
		txtID = new JTextField();		
		txtID.setBackground(new Color(240, 255, 255));
		txtID.setFont(new Font("Arial", Font.PLAIN, 14));
		txtID.setHorizontalAlignment(SwingConstants.CENTER);
		txtID.setBounds(192, 108, 114, 29);
		panel1.add(txtID);
		txtID.setColumns(30);
		
		boxFilterSite1 = new JComboBox();
		boxFilterSite1.setBackground(new Color(255, 228, 181));		
		boxFilterSite1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxFilterSite1.setBounds(192, 67, 162, 29);
		panel1.add(boxFilterSite1);
		//add site list
		try{
			ResultSet rs = db.getSitetList();
			while (rs.next()){
				boxFilterSite1.addItem(rs.getString(1));
			}
		}catch (Exception ex){
			ex.getStackTrace();
		}	
		
		boxDep = null;
		boxDep = new JComboBox();		
		boxDep.setBackground(new Color(255, 228, 181));
		boxDep.setFont(new Font("Segoe UI", Font.BOLD, 14));				
		boxDep.setEditable(false);
		boxDep.setVisible(false);
		boxDep.setBounds(192, 160, 162, 35);
		boxDep.addItem("Select Department");
		panel1.add(boxDep);	
		
		lblWorkstation = new JLabel("Worstation:");
		lblWorkstation.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWorkstation.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblWorkstation.setBounds(48, 218, 109, 29);
		panel1.add(lblWorkstation);
		
		boxWorkstation = new JComboBox();
		boxWorkstation.setBackground(new Color(255, 228, 181));
		boxWorkstation.setMaximumRowCount(15);
		boxWorkstation.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxWorkstation.setBounds(192, 216, 162, 35);
		panel1.add(boxWorkstation);
		
		lblSN = new JLabel("Serial number:");
		lblSN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSN.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblSN.setBounds(36, 271, 121, 29);
		panel1.add(lblSN);
		
		txtSN = new JTextField();		
		txtSN.setBackground(new Color(240, 255, 255));
		txtSN.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtSN.setEditable(false);
		txtSN.setBounds(192, 272, 147, 29);
		panel1.add(txtSN);
		txtSN.setColumns(10);
		
		lblReading = new JLabel("Reading:");
		lblReading.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReading.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblReading.setBounds(76, 341, 109, 29);
		panel1.add(lblReading);
		
		btnID = new JButton("");
		btnID.setForeground(Color.RED);
		btnID.setBackground(new Color(0, 139, 139));
		btnID.setIcon(new ImageIcon("C:\\Scale Inventory\\go.jpg"));
		btnID.setBounds(307, 108, 47, 29);
		panel1.add(btnID);
		
		lblSNVerification = new JLabel("Correct SN?");
		lblSNVerification.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSNVerification.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSNVerification.setForeground(Color.RED);
		lblSNVerification.setBounds(46, 303, 121, 27);
		panel1.add(lblSNVerification);
		
		btnSNYes = new JButton("Yes");
		btnSNYes.setForeground(new Color(255, 255, 255));
		btnSNYes.setBackground(new Color(0, 139, 139));
		btnSNYes.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnSNYes.setBounds(192, 301, 75, 31);
		panel1.add(btnSNYes);
		
		btnSNNo = new JButton("No");
		btnSNNo.setForeground(new Color(255, 255, 255));
		btnSNNo.setBackground(new Color(0, 139, 139));
		btnSNNo.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnSNNo.setBounds(265, 301, 75, 31);
		panel1.add(btnSNNo);
		
		txtReading = new JTextField();
		txtReading.setBackground(new Color(240, 255, 255));
		txtReading.setFont(new Font("Arial", Font.PLAIN, 14));
		txtReading.setHorizontalAlignment(SwingConstants.RIGHT);
		txtReading.setBounds(192, 345, 52, 26);
		panel1.add(txtReading);
		txtReading.setColumns(10);
		
		lblLbs = new JLabel("lbs");
		lblLbs.setFont(new Font("Arial", Font.PLAIN, 13));
		lblLbs.setBounds(253, 350, 46, 14);
		panel1.add(lblLbs);
		
		lblAdj = new JLabel("Were adjustments necessary?");
		lblAdj.setBackground(new Color(0, 139, 139));
		lblAdj.setForeground(new Color(0, 0, 0));
		lblAdj.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdj.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblAdj.setBounds(349, 313, 232, 29);
		panel1.add(lblAdj);
		
		btnAdjYes = new JButton("Yes");
		btnAdjYes.setForeground(new Color(255, 255, 255));
		btnAdjYes.setBackground(new Color(0, 139, 139));
		btnAdjYes.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnAdjYes.setBounds(369, 341, 65, 31);
		panel1.add(btnAdjYes);
		
		btnAdjNo = new JButton("No");		
		btnAdjNo.setForeground(new Color(255, 255, 255));
		btnAdjNo.setBackground(new Color(0, 139, 139));
		btnAdjNo.setFont(new Font("Arial Black", Font.PLAIN, 13));
		btnAdjNo.setBounds(429, 341, 65, 31);
		panel1.add(btnAdjNo);
		
		lblReason = new JLabel("Select reason:");
		lblReason.setHorizontalAlignment(SwingConstants.CENTER);
		lblReason.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblReason.setBounds(328, 377, 212, 29);
		panel1.add(lblReason);
		
	
		check1Panel1 = new JRadioButton("Adjusted leg(s)");
		check1Panel1.setFont(new Font("Arial", Font.PLAIN, 14));
		check1Panel1.setBackground(SystemColor.activeCaption);
		check1Panel1.setBounds(218, 416, 161, 35);
		panel1.add(check1Panel1);
		
		check3Panel1 = new JRadioButton("Added missing rubbers");
		check3Panel1.setFont(new Font("Arial", Font.PLAIN, 14));
		check3Panel1.setBackground(SystemColor.activeCaption);
		check3Panel1.setBounds(218, 439, 171, 36);
		panel1.add(check3Panel1);
		
		check2Panel1 = new JRadioButton("Reboot Scale");
		check2Panel1.setFont(new Font("Arial", Font.PLAIN, 14));
		check2Panel1.setBackground(SystemColor.activeCaption);
		check2Panel1.setBounds(530, 416, 137, 35);
		panel1.add(check2Panel1);
		
		check5Panel1 = new JRadioButton("Cleaned Moisture");
		check5Panel1.setFont(new Font("Arial", Font.PLAIN, 14));
		check5Panel1.setBackground(SystemColor.activeCaption);
		check5Panel1.setBounds(530, 443, 180, 29);
		panel1.add(check5Panel1);
		
		check4Panel1 = new JRadioButton("Other reason");
		check4Panel1.setFont(new Font("Arial", Font.PLAIN, 14));
		check4Panel1.setBounds(395, 442, 130, 31);
		check4Panel1.setBackground(SystemColor.activeCaption);
		panel1.add(check4Panel1);
		groupCheck.add(check1Panel1);
		groupCheck.add(check2Panel1);
		groupCheck.add(check3Panel1);
		groupCheck.add(check4Panel1);
		groupCheck.add(check5Panel1);
		
		btnNext = new JButton("Next scale");
		btnNext.setForeground(new Color(255, 255, 255));
		btnNext.setBackground(new Color(0, 139, 139));
		btnNext.setFont(new Font("Segoe UI", Font.BOLD, 17));
		btnNext.setBounds(485, 211, 147, 42);
		panel1.add(btnNext);
		
			
			btnExit = new JButton("Save and Exit");
			btnExit.setForeground(new Color(255, 255, 255));
			btnExit.setBackground(new Color(0, 139, 139));
			btnExit.setFont(new Font("Segoe UI", Font.BOLD, 17));
			btnExit.setBounds(485, 264, 147, 42);
			panel1.add(btnExit);
			
			btnStartOver = new JButton("Start over");
			btnStartOver.setForeground(new Color(255, 255, 255));
			btnStartOver.setBackground(new Color(0, 139, 139));
			btnStartOver.setFont(new Font("Segoe UI", Font.BOLD, 17));
			btnStartOver.setBounds(485, 155, 147, 42);
			panel1.add(btnStartOver);
			
			lblNewSN = new JLabel("Enter New scale SN");
			lblNewSN.setBackground(new Color(0, 139, 139));
			lblNewSN.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewSN.setForeground(Color.RED);
			lblNewSN.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewSN.setBounds(192, 249, 147, 29);
			panel1.add(lblNewSN);
			
			btnDone = new JButton("Done");
			btnDone.setForeground(new Color(255, 255, 255));
			btnDone.setBackground(new Color(0, 139, 139));
			btnDone.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnDone.setBounds(338, 272, 75, 29);
			panel1.add(btnDone);
			
			lblWeight = new JLabel("Weight:");
			lblWeight.setHorizontalAlignment(SwingConstants.RIGHT);
			lblWeight.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblWeight.setBounds(76, 381, 109, 23);
			panel1.add(lblWeight);
			lblWeight.setVisible(false);
			
			txtWeight = new JTextField();
			txtWeight.setBackground(new Color(240, 255, 255));
			txtWeight.setFont(new Font("Arial", Font.PLAIN, 14));
			txtWeight.setHorizontalAlignment(SwingConstants.RIGHT);
			txtWeight.setText("15.00");
			txtWeight.setBounds(192, 379, 52, 29);
			panel1.add(txtWeight);
			txtWeight.setColumns(10);
			txtWeight.setVisible(false);
			
			lblLbs2 = new JLabel("lbs");
			lblLbs2.setFont(new Font("Arial", Font.PLAIN, 13));
			lblLbs2.setBounds(253, 386, 46, 14);
			panel1.add(lblLbs2);
			
			btnProceed = new JButton("Proceed");
			btnProceed.setForeground(new Color(255, 255, 255));
			btnProceed.setBackground(new Color(0, 139, 139));
			btnProceed.setFont(new Font("Segoe UI", Font.BOLD, 15));
			btnProceed.setBounds(385, 405, 109, 37);
			panel1.add(btnProceed);
			
			try{
			btnKeyboard = new JButton (new ImageIcon(ImageIO.read(new File("C:\\Scale Inventory\\keyboard.jpg"))));
			btnKeyboard.setBackground(new Color(222, 184, 135));
			btnKeyboard.setBounds(485, 83, 147, 50);
			panel1.add(btnKeyboard);
			}catch (Exception i){
				i.printStackTrace();
				return;
			}			
			//panel2 components
			panel2 = new JPanel();
			panel2.setBackground(new Color(100, 149, 237));
			tab.addTab("Generate Report", null, panel2, null);
			tab.setEnabledAt(1, true);
			panel2.setLayout(null);
			
			boxReports = new JComboBox();		
			boxReports.setBackground(new Color(255, 228, 181));
			boxReports.setFont(new Font("Arial", Font.BOLD, 15));
			boxReports.setModel(new DefaultComboBoxModel(new String[] {"Choose one option", "Date(s)", "Department", "Workstation", "Single Scale"}));
			boxReports.setBounds(274, 70, 160, 31);
			panel2.add(boxReports);
			
			lblRecords = new JLabel("Records");
			lblRecords.setHorizontalAlignment(SwingConstants.CENTER);
			lblRecords.setBounds(220, 25, 213, 31);
			lblRecords.setFont(new Font("Arial Black", Font.BOLD, 30));
			panel2.add(lblRecords);
			
			lblSelectReport = new JLabel("Select Report to generate:");
			lblSelectReport.setBackground(new Color(0, 139, 139));
			lblSelectReport.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblSelectReport.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSelectReport.setBounds(77, 74, 189, 20);
			panel2.add(lblSelectReport);
			
			lblSite2 = new JLabel("Site:");
			 lblSite2.setBackground(new Color(0, 139, 139));
			 lblSite2.setFont(new Font("Segoe UI", Font.BOLD, 15));
			 lblSite2.setHorizontalAlignment(SwingConstants.RIGHT);
			 lblSite2.setBounds(215, 108, 50, 31);
			 lblSite2.setVisible(false);
			panel2.add(lblSite2);	
			
			
			boxFilterSite2 = new JComboBox();		
			boxFilterSite2.setBackground(new Color(255, 228, 181));
			boxFilterSite2.setFont(new Font("Arial", Font.BOLD, 15));
			boxFilterSite2.setModel(new DefaultComboBoxModel());
			boxFilterSite2.setBounds(274, 108, 160, 31);
			boxFilterSite2.setVisible(false);
			panel2.add(boxFilterSite2);
			
			txtDate1 = new JTextField();		
			txtDate1.setBackground(new Color(240, 255, 255));
			txtDate1.setToolTipText("mm/dd/yy");
			txtDate1.setFont(new Font("Arial", Font.PLAIN, 14));
			txtDate1.setBounds(274, 145, 63, 30);
			txtDate1.setVisible(false);
			txtDate1.setColumns(10);
			panel2.add(txtDate1);
		
			
			lblFrom = new JLabel("From:");
			lblFrom.setBackground(new Color(0, 139, 139));
			lblFrom.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFrom.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblFrom.setBounds(218, 145, 46, 30);
			lblFrom.setVisible(false);
			panel2.add(lblFrom);
			
			lblTo = new JLabel("To:");
			lblTo.setBackground(new Color(0, 139, 139));
			lblTo.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblTo.setBounds(343, 145, 30, 30);
			lblTo.setVisible(false);
			panel2.add(lblTo);
			
			txtDate2 = new JTextField();
			txtDate2.setBackground(new Color(240, 255, 255));
			txtDate2.setToolTipText("mm/dd/yy");
			txtDate2.setFont(new Font("Arial", Font.PLAIN, 14));
			txtDate2.setColumns(10);
			txtDate2.setBounds(370, 145, 64, 30);
			txtDate2.setVisible(false);
			panel2.add(txtDate2);
			
			btnGenerateReport = new JButton("Get Report");
			btnGenerateReport.setForeground(new Color(255, 255, 255));
			btnGenerateReport.setBackground(new Color(0, 139, 139));
			btnGenerateReport.setFont(new Font("Arial", Font.BOLD, 14));
			btnGenerateReport.setBounds(447, 145, 160, 30);
			btnGenerateReport.setVisible(false);
			panel2.add(btnGenerateReport);
			
			
			btnExportToExcel = new JButton("Export to Excel");
			btnExportToExcel.setForeground(new Color(255, 255, 255));
			btnExportToExcel.setBackground(new Color(0, 128, 128));
			btnExportToExcel.setFont(new Font("Arial", Font.BOLD, 15));
			btnExportToExcel.setBounds(447, 86, 160, 30);
			panel2.add(btnExportToExcel);
			
		
		lblDateInstructions = new JTextPane();
		lblDateInstructions.setBackground(new Color(102, 205, 170));
		lblDateInstructions.setForeground(Color.BLACK);
		lblDateInstructions.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDateInstructions.setBounds(165, 240, 383, 122);
		lblDateInstructions.setText("Intructions: (mm/dd/yy)\r\n\r\n *For all records leave both fields blank\r\n*For a specific date fill one of the fields\r\n*For records between 2 dates fill both fields");
		lblDateInstructions.setVisible(false);	
		panel2.add(lblDateInstructions);
		
		boxDepReport = new JComboBox();
		boxDepReport.setModel(new DefaultComboBoxModel(new String[] {"Select Department"}));
		boxDepReport.setBackground(new Color(255, 228, 181));
		boxDepReport.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxDepReport.addItem("Select Department");
		boxDepReport.addItem("Deli");
		boxDepReport.addItem("Meat");
		boxDepReport.addItem("Produce");
		boxDepReport.addItem("Kitchen/HMR");
		boxDepReport.addItem("Seafood");
		boxDepReport.addItem("Coffee");
		boxDepReport.setEditable(false);
		boxDepReport.setBounds(274, 145, 160, 30);		
		panel2.add(boxDepReport);
		
		lblReports = new JLabel("");
		lblReports.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReports.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblReports.setBounds(98, 145, 166, 30);
		panel2.add(lblReports);
		
		boxWorkstationReport = new JComboBox();
		boxWorkstationReport.setBackground(new Color(255, 228, 181));
		boxWorkstationReport.setModel(new DefaultComboBoxModel(new String[] {"Select One"}));
		boxWorkstationReport.setMaximumRowCount(15);
		boxWorkstationReport.setBounds(274, 145, 160, 29);
		boxWorkstationReport.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxWorkstationReport.setEditable(false);
		panel2.add(boxWorkstationReport);
		
		txtScaleReport = new JTextField();		
		txtScaleReport.setBackground(new Color(240, 255, 255));
		txtScaleReport.setHorizontalAlignment(SwingConstants.RIGHT);
		txtScaleReport.setFont(new Font("Segoe UI", Font.BOLD, 15));
		txtScaleReport.setBounds(274, 145, 160, 30);
		panel2.add(txtScaleReport);
		txtScaleReport.setColumns(10);
		
		try{
		btnKeyboard2 = new JButton (new ImageIcon(ImageIO.read(new File("C:\\Scale Inventory\\keyboard.jpg"))));		
		btnKeyboard2.setBackground(new Color(222, 184, 135));
		btnKeyboard2.setBounds(35, 133, 147, 50);
		panel2.add(btnKeyboard2);
		btnKeyboard2.setVisible(false);
		}catch (Exception k){
			k.printStackTrace();
			return;
		}
		
		checkIncludeNonSAP = new JCheckBox("Also include scales not inspected by federal agencies");
		checkIncludeNonSAP.setForeground(new Color(255, 255, 255));
		checkIncludeNonSAP.setBackground(new Color(0, 139, 139));
		checkIncludeNonSAP.setBounds(274, 190, 333, 23);
		checkIncludeNonSAP.setVisible(false);
		panel2.add(checkIncludeNonSAP);	
		
			
		//maximize window
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);	
	
		//panel3
		panel3 =   new JPanel();		
		panel3.setBackground(new Color(255, 222, 173));
		tab.addTab("Manage Scales", null, panel3, null);
		panel3.setLayout(null);		
		
		lbltitle3 = new JLabel("Manage Scales");
		lbltitle3.setHorizontalAlignment(SwingConstants.CENTER);
		lbltitle3.setFont(new Font("Arial Black", Font.BOLD, 30));
		lbltitle3.setBounds(89, 5, 564, 35);
		panel3.add(lbltitle3);
		
		lblMSQuickAction = new JLabel("Quick Actions:");
		lblMSQuickAction.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMSQuickAction.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblMSQuickAction.setBounds(109, 416, 125, 21);
		panel3.add(lblMSQuickAction);
		
		boxMSQuickAction = new JComboBox();
		boxMSQuickAction.setBackground(new Color(255, 228, 181));
		boxMSQuickAction.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxMSQuickAction.setModel(new DefaultComboBoxModel(new String[] {"Select Action", "Add New Scale to Inventory", "Set Scale as Good", "Scale was replaced/Removed", "Ship out Scale for repair", "Scale came back from repair", "Fix spelling for a scale", "Scale Decommission"}));
		boxMSQuickAction.setBounds(260, 410, 199, 35);
		panel3.add(boxMSQuickAction);
		
		lblMSSN = new JLabel("Scale SN:");
		lblMSSN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMSSN.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblMSSN.setBounds(109, 460, 125, 30);
		panel3.add(lblMSSN);
		
		txtMSQuickAction = new JTextField();
		txtMSQuickAction.setBackground(new Color(240, 255, 255));
		txtMSQuickAction.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtMSQuickAction.setBounds(259, 461, 200, 30);
		panel3.add(txtMSQuickAction);
		
		
		btnMSQuickAction = new JButton("Perform Action");		
		btnMSQuickAction.setForeground(new Color(255, 255, 255));
		btnMSQuickAction.setBackground(new Color(0, 139, 139));
		btnMSQuickAction.setFont(new Font("Arial", Font.BOLD, 15));
		btnMSQuickAction.setBounds(482, 462, 147, 30);
		panel3.add(btnMSQuickAction);
		
		try{
			btnKeyboard3 = new JButton (new ImageIcon(ImageIO.read(new File("C:\\Scale Inventory\\keyboard.jpg"))));					
			btnKeyboard3.setBackground(new Color(222, 184, 135));
			btnKeyboard3.setBounds(482, 399, 147, 50);
			btnKeyboard3.setVisible(true);
			panel3.add(btnKeyboard3);
		}catch (Exception k){
			k.printStackTrace();
			return;
		}
		

			lblMSEditData = new JLabel("Edit Scale Data:");
			lblMSEditData.setHorizontalAlignment(SwingConstants.CENTER);
			lblMSEditData.setFont(new Font("Segoe UI", Font.BOLD, 17));
			lblMSEditData.setBounds(292, 332, 131, 21);
			panel3.add(lblMSEditData);
			
			txtMSSN = new JTextField();					
			txtMSSN.setText("SN");
			txtMSSN.setHorizontalAlignment(SwingConstants.CENTER);
			txtMSSN.setToolTipText("");
			txtMSSN.setColumns(15);
			txtMSSN.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			txtMSSN.setBackground(new Color(240, 255, 255));
			txtMSSN.setBounds(10, 360, 63, 30);
			panel3.add(txtMSSN);
			
			txtMSWorkstation = new JTextField();
			txtMSWorkstation.setText("Workstation");
			txtMSWorkstation.setToolTipText("");
			txtMSWorkstation.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			txtMSWorkstation.setColumns(15);
			txtMSWorkstation.setBackground(new Color(240, 255, 255));
			txtMSWorkstation.setBounds(72, 360, 63, 30);
			panel3.add(txtMSWorkstation);
			
			
			txtMSWarranty = new JTextField();
			txtMSWarranty.setText("Date War.");
			txtMSWarranty.setToolTipText("");
			txtMSWarranty.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			txtMSWarranty.setColumns(10);
			txtMSWarranty.setBackground(new Color(240, 255, 255));
			txtMSWarranty.setBounds(495, 360, 76, 30);
			panel3.add(txtMSWarranty);
			
			txtMSPurchased = new JTextField();
			txtMSPurchased.setText("Date Pur.");
			txtMSPurchased.setToolTipText("");
			txtMSPurchased.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			txtMSPurchased.setColumns(10);
			txtMSPurchased.setBackground(new Color(240, 255, 255));
			txtMSPurchased.setBounds(570, 360, 76, 30);
			panel3.add(txtMSPurchased);
			
			txtMSDimensions = new JTextField();
			txtMSDimensions.setText("Dim.");
			txtMSDimensions.setToolTipText("");
			txtMSDimensions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			txtMSDimensions.setColumns(20);
			txtMSDimensions.setBackground(new Color(240, 255, 255));
			txtMSDimensions.setBounds(645, 360, 38, 30);
			panel3.add(txtMSDimensions);
			
			try{
			btnMSUpdate = new JButton (new ImageIcon(ImageIO.read(new File("C:\\Scale Inventory\\update.png"))));				
			btnMSUpdate.setBackground(new Color(222, 184, 135));
			btnMSUpdate.setBounds(682, 345, 50, 50);
			panel3.add(btnMSUpdate);
			}catch (Exception i){
				i.printStackTrace();
			return;
			}
			boxMSCategory= new JComboBox();
			boxMSCategory.setModel(new DefaultComboBoxModel(new String[] {"Cat.", "1", "2", "3"}));
			boxMSCategory.setSelectedIndex(0);
			boxMSCategory.setFont(new Font("Segoe UI", Font.BOLD, 14));
			boxMSCategory.setBackground(new Color(255, 228, 181));
			boxMSCategory.setBounds(134, 360, 51, 30);			
			panel3.add(boxMSCategory);
			
			boxMSCondition = new JComboBox();
			boxMSCondition.setModel(new DefaultComboBoxModel(new String[] {"Status", "Bad", "Check", "OFS", "Good", "Decomm."}));
			boxMSCondition.setSelectedIndex(0);
			boxMSCondition.setFont(new Font("Segoe UI", Font.BOLD, 14));
			boxMSCondition.setBackground(new Color(255, 228, 181));
			boxMSCondition.setBounds(184, 360, 65, 30);
			panel3.add(boxMSCondition);
			
			boxMSCap = new JComboBox();
			boxMSCap.setModel(new DefaultComboBoxModel(new String[] {"Cap.", "20", "30", "60", "130", "200", "44", "13.2", "2.2", "Other"}));
			boxMSCap.setFont(new Font("Segoe UI", Font.BOLD, 14));
			boxMSCap.setBackground(new Color(255, 228, 181));
			boxMSCap.setBounds(438, 360, 57, 30);
			panel3.add(boxMSCap);
			
			boxMSManufacturer = new JComboBox();			
			boxMSManufacturer.setModel(new DefaultComboBoxModel(new String[] {"Manufacturer", "Pennsylvania", "Weigh-Tronix", "CAS", "Ohaus", "A&D", "Other"}));
			boxMSManufacturer.setFont(new Font("Segoe UI", Font.BOLD, 14));
			boxMSManufacturer.setBackground(new Color(255, 228, 181));
			boxMSManufacturer.setBounds(248, 360, 118, 30);
			panel3.add(boxMSManufacturer);
			
			boxMSModel = new JComboBox();
			boxMSModel.setModel(new DefaultComboBoxModel(new String[] {"Model", "7300", "ZQ375", "ZQ3275", "CKW-55", "LP-1000N", "SW-1N", "SC-60KAL", "SK-20KWP", "HL-1000WP", "Other"}));
			boxMSModel.setFont(new Font("Segoe UI", Font.BOLD, 14));
			boxMSModel.setBackground(new Color(255, 228, 181));
			boxMSModel.setBounds(365, 360, 75, 30);
			panel3.add(boxMSModel);
			
			boxFilterSite3 = new JComboBox();
			boxFilterSite3.setModel(new DefaultComboBoxModel());
			boxFilterSite3.setFont(new Font("Segoe UI", Font.BOLD, 14));
			boxFilterSite3.setBackground(new Color(255, 228, 181));
			boxFilterSite3.setBounds(577, 291, 147, 25);
			boxFilterSite3.addItem("Show all Sites");			
			panel3.add(boxFilterSite3);
			//get list of sites
			try{
				ResultSet rs = db.getSitetList();
				while (rs.next()){
					boxFilterSite3.addItem(rs.getString(1));
				}
			}catch (Exception site){
				site.getStackTrace();
			}	
			
			btnMSRefresh = new JButton("Refresh Table");			
			btnMSRefresh.setForeground(Color.WHITE);
			btnMSRefresh.setFont(new Font("Arial", Font.BOLD, 15));
			btnMSRefresh.setBackground(new Color(0, 139, 139));
			btnMSRefresh.setBounds(10, 326, 147, 25);
			panel3.add(btnMSRefresh);
			
			btnMSExport = new JButton("Export to Excel");			
			btnMSExport.setForeground(Color.WHITE);
			btnMSExport.setFont(new Font("Arial", Font.BOLD, 15));
			btnMSExport.setBackground(new Color(0, 139, 139));
			btnMSExport.setBounds(516, 326, 147, 25);
			panel3.add(btnMSExport);			
			
			check1Panel3 = new JRadioButton("All Categories");
			check1Panel3.setFont(new Font("Arial", Font.PLAIN, 14));
			check1Panel3.setBackground(new Color(255, 222, 173));
			check1Panel3.setBounds(10, 293, 123, 23);
			panel3.add(check1Panel3);
			
			check2Panel3 = new JRadioButton("Pricing Scales (1)");
			check2Panel3.setBackground(new Color(255, 222, 173));
			check2Panel3.setFont(new Font("Arial", Font.PLAIN, 14));
			check2Panel3.setBounds(130, 293, 138, 23);
			panel3.add(check2Panel3);
			
			check3Panel3 = new JRadioButton("Standalone (2)");
			check3Panel3.setFont(new Font("Arial", Font.PLAIN, 14));
			check3Panel3.setBackground(new Color(255, 222, 173));
			check3Panel3.setBounds(273, 293, 123, 23);
			panel3.add(check3Panel3);
			
			check4Panel3 = new JRadioButton("Small scales (3)");
			check4Panel3.setFont(new Font("Arial", Font.PLAIN, 14));
			check4Panel3.setBackground(new Color(255, 222, 173));
			check4Panel3.setBounds(397, 293, 160, 23);
			panel3.add(check4Panel3);			
			
			groupCheck3 = new ButtonGroup();
			groupCheck3.add(check1Panel3);
			groupCheck3.add(check2Panel3);
			groupCheck3.add(check3Panel3);
			groupCheck3.add(check4Panel3);
			check1Panel3.setSelected(true);
			
			loadPanelTable(3); 
			
		//panel4
		panel4 = new JPanel();
		panel4.setBackground(new Color(204, 204, 255));
		tab.addTab("Manage Workstations", null, panel4, null);
		panel4.setLayout(null);
		
		lbltitle4 = new JLabel("Manage Workstations");
		lbltitle4.setBounds(89, 5, 564, 35);
		lbltitle4.setHorizontalAlignment(SwingConstants.CENTER);
		lbltitle4.setFont(new Font("Arial Black", Font.BOLD, 30));
		panel4.add(lbltitle4);
		
		lblMWQuickAction = new JLabel("Quick Actions:");
		lblMWQuickAction.setBounds(109, 416, 125, 21);
		lblMWQuickAction.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMWQuickAction.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panel4.add(lblMWQuickAction);
		
		boxMWQuickAction = new JComboBox();	
		boxMWQuickAction.setBounds(260, 410, 199, 35);
		boxMWQuickAction.setBackground(new Color(255, 228, 181));
		boxMWQuickAction.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxMWQuickAction.setModel(new DefaultComboBoxModel(new String[] {"Select Action", "Add New Station", "Remove Station", "Rename station", "Add Department", "Add Site"}));
		panel4.add(boxMWQuickAction);
		
		lblMWWorkstation = new JLabel("Workstation Name:");
		lblMWWorkstation.setBounds(10, 460, 224, 30);
		lblMWWorkstation.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMWWorkstation.setFont(new Font("Segoe UI", Font.BOLD, 16));
		panel4.add(lblMWWorkstation);
		
		txtMWQuickAction = new JTextField();
		txtMWQuickAction.setBounds(259, 461, 200, 30);
		txtMWQuickAction.setBackground(new Color(240, 255, 255));
		txtMWQuickAction.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panel4.add(txtMWQuickAction);
		
		try{
			btnKeyboard4 = new JButton (new ImageIcon(ImageIO.read(new File("C:\\Scale Inventory\\keyboard.jpg"))));				
			btnKeyboard4.setBounds(482, 402, 147, 50);
			btnKeyboard4.setBackground(new Color(222, 184, 135));
			btnKeyboard4.setVisible(true);
			panel4.add(btnKeyboard4);
		}catch (Exception k){
			k.printStackTrace();
			return;
		}
		lblMWEditData = new JLabel("Edit Workstation:");
		lblMWEditData.setBounds(287, 328, 163, 21);
		lblMWEditData.setHorizontalAlignment(SwingConstants.CENTER);
		lblMWEditData.setFont(new Font("Segoe UI", Font.BOLD, 17));
		panel4.add(lblMWEditData);
		
		txtMWWorkstation = new JTextField();		
		txtMWWorkstation.setBounds(149, 360, 93, 30);
		txtMWWorkstation.setText("Workstation");
		txtMWWorkstation.setToolTipText("");
		txtMWWorkstation.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtMWWorkstation.setColumns(15);
		txtMWWorkstation.setBackground(new Color(240, 255, 255));
		panel4.add(txtMWWorkstation);
		
		try{
			btnMWUpdate = new JButton (new ImageIcon(ImageIO.read(new File("C:\\Scale Inventory\\update.png"))));				
			btnMWUpdate.setForeground(new Color(204, 204, 255));
			btnMWUpdate.setBounds(549, 347, 50, 50);
			btnMWUpdate.setBackground(new Color(204, 204, 255));
			panel4.add(btnMWUpdate);
			}catch (Exception i){
				i.printStackTrace();
			return;
			}
		
		boxMWCategory = new JComboBox();
		boxMWCategory.setBounds(456, 360, 93, 30);
		boxMWCategory.setModel(new DefaultComboBoxModel(new String[] {"Category", "1", "2", "3"}));
		boxMWCategory.setSelectedIndex(0);
		boxMWCategory.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxMWCategory.setBackground(new Color(255, 228, 181));
		panel4.add(boxMWCategory);
		
		boxMWDep = new JComboBox();			
		boxMWDep.setBounds(240, 360, 118, 30);
		boxMWDep.setModel(new DefaultComboBoxModel(new String[] {"Department"}));
		boxMWDep.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxMWDep.setBackground(new Color(255, 228, 181));
		panel4.add(boxMWDep);
		
		btnMWRefresh = new JButton("Refresh Table");		
		btnMWRefresh.setBounds(10, 323, 147, 30);
		btnMWRefresh.setForeground(Color.WHITE);
		btnMWRefresh.setFont(new Font("Arial", Font.BOLD, 15));
		btnMWRefresh.setBackground(new Color(0, 139, 139));
		panel4.add(btnMWRefresh);
		
		check1Panel4 = new JRadioButton("All Categories");
		check1Panel4.setFont(new Font("Arial", Font.PLAIN, 14));
		check1Panel4.setBackground(new Color(204, 204, 255));
		check1Panel4.setBounds(14, 293, 115, 23);
		panel4.add(check1Panel4);
		
		check2Panel4 = new JRadioButton("Pricing Stations(1)");
		check2Panel4.setBounds(132, 293, 142, 23);
		check2Panel4.setBackground(new Color(204, 204, 255));
		check2Panel4.setFont(new Font("Arial", Font.PLAIN, 14));
		panel4.add(check2Panel4);
		
		check3Panel4 = new JRadioButton("Standalone S.(2)");
		check3Panel4.setBounds(278, 293, 140, 23);
		check3Panel4.setFont(new Font("Arial", Font.PLAIN, 14));
		check3Panel4.setBackground(new Color(204, 204, 255));
		panel4.add(check3Panel4);
		
		check4Panel4 = new JRadioButton("Special Cat.(3)");
		check4Panel4.setBounds(416, 293, 130, 23);
		check4Panel4.setFont(new Font("Arial", Font.PLAIN, 14));
		check4Panel4.setBackground(new Color(210, 204, 255));
		panel4.add(check4Panel4);		
		
		groupCheck4= new ButtonGroup();
		groupCheck4.add(check1Panel4);
		groupCheck4.add(check2Panel4);
		groupCheck4.add(check3Panel4);
		groupCheck4.add(check4Panel4);
		check1Panel4.setSelected(true);
		
		btnMWQuickAction = new JButton("Perform Action");			
		btnMWQuickAction.setBounds(482, 462, 147, 30);
		btnMWQuickAction.setForeground(new Color(255, 255, 255));
		btnMWQuickAction.setBackground(new Color(0, 139, 139));
		btnMWQuickAction.setFont(new Font("Arial", Font.BOLD, 15));
		panel4.add(btnMWQuickAction);
		
		areaCategory = new JTextArea();		
		areaCategory.setBackground(new Color(240, 255, 255));
		areaCategory.setFont(new Font("Segoe UI", Font.BOLD, 13));
		areaCategory.setBounds(450, 57, 268, 233);
		areaCategory.setText("  Special Category Examples:\n  0 - IT storage\n  0 - Manufacturers\n  0 - A&Ds\n\n  Justification:\n All scales need to have a station assigned.\n On this system each seller/manufacturer\n is represented by a station and A&D \n scales don't need a station, hence 'n/a'\n is the labeling of their station");
		panel4.add(areaCategory);
		
		boxMWSite = new JComboBox();
		boxMWSite.setModel(new DefaultComboBoxModel(new String[] {"Site"}));
		boxMWSite.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxMWSite.setBackground(new Color(255, 228, 181));
		boxMWSite.setBounds(357, 360, 100, 30);
		panel4.add(boxMWSite);
		
		boxFilterSite4 = new JComboBox();
		boxFilterSite4.setModel(new DefaultComboBoxModel());
		boxFilterSite4.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxFilterSite4.setBackground(new Color(255, 228, 181));
		boxFilterSite4.setBounds(571, 291, 147, 25);
		boxFilterSite4.addItem("Show all Sites");			
		panel4.add(boxFilterSite4);
		//get list of sites
		try{
			ResultSet rs = db.getSitetList();
			while (rs.next()){
				boxFilterSite4.addItem(rs.getString(1));
			}
		}catch (Exception site){
			site.getStackTrace();
		}			
		loadPanelTable(4);
		loadDepartments(4);
		
		//panel5
		panel5 = new JPanel();			
		panel5.setBackground(new Color(210, 105, 30));
		tab.addTab("Troubleshooting Records", null, panel5, null);
		panel5.setLayout(null);
		
		lblTitle5 = new JLabel("Scale Repair Records");
		lblTitle5.setBounds(89, 5, 564, 35);
		lblTitle5.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle5.setFont(new Font("Arial Black", Font.BOLD, 30));
		panel5.add(lblTitle5);	
		
		check1Panel5 = new JRadioButton("All Categories");
		check1Panel5.setFont(new Font("Arial", Font.PLAIN, 14));
		check1Panel5.setBackground(new Color(210, 105, 30));
		check1Panel5.setBounds(10, 293, 123, 23);
		panel5.add(check1Panel5);
		
		check2Panel5 = new JRadioButton("Pricing Scales (1)");
		check2Panel5.setBackground(new Color(210, 105, 30));
		check2Panel5.setFont(new Font("Arial", Font.PLAIN, 14));
		check2Panel5.setBounds(130, 293, 138, 23);
		panel5.add(check2Panel5);
		
		check3Panel5 = new JRadioButton("Standalone (2)");
		check3Panel5.setFont(new Font("Arial", Font.PLAIN, 14));
		check3Panel5.setBackground(new Color(210, 105, 30));
		check3Panel5.setBounds(273, 293, 123, 23);
		panel5.add(check3Panel5);
		
		check4Panel5 = new JRadioButton("Small scales (3)");
		check4Panel5.setFont(new Font("Arial", Font.PLAIN, 14));
		check4Panel5.setBackground(new Color(210, 105, 30));
		check4Panel5.setBounds(397, 293, 160, 23);
		panel5.add(check4Panel5);			
		
		groupCheck5= new ButtonGroup();
		groupCheck5.add(check1Panel5);
		groupCheck5.add(check2Panel5);
		groupCheck5.add(check3Panel5);
		groupCheck5.add(check4Panel5);
		check1Panel5.setSelected(true);
		
		checkRRNewUpdate = new JRadioButton("New Record");		
		checkRRNewUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		checkRRNewUpdate.setFont(new Font("Arial Black", Font.BOLD, 14));
		checkRRNewUpdate.setBackground(new Color(210, 105, 30));
		checkRRNewUpdate.setBounds(58, 360, 147, 23);
		panel5.add(checkRRNewUpdate);
		
		btnRRRefresh = new JButton("Refresh Table");		
		btnRRRefresh.setBounds(10, 323, 147, 30);
		btnRRRefresh.setForeground(new Color(255, 255, 255));
		btnRRRefresh.setFont(new Font("Arial", Font.BOLD, 15));
		btnRRRefresh.setBackground(new Color(0, 0, 0));
		panel5.add(btnRRRefresh);
		
		btnRRExport = new JButton("Export to Excel");		
		btnRRExport.setForeground(Color.WHITE);
		btnRRExport.setFont(new Font("Arial", Font.BOLD, 15));
		btnRRExport.setBackground(new Color(0, 0, 0));
		btnRRExport.setBounds(536, 323, 147, 30);
		panel5.add(btnRRExport);	
		
		txtRRID = new JTextField();
		txtRRID.setHorizontalAlignment(SwingConstants.LEFT);
		txtRRID.setText("Column 1");
		txtRRID.setBounds(20, 408, 64, 30);
		txtRRID.setToolTipText("");
		txtRRID.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtRRID.setColumns(15);
		txtRRID.setBackground(new Color(240, 255, 255));
		panel5.add(txtRRID);
		
		txtRRSN = new JTextField();		
		txtRRSN.setText("#########");
		txtRRSN.setBounds(89, 408, 78, 30);
		txtRRSN.setToolTipText("");
		txtRRSN.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtRRSN.setColumns(15);
		txtRRSN.setBackground(new Color(240, 255, 255));
		panel5.add(txtRRSN);
		
		txtDownSince = new JTextField();		
		txtDownSince.setText("mm/dd/yy");
		txtDownSince.setBounds(89, 465, 78, 30);
		txtDownSince.setToolTipText("");
		txtDownSince.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtDownSince.setColumns(15);
		txtDownSince.setBackground(new Color(240, 255, 255));
		panel5.add(txtDownSince);
		
		boxRRStatus = new JComboBox();			
		boxRRStatus.setModel(new DefaultComboBoxModel(new String[] {"Not Inspected", "In Progress", "RMA Requested", "Shipped Out", "Completed"}));
		boxRRStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxRRStatus.setBackground(new Color(255, 228, 181));
		boxRRStatus.setBounds(172, 408, 118, 30);
		panel5.add(boxRRStatus);
		
		txtSymptom = new JTextField();		
		txtSymptom.setText("How was the issue identified  (or issues)");
		txtSymptom.setBounds(414, 374, 294, 30);
		txtSymptom.setToolTipText("");
		txtSymptom.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtSymptom.setColumns(150);
		txtSymptom.setBackground(new Color(240, 255, 255));
		panel5.add(txtSymptom);
		
		txtRootCause = new JTextField();		
		txtRootCause.setText("What caused the issue(s)");
		txtRootCause.setBounds(414, 403, 294, 30);
		txtRootCause.setToolTipText("");
		txtRootCause.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtRootCause.setColumns(150);
		txtRootCause.setBackground(new Color(240, 255, 255));
		panel5.add(txtRootCause);
		
		txtResolution = new JTextField();		
		txtResolution.setText("What action(s) led to the resolution");
		txtResolution.setBounds(414, 431, 294, 30);
		txtResolution.setToolTipText("");
		txtResolution.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtResolution.setColumns(150);
		txtResolution.setBackground(new Color(240, 255, 255));
		panel5.add(txtResolution);
		
		txtRepairedDate = new JTextField();		
		txtRepairedDate.setText("mm/dd/yy");
		txtRepairedDate.setBounds(172, 465, 78, 30);
		txtRepairedDate.setToolTipText("");
		txtRepairedDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtRepairedDate.setColumns(15);
		txtRepairedDate.setBackground(new Color(240, 255, 255));
		panel5.add(txtRepairedDate);
		
		txtRepairedBy = new JTextField();		
		txtRepairedBy.setText("Initials");
		txtRepairedBy.setBounds(20, 465, 64, 30);
		txtRepairedBy.setToolTipText("");
		txtRepairedBy.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtRepairedBy.setColumns(15);
		txtRepairedBy.setBackground(new Color(240, 255, 255));
		panel5.add(txtRepairedBy);
		
		btnRRAddRecord  = new JButton("Update Record");			
		btnRRAddRecord.setBounds(563, 469, 145, 30);
		btnRRAddRecord.setForeground(new Color(255, 255, 255));
		btnRRAddRecord.setBackground(new Color(0, 0, 0));
		btnRRAddRecord.setFont(new Font("Arial", Font.BOLD, 15));
		panel5.add(btnRRAddRecord);
		
		try{
			btnKeyboard5 = new JButton (new ImageIcon(ImageIO.read(new File("C:\\Scale Inventory\\keyboard.jpg"))));		
			btnKeyboard5.setBackground(new Color(222, 184, 135));
			btnKeyboard5.setBounds(414, 469, 145, 30);
			panel5.add(btnKeyboard5);			
			}catch (Exception k){
				k.printStackTrace();
				return;
			}
		lblRRRecords = new JLabel("Add/Update Repairs Logs:");
		lblRRRecords.setHorizontalAlignment(SwingConstants.CENTER);
		lblRRRecords.setFont(new Font("Segoe UI", Font.BOLD, 19));
		lblRRRecords.setBounds(215, 332, 263, 21);
		panel5.add(lblRRRecords);	
		
		
		lblSymptom = new JLabel("Symptom(s):");
		lblSymptom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSymptom.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSymptom.setBounds(311, 379, 93, 21);
		panel5.add(lblSymptom);
		
		lblRootCause = new JLabel("Root Cause(s):");
		lblRootCause.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRootCause.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblRootCause.setBounds(300, 406, 104, 21);
		panel5.add(lblRootCause);
		
		lblResolution = new JLabel("Resolution:");
		lblResolution.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResolution.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblResolution.setBounds(311, 436, 93, 21);
		panel5.add(lblResolution);
		
		lblRepairedBy = new JLabel("Tech:");
		lblRepairedBy.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblRepairedBy.setBounds(20, 441, 64, 21);
		panel5.add(lblRepairedBy);
		
		lblDownSince = new JLabel("Down Since");
		lblDownSince.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDownSince.setBounds(89, 441, 78, 21);
		panel5.add(lblDownSince);
		
		lblDateRepaired = new JLabel("Repaired Date:");
		lblDateRepaired.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDateRepaired.setBounds(172, 441, 118, 21);
		panel5.add(lblDateRepaired);
		
		lblRRID = new JLabel("Record#:");
		lblRRID.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblRRID.setBounds(20, 384, 64, 21);
		panel5.add(lblRRID);
		
		lblRRSN = new JLabel("Scale SN:");
		lblRRSN.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblRRSN.setBounds(89, 384, 64, 21);
		panel5.add(lblRRSN);
		
		lblRRStatus = new JLabel("Repair Status:");
		lblRRStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblRRStatus.setBounds(172, 384, 118, 21);
		panel5.add(lblRRStatus);
		
		checkToday = new JRadioButton("Today");		
		checkToday.setHorizontalAlignment(SwingConstants.LEFT);
		checkToday.setFont(new Font("Arial Black", Font.BOLD, 14));
		checkToday.setBackground(new Color(210, 105, 30));
		checkToday.setBounds(257, 471, 86, 23);
		panel5.add(checkToday);
		
		boxFilterSite5 = new JComboBox();
		boxFilterSite5.setModel(new DefaultComboBoxModel());
		boxFilterSite5.setFont(new Font("Segoe UI", Font.BOLD, 14));
		boxFilterSite5.setBackground(new Color(255, 228, 181));
		boxFilterSite5.setBounds(577, 291, 147, 25);
		boxFilterSite5.addItem("Show all Sites");			
		panel5.add(boxFilterSite5);
		//get list of sites
		try{
			ResultSet rs = db.getSitetList();
			while (rs.next()){
				boxFilterSite5.addItem(rs.getString(1));
			}
		}catch (Exception site){
			site.getStackTrace();
		}	
		
		loadPanelTable(5);
		
		frame.setVisible(true);
		setScalePMDefaults();
		//event listeners:
		
		//panel 1:
		//tech ID input validation
		btnID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String techID = txtID.getText();
				if ((techID.length()!=0) && (techID.length() <10)){
					// repopulate Department list
					if(boxFilterSite1.getSelectedIndex()!=-1){
					site = boxFilterSite1.getSelectedItem().toString();
					}
					loadDepartments(1);
					boxDep.setSelectedIndex(0);
				lblDepartment.setVisible(true);
				boxDep.setVisible(true);
				}
				else{ 
					JOptionPane.showMessageDialog(null, "Enter the initials of your name and try again");
					return;
				}
			}
		});
		
		boxDep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boxDep.getSelectedIndex()!=0){
				try{
					if (boxWorkstation.getItemCount()!=0){
						do{
							boxWorkstation.removeItemAt(0);							
						} while (boxWorkstation.getItemCount()!=0);
						}
					if(boxDep.isFocusOwner()){
					if(boxDep.getSelectedIndex()>0){
					workstationsCollection = db.getAllWorkstations(boxDep.getSelectedItem().toString(), boxFilterSite1.getSelectedItem().toString());
					for (int n = 0;n<db.getRowCount(); n++){
						try{
							boxWorkstation.addItem(workstationsCollection[n].toString());
							}catch(Exception h){
								h.printStackTrace();								
								}
						}
					}
					System.out.println(boxWorkstation.getItemCount() +" records loaded");
					
				
				}else{
					return;
				}
				} catch (Exception ex){
					ex.printStackTrace();
					return;
					}				
			 		System.out.println("box workstation was populated");
			 		boxWorkstation.setSelectedIndex(0);
					lblWorkstation.setVisible(true);
					boxWorkstation.setVisible(true);
					txtID.setEnabled(false);
					btnID.setEnabled(false);
					boxFilterSite1.setEnabled(false);
					techName = txtID.getText().trim();	
				}else{
					lblWorkstation.setVisible(false);
					boxWorkstation.setVisible(false);
			}
			
				
			}
		});
		
		//looks up last scale SN on record;
		boxWorkstation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boxWorkstation.getSelectedIndex()>-1){
				try{
				String department = boxDep.getSelectedItem().toString();
				System.out.println("worstation selected");
				String selection = boxWorkstation.getSelectedItem().toString();
				System.out.println("selected: "+selection);
				String scaleSN = db.lookUpLastSN(department, selection);
				System.out.println("function called");
				txtSN.setText(scaleSN);
				System.out.println("SN parsed");
				txtSN.setVisible(true);
				lblSN.setVisible(true);
				btnSNYes.setVisible(true);
				btnSNNo.setVisible(true);				
				lblSNVerification.setVisible(true);	
				} catch (Exception ex){
					return;
				}
				}else{
					lblSN.setVisible(false);
					txtSN.setVisible(false);
					lblSNVerification.setVisible(false);
					btnSNYes.setVisible(false);
					btnSNNo.setVisible(false);
				}
				
			}
		});
		//button Yes pressed for serial number verification
		btnSNYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblReading.setVisible(true);
				txtReading.setVisible(true);
				lblLbs.setVisible(true);
				lblLbs2.setVisible(true);
				boxDep.setEnabled(false);
				txtReading.requestFocus();					
				txtReading.requestFocusInWindow();
								
			}
		});
		//button No pressed for serial number verification
		btnSNNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db.setOldSN(txtSN.getText());
				lblSNVerification.setVisible(false);
				btnSNYes.setVisible(false);
				btnSNNo.setVisible(false);
				lblNewSN.setVisible(true);
				btnDone.setVisible(true);
				txtSN.setEditable(true);
				boxDep.setEnabled(false);
				txtSN.setEnabled(true);
				operationInProgress=2;
			}
		});
		//after users enters new SN#
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				newSN = txtSN.getText().trim();
				newScale = true;
				JOptionPane.showMessageDialog(null, "Thank you for your collaboration!");								
				btnDone.setVisible(false);
				lblReason.setVisible(false);				
				txtSN.setEditable(false);
				check3Panel1.setText("Misspelled SN");
				check5Panel1.setText("Scale was replaced");
				check4Panel1.setVisible(false);
				showCheckBoxes();
				lblNewSN.setVisible(false);
				btnProceed.setVisible(true);
				groupCheck.clearSelection();
				db.updateDBRow("Scales", "Workstation", boxWorkstation.getSelectedItem().toString(), newSN);
				System.out.println("Defined as new scale");
			}
		});
		//add notes to the inspection of the scale
		btnProceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//to report adjustments necessary for good readings
				if (operationInProgress==1){
					if(check1Panel1.isSelected()){
						dbNotes += check1Panel1.getText() +" was required for a good reading; ";
					}else if(check2Panel1.isSelected()){
						dbNotes += check2Panel1.getText()+" was required for a good reading; ";
					}else if(check3Panel1.isSelected()){
						dbNotes += check3Panel1.getText()+" was required for a good reading; ";
					}else if(check4Panel1.isSelected()){
						dbNotes += JOptionPane.showInputDialog("Please specify") +" was required for a good reading; ";						
					}else if(check5Panel1.isSelected()){
						dbNotes += check5Panel1.getText()+" was required for a good reading; ";
					}	
					btnNext.setVisible(true);
					btnExit.setVisible(true);
				//to report serial number mismatch
				}else if (operationInProgress==2){
					//Serial number was mistyped on previous record
					if(check3Panel1.isSelected()){
						dbNotes += "SN number was wrong; ";
						lblReading.setVisible(true);
						txtReading.setVisible(true);
						btnProceed.setVisible(false);
						lblLbs.setVisible(true);
						changeSN = 1;
						check1Panel1.setVisible(false);
						check3Panel1.setVisible(false);
						check5Panel1.setVisible(false);
						check4Panel1.setVisible(false);
						System.out.println("Defined as SN mismatch");
						//other reason not presented						
					}else if(check5Panel1.isSelected()){
						dbNotes += "Scale was replaced because of ";
						check1Panel1.setText("Unknown reasons");
						check2Panel1.setText("No power");
						check3Panel1.setText("Unit not calibrated");
						check5Panel1.setText("No serial communication");
						check1Panel1.setVisible(true);
						check2Panel1.setVisible(true);
						operationInProgress=3;
						changeSN = 2;
						groupCheck.clearSelection();
						System.out.println("Defined as scale replacement");
					}else{
						dbNotes += JOptionPane.showInputDialog("Please specify") +"; ";	
						lblReading.setVisible(true);
						txtReading.setVisible(true);
						btnProceed.setVisible(false);
						lblLbs.setVisible(true);
						changeSN=1;		
						check1Panel1.setVisible(false);
						check3Panel1.setVisible(false);
						check5Panel1.setVisible(false);
						check4Panel1.setVisible(false);
					}
					//to report why was the scale replaced
				}else if(operationInProgress==3){
					//other reson not presented
					if(check4Panel1.isSelected()){
						dbNotes += JOptionPane.showInputDialog("Please specify") +"; ";					
					}else	if(check1Panel1.isSelected()){
						dbNotes += " unknown reasons; ";						
					} else if(check2Panel1.isSelected()){
						dbNotes +=  "Scale was replaced due to no power; ";
					}else if(check3Panel1.isSelected()){
						dbNotes +=  "Scale was replaced due to not calibrated; ";
					}else  if(check5Panel1.isSelected()){
						dbNotes +=  "Scale was replaced due to no serial communication ";
					}
					check1Panel1.setVisible(false);
					check2Panel1.setVisible(false);
					check3Panel1.setVisible(false);
					check4Panel1.setVisible(false);
					check5Panel1.setVisible(false);
					lblLbs.setVisible(true);
					txtReading.setVisible(true);
					lblReading.setVisible(true);
					btnProceed.setVisible(false);
				}
			
			}
		});
		//hiding the checks and other stuff that is no longer necessary
		txtReading.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblWeight.setVisible(true);
				txtWeight.setVisible(true);
				lblLbs2.setVisible(true);
				txtSN.setEnabled(false);
				lblSNVerification.setVisible(false);
				btnSNYes.setVisible(false);
				btnSNNo.setVisible(false);
				btnDone.setVisible(false);
				lblNewSN.setVisible(false);
				dep = boxDep.getSelectedItem().toString();
				SN = txtSN.getText();
				workstation = boxWorkstation.getSelectedItem().toString();
				boxWorkstation.setEnabled(false);
				lblAdj.setVisible(true);
				btnAdjYes.setVisible(true);
				btnAdjNo.setVisible(true);
				lblReason.setVisible(false);					
			}
		});
		txtReading.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					btnAdjNo.doClick();
				}
			}
		});		
		//button Yes on adjustments
		btnAdjYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				operationInProgress=1;				
				check1Panel1.setText("Adjusted legs");
				check2Panel1.setText("Reboot scale");
				check3Panel1.setText("Added missing rubber");				
				check5Panel1.setText("Cleaned moisture");			
				showCheckBoxes();
				check1Panel1.setVisible(true);
				check2Panel1.setVisible(true);
				lblReason.setVisible(true);
				groupCheck.clearSelection();		
				boxWorkstation.setEnabled(false);
				btnProceed.setVisible(true);
			}
		});
		btnAdjNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				btnNext.setVisible(true);
				btnExit.setVisible(true);						
				boxWorkstation.setEnabled(false);
			}
		});
		//button that defaults all values
			btnStartOver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setScalePMDefaults();
					System.out.println("success");
					txtSN.setVisible(false);
					lblSN.setVisible(false);
					btnSNYes.setVisible(false);
					btnSNNo.setVisible(false);
					lblSNVerification.setVisible(false);
					txtWeight.setVisible(false);
					txtID.requestFocusInWindow();						
					placeCursor(txtID);
				}
			});
			//submit data to db and go to next scale
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if((txtReading.getText().length()<1)||(txtWeight.getText().length()<1)){
						JOptionPane.showInputDialog(ERROR,"Reading or Weight cannot be blank");
						return;				
					}
					try{
					weight = Double.parseDouble(txtWeight.getText().trim());
					reading = Double.parseDouble(txtReading.getText().trim());
					if(db.getDBValue("Scales", "Category", SN)=="1"){
					if(reading < weight - 0.02|| reading > weight + 0.02){
						badReadings++;
						if (badReadings%2 == 1){
							JOptionPane.showMessageDialog(null, "Reading not within the acceptable values. Please make adjustments to scale for a correct reading then replace the scale if necessary. ");
							return;
						}
					}
					}else{
						if(reading < weight - 0.1|| reading > weight + 0.1){
							badReadings++;
							if (badReadings%2 == 1){
								JOptionPane.showMessageDialog(null, "Reading not within the acceptable values. Please make adjustments to scale for a correct reading then replace the scale if necessary. ");
								return;
							}
						}
					}
													
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Value for 'reading' and/or 'weight' not accepted, check values and try again");
						return;
					}			
				
					if(db.InsertReadingRecord(techName, workstation, SN, reading, weight, dbNotes)){
						JOptionPane.showMessageDialog(null, "Record saved successfully");
					}else{
						JOptionPane.showMessageDialog(null, "Could not save new record, if problem persists please contact Nuno. Error: 251");
					}
					//if the SN was modified
					if (newScale==true){
						System.out.println("SetNewScale() called");
						db.setNewScale(boxWorkstation.getSelectedItem().toString(), newSN, changeSN);
						}
					//start the process over
					btnProceed.setVisible(false);
					txtSN.setText("");
					lblWorkstation.setVisible(true);
					boxWorkstation.setEnabled(true);
					lblSN.setVisible(false);								
					lblNewSN.setVisible(false);
					btnDone.setVisible(false);
					lblSNVerification.setVisible(false);
					txtReading.setText("");
					lblLbs.setVisible(false);
					lblLbs2.setVisible(false);
					btnSNYes.setVisible(false);
					btnSNNo.setVisible(false);
					lblReading.setVisible(false);
					txtReading.setVisible(false);
					lblAdj.setVisible(false);
					btnAdjYes.setVisible(false);
					btnAdjNo.setVisible(false);
					lblReason.setVisible(false);					
					groupCheck.clearSelection();				
					btnNext.setVisible(false);
					btnExit.setVisible(false);			
					txtWeight.setText("15.00");
					txtWeight.setVisible(false);
					lblWeight.setVisible(false);							
					lblAdj.setVisible(false);
					btnAdjYes.setVisible(false);
					btnAdjNo.setVisible(false);				
					txtSN.setEnabled(false);
					boxWorkstation.setEnabled(true);	
					int currentWorkstation = boxWorkstation.getSelectedIndex();
					boxWorkstation.removeItemAt(currentWorkstation);
					boxWorkstation.setSelectedIndex(currentWorkstation);
					dbNotes = "";
					newScale = false;
					badReadings = 0;
				}
			});
			//send data to db and set all defaults
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						weight = Double.parseDouble(txtWeight.getText().trim());
						reading = Double.parseDouble(txtReading.getText().trim());
						if(db.getDBValue("Scales", "Category", SN)=="1"){
						if(reading < weight - 0.02|| reading > weight + 0.02){
							badReadings++;
							if (badReadings%2 == 1){
								JOptionPane.showMessageDialog(null, "Reading not within the acceptable values. Please make adjustments to scale for a correct reading then replace the scale if necessary. ");
								return;
							}
						}else{
							if(reading < weight - 0.1|| reading > weight + 0.1){
								badReadings++;
								if (badReadings%2 == 1){
									JOptionPane.showMessageDialog(null, "Reading not within the acceptable values. Please make adjustments to scale for a correct reading then replace the scale if necessary. ");
									return;
								}
							}
						}
						}								
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, "Value for 'reading' and/or 'weight' not accepted, check values and try again");
							return;
						}						
						if(db.InsertReadingRecord(techName, workstation, SN, reading, weight, dbNotes)){
							JOptionPane.showMessageDialog(null, "Record saved successfully");
						}else{
							JOptionPane.showMessageDialog(null, "Could not save new record, if problem persists please contact Nuno. Error: 251");
						}
						if (newScale==true){
						db.setNewScale(boxWorkstation.getSelectedItem().toString(), newSN, changeSN);
						}
						setScalePMDefaults();
						
						
					}
				});
	
//use enter key to submit input
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					btnID.doClick();
					return;
				}
			}
		});
txtSN.addKeyListener(new KeyAdapter() {
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
			btnDone.doClick();
			return;
		}
	
	}
});
txtScaleReport.addKeyListener(new KeyAdapter() {
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
			btnGenerateReport.doClick();
			return;
		}
	}
});
		//panel2:		
		boxReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//show site box and label
				lblSite2.setVisible(true);				
				boxFilterSite2.setVisible(true);
				
				while (boxFilterSite2.getItemCount()!=0){
					boxFilterSite2.removeItemAt(0);
				}
				boxFilterSite2.addItem("All");
				//look up list of sites
				try{
					ResultSet rs = db.getSitetList();					
					while (rs.next()){
						boxFilterSite2.addItem(rs.getString(1));
					}
				}catch (Exception ex){
					ex.getStackTrace();
				}
				
				if(boxReports.getSelectedIndex()==1){	
					if (stblReports!=null){
					stblReports.setVisible(false);		
					}
					btnExportToExcel.setVisible(false);						
					lblFrom.setVisible(true);
					lblTo.setVisible(true);
					txtDate1.setVisible(true);
					txtDate2.setVisible(true);
					lblDateInstructions.setVisible(true);
					lblDateInstructions.setEnabled(true);
					lblReports.setVisible(false);
					boxDepReport.setVisible(false);
					boxWorkstationReport.setVisible(false);
					txtScaleReport.setVisible(false);
					btnGenerateReport.setVisible(true);
					btnKeyboard2.setVisible(true);					
					placeCursor(txtDate1);
					checkIncludeNonSAP.setVisible(true);
					
				}else if (boxReports.getSelectedIndex()==2){
					btnExportToExcel.setVisible(false);					
					lblFrom.setVisible(false);
					lblTo.setVisible(false);
					txtDate1.setVisible(false);
					txtDate2.setVisible(false);
					lblDateInstructions.setVisible(false);
					lblDateInstructions.setEnabled(false);
					lblReports.setVisible(true);
					lblReports.setText("Select Department:");
					boxDepReport.setVisible(true);
					boxWorkstationReport.setVisible(false);
					txtScaleReport.setVisible(false);
					btnGenerateReport.setVisible(true);
					btnKeyboard2.setVisible(false);
					checkIncludeNonSAP.setVisible(true);
					loadDepartments(2);
				}else if(boxReports.getSelectedIndex()==3){
					btnExportToExcel.setVisible(false);					
					lblFrom.setVisible(false);
					lblTo.setVisible(false);
					txtDate1.setVisible(false);
					txtDate2.setVisible(false);
					checkIncludeNonSAP.setVisible(false);
					lblDateInstructions.setVisible(false);
					lblDateInstructions.setEnabled(false);
					lblReports.setVisible(true);
					lblReports.setText("Select Workstation:");
					boxDepReport.setVisible(false);
					boxWorkstationReport.setVisible(true);					
					txtScaleReport.setVisible(false);
					btnGenerateReport.setVisible(true);
					int n =0;
					try{						
							if (boxWorkstationReport.getItemCount()!=1){							
								while (boxWorkstationReport.getItemCount()!=1){						
									 Object boxItem = boxWorkstationReport.getItemAt(1);	
									 boxWorkstationReport.removeItem(boxItem);									
							}
						}						
						workstationsCollection = db.getAllWorkstations(null, boxFilterSite2.getSelectedItem().toString());							
						for (n = 0;n<db.getRowCount(); n++){
							try{
							boxWorkstationReport.addItem(workstationsCollection[n].toString());
							}catch(Exception h){
								h.printStackTrace();
							}
						}
				 		System.out.println(boxWorkstationReport.getItemCount() +" records loaded");				 		
					} catch (Exception ex){
						ex.printStackTrace();
					}
					btnKeyboard2.setVisible(false);
				}else if(boxReports.getSelectedIndex()==4){	
					lblSite2.setVisible(false);
					boxFilterSite2.setVisible(false);
					btnExportToExcel.setVisible(false);					
					lblFrom.setVisible(false);
					lblTo.setVisible(false);
					txtDate1.setVisible(false);
					txtDate2.setVisible(false);
					checkIncludeNonSAP.setVisible(false);
					lblDateInstructions.setVisible(false);
					lblDateInstructions.setEnabled(false);
					lblReports.setVisible(true);
					lblReports.setText("Scale SN:");
					boxDepReport.setVisible(false);
					boxWorkstationReport.setVisible(false);
					txtScaleReport.setVisible(true);
					btnGenerateReport.setVisible(true);
					btnKeyboard2.setVisible(true);					
					placeCursor(txtScaleReport);
				}					
			}
		});
		//refresh list of workstations or departments based on site selected
		boxFilterSite2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boxReports.getSelectedIndex()==3){
					try{						
						if (boxWorkstationReport.getItemCount()!=1){							
							while (boxWorkstationReport.getItemCount()!=1){						
								 Object boxItem = boxWorkstationReport.getItemAt(1);	
								 boxWorkstationReport.removeItem(boxItem);									
						}
					}						
					workstationsCollection = db.getAllWorkstations(null, boxFilterSite2.getSelectedItem().toString());							
					for (int n = 0;n<db.getRowCount(); n++){
						try{
						boxWorkstationReport.addItem(workstationsCollection[n].toString());
						}catch(Exception h){
							h.printStackTrace();
						}
					}
			 		System.out.println(boxWorkstationReport.getItemCount() +" records loaded");				 		
				} catch (Exception ex){
					ex.printStackTrace();
				}
				}else if (boxReports.getSelectedIndex()==2){
					loadDepartments(2);
				}else{
					return;
				}
			}});
		btnGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tblReports = new JTable();
				tblReports.setEnabled(false);
				btnExportToExcel.setVisible(true);				
				if(boxReports.getSelectedIndex()==1){					
					String date1 = txtDate1.getText();
					String date2 = txtDate2.getText();
					
					try{
					if ((date1.length()==0)&&(date2.length()==0)){
						tblValues = db.getDateReport1(boxFilterSite2.getSelectedItem().toString(), checkIncludeNonSAP.isSelected());
						
						tblReports.setModel(new DefaultTableModel(tblValues, new String[]{"Date","Tech","Department", "Workstation", "Serial No.", "Reading", "Weight", "Notes"}));
						tblReports.getColumnModel().getColumn(0).setPreferredWidth(76);  //Date
						tblReports.getColumnModel().getColumn(1).setPreferredWidth(50);  //Tech
						tblReports.getColumnModel().getColumn(2).setPreferredWidth(85);  //Department
						tblReports.getColumnModel().getColumn(3).setPreferredWidth(88);  //Work
						tblReports.getColumnModel().getColumn(4).setPreferredWidth(82);  //SN
						tblReports.getColumnModel().getColumn(5).setPreferredWidth(57);  //Read
						tblReports.getColumnModel().getColumn(6).setPreferredWidth(51);  //Weight
						tblReports.getColumnModel().getColumn(7).setPreferredWidth(300); //Notes
						stblReports = new JScrollPane(tblReports);
						stblReports.setBounds(10, 217, 715, 300);			
						panel2.add(stblReports);
						exportMode = 1;
					}else if((date1.length()!=0 && date2.length()==0)||(date1.length()==0 && date2.length()!=0)){
						String date3 ="";
						if (date2.length()==0){
							date3=date1;
						} else{
							date3=date2;
						}
						tblValues = db.getDateReport2(date3, boxFilterSite2.getSelectedItem().toString(), checkIncludeNonSAP.isSelected());
						JOptionPane.showMessageDialog(null, +db.getRowCount() +" rows obtained");
						
						tblReports.setModel(new DefaultTableModel(tblValues, new String[]{"Date","Tech","Department", "Workstation", "Serial No.", "Reading", "Weight", "Notes"}));
						tblReports.getColumnModel().getColumn(0).setPreferredWidth(76);  //Date
						tblReports.getColumnModel().getColumn(1).setPreferredWidth(50);  //Tech
						tblReports.getColumnModel().getColumn(2).setPreferredWidth(85);  //Department
						tblReports.getColumnModel().getColumn(3).setPreferredWidth(88);  //Work
						tblReports.getColumnModel().getColumn(4).setPreferredWidth(82);  //SN
						tblReports.getColumnModel().getColumn(5).setPreferredWidth(57);  //Read
						tblReports.getColumnModel().getColumn(6).setPreferredWidth(51);  //Weight
						tblReports.getColumnModel().getColumn(7).setPreferredWidth(300); //Notes
						stblReports = new JScrollPane(tblReports);
						stblReports.setBounds(10, 217, 715, 300);
						stblReports.setVisible(true);
						panel2.add(stblReports);
						exportMode = 1;
					}else{
						tblValues = db.getDateReport3(date1, date2,boxFilterSite2.getSelectedItem().toString(), checkIncludeNonSAP.isSelected());
							
						tblReports.setModel(new DefaultTableModel(tblValues, new String[]{"Date","Tech","Department", "Workstation", "Serial No.", "Reading", "Weight", "Notes"}));
						tblReports.getColumnModel().getColumn(0).setPreferredWidth(76);  //Date
						tblReports.getColumnModel().getColumn(1).setPreferredWidth(50);  //Tech
						tblReports.getColumnModel().getColumn(2).setPreferredWidth(85);  //dep
						tblReports.getColumnModel().getColumn(3).setPreferredWidth(88);  //Work
						tblReports.getColumnModel().getColumn(4).setPreferredWidth(82);  //SN
						tblReports.getColumnModel().getColumn(5).setPreferredWidth(57);  //Read
						tblReports.getColumnModel().getColumn(6).setPreferredWidth(51);  //Weight
						tblReports.getColumnModel().getColumn(7).setPreferredWidth(300); //Notes
						stblReports = new JScrollPane(tblReports);
						stblReports.setBounds(10, 217, 715, 300);
						stblReports.setVisible(true);
						panel2.add(stblReports);
						exportMode = 1;
					}
					lblDateInstructions.setVisible(false);
					lblDateInstructions.setEnabled(false);
					} catch (Exception ex){
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Please check the date entered and try again. Acceptable format is MM/DD/YY");
						return;
					}
				
				}else if (boxReports.getSelectedIndex()==2){
					String department;
					if (boxDepReport.getSelectedIndex()==0){
						return;						
					}else{
						department = boxDepReport.getSelectedItem().toString();
					}
					try{
						tblValues = db.getDepReport(department,boxFilterSite2.getSelectedItem().toString(), checkIncludeNonSAP.isSelected());					
					
					tblReports.setModel(new DefaultTableModel(tblValues, new String[]{"Date","Tech","Workstation", "Serial No.", "Reading", "Weight", "Notes"}));
					tblReports.getColumnModel().getColumn(0).setPreferredWidth(76);  //Date
					tblReports.getColumnModel().getColumn(1).setPreferredWidth(50);  //Tech
					tblReports.getColumnModel().getColumn(2).setPreferredWidth(88);  //Work					
					tblReports.getColumnModel().getColumn(3).setPreferredWidth(82);  //SN
					tblReports.getColumnModel().getColumn(4).setPreferredWidth(57);  //Read
					tblReports.getColumnModel().getColumn(5).setPreferredWidth(51);  //Weight
					tblReports.getColumnModel().getColumn(6).setPreferredWidth(300); //Notes
					stblReports = new JScrollPane(tblReports);
					stblReports.setBounds(10, 217, 715, 300);
					stblReports.setVisible(true);
					panel2.add(stblReports);
					exportMode = 2;
					}catch (Exception ex){
						ex.printStackTrace();
						return;
					}
				}else if(boxReports.getSelectedIndex()==3){
					if(boxWorkstationReport.getSelectedIndex()==0){
						return;
					}
					try{						
						tblValues= db.getWorkstationReport(boxWorkstationReport.getSelectedItem().toString().trim());
						
						tblReports.setModel(new DefaultTableModel(tblValues, new String[]{"Date","Tech", "Serial No.", "Reading", "Weight", "Notes"}));
						tblReports.getColumnModel().getColumn(0).setPreferredWidth(76);  //Date
						tblReports.getColumnModel().getColumn(1).setPreferredWidth(50);  //Tech										
						tblReports.getColumnModel().getColumn(2).setPreferredWidth(82);  //SN
						tblReports.getColumnModel().getColumn(3).setPreferredWidth(57);  //Read
						tblReports.getColumnModel().getColumn(4).setPreferredWidth(51);  //Weight
						tblReports.getColumnModel().getColumn(5).setPreferredWidth(300); //Notes						
						stblReports = new JScrollPane(tblReports);
						stblReports.setBounds(10, 217, 715, 300);
						stblReports.setVisible(true);											
						panel2.add(stblReports);
						exportMode = 3;
						}catch (Exception ex){
							ex.printStackTrace();
							return;
						}
					
				}else if(boxReports.getSelectedIndex()==4){
					String sn=txtScaleReport.getText().trim();
					try{
						tblValues  = db.get1ScaleReport(sn);
										
					tblReports.setModel(new DefaultTableModel(tblValues, new String[]{"Date","Tech","Workstation",  "Reading", "Weight", "Notes"}));
					tblReports.getColumnModel().getColumn(0).setPreferredWidth(76);  //Date
					tblReports.getColumnModel().getColumn(1).setPreferredWidth(50);  //Tech
					tblReports.getColumnModel().getColumn(2).setPreferredWidth(88);  //Work						
					tblReports.getColumnModel().getColumn(3).setPreferredWidth(57);  //Read
					tblReports.getColumnModel().getColumn(4).setPreferredWidth(51);  //Weight
					tblReports.getColumnModel().getColumn(5).setPreferredWidth(300); //Notes
					stblReports = new JScrollPane(tblReports);
					stblReports.setBounds(10, 217, 715, 300);
					stblReports.setVisible(true);					
					panel2.add(stblReports);
					exportMode = 4;
					}catch (Exception ex){
						ex.printStackTrace();
						return;
					}
				}
				
			}
		});
		//this method sends the data on the table to be output into a excel file
		btnExportToExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				try {
					IO.exportTransaction(tblValues, db.getRowCount(), exportMode);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Failure to create spreadsheet. If problem persists please inform Tech Support");
					e.printStackTrace();
					return;
				}
				return;
			}
		});
	//panel 3 event handlers	

	//button Update to edit date for a single scale
			btnMSUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//input verification		
					String sn = txtMSSN.getText().trim();
					if(sn.length()==0||sn.toLowerCase().equals("sn")){
						JOptionPane.showMessageDialog(null, " Serial Number can't be blank and must exist");
						return;	
					} else	if(!db.SNExists(sn)){					
						JOptionPane.showMessageDialog(null, "Scale with SN specified doesn't exist, please check value entered or add new unit on the quick action menu");
						return;
					}	
					String workstation;
					if (txtMSWorkstation.getText() == null){
						JOptionPane.showMessageDialog(null, " Workstation can't be blank and must exist, assign it to the 'Parts Room' or to a vendor if that's the case");
						return;	
					}else if(db.workstationExists(txtMSWorkstation.getText().trim())){
						workstation = txtMSWorkstation.getText().trim();
					}else{
						JOptionPane.showMessageDialog(null, "Workstation specified doesn't exist, please check value entered");
						return;
					}		
					String category = null;
					if(boxMSCategory.getSelectedIndex()!=0){
						category = String.valueOf(boxMSCategory.getSelectedIndex());
					}else{
						JOptionPane.showMessageDialog(null, "Please select a Category");
					}
					String condition=null;
					if(boxMSCondition.getSelectedIndex()==4){
						condition = "G";
					} else if (boxMSCondition.getSelectedIndex()==1){
						condition="B";
					}else if (boxMSCondition.getSelectedIndex()==3){
						try{
						if(db.updateDBRow("Scales", "Condition", "F", sn)){
							String manufacturer = boxMSManufacturer.getSelectedItem().toString();
							if(manufacturer.equals("Weigh-Tronix")){
								db.updateDBRow("Scales", "Workstation", "Precision", sn);
								JOptionPane.showMessageDialog(null, "Operation successful.\n\nShip the the unit to the following address:\n\t Roger Pitt / Dani Vetrano\n\tPrecision Scales Co. Inc.\n\t744 MCBride Ave.\n\tWoodland Park, NJ, 07424");
							} else if(manufacturer.equals("A&D")){
								db.updateDBRow("Scales", "Workstation", "A&D Engineering", sn);
								JOptionPane.showMessageDialog(null, "Operation successful.\n\nShip the the unit to the following address:\n\t Javier Cortez / Darryl Fortier\n\tA&D Engineering\n\t1756 Automation Parkway\n\tSan Jose, CA, 95131");
							}else{
								db.updateDBRow("Scales", "Workstation", "Totalcomp", sn);
								JOptionPane.showMessageDialog(null, "Operation successful.\n\nShip the the unit to the following address:\n\t Tony Savastano / Mike Faraon\n\tTotalcomp\n\t1301 Pollitt Drive\n\tFair Lawn, NJ, 07410");
							}
						}
						}catch (Exception n){
							JOptionPane.showMessageDialog(null, "Operation unsuccessful.Please set manufacturer, model and capacity prior to setting scale down for repair");
							return;
						}
					}else if (boxMSCondition.getSelectedIndex()==5){
						condition = "Z";
					}else{
						condition = "C";
					}
					String manufacturer = boxMSManufacturer.getSelectedItem().toString();
					if (manufacturer == "Other"){
						manufacturer = JOptionPane.showInputDialog("Please enter manufacturer");
					}
					String model = boxMSModel.getSelectedItem().toString();
					if(model == "Other"){
						model = JOptionPane.showInputDialog("Please enter model");
					}
					String capacity = boxMSCap.getSelectedItem().toString();
					if(capacity == "Other"){
						capacity = JOptionPane.showInputDialog("Please enter scale maximimum capacity");
					}
					String warranty=txtMSWarranty.getText().trim();
					if (warranty.length()==0||warranty.toLowerCase().equals("n/a")||warranty.equals("Date War.")){
						warranty=null;
					}else{
					String date = txtMSWarranty.getText();
					String year ="";
					String day="";
					String month="";			
					if(date.length()==8){		
							day = date.substring(3, 5);	
							month = date.substring(0,2);	
							year = date.substring(6, 8);
							warranty="20"+year+"-"+month+"-"+day;
					} else if(date.length()==6){
							month ="0"+date.substring(0,1);
							day = "0"+date.substring(2, 3);
							year = date.substring(4,6);		
							warranty="20"+year+"-"+month+"-"+day;	
					}else if(date.length()==7){
						if(date.charAt(1)=='-'||date.charAt(1)=='/'||date.charAt(1)=='.'){
							month ="0"+date.substring(0,1);
							day = date.substring(2, 4);
							year = date.substring(5,7);	
						}else{
							month =date.substring(0,2);
							day = 0+date.substring(3, 4);
							year = date.substring(5,7);	
						}	
						warranty="20"+year+"-"+month+"-"+day;			
					}
					}
					
					String purchased = txtMSPurchased.getText().trim();
					if (purchased.length()==0||purchased.toLowerCase().equals("n/a")||purchased.equals("Date Pur.")){
						purchased=null;
					}else{
					String date = txtMSPurchased.getText();
					String year ="";
					String day="";
					String month="";
					if(date.length()==8){		
							day = date.substring(3, 5);	
							month = date.substring(0,2);	
							year = date.substring(6, 8);
							purchased="20"+year+"-"+month+"-"+day;
					} else if(date.length()==6){
							month ="0"+date.substring(0,1);
							day = "0"+date.substring(2, 3);
							year = date.substring(4,6);		
							purchased="20"+year+"-"+month+"-"+day;	
					}else if(date.length()==7){
						if(date.charAt(1)=='-'||date.charAt(1)=='/'||date.charAt(1)=='.'){
							month ="0"+date.substring(0,1);
							day = date.substring(2, 4);
							year = date.substring(5,7);	
						}else{
							month =date.substring(0,2);
							day = 0+date.substring(3, 4);
							year = date.substring(5,7);	
						}
						purchased="20"+year+"-"+month+"-"+day;					
														
					}
					}
					String dimensions =txtMSDimensions.getText();
					 if (dimensions.length()==0||dimensions.equals("Dim.")||dimensions.toLowerCase().equals("n/a")){
					 dimensions = null;
					 }
					//Sending the data to database
					System.out.println("Purchase date is "+purchased+"\nWarranty date is "+warranty);
					if(db.updateDBRow(sn, workstation, category, condition, capacity, model, manufacturer, warranty, purchased, dimensions)){
						JOptionPane.showMessageDialog(null, "Operation successful.\n\nUnit data updated");
						return;
					}else{
						return;
					}
				
			
				}
			});
			
	//autoset model and manufacturern edit scale when specifying capacity
	boxMSCap.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {				
			if(boxMSCap.getSelectedIndex()==1){
				boxMSManufacturer.setSelectedIndex(1);
				boxMSModel.setSelectedIndex(1);
			}else if(boxMSCap.getSelectedIndex()==2){
				boxMSManufacturer.setSelectedIndex(3);
				boxMSModel.setSelectedIndex(5);
			}else if(boxMSCap.getSelectedIndex()==3){
				boxMSManufacturer.setSelectedIndex(2);
				boxMSModel.setSelectedIndex(2);
			}else if(boxMSCap.getSelectedIndex()==4){
				boxMSManufacturer.setSelectedIndex(5);
				boxMSModel.setSelectedIndex(7);
			}else if(boxMSCap.getSelectedIndex()==5){
				boxMSManufacturer.setSelectedIndex(2);
				boxMSModel.setSelectedIndex(3);
			}else if(boxMSCap.getSelectedIndex()==6){
				boxMSManufacturer.setSelectedIndex(5);
				boxMSModel.setSelectedIndex(8);
			}else if(boxMSCap.getSelectedIndex()==8){
				boxMSManufacturer.setSelectedIndex(5);
				boxMSModel.setSelectedIndex(9);
	
				}}});
				//quick actions menu
				btnMSQuickAction.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtMSQuickAction.getText().trim()!=null && boxMSQuickAction.getSelectedIndex() !=0){							
							if(db.SNExists(txtMSQuickAction.getText().trim()) || boxMSQuickAction.getSelectedIndex()==1){
								String sn = txtMSQuickAction.getText().trim();
								if(boxMSQuickAction.getSelectedIndex()==1){		
									String [] categorySelection={"1", "2", "3"};									
									int category = JOptionPane.showOptionDialog(null, "Please set scale category\n1-Pricing Scales\n2-Standalone (not used for pricing)\n3-Small scales", "Select Category", JOptionPane.ERROR_MESSAGE, 0, null, categorySelection, categorySelection[2]);
									if (category>-1){
									System.out.println("\nSN is "+sn);
									if(db.AddNewScale(sn, String.valueOf(category+1))){										
										JOptionPane.showMessageDialog(null,"Operation successful.\nScale added to inventory" );										
									}else{
										JOptionPane.showMessageDialog(null,"Operation unsuccessful.\nPlease check if scale is already in the inventory");
									}									
									}else{
										JOptionPane.showMessageDialog(null, "Request canceled");
									}
								}else if(boxMSQuickAction.getSelectedIndex()==3){
									if(db.updateDBRow("Scales","Condition", "B", sn)){		
										JOptionPane.showMessageDialog(null, "Operation successful.\n"+sn+" Set as 'bad'/'Needs to be checked'");
									}else{
										JOptionPane.showMessageDialog(null, "Operation unsuccessful.\nPlease restart program Error code: 205");
									}
								}else if(boxMSQuickAction.getSelectedIndex()==4){
									try{
									if(db.updateDBRow("Scales", "Condition", "F", sn)){
										String manufacturer = db.getDBValue("Scales", "Manufacturer", sn);
										if(manufacturer.equals("Weigh-Tronix")){
											db.updateDBRow("Scales", "Workstation", "Precision", sn);
											JOptionPane.showMessageDialog(null, "Operation successful.\n\nShip the the unit to the following address:\n\t Roger Pitt / Dani Vetrano\n\tPrecision Scales Co. Inc.\n\t744 MCBride Ave.\n\tWoodland Park, NJ, 07424");
										} else if(manufacturer.equals("A&D")){
											db.updateDBRow("Scales", "Workstation", "A&D Engineering", sn);
											JOptionPane.showMessageDialog(null, "Operation successful.\n\nShip the the unit to the following address:\n\t Javier Cortez / Darryl Fortier\n\tA&D Engineering\n\t1756 Automation Parkway\n\tSan Jose, CA, 95131");
										}else{
											db.updateDBRow("Scales", "Workstation", "Totalcomp", sn);
											JOptionPane.showMessageDialog(null, "Operation successful.\n\nShip the the unit to the following address:\n\t Tony Savastano / Mike Faraon\n\tTotalcomp\n\t1301 Pollitt Drive\n\tFair Lawn, NJ, 07410");
										}
									}
									}catch (Exception f){
										JOptionPane.showMessageDialog(null, "Manufacturer not assigned to unit. It has been set as 'Shipped out' nevertheless");
										
									}
								}else if(boxMSQuickAction.getSelectedIndex()==5){
									String condition =db.getDBValue("Scales", "Condition", sn);
									if(condition.equals("F")){
										if(db.updateDBRow("Scales", "Condition", "C", sn)){
											if((db.updateDBRow("Scales", "Workstation", "Parts Room", sn)))
											JOptionPane.showMessageDialog(null, "Operation successful.\n "+sn+" Set as 'Back from repair'");
										}
									}else{
										JOptionPane.showMessageDialog(null, "Operation unsuccessful.\nScale not Set as 'Out for Repair'");
									}
								}else if (boxMSQuickAction.getSelectedIndex()==6){
									newSN=JOptionPane.showInputDialog("Enter new SN");
									if(db.updateDBRow("Scales", "SN", newSN, sn)){
										JOptionPane.showMessageDialog(null, "Operation successful.\nNew SN set");
									}else{
										JOptionPane.showMessageDialog(null, "Operation unsuccessful.\n "+sn+" previously recorded");
									}
								}else if(boxMSQuickAction.getSelectedIndex()==7){									
									if(db.updateDBRow("Scales", "Condition", "Z", sn)){
										JOptionPane.showMessageDialog(null, "Operation successful.\n "+sn+" Set as 'Decommissioned'");
									}else{
										JOptionPane.showMessageDialog(null, "Operation unsuccessful.\nPlease restart program Error code: 206");
									}
								}else{
									if(db.updateDBRow("Scales", "Condition", "G", sn)){
										JOptionPane.showMessageDialog(null, "Operation successful. ");
									}else{
										JOptionPane.showMessageDialog(null, "Operation unsuccessful.\nPlease restart program Error code: 207");
									}
								}
							} else{
								JOptionPane.showMessageDialog(null, "SN specified is not on the records");
							}
						}
					}
				});
				//Removing tooltip text
				txtMSSN.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {					
					if(txtMSSN.getText().length()==2){
						txtMSSN.setText("");}}});
				txtMSWorkstation.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {					
					if(txtMSWorkstation.getText().length()==11){
						txtMSWorkstation.setText("");}}});
				txtMSWarranty.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {					
					if(txtMSWarranty.getText().length()==9){
						txtMSWarranty.setText("");}}});
				txtMSPurchased.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {					
					if(txtMSPurchased.getText().length()==9){
						txtMSPurchased.setText("");}}});
				txtMSDimensions.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {					
					if(txtMSDimensions.getText().length()==4){
						txtMSDimensions.setText("");}}});
				
				//lookup info after adding SN
				txtMSSN.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent arg0) {
						try{
						if (db.SNExists(txtMSSN.getText().trim())){							
							rowValue = db.EditScales(txtMSSN.getText().trim());
							String sn = rowValue[0];
							String workstation = rowValue[1];
							String category = rowValue[2];
							String condition = rowValue[3];
							String manufacturer = rowValue[4];
							String model =rowValue[5];
							String cap = rowValue[6];
							String warranty =rowValue[7];
							String purchased = rowValue[8];
							String dimensions = rowValue[9];
							
							if(workstation!=null){
						txtMSWorkstation.setText(workstation);	
							}
							try{
						txtMSWarranty.setText(warranty.substring(0, 9));	
							}catch (Exception o){								
							}
							try{
						txtMSPurchased.setText(purchased.substring(0, 9));	
							}catch (Exception o){								
							}
							try{
						txtMSDimensions.setText(dimensions);
							}catch (Exception o){								
							}
							boxMSCategory.setSelectedIndex(Integer.parseInt(category));
					if (condition.trim().equals("G")){
					boxMSCondition.setSelectedIndex(4);
					}else if(condition.trim().equals("C")){
					boxMSCondition.setSelectedIndex(2);
					}else if(condition.trim().equals("B")){
					boxMSCondition.setSelectedIndex(1);
					}else if (condition.trim().equals("F")){
					boxMSCondition.setSelectedIndex(3);
					}else if (condition.trim().equals("Z")){
					boxMSCondition.setSelectedIndex(5);				
					}
					if(manufacturer!=null){
					boxMSManufacturer.setSelectedItem(manufacturer);
					}
					if(model!=null){
					boxMSModel.setSelectedItem(model);
					}
					if(cap!=null){
					boxMSCap.setSelectedItem(cap);
					}
						}
						}catch (Exception db){
							db.printStackTrace();
						}			
				}
				});
				//lookup info after adding workstation
				txtMSWorkstation.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent arg0) {
						try{
							if (txtMSSN.getText().trim().length()==0||(txtMSSN.getText().equals("SN"))){
						if (db.workstationExists(txtMSWorkstation.getText().trim())){				
							rowValue= db.EditScales(txtMSWorkstation.getText().trim());
							String sn = rowValue[0];
							String workstation =rowValue[1];
							String category = rowValue[2];
							String condition =rowValue[3];
							String manufacturer = rowValue[4];	
							String model = rowValue[5];
							String cap = rowValue[6];												
							String warranty = rowValue[7];
							String purchased = rowValue[8];
							String dimensions = rowValue[9];
							
							if(sn!=null){
						txtMSSN.setText(sn);		
							}
							try{
						txtMSWarranty.setText(warranty.substring(0, 9));
							}catch (Exception o){
							}
							try{
						txtMSPurchased.setText(purchased.substring(0, 9));
							}catch (Exception o){
							}
							try{
						txtMSDimensions.setText(dimensions);
							}catch (Exception o){
							}
							boxMSCategory.setSelectedIndex(Integer.parseInt(category));
					if (condition == "G"){
					boxMSCondition.setSelectedItem(4);
					}else if(condition== "C"){
					boxMSCondition.setSelectedItem(2);
					}else if(condition == "B"){
					boxMSCondition.setSelectedItem(1);
					}else if (condition == "F"){
					boxMSCondition.setSelectedItem(3);
					}else if (condition == "Z"){
					boxMSCondition.setSelectedItem(5);					
					}
					if(manufacturer!=null){
					boxMSManufacturer.setSelectedItem(manufacturer);
					}
					if(model!=null){
					boxMSModel.setSelectedItem(model);
					}
					if(cap!=null){
					boxMSCap.setSelectedItem(cap);
					}
						}}
						}catch (Exception db){
							db.printStackTrace();
						}	
					
				}});
				//refresh button
				btnMSRefresh.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						panel3.remove(stblScaleInventory);
						loadPanelTable(3);
					}
				});
				//export table to an excel file
				btnMSExport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							IO.exportTransaction(tblValues, db.getRowCount(), 5);
							
						} catch (IOException en) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "Failure to create spreadsheet. If problem persists please inform Tech Support");
							en.printStackTrace();
						}
					}
				});		
		//panel4 event handlers:
		
		//refresh button
		btnMWRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel4.remove(stblManageWorkstations);
				loadPanelTable(4);
			}			
		});
		//auto add station info
		txtMWWorkstation.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				try{	
					if (db.workstationExists(txtMWWorkstation.getText().trim())){
						rowValue = db.editStations(txtMWWorkstation.getText().trim());
						
						String department = rowValue[2];
						String category = rowValue[4];
						String site = rowValue[3];
						
						if (department.toString().length()!=0){
							boxMWDep.setSelectedItem(department);
							}
						if (category.toString().length()!=0){
							boxMWCategory.setSelectedItem(category);
						}
						if (site.toString().length()!=0){
							boxMWSite.setSelectedItem(site);
							}					
					}
					
						}catch (Exception db){
							db.printStackTrace();
							}
									
				}});
		//remove tiptext from workstation text box
		txtMWWorkstation.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(txtMWWorkstation.getText().equals("Workstation")){
					txtMWWorkstation.setText("");
				}
			}
		});
		//edit station information
		btnMWUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String workstation = txtMWWorkstation.getText().trim();
				String department = boxMWDep.getSelectedItem().toString();
				String site = boxMWSite.getSelectedItem().toString();
				int category = boxMWCategory.getSelectedIndex();
				//input verification
				if(txtMWWorkstation.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Workstation can't be blank");
				}else{					
					if(db.workstationExists(workstation)){
						if(boxMWDep.getSelectedIndex()==0||boxMWDep.getSelectedIndex()==-1){
							JOptionPane.showMessageDialog(null, "Please select a Department");
							return;
						}
						if(boxMWSite.getSelectedIndex()==0||boxMWCategory.getSelectedIndex()==-1){
							JOptionPane.showMessageDialog(null, "Please select a Site");
							return;
						}
						if(boxMWCategory.getSelectedIndex()==0||boxMWCategory.getSelectedIndex()==-1){
							JOptionPane.showMessageDialog(null, "Please select a category");
							return;
						}
						if(db.updateDBRow(workstation, department, site, category)){
							JOptionPane.showMessageDialog(null, "Unit information updated, success!");
						}else{
							JOptionPane.showMessageDialog(null, "Failed to update station data.\nError 1224");
						}
					}else{
						JOptionPane.showMessageDialog(null, "Workstation not found in our records. Please check spelling");
						return;
					}
				}
			}
		});
		//change the label for input field
		boxMWQuickAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				if(boxMWQuickAction.getSelectedIndex()==4){
					lblMWWorkstation.setText(" New Department Name:");
				}else if(boxMWQuickAction.getSelectedIndex()==5){
					lblMWWorkstation.setText("New Site Name:");
				}else{
					lblMWWorkstation.setText("Workstation Name:");
				}
				}catch (Exception n){
					
				}
			}
		});
		//quick actions button
		btnMWQuickAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String inputField = txtMWQuickAction.getText().trim();
				if(inputField.length()<3){
					JOptionPane.showMessageDialog(null, "Input text must have a lenght of 3 letters at least");
				}else{
					if(boxMWQuickAction.getSelectedIndex()==1){
						if(db.workstationExists(inputField)){
							JOptionPane.showMessageDialog(null, "Name must be unique. A workstation with that name already exists. ");
							return;
						}else{	
							String [] categorySelection={"1", "2", "3"};
							int category = JOptionPane.showOptionDialog(null, "Please select workstation category:", "Selection needed", JOptionPane.INFORMATION_MESSAGE, 0, null, categorySelection, categorySelection[2]);
							category = category+1;
							if (category>0){
								System.out.println("\nWorkstation is "+inputField);
								System.out.println("\nCategory is "+category);
								if(db.AddNewWorkstation(inputField, String.valueOf(category))){
									
									JOptionPane.showMessageDialog(null,"Operation successful.\nWorkstation added to inventory" );
									return;
									}else{
										JOptionPane.showMessageDialog(null,"Operation unsuccessful.\nError 399");
										return;
										}
								}else{
									return;
								}						
							}
						}else if(boxMWQuickAction.getSelectedIndex()==2){
							String[] options = new String[] {"Remove from PM", "Remove from records"};
							int selection = JOptionPane.showOptionDialog(null, "Please make a selection:", "Selection needed", JOptionPane.ERROR_MESSAGE, 0, null, options, options[0]);
							if(selection ==0){
								if(db.updateDBRow("Workstations", "Visible", "false", inputField)){
									JOptionPane.showMessageDialog(null,"Operation successful");
									return;
								}else{
									JOptionPane.showMessageDialog(null,"Operation unsuccessful\nError code 4225");
								}
							}else{
							if(db.removeStation(inputField)){
								JOptionPane.showMessageDialog(null,"Operation successful.\n"+inputField+" has been removed from the records");
								return;
							}else{
								JOptionPane.showMessageDialog(null,"Operation unsuccessful.\n Station can't be removed once a scale has been assigned to it. If name is wrong it can be renamed, if station is not used any longer, please try again selecting 'Remove from PM' when prompted");
							return;
							}
							}				
					}else if(boxMWQuickAction.getSelectedIndex()==3){
						String newName = JOptionPane.showInputDialog("Enter new Station name");
						String scaleOnStation= db.getScaleOnStation(inputField);
						if(db.updateDBRow("Workstations", "Workstation", newName, inputField)){
							JOptionPane.showMessageDialog(null,"Operation successful.\n"+newName+" has been set as the new name");							
							db.updateDBRow("Scales", "Workstation", newName, scaleOnStation);
							return;
						}else{
							JOptionPane.showMessageDialog(null,"Operation unsuccessful.\n"+newName+" Workstation name cannot be changed once it has been associated with a scale. To resolve this issue, create a new workstation and remove " +inputField+ "from records by selecting 'Remove from PM' option\nError 93 ");
						return;
						}
					}else if(boxMWQuickAction.getSelectedIndex()==4){
						if(inputField.length()<3){
							JOptionPane.showMessageDialog(null,"Department must be at least 3 characters long");
							return;
						}
						String assignedStation =  JOptionPane.showInputDialog("In order to create a new Department it is necessary to assign a Workstation to it.\n(It's recomended to create a workstation for the new deparment before creating a new department).\n\nEnter Workstation name to associate with the new department:");
						if(db.updateDBRow("Workstations", "Department", inputField, assignedStation)){
							JOptionPane.showMessageDialog(null,"Operation successful.\n"+inputField+" has been created as new Department");
							return;
						}else{
							JOptionPane.showMessageDialog(null,"Operation unsuccessful.\nError 12");
							return;
						}
					}else if(boxMWQuickAction.getSelectedIndex()==5){
						if(inputField.length()<3){
							JOptionPane.showMessageDialog(null,"Site must be at least 3 characters long");
							return;
						}
						String assignedDepartment =  JOptionPane.showInputDialog("In order to create a new Site it is necessary to assign a Department to it.\n(It's recomended to create a Department for the new Site before creating a new Site).\n\nEnter Department name to associate with the new Site:");
						String dep = db.getWorkstation("Department", assignedDepartment);						
						if(db.updateDBRow("Workstations", "Site", inputField, dep )){
							JOptionPane.showMessageDialog(null,"Operation successful.\n"+inputField+" has been created as new Site");
							return;
						}else{
							JOptionPane.showMessageDialog(null,"Operation unsuccessful.\nError 13");
							return;
						}
					}
				}		
			}
			
		});
		//panel5 event handlers:
		//reload table
				btnRRRefresh.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						panel4.remove(stblRepairs);
						loadPanelTable(5);
					}			
				});
				//remove text from input fields
				txtRRID.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						if(txtRRID.getText().equals("Column 1")){
							txtRRID.setText("");
						}
					}
				});
				txtRRSN.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						if(txtRRSN.getText().equals("#########")){
							txtRRSN.setText("");
						}
					}
				});
				txtRepairedBy.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						if(txtRepairedBy.getText().equals("Initials")){
							txtRepairedBy.setText("");
						}
					}
				});
				txtDownSince.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						if(txtDownSince.getText().equals("mm/dd/yy")){
							txtDownSince.setText("");
						}
					}
				});
				txtRepairedDate.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						checkToday.setSelected(false);
						if(txtRepairedDate.getText().equals("mm/dd/yy")){
							txtRepairedDate.setText("");
						}
					}
				});
				txtSymptom.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						if(txtSymptom.getText().equals("How was the issue identified  (or issues)")){
							txtSymptom.setText("");
						}
					}
				});
				txtRootCause.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						if(txtRootCause.getText().equals("What caused the issue(s)")){
							txtRootCause.setText("");
						}
					}
				});
				txtResolution.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						if(txtResolution.getText().equals("What action(s) led to the resolution")){
							txtResolution.setText("");
						}
					}
				});				
				//New Record / Update toggle
				checkRRNewUpdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(checkRRNewUpdate.isSelected()){
							lblRRID.setVisible(false);
							txtRRID.setVisible(false);
							btnRRAddRecord.setText("Submit Record");
						}else{
							lblRRID.setVisible(true);
							txtRRID.setVisible(true);
							btnRRAddRecord.setText("Update Record");
						}
					}
				});
				//auto set todays date
				checkToday.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {					
					if(checkToday.isSelected()){
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
						LocalDateTime dt = LocalDateTime.now();
						String date = dtf.format(dt);
						txtRepairedDate.setText(date);
					}
					}
				});
				//auto fill in fields when record ID is added
				txtRRID.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent arg0) {
						if(db.repairRecordExists(txtRRID.getText().trim())){
							try{
							rowValue = db.getRepairRecords(txtRRID.getText().trim());
							
							txtDownSince.setText(rowValue[0].substring(0, 10));
								txtRRSN.setText(rowValue[1]);
								boxRRStatus.setSelectedIndex(Integer.parseInt(rowValue[2])-1);		
								txtSymptom.setText(rowValue[3]);
								txtRootCause.setText(rowValue[4]);
								txtResolution.setText(rowValue[5]);															
								txtRepairedBy.setText(rowValue[7]);
								try{
								txtRepairedDate.setText(rowValue[6].substring(0, 10));	
								}catch (Exception p){	
									return;
								}
							
						
							}catch (Exception b){
								b.printStackTrace();
								return;
							}
						}
					}
				});
				//export records to Excel
				btnRRExport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							IO.exportTransaction(tblValues, db.getRowCount(), 9);
						}catch(Exception x){
							JOptionPane.showMessageDialog(null, "Failure to create spreadsheet. If problem persists please inform Tech Support");
						}
					}
				});
				//submit records
				btnRRAddRecord.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// get input:
						System.out.println("button pressed");
						String RNumber = txtRRID.getText().trim();
						String sn = txtRRSN.getText().trim();
						String status = String.valueOf(boxRRStatus.getSelectedIndex()+1);
						String tech =txtRepairedBy.getText().trim();
						String inDate = txtDownSince.getText().trim();
						String outDate = txtRepairedDate.getText().trim();
						String symptom = txtSymptom.getText().trim();
						String rootCause = txtRootCause.getText().trim();
						String resolution = txtResolution.getText().trim();
						System.out.println("RNumber is "+RNumber+" ,sn is "+sn+" ,status is"+status+" ,inDate is "+inDate+" ,outDate is "+outDate+" ,symptom is "+symptom+" ,rootCause is "+rootCause+" ,resolution is "+resolution);
						//check for default values:
						if(RNumber.equals("column 1")){
							RNumber="";
						}
						if(sn.equals("+++++++++")){
								sn ="";
						}
						if(tech.equals("Initials")){
							tech="";
						}
						if(inDate.equals("mm/dd/yy")){
								inDate="";
						}
						if(outDate.equals("mm/dd/yy")){
							outDate="";
						}
						if(symptom.equals("How was the issue identified  (or issues)")){
							symptom="";
						}
						if(rootCause.equals("What caused the issue(s)")){
							rootCause="";
						}
						if(resolution.equals("What action(s) led to the resolution")){
							resolution="";
						}
						
						//input validation:
						if (tech.length()<2){
							JOptionPane.showMessageDialog(null," Please check Tech input value and try again");
							return;
						}
						boolean newRecord = checkRRNewUpdate.isSelected();
						if(!newRecord){
							if(!db.repairRecordExists(RNumber)){
								JOptionPane.showMessageDialog(null,"Record not found, please confirm record#. To add a new record, please set  'New Record' button located below 'Refresh Table' button");
								return;
							}						
						}
						if(inDate.length()!=0){
							String date = inDate;
							String year ="";
							String day="";
							String month="";
							if(date.length()==10){
								if((date.charAt(0)=='2') && (date.charAt(1)=='0')){
									day = date.substring(8, 10);
									month = date.substring(5,7);
									year = date.substring(2, 4);
									}else{
										day = date.substring(3, 5);
										month = date.substring(0,2);
										year = date.substring(8, 10);
										}	
								}else if (date.length()==8){
									day = date.substring(3, 5);	
									month = date.substring(0,2);
									year = date.substring(6, 8);	
									} else if(date.length()==6){
										month ="0"+date.substring(0,1);
										day = "0"+date.substring(2, 3);
										year = date.substring(4,6);	
										}else if(date.length()==7){
											if(date.charAt(1)=='-'||date.charAt(1)=='/'||date.charAt(1)=='.'){
												month ="0"+date.substring(0,1);
												day = date.substring(2, 4);
												year = date.substring(5,7);	
												}else{
													month =date.substring(0,2);
													day = 0+date.substring(3, 4);
													year = date.substring(5,7);
													}
											}else{
												JOptionPane.showMessageDialog(null, "Improper date size in 'Down Since' text field");
												return;		
												}	
							inDate="20"+year+"-"+month+"-"+day;	
							System.out.println("In date is "+inDate);
							}
						if(outDate.length()!=0){
							String date = outDate;
							String year ="";
							String day="";
							String month="";
							if(date.length()==10){
								if((date.charAt(0)=='2') && (date.charAt(1)=='0')){
									day = date.substring(8, 10);
									month = date.substring(5,7);
									year = date.substring(2, 4);
									}else{
										day = date.substring(3, 5);
										month = date.substring(0,2);
										year = date.substring(8, 10);
										}	
								}else if (date.length()==8){
									day = date.substring(3, 5);	
									month = date.substring(0,2);
									year = date.substring(6, 8);	
									} else if(date.length()==6){
										month ="0"+date.substring(0,1);
										day = "0"+date.substring(2, 3);
										year = date.substring(4,6);	
										}else if(date.length()==7){
											if(date.charAt(1)=='-'||date.charAt(1)=='/'||date.charAt(1)=='.'){
												month ="0"+date.substring(0,1);
												day = date.substring(2, 4);
												year = date.substring(5,7);	
												}else{
													month =date.substring(0,2);
													day = 0+date.substring(3, 4);
													year = date.substring(5,7);
													}
											}else{
												JOptionPane.showMessageDialog(null, "Improper date size in 'Down Since' text field");
												return;		
												}	
							outDate="20"+year+"-"+month+"-"+day;		
							System.out.println("out date is "+outDate);
							}
				
					if(!db.SNExists(sn) && !sn.equals("")){
							String [] options={"Add Scale", "Cancel"};
							int addScale = JOptionPane.showOptionDialog(null, "Scale not found in our records. Please verify if Serial Number is correct.\n\nWould you like to add the scale to the inventory?", "Selection needed", JOptionPane.ERROR_MESSAGE, 0, null, options, options[1]);
							if(addScale ==1){
								return;
							} else{								
									String [] categorySelection={"1", "2", "3"};									
									int category = JOptionPane.showOptionDialog(null, "Please set scale category\n1-SAP scales\n2-NonSAP scales (over 45lbs)\n3-Low capacity units (under 45lbs)", "Select Category", JOptionPane.ERROR_MESSAGE, 0, null, categorySelection, categorySelection[2]);
									if (category>0){
									System.out.println("\nSN is "+sn);
									if(db.AddNewScale(sn, String.valueOf(category+1))){										
										JOptionPane.showMessageDialog(null,"Operation successful.\nScale added to inventory" );										
									}else{
										JOptionPane.showMessageDialog(null,"Operation unsuccessful.\nPlease check if scale is already in the inventory");
									}		
									}else{
										return;
									}
							}
						}
						if(newRecord){
							if(db.addRepairRecord(sn, status, tech, inDate, outDate, symptom, rootCause, resolution, 0)){
								JOptionPane.showMessageDialog(null, "New record added successfully");
								return;
							}else{
								JOptionPane.showMessageDialog(null, "Failure adding new record. Check dates and try again");
								return;
							}
						}else{
							if(db.addRepairRecord(sn, status, tech, inDate, outDate, symptom, rootCause, resolution,Integer.valueOf(RNumber))){
								JOptionPane.showMessageDialog(null, "Record updated successfully");
								return;
							}else{
								JOptionPane.showMessageDialog(null, "issues adding new record");
								return;
							}
						}
				
				
							
					}
				});
		//on screen keyboardss
		btnKeyboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startOSK();
			}
		});	
		btnKeyboard2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startOSK();
			}
		});
		btnKeyboard3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startOSK();
			}
		});
		btnKeyboard4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startOSK();
			}			
		});
		btnKeyboard5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startOSK();
			}
		});		
	}

//this method displays checkboxes on panel1
private void showCheckBoxes(){		
	check3Panel1.setVisible(true);
	check4Panel1.setVisible(true);
	check5Panel1.setVisible(true);
}
//this method removes text entered and enables all user controls that may have been disabled on panel1
private void clearUnlockControls(){
	//enable all textboxes except for SN input
	txtID.setEnabled(true);
	txtSN.setEnabled(true);
	boxWorkstation.setEnabled(true);
	boxDep.setEnabled(true);
	btnID.setEnabled(true);
	txtSN.setEnabled(false);
	txtSN.setText("");		
	txtReading.setText("");		
	txtWeight.setText("15.00");
	
}
//this method changes the ScalePM tab to as it was first opened on panel1

private void setScalePMDefaults(){
	//hiding all elements			
	boxDep.setVisible(false);
	lblDepartment.setVisible(false);				
	lblWorkstation.setVisible(false);		
	boxWorkstation.setVisible(false);		
	lblSN.setVisible(false);		
	txtSN.setVisible(false);
	lblNewSN.setVisible(false);
	btnDone.setVisible(false);
	lblSNVerification.setVisible(false);		
	lblLbs.setVisible(false);
	lblLbs2.setVisible(false);
	btnSNYes.setVisible(false);
	btnSNNo.setVisible(false);
	lblReading.setVisible(false);
	txtReading.setVisible(false);
	lblAdj.setVisible(false);
	btnAdjYes.setVisible(false);
	btnAdjNo.setVisible(false);
	lblReason.setVisible(false);			
	btnProceed.setVisible(false);		
	btnNext.setVisible(false);
	btnExit.setVisible(false);		
	txtSN.setVisible(false);
	lblSN.setVisible(false);
	btnSNYes.setVisible(false);
	btnSNNo.setVisible(false);
	lblSNVerification.setVisible(false);
	txtWeight.setVisible(false);
	lblWeight.setVisible(false);		
	lblAdj.setVisible(false);
	btnAdjYes.setVisible(false);
	btnAdjNo.setVisible(false);	
	//default department selection	
	btnExportToExcel.setVisible(false);	
	check1Panel1.setVisible(false);
	check2Panel1.setVisible(false);
	check3Panel1.setVisible(false);
	check4Panel1.setVisible(false);
	check5Panel1.setVisible(false);
	boxDepReport.setVisible(false);
	txtScaleReport.setVisible(false);
	boxWorkstationReport.setVisible(false);	
	boxFilterSite1.setEnabled(true);
	newScale = false;
	badReadings = 0;
	//remove notes
	dbNotes = "";
	clearUnlockControls();
	
}

//open inventory of scales when panel 3 is opened:
	private void loadPanelTable(int panelNumber){
		if(panelNumber==3){
			tblScaleInventory = new JTable();
			int scalesRequested=0;
			try{
			if(check2Panel3.isSelected()){				
						scalesRequested=1; 					
			}else if(check3Panel3.isSelected()){
					scalesRequested=2;					
			}else if(check4Panel3.isSelected()){ 
				scalesRequested=3;
			}else{
				scalesRequested=0;
			}
			}catch (Exception u){
			u.getStackTrace();	
			}
			try{
				tblValues = db.getScaleInventory(scalesRequested, boxFilterSite3.getSelectedItem().toString());			
			
			tblScaleInventory.setModel(new DefaultTableModel(tblValues, new String[]{"Serial No.","Workstation", "Cat.", "Status", "Checked D.", "Cap.", "Model","Manufacturer","Warranty Termination", "Date Purchased", "Other Details"}));			
			tblScaleInventory.getColumnModel().getColumn(0).setPreferredWidth(75);  //Serial No.
			tblScaleInventory.getColumnModel().getColumn(1).setPreferredWidth(95);  //Workstation
			tblScaleInventory.getColumnModel().getColumn(2).setPreferredWidth(20);  //Category
			tblScaleInventory.getColumnModel().getColumn(3).setPreferredWidth(58);  //Status
			tblScaleInventory.getColumnModel().getColumn(4).setPreferredWidth(68);  //Date checked
			tblScaleInventory.getColumnModel().getColumn(5).setPreferredWidth(30);  //Capacity
			tblScaleInventory.getColumnModel().getColumn(6).setPreferredWidth(66); //Model
			tblScaleInventory.getColumnModel().getColumn(7).setPreferredWidth(83);  //Manufacturer
			tblScaleInventory.getColumnModel().getColumn(8).setPreferredWidth(70);  //Warranty termination
			tblScaleInventory.getColumnModel().getColumn(9).setPreferredWidth(70);  //Date purchased
			tblScaleInventory.getColumnModel().getColumn(10).setPreferredWidth(30);  //other details			
			stblScaleInventory = new JScrollPane(tblScaleInventory);			
			stblScaleInventory.setBounds(10, 55, 715, 236);			
			panel3.add(stblScaleInventory);
			
	}catch (Exception table){
		System.out.println("Unable to generate table");
		table.printStackTrace();
	}			
		}else if(panelNumber==4){
			tblManageWorkstations = new JTable();
			int stationsRequested=0;
			try{
			if(check2Panel4.isSelected()){				
					stationsRequested=1;				
			}else if(check3Panel4.isSelected()){   				
					stationsRequested=2; 				
			}else if(check4Panel4.isSelected()){         
				stationsRequested=3;
			}else{
				stationsRequested=0;
			}
			}catch (Exception u){
			u.getStackTrace();	
			}
			try{
				tblValues = db.getWorkstationIventory(stationsRequested, boxFilterSite4.getSelectedItem().toString());			
			
			tblManageWorkstations.setModel(new DefaultTableModel(tblValues, new String[]{"Workstation","Scale SN", "Department", "Site", "Cat."}));			
			tblManageWorkstations.getColumnModel().getColumn(0).setPreferredWidth(85);  //Wostation
			tblManageWorkstations.getColumnModel().getColumn(1).setPreferredWidth(80);  //Scale SN
			tblManageWorkstations.getColumnModel().getColumn(2).setPreferredWidth(76);  //Department
			tblManageWorkstations.getColumnModel().getColumn(3).setPreferredWidth(40);  //Site
			tblManageWorkstations.getColumnModel().getColumn(4).setPreferredWidth(20);  //Category
			
			stblManageWorkstations = new JScrollPane(tblManageWorkstations);			
			stblManageWorkstations.setBounds(20, 55, 430, 236);			
			panel4.add(stblManageWorkstations);
			
	}catch (Exception table){
		System.out.println("Unable to generate table");
		table.printStackTrace();
	}
			
			}else if(panelNumber ==5){
				tblRepairs = new JTable();
				int scalesRequested=0;
				try{
				if(check2Panel5.isSelected()){				
							scalesRequested=1; 					
				}else if(check3Panel5.isSelected()){
						scalesRequested=2;					
				}else if(check4Panel5.isSelected()){ 
					scalesRequested=3;
				}else{
					scalesRequested=0;
				}
				}catch (Exception u){
				u.getStackTrace();	
				}
				try{
					
					tblValues = db.getRepairRecords(scalesRequested, boxFilterSite5.getSelectedItem().toString());				
					
				tblRepairs.setModel(new DefaultTableModel(tblValues, new String[]{"Record#","Down since", "Scale", "Status", "Symptom", "Root Cause", "Resolution","Repair Date","Tech"}));			
				tblRepairs.getColumnModel().getColumn(0).setPreferredWidth(60);  //Rep #
				tblRepairs.getColumnModel().getColumn(1).setPreferredWidth(68);  //WDown Since
				tblRepairs.getColumnModel().getColumn(2).setPreferredWidth(75);  //Scale
				tblRepairs.getColumnModel().getColumn(3).setPreferredWidth(60);  //Status
				tblRepairs.getColumnModel().getColumn(4).setPreferredWidth(100);  //Symptom
				tblRepairs.getColumnModel().getColumn(5).setPreferredWidth(100);  //Root Cause
				tblRepairs.getColumnModel().getColumn(6).setPreferredWidth(100); //Resolution
				tblRepairs.getColumnModel().getColumn(7).setPreferredWidth(68);  //Repair Date
				tblRepairs.getColumnModel().getColumn(8).setPreferredWidth(30);  //Repaired By			
				stblRepairs = new JScrollPane(tblRepairs);			
				stblRepairs.setBounds(10, 55, 720, 236);			
				panel5.add(stblRepairs);
				
		}catch (Exception table){
			System.out.println("Unable to generate table");
			table.printStackTrace();
		}
			}		
	}
	/*
	//timer for showing the tooltip text
	public void timerTipText(){
		
	timer = new Timer();
	timer.schedule( new TimerTask(){				
		public void run(){
			if (txtMSSN.getText().length()==0){
				txtMSSN.setText("SN");
			}else if (txtMSSN.getText().length()==2){
				txtMSSN.setText("");
		}
		if (txtMSWorkstation.getText().length()==0){
			txtMSWorkstation.setText("Workstation");
		}else if (txtMSWorkstation.getText().length()==11){
			txtMSWorkstation.setText("");
		
	}
	if (txtMSWarranty.getText().length()==0){
			txtMSWarranty.setText("Date War.");
		}else if (txtMSWarranty.getText().length()==9){
			txtMSWarranty.setText("");
		
	}	
	if (txtMSPurchased.getText().length()==0){
			txtMSPurchased.setText("Date Pur.");
		}else if (txtMSPurchased.getText().length()==9){
			txtMSPurchased.setText("");
		
	}	
	if (txtMSDimensions.getText().length()==0){
			txtMSDimensions.setText("Dim.");
		}else if (txtMSDimensions.getText().length()==4){
			txtMSDimensions.setText("");
		}
		}				
	}, 3000, 10000);//start time, then period (secx1000)
	}*/
	
	//load department list; runs when program starts
	private void loadDepartments(int panel){
		System.out.println("loadDepartments called");
		if( panel == 1){
			try {
				ResultSet rs = db.getDepartmentList(1, site);		
					
						do{
							boxDep.removeItemAt(0);							
						} while (boxDep.getItemCount()!=0);
						System.out.println("All items removed");
						boxDep.addItem("Select Department");
						try{
							int i=1;
						while(rs.next()){				
							boxDep.addItem(rs.getString(1));	
							System.out.println("item added: "+boxDep.getItemAt(i));
							i++;
						}	
						}catch (Exception load){
							load.printStackTrace();
							System.out.println("Unable to load departments");
						}						
							
			}catch(Exception db){
				db.printStackTrace();
				System.out.println("Unable to get department list");
			}
		}else if( panel == 2){
				try {
					ResultSet rs = db.getDepartmentList(2, boxFilterSite2.getSelectedItem().toString());
					boxDepReport.removeAllItems();
					boxDepReport.addItem("Department");
					while(rs.next()){				
						boxDepReport.addItem(rs.getString(1));									
					}		
					int i = 0;
					while( i<boxDepReport.getItemCount()){
						if (boxDepReport.getItemAt(i).toString().length()<3){
							boxDepReport.removeItemAt(i);
						}else{
						i++;	
						}
					}
				
				} catch (Exception v){
					v.printStackTrace();
				}	
		}else if( panel == 4){
			try {
				ResultSet rs = db.getDepartmentList(4, null);
				boxMWDep.removeAllItems();
				boxMWDep.addItem("Department");
				while(rs.next()){				
					boxMWDep.addItem(rs.getString(1));									
				}		
				int i = 0;
				while( i<boxMWDep.getItemCount()){
					if (boxMWDep.getItemAt(i).toString().length()<3){
						boxMWDep.removeItemAt(i);
					}else{
					i++;	
					}
				}
			} catch (Exception v){
				v.printStackTrace();
			}	
			try {	
				ResultSet rs = db.getSitetList();				
				while ( rs.next()){
				boxMWSite.addItem(rs.getString(1));	
				}
				int i = 0;
				while( i<boxMWSite.getItemCount()){
					if (boxMWSite.getItemAt(i).toString().length()<3){
						boxMWSite.removeItemAt(i);
					}else{
					i++;	
					}
				}
			}catch (Exception h){
				h.printStackTrace();
			}			
		}
	}
	
	//start on screen keyboard
	private void startOSK(){
		try{
			String sysroot = System.getenv("SystemRoot"); //$NON-NLS-1$
			Runtime.getRuntime().exec("cmd.exe /c "+sysroot + "\\system32\\osk.exe /n"); //$NON-NLS-1$ //$NON-NLS-2$
            Runtime.getRuntime().exec("osk");
		
		} catch (Exception e) {			
			e.printStackTrace();
			return;
		}
	}
	public void placeCursor(Component comp){
		comp.requestFocusInWindow();
	}
}
