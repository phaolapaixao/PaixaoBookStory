# 📚 Marketplace de Livros  

Um sistema completo para compra e venda de livros, conectando **vendedores** e **clientes** em uma plataforma moderna e intuitiva.  

## 🚀 Funcionalidades  

✅ Cadastro e login de clientes e vendedores  
✅ Listagem de livros por categoria, autor e preço  
✅ Carrinho de compras e finalização de pedidos  
✅ Área do vendedor com gerenciamento de livros e pedidos  
✅ CRUD de livros exclusivo para vendedores  
✅ Clientes podem acessar a página de vendas e se cadastrar para realizar compras  
✅ Vários vendedores → variedade de livros com preços diferentes  
✅ Cada vendedor possui acesso único à sua própria página de livros  

---

## 🛠️ Tecnologias Utilizadas  

- **Backend:** Java + Spring Boot  
- **Frontend:** Thymeleaf + Bootstrap  
- **Banco de Dados:** MySQL  
- **API REST:** Integração entre frontend e backend  
- **Autenticação:** Spring Security  

---

## 📂 Estrutura do Projeto  

```
📦 marketplace-livros
├── 📂 backend
│   ├── src/main/java/com/api/Marketplace_de_livros
│   ├── src/main/resources
│   └── pom.xml
├── 📂 frontend
│   ├── templates (páginas HTML Thymeleaf)
│   └── static (CSS, JS, imagens)
└── README.md
```

---

## ⚙️ Como Executar  

### 🔧 Pré-requisitos  
- Java 17+  
- Maven  
- MySQL  

### ▶️ Passo a Passo  

1️⃣ **Clone o repositório:**  
```bash
git clone https://github.com/phaolapaixao/PaixaoBookStory
```

2️⃣ **Configure o banco de dados no arquivo `application.properties`:**  
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/marketplace
spring.datasource.username=root
spring.datasource.password=senha
```

3️⃣ **Rode o projeto:**  
```bash
cd backend
mvn spring-boot:run
```

4️⃣ **Acesse no navegador:**  
```
http://localhost:8080
```

---

## 📌 Futuras Melhorias  

🔹 Integração com métodos de pagamento  
🔹 Chat entre cliente e vendedor  
🔹 Recomendações personalizadas de livros  
🔹 API pública para desenvolvedores parceiros  

---

## 📷 Visualização do Sistema  

### 🏠 Página Inicial do Cliente  
<img width="1896" height="939" src="https://github.com/user-attachments/assets/fbceb6d7-cdc7-4980-92c8-0271dfea3d47" />  

🔹 O cliente pode visualizar livros, adicionar ao carrinho, pesquisar por livros, autores ou categorias e aplicar filtros como idioma, formato e preço.  
🔹 O acesso ao perfil e carrinho só é permitido após o login.  

---

### 🔐 Página de Login  
<img width="1854" height="857" src="https://github.com/user-attachments/assets/b3247ecb-64d9-49dd-8b28-c8298be48af5" />  

---

### 🏠 Área do Cliente  
<img width="1877" height="921" src="https://github.com/user-attachments/assets/463e94ad-57c7-48bd-b485-971f5552e5fd" />  

🔹 Após o login, o cliente pode acessar seu perfil, cadastrar/editar endereço e visualizar o carrinho.  

---

### 🏷️ Tela de Endereço  
<img width="681" height="881" src="https://github.com/user-attachments/assets/69bbaf7c-6763-41c3-817e-3019b5d5adad" />  

🔹 O CEP é preenchido automaticamente após informar estado, cidade e rua.  

---

### 🛒 Carrinho  
<img width="1453" height="430" src="https://github.com/user-attachments/assets/0819ceb7-baa1-4331-baab-4892840385a0" />  

🔹 Possui funcionalidades essenciais, como **remover itens** e **finalizar compra**.  

---

### 🔎 Exemplo de Filtro  
<img width="1593" height="910" src="https://github.com/user-attachments/assets/595e5cc0-b308-439c-bf90-2a266f9a73d6" />  

---

### 🔒 Segurança  
<img width="1797" height="891" src="https://github.com/user-attachments/assets/b79722d1-bb5e-4a05-86cc-9bb41bd09c11" />  

🔹 Acesso restrito para páginas exclusivas de vendedores, como cadastro de livros.  

---

### 👨‍💻 Login do Vendedor  
<img width="1855" height="911" src="https://github.com/user-attachments/assets/fb58cb95-aa39-4784-85e8-70bc77d1dbd1" />  

---

### 🏪 Página Inicial do Vendedor  
<img width="1413" height="658" src="https://github.com/user-attachments/assets/15296dc7-af51-49e6-992a-3982ee41cd3f" />  

🔹 O vendedor pode cadastrar livros, ver pedidos pendentes, atualizar dados e acessar relatórios.  

---

### 📊 Relatório de Vendas  
<img width="1200" height="460" src="https://github.com/user-attachments/assets/0bc171f9-d0bf-497f-95ca-e845fe0e83c3" />  

---

### 📚 Lista de Livros do Vendedor  
<img width="1794" height="962" src="https://github.com/user-attachments/assets/f5343b4e-f8d8-47eb-a87e-040cfe3db9e6" />  

---

### ✍ Cadastro de Livros  
<img width="1119" height="899" src="https://github.com/user-attachments/assets/845e66ca-070e-497c-a488-ee2b5fac8314" />  

---

## 👩‍💻 Autora  

Projeto desenvolvido por **Phaola Paixão** 💜  
