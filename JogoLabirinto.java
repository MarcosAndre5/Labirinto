package ...;

public class JogoLabirinto {	
	private static final char PAREDE_VERTICAL = '|';
	private static final char PAREDE_ORIZONTAL = '_';
	private static final char VAZIO = ' ';
	private static final int TAMANHO = 10;
	private static final char PAREDE = '@';
	private static final double PROBABILIDADE = 0.7;
	private static final char INICIO = 'I';
	private static final char DESTINO = 'D';
	private static final char CAMINHO = '.';
	private static final char SEM_SAIDA = 'x';
	
	private static char[][] tabuleiro;
	private static int linhaInicio;
	private static int colunaInicio;
	private static int linhaDestino;
	private static int colunaDestino;
	
	public static int gerarNumero(int minimo, int maximo) {
		int valor = (int) Math.round(Math.random()  * (maximo - minimo));
		return minimo + valor;
	}
	
	public static void iniciarMatriz() {
		int i, j;
		
		for (i = 0; i < TAMANHO; i++) {
			tabuleiro[i][0] = PAREDE_VERTICAL;
			tabuleiro[i][TAMANHO - 1] = PAREDE_VERTICAL;
			tabuleiro[0][i] = PAREDE_ORIZONTAL;
			tabuleiro[TAMANHO - 1][i] = PAREDE_ORIZONTAL;
		}
		
		for (i = 1; i < TAMANHO - 1; i++) {
			for (j = 1; j < TAMANHO - 1; j++) {
				if (Math.random() > PROBABILIDADE) {
					tabuleiro[i][j] = PAREDE;
				} else {
					tabuleiro[i][j] = VAZIO;
				}	
			}
		}
		linhaInicio = gerarNumero(1, TAMANHO / 2 - 1);
		colunaInicio = gerarNumero(1, TAMANHO / 2 - 1);
		tabuleiro[linhaInicio][colunaInicio] = INICIO;
		
		linhaDestino = gerarNumero(TAMANHO / 2, TAMANHO - 2);
		colunaDestino = gerarNumero(TAMANHO / 2, TAMANHO - 2);
		tabuleiro[linhaDestino][colunaDestino] = DESTINO;
	}
	
	public static void main(String[] args) {	
		tabuleiro = new char[TAMANHO][TAMANHO]; 
		iniciarMatriz();
		imprimirTabuleiro();
		
		System.out.println("\n- Procurando soluçăo -\n"); 
		boolean achou = procurarCaminho(linhaInicio, colunaInicio); 
		if (achou) { 
			System.out.println("\nACHOU O CAMINHO!"); 
		} else { 
			System.out.println("\nNĂO TEM CAMINHO!"); 
		}
	}
	
	public static boolean procurarCaminho(int linhaAtual, int colunaAtual) { 
		int proxLinha; 
		int proxColuna; 
		boolean achou = false; 
		
		proxLinha = linhaAtual - 1; 
		proxColuna = colunaAtual; 
		achou = tentarCaminho(proxLinha, proxColuna); 
		
		if (!achou) { 
			proxLinha = linhaAtual + 1; 
			proxColuna = colunaAtual; 
			achou = tentarCaminho(proxLinha, proxColuna); 
		} 
		 
		if (!achou) { 
			proxLinha = linhaAtual; 
			proxColuna = colunaAtual - 1; 
			achou = tentarCaminho(proxLinha, proxColuna); 
		} 
		 
		if (!achou) { 
			proxLinha = linhaAtual; 
			proxColuna = colunaAtual + 1; 
			achou = tentarCaminho(proxLinha, proxColuna); 
		} 
		return achou; 
	}
	
	private static boolean tentarCaminho(int proxLinha, int proxColuna) { 
		boolean achou = false; 
		if (tabuleiro[proxLinha][proxColuna] == DESTINO) { 
			achou = true; 
		} else if (posicaoVazia(proxLinha, proxColuna)) {  
			tabuleiro[proxLinha][proxColuna] = CAMINHO; 
			imprimirTabuleiro(); 
			achou = procurarCaminho(proxLinha, proxColuna); 
			if (!achou) { 
				tabuleiro[proxLinha][proxColuna] = SEM_SAIDA; 
				imprimirTabuleiro(); 
			} 
		} 
		return achou; 
	}
	
	public static boolean posicaoVazia(int linha, int coluna) { 
		boolean vazio = false; 
		if (linha >= 0 && coluna >= 0 && linha < TAMANHO && coluna < TAMANHO) { 
			vazio = (tabuleiro[linha][coluna] == VAZIO); 
		} 
		return vazio; 
	}
	
	public static void imprimirTabuleiro() {
		for (int i = 0; i < TAMANHO; i++) {
			for (int j = 0; j < TAMANHO; j++) {
				System.out.print(tabuleiro[i][j]);
			}
			System.out.println();
		}
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
