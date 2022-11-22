package com.example.producingwebservice;

import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	private CountryRepository countryRepository;

	@Autowired
	public CountryEndpoint(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		response.setCountry(countryRepository.findCountry(request.getName()));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "plusRequest")
	@ResponsePayload
	public PlusResponse getCountry(@RequestPayload PlusRequest request) {
		PlusResponse response = new PlusResponse();

		response.setSum(request.getNumber1() + request.getNumber2());

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ConvertImageRequest")
	@ResponsePayload
	public ConvertImageResponse convertImage(@RequestPayload ConvertImageRequest request){
		ConvertImageResponse response = new ConvertImageResponse();
		BufferedImage image = decodeToImage(request.getImage());
		File outputFile = new File("image.png");

		boolean success=false;
		try {
			success = ImageIO.write(image, "PNG", outputFile);
			Process process = new ProcessBuilder("imageTogb.exe").start();
			int exitCode = process.waitFor();
			if(exitCode == 0){
				response.setImage(Files.readAllBytes(new File("small_image.png").toPath()));
			}

		} catch (Exception e ) {
			e.printStackTrace();
		}
		return response;
	}

	public static BufferedImage decodeToImage(byte[] image) {
		BufferedImage bufferedImage = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(image);
			bufferedImage = ImageIO.read(bis);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bufferedImage;
	}
}
