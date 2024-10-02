from bs4 import BeautifulSoup as parse_html
import logging
from locust import FastHttpUser, HttpUser, User, tag, task


class AuthorizationCodeUser(FastHttpUser):
    abstract = True

    def on_start(self):
        with self.client.get("/login", name=self.name) as response:
            logging.info(response)
            print(response.text)
            parsed = parse_html(response.text, "html.parser")
            logging.info(parsed)
            print(parsed)
            csrf = parsed.css.select_one('input[name="_csrf"]')["value"]
            self.client.post(
                "/login",
                {"username": "alice", "password": "password", "_csrf": csrf},
                name=self.name
            )

    @task
    def main_page(self):
        with self.client.get("/admin", name=self.name) as response:
            pass

class NativeOptimized(AuthorizationCodeUser):

    host = "http://localhost:9002"
    name = "native-pgo"

class Native(AuthorizationCodeUser):

    host = "http://localhost:9001"
    name = "native"

class Jvm(AuthorizationCodeUser):

    host = "http://localhost:9000"
    name = "jvm"

