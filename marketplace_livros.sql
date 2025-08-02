-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 31/07/2025 às 17:11
-- Versão do servidor: 9.1.0
-- Versão do PHP: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `marketplace_livros`
--

DELIMITER $$
--
-- Procedimentos
--
DROP PROCEDURE IF EXISTS `AtualizarStatusPedido`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AtualizarStatusPedido` (IN `p_pedido_id` INT, IN `p_status` VARCHAR(50))   BEGIN
    UPDATE pedidos
    SET status = p_status
    WHERE id_pedido = p_pedido_id;
END$$

DROP PROCEDURE IF EXISTS `ClientesQueMaisCompraram`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `ClientesQueMaisCompraram` (IN `p_vendedor_id` INT)   BEGIN
    SELECT c.id_cliente, c.nome, COUNT(DISTINCT p.id_pedido) AS total_pedidos,
           SUM(ip.quantidade) AS total_livros
    FROM pedidos p
    JOIN item_pedido ip ON ip.pedido_id = p.id_pedido
    JOIN livros_vendedor lv ON lv.livro_id = ip.livro_id
    JOIN cliente c ON c.id_cliente = p.cliente_id
    WHERE lv.vendedor_id = p_vendedor_id
    GROUP BY c.id_cliente, c.nome
    ORDER BY total_pedidos DESC;
END$$

DROP PROCEDURE IF EXISTS `PedidosPendentes`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PedidosPendentes` (IN `p_vendedor_id` INT)   BEGIN
    SELECT p.id_pedido, c.nome AS cliente, p.data_pedido, p.status
    FROM pedidos p
    JOIN item_pedido ip ON ip.pedido_id = p.id_pedido
    JOIN livros_vendedor lv ON lv.livro_id = ip.livro_id
    JOIN cliente c ON c.id_cliente = p.cliente_id
    WHERE lv.vendedor_id = p_vendedor_id
      AND LOWER(p.status) = 'pendente';
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura para tabela `autores`
--

DROP TABLE IF EXISTS `autores`;
CREATE TABLE IF NOT EXISTS `autores` (
  `id_autor` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `nacionalidade` varchar(255) DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `data_morte` date DEFAULT NULL,
  PRIMARY KEY (`id_autor`)
) ENGINE=MyISAM AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `autores`
--

