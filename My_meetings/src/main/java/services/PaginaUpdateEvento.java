package services;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

import spark.Request;
import spark.Response;

import dao.*;
import models.*;

public class PaginaUpdateEvento {

	public static String createUpdateEvento(Request req, Response res) {
		String page = "";
		
		try {
			Scanner scan = new Scanner(new File("src/main/resources/public/atualizarEvento.html"));
			while(scan.hasNextLine()) {
				page += scan.nextLine() + "\n";
			}
			scan.close();
		}catch(FileNotFoundException err) {
			System.out.println(err.getMessage());
		}
		
		//exbindo informacoes
		EventoDAO eDao = new EventoDAO();
		Evento e = eDao.getEvento(Integer.parseInt(req.params("id")));
		
		page = page.replaceFirst("<NAME>", e.getNome());
		page = page.replaceFirst("<DATE>", e.getData());
		page = page.replaceFirst("<TIME>", e.getHorario());
		page = page.replaceFirst("<ADDRESS>", e.getEndereco());
		page = page.replaceFirst("<INFO>", e.getDescricao());
		page = page.replaceFirst("<MAXP>", String.valueOf(e.getMaxParticipantes()));
		
		page = page.replaceFirst("<URL>", "/sendEvento/" + e.getId());
		
		return page;
	}
}
