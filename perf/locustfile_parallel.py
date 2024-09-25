from bs4 import BeautifulSoup as parse_html
import logging
from locust import HttpUser, User, tag, task


class AuthorizationCodeUser(HttpUser):
    abstract = True

    def on_start(self):
        with self.client.get("/login", verify=False, name=self.name) as response:
            logging.info(response)
            print(response)
            parsed = parse_html(response.text, "html.parser")
            logging.info(parsed)
            print(parsed)
            csrf = parsed.css.select_one('input[name="_csrf"]')["value"]
            self.client.post(
                "/login",
                {"username": "alice", "password": "password", "_csrf": csrf},
                verify=False,
                name=self.name
            )

    @task
    def main_page(self):
        with self.client.get("/admin", verify=False, name=self.name) as response:
            pass

class Native(AuthorizationCodeUser):

    base_url = "http://localhost:8081"
    host = "http://localhost:8081"
    name = "native"

class Jvm(AuthorizationCodeUser):

    base_url = "http://localhost:8080"
    host = "http://localhost:8080"
    name = "jvm"