INSERT INTO `autores` (`id_autor`, `nome`, `nacionalidade`, `data_nascimento`, `data_morte`) VALUES
(1, 'Machado de Assis', 'Brasileira', '1839-06-21', '1908-09-29'),
(2, 'Clarice Lispector', 'Brasileira', '1920-12-10', '1977-12-09'),
(3, 'Jorge Amado', 'Brasileira', '1912-08-10', '2001-08-06'),
(4, 'J.K. Rowling', 'Britânica', '1965-07-31', NULL),
(5, 'George Orwell', 'Britânica', '1903-06-25', '1950-01-21'),
(6, 'Agatha Christie', 'Britânica', '1890-09-15', '1976-01-12'),
(7, 'Gabriel García Márquez', 'Colombiana', '1927-03-06', '2014-04-17'),
(8, 'Stephen King', 'Americana', '1947-09-21', NULL),
(9, 'Toni Morrison', 'Americana', '1931-02-18', '2019-08-05'),
(10, 'Haruki Murakami', 'Japonesa', '1949-01-12', NULL),
(11, 'Lygia Fagundes Telles', 'Brasileira', '1923-04-19', '2022-04-03'),
(12, 'Paulo Coelho', 'Brasileira', '1947-08-24', NULL),
(13, 'Monteiro Lobato', 'Brasileira', '1882-04-18', '1948-07-04'),
(14, 'Rachel de Queiroz', 'Brasileira', '1910-11-17', '2003-11-04'),
(15, 'Carlos Drummond de Andrade', 'Brasileira', '1902-10-31', '1987-08-17'),
(16, 'William Shakespeare', 'Britânica', '1564-04-26', '1616-04-23'),
(17, 'Jane Austen', 'Britânica', '1775-12-16', '1817-07-18'),
(18, 'Fiódor Dostoiévski', 'Russa', '1821-11-11', '1881-02-09'),
(19, 'Franz Kafka', 'Tcheca', '1883-07-03', '1924-06-03'),
(20, 'Virginia Woolf', 'Britânica', '1882-01-25', '1941-03-28'),
(21, 'Ernest Hemingway', 'Americana', '1899-07-21', '1961-07-02'),
(22, 'Fernando Pessoa', 'Portuguesa', '1888-06-13', '1935-11-30'),
(23, 'José Saramago', 'Portuguesa', '1922-11-16', '2010-06-18'),
(24, 'Isabel Allende', 'Chilena', '1942-08-02', NULL),
(25, 'Mario Vargas Llosa', 'Peruana', '1936-03-28', NULL),
(26, 'Octavia Butler', 'Americana', '1947-06-22', '2006-02-24'),
(27, 'Neil Gaiman', 'Britânica', '1960-11-10', NULL),
(28, 'Chimamanda Ngozi Adichie', 'Nigeriana', '1977-09-15', NULL),
(29, 'Yuval Noah Harari', 'Israelense', '1976-02-24', NULL),
(30, 'Margaret Atwood', 'Canadense', '1939-11-18', NULL),
(31, 'Homero', 'Grega', '0800-01-01', '0701-01-01'),
(32, 'Dante Alighieri', 'Italiana', '1265-01-01', '1321-09-14'),
(33, 'Miguel de Cervantes', 'Espanhola', '1547-09-29', '1616-04-22'),
(34, 'Victor Hugo', 'Francesa', '1802-02-26', '1885-05-22'),
(35, 'Liev Tolstói', 'Russa', '1828-09-09', '1910-11-20'),
(36, 'Charles Dickens', 'Britânica', '1812-02-07', '1870-06-09'),
(37, 'Edgar Allan Poe', 'Americana', '1809-01-19', '1849-10-07'),
(38, 'Mark Twain', 'Americana', '1835-11-30', '1910-04-21'),
(39, 'Oscar Wilde', 'Irlandesa', '1854-10-16', '1900-11-30'),
(40, 'Júlio Verne', 'Francesa', '1828-02-08', '1905-03-24'),
(41, 'Graciliano Ramos', 'Brasileira', '1892-10-27', '1953-03-20'),
(42, 'Érico Veríssimo', 'Brasileira', '1905-12-17', '1975-11-28'),
(43, 'Joaquim Manuel de Macedo', 'Brasileira', '1820-06-24', '1882-04-11'),
(44, 'Aluísio Azevedo', 'Brasileira', '1857-04-14', '1913-01-21'),
(45, 'Cecília Meireles', 'Brasileira', '1901-11-07', '1964-11-09'),
(46, 'Rubem Fonseca', 'Brasileira', '1925-05-11', '2020-04-15'),
(47, 'Lima Barreto', 'Brasileira', '1881-05-13', '1922-11-01'),
(48, 'Adélia Prado', 'Brasileira', '1935-12-13', NULL),
(49, 'Milton Hatoum', 'Brasileira', '1952-08-19', NULL),
(50, 'Ana Maria Machado', 'Brasileira', '1941-12-24', NULL),
(51, 'Isaac Asimov', 'Americana', '1920-01-02', '1992-04-06'),
(52, 'Philip K. Dick', 'Americana', '1928-12-16', '1982-03-02'),
(53, 'Ursula K. Le Guin', 'Americana', '1929-10-21', '2018-01-22'),
(54, 'Terry Pratchett', 'Britânica', '1948-04-28', '2015-03-12'),
(55, 'Brandon Sanderson', 'Americana', '1975-12-19', NULL),
(56, 'Patrick Rothfuss', 'Americana', '1973-06-06', NULL),
(57, 'Sally Rooney', 'Irlandesa', '1991-02-20', NULL),
(58, 'Hanya Yanagihara', 'Americana', '1974-09-20', NULL),
(59, 'Ocean Vuong', 'Vietnamita', '1988-10-14', NULL),
(60, 'Rupi Kaur', 'Canadense', '1992-10-04', NULL),
(61, 'Jhumpa Lahiri', 'Americana', '1967-07-11', NULL),
(62, 'Michel Foucault', 'Francesa', '1926-10-15', '1984-06-25'),
(63, 'Noam Chomsky', 'Americana', '1928-12-07', NULL),
(64, 'Malcolm Gladwell', 'Canadense', '1963-09-03', NULL),
(65, 'Brené Brown', 'Americana', '1965-11-18', NULL),
(66, 'Nassim Nicholas Taleb', 'Libanesa', '1960-01-01', NULL),
(67, 'Arthur C. Clarke', 'Britânica', '1917-12-16', '2008-03-19'),
(68, 'N.K. Jemisin', 'Americana', '1972-09-19', NULL),
(69, 'Liu Cixin', 'Chinesa', '1963-06-23', NULL),
(70, 'Ann Leckie', 'Americana', '1966-03-02', NULL),
(71, 'Emily Dickinson', 'Americana', '1830-12-10', '1886-05-15'),
(72, 'Maya Angelou', 'Americana', '1928-04-04', '2014-05-28'),
(73, 'Charles Bukowski', 'Americana', '1920-08-16', '1994-03-09'),
(74, 'Wisława Szymborska', 'Polonesa', '1923-07-02', '2012-02-01'),
(75, 'Raymond Chandler', 'Americana', '1888-07-23', '1959-03-26'),
(76, 'Patricia Highsmith', 'Americana', '1921-01-19', '1995-02-04'),
(77, 'Stieg Larsson', 'Sueca', '1954-08-15', '2004-11-09'),
(78, 'Gillian Flynn', 'Americana', '1971-02-24', NULL),
(79, 'Tana French', 'Irlandesa', '1973-05-10', NULL),
(80, 'Hilary Mantel', 'Britânica', '1952-07-06', '2022-09-22'),
(81, 'Ken Follett', 'Britânica', '1949-06-05', NULL),
(82, 'Umberto Eco', 'Italiana', '1932-01-05', '2016-02-19'),
(83, 'Marguerite Yourcenar', 'Belga', '1903-06-08', '1987-12-17'),
(84, 'Gore Vidal', 'Americana', '1925-10-03', '2012-07-31'),
(85, 'Rebecca Ross', 'americana', '1980-06-05', NULL),
(86, 'Jane Austen', 'britanica', '2005-09-10', NULL),
(87, 'Phaola', 'brasileira', '2005-05-10', NULL),
(90, 'Pedro Alvares', 'americano', '1990-08-09', NULL),
(91, 'Bianca Mores', 'brasileira', '1989-01-01', NULL),
(92, 'Paula Moraes', 'brasileira', '2005-08-10', NULL),
(95, 'Fiódor Dostoiévski', 'brasileiro', '1980-04-10', NULL),
(98, 'Jorge Amado', 'brasileiro', '2003-10-10', NULL),
(101, 'Banana', 'brasil', '2005-05-10', NULL),
(102, 'Lua', 'brasileiro', NULL, NULL),
(103, 'Alexandre Dumas', 'Francês', '1802-07-24', '1870-12-05'),
(104, 'Antoine de Saint-Exupéry', 'Francesa', '1900-06-29', '1944-07-31'),
(105, 'Antoine de Saint-Exupéry', 'Francesa', '1900-06-29', '1944-07-31'),
(106, 'Carla Madeira', 'Brasileira', '1964-10-18', NULL),
(107, 'Ilko Minev', 'Brasileiro (naturalizado)', '1946-01-01', NULL),
(108, 'Itamar Vieira Júnior', 'Brasileiro', '1979-08-06', NULL),
(109, 'Jeferson Tenório', 'Brasileiro', NULL, NULL),
(110, 'Milena Martins Moura', 'Brasileira', '1986-10-02', NULL),
(111, 'Liara Oliveira', 'Brasileira', '1994-07-02', NULL),
(112, 'Tatiana Salem Levy', 'Brasileira', '1979-01-24', NULL),
(113, 'Antoine de Saint-Exupéry', 'Francês', '1900-06-29', '1944-07-31'),
(114, 'Anne Frank', 'Alemã', '1929-06-12', '1945-02-01');

