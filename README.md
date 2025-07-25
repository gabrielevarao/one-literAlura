---
<p align="center"> <b> <sup>ONE - Oracle Next Education | Alura | Especialização Back-end</sup></b></p> 
<h1 align="center">LiterAlura</h1>

<div align="center">
  
![Static Badge](https://img.shields.io/badge/java-%236A5ACD?style=flat-square) 
![Static Badge](https://img.shields.io/badge/linha_de_comando-%232E8B57?style=flat-square)
![Static Badge](https://img.shields.io/badge/api_consumer-%232F4F4F%09?style=flat-square)
![Static Badge](https://img.shields.io/badge/spring_data_jpa-red?style=flat-square)
![Static Badge](https://img.shields.io/badge/postgres-blue?style=flat-square)
![Static Badge](https://img.shields.io/badge/hibernate-8A2BE2?style=flat-square)
![Static Badge](https://img.shields.io/badge/spring_boot-gray?style=flat-square)

</div>

Esta aplicação funciona como um catálogo de livros manipulável por linha de comando. Nela, o usuário pode cadastrar livros num banco de dados e acessá-los em buscas personalizadas.

## 🔄 Interações possíveis
↳  Cadastrar um livro no banco de dados pelo título.\
↳  Buscar um livro já cadastrado pelo título.\
↳  Consultar a lista de todos os livros cadastrados.\
↳  Consultar a lista de todos os autores cadastrados.\
↳  Buscar autores que estavam vivos em determinado ano.\
↳  Buscar livros em determinado idioma.
##  ⚙ Funcionamento
- Para cadastrar um livro, o usuário fornece seu título e, por meio dele, uma busca é feita na API Gutendex. Se o livro existir lá, as informações retornadas são persistidas no banco.
- Os autores também são entidades, e suas informações estão contidas nas dos livros.
- Todas as outras interações não necessitam do consumo da API Gutendex, sendo apenas consultas no banco de dados.

## 🚥 Validações 
#### ⚠️Autores duplicados
É possível que haja mais de um livro do mesmo autor. 
Dessa forma, para que não haja autores duplicados (sem restrição) ou para evitar que um livro não seja persistido porque é de um autor que já está no banco (restrição _unique_ para o nome do autor),
uma validação é feita antes de salvar os dados de um livro no banco: caso o autor já exista, ele é setado em vez de criado em cascata.

#### ⚠️Livros duplicados
De forma parecida, antes de buscar na API o livro solicitado, a aplicação consulta o título no banco e, caso já exista, mostra um aviso para o usuário comunicando que o livro já foi cadastrado.
> O objetivo dessas validações é evitar a violação da restrição de não duplicidade. Apenas são persistidos livros únicos e autores únicos.


## 🔗 Tecnologias utilizadas
- `API` [Gutendex API](https://gutendex.com/) para obter as informações dos livros).
- `Hibernate` para ORM.
- `Spring Data JPA` para faciitar a interação com o banco.
- `PostgreSQL` para banco de dados.

## 📚 Contexto
Este projeto é um dos desafios obrigatórios para a conclusão da _Tech Foundation_ - fase 3 da Especialização Back-end do Programa Oracle ONE.

---
