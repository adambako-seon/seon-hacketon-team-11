from idv import PhoneNumberProvider
from pydantic import BaseModel
from flask import Flask, request, jsonify
import requests

class PostBody(BaseModel):
    username: str
    userID: str

class Response(BaseModel):
    username: str
    userID: str
    phone_number: str

class PhoneNumberAPI:
    def __init__(self):
        self.app = Flask(__name__)
        self.provider = PhoneNumberProvider()
        self._setup_routes()

    def _setup_routes(self):
        @self.app.route('/get_phone_number', methods=['POST'])
        def get_phone_number():
            try:
                body = PostBody.model_validate_json(request.data)
            except Exception as e:
                return jsonify({"error": "Please provide a valid body"}), 400
            print(body.username)
            phone_number = self.provider.provide_phone_number(body.username)
            response = Response(
                username=body.username,
                userID=body.userID,
                phone_number=phone_number
            )

            """try:
                requests.post(url="", body=response.model_dump_json())
            except Exception:
                raise Exception("Failed to post to UM")"""

            return response.model_dump_json(), 200

    def run(self):
        self.app.run(host="0.0.0.0", port=5000)

if __name__ == "__main__":
    api = PhoneNumberAPI()
    api.run()
