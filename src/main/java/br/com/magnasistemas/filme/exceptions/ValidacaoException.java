package br.com.magnasistemas.filme.exceptions;

public class ValidacaoException extends RuntimeException{

	/**
	 * Exception criada para a validação da entrada de dados
	 */
	private static final long serialVersionUID = 1L;

	public ValidacaoException(String message) {
		super(message);
	}
	
	

}
