public class Product {

	private String productName;
	private String fileTrasmitionYear;
	private String fileTrasmitionMonth;
	private String fileTrasmitionDay;
	private String fileTrasmitionHour;
	private String fileTrasmitionMin;
	private String fileTrasmitionSec;

	private String fileGenearationYear;
	private String fileGenearationMonth;
	private String fileGenearationDay;

	private String fileGenerationHour;
	private String fileGenerationMin;
	private String fileGenerationSec;
	
	private Integer expDisseminatedFiles;
	private Long expTimelinessMin;
	private Integer actualTimelinessMin;

	private Long timelinessOffsetMin;

	private double dimension; // KiB
	private boolean isOutOfTinmeliness;
	private String fileName;
	private boolean isDuplicated = false;

	public Product(String name) {
		this.productName = name;
		Long timeliness = Timeliness.timelinessMap.get(name);
		this.setExpectedTimeliness(timeliness);
		Long timelinessOffset = Offset.offsetMap.get(name);
		this.setTimelinessOffsetMin(timelinessOffset);

		Integer expectedDisseminatedFiles = ExpectedDisseminatedProducts.expFilesMap
				.get(name);
		this.setExpDisseminatedFiles(expectedDisseminatedFiles);
		

	}

	private void setExpectedTimeliness(Long timeliness) {
		this.expTimelinessMin = timeliness;

	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFileGenYear() {
		return fileTrasmitionYear;
	}

	public void setFileTrasmitionYear(String year) {
		this.fileTrasmitionYear = year;
	}

	public String getFileGenMonth() {
		return fileTrasmitionMonth;
	}

	public void setFileTrasmitionMonth(String month) {
		this.fileTrasmitionMonth = month;
	}

	public String getDay() {
		return fileTrasmitionDay;
	}

	public void setFileTrasmitionDay(String day) {
		this.fileTrasmitionDay = day;
	}

	public Double getDimension() {
		return dimension;
	}

	public void setDimension(double dimension) {
		this.dimension = dimension;
	}

	public String getProdGenMonth() {
		return fileGenearationMonth;
	}

	public void setFileGenerationMonth(String prodGenMonth) {
		this.fileGenearationMonth = prodGenMonth;
	}

	public String getProdGenYear() {
		return fileGenearationYear;
	}

	public void setFileGenerationYear(String prodGenYear) {
		this.fileGenearationYear = prodGenYear;
	}

	public String getProdGenDay() {
		return fileGenearationDay;
	}

	public void setFileGenerationDay(String prodGenDay) {
		this.fileGenearationDay = prodGenDay;
	}

	public String getFileTrasmitionHour() {
		return fileTrasmitionHour;
	}

	public void setFileTrasmitionHour(String fileTrasmitionHour) {
		this.fileTrasmitionHour = fileTrasmitionHour;
	}

	public String getFileTrasmitionMin() {
		return fileTrasmitionMin;
	}

	public void setFileTrasmitionMin(String fileTrasmitionMin) {
		this.fileTrasmitionMin = fileTrasmitionMin;
	}

	public String getFileTrasmitionSec() {
		return fileTrasmitionSec;
	}

	public void setFileTrasmitionSec(String fileTrasmitionSec) {
		this.fileTrasmitionSec = fileTrasmitionSec;
	}

	public String getFileGenerationHour() {
		return fileGenerationHour;
	}

	public void setFileGenerationHour(String fileGenerationHour) {
		this.fileGenerationHour = fileGenerationHour;
	}

	public String getFileGenerationMin() {
		return fileGenerationMin;
	}

	public void setFileGenerationMin(String fileGenerationMin) {
		this.fileGenerationMin = fileGenerationMin;
	}

	public String getFileGenerationSec() {
		return fileGenerationSec;
	}

	public void setFileGenerationSec(String fileGenerationSec) {
		this.fileGenerationSec = fileGenerationSec;
	}

	public void computeTimeliness() {

		Integer trasmissionSeconds = Integer.valueOf(fileTrasmitionSec)
				+ Integer.valueOf(fileTrasmitionMin) * 60
				+ Integer.valueOf(fileTrasmitionHour) * 3600;
		Integer generationSeconds = Integer.valueOf(fileGenerationSec)
				+ Integer.valueOf(fileGenerationMin) * 60
				+ Integer.valueOf(fileGenerationHour) * 3600;

		Integer differenceDay = Integer.valueOf(fileTrasmitionDay)
				- Integer.valueOf(fileGenearationDay);

		if (differenceDay != 0 && Integer.valueOf(fileGenerationHour) != 0)
			this.actualTimelinessMin = (trasmissionSeconds + ((1440 * 60) - generationSeconds)) / 60;
		else
			this.actualTimelinessMin = (trasmissionSeconds - generationSeconds) / 60;

		if (this.actualTimelinessMin > (this.expTimelinessMin + this.timelinessOffsetMin))
			this.isOutOfTinmeliness = true;

	}

	public boolean isOutOfTinmeliness() {
		return isOutOfTinmeliness;
	}

	public Integer getActualTimelinessMin() {
		return actualTimelinessMin;
	}

	public void setFile(String filename) {
		this.fileName = filename;

	}

	public String getFileName() {
		return this.fileName;

	}

	public void setIsDuplicated(boolean isDuplicated) {
		this.isDuplicated = isDuplicated;
	}

	public boolean isDuplicated() {

		return this.isDuplicated;
	}

	public Long getTimelinessOffsetMin() {
		return timelinessOffsetMin;
	}

	public void setTimelinessOffsetMin(Long timelinessOffsetMin) {
		this.timelinessOffsetMin = timelinessOffsetMin;
	}

	public Integer getExpDisseminatedFiles() {
		return expDisseminatedFiles;
	}

	public void setExpDisseminatedFiles(Integer expDisseminatedFiles) {
		this.expDisseminatedFiles = expDisseminatedFiles;
	}
}