-- --------------------------------------------------------

--
-- Estrutura para tabela `carrinho`
--

DROP TABLE IF EXISTS `carrinho`;
CREATE TABLE IF NOT EXISTS `carrinho` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `data_criacao` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `data_atualizacao` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FKd2amlxdq5n5mho547c1338tm6` (`cliente_id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `carrinho`
--

INSERT INTO `carrinho` (`id`, `cliente_id`, `status`, `data_criacao`, `data_atualizacao`) VALUES
(1, 51, 'FINALIZADO', '2025-07-28 20:24:59', '2025-07-28 20:24:59'),
(2, 52, 'FINALIZADO', '2025-07-28 20:26:18', '2025-07-28 20:26:18'),
(3, 53, 'FINALIZADO', '2025-07-29 14:03:38', '2025-07-29 14:03:38'),
(4, 51, 'FINALIZADO', '2025-07-29 14:05:53', '2025-07-29 14:05:53'),
(5, 53, 'FINALIZADO', '2025-07-29 14:10:41', '2025-07-29 14:10:41'),
(6, 51, 'FINALIZADO', '2025-07-29 16:58:00', '2025-07-29 16:58:00'),
(7, 53, 'FINALIZADO', '2025-07-29 17:00:14', '2025-07-29 17:00:14'),
(8, 53, 'FINALIZADO', '2025-07-30 01:13:00', '2025-07-30 01:13:00'),
(9, 53, 'FINALIZADO', '2025-07-30 01:39:20', '2025-07-30 01:39:20'),
(10, 54, 'ATIVO', '2025-07-30 02:24:43', '2025-07-30 02:24:43'),
(11, 55, 'FINALIZADO', '2025-07-30 02:37:11', '2025-07-30 02:37:11'),
(12, 53, 'FINALIZADO', '2025-07-30 15:19:03', '2025-07-30 15:19:03'),
(13, 53, 'FINALIZADO', '2025-07-30 23:09:21', '2025-07-30 23:09:21'),
(14, 53, 'FINALIZADO', '2025-07-30 23:09:43', '2025-07-30 23:09:43'),
(15, 53, 'ATIVO', '2025-07-31 02:38:34', '2025-07-31 02:38:34');

-- --------------------------------------------------------

--
-- Estrutura para tabela `categorias`
--

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE IF NOT EXISTS `categorias` (
  `id_categoria` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `categorias`
--

INSERT INTO `categorias` (`id_categoria`, `nome`) VALUES
(1, 'Literatura Brasileira'),
(2, 'Ficção Científica'),
(3, 'Fantasia'),
(4, 'Distopia'),
(5, 'Mistério/Thriller'),
(6, 'Realismo Mágico'),
(7, 'Terror'),
(8, 'Literatura Clássica'),
(9, 'Romance'),
(10, 'Poesia'),
(11, 'Drama'),
(12, 'Ficção Histórica'),
(13, 'Literatura Infantojuvenil'),
(14, 'Autoajuda'),
(15, 'Filosofia'),
(16, 'Ensaios'),
(17, 'Literatura Contemporânea'),
(18, 'Literatura Africana'),
(19, 'Literatura Asiática'),
(20, 'Literatura Feminista'),
(22, 'Literatura Policial'),
(23, 'Literatura de Viagem'),
(24, 'Biografia'),
(25, 'Literatura Experimental');

-- --------------------------------------------------------

--
-- Estrutura para tabela `categoria_livros`
--

DROP TABLE IF EXISTS `categoria_livros`;
CREATE TABLE IF NOT EXISTS `categoria_livros` (
  `id_categoria_livro` int NOT NULL AUTO_INCREMENT,
  `livro_id` int NOT NULL,
  `categoria_id` int NOT NULL,
  PRIMARY KEY (`id_categoria_livro`),
  KEY `FK5rus6smng3wep7g6n4nky6x48` (`categoria_id`),
  KEY `FKtdl031qxibjs7ju1xy8hpb7i3` (`livro_id`)
) ENGINE=MyISAM AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `categoria_livros`
--

INSERT INTO `categoria_livros` (`id_categoria_livro`, `livro_id`, `categoria_id`) VALUES
(27, 27, 3),
(18, 13, 3),
(17, 22, 2),
(6, 11, 3),
(7, 12, 8),
(10, 14, 4),
(12, 16, 7),
(13, 17, 2),
(14, 18, 2),
(15, 19, 2),
(16, 20, 2),
(26, 27, 9),
(23, 25, 5),
(34, 34, 9),
(32, 32, 11),
(33, 33, 9),
(35, 34, 3),
(36, 35, 3),
(37, 35, 9),
(38, 36, 3),
(39, 36, 9),
(40, 37, 9),
(41, 1, 9),
(42, 2, 11),
(43, 3, 9),
(44, 3, 3),
(45, 4, 9),
(46, 4, 3),
(47, 5, 9),
(48, 6, 9),
(49, 7, 11),
(50, 8, 5),
(51, 9, 9),
(52, 10, 16),
(53, 11, 9),
(54, 12, 9),
(55, 13, 9),
(56, 14, 2),
(57, 15, 13),
(58, 16, 24),
(59, 19, 1),
(60, 19, 9),
(61, 20, 1),
(62, 20, 9),
(63, 21, 1),
(64, 21, 4),
(65, 23, 13),
(66, 24, 9),
(67, 26, 15),
(68, 28, 7),
(69, 29, 9),
(70, 30, 15),
(71, 31, 5),
(72, 32, 13),
(73, 33, 5),
(74, 34, 9),
(75, 34, 5),
(76, 35, 14);

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `email` varchar(40) NOT NULL,
  `data_nascimento` date NOT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `telefone_principal` varchar(255) DEFAULT NULL,
  `data_cadastro` date NOT NULL,
  `endereco_id` int DEFAULT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `unique_email` (`email`),
  KEY `FKjhpvunum71feup73xpuri6yxh` (`endereco_id`)
) ENGINE=MyISAM AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `nome`, `email`, `data_nascimento`, `cpf`, `telefone_principal`, `data_cadastro`, `endereco_id`, `senha`) VALUES

