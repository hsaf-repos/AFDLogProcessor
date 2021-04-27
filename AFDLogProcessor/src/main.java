import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

// The input file have been generated through the following command : "grep -r h13_2018 >> output/h13"
public class main {

	private static String outputFolderPath = "C:\\Users\\telespazio\\Documents\\HSAF\\eclipse-workspace\\AFDLogProcessor\\outputExcelFiles\\";
	private static String inputFolderPath = "C:\\Users\\telespazio\\Documents\\HSAF\\eclipse-workspace\\AFDLogProcessor";
	private static String inputFolderName = "\\inputFolder";
	private static String inputFolderFullName = inputFolderPath.concat(inputFolderName);
	private static int year = 2019;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		File inputFile = new File(inputFolderFullName);
		Map<String, List<Product>> productsMap = new HashMap<String, List<Product>>();
		List<String> productNames = new ArrayList<>();

		File[] listOfInputFiles = inputFile.listFiles();
		Map<String, List<String>> errorLogs = null;

		System.out.println("List of files to process in folder : " + inputFolderFullName);
		System.out.println("");
		for (File file : listOfInputFiles) {

			productNames.add(file.getName());

		}

		errorLogs = new HashMap<String, List<String>>();
		for (File file : listOfInputFiles) {

			List<Product> products = new ArrayList<Product>();

			List<String> errorList = new ArrayList<>();

			int lineCounter = 0;

			System.out.println("Processing product : " + file.getName());

			BufferedReader fileRd = new BufferedReader(new FileReader(file.getAbsoluteFile()));

			Product newProduct = new Product(file.getName());

			String readLine = fileRd.readLine();

			try {

				processLine(readLine, newProduct);
			} catch (Exception e1) {

				errorList.add("Error processing Line " + lineCounter + " for product " + file.getName());
			}
			lineCounter++;

			products.add(newProduct);

			while (readLine != null) {

				newProduct = new Product(file.getName());
				try {

					lineCounter++;
					readLine = fileRd.readLine();
					newProduct = processLine(readLine, newProduct);
					newProduct.computeTimeliness();
					if (isDuplicated(newProduct, products)) {
						newProduct.setIsDuplicated(true);
						continue;
					}
					products.add(newProduct);
				} catch (Exception e) {

					errorList.add("Error processing Line " + lineCounter + " for product " + file.getName());

				}

			}
			productsMap.put(file.getName(), products);

			errorLogs.put(file.getName(), errorList);

		}

		FileWriter excelCSV_MonthlyPerfTable_1stSem = new FileWriter(outputFolderPath.concat("ExcelCSV_MonthlyPerfTable_1st.txt"));
		FileWriter excelCSV_MonthlyPerfTable_2ndSem = new FileWriter(outputFolderPath.concat("ExcelCSV_MonthlyPerfTable_2nd.txt"));
		
		FileWriter excelCSV_OOTandLostTable_1st = new FileWriter(outputFolderPath.concat("ExcelCSV_OOTandLostTable_1st.txt"));
		FileWriter excelCSV_OOTandLostTable_2nd = new FileWriter(outputFolderPath.concat("ExcelCSV_OOTandLostTable_2nd.txt"));

		BufferedWriter excelCSV_MonthlyPerfTable_1stSem_bw = new BufferedWriter(excelCSV_MonthlyPerfTable_1stSem);
		BufferedWriter excelCSV_MonthlyPerfTable_2ndSem_bw = new BufferedWriter(excelCSV_MonthlyPerfTable_2ndSem);
		
		BufferedWriter excelCSV_OOTandLostTable_1st_bw = new BufferedWriter(excelCSV_OOTandLostTable_1st);
		BufferedWriter excelCSV_OOTandLostTable_2nd_bw = new BufferedWriter(excelCSV_OOTandLostTable_2nd);

		for (String productName : productNames) {

			ProductStatistics prodStat = new ProductStatistics(productName, year);

			List<Product> productsByName = productsMap.get(productName);

			for (Product productGenerated : productsByName) {

				// System.out.println(productGenerated.getFileName());
				if (productGenerated.getFileName() != null)
					prodStat.updateStat(productGenerated);
				// System.out.println("-----");
			}

			prodStat.printStat(errorLogs.get(productName));

			// Write CSV
			System.out.println("Write Csv files");

			excelCSV_MonthlyPerfTable_1stSem_bw.write(prodStat.csvMonthlyPerf(1));
			excelCSV_MonthlyPerfTable_1stSem_bw.newLine();
			excelCSV_MonthlyPerfTable_2ndSem_bw.write(prodStat.csvMonthlyPerf(2));
			excelCSV_MonthlyPerfTable_2ndSem_bw.newLine();
			
			excelCSV_OOTandLostTable_1st_bw.write(prodStat.csvOOTandLost(1));
			excelCSV_OOTandLostTable_1st_bw.newLine();
			excelCSV_OOTandLostTable_2nd_bw.write(prodStat.csvOOTandLost(2));
			excelCSV_OOTandLostTable_2nd_bw.newLine();

		}

