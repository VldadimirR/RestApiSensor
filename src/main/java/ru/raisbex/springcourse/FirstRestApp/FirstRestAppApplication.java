package ru.raisbex.springcourse.FirstRestApp;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.raisbex.springcourse.FirstRestApp.dto.MeasurementDTO;
import ru.raisbex.springcourse.FirstRestApp.dto.SensorDTO;

import java.io.IOException;
import java.util.Random;

@SpringBootApplication
public class FirstRestAppApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FirstRestAppApplication.class, args);

		// Эмуляция отправки 1000 измерений
		RestTemplate restTemplate = new RestTemplate();
		String sensorUrl = "http://localhost:8080/sensors/registration";
		String measurementUrl = "http://localhost:8080/measurements/add";
		String getMeasurementsUrl = "http://localhost:8080/measurements";

		// Регистрация нового сенсора
//		SensorDTO sensorDTO = new SensorDTO();
//		sensorDTO.setName("New Sensor3");
//		HttpEntity<SensorDTO> sensorRequest = new HttpEntity<>(sensorDTO);
//		ResponseEntity<String> sensorResponse = restTemplate.postForEntity(sensorUrl, sensorRequest, String.class);

		// Добавление 1000 измерений
//		Random random = new Random();
//		for (int i = 0; i < 1000; i++) {
//			MeasurementDTO measurementDTO = new MeasurementDTO();
//			measurementDTO.setValue(Float.parseFloat(String.format("%.1f", random.nextFloat() * 100).replace(',', '.')));			measurementDTO.setRaining(random.nextBoolean());
//			measurementDTO.setSensor(sensorDTO);
//			HttpEntity<MeasurementDTO> measurementRequest = new HttpEntity<>(measurementDTO);
//			ResponseEntity<String> measurementResponse = restTemplate.postForEntity(measurementUrl, measurementRequest, String.class);
//			// Проверка создания измерения
//			if (measurementResponse.getStatusCode() != HttpStatus.OK) {
//				System.out.println("Failed to add measurement: " + measurementResponse.getBody());
//			}
//		}


		// Получение всех Измерений
		ResponseEntity<MeasurementDTO[]> response = restTemplate.getForEntity(getMeasurementsUrl, MeasurementDTO[].class);
		MeasurementDTO[] measurements = response.getBody();
//		for (MeasurementDTO measurement : measurements) {
//			System.out.println(measurement);
//		}

		if (measurements == null || measurements.length == 0) {
			System.out.println("Нет доступных данных измерений.");
			return;
		}

// Создание графика
		XYChart chart = new XYChartBuilder()
				.width(800)
				.height(600)
				.title("График измерений")
				.xAxisTitle("X")
				.yAxisTitle("Y")
				.theme(Styler.ChartTheme.Matlab)
				.build();

		int step = measurements.length / 10; // Определите шаг
		double[] xData = new double[measurements.length / step];
		double[] yData = new double[measurements.length / step];

		for (int i = 0, j = 0; i < measurements.length; i += step, j++) {
			xData[j] = j; // Используйте j в качестве X
			double sum = 0.0;
			for (int k = i; k < Math.min(i + step, measurements.length); k++) {
				sum += measurements[k].getValue();
			}
			yData[j] = sum / step;
		}

// Измените стиль линии
		XYSeries series = chart.addSeries("Измерения", xData, yData);
		series.setLineColor(XChartSeriesColors.RED); // Измените цвет
		series.setLineWidth(2); // Измените толщину линии

// Сохраните график
		BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);

	}
}