(51, 'Paula Moraes', 'paula@moraes', '2010-09-10', '07598723456', '89999722341', '2025-07-26', 223, '$2a$10$BEf7./vAOjZOj3j0oIZ6u.8e3m8LBDj3EHn28fsC9P4bwvUnBKENy'),
(52, 'Priscilla', 'priscilla@gmail', '2010-09-10', '09876534213', '89999875645', '2025-07-28', 224, '$2a$10$RaJgqzH/tXRIk88NwsvzHu0nFxuCH4v.JDusUe34qb4a5HtzGg4qG'),
(53, 'Bella ', 'bella@gmail.com', '2004-08-10', '09756798712', '89765432189', '2025-07-28', 225, '$2a$10$gKLQ3VNmyFfKaIpxjlXwDuK4G/j9AJSmNYUzKVYcKrSlzT5Lx4uzK'),
(54, 'Phaola', 'phaola@gmail', '2003-04-11', '05698723454', '89999678906', '2025-07-28', NULL, '$2a$10$ATLoo4LRwhRuih3C.QPIEeI9EB7kAGow77RFan2R0Z8zgDNZWyVxS'),
(55, 'Amanda', 'amanda@gmail', '2007-07-22', '09867856743', '899998765432', '2025-07-29', NULL, '$2a$10$OgTkTbn7Y5dVlG3egWEKLeaqTYatvAX.u7caqKbDL6jstb77Zobh6'),
(56, 'Ingrid', 'ingrid@gmail', '2004-05-09', '09867856432', '8999967875', '2025-07-29', NULL, '$2a$10$2EtHmZktygGvBfu.uDQpf.k7JiE1c8/uJdGlaWpx0xwlqmh/hzYYu');

-- --------------------------------------------------------

--
-- Estrutura para tabela `editora`
--

