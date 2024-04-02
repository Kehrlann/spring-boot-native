from bs4 import BeautifulSoup as parse_html
import logging
from locust import HttpUser, User, tag, task


class AuthorizationCodeUser(HttpUser):

    def on_start(self):
        with self.client.get("/authenticated/hello", verify=False) as response:
            logging.info(response)
            print(response)
            parsed = parse_html(response.text, "html.parser")
            logging.info(parsed)
            print(parsed)
            csrf = parsed.css.select_one('input[name="_csrf"]')["value"]
            self.client.post(
                "/login.do",
                {"username": "alice", "password": "password", "_csrf": csrf},
                verify=False,
            )

    @task
    def main_page(self):
        with self.client.get("/authenticated/hello", verify=False) as response:
            pass
