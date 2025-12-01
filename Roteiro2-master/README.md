# Aplicativo de Rotas

## Descrição do App

Este aplicativo tem como objetivo gerenciar as rotas de transporte escolar, permitindo que o motorista organize e controle os alunos que utilizam o serviço. O app facilita a manutenção de um cadastro de alunos, condutores, escolas e responsáveis.

## Telas Principais

(Aqui você pode adicionar os prints das telas que mencionamos)

## Tecnologias Utilizadas

*   **Kotlin:** Linguagem de programação principal.
*   **Android Jetpack:**
    *   **Room:** Para persistência de dados local (banco de dados).
    *   **Lifecycle (ViewModel e LiveData):** Para gerenciar o ciclo de vida dos componentes da interface e os dados.
    *   **Navigation Component:** Para gerenciar a navegação entre as telas do app.
    *   **ViewBinding:** Para acessar as views do layout de forma segura.
*   **Material Design:** Para os componentes de interface do usuário.
*   **CircleImageView:** Para exibir imagens em formato circular.

## Como Instalar e Rodar

1.  Clone este repositório.
2.  Abra o projeto no Android Studio.
3.  Compile e execute o aplicativo em um emulador ou dispositivo Android.

Não há necessidade de configurações adicionais, como chaves de API.

## Funcionamento do CRUD

O aplicativo implementa as funcionalidades de Criar, Ler, Atualizar e Deletar (CRUD) para as seguintes entidades:

*   **Alunos**
*   **Condutores**
*   **Escolas**
*   **Responsáveis**

Todas as informações são armazenadas localmente em um banco de dados Room. As telas do aplicativo permitem que o usuário adicione, edite, visualize e remova registros de cada uma dessas entidades.

## Autores

*   **Ronaldo** - RA: 2403661
*   **Luis** - RA: 2402947
*   **Maycon** - RA: 2402929