DROP TABLE IF EXISTS `editora`;
CREATE TABLE IF NOT EXISTS `editora` (
  `id_editora` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_editora`)
) ENGINE=MyISAM AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `editora`
--

INSERT INTO `editora` (`id_editora`, `nome`, `telefone`, `email`) VALUES
(1, 'Companhia das Letras', '(11) 3707-3500', 'contato@companhiadasletras.com.br'),
(2, 'Editora Record', '(21) 2585-2000', 'sac@record.com.br'),
(3, 'Editora Rocco', '(21) 3525-2000', 'rocco@rocco.com.br'),
(4, 'Editora Planeta', '(11) 3087-8888', 'planeta@editoraplaneta.com.br'),
(5, 'Editora Intrínseca', '(21) 3206-7400', 'contato@intrinseca.com.br'),
(6, 'Editora Sextante', '(21) 2219-7011', 'atendimento@sextante.com.br'),
(7, 'Editora Leya', '(11) 3068-9866', 'contato@leya.com.br'),
(8, 'Editora Nova Fronteira', '(21) 3882-8200', 'contato@novafronteira.com.br'),
(9, 'Editora Globo Livros', '(21) 2535-9200', 'atendimento@globolivros.globo.com'),
(10, 'Editora Arqueiro', '(21) 2585-2000', 'arqueiro@record.com.br'),
(11, 'Editora Valentina', '(21) 3206-7400', 'contato@editoravalentina.com.br'),
(12, 'Editora HarperCollins Brasil', '(11) 3068-9866', 'sac@harpercollins.com.br'),
(13, 'Editora Paralela', '(11) 3707-3500', 'paralela@companhiadasletras.com.br'),
(14, 'Editora Objetiva', '(21) 2585-2000', 'objetiva@record.com.br'),
(15, 'Editora BestSeller', '(21) 2585-2000', 'bestseller@record.com.br'),
(16, 'Editora Zahar', '(21) 3970-9500', 'contato@zahar.com.br'),
(17, 'Editora L&PM', '(51) 3225-5777', 'contato@lpm.com.br'),
(18, 'Editora Martin Claret', '(11) 3672-7070', 'contato@martinclaret.com.br'),
(19, 'Editora Autêntica', '(31) 3222-6819', 'autentica@autenticaeditora.com.br'),
(20, 'Editora 34', '(11) 3816-6777', 'editora34@editora34.com.br'),
(21, 'Editora Todavia', '(11) 3707-3500', 'contato@todavia.com.br'),
(22, 'Editora Fósforo', '(11) 3816-6777', 'contato@editorafosforo.com.br'),
(23, 'Editora Dublinense', '(51) 3028-8188', 'contato@dublinense.com.br'),
(24, 'Editora Nemo', '(19) 3252-4501', 'contato@editoranemo.com.br'),
(25, 'Editora Moinhos', '(11) 3816-6777', 'contato@editoramoinhos.com.br'),
(26, 'Editora Carambaia', '(11) 3707-3500', 'contato@editoracarambaia.com.br'),
(27, 'Editora Ubu', '(11) 3816-6777', 'contato@editoraubu.com.br'),
(28, 'Editora Âyiné', '(71) 3235-5400', 'contato@ayine.com.br'),
(29, 'Editora Iluminuras', '(11) 3031-6161', 'iluminuras@iluminuras.com.br'),
(30, 'Editora Patuá', '(11) 98351-3547', 'contato@editorapatua.com.br'),
(31, 'Editora Nós', '(11) 3816-6777', 'contato@editoranos.com.br'),
(32, 'Editora Urutau', '(21) 99784-5123', 'contato@editoraurutau.com.br'),
(33, 'Editora Kotter', '(11) 94738-1923', 'contato@kotter.com.br'),
(34, 'Editora Malê', '(21) 3970-9500', 'contato@editoramale.com.br'),
(35, 'Editora Penalux', '(12) 98214-5678', 'contato@editorapenalux.com.br'),
(36, 'Editora Contexto', '(11) 3832-5832', 'contexto@editoracontexto.com.br'),
(37, 'Editora Unesp', '(11) 3242-7171', 'editora@unesp.br'),
(38, 'Editora Fiocruz', '(21) 3882-9030', 'editora@fiocruz.br'),
(39, 'Editora Senac', '(11) 2187-4400', 'editorasenacsp@sp.senac.br'),
(40, 'Editora Saraiva', '(11) 3613-3000', 'atendimento@saraiva.com.br'),
(41, 'Editora Melhoramentos', '(11) 3874-0800', 'melhoramentos@melhoramentos.com.br'),
(42, 'Editora Ática', '(11) 3990-2100', 'atendimento@atica.com.br'),
(43, 'Editora Scipione', '(11) 2790-1500', 'scipione@scipione.com.br'),
(44, 'Editora FTD', '(11) 3224-3000', 'atendimento@ftd.com.br'),
(45, 'Editora Moderna', '(11) 2790-1300', 'atendimento@moderna.com.br'),
(46, 'Editora Positivo', '(41) 3335-3500', 'editora@positivo.com.br'),
(47, 'Editora do Brasil', '(11) 3226-2000', 'contato@editoradobrasil.com.br'),
(48, 'Editora Salamandra', '(11) 2790-1300', 'salamandra@moderna.com.br'),
(49, 'Editora Formato', '(31) 3222-6819', 'formato@editoraformato.com.br'),
(50, 'Editora Rideel', '(11) 3660-9900', 'rideel@rideel.com.br'),
(51, 'Editora Quelônio', '(31) 99123-4567', 'contato@editoraquelonio.com.br'),
(52, 'Editora Cousa', '(51) 99123-4567', 'contato@editoracousa.com.br'),
(53, 'Editora Reformatório', '(81) 99123-4567', 'contato@reformatorio.com.br'),
(54, 'Editora Jabuticaba', '(31) 99123-4567', 'contato@jabuticaba.com.br'),
(55, 'Editora Lamparina', '(21) 99123-4567', 'contato@lamparina.com.br'),
(56, 'Editora Oficina Raquel', '(11) 99123-4567', 'contato@oficinaraquel.com.br'),
(57, 'Editora Monstro dos Mares', '(48) 99123-4567', 'contato@monstrodosmares.com.br'),
(58, 'Editora Realejo', '(51) 99123-4567', 'contato@realejo.com.br'),
(59, 'Editora Oito e Meio', '(21) 99123-4567', 'contato@oitoemeio.com.br'),
(60, 'Editora Luna Parque', '(11) 99123-4567', 'contato@lunaparquedelivros.com.br'),
(61, 'Editora Grimm', '(51) 99123-4567', 'contato@editoragrimm.com.br'),
(62, 'Editora Livros do Mal', '(11) 99123-4567', 'contato@livrosdomal.com.br'),
(63, 'Editora A Bolha', '(11) 99123-4567', 'contato@abolha.com.br'),
(64, 'Editora Macondo', '(31) 99123-4567', 'contato@editoramacondo.com.br'),
(65, 'Editora Caligo', '(11) 99123-4567', 'contato@editoracaligo.com.br'),
(66, 'Editora Urso', '(51) 99123-4567', 'contato@editoraurso.com.br'),
(67, 'Editora Ayllu', '(11) 99123-4567', 'contato@ayllu.com.br'),
(68, 'Editora Javali', '(21) 99123-4567', 'contato@editorajavali.com.br'),
(69, 'Editora Piseagrama', '(31) 99123-4567', 'contato@piseagrama.com.br'),
(70, 'Editora Medusa', '(11) 99123-4567', 'contato@editoramedusa.com.br'),
(71, 'Penguin Random House', '+1 212-782-9000', 'contact@penguinrandomhouse.com'),
(72, 'HarperCollins', '+1 212-207-7000', 'harpercollins@harpercollins.com'),
(73, 'Simon & Schuster', '+1 212-698-7000', 'customerservice@simonandschuster.com'),
(74, 'Hachette Livre', '+33 1 43 92 30 00', 'contact@hachette-livre.fr'),
(75, 'Macmillan Publishers', '+44 20 7014 6000', 'information@macmillan.com'),
(76, 'Oxford University Press', '+44 1865 556767', 'enquiry@oup.com'),
(77, 'Cambridge University Press', '+44 1223 358331', 'information@cambridge.org'),
(78, 'Pearson Education', '+44 1279 623623', 'customer.service@pearson.com'),
(79, 'Scholastic Corporation', '+1 212-343-6100', 'info@scholastic.com'),
(80, 'Bloomsbury Publishing', '+44 20 7494 2111', 'contact@bloomsbury.com'),
(81, 'Gallimard', '+33 1 49 54 42 00', 'contact@gallimard.fr'),
(82, 'Einaudi', '+39 011 56561', 'info@einaudi.it'),
(83, 'Suhrkamp Verlag', '+49 69 7560110', 'info@suhrkamp.de'),
(84, 'Editorial Anagrama', '+34 93 241 90 00', 'anagrama@anagrama-ed.es'),
(85, 'Editorial Planeta', '+34 93 492 80 00', 'info@planeta.es'),
(86, 'Alfaguara', '+34 91 423 03 00', 'alfaguara@santillana.es'),
(87, 'De Boeck', '+32 2 736 24 06', 'info@deboeck.com'),
(88, 'Actes Sud', '+33 4 90 49 86 91', 'info@actes-sud.fr'),
(89, 'Editorial Tusquets', '+34 93 253 04 00', 'tusquets@tusquets-editores.com'),
(90, 'Editorial Seix Barral', '+34 93 492 80 00', 'seixbarral@planeta.es'),
(91, 'Elsevier', '+31 20 485 3911', 'nlinfo-f@elsevier.com'),
(92, 'Springer Nature', '+49 6221 4870', 'service@springernature.com'),
(93, 'Wiley', '+1 201-748-6000', 'customer@wiley.com'),
(94, 'De Gruyter', '+49 30 260 05 0', 'service@degruyter.com'),
(95, 'Taylor & Francis', '+44 20 7017 6000', 'enquiries@taylorandfrancis.com'),
(96, 'SAGE Publishing', '+1 805-499-0721', 'info@sagepub.com'),
(97, 'MIT Press', '+1 617-253-5646', 'mitpress-orders@mit.edu'),
(98, 'University of Chicago Press', '+1 773-702-7700', 'marketing@press.uchicago.edu'),
(99, 'Harvard University Press', '+1 617-495-2600', 'contact@harvard.edu'),
(100, 'Princeton University Press', '+1 609-258-4900', 'press@princeton.edu');

-- --------------------------------------------------------

--
-- Estrutura para tabela `enderecos`
--

DROP TABLE IF EXISTS `enderecos`;
CREATE TABLE IF NOT EXISTS `enderecos` (
  `id_endereco` int NOT NULL AUTO_INCREMENT,
  `rua` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_endereco`)
) ENGINE=MyISAM AUTO_INCREMENT=226 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Estrutura para tabela `item_carrinho`
--

