from bs4 import BeautifulSoup as parse_html
import logging
from locust import FastHttpUser, task


class AuthorizationCodeUser(FastHttpUser):

    def on_start(self):
        with self.client.get("/login") as response:
            logging.info(response)
            print(response)
            parsed = parse_html(response.text, "html.parser")
            logging.info(parsed)
            print(parsed)
            csrf = parsed.css.select_one('input[name="_csrf"]')["value"]
            self.client.post(
                "/login",
                {"username": "alice", "password": "password", "_csrf": csrf},
            )

    @task
    def main_page(self):
        with self.client.get("/admin") as response:
            pass
