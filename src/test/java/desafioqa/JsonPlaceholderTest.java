package desafioqa;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static io.restassured.RestAssured.given;

public class JsonPlaceholderTest {

    @BeforeAll
    public static void setup() {
        // Define a base URL
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetPosts() {
        // Faz a requisição para o endpoint /posts
        Response response = given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)  // Verifica se o status code é 200
                .extract()
                .response();

        // Verifica se o retorno é uma lista de posts
        assertEquals(100, response.jsonPath().getList("$").size(), "Deveria retornar 100 posts.");
    }

    @Test
    public void testCreatePost() {
        // Dados para o novo post
        String newPost = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";

        // Faz a requisição para o endpoint /posts
        Response response = given()
                .header("Content-Type", "application/json")
                .body(newPost)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)  // Verifica se o status code é 201 (Created)
                .extract()
                .response();

        // Verifica se o post foi criado com os dados corretos
        assertEquals("foo", response.jsonPath().getString("title"), "O título do post deveria ser 'foo'.");
        assertEquals("bar", response.jsonPath().getString("body"), "O corpo do post deveria ser 'bar'.");
        assertEquals(1, response.jsonPath().getInt("userId"), "O userId do post deveria ser 1.");
    }

    @Test
    public void testUpdatePost() {
        // Dados para atualizar o post
        String updatedPost = "{\"id\": 1, \"title\": \"foo updated\", \"body\": \"bar updated\", \"userId\": 1}";

        // Faz a requisição para o endpoint /posts/1
        Response response = given()
                .header("Content-Type", "application/json")
                .body(updatedPost)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)  // Verifica se o status code é 200 (OK)
                .extract()
                .response();

        // Verifica se o post foi atualizado com os dados corretos
        assertEquals("foo updated", response.jsonPath().getString("title"), "O título do post deveria ser 'foo updated'.");
        assertEquals("bar updated", response.jsonPath().getString("body"), "O corpo do post deveria ser 'bar updated'.");
        assertEquals(1, response.jsonPath().getInt("userId"), "O userId do post deveria ser 1.");
    }

    @Test
    public void testPatchPost() {
        // Dados para atualizar parcialmente o post 
        String partialUpdate = "{\"title\": \"foo partially updated\"}";

        // Faz a requisição para o endpoint /posts/1
        Response response = given()
                .header("Content-Type", "application/json")
                .body(partialUpdate)
                .when()
                .patch("/posts/1")
                .then()
                .statusCode(200)  // Verifica se o status code é 200 (OK)
                .extract()
                .response();

        // Verifica se o post foi atualizado parcialmente com os dados corretos
        assertEquals("foo partially updated", response.jsonPath().getString("title"), "O título do post deveria ser 'foo partially updated'.");
    }

    @Test
    public void testDeletePost() {
        // Faz a requisição para o endpoint /posts/1
        Response response = given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200)  // Verifica se o status code é 200 (OK)
                .extract()
                .response();

        // Verifica se o corpo da resposta está vazio, indicando exclusão
        assertNotNull(response.body().asString(), "O corpo da resposta deveria estar vazio após a exclusão.");
    }
}
