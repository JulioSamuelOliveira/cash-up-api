package br.com.fiap.cash_up_api.model;

import java.math.BigDecimal;
 import java.time.LocalDate;
 
 import jakarta.persistence.Entity;
 import jakarta.persistence.EnumType;
 import jakarta.persistence.Enumerated;
 import jakarta.persistence.GeneratedValue;
 import jakarta.persistence.GenerationType;
 import jakarta.persistence.Id;
 import jakarta.persistence.ManyToOne;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotNull;
 import jakarta.validation.constraints.PastOrPresent;
 import jakarta.validation.constraints.Positive;
 import jakarta.validation.constraints.Size;
 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;

@Entity //faz com que seja salvo no banco de dados como uma tabela
@Data //Gera todos os gets,sets e construtores sem precisar poluir a classe
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Transaction {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //faz com que seja a chave primária || o próprio banco de dados gera o id
    private Long id;

    @NotBlank(message = "campo obrigatorio") //apenbas para strings
    @Size(min = 5, max = 255, message =  "deve ter pelo menos 5 caracteres") // tamanho da descrição
    private String description;

    @Positive(message = "deve ser maior que zero") //só aceita valores acima de zero
    private BigDecimal amount; //melhor recomendado para valores em money

    @PastOrPresent(message = "não pode ser no futuro")
    private LocalDate date;

    @NotNull(message = "campo obrigatório") //para qualquer objeto
    @Enumerated(EnumType.STRING) //indicar o tipo da transação pelo TransactionType.java
    private TransactionType type;

    @NotNull(message = "campo obrigatório")
    // M : 1
    @ManyToOne //Cardinalidade para organização do bd
    private Category category;

}
