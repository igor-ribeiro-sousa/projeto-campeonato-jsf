package entidade.enuns;

public enum Resultado {

	VITORIA(0, "VITÓRIA"), 
	DERROTA(1,"DERROTA"),
	EMPATE(2, "EMPATE");

	   private Integer codigo;

	   private String descricao;

	   private Resultado(Integer codigo, String descricao)
	   {
	      this.codigo = codigo;
	      this.descricao = descricao;
	   }

	   public Integer getCodigo()
	   {
	      return codigo;
	   }

	   public String getDescricao()
	   {
	      return descricao;
	   }

	   public static Resultado toEnum(Integer cod)
	   {
	      if (cod == null)
	      {
	         return null;
	      }

	      for (Resultado x : Resultado.values())
	      {
	         if (cod.equals(x.getCodigo()))
	         {
	            return x;
	         }
	      }

	      throw new IllegalArgumentException("Jogo cancelado");
	   }
}
