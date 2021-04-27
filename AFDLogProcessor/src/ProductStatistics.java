import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.plaf.synth.SynthStyleFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ProductStatistics {

	final private Map<Integer, String> MONTHS = new HashMap<>();

	final static String TABLE_LAYOUT_1_0 = "%-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s\n";
	final static String TABLE_LAYOUT_1_1 = "%-30s %-30s %-30s %-30.2f %-30s %-30.2f %-30s %-30.2f %-30s %-30.2f %-30.2f %-30.2f %-30.2f\n";

	final static String TABLE_LAYOUT_2_0 = "%-20s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n";
	final static String TABLE_LAYOUT_2_1 = "%-20s %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f\n";
	final static String TABLE_LAYOUT_2_2 = "%-20s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n";
	String prodName;

	Vector<Integer> monthlyProduction = new Vector<>(12);

	Vector<Vector<Integer>> dailyProduction = new Vector<Vector<Integer>>(31);

	Vector<Double> totalMonthlyDimProduction = new Vector<>(12);

	Vector<Vector<Double>> dailyDimension = new Vector<Vector<Double>>(31);

	Vector<Double> maxmonthDimProduction = new Vector<>(12);

	Vector<Double> minmonthDimProduction = new Vector<>(12);

	Vector<Double> meanmonthDimProduction = new Vector<>(12);

	Vector<Double> outOfTimelinessPercentage = new Vector<>(12);

	Vector<Double> disseminationPercentage = new Vector<>(12);

	Vector<Double> successfullyDisseminatedInTimePercentage = new Vector<>(12);

	Vector<Integer> disseminatedInTime = new Vector<>(12);

	Vector<Integer> outOfTimelinessCounter = new Vector<>(12);

	Vector<Integer> duplicatedFilesCounter = new Vector<>(12);

	Vector<Integer> expectedDisseminatedFilesPerMonth = new Vector<>(12);

	private int year;

	private List<Product> outOfTimelinessFiles = new ArrayList<Product>();

	private List<Product> duplicatedFiles = new ArrayList<Product>();

	public ProductStatistics(String prodName, int year) {

		this.prodName = prodName;
		this.year = year;

		this.monthlyProduction.setSize(12);
		this.totalMonthlyDimProduction.setSize(12);

		this.maxmonthDimProduction.setSize(12);
		this.minmonthDimProduction.setSize(12);
		this.dailyProduction.setSize(12);
		this.dailyDimension.setSize(12);
		this.meanmonthDimProduction.setSize(12);
		this.outOfTimelinessCounter.setSize(12);
		this.duplicatedFilesCounter.setSize(12);
		this.outOfTimelinessPercentage.setSize(12);
		this.disseminationPercentage.setSize(12);
		this.successfullyDisseminatedInTimePercentage.setSize(12);
		this.expectedDisseminatedFilesPerMonth.setSize(12);
		this.disseminatedInTime.setSize(12);

		for (int i = 0; i < 12; i++) {

			this.monthlyProduction.set(i, 0);
			this.totalMonthlyDimProduction.set(i, 0.0);
			this.maxmonthDimProduction.set(i, 0.0);
			this.minmonthDimProduction.set(i, Double.MAX_VALUE);
			this.meanmonthDimProduction.set(i, 0.0);
			this.outOfTimelinessCounter.set(i, 0);
			this.outOfTimelinessPercentage.set(i, 0.0);
			this.disseminationPercentage.set(i, 0.0);
			this.successfullyDisseminatedInTimePercentage.set(i, 0.0);
			this.duplicatedFilesCounter.set(i, 0);
			this.expectedDisseminatedFilesPerMonth.set(i, 0);
			this.disseminatedInTime.set(i, 0);

			Vector<Integer> productionDay = new Vector<Integer>(31);
			productionDay.setSize(31);
			for (int j = 0; j < 31; j++) {

				productionDay.set(j, 0);
			}

			this.dailyProduction.set(i, productionDay);

			Vector<Double> dimensionDay = new Vector<Double>(31);
			dimensionDay.setSize(31);
			for (int j = 0; j < 31; j++) {

				dimensionDay.set(j, 0.0);
			}

			this.dailyDimension.set(i, dimensionDay);
		}

		MONTHS.put(0, "JANUARY");
		MONTHS.put(1, "FEBRUARY");
		MONTHS.put(2, "MARCH");
		MONTHS.put(3, "APRIL");
		MONTHS.put(4, "MAY");
		MONTHS.put(5, "JUNE");
		MONTHS.put(6, "JULY");
		MONTHS.put(7, "AUGUST");
		MONTHS.put(8, "SEPTEMBER");
		MONTHS.put(9, "OCTOBER");
		MONTHS.put(10, "NOVEMBER");
		MONTHS.put(11, "DECEMBER");

	}

	public void updateStat(Product product) {

		Integer genMonth = Integer.valueOf(product.getProdGenMonth().replaceFirst("^0+(?!$)", "")) - 1;
		Integer genDay = Integer.valueOf(product.getProdGenDay().replaceFirst("^0+(?!$)", "")) - 1;

		int daysPerMonth = 30;
		switch (genMonth) {
		case 0:
		case 2:
		case 4:
		case 6:
		case 7:
		case 9:
		case 11:
			daysPerMonth = 31;
			break;

		case 3:
		case 5:
		case 8:
		case 10:

			daysPerMonth = 30;
			break;
		case 1:
			daysPerMonth = 28;

			break;

		default:
			break;
		}

		this.expectedDisseminatedFilesPerMonth.set(genMonth, product.getExpDisseminatedFiles() * daysPerMonth);

		this.disseminationPercentage.set(genMonth, (((double) monthlyProduction.get(genMonth) + 1)
				/ ((double) this.expectedDisseminatedFilesPerMonth.get(genMonth))) * 100);

		disseminatedInTime.set(genMonth, (monthlyProduction.get(genMonth) + 1) - outOfTimelinessCounter.get(genMonth));
		successfullyDisseminatedInTimePercentage.set(genMonth,
				(disseminatedInTime.get(genMonth) / ((double) this.expectedDisseminatedFilesPerMonth.get(genMonth)))
						* 100);

		monthlyProduction.set(genMonth, monthlyProduction.get(genMonth) + 1);

		if (monthlyProduction.get(genMonth) != 0) {
			outOfTimelinessPercentage.set(genMonth,
					(((double) outOfTimelinessCounter.get(genMonth) / ((double) monthlyProduction.get(genMonth)))
							* 100));
		}

		totalMonthlyDimProduction.set(genMonth, totalMonthlyDimProduction.get(genMonth) + product.getDimension());

		meanmonthDimProduction.set(genMonth, totalMonthlyDimProduction.get(genMonth) / monthlyProduction.get(genMonth));

		Integer currentDailyProd = dailyProduction.get(genMonth).get(genDay);
		dailyProduction.get(genMonth).set(genDay, currentDailyProd + 1);
		Double currentDailyDimen = dailyDimension.get(genMonth).get(genDay);
		dailyDimension.get(genMonth).set(genDay, currentDailyDimen + product.getDimension());

		if (product.getDimension() > maxmonthDimProduction.get(genMonth))
			maxmonthDimProduction.set(genMonth, product.getDimension());
		if (product.getDimension() < minmonthDimProduction.get(genMonth))
			minmonthDimProduction.set(genMonth, product.getDimension());

		if (product.isOutOfTinmeliness()) {
			outOfTimelinessCounter.set(genMonth, outOfTimelinessCounter.get(genMonth) + 1);
			outOfTimelinessFiles.add(product);
		}

		if (product.isDuplicated()) {
			duplicatedFilesCounter.set(genMonth, duplicatedFilesCounter.get(genMonth) + 1);
			duplicatedFiles.add(product);
		}

	}

	public void printStat(List<String> errors) {

		FileWriter statFile = null;

		try {
			statFile = new FileWriter("Statistics_".concat(this.prodName).concat(".txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			System.out.println("Write statistics file for product " + this.prodName + " ...");
			statFile.append("\n");

			statFile.append("*************************************************************************\n");
			statFile.append("************** Statistics for product : " + this.prodName + " year " + this.year
					+ " *******************\n");
			statFile.append("*************************************************************************\n");
			statFile.append("\n");

			statFile.append(String.format(TABLE_LAYOUT_1_0, "Month", "Expected", "Disseminated", "Dissemination (%)",
					"Out Of Timeliness", "OOT (%) ", "Disseminated in Time", "Success. Diss. in Time(%)",
					"Duplicated Files", "Total Dimension [Kib]", "Min month Dimension [Kib]",
					"Max month Dimension [Kib]", "Mean [Kib]"));
			statFile.append("\n");

			for (int i = 0; i < 12; i++) {

				if (minmonthDimProduction.get(i) == Double.MAX_VALUE)
					minmonthDimProduction.set(i, 0.0);
				if (maxmonthDimProduction.get(i) == Double.MAX_VALUE)
					maxmonthDimProduction.set(i, 0.0);
				if (meanmonthDimProduction.get(i) == Double.MAX_VALUE)
					meanmonthDimProduction.set(i, 0.0);

				statFile.append(String.format(TABLE_LAYOUT_1_1, MONTHS.get(i), expectedDisseminatedFilesPerMonth.get(i),
						monthlyProduction.get(i), disseminationPercentage.get(i), outOfTimelinessCounter.get(i),
						outOfTimelinessPercentage.get(i), disseminatedInTime.get(i),
						successfullyDisseminatedInTimePercentage.get(i), duplicatedFilesCounter.get(i),
						totalMonthlyDimProduction.get(i), minmonthDimProduction.get(i), maxmonthDimProduction.get(i),
						meanmonthDimProduction.get(i)));

			}
			statFile.append("\n");
			statFile.append("Daily statistics for each month\n");

			for (int i = 0; i < 12; i++) {

				statFile.append("\n");

				statFile.append(String.format(TABLE_LAYOUT_2_0, MONTHS.get(i), "01", "02", "03", "04", "05", "06", "07",
						"08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
						"24", "25", "26", "27", "28", "29", "30", "31"));
				statFile.append("\n");
				statFile.append("");

				Vector<Double> dailyDim = dailyDimension.get(i);

				statFile.append(String.format(TABLE_LAYOUT_2_1, "Daily Dim [MiB]", dailyDim.get(0), dailyDim.get(1),
						dailyDimension.get(i).get(2), dailyDim.get(3), dailyDim.get(4), dailyDim.get(5),
						dailyDim.get(6), dailyDim.get(7), dailyDim.get(8), dailyDim.get(9), dailyDim.get(10),
						dailyDim.get(11), dailyDim.get(12), dailyDim.get(13), dailyDim.get(14), dailyDim.get(15),
						dailyDim.get(16), dailyDim.get(17), dailyDim.get(18), dailyDim.get(19), dailyDim.get(20),
						dailyDim.get(21), dailyDim.get(22), dailyDim.get(23), dailyDim.get(24), dailyDim.get(25),
						dailyDim.get(26), dailyDim.get(27), dailyDim.get(28), dailyDim.get(29), dailyDim.get(30)));
				statFile.append("\n");

				Vector<Integer> dailyProd = dailyProduction.get(i);
				statFile.append(String.format(TABLE_LAYOUT_2_2, "Daily Products", dailyProd.get(0), dailyProd.get(1),
						dailyProd.get(2), dailyProd.get(3), dailyProd.get(4), dailyProd.get(5), dailyProd.get(6),
						dailyProd.get(7), dailyProd.get(8), dailyProd.get(9), dailyProd.get(10), dailyProd.get(11),
						dailyProd.get(12), dailyProd.get(13), dailyProd.get(14), dailyProd.get(15), dailyProd.get(16),
						dailyProd.get(17), dailyProd.get(18), dailyProd.get(19), dailyProd.get(20), dailyProd.get(21),
						dailyProd.get(22), dailyProd.get(23), dailyProd.get(24), dailyProd.get(25), dailyProd.get(26),
						dailyProd.get(27), dailyProd.get(28), dailyProd.get(29), dailyProd.get(30)));
				statFile.append("\n");

				statFile.append("*********************************************************************");
				statFile.append("\n");

			
			}
			statFile.append("Out of Timeliness Files (Expected: " + Timeliness.timelinessMap.get(this.prodName) + ")");
			statFile.append("\n");
			for (int i = 0; i < outOfTimelinessFiles.size(); i++) {

				statFile.append(outOfTimelinessFiles.get(i).getFileName() + " , TIMELINESS (min): "
						+ outOfTimelinessFiles.get(i).getActualTimelinessMin());
				statFile.append("\n");

			}
			statFile.append("*********************************************************************");
			statFile.append("\n");

			statFile.append("Duplicated Files");
			statFile.append("\n");
			for (int i = 0; i < duplicatedFiles.size(); i++) {

				statFile.append(duplicatedFiles.get(i).getFileName());
				statFile.append("\n");

			}
			statFile.append("*********************************************************************");
			statFile.append("\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (errors != null) {
			try {
				statFile.append("ERROR processing file for product \n");
				statFile.append("Number of errors = " + errors.size() + "\n");

				for (String error : errors) {

					statFile.append("\n");
					statFile.append(error);

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			statFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}



	public String csvMonthlyPerf(int semester) {
		
		int low;
		int up;
		
		if(semester == 1)
		{
			low = 0;
			up =6;
		}
		else
		{
			low = 6;
			up =12;
		}
		
		
		String csv = new String();
		csv = prodName + ",";

	
		
		for (int i = low; i < up; i++) {
			csv = csv + expectedDisseminatedFilesPerMonth.get(i) + ",";
			csv = csv + disseminatedInTime.get(i) + ",";
			csv = csv + successfullyDisseminatedInTimePercentage.get(i) + ",";
		}

		return csv;

	}


	
	public String csvOOTandLost(int semester) {
		
		int low;
		int up;
		
		if(semester == 1)
		{
			low = 0;
			up =6;
		}
		else
		{
			low = 6;
			up =12;
		}

		String csv = new String();

		csv = prodName + ",";

		for (int i = low; i < up; i++) {
			csv = csv + expectedDisseminatedFilesPerMonth.get(i) + ",";
			csv = csv + disseminatedInTime.get(i) + ",";
			csv = csv + outOfTimelinessCounter.get(i) + ",";
			csv = csv + outOfTimelinessPercentage.get(i) + ",";
			
			Integer lost = expectedDisseminatedFilesPerMonth.get(i) - disseminatedInTime.get(i) - outOfTimelinessCounter.get(i);
			Double lostPerc =  (lost.doubleValue() / expectedDisseminatedFilesPerMonth.get(i).doubleValue());
			
			csv = csv + lost + ",";
			csv = csv + lostPerc + ",";
		}

		return csv;

	}
}
