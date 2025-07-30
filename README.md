
# 📚 Marketplace de Livros  

Um sistema completo para compra e venda de livros, conectando **vendedores** e **clientes** em uma plataforma intuitiva e moderna.  

## 🚀 Funcionalidades  

✅ Cadastro e login de clientes e vendedores  
✅ Listagem de livros por categoria, autor e preço  
✅ Carrinho de compras e finalização de pedidos  
✅ Área do vendedor com gerenciamento de livros e pedidos 
✅ O vendedor é o responsável pelo CRUD, é ele que vai ter acesso ao cadastro de livros
✅ Os Clientes vão podem acessar a página de vendas e fazer cadastro para realizar suas compras
✅ Como são vários vendedores os clientes terão acesso a uma variedade de livros de preços diferentes
✅ Cada vendedor que se cadastra tem acesso único ao sua páginas de livros


---

## 🛠️ Tecnologias Utilizadas  

- **Backend:** Java + Spring Boot  
- **Frontend:** Thymeleaf + Bootstrap  
- **Banco de Dados:** MySQL
- **API REST:** Para integração entre frontend e backend  
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

## Visualização
---
<img width="1896" height="939" alt="image" src="https://github.com/user-attachments/assets/fbceb6d7-cdc7-4980-92c8-0271dfea3d47" />
---
🔹Essa é a página inicial do cliente, ele pode ver livros, adicionar ao carrinhos, pesquisar por livros, autores ou categorias e fitrar 
por algumas informações, como idioma, formato e preço.
🔹O Cliente só vai poder acessar o perfil e ver carrinho depois que estiver logado.
---
<img width="1854" height="857" alt="image" src="https://github.com/user-attachments/assets/b3247ecb-64d9-49dd-8b28-c8298be48af5" />
---
🔹Essa é a página de login
---
<img width="1877" height="921" alt="image" src="https://github.com/user-attachments/assets/463e94ad-57c7-48bd-b485-971f5552e5fd" />
---
Depois que o cliente realiza login ele tem acesso ao seu endereço, perfil e carrinho, ele pode editar ou cadastrar endereço,
caso seja seu primeiro registro.
🔹Tela de Endereço
---
<img width="681" height="881" alt="image" src="https://github.com/user-attachments/assets/69bbaf7c-6763-41c3-817e-3019b5d5adad" />
---
Como não é a primeira vez, o usuário pode editar o perfil, o CEP é preenchido automaticamente após digitar estado, cidade, e rua
---
🔹Carrinho
---
<img width="1453" height="430" alt="image" src="https://github.com/user-attachments/assets/0819ceb7-baa1-4331-baab-4892840385a0" />
---
O carrinho apresenta as funções que todo site de vendas deve ter, como remover e finalizar compra.
🔹Exemplo de Filtro
---
<img width="1593" height="910" alt="image" src="https://github.com/user-attachments/assets/595e5cc0-b308-439c-bf90-2a266f9a73d6" />
---
🔹Segurança
---
<img width="1797" height="891" alt="image" src="https://github.com/user-attachments/assets/b79722d1-bb5e-4a05-86cc-9bb41bd09c11" />
---
Isso é mostrado quando o usuário não tem acesso a uma rota, nesse caso é o cadastro de livros que só vendedores cadastrados podem acessar
🔹Login do Vendedor
---
<img width="1855" height="911" alt="image" src="https://github.com/user-attachments/assets/fb58cb95-aa39-4784-85e8-70bc77d1dbd1" />
---
🔹Página Inicial do Vendedor
---
<img width="1413" height="658" alt="image" src="https://github.com/user-attachments/assets/15296dc7-af51-49e6-992a-3982ee41cd3f" />
---
Aqui o vendedor pode cadastrar novos livros, ver pedidos pendentes e atualizar e ver relatórios.
---
<img width="1182" height="374" alt="image" src="https://github.com/user-attachments/assets/c4a1d8ad-a319-4a34-b2cb-8a321187fba3" />
---
O vendedor também pode visualizar os clientes que mais compraram livros

🔹Relatório de Vendas
---
<img width="1200" height="460" alt="image" src="https://github.com/user-attachments/assets/0bc171f9-d0bf-497f-95ca-e845fe0e83c3" />
---
🔹Lista de Livros Cadastrados pelo vendedor.
---
<img width="1794" height="962" alt="image" src="https://github.com/user-attachments/assets/f5343b4e-f8d8-47eb-a87e-040cfe3db9e6" />
---
🔹Cadastro de Livros
---
<img width="1119" height="899" alt="image" src="https://github.com/user-attachments/assets/845e66ca-070e-497c-a488-ee2b5fac8314" />
---
## 👩‍💻 Autora  

Projeto desenvolvido por **Phaola Paixão** 💜  
