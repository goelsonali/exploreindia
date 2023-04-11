package com.explore.ec;

import com.explore.ec.domain.Difficulty;
import com.explore.ec.domain.Region;
import com.explore.ec.service.TourPackageService;
import com.explore.ec.service.TourService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
@ComponentScan({"com.explore.ec.service", "com.explore.ec.web"})
@EnableJpaRepositories("com.explore.ec.repository")
@EnableTransactionManagement
@EntityScan("com.explore.ec.domain")
public class ExploreindiaApplication implements CommandLineRunner {

	@Autowired
	private TourService tourService;

	@Autowired
	private TourPackageService tourPackageService;

	public static void main(String[] args) {
		SpringApplication.run(ExploreindiaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		createTourPackages();
		long numberOfTourPackages = tourPackageService.total();

		createTours("ExploreIndia.json");
		long numberOfTours = tourService.total();
	}

	/**
	 * Sample data set to be created at the startup.
	 */
	private void createTourPackages() throws IOException {
		tourPackageService.createTourPackage("BI", "Backpack India");
		tourPackageService.createTourPackage("CI", "Culture of India");
		tourPackageService.createTourPackage("YI", "Yoga in India");
		tourPackageService.createTourPackage("MO", "Mountains of India");
		tourPackageService.createTourPackage("RI", "Rivers of India");

	}

	private void createTours(String fileToImport) throws IOException {
		ToursFromFile.read(fileToImport).forEach(importedTour ->
				tourService.createTour(importedTour.title(),importedTour.description(),importedTour.blurb(),importedTour.price(),importedTour.length(),
						importedTour.bullets(),importedTour.keywords(),importedTour.packageType(),importedTour.difficulty(),importedTour.region()));

	}

	private static class ToursFromFile {

		private String packageType, title, description, blurb, price, length, bullets, keywords, difficulty, region;

		static List<ToursFromFile> read(String fileToImport) throws IOException {
			return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
					.readValue(new FileInputStream(fileToImport), new TypeReference<List<ToursFromFile>>() {});
		}
		protected ToursFromFile(){}

		public String packageType() {
			return packageType;
		}

		public String title() {
			return title;
		}

		public String description() {
			return description;
		}

		public String blurb() {
			return blurb;
		}

		public Integer price() {
			return Integer.parseInt(price);
		}

		public String length() {
			return length;
		}

		public String bullets() {
			return bullets;
		}

		public String keywords() {
			return keywords;
		}

		public Difficulty difficulty() {
			return Difficulty.valueOf(difficulty);
		}

		public Region region() {
			return Region.findByLabel(region);
		}
	}

}