DROP TABLE IF EXISTS `item_carrinho`;
CREATE TABLE IF NOT EXISTS `item_carrinho` (
  `id` int NOT NULL AUTO_INCREMENT,
  `carrinho_id` int NOT NULL,
  `livro_id` int NOT NULL,
  `quantidade` int NOT NULL DEFAULT '1',
  `preco_unitario` decimal(38,2) DEFAULT NULL,
  `vendedor_livro_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr3dusq21jhlttwc4hanxhlbua` (`carrinho_id`),
  KEY `FKpg3qgvledlwy38v71a9ftbk2` (`livro_id`),
  KEY `FKg6pdefnqk4f1uln0husn8vxdo` (`vendedor_livro_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `item_pedido`
--

DROP TABLE IF EXISTS `item_pedido`;
CREATE TABLE IF NOT EXISTS `item_pedido` (
  `id_item_pedido` int NOT NULL AUTO_INCREMENT,
  `preco_unitario` decimal(10,2) NOT NULL,
  `quantidade` int NOT NULL,
  `livro_id` int DEFAULT NULL,
  `pedido_id` int DEFAULT NULL,
  PRIMARY KEY (`id_item_pedido`),
  KEY `FK71muohj2gwwsvf8tcxm3ufde` (`livro_id`),
  KEY `FKq6cx2t1dh4ikg93nvlpumswxx` (`pedido_id`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `item_pedido`
--

INSERT INTO `item_pedido` (`id_item_pedido`, `preco_unitario`, `quantidade`, `livro_id`, `pedido_id`) VALUES
(1, 45.80, 1, 34, 5),
(2, 120.90, 1, 1, 4),
(3, 45.90, 1, 2, 4),
(4, 45.90, 1, 2, 5),
(5, 120.90, 1, 1, 5),
(6, 120.90, 1, 1, 6),
(7, 23.90, 1, 3, 6),
(8, 30.40, 1, 5, 6),
(9, 120.90, 1, 1, 7),
(10, 23.90, 1, 3, 7),
(11, 45.90, 1, 2, 8),
(12, 23.90, 1, 3, 8),
(13, 120.90, 1, 1, 8),
(14, 45.90, 1, 2, 9),
(15, 23.90, 1, 3, 9),
(16, 120.90, 1, 1, 13),
(17, 23.90, 1, 3, 13),
(18, 45.90, 1, 2, 13),
(19, 45.90, 1, 2, 14),
(20, 67.90, 1, 4, 14),
(21, 45.90, 1, 2, 15),
(22, 12.00, 2, 11, 16),
(23, 67.90, 1, 4, 17),
(24, 30.40, 1, 5, 17),
(25, 30.40, 2, 5, 18),
(26, 23.90, 1, 6, 18),
(27, 30.40, 2, 5, 19),
(28, 23.90, 1, 6, 19),
(29, 45.90, 1, 2, 19),
(30, 67.90, 1, 4, 19),
(31, 120.90, 1, 1, 19),
(32, 22.80, 1, 23, 20),
(33, 45.90, 2, 2, 21);

-- --------------------------------------------------------

--
-- Estrutura para tabela `livros`
--

DROP TABLE IF EXISTS `livros`;
CREATE TABLE IF NOT EXISTS `livros` (
  `id_livro` int NOT NULL AUTO_INCREMENT,
  `ano_publicacao` int NOT NULL,
  `isbn` varchar(255) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `editora_id` int DEFAULT NULL,
  `img` longtext,
  PRIMARY KEY (`id_livro`),
  KEY `FK4y8fvvlggbkkctmerqor44h4w` (`editora_id`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Estrutura para tabela `livros_vendedor`
--

DROP TABLE IF EXISTS `livros_vendedor`;
CREATE TABLE IF NOT EXISTS `livros_vendedor` (
  `id_livros_vendedor` int NOT NULL AUTO_INCREMENT,
  `idioma` varchar(255) NOT NULL,
  `formato` varchar(255) NOT NULL,
  `preco` decimal(10,2) NOT NULL,
  `condicao` varchar(255) NOT NULL,
  `vendedor_id` int NOT NULL,
  `livro_id` int NOT NULL,
  PRIMARY KEY (`id_livros_vendedor`),
  UNIQUE KEY `uq_livro_vendedor` (`livro_id`,`vendedor_id`),
  KEY `FK5ycketgyqdoyd5k59y9fi28ci` (`vendedor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `livros_vendedor`
--

INSERT INTO `livros_vendedor` (`id_livros_vendedor`, `idioma`, `formato`, `preco`, `condicao`, `vendedor_id`, `livro_id`) VALUES
(1, 'Português', 'Físico', 120.90, 'Novo', 1, 1),
(2, 'Português', 'Físico ', 45.90, 'Novo', 1, 2),
(3, 'Inglês', 'Digital', 23.90, 'Novo', 2, 3),
(4, 'Português', 'Físico', 67.90, 'Novo', 2, 4),
(5, 'Inglês', 'Físico', 30.40, 'Usado', 2, 5),
(6, 'Português', 'fisico', 23.90, 'Novo', 2, 6),
(7, 'Inglês', 'Digital', 25.60, 'Novo', 1, 7),
(8, 'Português', 'Físico', 56.70, 'Novo', 1, 8),
(9, 'Inglês', 'Digital', 45.34, 'Novo', 1, 9),
(10, 'Português', 'Físico ', 80.90, 'Novo', 1, 10),
(11, 'Inglês', 'Físico', 12.00, 'usado', 1, 11),
(12, 'Português', 'Físico', 130.80, 'Novo', 3, 12),
(13, 'Espanhol', 'Ebook', 27.80, 'Novo', 4, 13),
(14, 'Português', 'Físico ', 21.50, 'Novo', 4, 14),
(15, 'Português', 'Físico ', 25.80, 'Novo', 4, 15),
(16, 'Português', 'Físico', 10.80, 'Usado', 4, 16),
(17, 'Português', 'Ebook', 11.09, 'Novo', 5, 19),
(18, 'Português', 'Físico ', 34.70, 'Novo', 5, 20),
(19, 'Português', 'Físico', 23.90, 'Novo', 5, 21),
(20, 'Português', 'Físico', 22.80, 'usado', 1, 23),
(21, 'Inglês', 'Físico', 120.00, 'Novo', 1, 24),
(22, 'Português', 'Físico ', 203.70, 'Novo', 3, 26),
(23, 'Português', 'Físico', 150.90, 'Novo', 3, 28),
(24, 'Português', 'Físico ', 56.90, 'Novo', 3, 29),
(25, 'Inglês', 'Físico', 59.70, 'Novo', 3, 30),
(26, 'Português', 'Físico ', 45.00, 'Novo', 3, 31),
(27, 'Português', 'Físico', 34.90, 'Novo', 3, 32),
(28, 'Português', 'Ebook', 22.03, 'Novo', 3, 33),
(29, 'Português', 'Físico', 45.21, 'Novo', 3, 34),
(30, 'Português', 'Digital', 78.56, 'Novo', 3, 35);

-- --------------------------------------------------------

--
-- Estrutura para tabela `livro_autor`
--

DROP TABLE IF EXISTS `livro_autor`;
CREATE TABLE IF NOT EXISTS `livro_autor` (
  `livro_id` int NOT NULL,
  `autor_id` int NOT NULL,
  PRIMARY KEY (`livro_id`,`autor_id`),
  KEY `FKbp6mk2jusxg3njryvs4jbfv90` (`autor_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `livro_autor`
--

INSERT INTO `livro_autor` (`livro_id`, `autor_id`) VALUES
(1, 18),
(2, 18),
(3, 20),
(4, 10),
(5, 10),
(6, 6),
(7, 18),
(8, 70),
(9, 11),
(10, 2),
(10, 45),
(11, 5),
(11, 87),
(12, 2),
(12, 103),
(13, 2),
(13, 24),
(14, 5),
(14, 91),
(15, 105),
(16, 2),
(16, 114),
(17, 1),
(18, 1),
(19, 1),
(19, 3),
(20, 1),
(20, 3),
(21, 1),
(22, 2),
(23, 106),
(24, 11),
(25, 11),
(26, 10),
(27, 11),
(28, 36),
(29, 11),
(30, 49),
(31, 14),
(32, 50),
(33, 45),
(33, 95),
(34, 24),
(34, 85),
(35, 5),
(35, 22),
(36, 6),
(37, 12);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
CREATE TABLE IF NOT EXISTS `pedidos` (
  `id_pedido` int NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `valor_total` decimal(10,2) NOT NULL,
  `forma_pagamento` varchar(255) DEFAULT NULL,
  `data_pedido` date NOT NULL,
  `cliente_id` int DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `FK6wgk5emlhdcthucnnmi4dpl33` (`cliente_id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `pedidos`
--

INSERT INTO `pedidos` (`id_pedido`, `status`, `valor_total`, `forma_pagamento`, `data_pedido`, `cliente_id`) VALUES
(1, 'pendente', 0.00, 'pix', '2025-07-28', 51),
(2, 'pendente', 0.00, 'pix', '2025-07-28', 51),
(3, 'pendente', 0.00, 'pix', '2025-07-28', 51),
(4, 'Aprovado', 166.80, 'cartao', '2025-07-28', 51),
(5, 'Aprovado', 166.80, 'cartao', '2025-07-28', 52),
(6, 'Aprovado', 175.20, 'cartao', '2025-07-28', 51),
(7, 'Aprovado', 144.80, 'pix', '2025-07-29', 53),
(8, 'Aprovado', 190.70, 'pix', '2025-07-29', 53),
(9, 'PENDENTE', 69.80, 'pix', '2025-07-29', 53),
(10, 'PENDENTE', 0.00, NULL, '2025-07-29', 51),
(11, 'PENDENTE', 0.00, NULL, '2025-07-29', 51),
(12, 'PENDENTE', 0.00, NULL, '2025-07-29', 51),
(13, 'Aprovado', 190.70, 'pix', '2025-07-29', 51),
(14, 'Aprovado', 113.80, 'pix', '2025-07-29', 51),
(15, 'PENDENTE', 45.90, 'pix', '2025-07-29', 53),
(16, 'PENDENTE', 24.00, 'pix', '2025-07-29', 53),
(17, 'PENDENTE', 98.30, 'cartão', '2025-07-29', 53),
(18, 'PENDENTE', 84.70, 'boleto', '2025-07-29', 55),
(19, 'Aprovado', 319.40, 'Cartão', '2025-07-30', 53),
(20, 'PENDENTE', 22.80, 'boleto', '2025-07-30', 53),
(21, 'PENDENTE', 91.80, 'pix', '2025-07-30', 53);

-- --------------------------------------------------------

--
-- Estrutura stand-in para view `vendas_vendedor_mes`
-- (Veja abaixo para a visão atual)
--
DROP VIEW IF EXISTS `vendas_vendedor_mes`;
CREATE TABLE IF NOT EXISTS `vendas_vendedor_mes` (
`ano` int
,`mes` int
,`total_livros_vendidos` bigint
,`total_pedidos` bigint
,`total_vendas` decimal(10,2)
,`vendedor_id` int
);

-- --------------------------------------------------------

--
-- Estrutura para tabela `vendedores`
--

DROP TABLE IF EXISTS `vendedores`;
CREATE TABLE IF NOT EXISTS `vendedores` (
  `id_vendedor` int NOT NULL AUTO_INCREMENT,
  `nome_fantasia` varchar(255) DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `cnpj` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_vendedor`),
  UNIQUE KEY `unique_email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `vendedores`
--

INSERT INTO `vendedores` (`id_vendedor`, `nome_fantasia`, `email`, `cnpj`, `telefone`, `senha`) VALUES
(1, 'BookStory', 'bookstory@gmail', '12344837637162', '(89) 86215-3615', '$2a$10$uJ5J0aPyZMmmCrU7/mBa4.MFRHRVFafutiok3VB4C1At3EWA.puOy'),
(2, 'BookSeus', 'bookseus@gmail', '23567900236155', '(89) 86215-3615', '$2a$10$HG2LEZWZuO0x2j7nQEH2VOERmenoly1qB4GVJGcjxrOE2XX/bigCy'),
(3, 'Livraria Paixão', 'livrariapaixao@gmail', '45678901234567', '(89) 86215-3615', '$2a$10$Fu3yCeHMKiV.XdjR.a7p2OlyjJ/M9HUuUhc/1yEnZ7iNEaqOuOmXC'),
(4, 'BusqueLivros', 'busquelivros@gmail', '76543219087321', '8999976453421', '$2a$10$ow/NZqY7ZHbbj4.QKQ0FYeVAtA8kZshT3Apd.02HUuEQeqQQd4Lji'),
(5, 'FusqueSeuLivro', 'fuque@gmail', '74276175261536', '897653245312', '$2a$10$eqIKLFadsOJxU0mL2OgE0Ox55AR3IyWOHMEZBPmloEspAMw3kLsie');

-- --------------------------------------------------------

--
-- Estrutura para view `vendas_vendedor_mes`
--
DROP TABLE IF EXISTS `vendas_vendedor_mes`;

DROP VIEW IF EXISTS `vendas_vendedor_mes`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vendas_vendedor_mes`  AS SELECT `lv`.`vendedor_id` AS `vendedor_id`, extract(year from `p`.`data_pedido`) AS `ano`, extract(month from `p`.`data_pedido`) AS `mes`, count(distinct `p`.`id_pedido`) AS `total_pedidos`, cast(sum(`ip`.`quantidade`) as signed) AS `total_livros_vendidos`, cast(sum(`p`.`valor_total`) as decimal(10,2)) AS `total_vendas` FROM ((`pedidos` `p` join `item_pedido` `ip` on((`p`.`id_pedido` = `ip`.`pedido_id`))) join `livros_vendedor` `lv` on((`ip`.`livro_id` = `lv`.`livro_id`))) GROUP BY `lv`.`vendedor_id`, extract(year from `p`.`data_pedido`), extract(month from `p`.`data_pedido`) ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
