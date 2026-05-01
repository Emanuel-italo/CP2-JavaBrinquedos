package fiap.com.br.cp2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.math.BigDecimal;


@Entity
@Table(name = "TDS_TB_BRINQUEDO")
public class Brinquedo {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqBrinquedo")
    @SequenceGenerator(
            name           = "seqBrinquedo",
            sequenceName   = "SEQ_BRINQUEDOS",
            allocationSize = 1
    )
    @Column(name = "ID_BRINQUEDO")
    private Long id;

    @Column(name = "NM_BRINQUEDO", nullable = false, length = 100)
    private String nome;

    @Column(name = "TP_BRINQUEDO", nullable = false, length = 50)
    private String tipo;

    /** Classificação etária: 0–14 anos. */
    @Column(name = "NR_CLASSIFICACAO", nullable = false)
    private Integer classificacao;

    @Column(name = "DS_TAMANHO", nullable = false, length = 20)
    private String tamanho;

    @Column(name = "VL_PRECO", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;



    protected Brinquedo() {}


    public Brinquedo(Long id, String nome, String tipo,
                     Integer classificacao, String tamanho, BigDecimal preco) {
        this.id            = id;
        this.nome          = nome;
        this.tipo          = tipo;
        this.classificacao = classificacao;
        this.tamanho       = tamanho;
        this.preco         = preco;
    }


    public void atualizarCom(String nome, String tipo,
                             Integer classificacao, String tamanho, BigDecimal preco) {
        this.nome          = nome;
        this.tipo          = tipo;
        this.classificacao = classificacao;
        this.tamanho       = tamanho;
        this.preco         = preco;
    }


    public Long getId()             { return id; }
    public String getNome()         { return nome; }
    public String getTipo()         { return tipo; }
    public Integer getClassificacao() { return classificacao; }
    public String getTamanho()      { return tamanho; }
    public BigDecimal getPreco()    { return preco; }
}