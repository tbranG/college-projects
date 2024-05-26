package ia;

import java.util.List;

import dao.*;
import models.*;

public class RecomendadorEventos {

	/**
	 * Compara um evento com uma lista de outros e retorna um valor de semelhança entre eles. (0 a 1)
	 * @param target - evento a ser comparado
	 * @param eventosUsuario - lista de possibilidades para comparação
	 * @return - um valor {@code double} entre 0 e 1 conforme a similaridade.
	 * */
	public static double verificarSimilaridade(Evento target, List<Evento> eventosUsuario) {
		double maior = 0.0;
		for(int i = 0; i < eventosUsuario.size(); i++) {
			int hora = 0, dia = 0, dono = 0, privacidade = 0;
			//coparara o horario
			if(eventosUsuario.get(i).getHorario().equals(target.getHorario())) hora = 1;
			//compara a data
			if(eventosUsuario.get(i).getData().equals(target.getData())) dia = 1;
			//compara o dono
			if(eventosUsuario.get(i).getDono().equals(target.getDono())) dono = 1;
			//compara a privacidade
			if(eventosUsuario.get(i).getPrivacidade() == target.getPrivacidade()) privacidade = 1;
			
			//calcula a similaridade
			double simi = (double)(hora + dia + dono + privacidade)/4;
			//coleta o maior resultado
			if(simi >= maior) maior = simi;
		}
		return maior;
	}
	
	/**
	 * Verifica a similaridade dos eventos por meio dos participantes
	 * @param target - evento a ser comparado
	 * @param eventosUsuario - lista de possibilidades para comparação
	 * @return - um valor {@code double} entre 0 e 1 conforme a similaridade.
	 * */
	public static double verificarPessoas(Evento target, List<Evento> eventosUsuario) {
		double result = 0.0;
		
		ControleEvento contE = new ControleEvento();
		List<Usuario> targetPartic = contE.recuperarParticipantes(target.getId());
		for(int i = 0; i < eventosUsuario.size(); i++) {
			int numPartic = eventosUsuario.get(i).getQtdParticipantes();
			int matchPartic = 0;
			List<Usuario> partic = contE.recuperarParticipantes(eventosUsuario.get(i).getId());
			
			for(int j = 0; j < targetPartic.size(); j++) {
				for(int k = 0; k < partic.size(); k++) {
					if(targetPartic.get(j).getCpf().equals(partic.get(k).getCpf())) {
						matchPartic++;
					}
				}
			}
			
			double r = (double)matchPartic/numPartic;
			if(r >= result) result = r;
		}
		return result;
	}
}
