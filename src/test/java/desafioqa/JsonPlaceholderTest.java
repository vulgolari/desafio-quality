package desafioqa;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.restassured.RestAssured.given;

public class JsonPlaceholderTest {

    @BeforeAll
    public static void setup() {
        // Define a URL base para a API JSONPlaceholder
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    /**
     * Método auxiliar para criar um novo post
     *
     * @param title  Título do post
     * @param body   Corpo do post
     * @param userId ID do usuário
     * @return Response da criação do post
     */
    private Response createPost(String title, String body, int userId) {
        String newPost = String.format("{\"title\": \"%s\", \"body\": \"%s\", \"userId\": %d}", title, body, userId);
        return given()
                .header("Content-Type", "application/json")
                .body(newPost)
                .when()
                .post("/posts");
    }

    @Test
    public void testGetPosts() {
        // Faz uma requisição GET para o endpoint /posts
        Response response = given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)  // Verifica se o código de status da resposta é 200 (OK)
                .extract()
                .response();

        // Verifica se o retorno é uma lista de 100 posts
        assertEquals(100, response.jsonPath().getList("$").size(), "Deveria retornar 100 posts.");
    }

    @Test
    public void testCreatePost() {
        // Cria um novo post
        Response response = createPost("foo", "bar", 1)
                .then()
                .statusCode(201)  // Verifica se o código de status da resposta é 201 (Created)
                .extract()
                .response();

        // Verifica se o post foi criado com os dados corretos
        assertEquals("foo", response.jsonPath().getString("title"));
        assertEquals("bar", response.jsonPath().getString("body"));
        assertEquals(1, response.jsonPath().getInt("userId"));
    }

    @Test
    public void testCreatePostError() {
        // Tenta criar um post sem o campo obrigatório "title"
        String newPost = "{\"body\": \"bar\", \"userId\": 1}";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(newPost)
                .when()
                .post("/posts")
                .then()
                .statusCode(201) // Verifica se o código de status da resposta é 201 (Created)
                .extract()
                .response();

        // Verifica a mensagem de erro na resposta
        assertNotNull(response.getBody().asString(), "Deveria retornar uma resposta de erro.");
    }

    @Test
    public void testUpdatePost() {
        // Dados para atualizar o post com ID 1
        String updatedPost = "{\"id\": 1, \"title\": \"foo updated\", \"body\": \"bar updated\", \"userId\": 1}";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(updatedPost)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)  // Verifica se o código de status da resposta é 200 (OK)
                .extract()
                .response();

        // Verifica se o post foi atualizado com os dados corretos
        assertEquals("foo updated", response.jsonPath().getString("title"));
        assertEquals("bar updated", response.jsonPath().getString("body"));
        assertEquals(1, response.jsonPath().getInt("userId"));
    }

    @Test
    public void testUpdatePostError() {
        // Tenta atualizar um post com ID inexistente
        String updatedPost = "{\"id\": 999, \"title\": \"foo updated\", \"body\": \"bar updated\", \"userId\": 1}";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(updatedPost)
                .when()
                .put("/posts/999")
                .then()
                .statusCode(500) // Espera um código de status 500 (Internal Server Error)
                .extract()
                .response();

        // Verifica se a resposta contém a mensagem esperada
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("TypeError: Cannot read properties of undefined (reading 'id')"),
                "A resposta deveria conter a mensagem de erro 'TypeError: Cannot read properties of undefined (reading 'id')'");
    }

    @Test
    public void testPatchPost() {
        // Dados para atualizar parcialmente o post com ID 1
        String partialUpdate = "{\"title\": \"foo partially updated\"}";

        // Faz uma requisição PATCH para o endpoint /posts/1
        Response response = given()
                .header("Content-Type", "application/json")
                .body(partialUpdate)
                .when()
                .patch("/posts/1")
                .then()
                .statusCode(200)  // Verifica se o código de status da resposta é 200 (OK)
                .extract()
                .response();

        // Verifica se o post foi parcialmente atualizado com os dados corretos
        assertEquals("foo partially updated", response.jsonPath().getString("title"));
    }

    @Test
    public void testPatchPostError() {
        // Tenta atualizar parcialmente um post com ID inexistente
        String partialUpdate = "{\"title\": \"foo partially updated\"}";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(partialUpdate)
                .when()
                .patch("/posts/999")
                .then()
                .statusCode(200) // Verifica se o código de status da resposta é 200 (OK)
                .extract()
                .response();

        // Verifica a mensagem de erro na resposta
        assertNotNull(response.getBody().asString(), "Deveria retornar uma mensagem de erro.");
    }

    @Test
    public void testDeletePost() {
        // Faz uma requisição DELETE para o endpoint /posts/1
        Response response = given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200)  // Verifica se o código de status da resposta é 200 (OK)
                .extract()
                .response();

        // Verifica se o corpo da resposta está vazio, indicando que o post foi excluído
        assertEquals("{}", response.getBody().asString(), "O corpo da resposta deveria estar vazio após a exclusão.");
    }

    @Test
    public void testDeletePostError() {
        // Faz uma requisição DELETE para um post inexistente
        Response response = given()
                .when()
                .delete("/posts/999")
                .then()
                .statusCode(200)  // Verifica se o código de status da resposta é 200 (OK)
                .extract()
                .response();

        // Verifica se o corpo da resposta está vazio
        assertEquals("{}", response.getBody().asString(), "O corpo da resposta deveria estar vazio");
    }
}
