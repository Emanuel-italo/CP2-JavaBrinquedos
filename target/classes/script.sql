-- ============================================================
-- Script SQL - CP2: API REST Brinquedos Infantis
-- Disciplina: Java Advanced - FIAP 2026
-- Professor: Dr. Marcel Stefan Wagner
-- ============================================================

-- ------------------------------------------------------------
-- 1. Remove objetos anteriores (se existirem)
--    Execute com cuidado em ambiente de produção!
-- ------------------------------------------------------------
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE TDS_TB_BRINQUEDO CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_BRINQUEDOS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN RAISE; END IF;
END;
/

-- ------------------------------------------------------------
-- 2. Criação da SEQUENCE
--    Usada pelo Spring (GenerationType.SEQUENCE) para gerar
--    o ID de cada brinquedo automaticamente.
-- ------------------------------------------------------------
CREATE SEQUENCE SEQ_BRINQUEDOS
    START WITH 1        -- primeiro valor gerado
    INCREMENT BY 1      -- incremento a cada chamada
    NOCACHE             -- sem cache (ambiente de dev)
    NOCYCLE;            -- não reinicia ao atingir o limite

-- ------------------------------------------------------------
-- 3. Criação da TABELA
--    Mapeada pela entidade Brinquedo.java (@Table)
--    Colunas: Id, Nome, Tipo, Classificacao, Tamanho, Preco
-- ------------------------------------------------------------
CREATE TABLE TDS_TB_BRINQUEDO (

    -- PK gerada pela sequence SEQ_BRINQUEDOS
    ID_BRINQUEDO     NUMBER(10)     NOT NULL,

    -- Nome do brinquedo
    NM_BRINQUEDO     VARCHAR2(100)  NOT NULL,

    -- Tipo/categoria do brinquedo
    TP_BRINQUEDO     VARCHAR2(50)   NOT NULL,

    -- Classificação etária: 0-14 anos
    NR_CLASSIFICACAO NUMBER(2)      NOT NULL,

    -- Tamanho do brinquedo
    DS_TAMANHO       VARCHAR2(20)   NOT NULL,

    -- Preço com 2 casas decimais
    VL_PRECO         NUMBER(10, 2)  NOT NULL,

    -- Chave primária
    CONSTRAINT PK_BRINQUEDO PRIMARY KEY (ID_BRINQUEDO),

    -- Classificação deve estar entre 0 e 14 (regra de negócio)
    CONSTRAINT CK_CLASSIFICACAO CHECK (NR_CLASSIFICACAO BETWEEN 0 AND 14),

    -- Preço deve ser maior que zero
    CONSTRAINT CK_PRECO CHECK (VL_PRECO > 0)
);

-- ------------------------------------------------------------
-- 4. Comentários nas colunas (boa prática de documentação)
-- ------------------------------------------------------------
COMMENT ON TABLE  TDS_TB_BRINQUEDO                    IS 'Tabela de brinquedos infantis (0 a 14 anos)';
COMMENT ON COLUMN TDS_TB_BRINQUEDO.ID_BRINQUEDO       IS 'Identificador único do brinquedo (PK gerada por sequence)';
COMMENT ON COLUMN TDS_TB_BRINQUEDO.NM_BRINQUEDO       IS 'Nome do brinquedo';
COMMENT ON COLUMN TDS_TB_BRINQUEDO.TP_BRINQUEDO       IS 'Tipo/categoria do brinquedo (ex: Carrinho, Boneca)';
COMMENT ON COLUMN TDS_TB_BRINQUEDO.NR_CLASSIFICACAO   IS 'Classificação etária recomendada (0 a 14 anos)';
COMMENT ON COLUMN TDS_TB_BRINQUEDO.DS_TAMANHO         IS 'Tamanho do brinquedo (ex: Pequeno, Médio, Grande)';
COMMENT ON COLUMN TDS_TB_BRINQUEDO.VL_PRECO           IS 'Preço de venda do brinquedo em reais';

-- ------------------------------------------------------------
-- 5. Dados iniciais para teste
-- ------------------------------------------------------------
-- Jogos de Tabuleiro e Cartas
INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Banco Imobiliário', 'Jogo de Tabuleiro', 8, 'Médio', 135.50);

INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Jogo Uno', 'Jogo de Cartas', 7, 'Pequeno', 25.00);

INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Cara a Cara', 'Jogo de Tabuleiro', 6, 'Médio', 89.90);

-- Educativos e Montar
INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Cubo Mágico Profissional', 'Educativo', 6, 'Pequeno', 35.90);

INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Kit Massinha Play-Doh', 'Modelagem', 3, 'Médio', 65.00);

INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Quebra-Cabeça 500 peças', 'Educativo', 10, 'Médio', 45.00);

-- Bonecos e Pelúcias
INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Urso de Pelúcia Gigante', 'Pelúcia', 0, 'Grande', 180.00);

INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Boneco Max Steel', 'Boneco de Ação', 4, 'Médio', 75.90);

-- Diversos e Maiores
INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Pista Hot Wheels Tubarão', 'Pista de Carrinho', 5, 'Grande', 250.00);

INSERT INTO TDS_TB_BRINQUEDO (ID_BRINQUEDO, NM_BRINQUEDO, TP_BRINQUEDO, NR_CLASSIFICACAO, DS_TAMANHO, VL_PRECO)
VALUES (SEQ_BRINQUEDOS.NEXTVAL, 'Bicicleta Infantil Aro 16', 'Veículo', 5, 'Grande', 450.00);

COMMIT;

-- ------------------------------------------------------------
-- 6. Verificação
-- ------------------------------------------------------------
SELECT * FROM TDS_TB_BRINQUEDO ORDER BY ID_BRINQUEDO;