		if (excelCSV_MonthlyPerfTable_1stSem_bw != null) {
			excelCSV_MonthlyPerfTable_1stSem_bw.flush();
			excelCSV_MonthlyPerfTable_1stSem_bw.close();
		}

		if (excelCSV_MonthlyPerfTable_2ndSem_bw != null) {
			excelCSV_MonthlyPerfTable_2ndSem_bw.flush();
			excelCSV_MonthlyPerfTable_2ndSem_bw.close();
		}
		
		if (excelCSV_OOTandLostTable_1st_bw != null) {
			excelCSV_OOTandLostTable_1st_bw.flush();
			excelCSV_OOTandLostTable_1st_bw.close();
		}

		if (excelCSV_OOTandLostTable_2nd_bw != null) {
			excelCSV_OOTandLostTable_2nd_bw.flush();
			excelCSV_OOTandLostTable_2nd_bw.close();
		}

	}

	private static boolean isDuplicated(Product newProduct, List<Product> products) {
		for (Product product : products) {
			if (product.getFileName().compareTo(newProduct.getFileName()) == 0)
				return true;
		}
		return false;
	}

	private static Product processLine(String readLine, Product newProduct) throws Exception {

		if (!readLine.endsWith("KiB") && !readLine.endsWith("MiB") && !readLine.endsWith("B")) {
			throw new Exception();
		}

		String file = readLine.split("EUMETCAST ")[1];
		newProduct.setFile(file);

		String fileData = file.split("_")[1];

		newProduct.setFileGenerationYear(fileData.substring(0, 4));
		newProduct.setFileGenerationMonth(fileData.substring(4, 6));
		newProduct.setFileGenerationDay(fileData.substring(6, 8));

		if (newProduct.getProductName().compareTo("h10") != 0 && newProduct.getProductName().compareTo("h11") != 0
				&& newProduct.getProductName().compareTo("h12") != 0
				&& newProduct.getProductName().compareTo("h13") != 0) {
			String fileTime = file.split("_")[2];
			newProduct.setFileGenerationHour(fileTime.substring(0, 2));
			newProduct.setFileGenerationMin(fileTime.substring(2, 4));
			newProduct.setFileGenerationSec("0");
		} else {
			newProduct.setFileGenerationHour("05");
			newProduct.setFileGenerationMin("30");
			newProduct.setFileGenerationSec("0");
		}
		String subString = readLine.substring(10, 18);
		newProduct.setFileTrasmitionYear(subString.substring(0, 4));
		newProduct.setFileTrasmitionMonth(subString.substring(4, 6));
		newProduct.setFileTrasmitionDay(subString.substring(6, 8));

		subString = readLine.substring(34, 42);
		newProduct.setFileTrasmitionHour(subString.substring(0, 2));
		newProduct.setFileTrasmitionMin(subString.substring(3, 5));
		newProduct.setFileTrasmitionSec(subString.substring(6, 8));

		String productExtention = FilesExtention.filesExtentionMap.get(newProduct.getProductName());
		int startDimension = readLine.lastIndexOf(productExtention) + 4;
		int endDimension = readLine.length() - 4;
		String dimension = readLine.substring(startDimension, endDimension);

		dimension = dimension.replace(".", "");

		double dimensionConverted = Double.parseDouble(dimension);

		if (readLine.endsWith("KiB")) {

			newProduct.setDimension(dimensionConverted / 1024);

		} else if (readLine.endsWith("MiB")) {

			newProduct.setDimension(dimensionConverted);

		} else if (readLine.endsWith("B")) {

			newProduct.setDimension(dimensionConverted / 1000000.0);
		}

		return newProduct;

	}
	
	void plotStatistics() {

		DefaultCategoryDataset successfulRate1stSem = new DefaultCategoryDataset();
		DefaultCategoryDataset successfulRate2ndSem = new DefaultCategoryDataset();

						
		
		

		JFreeChart chart = ChartFactory.createBarChart("Success Rate 1st semester", "", "",successfulRate1stSem, PlotOrientation.VERTICAL, true,
				true, true);

		

		try {

			ChartUtilities.saveChartAsPNG(
					new java.io.File("SuccessFisrtSem".concat(".png")),
					chart, 1500, 500);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
